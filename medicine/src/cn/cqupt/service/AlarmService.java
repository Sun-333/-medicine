package cn.cqupt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;


import Modle.TableValue;
import Modle.findAlarmByMoreCondition;
import Util.CheckUtil;
import Util.PageBean;
import Util.PlanUtil;
import Util.ResultBean;
import Util.UserUtil;
import cn.cqupt.dao.AlarmDao;
import cn.cqupt.dao.PasAlarmDao;
import cn.cqupt.dao.PatientDao;
import cn.cqupt.dao.PlanDao;
import cn.cqupt.entity.Alarm;
import cn.cqupt.entity.PasAlarm;
import cn.cqupt.entity.Patient;
import cn.cqupt.entity.Plan;


public class AlarmService {
	//配置依赖
	AlarmDao alarmDao;
	public void setAlarmDao(AlarmDao alarmDao) {
		this.alarmDao = alarmDao;
	}
	PasAlarmDao pasAlarmDao;
	public void setPasAlarmDao(PasAlarmDao pasAlarmDao) {
		this.pasAlarmDao = pasAlarmDao;
	}
	PatientDao patientDao;
	PlanDao planDao;
	public void setPatientDao(PatientDao patientDao) {
		this.patientDao = patientDao;
	}
	public void setPlanDao(PlanDao planDao) {
		this.planDao = planDao;
	}
	/**
	 * 多条件查询告警
	 * @param condition
	 * @return
	 */
	public ResultBean<PageBean<Map<String, Object>>> findByMoreCondition(findAlarmByMoreCondition condition) {
		
		//创建pageBean
		PageBean<Map<String, Object>> pageBean = new PageBean<>();
		List<Map<String, Object>> alarmList = new ArrayList<>();
		//设置限制页数
		pageBean.setLimit(condition.getLimit());
		//设置查询页数
		pageBean.setPage(condition.getCurrentPage().intValue());
		//获取总记录数
		int cnt = alarmDao.findCntByMoreCondition(condition);
		//设置总记录数
		pageBean.setTotalCount(cnt);
		//直接调用下层方法执行查询
		int begin =pageBean.getBegin();
		int limit = pageBean.getLimit();
		List<Alarm> alarms = alarmDao.findByMoreCondition(condition,begin,limit);
		/**
		 * 封装返回结果集
		 */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-hh HH:mm:ss");
		String[] tables = {"告警ID","姓名","计划ID","告警种类","住院号","科室","床位","执行护士","告警产生时间"};
		List<String> tableNames =Arrays.asList(tables);
		//表头封装完成
		pageBean.setTableNames(tableNames);
		//封装集合数据
		for (Alarm alarm : alarms) {
			//创建封装计划的map
			Map<String, Object> map = new LinkedHashMap<>();
			//告警ID
			TableValue alarmId=new TableValue();
			alarmId.setTableName("告警ID");
			alarmId.setValue(alarm.getAlarmId());
			map.put("alarmId", alarmId);
			//姓名
			TableValue patientName= new TableValue();
			patientName.setTableName("姓名");
			patientName.setValue(alarm.getPatient().getPatientName());
			map.put("patientName", patientName);
			//计划ID
			TableValue planId= new TableValue();
			planId.setTableName("计划ID");
			planId.setValue(alarm.getPlan().getPlanId());
			map.put("planId",planId);
			//告警种类
			TableValue alarmType = new TableValue();
			alarmType.setTableName("告警种类");
			String alarmTypeValue =null;
			if(alarm.getAlarmType()==5) alarmTypeValue = "发药超时告警"; 
			if(alarm.getAlarmType()==4) alarmTypeValue = "服药超时告警";
			alarmType.setValue(alarmTypeValue);
			map.put("alarmType", alarmType);
			//住院号
			TableValue hospitalId = new TableValue();
			hospitalId.setTableName("住院号");
			hospitalId.setValue(alarm.getPatient().getHospitalId());
			map.put("hospitalId", hospitalId);
			//科室
			TableValue depName = new TableValue();
			depName.setTableName("科室");
			depName.setValue(alarm.getDep().getDepName());
			map.put("depName", depName);
			//床位
			TableValue bedNumber = new TableValue();
			bedNumber.setTableName("床位");
			bedNumber.setValue(alarm.getBed().getBedNumber());
			map.put("bedNumber", bedNumber);
			//执行护士
			TableValue excuteNurseName = new TableValue();
			excuteNurseName.setTableName("执行护士");
			excuteNurseName.setValue(alarm.getPlan().getExcuteNurseName());
			map.put("excuteNurseName", excuteNurseName);
			//计划产生时间 
			TableValue alarmTime = new TableValue();
			alarmTime.setTableName("告警产生时间");
			alarmTime.setValue(format.format(alarm.getAlarmTime()));
			map.put("alarmTime", alarmTime);
			alarmList.add(map);
		}
		pageBean.setList(alarmList);
		return new ResultBean<PageBean<Map<String,Object>>>(pageBean);
	}
	@Transactional
	public ResultBean<Boolean> handleAlarm(String alarmId, String handleStr) {
		//获取此告警
		Alarm alarm = alarmDao.findByAlarmId(alarmId);
		/**
		 * 校验
		 */
		//此告警是否还存在
		CheckUtil.notNull(alarm,"此告警已经被处理请不要重复处理");
		//获取处理告警情况
		String handleMethod = PlanUtil.gethandleStr(handleStr);
		CheckUtil.notEmpty(handleMethod,"处理方法不存在");
		Map<String, Integer> map = PlanUtil.getHandleCondition(handleMethod);
		System.out.println(map.toString());
		Integer alarmState = map.get("alarmState");
		Integer change2State = map.get("change2State");
		//判断告警状态与所选处理方式是否匹配
		CheckUtil.check(alarm.getAlarmType().equals(alarmState),"告警处理方式不符合规范");
		/**
		 * 告警处理操作
		 * 1.将实时告警移入历史告警中
		 * 2.将更新病人状态
		 */
		//1.删除实时告警
		alarmDao.delete(alarm);
		//配置历史实时告警
		PasAlarm pasAlarm = new PasAlarm();
		pasAlarm.setAlarmId(alarm.getAlarmId());
		pasAlarm.setAlarmTime(alarm.getAlarmTime());
		pasAlarm.setAlarmType(alarm.getAlarmType());
		pasAlarm.setHandlePerson(UserUtil.getUser().getUserName());
		pasAlarm.setHandleTime(new Date());
		pasAlarm.setHandleReason(handleStr);
		pasAlarm.setHospitalId(alarm.getPatient().getHospitalId());
		pasAlarm.setPatientName(alarm.getPatient().getPatientName());
		pasAlarm.setPlanId(alarm.getPlan().getPlanId());
		pasAlarm.setDepName(alarm.getDep().getDepName());
		pasAlarm.setBedNumber(alarm.getBed().getBedNumber());
		//2.保存到历史告警
		pasAlarmDao.add(pasAlarm);
		//3.更新病人的服药
		Plan plan = alarm.getPlan();
		Patient patient = alarm.getPatient();
		/**
		 * 首先判定切换状态是否为3
		 * 	不为三计划于病人状态都切换成相应状态
		 */
		if(change2State!=3){
			plan.setPlanState(change2State);
			//patient.setPlanState(change2State);
			
		}else{
			Set<Plan> setPlan = patient.getSetPlan();
			int cnt=0;//统计现在执行的计划参数
			plan.setExcuteTime(new Date());//将计划执行时间设置为当前时间
			for (Plan plan_find : setPlan) {
				//如果查询到的计划状态不为0,并且计划ID不予告警计划ID相同就执行
				if(plan_find.getPlanState()!=0&&plan_find.getPlanId()!=plan.getPlanId()){
					plan.setPlanState(change2State);
					//patient.setPlanState(plan_find.getPlanState());
					cnt++;
				}else{
					plan.setPlanState(change2State);
					//patient.setPlanState(change2State);
				}
			}
			//校验同时执行计划的数量，防止BUg
			CheckUtil.check(cnt<=1,"此病人同时执行的计划超过2个,请联系管理员修改此病人的计划设置，此病人服药管理功能失效");
		}
		//保存新病人与新计划信息
		patientDao.update(patient);
		planDao.update(plan);
		return new ResultBean<Boolean>(true);
	}
	/**
	 * 添加告警
	 * @param alarm
	 * @return
	 */
	@Transactional
	public ResultBean<Boolean> addAlarm(Alarm alarm){
		alarmDao.add(alarm);
		return new ResultBean<Boolean>(true);
	}
}

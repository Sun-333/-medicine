package cn.cqupt.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Modle.TableValue;
import Modle.findPasAlam;
import Util.PageBean;
import Util.PlanUtil;
import Util.ResultBean;
import cn.cqupt.dao.PasAlarmDao;
import cn.cqupt.entity.PasAlarm;


public class PasAlarmService {
	//配置依赖
	PasAlarmDao pasAlarmDao;
	public void setPasAlarmDao(PasAlarmDao pasAlarmDao) {
		this.pasAlarmDao = pasAlarmDao;
	}
	public ResultBean<PageBean<Map<String, Object>>> findByMoreCondition(findPasAlam findPasAlam) {
		//创建pageBean
		PageBean<Map<String, Object>> pageBean = new PageBean<>();
		//设置查询页数
		pageBean.setPage(findPasAlam.getCurrentPage());
		//设置每页显示记录条数
		pageBean.setLimit(findPasAlam.getLimit().intValue());
		//获取总记录条数
		int Cnt = pasAlarmDao.findCntByMoreCondition(findPasAlam);
		System.out.println(Cnt);
		//设置总记录数
		pageBean.setTotalCount(Cnt);
		//调用下层方法执行查询
		int begin=pageBean.getBegin();
		int limit = pageBean.getLimit();
		List<PasAlarm> list = pasAlarmDao.findByMoreCondition(begin,limit,findPasAlam);
		/**
		 * 分装结果
		 */
		//封装表头
		String[] tableArray = {"告警ID","住院号","计划ID","告警种类","姓名","告警产生时间","告警恢复人","告警恢复时间","恢复原因值"};
		List<String> tableNames = Arrays.asList(tableArray);
		pageBean.setTableNames(tableNames);
		//封装数据集合
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		List<Map<String, Object>> maps = new LinkedList<>();
		for (PasAlarm pasAlarm : list) {
			Map<String,Object> map = new LinkedHashMap<>();
			//告警ID
			TableValue alarmId = new TableValue();
			alarmId.setTableName("告警ID");
			alarmId.setValue(pasAlarm.getAlarmId());
			map.put("alarmId",alarmId);
			//设置住院号
			TableValue hospitalId = new TableValue();
			hospitalId.setTableName("住院号");
			hospitalId.setValue(pasAlarm.getHospitalId());
			map.put("hospitalId",hospitalId);
			//计划ID
			TableValue planId = new TableValue();
			planId.setTableName("计划ID");
			planId.setValue(pasAlarm.getPlanId());
			map.put("planId",planId);
			//告警种类
			TableValue alarmType = new TableValue();
			alarmType.setTableName("告警种类");
			alarmType.setValue(pasAlarm.getAlarmType());
			map.put("alarmType",alarmType);
			//姓名
			TableValue patientId = new TableValue();
			patientId.setTableName("姓名");
			patientId.setValue(pasAlarm.getPatientName());
			map.put("patientId",patientId);
			//告警产生时间
			TableValue alarmTime = new TableValue();
			alarmTime.setTableName("告警产生时间");
			alarmTime.setValue(format.format(pasAlarm.getAlarmTime()));
			map.put("alarmTime",alarmTime);
			//告警恢复人
			TableValue handlePerson = new TableValue();
			handlePerson.setTableName("告警恢复人");
			handlePerson.setValue(pasAlarm.getHandlePerson());
			map.put("handlePerson",handlePerson);
			//告警恢复时间
			TableValue handleTime =  new TableValue();
			handleTime.setTableName("告警恢复时间");
			handleTime.setValue(format.format(pasAlarm.getHandleTime()));
			map.put("handleTime",handleTime);
			//告警恢复原因
			TableValue handleReason = new TableValue();
			handleReason.setTableName("告警恢复原因");
			handleReason.setValue(PlanUtil.changHandleStr2Reason(pasAlarm.getHandleReason()));
			map.put("handleReason",handleReason);
			maps.add(map);
		}
		pageBean.setList(maps);
		return new ResultBean<PageBean<Map<String,Object>>>(pageBean);
	}
}

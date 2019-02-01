package cn.cqupt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


import Modle.DepByDownLoad;
import Modle.DoctorTelDownLoad;
import Modle.DoctorTelFind;
import Modle.TableValueChangeNull;
import Util.CheckUtil;
import Util.PageBean;
import Util.PlanUtil;
import Util.ResultBean;
import Util.UserUtil;
import cn.cqupt.dao.DoctorTelDao;
import cn.cqupt.dao.PatientDao;
import cn.cqupt.dao.PlanDao;
import cn.cqupt.entity.Alarm;
import cn.cqupt.entity.Bed;
import cn.cqupt.entity.Dep;
import cn.cqupt.entity.DoctorTel;
import cn.cqupt.entity.PasDoctorTel;
import cn.cqupt.entity.Patient;
import cn.cqupt.entity.Plan;
@Transactional
public class DoctorTelService {
	
	private DoctorTelDao doctorTelDao;
	private PlanDao planDao;
	private PatientDao patientDao;
	private PasDoctorTelService pasDoctorTelService;
	private AlarmService alarmService;
	
	public void setAlarmService(AlarmService alarmService) {
		this.alarmService = alarmService;
	}


	public void setPasDoctorTelService(PasDoctorTelService pasDoctorTelService) {
		this.pasDoctorTelService = pasDoctorTelService;
	}


	public void setPatientDao(PatientDao patientDao) {
		this.patientDao = patientDao;
	}


	public void setDoctorTelDao(DoctorTelDao doctorTelDao) {
		this.doctorTelDao = doctorTelDao;
	}


	public void setPlanDao(PlanDao planDao) {
		this.planDao = planDao;
	}

	
	public ResultBean<Map<String, Object>> find(String planId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//1.非空校验
		CheckUtil.notEmpty(planId,"计划编号不能为空");
		//2.执行查询
		Plan plan = planDao.findByPlanId(planId);
		CheckUtil.notNull(plan,"计划ID对应计划不存在");
		//将计划转化成饭前饭后
		Integer planType= plan.getPlanType();
		boolean eatType= true;
		if(planType%2==0){
			eatType=false;
		}
		Patient patient = plan.getPatient();
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("patientName",patient.getPatientName());//病人姓名
		result.put("bedNumber",patient.getBed().getBedNumber());//病人床号
		result.put("depName",patient.getDep().getDepName());//科室名称
		result.put("useTime",format.format(new Date()));//用药日期
		result.put("hospitalId",patient.getHospitalId());
		List<DoctorTel> doctorTels = doctorTelDao.findByPatientId(patient.getPatientId(),eatType);
		for (DoctorTel doctorTel : doctorTels) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("sendTime",format2.format(doctorTel.getSendTime()));
				map.put("medicine", doctorTel.getMedicine());
				map.put("quantity", doctorTel.getQuantity());
				map.put("frequence",doctorTel.getFrequence());
				map.put("detail",doctorTel.getDetail());
				list.add(map);
		}
		result.put("doctorTels",list);
		return new ResultBean<Map<String,Object>>(result);
	}

	/**
	 * 添加医嘱
	 * @param doctorTel
	 * @return
	 */
	public ResultBean<Boolean> add(DoctorTel doctorTel) {
		Calendar calendar_now = Calendar.getInstance();
		Calendar calendar_begin = Calendar.getInstance();
		calendar_begin.setTime(doctorTel.getBeginTime());
		calendar_now.setTime(new Date());
		Calendar calendar_End = Calendar.getInstance();
		calendar_End.setTime(doctorTel.getEndTime());
		//1.校验
		CheckUtil.notEmpty(doctorTel.getMedicine(),"药品不能为空");
		CheckUtil.notEmpty(doctorTel.getFrequence(),"频次不能为空");
		CheckUtil.notEmpty(doctorTel.getQuantity(),"药品量不能为空");
		CheckUtil.notNull(doctorTel.getBeginTime(),"起始时间不能为空");
		CheckUtil.notNull(doctorTel.getEatType(),"饭前/饭后不能为空");
		CheckUtil.notNull(doctorTel.getEndTime(),"结束时间不能为空");
		
		CheckUtil.notNull(doctorTel.getPatient(),"病人已经不存在");
		CheckUtil.check(calendar_now.before(calendar_begin),"开始时间必须在当前时间之后");
		CheckUtil.check(calendar_begin.before(calendar_End),"结束时间必须在开始时间之后");
		//2.查询对应病人是否存在
		Patient patient = patientDao.findOne(doctorTel.getPatient().getPatientId());
		
		CheckUtil.notNull(patient,"对应病人不存在,请刷新页面");
		doctorTel.setPatient(patient);
		//3.系统设置缺省项
		doctorTel.setState(0);//设置医嘱为未下达
		doctorTel.setExcuteTime(new Date());
		doctorTel.setSendTime(new Date());
		doctorTelDao.add(doctorTel);//保存到数据库
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 删除医嘱
	 * @param doctorTelId
	 * @return
	 */
	public ResultBean<Boolean> delect(Integer doctorTelId) {
		/**
		 * 校验
		 */
		//1.校验医嘱Id是否为空
		CheckUtil.notNull(doctorTelId,"医嘱编号不能为空");
		//2.校验对应医嘱是否存在
		DoctorTel doctorTel = doctorTelDao.findOne(doctorTelId);
		CheckUtil.notNull(doctorTel,"医嘱Id对应医嘱不存在");
		//3.判断医嘱的状态是否能删除
		CheckUtil.check(doctorTel.getState()==0,"此医嘱状态不为未下达，不能删除,需要删除必须撤销医嘱后再删除");
		doctorTelDao.delete(doctorTel);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 修改医嘱
	 * @param doctorTel
	 * @return
	 */

	public ResultBean<Boolean> modify(DoctorTel doctorTel) {
		Calendar calendar_now = Calendar.getInstance();
		Calendar calendar_begin = Calendar.getInstance();
		calendar_begin.setTime(doctorTel.getBeginTime());
		calendar_now.setTime(new Date());
		Calendar calendar_End = Calendar.getInstance();
		calendar_End.setTime(doctorTel.getEndTime());
		/*
		 * 校验
		 */
		//1.校验非空
		CheckUtil.notEmpty(doctorTel.getMedicine(),"药品不能为空");
		CheckUtil.notEmpty(doctorTel.getFrequence(),"频次不能为空");
		CheckUtil.notEmpty(doctorTel.getQuantity(),"药品量不能为空");
		CheckUtil.notNull(doctorTel.getDoctorTelId(),"医嘱编号不能为空");
		CheckUtil.notNull(doctorTel.getBeginTime(),"起始时间不能为空");
		CheckUtil.notNull(doctorTel.getEndTime(),"结束时间不能为空");
		CheckUtil.notEmpty(doctorTel.getDoctorName(),"医生名称不能为空");
		CheckUtil.notNull(doctorTel.getEatType(),"饭前/饭后不能为空");
		CheckUtil.check(calendar_now.before(calendar_begin),"开始时间必须在当前时间之后");
		CheckUtil.check(calendar_begin.before(calendar_End),"结束时间必须在开始时间之后");
		//2.校验是否能修改
		DoctorTel doctorTel_find = doctorTelDao.findOne(doctorTel.getDoctorTelId());
		CheckUtil.notNull(doctorTel_find,"对应医嘱已经不存在需要刷新页面");
		CheckUtil.check(doctorTel_find.getState().intValue()==0,"对应医嘱不处于未下达状态不能修改");
		
		//执行修改
		doctorTel_find.setDetail(doctorTel.getDetail());
		doctorTel_find.setEndTime(doctorTel.getEndTime());
		doctorTel_find.setFrequence(doctorTel.getFrequence());
		doctorTel_find.setMedicine(doctorTel.getMedicine());
		doctorTel_find.setQuantity(doctorTel.getQuantity());
		doctorTel_find.setBeginTime(doctorTel.getBeginTime());
		doctorTel_find.setDoctorName(doctorTel.getDoctorName());
		doctorTel_find.setEatType(doctorTel.getEatType());
		doctorTelDao.update(doctorTel_find);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 医嘱撤销
	 * @param doctorTelId
	 * @return
	 */
	
	public ResultBean<Boolean> withdraw(Integer doctorTelId) {
		//1.校验
		CheckUtil.notNull(doctorTelId,"医嘱编号不能为空");
		DoctorTel doctorTel_find = doctorTelDao.findOne(doctorTelId);
		CheckUtil.notNull(doctorTel_find,"对应医嘱已近不存在请刷新页面");
		CheckUtil.check(doctorTel_find.getState().intValue()!=0,"对应的计划已经处于未下达状态，本次操作无效");
		//2.执行
		doctorTel_find.setState(0);
		doctorTel_find.setExcuteTime(new Date());
		doctorTel_find.setExcuteTimes(0);
		doctorTelDao.update(doctorTel_find);
		return new ResultBean<Boolean>(true);
	}
	
	/**
	 * 计划下达
	 * @param doctorTelId
	 * @return
	 */
	
	public ResultBean<Boolean> ackDoctorTel(Integer doctorTelId) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		//校验
		CheckUtil.notNull(doctorTelId,"医嘱编号不能为空");
		DoctorTel doctorTel_find = doctorTelDao.findOne(doctorTelId);
		CheckUtil.notNull(doctorTel_find,"对应医嘱已经不存在请刷新页面");
		CheckUtil.check(doctorTel_find.getState().intValue()!=1,"对应的计划已经处于下达状态，本次操作无效");
		//时间校验
		Calendar calendar_begin=Calendar.getInstance();
		calendar_begin.setTime(doctorTel_find.getBeginTime());
		Calendar calendar_now=Calendar.getInstance();
		calendar_now.setTime(new Date());
		CheckUtil.check(calendar_now.before(calendar_begin),"计划开始时间已经在当前时间之前");
		//执行设置状态为下达
		doctorTel_find.setState(1);
		//设置本次操作的护士名字
		doctorTel_find.setExcuteNurseName(UserUtil.getUser().getUserName());
		//设置本次操作的时间
		doctorTel_find.setSendTime(new Date());
		//初始化计划状态
		doctorTel_find.setExcuteTimes(0);
		doctorTelDao.update(doctorTel_find);
		return new ResultBean<Boolean>(true);
	}
	
	/**
	 * 为删除线程提供接口
	 * @return
	 */
	public ResultBean<Boolean> autoChange() {
		List<DoctorTel> doctorTels = doctorTelDao.findAll();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime( new Date());
		for (DoctorTel doctorTel : doctorTels) {
			if(calendar.getTime().after(doctorTel.getEndTime())&&
					(doctorTel.getState().intValue()==2||doctorTel.getState().intValue()==3)) {
				this.delect(doctorTel.getDoctorTelId());
				PasDoctorTel pasDoctorTel = new PasDoctorTel();
				pasDoctorTel.setBeginTime(doctorTel.getBeginTime());
				pasDoctorTel.setDetail(doctorTel.getDetail());
				pasDoctorTel.setEatType(doctorTel.getEatType());
				pasDoctorTel.setEndTime(doctorTel.getEndTime());
				pasDoctorTel.setExcuteNurseId(doctorTel.getExcuteNurseId());
				pasDoctorTel.setExcuteNurseName(doctorTel.getExcuteNurseName());
				pasDoctorTel.setExcuteTime(new Date());
				pasDoctorTel.setExcuteTimes(doctorTel.getExcuteTimes());
				pasDoctorTel.setFrequence(doctorTel.getFrequence());
				pasDoctorTel.setMedicine(doctorTel.getMedicine());
				pasDoctorTel.setPatientName(doctorTel.getPatient().getPatientName());
				pasDoctorTel.setQuantity(doctorTel.getQuantity());
				pasDoctorTel.setSendDoctor(doctorTel.getDoctorName());
				pasDoctorTel.setSendTime(doctorTel.getSendTime());
				pasDoctorTel.setState(4);
				pasDoctorTelService.addPasDoctorTel(pasDoctorTel);
			}
			
			if(calendar.after(doctorTel.getBeginTime())
					&&(doctorTel.getState().intValue()==1)){
				Patient patient = doctorTel.getPatient();
				Dep dep = doctorTel.getPatient().getDep();
				Bed bed = doctorTel.getPatient().getBed();
				Alarm alarm = new Alarm();
				alarm.setAlarmTime(new Date());
				alarm.setAlarmType(6);
				alarm.setBed(bed);
				alarm.setDep(dep);
				alarm.setPatient(patient);
				alarmService.addAlarm(alarm);
			}
		}
		return new ResultBean<>(true);
	}
	
	public ResultBean<PageBean<Map<String,Object>>> moreCondition(Integer limit, Integer currentPage, DoctorTelFind doctorTelFind) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//1.校验
		CheckUtil.notNull(limit,"每页显示记录数量不能为空");
		CheckUtil.notNull(currentPage,"当前页数不能为空");
		PageBean<Map<String,Object>> pageBean = new PageBean<>();
		pageBean.setLimit(limit);//设置每页显示数量
		pageBean.setPage(currentPage);//设置当前页数
		
		//2.查询数量
		Integer cnt = doctorTelDao.findCntByCnt(doctorTelFind);
		pageBean.setTotalCount(cnt);
		
		//3.具体查询
		String[] tableNames = {"医嘱编号","住院号","科室","床号","医嘱状态","状态时间","起始时间","结束时间","执行人","医生姓名","药品名称","计量","频次","饭前/饭后","执行次数","备注"};
		pageBean.setTableNames(Arrays.asList(tableNames));
		List<DoctorTel> doctorTels = doctorTelDao.moreCondition(pageBean.getBegin(),pageBean.getLimit(),doctorTelFind);
		List<Map<String,Object>> list = new ArrayList<>();
		for (DoctorTel doctorTel : doctorTels) {
			Patient patient = doctorTel.getPatient();
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("doctorTelId",new TableValueChangeNull("医嘱编号",doctorTel.getDoctorTelId(),false,false));
			map.put("hospitalId",new TableValueChangeNull("住院号",patient.getHospitalId(),false,false));
			map.put("depName",new TableValueChangeNull("科室",patient.getDep().getDepName(),false,false));
			map.put("bedNumber",new TableValueChangeNull("床号",patient.getBed().getBedNumber(),false,false));
			map.put("state",new TableValueChangeNull("医嘱状态",doctorTel.getState(),false,false));
			map.put("stateTime",new TableValueChangeNull("状态时间",format.format(doctorTel.getExcuteTime()),false,false));
			map.put("beginTime",new TableValueChangeNull("起始时间",format.format(doctorTel.getBeginTime()),false,true));
			map.put("endTime",new TableValueChangeNull("结束时间",format.format(doctorTel.getEndTime()),false,true));
			map.put("excuteNurseName",new TableValueChangeNull("执行人",doctorTel.getExcuteNurseName(),false,false));
			map.put("sendDoctor",new TableValueChangeNull("医生姓名",doctorTel.getDoctorName(),false,false));
			map.put("medicineName",new TableValueChangeNull("药品名称",doctorTel.getMedicine(),false,true));
			
			//map.put("sendTime", new TableValueChangeNull("医嘱下达时间",format.format(doctorTel.getSendTime()),false,false));
			map.put("quantity",new TableValueChangeNull("计量",doctorTel.getQuantity(),false,true));
			map.put("frequence", new TableValueChangeNull("频次",doctorTel.getFrequence(),false,true));
			String eatTypeSt = null;
			if(doctorTel.getEatType())  eatTypeSt="饭前";
			else eatTypeSt="饭后";
			map.put("eatType",new TableValueChangeNull("饭前/饭后",eatTypeSt,false,true));
			map.put("excuteTimes",new TableValueChangeNull("执行次数",doctorTel.getExcuteTimes(),false,false));
			map.put("pastil",new TableValueChangeNull("备注",doctorTel.getDetail(),true,true));
			map.put("patientId",new TableValueChangeNull("",doctorTel.getPatient().getPatientId(), false,false));
			list.add(map);
		}
		pageBean.setList(list);
		return new ResultBean<PageBean<Map<String,Object>>>(pageBean);
	}
	
	public ResultBean<List<DoctorTel>> findState0(){
		List<DoctorTel> doctorTels = doctorTelDao.findState0();
		return new ResultBean<List<DoctorTel>>(doctorTels);
	}
	
	public ResultBean<Boolean> excute(Integer doctorTelId){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		//校验
		CheckUtil.notNull(doctorTelId,"医嘱编号不能为空");
		DoctorTel doctorTel_find = doctorTelDao.findOne(doctorTelId);
		CheckUtil.notNull(doctorTel_find,"对应医嘱已经不存在请刷新页面");
		CheckUtil.check(doctorTel_find.getState().intValue()!=2,"对应的计划已经处于下达状态，本次操作无效");
		CheckUtil.check(doctorTel_find.getState().intValue()==1,"只有处于下达状态的医嘱才能被执行");
		CheckUtil.check(calendar.getTime().before(doctorTel_find.getBeginTime()),"当前时间必须在计划开始时间之前");
		//执行
		doctorTel_find.setState(2);
		doctorTel_find.setSendTime(new Date());
		doctorTel_find.setExcuteTimes(0);
		doctorTelDao.update(doctorTel_find);
		return new ResultBean<Boolean>(true);
	}
	private String getDoctorTelStateStr(int state){
		String a=null;
		if(state==0) {
			 a="未下达";
		} else if(state==1) {
			 a="已下达";
		} else if(state==2) {
			a="执行中";
		} else if(state==3) {
			 a="已执行";
		}
		else if(state==4) {
			a="自动关闭";
		}else {
			a="未知状态";
		}
		return a;
	}


	public ResultBean<Boolean> close(Integer doctorTelId) {
		DoctorTel doctorTel = doctorTelDao.findOne(doctorTelId);
		/**
		 * 校验
		 */
		CheckUtil.notNull(doctorTel,"此医嘱已经不存在，请刷新页面");
		CheckUtil.check(doctorTel.getState()==2,"医嘱不在执行中不能删除");;
		doctorTelDao.delete(doctorTel);
		PasDoctorTel pasDoctorTel = new PasDoctorTel();
		pasDoctorTel.setBeginTime(doctorTel.getBeginTime());
		pasDoctorTel.setDetail(doctorTel.getDetail());
		pasDoctorTel.setEatType(doctorTel.getEatType());
		pasDoctorTel.setEndTime(doctorTel.getEndTime());
		pasDoctorTel.setExcuteNurseId(doctorTel.getExcuteNurseId());
		pasDoctorTel.setExcuteNurseName(doctorTel.getExcuteNurseName());
		pasDoctorTel.setExcuteTime(new Date());
		pasDoctorTel.setExcuteTimes(doctorTel.getExcuteTimes());
		pasDoctorTel.setFrequence(doctorTel.getFrequence());
		pasDoctorTel.setMedicine(doctorTel.getMedicine());
		pasDoctorTel.setPatientName(doctorTel.getPatient().getPatientName());
		pasDoctorTel.setQuantity(doctorTel.getQuantity());
		pasDoctorTel.setSendDoctor(doctorTel.getDoctorName());
		pasDoctorTel.setSendTime(doctorTel.getSendTime());
		pasDoctorTel.setState(4);
		pasDoctorTelService.addPasDoctorTel(pasDoctorTel);
		return new ResultBean<Boolean>(true);
	}


	public ResultBean<List<DoctorTelDownLoad>> findAll() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<DoctorTelDownLoad> list = new ArrayList<>();
		List<DoctorTel> doctorTels = doctorTelDao.findAll();
		for (DoctorTel doctorTel : doctorTels) {
			Patient patient = doctorTel.getPatient();
			DoctorTelDownLoad doctorTelDownLoad = new DoctorTelDownLoad();
			doctorTelDownLoad.setDoctorTelId(doctorTel.getDoctorTelId());
			doctorTelDownLoad.setHospitalId(patient.getHospitalId());
			doctorTelDownLoad.setDepName(patient.getDep().getDepName());
			if(patient.getBed().getBedNumber()!=null)
			doctorTelDownLoad.setBedNumber(patient.getBed().getBedNumber());
			if(doctorTel.getState()!=null)
			doctorTelDownLoad.setState(doctorTel.getState());
			if(doctorTel.getSendTime()!=null) {
				doctorTelDownLoad.setStateTime(format.format(doctorTel.getSendTime()));
			}
			if(doctorTel.getBeginTime()!=null) 
			doctorTelDownLoad.setBeginTime(format.format(doctorTel.getBeginTime()));
			if(doctorTel.getBeginTime()!=null) 
			doctorTelDownLoad.setEndTime(format.format(doctorTel.getEndTime()));
			if(doctorTel.getExcuteNurseName()!=null&&!doctorTel.getExcuteNurseName().trim().isEmpty()){
				doctorTelDownLoad.setExcuteNurse(doctorTel.getExcuteNurseName());
			}else {
				doctorTelDownLoad.setExcuteNurse("无");
			}
			doctorTelDownLoad.setDoctorName(doctorTel.getDoctorName());
			doctorTelDownLoad.setMedicineName(doctorTel.getMedicine());
			doctorTelDownLoad.setQuantity(doctorTel.getQuantity());
			doctorTelDownLoad.setFrequnce(doctorTel.getFrequence());
			
			String eatType=null;
			if(doctorTel.getEatType()){
				eatType="饭前";
			}else {
				eatType="饭后";
			}
			doctorTelDownLoad.setEatType(eatType);
			if(doctorTel.getExcuteTimes()!=null)
			doctorTelDownLoad.setExcuteTimes(doctorTel.getExcuteTimes());
			if(doctorTel.getDetail()!=null)
			doctorTelDownLoad.setDetail(doctorTel.getDetail());
			list.add(doctorTelDownLoad);
		}
		System.out.println(list.toString());
		return new ResultBean<List<DoctorTelDownLoad>>(list);
	}
	
	public ResultBean<Boolean> upLoadDoctorTel(List<Map<String,Object>> list){
		for (Map<String, Object> map : list) {
			DoctorTel doctorTel = new DoctorTel();
			
			if(map.get("开始时间")!=null) {
				doctorTel.setBeginTime((Date) map.get("开始时间"));
			}
			if(map.get("备注")!=null) {
				doctorTel.setDetail((String) map.get("备注"));
			}	
			if(map.get("医生姓名")!=null) {
				doctorTel.setDoctorName((String) map.get("医生姓名"));
			}
			
			if(map.get("饭前饭后")!=null) {
				String str  = (String) map.get("饭前饭后");
				Boolean eatType=false;
				if(str.equals("饭前")) {
					eatType=true;
				}else if(str.equals("饭后")) {
					eatType=false;
				}else {
					CheckUtil.check(false,"请正确填写饭前/饭后");
				}
				doctorTel.setEatType(eatType);
			}
			if(map.get("结束时间")!=null) {
				doctorTel.setEndTime((Date) map.get("结束时间"));
			}
			if(map.get("医生姓名")!=null) {
				doctorTel.setDoctorName((String) map.get("医生姓名"));
			}
			if(map.get("频率")!=null) {
				doctorTel.setFrequence((String) map.get("频率"));
			}
			if(map.get("药品名称")!=null) {
				doctorTel.setMedicine((String) map.get("药品名称"));
			}
			if(map.get("剂量")!=null) {
				doctorTel.setQuantity((String) map.get("剂量"));
			}
			System.out.println(map.get("住院号").toString());
			if(map.get("住院号")!=null) {
				
				List<Patient> patients = patientDao.findByHospitalId((String) map.get("住院号"));
				if(patients==null||patients.size()!=1){
					CheckUtil.check(false,"病人不存在/或者住院号重复联系管理员");
				}
				doctorTel.setPatient(patients.get(0));
			}
			add(doctorTel);
		}
		return new ResultBean<>(true);
	}
}

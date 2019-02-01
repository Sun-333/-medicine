package cn.cqupt.service;

import java.lang.management.PlatformLoggingMXBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Check;
import org.springframework.transaction.annotation.Transactional;

import Modle.AddPlanModle;
import Modle.ModifyPlanModel;
import Modle.PatientBydownLoad;
import Modle.PlanByDownLoad;
import Modle.PlanTypeInfo;
import Modle.TableValueChangeNull;
import Util.CheckUtil;
import Util.Constant;
import Util.HttpUtils;
import Util.PageBean;
import Util.PlanUtil;
import Util.ResultBean;
import Util.UserUtil;
import cn.cqupt.Exception.CheckException;
import cn.cqupt.dao.BedDao;
import cn.cqupt.dao.ConfigDao;
import cn.cqupt.dao.ConstantDao;
import cn.cqupt.dao.DepDao;
import cn.cqupt.dao.PatientDao;
import cn.cqupt.dao.PlanDao;
import cn.cqupt.entity.Bed;
import cn.cqupt.entity.Configuration;
import cn.cqupt.entity.Dep;
import cn.cqupt.entity.Patient;
import cn.cqupt.entity.Plan;
import cn.cqupt.entity.PlanConstant;
import cn.cqupt.entity.UserToken;
@Transactional
public class PlanService {
	//配置依赖
	PlanDao planDao;
	public void setPlanDao(PlanDao planDao) {
		this.planDao = planDao;
	}
	PatientDao patientDao;
	public void setPatientDao(PatientDao patientDao) {
		this.patientDao = patientDao;
	}
	ConstantDao constantDao;
	public void setConstantDao(ConstantDao constantDao) {
		this.constantDao = constantDao;
	}
	ConfigDao configDao;
	public void setConfigDao(ConfigDao configDao) {
		this.configDao = configDao;
	}
	DepDao depDao;
	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}
	BedDao bedDao;
	public void setBedDao(BedDao bedDao) {
		this.bedDao = bedDao;
	}
	//多条件查询服药计划
	public ResultBean<PageBean<Map<String, Object>>> findByMoreCondition(Plan plan, Integer page, Integer limit) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//非空校验
		CheckUtil.notNull(page,"查询页数不能为空");
		CheckUtil.notNull(limit,"每页显示数目不能为空");
		//创建pageBean
		PageBean<Map<String, Object>> pageBean = new PageBean<>();
		//设置每页显示数量
		pageBean.setLimit(limit.intValue());
		//设置查询页数
		pageBean.setPage(page.intValue());
		//获取总记录数
		int cnt = planDao.findCntByMoreCondition(plan);
		pageBean.setTotalCount(cnt);
		//查询对应页数
		List<Plan>	list = planDao.findByMoreCondition(plan,pageBean.getBegin(),pageBean.getLimit());
		/**
		 * 封装查询结果
		 */
		//1.设置查询表头
		String[] tableNames = {"计划ID","床号","姓名","住院号","科室","状态","计划时间","计划人","执行时间","执行人","计划有效时间"};
		pageBean.setTableNames(Arrays.asList(tableNames));
		List<Map<String, Object>> result = new ArrayList<>();
		for (Plan plan_find : list) {
			Map<String, Object> plan_msg = new LinkedHashMap<>();
			plan_msg.put("planId",new TableValueChangeNull("计划ID",plan_find.getPlanId(),false,false));
			//plan_msg.put("BedId", new TableValueChangeNull("床号",plan_find.getBed().getBedId(),false, false));
			plan_msg.put("bedNumber", new TableValueChangeNull("床号",plan_find.getBed().getBedNumber(),false, false));
			plan_msg.put("patientName",new TableValueChangeNull("姓名",plan_find.getPatient().getPatientName(), false, false));
			plan_msg.put("hospitalId", new TableValueChangeNull("住院号",plan_find.getPatient().getHospitalId(),false,false));
			plan_msg.put("depName", new TableValueChangeNull("科室",plan_find.getDep().getDepName(),false,false));
			plan_msg.put("planState", new TableValueChangeNull("状态",IntState2Str(plan_find.getPlanState().intValue()),false,false));
			plan_msg.put("planTime", new TableValueChangeNull("计划时间",format.format(plan_find.getPlanTime()),false,false));
			plan_msg.put("releaseNurseName",new TableValueChangeNull("计划人",plan_find.getReleaseNurseName(),false,false));
			plan_msg.put("excuteTime", new TableValueChangeNull("执行时间",plan_find.getExcuteTime(), false,false));
			plan_msg.put("excuteNurseName",new TableValueChangeNull("执行人",plan_find.getExcuteNurseName(),false,true));
			Date planBeginTime = plan_find.getPlanBeginTime();
			Date planEndTime = plan_find.getPlanEndTime();
			String planValidTime =format.format(planBeginTime)+"/"+format.format(planEndTime);
			plan_msg.put("planValidTime",new TableValueChangeNull("计划有效时间",planValidTime,false,true));
			PlanTypeInfo info = PlanUtil.getPlanTypeInfo(plan_find.getPlanType());
			plan_msg.put("excuteNurseId",new TableValueChangeNull("",plan_find.getExcuteNurseId(),false,true));
			plan_msg.put("timeName",new TableValueChangeNull("",info.getTimeName(),false,true));
			plan_msg.put("eatType",new TableValueChangeNull("",info.getEatType(),false,true));
			plan_msg.put("depId", new TableValueChangeNull("",plan_find.getDep().getDepId(),false,true));
			result.add(plan_msg);
		}
		pageBean.setList(result);
		return new ResultBean<PageBean<Map<String,Object>>>(pageBean);
	}
	/**
	 * 添加计划
	 * @param addPlanModle
	 * @return
	 * @throws ParseException 
	 */
	public ResultBean<Boolean> addPlan(AddPlanModle addPlanModle) throws ParseException {
		StringBuffer buffer = new StringBuffer("method=sendPlanIds&planIds=");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/**
		 * 将addPlanModle转化成Plan集合
		 */
		Integer depId = addPlanModle.getDepId();//科室编号
		Date planBeginTime = format.parse(addPlanModle.getPlanBeginTime());//计划开始时间
		Date planEndTime = format.parse(addPlanModle.getPlanEndTime());//计划结束时间
		List<Integer> bedIds = addPlanModle.getBeds();//添加计划的床位
		Boolean beforeEat = addPlanModle.getBeforeEat();//是否饭前
		Boolean afterEat = addPlanModle.getAfterEat();//是否饭后
		int planInitTimes = this.getplanTime();//获取设置计划初始编号
		UserToken user = UserUtil.getUser();//获取计划下发人的信息
		String timeName = addPlanModle.getTimeName();//获取时间类型
		CheckUtil.notEmpty(timeName,"时间类型不能为空");
		Integer planType = PlanUtil.planType2Number(timeName);
		/**
		 * 非空校验
		 */
		CheckUtil.notNull(depId,"科室编号不能为空");
		CheckUtil.notNull(planBeginTime,"计划开始时间不能为空");
		CheckUtil.notNull(planEndTime,"计划结束时间不能为空");
		CheckUtil.notNull(timeName,"要选择名称");
		CheckUtil.notNull(planType,"请输入timeName为：早上、中午、晚上");
		/**
		 * 
		 * 判断计划有效时间的合理性
		 */
			//1.计划时间必须大于当前系统时间
			Calendar calendar_now = Calendar.getInstance();
			calendar_now.setTime(new Date());
			Calendar calendar_begin=Calendar.getInstance();
			calendar_begin.setTime(planBeginTime);
			Calendar calendar_end = Calendar.getInstance();
			calendar_end.setTime(planEndTime);
			//System.out.println(calendar_now.getTime().toString());
			//System.out.println(calendar_begin.getTime().toString());
			CheckUtil.check(calendar_now.before(calendar_begin),"当前新加入计划开始时间必须大于当前系统时间");
			CheckUtil.check(calendar_begin.before(calendar_end),"计划结束时间必须大于计划开始时间");
			//2.计划有效时间差必须大于配置阀值
			Configuration configuration = configDao.findOne(1);
			long sub = (planEndTime.getTime()-planBeginTime.getTime())/(60*1000);//差了多少分钟
			CheckUtil.check(configuration.getTimeoutPlan()<=sub,"计划开始时间与结束时间必须大于阀值"+configuration.getTimeoutPlan()+"分钟");
			//3.计划时间必须小于对应闹钟时间
			TimeCompare(configuration,planType,planBeginTime,planEndTime);//判断是否满足条件
		for (Integer bedId : bedIds) {
			//bedId对应查询病人Id
			Integer patientId = patientDao.findPatientByBedId(bedId);
			//校验对应bedId下是否存在病人，防止Bug
			CheckUtil.notNull(patientId,"床位号ID"+bedId.intValue()+"的病床不存在病人请刷新页面后重新添加");
			//获取病人Id,计划类型对应的计划数量
			/**
			 * 判断计划对应天数、类型是否超出
			 */
			int planBeforeEatCnt = planDao.planTypeCnt(patientId,PlanUtil.getPlanType(planType, true),planBeginTime);//先查询对应饭前计划个数
			int planAfterEatCnt = planDao.planTypeCnt(patientId, PlanUtil.getPlanType(planType, false),planBeginTime);
			CheckUtil.check(planBeforeEatCnt+planAfterEatCnt<=1,"病床Id:"+bedId+":对应病人的"+addPlanModle.getTimeName()+"服药计划已经有饭前饭后");
			//1.饭前计划生成
			if(beforeEat){
				CheckUtil.check(planBeforeEatCnt==0,"病床Id:"+bedId+":对应病人的"+addPlanModle.getTimeName()+"服药计划已经有饭前");
				Plan plan = new Plan();
				Dep dep = new Dep();
				Patient patient = new Patient();
				Bed bed = new Bed();
				dep.setDepId(depId);//设置科室
				bed.setBedId(bedId);//设置床号
				plan.setBed(bed);
				plan.setDep(dep);
				plan.setPlanType(PlanUtil.getPlanType(planType, true));//设置计划类型
				//设置病人Id
				patient.setPatientId(patientId);
				plan.setPatient(patient);
				/**
				 * 根据服药为饭前饭后生成计划
				 */
				plan.setExcuteNurseId(addPlanModle.getExcuteNurseId());//设置计划执行人编号
				plan.setExcuteNurseName(addPlanModle.getExcuteNurseName());//设置计划执行人姓名
				plan.setPlanBeginTime(planBeginTime);//设置计划开始时间
				plan.setPlanEndTime(planEndTime);//设置计划结束时间
				plan.setPlanState(0);//设置计划状态为未执行
				plan.setPlanTime(PlanUtil.getPlanTime(configuration, PlanUtil.getPlanType(planType, true), planBeginTime));//设置计划时间
				//从token中获取账号ID(下发计划人的账号编号);
				plan.setReleaseNurseId(user.getAccountId());
				//从token中获取用户名称（下发计划人的用户名称）;
				plan.setReleaseNurseName(user.getUserName());
				//获取计划ID
				plan.setPlanId(getPlanId(planInitTimes));
				//保存此条计划
				planDao.add(plan);
				//计划编号+1
				planInitTimes++;
				//向数据库中添加计划
				planDao.add(plan);
				planBeforeEatCnt++;
				buffer.append(getPlanId(planInitTimes)+",");
			}
			//2.饭后计划生成
			if(afterEat){
				CheckUtil.check(planAfterEatCnt==0,"病床Id:"+bedId+":对应病人的"+addPlanModle.getTimeName()+"服药计划已经有饭饭后");
				Plan plan = new Plan();
				Dep dep = new Dep();
				Patient patient = new Patient();
				Bed bed = new Bed();
				dep.setDepId(depId);//设置科室
				bed.setBedId(bedId);//设置床号
				plan.setBed(bed);
				plan.setDep(dep);
				plan.setPlanType(PlanUtil.getPlanType(planType,false));//设置计划类型
				//设置病人Id
				patient.setPatientId(patientId);
				plan.setPatient(patient);
				/**
				 * 根据服药为饭前饭后生成计划
				 */
				plan.setExcuteNurseId(addPlanModle.getExcuteNurseId());//设置计划执行人编号
				plan.setExcuteNurseName(addPlanModle.getExcuteNurseName());//设置计划执行人姓名
				plan.setPlanBeginTime(planBeginTime);//设置计划开始时间
				plan.setPlanEndTime(planEndTime);//设置计划结束时间
				plan.setPlanState(0);//设置计划状态为未执行
				plan.setPlanTime(PlanUtil.getPlanTime(configuration, PlanUtil.getPlanType(planType, false), planBeginTime));//设置计划时间
				//从token中获取账号ID(下发计划人的账号编号);
				plan.setReleaseNurseId(user.getAccountId());
				//从token中获取用户名称（下发计划人的用户名称）;
				plan.setReleaseNurseName(user.getUserName());
				//获取计划ID
				plan.setPlanId(getPlanId(planInitTimes));
				//保存此条计划
				planDao.add(plan);
				//计划编号+1
				planInitTimes++;
				planAfterEatCnt++;
				buffer.append(getPlanId(planInitTimes)+",");
			}
		}
		//更新constant中的日期、计划次数
		PlanConstant planConstant = constantDao.findOne(1);
		planConstant.setPlanTime(new Date());
		planConstant.setTimes(planInitTimes-1);
		constantDao.update(planConstant);
		HttpUtils.sentPost("http://localhost:8080/communicate/sendPlans",buffer.toString());
		return new ResultBean<Boolean>(true);
	}
	/**
	 * 判断计划截止时间是否必须小于对应类型的闹钟时间
	 * @param configuration
	 * @param planType
	 * @param planEndTime
	 * @param planEndTime2 
	 * @return
	 */
	private void  TimeCompare(Configuration configuration, Integer planType, Date planBeforeTime, Date planEndTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeBefPart =format.format(planBeforeTime);
		String timeComparam = format.format(planEndTime);
		CheckUtil.check(timeBefPart.equals(timeComparam),"计划开始时间与计划结束时间必须在一天");
		Date mornAlarm;
		Date noonAlarm;
		Date nightAlarm;
		try{
			 mornAlarm = format2.parse(timeBefPart+" "+configuration.getMorningTime());
			 noonAlarm = format2.parse(timeBefPart+" "+configuration.getNoonTime());
			 nightAlarm = format2.parse(timeBefPart+" "+configuration.getNightTime());
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		if(planType==1){
			CheckUtil.check(mornAlarm.after(planEndTime),"计划截止时间必须在早上服药之前");
		}
		else if(planType==2){
			CheckUtil.check(noonAlarm.after(planEndTime),"计划截止时间必须在中午服药之前");
			CheckUtil.check(mornAlarm.before(planBeforeTime),"您选择的是中午服药，所以计划开始时间必须在早上闹钟之后");
		}
		else if(planType==3){
			CheckUtil.check(nightAlarm.after(planEndTime),"计划截止时间必须在晚上服药之前");
			CheckUtil.check(noonAlarm.before(planBeforeTime),"您选择的是晚上服药，所以计划开始时间必需在中午闹钟之后");
		}else {
			CheckUtil.check(false,"计划类型必须为：早上/中午/晚上");
		}
	}
	/**
	 * 获取目前应该设置账号Times+1
	 * @return
	 */
	private int getplanTime() {
		/*
		 * 1.首先获取系统日期
		 * 2.判定系统时间与数据库中的日期是否相同
		 * 	2.1如果相同从数据库中获取次数+1返回
		 * 	2.2如果不相同将日期更新为当前系统日期，并返回times=1
		 */
		//获取系统时间 
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String nowTime = format.format(new Date());
		//查询数据库获取计划时间
		PlanConstant planConstant =constantDao.findOne(1);
		System.out.println(planConstant.toString());
		String saveTime = format.format(planConstant.getPlanTime());
		//判断是否相同
		if(nowTime.equals(saveTime)){
			//相同返回数据库中次数+1
			return planConstant.getTimes().intValue()+1;
		}else{
			return 1;
		}
	}
	/***
	 * 获取当前计划的Id
	 * @return
	 */
	private String getPlanId(int planNumber) {
		//获取系统时间 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = format.format(new Date());
		//将当前系统日期与计划编号拼接成计划ID
		return nowTime+"-"+(formatTimes(planNumber));
	}
	
	private String formatTimes(int time){
		if(time<10){
			return "000"+time;
		}else if(time<100){
			return "00"+time;
		}else {
			return "0"+time;
		}
	}
	/**
	 * 修改计划
	 * @param plan
	 * @return
	 */
	public ResultBean<Boolean> modifyPlan(ModifyPlanModel plan) {
		try {
			SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			CheckUtil.notEmpty(plan.getPlanBeginTime(),"计划开始时间不能为空");
			CheckUtil.notEmpty(plan.getPlanEndTime(),"计划截止时间不能为空");
			CheckUtil.notEmpty(plan.getTimeName(),"计划类型不能为空");
			Date planBeginTime =format.parse( plan.getPlanBeginTime());
			Date planEndTime = format.parse(plan.getPlanEndTime());
			Integer planType = PlanUtil.planType2Number(plan.getTimeName());
			CheckUtil.notNull(planType,"计划类型必须为早上、中午、晚上");
			//首先查询此的状态计划
			Plan plan_find = planDao.findByPlanId(plan.getPlanId());
			//判断此计划是否还存在避免bug
			CheckUtil.notNull(plan_find,"此计划已经不存在不能修改");
			//判断计划状态是否处于未执行0
			int planState=plan_find.getPlanState().intValue();
			CheckUtil.check(planState==0,"计划处于执行状态后,不能修改");
			//校验
				//1.计划时间必须大于当前系统时间
				Calendar calendar = Calendar.getInstance();
				CheckUtil.check(calendar.getTime().before(planBeginTime),"当前新加入计划开始时间必须大于当前系统时间");
				//2.计划有效时间差必须大于配置阀值
				Configuration configuration = configDao.findOne(1);
				long sub = (planEndTime.getTime()-planBeginTime.getTime())/(60*1000);//差了多少分钟
				CheckUtil.check(configuration.getTimeoutPlan()<=sub,"计划开始时间与结束时间必须大于阀值"+configuration.getTimeoutPlan());
				//2.计划时间必须小于对应闹钟时间
				TimeCompare(configuration,planType,planBeginTime,planEndTime);//判断是否满足条件
				//4校验对应的计划类型是否存在
				List<Plan> plans= planDao.findPlanByPatientIdAndType(plan_find.getPatient().getPatientId(),PlanUtil.getPlanType(planType, plan.getEatType()),planBeginTime);
				for (Plan plan2 : plans) {
					System.out.println(plan2.getPlanId());
					CheckUtil.check(plan2.getPlanId().equals(plan.getPlanId()),"该类型服药计划已经存在");
				}
			if(!plan_find.getPlanTime().equals(PlanUtil.getPlanTime(configuration, PlanUtil.getPlanType(planType, plan.getEatType()), planBeginTime))){
				HttpUtils.sentPost("http://localhost:8080/communicate/sendPlans","method=modifyPlanTime&planIds="+plan_find.getPlanId());
			}
			//更新计划状态
			plan_find.setExcuteNurseId(plan.getExcuteNurseId());
			plan_find.setExcuteNurseName(plan.getExcuteNurseName());
			plan_find.setPlanBeginTime(format.parse(plan.getPlanBeginTime()));
			plan_find.setPlanEndTime(format.parse(plan.getPlanEndTime()));
			plan_find.setPlanTime(PlanUtil.getPlanTime(configuration, PlanUtil.getPlanType(planType, plan.getEatType()), planBeginTime));//设置计划时间);
			plan_find.setPlanType(PlanUtil.getPlanType(planType, plan.getEatType()));
			//保存计划
			planDao.update(plan_find);
			return new ResultBean<Boolean>(true);
		}catch(ParseException e){
			e.printStackTrace();
			throw new CheckException("日期格式错误");
		}
	}
	public ResultBean<Boolean> delectPlan(List<String> planIds) {
		StringBuffer buffer = new StringBuffer("method=delectPlans&planIds=");
		for (String planId : planIds) {
			Plan plan_find = planDao.findByPlanId(planId);
			//判断此计划是否还存在
			CheckUtil.notNull(plan_find,"此计划已经不存在");
			//判断计划是否处于能删除状态
			int planState = plan_find.getPlanState().intValue();
			CheckUtil.check(planState==0,"计划ID:"+plan_find.getPlanId()+"已经执行不能删除");
			planDao.delete(plan_find);
			buffer.append(planId+",");
		}
		HttpUtils.sentPost("http://localhost:8080/communicate/sendPlans",buffer.toString());
		return new ResultBean<Boolean>(true);
	}
	public ResultBean<List<PlanByDownLoad>> findBydownLoad() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Plan> list = planDao.findAll();
		List<PlanByDownLoad> planByDownLoads = new ArrayList<>();
		for (Plan plan : list) {
			Patient patient = plan.getPatient();
			PlanByDownLoad planByDownLoad = new PlanByDownLoad();
			planByDownLoad.setBedNumber(plan.getBed().getBedNumber());
			planByDownLoad.setDepName(plan.getDep().getDepName());
			if(plan.getExcuteTime()!=null) {
				planByDownLoad.setExcuteTime(format.format(plan.getExcuteTime()));
			}else {
				planByDownLoad.setExcuteTime(null);
			}
			planByDownLoad.setExcyteNurserName(plan.getExcuteNurseName());
			planByDownLoad.setHospitalId(patient.getHospitalId());
			planByDownLoad.setPatientName(patient.getPatientName());
			planByDownLoad.setPlanId(plan.getPlanId());
			planByDownLoad.setPlanTime(format.format(plan.getPlanTime()));
			Date planBeginTime = plan.getPlanBeginTime();
			Date planEndTime = plan.getPlanEndTime();
			String planValidTime =format.format(planBeginTime)+"/"+format.format(planEndTime);
			planByDownLoad.setPlanValidTime(planValidTime);
			planByDownLoad.setReleaseNurseName(plan.getReleaseNurseName());
			planByDownLoad.setState(plan.getPlanState());
			planByDownLoads.add(planByDownLoad);
		}
		return new ResultBean<List<PlanByDownLoad>>(planByDownLoads);
	}
	public ResultBean<Map<String,Object>> findByPlanId(String planId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CheckUtil.notEmpty(planId,"计划ID不能为空");
		Plan plan_find = planDao.findByPlanId(planId);
		CheckUtil.notNull(plan_find,"计划ID对应计划不存在");
		Map<String, Object> plan_msg = new LinkedHashMap<>();
		plan_msg.put("planId",new TableValueChangeNull("计划ID",plan_find.getPlanId(),false,false));
		//plan_msg.put("BedId", new TableValueChangeNull("床号",plan_find.getBed().getBedId(),false, false));
		plan_msg.put("bedNumber", new TableValueChangeNull("床号",plan_find.getBed().getBedNumber(),false, false));
		plan_msg.put("patientName",new TableValueChangeNull("姓名",plan_find.getPatient().getPatientName(), false, false));
		plan_msg.put("hospitalId", new TableValueChangeNull("住院号",plan_find.getPatient().getHospitalId(),false,false));
		plan_msg.put("depName", new TableValueChangeNull("科室",plan_find.getDep().getDepName(),false,false));
		plan_msg.put("planState", new TableValueChangeNull("状态",plan_find.getPlanState(),false,false));
		plan_msg.put("planTime", new TableValueChangeNull("计划时间",format.format(plan_find.getPlanTime()),false,false));
		plan_msg.put("releaseNurseName",new TableValueChangeNull("计划人",plan_find.getReleaseNurseName(),false,false));
		plan_msg.put("excuteTime", new TableValueChangeNull("执行时间",plan_find.getExcuteTime(), false,false));
		plan_msg.put("excuteNurseName",new TableValueChangeNull("执行人",plan_find.getExcuteNurseName(),false,true));
		Date planBeginTime = plan_find.getPlanBeginTime();
		Date planEndTime = plan_find.getPlanEndTime();
		String planValidTime =format.format(planBeginTime)+"/"+format.format(planEndTime);
		plan_msg.put("planValidTime",new TableValueChangeNull("计划有效时间",planValidTime,false,true));
		PlanTypeInfo info = PlanUtil.getPlanTypeInfo(plan_find.getPlanType());
		plan_msg.put("excuteNurseId",new TableValueChangeNull("",plan_find.getExcuteNurseId(),false,true));
		plan_msg.put("timeName",new TableValueChangeNull("",info.getTimeName(),false,true));
		plan_msg.put("eatType",new TableValueChangeNull("",info.getEatType(),false,true));
		return new ResultBean<Map<String,Object>>(plan_msg);
	}
	public ResultBean<Boolean> upLoad(List<Map<String, Object>> maps) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Map<String, Object> map : maps) {
			AddPlanModle addPlanModle = new AddPlanModle();
			addPlanModle.setAfterEat(PlanUtil.changeStr2Bool((String) map.get("饭前")));
			addPlanModle.setBeforeEat(PlanUtil.changeStr2Bool((String) map.get("饭后")));
			Dep dep = depDao.finByDepName((String)map.get("科室"));
			CheckUtil.notNull(dep,"科室不存在");
			addPlanModle.setDepId(dep.getDepId());
			addPlanModle.setPlanBeginTime(simpleDateFormat.format((Date) map.get("计划开始时间")));
			System.out.println("计划开始时间"+addPlanModle.getPlanBeginTime());
			addPlanModle.setPlanEndTime(simpleDateFormat.format((Date) map.get("计划结束时间")));
			System.out.println("计划结束时间"+addPlanModle.getPlanEndTime());
			addPlanModle.setTimeName((String) map.get("早上/中午/晚上"));
			String beds= (String) map.get("床号");
			String[] bedNumbers = beds.replaceAll("\\[","").replaceAll("\\]","").split(",");
			List<Integer> bedIds = new ArrayList<>();
			List<Bed> beds_find = bedDao.findBedByDepId(dep.getDepId());
			for(int i=0;i<bedNumbers.length;i++) {
				boolean control=false;
				for (Bed bed : beds_find) {
					if(bed.getBedNumber().equals(Integer.valueOf(bedNumbers[i]))){
						bedIds.add(bed.getBedId());
						control=true;
						break;
					}
				}
				CheckUtil.check(control,"导入校验失败");
			}
			addPlanModle.setBeds(bedIds);
			addPlan(addPlanModle);
		}
		return new ResultBean<Boolean>(true);
	}
	private String IntState2Str(int a) {
		if(a==0) return "未计划";
		else if(a==1) return "计划中";
		else if(a==2) return "正常";
		else if(a==3) return "完成";
		else if(a==4) return "服药超时";
		else if(a==5) return "计划超时";
		else return "未知状态";
	}
}

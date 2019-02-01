package cn.cqupt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import Modle.PatientBydownLoad;
import Modle.TableValue;
import Modle.findPatientCondition;
import Util.CheckUtil;
import Util.PageBean;
import Util.ParamTool;
import Util.PermissionUtil;
import Util.PlanUtil;
import Util.ResultBean;
import Util.UserUtil;
import cn.cqupt.Exception.NoPermissionException;
import cn.cqupt.dao.BedChangeDao;
import cn.cqupt.dao.BedDao;
import cn.cqupt.dao.DepDao;
import cn.cqupt.dao.PasPatientDao;
import cn.cqupt.dao.PatientDao;
import cn.cqupt.dao.PlanDao;
import cn.cqupt.entity.Bed;
import cn.cqupt.entity.BedChange;
import cn.cqupt.entity.Dep;
import cn.cqupt.entity.PasPatient;
import cn.cqupt.entity.Patient;
import cn.cqupt.entity.Plan;
import cn.cqupt.entity.UserToken;

@Transactional
public class PatientService {
	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
	// 配置dao层依赖
	PatientDao patientDao;

	public void setPatientDao(PatientDao patientDao) {
		this.patientDao = patientDao;
	}

	BedDao bedDao;

	public void setBedDao(BedDao bedDao) {
		this.bedDao = bedDao;
	}

	DepDao depDao;

	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}

	PlanDao planDao;

	public void setPlanDao(PlanDao planDao) {
		this.planDao = planDao;
	}

	PasPatientDao pasPatientDao;

	public void setPasPatientDao(PasPatientDao pasPatientDao) {
		this.pasPatientDao = pasPatientDao;
	}

	BedChangeDao bedChangeDao;

	public void setBedChangeDao(BedChangeDao bedChangeDao) {
		this.bedChangeDao = bedChangeDao;
	}

	/***
	 * 多条件查询病人
	 * 
	 * @param condicine
	 * @return
	 */
	public ResultBean<PageBean<Map<String, Object>>> findByMoreCondition(findPatientCondition condicine) {
		// 校验排序方式的合法性
		CheckUtil.check(condicine.getSortType().equals("床号") || condicine.getSortType().equals("入院时间"),
				"排序方式为：床号、入院时间");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 初始化返回结果集
		PageBean<Map<String, Object>> pageBean = new PageBean<>();
		// 得到总页数
		int Cnt = patientDao.findCnt(condicine);
		// 设置当前显示页数
		pageBean.setPage(condicine.getCurrentPage());
		// 设置总页数
		pageBean.setTotalCount(Cnt);
		// 设置每页显示条数
		pageBean.setLimit(condicine.getLimit());
		// 直接调用下层方法执行查询
		List<Patient> patients = patientDao.findMoreCondition(condicine, pageBean.getBegin(), pageBean.getLimit());
		// 封装返回结果集
		// 1.设置表头
		String[] table_names = { "床位", "姓名", "诊断", "科室", "护理级别", "年龄", "性别", "住院号", "联系电话", "入院时间" };
		List<String> tableNames = Arrays.asList(table_names);
		pageBean.setTableNames(tableNames);
		List<Map<String, Object>> list = new LinkedList<>();
		for (Patient patient : patients) {
			Map<String, Object> map = new LinkedHashMap<>();
			TableValue bedNumber = ParamTool.getParam("床位", patient.getBed().getBedNumber());
			map.put("bedNumber", bedNumber);
			TableValue patientName = ParamTool.getParam("姓名", patient.getPatientName());
			map.put("patientName", patientName);
			TableValue diagnose = ParamTool.getParam("诊断", patient.getDiagnose());
			map.put("diagnose", diagnose);
			TableValue depName = ParamTool.getParam("科室", patient.getDep().getDepName());
			map.put("depName", depName);
			TableValue level = ParamTool.getParam("护理级别", patient.getLevel());
			map.put("level", level);
			map.put("patientAge", ParamTool.getParam("年龄", patient.getPatientAge()));
			map.put("patientSex", ParamTool.getParam("性别", patient.getPatientSex()));
			String admissionTime = format.format(patient.getAdmissionTime());
			map.put("hospitalId", ParamTool.getParam("住院号", patient.getHospitalId()));
			map.put("patientTel", ParamTool.getParam("联系电话", patient.getPatientTel()));
			map.put("admissionTime", ParamTool.getParam("入院时间", admissionTime));
			map.put("patientId", ParamTool.getParam("病人ID", patient.getPatientId()));
			map.put("depId", ParamTool.getParam("", patient.getDep().getDepId()));
			list.add(map);
		}
		pageBean.setList(list);
		return new ResultBean<PageBean<Map<String, Object>>>(pageBean);

	}

	/***
	 * 多条件查询病人
	 * 
	 * @param condicine
	 * @return
	 *//*
	public ResultBean<PageBean<Map<String, Object>>> findByMoreCondition(findPatientCondition condicine) {
		// 校验排序方式的合法性
		CheckUtil.check(condicine.getSortType().equals("床号") || condicine.getSortType().equals("入院时间"),
				"排序方式为：床号、入院时间");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 初始化返回结果集
		PageBean<Map<String, Object>> pageBean = new PageBean<>();
		// 得到总页数
		int Cnt = patientDao.findCnt(condicine);
		// 设置当前显示页数
		pageBean.setPage(condicine.getCurrentPage());
		// 设置总页数
		pageBean.setTotalCount(Cnt);
		// 设置每页显示条数
		pageBean.setLimit(condicine.getLimit());
		// 直接调用下层方法执行查询
		List<Patient> patients = patientDao.findMoreCondition(condicine, pageBean.getBegin(), pageBean.getLimit());
		// 封装返回结果集
		// 1.设置表头
		String[] table_names = { "床位", "姓名", "诊断", "科室", "护理级别", "年龄", "性别", "住院号", "联系电话", "入院时间" };
		List<String> tableNames = Arrays.asList(table_names);
		pageBean.setTableNames(tableNames);
		List<Map<String, Object>> list = new LinkedList<>();
		for (Patient patient : patients) {
			Map<String, Object> map = new LinkedHashMap<>();
			TableValue bedNumber = ParamTool.getParam("床位", patient.getBed().getBedNumber());
			map.put("bedNumber", bedNumber);
			TableValue patientName = ParamTool.getParam("姓名", patient.getPatientName());
			map.put("patientName", patientName);
			TableValue diagnose = ParamTool.getParam("诊断", patient.getDiagnose());
			map.put("diagnose", diagnose);
			TableValue depName = ParamTool.getParam("科室", patient.getDep().getDepName());
			map.put("depName", depName);
			TableValue level = ParamTool.getParam("护理级别", patient.getLevel());
			map.put("level", level);
			map.put("patientAge", ParamTool.getParam("年龄", patient.getPatientAge()));
			map.put("patientSex", ParamTool.getParam("性别", patient.getPatientSex()));
			String admissionTime = format.format(patient.getAdmissionTime());
			map.put("hospitalId", ParamTool.getParam("住院号", patient.getHospitalId()));
			map.put("patientTel", ParamTool.getParam("联系电话", patient.getPatientTel()));
			map.put("admissionTime", ParamTool.getParam("入院时间", admissionTime));
			map.put("patientId", ParamTool.getParam("病人ID", patient.getPatientId()));
			map.put("depId", ParamTool.getParam("", patient.getDep().getDepId()));
			list.add(map);
		}
		pageBean.setList(list);
		return new ResultBean<PageBean<Map<String, Object>>>(pageBean);
	
	}*/

	public ResultBean<Map<String, Object>> findByPatientId(Integer patientId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 非空校验
		CheckUtil.check(patientId != null, "病人编号不能为空");
		// 执行查询
		Patient patient = patientDao.findOne(patientId.intValue());
		// 查询结果的非空校验
		CheckUtil.notNull(patient, "此病人不存在");
		// 封装返回结果集
		Map<String, Object> map = new LinkedHashMap<>();
		// 病人基础信息
		map.put("patientId", ParamTool.getParam("", patient.getPatientId(), false, false));
		map.put("patientName", ParamTool.getParam("", patient.getPatientName(), false, false));
		map.put("patientAge", ParamTool.getParam("", patient.getPatientAge(), false, true));
		map.put("patientSex", ParamTool.getParam("", patient.getPatientSex(), false, true));
		map.put("admissionTime", ParamTool.getParam("", format.format(patient.getAdmissionTime()), false, false));
		map.put("idCardNumber", ParamTool.getParam("", patient.getIdCardNumber(), false, false));
		map.put("patientAddress", ParamTool.getParam("", patient.getPatientAddress(), true, true));
		map.put("patientTel", ParamTool.getParam("", patient.getPatientTel(), true, true));
		// 病人特征信息
		map.put("attendingDoctor", ParamTool.getParam("", patient.getAttendingDoctor(), false, false));
		map.put("hospitalId", ParamTool.getParam("", patient.getHospitalId(), false, false));
		map.put("bedId", ParamTool.getParam("", patient.getBed().getBedId(), false, false));
		map.put("bedNumber", ParamTool.getParam("", patient.getBed().getBedNumber(), false, false));
		map.put("depId", ParamTool.getParam("", patient.getDep().getDepId(), false, false));
		map.put("depName", ParamTool.getParam("", patient.getDep().getDepName(), false, false));
		map.put("diagnose", ParamTool.getParam("", patient.getDiagnose(), false, false));
		map.put("level", ParamTool.getParam("", patient.getLevel(), false, false));
		map.put("diet", ParamTool.getParam("", patient.getDiet(), true, true));
		map.put("allergichHistory", ParamTool.getParam("", patient.getAllergichHistory(), true, true));
		map.put("allergic", ParamTool.getParam("", patient.getAllergic(), false, true));
		map.put("hyperthermia", ParamTool.getParam("", patient.getHyperthermia(), false, true));
		map.put("operation", ParamTool.getParam("", patient.getOperation(), false, true));
		// 病人医保信息
		map.put("healthCareNumber", ParamTool.getParam("", patient.getHealthCareNumber(), true, true));
		map.put("healthCareType", ParamTool.getParam("", patient.getHealthCareType(), true, true));
		map.put("totalMoney", ParamTool.getParam("", patient.getTotalMoney(), false, true));
		map.put("personPayMoney", ParamTool.getParam("", patient.getPersonPayMoney(), true, true));
		map.put("payMoney", ParamTool.getParam("", patient.getPayMoney(), false, true));
		// 服药计划信息
		map.put("totalTimes", ParamTool.getParam("", patient.getTotalTimes(), false, false));
		map.put("timeoutDisTimes", ParamTool.getParam("", patient.getTimeoutDisTimes(), false, false));
		map.put("timeoutEatTimes", ParamTool.getParam("", patient.getTimeoutEatTimes(), false, false));

		// 返回结果
		return new ResultBean<Map<String, Object>>(map);
	}

	public ResultBean<Boolean> modify(Patient patient) {
		/**
		 * 参数校验
		 */
		CheckUtil.notNull(patient.getPatientId(), "病人ID不能为空");
		CheckUtil.notEmpty(patient.getPatientSex(), "病人性别不能为空");
		CheckUtil.notNull(patient.getLevel(), "护理级别不能为空");

		/**
		 * 关于费用问题有待考虑
		 */
		// 查询病人信息
		Patient patient_find = patientDao.findOne(patient.getPatientId());
		// 跟新数据
		patient_find.setPatientAge(patient.getPatientAge());// 设置病人年龄
		patient_find.setPatientSex(patient.getPatientSex());// 病人性别
		patient_find.setPatientTel(patient.getPatientTel());// 病人电话
		patient_find.setPatientAddress(patient.getPatientAddress());// 病人地址
		patient_find.setLevel(patient.getLevel());// 护理级别
		patient_find.setDiet(patient.getDiet());// 设置饮食
		patient_find.setAllergichHistory(patient.getAllergichHistory());// 设置过敏史
		patient_find.setAllergic(patient.getAllergic());// 设置是否为过敏病人
		patient_find.setHyperthermia(patient.getHyperthermia());// 设置是否为高温
		patient_find.setOperation(patient.getOperation());// 设置是否为手术病人
		patient_find.setHealthCareType(patient.getHealthCareType());// 设置社保类型
		patient_find.setHealthCareNumber(patient.getHealthCareNumber());// 设置社保卡号
		patient_find.setTotalMoney(patient.getTotalMoney());// 设置总费用
		patient_find.setPayMoney(patient.getPayMoney());// 设置付款费用
		patient_find.setPersonPayMoney(patient.getPersonPayMoney());// 设置个人支付费用
		// 保存更新信息
		patientDao.update(patient_find);
		return new ResultBean<Boolean>(true);
	}

	public ResultBean<Boolean> savePatient(Patient patient) {
		/**
		 * 校验
		 */
		// 非空校验
		CheckUtil.notEmpty(patient.getPatientName(), "病人姓名不能为空");
		CheckUtil.notEmpty(patient.getPatientAge(), "病人年龄不能为空");
		CheckUtil.notEmpty(patient.getPatientSex(), "病人性别不能为空");
		CheckUtil.notEmpty(patient.getIdCardNumber(), "身份证账号不能为空");
		CheckUtil.notEmpty(patient.getAttendingDoctor(), "主治医生不能为空");
		CheckUtil.notNull(patient.getDep().getDepId(), "临床科室不能为空");
		CheckUtil.notEmpty(patient.getHospitalId(), "住院号不能为空");
		CheckUtil.notNull(patient.getDep().getDepId(), "临床科室不能为空");
		CheckUtil.notNull(patient.getDiagnose(), "诊断类别不能为空");
		CheckUtil.notNull(patient.getBed().getBedId(), "床位不能为空");
		CheckUtil.notNull(patient.getLevel(), "护理级别不能为空");
		CheckUtil.notNull(patient.getTotalMoney(), "总费用输入不能为空");
		CheckUtil.notNull(patient.getPayMoney(), "总支付费用不能为空");
		CheckUtil.notNull(patient.getAllergic(), "是否过敏前端必须返回");
		CheckUtil.notNull(patient.getHyperthermia(), "是否高温病人前端必须返回");
		CheckUtil.notNull(patient.getOperation(), "是否为手术病人前端必须返回");

		// 对新加入病人未新病人
		patient.setNewPatient(true);
		// 添加入院时间
		patient.setAdmissionTime(new Date());
		// 费用合理性分析
		int totalMoney = patient.getTotalMoney();
		int payMoney = patient.getPayMoney();
		CheckUtil.check((totalMoney - payMoney) >= 0, "费用输入不合理");
		// 判断所选取的病床是否被使用
		Boolean ifUsed = bedDao.ifUsed(patient.getBed().getBedId());
		CheckUtil.check(!ifUsed, "此病床已被使用请重新选择");
		logger.info("add patient begin,patient:{}", patient.toString());
		// 保存新病人
		patient.setPlanState(0);
		patientDao.add(patient);
		// 设置病床为使用
		Bed bed = bedDao.findOne(patient.getBed().getBedId());
		bed.setUseState(true);
		bedDao.update(bed);
		return new ResultBean<Boolean>(true);
	}

	public ResultBean<Boolean> delectPatient(Integer patientId) {
		// 校验
		CheckUtil.check(patientId != null, "病人ID不能为空");
		/**
		 * 能否删除判断
		 */
		Patient patient = patientDao.findOne(patientId.intValue());
		// 病人是否还存在
		PermissionUtil.notNull(patient, "病人已经出院");
		// 1.病人是否欠费
		Integer totalMoney = patient.getTotalMoney();
		Integer payMoney = patient.getPayMoney();
		System.out.println(totalMoney + "_" + payMoney);
		int money = totalMoney.intValue() - payMoney.intValue();
		CheckUtil.check(money == 0, "病人还欠费不能出院");
		// 2.病人是否还存在服药计划
		int Cnt = patient.getSetPlan().size();
		CheckUtil.check(Cnt == 0, "此病人还存在服药计划不能出院");
		// 调用下层方法执行删除
		patientDao.delete(patient);
		// 将病人对应床位设为未使用
		Bed bed = patient.getBed();
		bed.setUseState(false);
		bedDao.update(bed);
		// 将病人加入；历史病人中
		PasPatient pasPatient = new PasPatient();
		pasPatient.setAdmissionTime(patient.getAdmissionTime());
		pasPatient.setAllergic(patient.getAllergic());
		pasPatient.setAllergichHistory(patient.getAllergichHistory());
		pasPatient.setAttendingDoctor(patient.getAttendingDoctor());
		pasPatient.setBedId(patient.getBed().getBedId());
		pasPatient.setBedNumber(patient.getBed().getBedNumber());
		pasPatient.setDepId(patient.getDep().getDepId());
		pasPatient.setDepName(patient.getDep().getDepName());
		pasPatient.setDiagnose(patient.getDiagnose());
		pasPatient.setDiet(patient.getDiet());
		pasPatient.setHealthCareNumber(patient.getHealthCareNumber());
		pasPatient.setHealthCareType(patient.getHealthCareType());
		pasPatient.setHospitalId(patient.getHospitalId());
		pasPatient.setHyperthermia(patient.getHyperthermia());
		pasPatient.setIdCardNumber(patient.getIdCardNumber());
		pasPatient.setLevel(patient.getLevel());
		pasPatient.setOperation(patient.getOperation());
		pasPatient.setPasPatientId(patient.getPatientId());
		pasPatient.setPatientAddress(patient.getPatientAddress());
		pasPatient.setPatientAge(patient.getPatientAge());
		pasPatient.setPatientName(patient.getPatientName());
		pasPatient.setPatientSex(patient.getPatientSex());
		pasPatient.setPatientTel(patient.getPatientTel());
		pasPatient.setPayMoney(patient.getPayMoney());
		pasPatient.setPersonPayMoney(patient.getPersonPayMoney());
		pasPatient.setTimeoutDisTimes(patient.getTimeoutDisTimes());
		pasPatient.setTimeoutEatTimes(patient.getTimeoutEatTimes());
		pasPatient.setTotalTimes(patient.getTotalTimes());
		pasPatientDao.add(pasPatient);
		logger.info("删除病人成功,patientId:{}", patientId);
		return new ResultBean<Boolean>(true);
	}

	public ResultBean<Map<String, Object>> getHomePageInf(Patient patientCondition) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 调用下层方法执行查询,得到病床信息,科室信息,病人信息
		List<Bed> beds = bedDao.moreCondition(patientCondition);
		CheckUtil.check(beds.size() != 0, "该科室下不存在床位,请选择其他科室");
		int alarmTotal = 0;
		int bedTotal = 0;// 创建床位统计参数
		int depId = beds.get(0).getDep().getDepId();// 获取科室编号
		String depName = beds.get(0).getDep().getDepName();// 获取科室名称

		int eatOutNumber = 0;// 创建服药超时统计参数
		int planNumber = 0;// 创建服药计划数量统计参数
		int eatInNumber = 0;// 按时服药人数
		int noUseBedNumber = 0;// 未使用床位数量

		int totalPatient = 0;// 病人总量
		int hyperthermiaNumer = 0;// 高温病人数量
		int operationNumber = 0;// 手术病人数量
		int newNumber = 0;// 新病人数量
		int allergicNumber = 0;// 过敏病人数量

		int superLevelNumber = 0;// 特级护理人数
		int firstLevelNumber = 0;// 以及护理人数
		int secondLevelNumber = 0;// 二级护理人数
		int thirdLevelNumber = 0;// 三级护理人数

		/**
		 * 封装返回结果集
		 */
		Map<String, Object> dep_msg = new HashMap<>();
		Map<String, Object> data = new HashMap<>();
		List<Object> beds_msg = new ArrayList<>();
		// 封装beds_msg,并且统计参数
		for (Bed bed : beds) {
			Map<String, Object> bed_msg = new HashMap<>();
			Map<String, Object> patient_msg = new HashMap<>();
			bed_msg.put("bedId", bed.getBedId());
			bed_msg.put("bedNmber", bed.getBedNumber());
			/**
			 * 封装病人信息
			 */
			// 病人基础信息
			if (bed.getPatient() != null) {
				patient_msg.put("patientId", bed.getPatient().getPatientId());
				patient_msg.put("patientName", bed.getPatient().getPatientName());
				patient_msg.put("patientAge", bed.getPatient().getPatientAge());
				patient_msg.put("patientSex", bed.getPatient().getPatientSex());
				patient_msg.put("admissionTime", format.format(bed.getPatient().getAdmissionTime()));
				// patient_msg.put("idCardNumber",bed.getPatient().getIdCardNumber());
				// patient_msg.put("patientAddress", bed.getPatient().getPatientAddress());
				// patient_msg.put("patientTel", bed.getPatient().getPatientTel());
				// 病人特征信息
				patient_msg.put("attendingDoctor", bed.getPatient().getAttendingDoctor());
				patient_msg.put("hospitalId", bed.getPatient().getHospitalId());
				// patient_msg.put("bedId", bed.getPatient().getBed().getBedId());
				// patient_msg.put("depId", bed.getPatient().getDep().getDepId());
				patient_msg.put("newPatient", bed.getPatient().getNewPatient());
				patient_msg.put("diagnose", bed.getPatient().getDiagnose());
				patient_msg.put("level", bed.getPatient().getLevel());
				patient_msg.put("diet", bed.getPatient().getDiet());
				patient_msg.put("allergichHistory", bed.getPatient().getAllergichHistory());
				patient_msg.put("allergic", bed.getPatient().getAllergic());
				patient_msg.put("hyperthermia", bed.getPatient().getHyperthermia());
				patient_msg.put("operation", bed.getPatient().getOperation());
				// 病人医保信息
				// patient_msg.put("healthCareNumber", bed.getPatient().getHealthCareNumber());
				patient_msg.put("healthCareType", bed.getPatient().getHealthCareType());
				patient_msg.put("totalMoney", bed.getPatient().getTotalMoney());
				patient_msg.put("personPayMoney", bed.getPatient().getPersonPayMoney());
				patient_msg.put("payMoney", bed.getPatient().getPayMoney());
				// 服药计划信息
				// patient_msg.put("totalTimes", bed.getPatient().getTotalTimes());
				// patient_msg.put("timeoutDisTimes", bed.getPatient().getTimeoutDisTimes());
				// patient_msg.put("timeoutEatTimes",bed.getPatient().getTimeoutEatTimes());
				/**
				 * 获取计划状态
				 */
				int planState = planDao.findMaxState(bed.getPatient().getPatientId());
				patient_msg.put("planState", planState);
				bed_msg.put("patient_msg", patient_msg);

				totalPatient++;// 病人增加
				if (bed.getPatient().getAllergic())
					allergicNumber++;
				if (bed.getPatient().getHyperthermia())
					hyperthermiaNumer++;
				if (bed.getPatient().getOperation())
					operationNumber++;
				if (bed.getPatient().getNewPatient())
					newNumber++;
				if (bed.getPatient().getLevel() == 0)
					superLevelNumber++;
				if (bed.getPatient().getLevel() == 1)
					firstLevelNumber++;
				if (bed.getPatient().getLevel() == 2)
					secondLevelNumber++;
				if (bed.getPatient().getLevel() == 3)
					thirdLevelNumber++;

				if (planState == 1)
					planNumber++;
				if (planState == 3)
					eatInNumber++;
				if (planState == 4)
					eatOutNumber++;
			} else {
				noUseBedNumber++;// 未使用床位数量
			}
			bedTotal++;
			beds_msg.add(bed_msg);
		}
		alarmTotal = beds.get(0).getDep().getSetAlarm().size();// 设置告警数量
		data.put("beds_msg", beds_msg);// 添加病床信息

		dep_msg.put("depId", depId);
		dep_msg.put("depName", depName);

		dep_msg.put("patientNumber", totalPatient);// 添加病人总量
		dep_msg.put("alarmTotal", alarmTotal);// 添加告警数量

		dep_msg.put("allergicNumber", allergicNumber);// 添加过敏病人总数
		dep_msg.put("hyperthermiaNumer", hyperthermiaNumer);// 添加高温病人数量
		dep_msg.put("newNumber", newNumber);// 添加新病人数量
		dep_msg.put("operationNumber", operationNumber);// 添加手术病人数量

		dep_msg.put("planNumber", planNumber);// 添加服药计划总量
		dep_msg.put("eatInNumber", eatInNumber);// 添加按时服药计划数量
		dep_msg.put("eatOutNumber", eatOutNumber);// 添加服药超时病人数量
		dep_msg.put("noUseBedNumber", noUseBedNumber);// 添加空闲床位数量

		dep_msg.put("superLevelNumber", superLevelNumber);// 添加特级护理病人数量
		dep_msg.put("firstLevelNumber", firstLevelNumber);// 添加一级护理病人数量
		dep_msg.put("secondLevelNumber", secondLevelNumber);// 添加二级护理病人数量
		dep_msg.put("thirdLevelNumber", thirdLevelNumber);// 添加三级护理病人数量
		data.put("dep_msg", dep_msg);
		return new ResultBean<Map<String, Object>>(data);
	}

	public ResultBean<Boolean> changeBed(Integer patientId, Integer bedId) {
		// 首先校验输入参数是否合法
		CheckUtil.notNull(patientId, "病人ID不能为空");
		CheckUtil.notNull(bedId, "换床床号不能为空");
		// 首先查询病人是否存在
		Patient patient_want_change_bed = patientDao.findOne(patientId.intValue());
		CheckUtil.notNull(patient_want_change_bed, "此病人已经不存在");
		// 判断换床床位是否有人使用
		Integer bedPatientId = patientDao.findPatientByBedId(bedId);
		PermissionUtil.check(bedPatientId == null, "此病床已经有人，请选择其他空病床。");
		// 判断病人是否存在告警
		CheckUtil.check(patient_want_change_bed.getSetAlarm().size() == 0, "此病人还存在告警请先处理，再换床");
		/**
		 * 执行换床操作
		 */
		// 1.更新原始床位(未使用)
		Bed bedPas = patient_want_change_bed.getBed();
		bedPas.setUseState(false);
		bedDao.update(bedPas);

		// 2.更新此病人的床位为新床位
		Bed bedNew = bedDao.findOne(bedId);
		bedNew.setUseState(true);
		patient_want_change_bed.setBed(bedNew);
		patientDao.update(patient_want_change_bed);
		// 3.更新所有未执行完成计划的床位
		Set<Plan> plans = patient_want_change_bed.getSetPlan();
		for (Plan plan : plans) {
			Plan newPlan = plan;
			plan.setBed(bedNew);
			planDao.update(newPlan);
		}
		// 4.更新新床位为使用
		bedDao.update(bedNew);
		// 加入换床记录
		BedChange bedChange = new BedChange();
		bedChange.setBedId(bedId);
		bedChange.setBedNumber(bedPas.getBedNumber());
		UserToken userToken = UserUtil.getUser();
		bedChange.setExcutePersonId(userToken.getAccountId());
		bedChange.setExcutePersonName(userToken.getUserName());
		bedChange.setTime(new Date());
		bedChangeDao.add(bedChange);
		logger.info("病人换床成功,patientId:{},bedId:{}", patientId.intValue(), bedId.intValue());
		return new ResultBean<Boolean>(true);
	}

	public ResultBean<List<String>> findHospitalId(String hopitalId) {
		List<Patient> patients = patientDao.findByHospitalId(hopitalId);
		List<String> hopitalIds = new ArrayList<>();
		int count = 0;
		for (Patient patient : patients) {
			hopitalIds.add(patient.getHospitalId());
			count++;
			if (count == 10)
				break;
		}

		return new ResultBean<List<String>>(hopitalIds);
	}

	public ResultBean<List<PatientBydownLoad>> findBydownLoad() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Patient> patients = patientDao.findAll();
		List<PatientBydownLoad> list = new ArrayList<>();
		for (Patient patient : patients) {
			PatientBydownLoad patientBydownLoad = new PatientBydownLoad();
			if(patient.getAdmissionTime()!=null)
			patientBydownLoad.setAdminTime(simpleDateFormat.format(patient.getAdmissionTime()));
			patientBydownLoad.setAge(Integer.valueOf(patient.getPatientAge()));
			patientBydownLoad.setAllergic(patient.getAllergic());
			patientBydownLoad.setAllergichHistory(patient.getAllergichHistory());
			patientBydownLoad.setAttendingDoctor(patient.getAttendingDoctor());
			if(patient.getBed().getBedNumber()!=null)
			patientBydownLoad.setBedNumber(patient.getBed().getBedNumber());
			patientBydownLoad.setDepName(patient.getDep().getDepName());
			patientBydownLoad.setDiet(patient.getDiet());
			patientBydownLoad.setHealthCareNumber(patient.getHealthCareNumber());
			patientBydownLoad.setHospitalId(patient.getHospitalId());
			if(patient.getHyperthermia()!=null)
			patientBydownLoad.setHyperthermia(patient.getHyperthermia());
			patientBydownLoad.setIdCardNumber(patient.getIdCardNumber());
			patientBydownLoad.setIllness(patient.getDiagnose());
			if(patient.getLevel()!=null)
			patientBydownLoad.setLevel(patient.getLevel());
			if(patient.getOperation()!=null)
			patientBydownLoad.setOparation(patient.getOperation());
			patientBydownLoad.setPatientAddress(patient.getPatientAddress());
			patientBydownLoad.setPatientName(patient.getPatientName());
			patientBydownLoad.setPatientTel(patient.getPatientTel());
			if(patient.getPayMoney()!=null)
			patientBydownLoad.setPayMoney(patient.getPayMoney());
			if(patient.getPersonPayMoney()!=null)
			patientBydownLoad.setPersonPayMoney(patient.getPersonPayMoney());
			patientBydownLoad.setSex(patient.getPatientSex());
			if(patient.getTotalMoney()!=null)
			patientBydownLoad.setTotalMoney(patient.getTotalMoney());
			patientBydownLoad.setHealthCareType(patient.getHealthCareType());
			// patientBydownLoad.setTimeoutDisTimes(patient.getTimeoutDisTimes());
			// patientBydownLoad.setTimeoutEatTimes(patient.getTimeoutEatTimes());
			// patientBydownLoad.setTotalTimes(patient.getTotalTimes());
			list.add(patientBydownLoad);
		}
		return new ResultBean<List<PatientBydownLoad>>(list);
	}

	public ResultBean<Boolean> upLoad(List<Map<String, Object>> maps) {
		
		for (Map<String, Object> map : maps) {
			Dep dep=null;
			Patient patient = new Patient();
			if (map.get("姓名") != null) {
				patient.setPatientName((String) map.get("姓名"));
			}
			if (map.get("年龄") != null) {
				patient.setPatientAge(((String)map.get("年龄")).toString());
			}
			if (map.get("性别") != null) {
				patient.setPatientSex((String) map.get("性别"));
			}
			if (map.get("身份证") != null) {
				patient.setIdCardNumber((String) map.get("身份证"));
			}
			if (map.get("住址") != null) {
				patient.setPatientAddress((String) map.get("住址"));
			}
			if (map.get("电话") != null) {
				patient.setPatientTel(((String) map.get("电话")).toString());
			}
			if (map.get("主治医生") != null) {
				patient.setAttendingDoctor((String) map.get("主治医生"));
			}
			if (map.get("住院号") != null) {
				patient.setHospitalId(((String) map.get("住院号")).toString());
			}
			CheckUtil.notEmpty((String)map.get("科室"),"科室不能为空");
			if (map.get("科室") != null) {
				dep = depDao.finByDepName((String) map.get("科室"));
				CheckUtil.notNull(dep,"科室不存在");
				patient.setDep(dep);
			}
			if (map.get("诊断") != null) {
				patient.setDiagnose((String) map.get("诊断"));
			}
			if (map.get("床号") != null) {
				List<Bed> beds_find = bedDao.findBedByDepId(dep.getDepId());
				Boolean control = false;
				Bed bed_save = new Bed();
				for (Bed bed : beds_find) {
					if(bed.getBedNumber().equals((Integer.parseInt((String)map.get("床号"))))) {
						control=true;
						bed_save.setBedId(bed.getBedId());
						patient.setBed(bed_save);
						break;
					}
				}
				CheckUtil.check(control,"床号不存在");
				
			}
			if(map.get("护理级别")!=null) {
				patient.setLevel((Integer.parseInt((String) map.get("护理级别"))));
			}
			if(map.get("饮食")!=null) {
				patient.setDiet((String) map.get("饮食"));
			}
			if(map.get("过敏史")!=null){
				patient.setAllergichHistory((String) map.get("过敏史"));
			}
			if(map.get("过敏")!=null) {
				patient.setAllergic(PlanUtil.changeStr2Bool((String) map.get("过敏")));
			}
			if(map.get("高温")!=null) {
				patient.setHyperthermia(PlanUtil.changeStr2Bool((String) map.get("高温")));
			}
			if(map.get("手术")!=null) {
				patient.setOperation(PlanUtil.changeStr2Bool((String) map.get("手术")));
			}
			if(map.get("社保账号")!=null) {
				patient.setHealthCareNumber((String) map.get("社保账号"));
			}
			if(map.get("社保类型")!=null){
				patient.setHealthCareType((String) map.get("社保类型"));
			}
			if(map.get("总费用")!=null){
				patient.setTotalMoney((Integer.parseInt((String)map.get("总费用"))));
			}
			if(map.get("个人支付")!=null){
				patient.setPersonPayMoney((Integer.parseInt((String) map.get("个人支付"))));
			}
			if(map.get("预支付")!=null){
				patient.setPayMoney((Integer.parseInt((String) map.get("预支付"))));
			}
			ResultBean<Boolean> resultBean = savePatient(patient);
			return resultBean;
		}
		 ResultBean<Boolean> res = new ResultBean<>(false);
		 res.setMsg("表格为空");
		 res.setCode(401);
		 return res;
	}
}

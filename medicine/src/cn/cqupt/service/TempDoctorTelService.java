package cn.cqupt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import Modle.TableValueChangeNull;
import Util.CheckUtil;
import Util.PageBean;
import Util.ResultBean;
import cn.cqupt.dao.DoctorTelDao;
import cn.cqupt.dao.PatientDao;
import cn.cqupt.dao.TempDoctorTelDao;
import cn.cqupt.entity.DoctorTel;
import cn.cqupt.entity.Patient;
import cn.cqupt.entity.TemporaryDoctorTel;
@Transactional
public class TempDoctorTelService {
	private DoctorTelService doctorTelService;
	private TempDoctorTelDao tempDoctorTelDao;
	private PatientDao patientDao;
	private DoctorTelDao doctorTelDao;
	
	public void setDoctorTelDao(DoctorTelDao doctorTelDao) {
		this.doctorTelDao = doctorTelDao;
	}

	public void setPatientDao(PatientDao patientDao) {
		this.patientDao = patientDao;
	}

	public void setDoctorTelService(DoctorTelService doctorTelService) {
		this.doctorTelService = doctorTelService;
	}

	public void setTempDoctorTelDao(TempDoctorTelDao tempDoctorTelDao) {
		this.tempDoctorTelDao = tempDoctorTelDao;
	}

	public ResultBean<Boolean> save(TemporaryDoctorTel temporaryDoctorTel) {
		CheckUtil.notNull(temporaryDoctorTel.getBeginTime(),"起始时间不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getMedicine(),"药品名称不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getQuantity(),"剂量不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getFrequence(),"频次不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getDoctorName(),"医生不能为空");
		CheckUtil.notNull(temporaryDoctorTel.getEndTime(),"结束时间不能为空");
		CheckUtil.notNull(temporaryDoctorTel.getEatType(),"饭前/饭后不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getDetail(),"备注不能为空");
		CheckUtil.notNull(temporaryDoctorTel.getPatient().getPatientId(),"病人编号不能为空");
		Patient patient = patientDao.findOne(temporaryDoctorTel.getPatient().getPatientId());
		CheckUtil.notNull(patient,"病人已经不存在，请刷新页面");
		tempDoctorTelDao.add(temporaryDoctorTel);
		return new ResultBean<Boolean>(true);
	}

	public ResultBean<Boolean> modify(TemporaryDoctorTel temporaryDoctorTel) {
		CheckUtil.notNull(temporaryDoctorTel.getTempId(),"id不能为空");
		CheckUtil.notNull(temporaryDoctorTel.getBeginTime(),"起始时间不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getMedicine(),"药品名称不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getQuantity(),"剂量不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getFrequence(),"频次不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getDoctorName(),"医生不能为空");
		CheckUtil.notNull(temporaryDoctorTel.getEndTime(),"结束时间不能为空");
		CheckUtil.notNull(temporaryDoctorTel.getEatType(),"饭前/饭后不能为空");
		CheckUtil.notEmpty(temporaryDoctorTel.getDetail(),"备注不能为空");
		Patient patient = patientDao.findOne(temporaryDoctorTel.getPatient().getPatientId());
		CheckUtil.notNull(patient,"病人已经不存在，请刷新页面");
		tempDoctorTelDao.update(temporaryDoctorTel);
		return new ResultBean<Boolean>(false);
	}

	public  ResultBean<PageBean<Map<String,Object>>> findByPatientId(Integer patientId, Integer limit, Integer currentPage) {
		/**
		 * 校验
		 */
		CheckUtil.notNull(patientId,"病人ID为空");
		CheckUtil.notNull(limit,"每页显示数量不能为空");
		CheckUtil.notNull(currentPage,"当前页数不能为空");
		
		PageBean<Map<String, Object>> pageBean = new PageBean<>();
		pageBean.setLimit(limit);//设置每页数量显示多少
		pageBean.setPage(currentPage);//设置当前页数
		int cnt = tempDoctorTelDao.findCnt(patientId);
		pageBean.setTotalCount(cnt);//设置总记录数
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		CheckUtil.notNull(patientId,"病人Id");
		List<TemporaryDoctorTel> list = tempDoctorTelDao.findByPatientId(patientId,limit,pageBean.getBegin());
		List<Map<String,Object>> res = new ArrayList<>();
		//String[] tableNames = {"医嘱ID","住院号","科室","床号","医嘱状态","起始时间","结束时间","执行人","医生姓名","药品名称","剂量","频次"};
		String[] tableNames = {"起始时间","药品名称","剂量","频次","医生名称","结束时间","饭前/饭后","备注"};
		pageBean.setTableNames(Arrays.asList(tableNames));
		for (TemporaryDoctorTel temporaryDoctorTel : list) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("beginTime",new TableValueChangeNull("起始时间",format.format(temporaryDoctorTel.getBeginTime()),false,true));
			map.put("medicine",new TableValueChangeNull("药品名称",temporaryDoctorTel.getMedicine(),false,true));
			map.put("quantity",new TableValueChangeNull("剂量",temporaryDoctorTel.getQuantity(),false,true));
			map.put("frequence",new TableValueChangeNull("频次",temporaryDoctorTel.getFrequence(),false,true));
			map.put("doctorName",new TableValueChangeNull("医生名称",temporaryDoctorTel.getDoctorName(),false,true));
			map.put("endTime",new TableValueChangeNull("结束时间",format.format(temporaryDoctorTel.getEndTime()),false,true));
			map.put("eatType",new TableValueChangeNull("饭前/饭后",temporaryDoctorTel.getEatType(),false,true));
			map.put("detail",new TableValueChangeNull("备注",temporaryDoctorTel.getDetail(),true,true));
			map.put("tempId",new TableValueChangeNull("",temporaryDoctorTel.getTempId(),false,false));
			res.add(map);
		}
		pageBean.setList(res);
 		return new ResultBean<PageBean<Map<String,Object>>>(pageBean);
	}
	/**
	 * 删除
	 * @param tempId
	 * @return
	 */
	public ResultBean<Boolean> delect(Integer tempId) {
		CheckUtil.notNull(tempId,"编号不能为空");
		TemporaryDoctorTel temporaryDoctorTel = tempDoctorTelDao.findOne(tempId);
		CheckUtil.notNull(temporaryDoctorTel,"临时医嘱不存在");
		tempDoctorTelDao.delete(temporaryDoctorTel);
		return new ResultBean<Boolean>(true);
	}
	/**
	 * 提交临时表单
	 * @return
	 */
	public ResultBean<Boolean> submit(Integer tempId){
		//1.校验tempId是否为空
		CheckUtil.notNull(tempId,"医嘱Id为空");
		//2.查询到临时医嘱
		TemporaryDoctorTel temporaryDoctorTel = tempDoctorTelDao.findOne(tempId);
		CheckUtil.notNull(temporaryDoctorTel,"零时表医嘱表中已经不存在ID为:"+tempId+"的医嘱");
		//3.执行转化
		DoctorTel doctorTel = new DoctorTel();
		doctorTel.setBeginTime(temporaryDoctorTel.getBeginTime());
		doctorTel.setMedicine(temporaryDoctorTel.getMedicine());
		doctorTel.setQuantity(temporaryDoctorTel.getQuantity());
		doctorTel.setFrequence(temporaryDoctorTel.getFrequence());
		doctorTel.setDoctorName(temporaryDoctorTel.getDoctorName());
		doctorTel.setEndTime(temporaryDoctorTel.getEndTime());
		doctorTel.setEatType(temporaryDoctorTel.getEatType());
		doctorTel.setDetail(temporaryDoctorTel.getDetail());
		doctorTel.setPatient(temporaryDoctorTel.getPatient());
		//3.删除临时表中的信息
		tempDoctorTelDao.delete(temporaryDoctorTel);
		//4.保存零时表中的信息到医嘱表中
		 this.add(doctorTel);
		 return new ResultBean<Boolean>(true);
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
	
}

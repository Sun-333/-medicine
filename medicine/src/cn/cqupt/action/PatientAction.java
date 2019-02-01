package cn.cqupt.action;


import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import Modle.DoctorTelDownLoad;
import Modle.PatientBydownLoad;
import Modle.delectPatientModle;
import Modle.findPatientCondition;
import Util.BaseAction;
import Util.ExportExcel;
import Util.JsonPluginsUtil;
import Util.ResultBean;
import Util.SendResponseJsonUtil;

import cn.cqupt.entity.Bed;
import cn.cqupt.entity.Dep;
import cn.cqupt.entity.Patient;
import cn.cqupt.service.PatientService;

public class PatientAction extends BaseAction{
	//配置下层依赖
	PatientService patientService;
	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}
	/**
	 * 多条件查询病人信息
	 */
	public void moreCondition(){
		//获取查询参数
		getRequestJsonStr();
		findPatientCondition condicine = JsonPluginsUtil.jsonToBean(jsonStr_In,findPatientCondition.class);
		//直接调用下层方法执行查询
		resultBean = patientService.findByMoreCondition(condicine);
		//发送结果
		String[] noStrName= {"begin"};
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean,noStrName,false);
	}
	public void getPatient(){
		//获取patientId
		getRequestMap();
		String patientIdStr = (String) map.get("patientId");
		Integer patientId = null;
		if(patientIdStr==null||patientIdStr.trim().isEmpty()){
			//无操作
		}else {
			patientId=Integer.valueOf(patientIdStr);
		}
		//调用下层方法执行查询
		resultBean = patientService.findByPatientId(patientId);
		String[] _nory_changes = {"tableName"};
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean, _nory_changes, false);;
	}
	/**
	 * 修改病人信息
	 */
	public void modifyPatient(){
		//获取修改参数
		getRequestJsonStr();
		//创建科室、病床、病人
		Patient patient = JsonPluginsUtil.jsonToBean(jsonStr_In, Patient.class);
		Dep dep = JsonPluginsUtil.jsonToBean(jsonStr_In, Dep.class);
		Bed bed = JsonPluginsUtil.jsonToBean(jsonStr_In, Bed.class);
		patient.setDep(dep);
		patient.setBed(bed);
		//调用下层方法执行查询
		resultBean = patientService.modify(patient);
		//返回结果集
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 添加病人
	 */
	public void addPatient(){
		//获取新加病人数据
		getRequestJsonStr();
		Patient patient = JsonPluginsUtil.jsonToBean(jsonStr_In, Patient.class);
		Dep dep = JsonPluginsUtil.jsonToBean(jsonStr_In,Dep.class);
		Bed bed = JsonPluginsUtil.jsonToBean(jsonStr_In, Bed.class);
		patient.setBed(bed);
		patient.setDep(dep);
		//调用下层方法执行查询
		resultBean = patientService.savePatient(patient);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		
	}
	public void delect(){
		//获取patientIds
		getRequestJsonStr();
		delectPatientModle patientIds = JsonPluginsUtil.jsonToBean(jsonStr_In, delectPatientModle.class);
		
		System.out.println(patientIds.toString());
		StringBuffer stringBuffer = new StringBuffer();
		Boolean contro = true;
		for (Integer patientId : patientIds.getPatientIds()) {
			//调用下层方法执行删除
			resultBean = patientService.delectPatient(patientId);
			if(resultBean.getCode()!=200) {
				stringBuffer.append("patientId:"+patientId+" "+"病人出院失败,原因为:"+resultBean.getMsg()+"。");
				contro=false;
			} 
		}
		if(contro){
			SendResponseJsonUtil.sendResponseJsonUtil(response, new ResultBean<Boolean>(true));
		}else{
			ResultBean<Boolean> resultBeanfall=new ResultBean<>();
			resultBeanfall.setCode(402);
			resultBeanfall.setData(false);
			resultBeanfall.setMsg(stringBuffer.toString());
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBeanfall);
		}
		
	}
	public void changeBed(){
		//获取病人编号,换床BedId
		getRequestMap();
		String bedIdStr=(String) map.get("bedId");
		String patientIdStr=(String) map.get("patientId");
		Integer patientId=null;
		Integer bedId=null;
		if(bedIdStr!=null&&!bedIdStr.trim().isEmpty()){
			bedId=Integer.valueOf(bedIdStr);
		}
		if(patientIdStr!=null&&!patientIdStr.trim().isEmpty()){
			patientId=Integer.valueOf(patientIdStr);
		}
		//调用下层方法执行病人换床
		resultBean = patientService.changeBed(patientId,bedId);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 获取主页参数
	 */
	public void getHomePageInf(){
		getRequestJsonStr();
		/**
		 * 方法一从token里面获取depId,userId
		 * 直接从token解析的user中获取
		 */
		Patient patient = new Patient();
		Dep dep = new Dep();
		Bed bed = new Bed();
		patient = JsonPluginsUtil.jsonToBean(jsonStr_In, Patient.class);
		dep = JsonPluginsUtil.jsonToBean(jsonStr_In, Dep.class);
		bed = JsonPluginsUtil.jsonToBean(jsonStr_In, Bed.class);
		patient.setDep(dep);
		patient.setBed(bed);
		//调用下层方法执行查询
		resultBean = patientService.getHomePageInf(patient);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	public void addPatients(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(int i=1;i<=50;i++){
			Patient patient = new Patient();
			patient.setAdmissionTime(new Date());
			patient.setAllergic(true);
			patient.setAttendingDoctor("医生"+i);
			Bed bed = new Bed();
			bed.setBedId(i+155);
			/*Bed bed_find =bedDao.findOne(i+104);
			bed_find.setUseState(true);
			bedDao.update(bed_find);*/
			patient.setBed(bed);
			Dep dep = new Dep();
			dep.setDepId(1);
			patient.setDep(dep);
			patient.setDiagnose("病情"+i);
			patient.setDiet("海鲜过敏");
			patient.setHealthCareNumber(UUID.randomUUID().toString());
			patient.setHealthCareType("居民医保");
			patient.setHospitalId("102133"+i);
			patient.setHyperthermia(true);
			patient.setIdCardNumber(UUID.randomUUID().toString());
			patient.setLevel(i%4);
			if(i%2!=0){
				patient.setNewPatient(true);
				patient.setOperation(true);
			}else{
				patient.setNewPatient(false);
				patient.setOperation(false);
			}
			patient.setPlanState(0);
			patient.setPatientAddress("重庆");
			patient.setPatientAge("70");
			patient.setPatientName("张三"+i);
			patient.setPatientSex("男");
			patient.setPatientTel("110"+i);
			patient.setPayMoney(15000);
			patient.setPersonPayMoney(1000);
			patient.setTotalMoney(15000);
			patientService.savePatient(patient);
		}
	}
	public void findHospitalId(){
		getRequestMap();
		String hopitalId = (String) map.get("hospitalId");
		resultBean = patientService.findHospitalId(hopitalId);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	public void downLoad(){
		try{
			   response.setContentType("application/x-execl");  
         //2.2 设置以下载方式打开文件  
     response.setHeader("Content-Disposition", "attachment;filename="+new String("病人列表.xls".getBytes(),"ISO-8859-1") ); 
			
			ResultBean<List<PatientBydownLoad>> bean =patientService.findBydownLoad();
			List<PatientBydownLoad> dotorTel=bean.getData();
			OutputStream outputStream = response.getOutputStream();
			System.out.println(dotorTel.toString());
			ExportExcel<PatientBydownLoad> exportExcel  = new ExportExcel<>();
			String[] tableNames ={"床位","姓名","诊断","科室","护理级别","年龄","性别","住院号","联系电话","入院时间","身份证","住址","主治医生","饮食","过敏史",
					"过敏","高温","手术","社保账号","社保类型","费用合计","个人支付","预交款","服药总次数","发药超时","服药超时"};
			exportExcel.exportExcel("病人导出列表",tableNames,dotorTel, outputStream,"yyyy-MM-dd");
			outputStream.close();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}

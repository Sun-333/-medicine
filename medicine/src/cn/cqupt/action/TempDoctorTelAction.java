package cn.cqupt.action;

import java.util.List;

import Modle.DelectTempDoctorTel;
import Modle.IDs;
import Util.BaseAction;
import Util.JsonPluginsUtil;
import Util.ResultBean;
import Util.SendResponseJsonUtil;
import cn.cqupt.entity.DoctorTel;
import cn.cqupt.entity.Patient;
import cn.cqupt.entity.TemporaryDoctorTel;
import cn.cqupt.service.DoctorTelService;
import cn.cqupt.service.TempDoctorTelService;

public class TempDoctorTelAction extends BaseAction {
	private TempDoctorTelService tempDoctorTelService;
	private DoctorTelService doctorTelService;
	public void setDoctorTelService(DoctorTelService doctorTelService) {
		this.doctorTelService = doctorTelService;
	}

	public void setTempDoctorTelService(TempDoctorTelService tempDoctorTelService) {
		this.tempDoctorTelService = tempDoctorTelService;
	}
	
	public void save(){
		getRequestJsonStr();
		TemporaryDoctorTel temporaryDoctorTel = JsonPluginsUtil.jsonToBean(jsonStr_In,TemporaryDoctorTel.class);
		Patient patient = JsonPluginsUtil.jsonToBean(jsonStr_In, Patient.class);
		temporaryDoctorTel.setPatient(patient);
		resultBean = tempDoctorTelService.save(temporaryDoctorTel);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	
	public void submit(){
		getRequestJsonStr();
		IDs iDs = JsonPluginsUtil.jsonToBean(jsonStr_In, IDs.class);
		List<Integer> ids = iDs.getIds();
		boolean control = true;
		StringBuffer buffer = new StringBuffer();
		for (Integer tempId : ids) {
			ResultBean<Boolean> bean = tempDoctorTelService.submit(tempId);
			if(!bean.getData()) {
				buffer.append("id对应为"+tempId+"的临时医嘱提交错误:"+bean.getMsg()+"。");
				control=false;
			}
		}
		if(control) {
			SendResponseJsonUtil.sendResponseJsonUtil(response, new ResultBean<Boolean>(true));
		}else {
			ResultBean<Boolean> res = new ResultBean<>();
			res.setCode(401);
			res.setData(false);
			res.setMsg(buffer.toString());
			SendResponseJsonUtil.sendResponseJsonUtil(response, res);
		}
	}
	public void modify(){
		getRequestJsonStr();
		TemporaryDoctorTel temporaryDoctorTel = JsonPluginsUtil.jsonToBean(jsonStr_In,TemporaryDoctorTel.class);
		Patient patient = JsonPluginsUtil.jsonToBean(jsonStr_In, Patient.class);
		temporaryDoctorTel.setPatient(patient);
		resultBean = tempDoctorTelService.modify(temporaryDoctorTel);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	public void findByPatientId(){
		getRequestMap();
		String patientIdStr = (String) map.get("patientId");
		Integer patientId = null;
		String limitStr = (String) map.get("limit");
		Integer limit = null;
		if(limitStr!=null&&!limitStr.trim().isEmpty()){
			limit=Integer.valueOf(limitStr);
		}
		String currentPageStr=(String)map.get("currentPage");
		Integer currentPage = null;
		if(currentPageStr!=null&&!currentPageStr.trim().isEmpty()) {
			currentPage=Integer.valueOf(currentPageStr);
		}
		if(patientIdStr!=null&&!patientIdStr.trim().isEmpty()){
			patientId = Integer.valueOf(patientIdStr);
		}
		if(patientId!=null) {
			resultBean = tempDoctorTelService.findByPatientId(patientId,limit,currentPage);
		}else {
			String[] tableNames = {"起始时间","药品名称","剂量","频次","医生名称","结束时间","饭前/饭后","备注"};
			resultBean=new ResultBean<String []>(tableNames);
		}
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	public void delect(){
		getRequestMap();
		DelectTempDoctorTel delectTempDoctorTel = JsonPluginsUtil.jsonToBean(jsonStr_In,DelectTempDoctorTel.class);
		List<Integer> tempIds=delectTempDoctorTel.getTempIds();
		boolean control=true;
		StringBuffer buffer = new StringBuffer("删除临时医嘱失败原因:");
		for (Integer tempId : tempIds) {
			resultBean = tempDoctorTelService.delect(tempId);
			if(resultBean.getCode()!=200){
				control=false;
				buffer.append("临时医嘱ID为"+tempId+"的临时医嘱："+resultBean.getMsg()+"。");
			}
		}
		if(control) {
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}else {
			ResultBean<Boolean> bean = new ResultBean<Boolean>(false);
			bean.setCode(401);
			bean.setMsg(buffer.toString());
			SendResponseJsonUtil.sendResponseJsonUtil(response, bean);
		}
	}
}

package cn.cqupt.action;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import Modle.DelectDoctorTel;
import Modle.DepByDownLoad;
import Modle.DoctorTelDownLoad;
import Modle.DoctorTelFind;
import Util.BaseAction;
import Util.ExportExcel;
import Util.JsonPluginsUtil;
import Util.ResultBean;
import Util.SendResponseJsonUtil;
import cn.cqupt.entity.DoctorTel;
import cn.cqupt.entity.Patient;
import cn.cqupt.service.DoctorTelService;

public class DoctorTelAction extends BaseAction{
	
	private DoctorTelService doctorTelService;
	public void setDoctorTelService(DoctorTelService doctorTelService) {
		this.doctorTelService = doctorTelService;
	}
	/**
	 * 查询医嘱
	 */
	public void find(){
		getRequestMap();
		String planId = (String) map.get("planId");
		resultBean = doctorTelService.find(planId);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 添加医嘱
	 */
	public void add() {
		getRequestJsonStr();
		DoctorTel doctorTel= JsonPluginsUtil.jsonToBean(jsonStr_In,DoctorTel.class);
		Patient patient = JsonPluginsUtil.jsonToBean(jsonStr_In,Patient.class);
		doctorTel.setPatient(patient);
		resultBean = doctorTelService.add(doctorTel);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 删除医嘱
	 */
	public void delect(){
		getRequestMap();
		DelectDoctorTel delectDoctorTel = JsonPluginsUtil.jsonToBean(jsonStr_In,DelectDoctorTel.class);
		boolean control = true;
		StringBuffer buffer = new StringBuffer("删除存在错误");
		List<Integer> doctorTelIds = delectDoctorTel.getDoctorTelIds();
		for (Integer doctorTelId : doctorTelIds) {
			resultBean = doctorTelService.delect(doctorTelId);
			if(resultBean.getCode()!=200){
				control=false;
				buffer.append("医嘱编号为"+doctorTelId+"的医嘱删除失败:"+resultBean.getMsg()+"。");
			}
		}
		if(control){
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}else {
			ResultBean<Boolean> bean = new ResultBean<Boolean>(false);
			bean.setCode(401);
			bean.setMsg(buffer.toString());
			SendResponseJsonUtil.sendResponseJsonUtil(response, bean);
		}
	}
	/**
	 * 修改医嘱
	 */
	public void modify(){
		getRequestJsonStr();
		DoctorTel doctorTel  = JsonPluginsUtil.jsonToBean(jsonStr_In,DoctorTel.class);
		resultBean = doctorTelService.modify(doctorTel);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 撤销医嘱
	 */
	public void withdraw(){
		getRequestMap();
		String doctorTelIdStr = (String) map.get("doctorTelId");
		Integer doctorTelId =null;
		if(doctorTelIdStr!=null&&!doctorTelIdStr.trim().isEmpty()) doctorTelId=Integer.valueOf(doctorTelIdStr);
		resultBean=doctorTelService.withdraw(doctorTelId);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 下发医嘱
	 */
	public void ackDoctorTel(){
		getRequestMap();
		String doctorTelIdStr = (String) map.get("doctorTelId");
		Integer doctorTelId =null;
		if(doctorTelIdStr!=null&&!doctorTelIdStr.trim().isEmpty()) doctorTelId=Integer.valueOf(doctorTelIdStr);
		resultBean=doctorTelService.ackDoctorTel(doctorTelId);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		
	}
	/**
	 * 多条件查询医嘱
	 */
	public void moreCondition(){
		getRequestMap();
		DoctorTelFind doctorTelFind = JsonPluginsUtil.jsonToBean(jsonStr_In,DoctorTelFind.class);
		String limitStr=(String) map.get("limit");
		Integer limit = null;
		if(limitStr!=null&&!limitStr.trim().isEmpty()){
			limit=Integer.valueOf(limitStr);
		}
		String currentPageStr = (String) map.get("currentPage");
		Integer currentPage = null;
		if(currentPageStr!=null&&!currentPageStr.trim().isEmpty()) {
			currentPage=Integer.valueOf(currentPageStr);
		}
		
		resultBean = doctorTelService.moreCondition(limit,currentPage,doctorTelFind);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 执行
	 */
	public void excute() {
		getRequestMap();
		String doctorTelIdStr = (String) map.get("doctorTelId");
		Integer doctorTelId =null;
		if(doctorTelIdStr!=null&&!doctorTelIdStr.trim().isEmpty()) doctorTelId=Integer.valueOf(doctorTelIdStr);
		resultBean=doctorTelService.excute(doctorTelId);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 手动关闭
	 */
	public void close(){
		getRequestMap();
		DelectDoctorTel delectDoctorTel = JsonPluginsUtil.jsonToBean(jsonStr_In,DelectDoctorTel.class);
		boolean control = true;
		StringBuffer buffer = new StringBuffer("关闭存在错误");
		List<Integer> doctorTelIds = delectDoctorTel.getDoctorTelIds();
		for (Integer doctorTelId : doctorTelIds) {
			resultBean = doctorTelService.close(doctorTelId);
			if(resultBean.getCode()!=200){
				control=false;
				buffer.append("医嘱编号为"+doctorTelId+"的医嘱关闭失败:"+resultBean.getMsg()+"。");
			}
		}
		if(control){
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}else {
			ResultBean<Boolean> bean = new ResultBean<Boolean>(false);
			bean.setCode(401);
			bean.setMsg(buffer.toString());
			SendResponseJsonUtil.sendResponseJsonUtil(response, bean);
		}
	}
	public void downLoad() {
		try{
			   response.setContentType("application/x-execl");  
            //2.2 设置以下载方式打开文件  
        response.setHeader("Content-Disposition", "attachment;filename="+new String("医嘱列表.xls".getBytes(),"ISO-8859-1") ); 
			
			ResultBean<List<DoctorTelDownLoad>> bean = doctorTelService.findAll();
			System.out.println(bean.toString());
			List<DoctorTelDownLoad> dotorTel=bean.getData();
			OutputStream outputStream = response.getOutputStream();
			System.out.println(dotorTel.toString());
			ExportExcel<DoctorTelDownLoad> exportExcel  = new ExportExcel<>();
			String[] tableNames = {"医嘱编号","住院号","科室","床号","医嘱状态","状态时间","起始时间","结束时间","执行人","医生姓名","药品名称","计量","频次","饭前/饭后","执行次数","备注"};
			exportExcel.exportExcel("医嘱导出文档",tableNames,dotorTel, outputStream,"yyyy-MM-dd");
			outputStream.close();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}


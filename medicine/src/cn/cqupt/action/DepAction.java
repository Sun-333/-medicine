package cn.cqupt.action;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import Modle.DepByDownLoad;
import Modle.DepIds;
import Modle.FindDep;
import Util.BaseAction;
import Util.ExportExcel;
import Util.JsonPluginsUtil;
import Util.ResultBean;
import Util.SendResponseJsonUtil;
import cn.cqupt.entity.Dep;
import cn.cqupt.service.DepService;

public class DepAction extends BaseAction{
	//创建依赖
	private DepService depService;
	public void setDepService(DepService depService) {
		this.depService = depService;
	}
	/***
	 * 获取所有部门名
	 */
	public void getAllName() {
		
		 //禁止页面缓存

	      response.setHeader("Pragma", "No-cache");

	      response.setHeader("Cache-Control", "No-cache");

	      response.setDateHeader("Expires", 0);
		
		//直接调用下层方法查询所有部门名
		resultBean = depService.findAllDepName();
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	
	/***
	 * 获取科室信息
	 */
	public void getDep() {
		//获取参数
		getRequestJsonStr();;
		FindDep findDep= JsonPluginsUtil.jsonToBean(jsonStr_In, FindDep.class);
	
		//调用下层方法进行查询
		resultBean=depService.findDep(findDep);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	
	/***
	 * 添加部门信息
	 */
	public void addDep(){
		//获取部门信息参数封装到
		getRequestJsonStr();
		Dep dep = JsonPluginsUtil.jsonToBean(jsonStr_In, Dep.class);
		resultBean=depService.save(dep);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/***
	 * 修改部门信息
	 */
	public void modifyDep(){
		//获取将参数封装到dep中
		getRequestJsonStr();
		Dep dep = JsonPluginsUtil.jsonToBean(jsonStr_In, Dep.class);
		resultBean=depService.modifyDep(dep);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/***
	 * 删除部门信息
	 */
	public void delectDep() {
		getRequestJsonStr();
		DepIds delectDep = JsonPluginsUtil.jsonToBean(jsonStr_In, DepIds.class);
		Integer[] depIds = delectDep.getDepIds();
		if(depIds==null||depIds.length==0){
			resultBean=new ResultBean<>();
			resultBean.setCode(ResultBean.NO_PERMISSION);
			resultBean.setMsg("必须选择删除目标");
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
			return ;
		}
		boolean control=true;
		StringBuffer buffer = new StringBuffer();
		for (Integer depId: depIds) {
			resultBean=depService.delectDep(depId);
			if(resultBean.getCode()!=200){
				buffer.append("depId:"+depId+"删除失败"+resultBean.getMsg());
				control=false;
			}
		}
		if(control){
			SendResponseJsonUtil.sendResponseJsonUtil(response, new ResultBean<Boolean>(true));
		}else {
			ResultBean<Boolean> bean=new ResultBean<Boolean>(false);
			bean.setCode(ResultBean.CHECK_FAIL);
			bean.setMsg(buffer.toString());
			SendResponseJsonUtil.sendResponseJsonUtil(response,bean);
		}
	}
	/**
	 * 下载文件
	 */
	public void downLoad(){
		try{
			   response.setContentType("application/x-execl");  
               //2.2 设置以下载方式打开文件  
           response.setHeader("Content-Disposition", "attachment;filename="+new String("科室列表.xls".getBytes(),"ISO-8859-1") ); 
			
			ResultBean<List<DepByDownLoad>> bean = depService.findAll();
			List<DepByDownLoad> deps=bean.getData();
			System.out.println(deps.toString());
			OutputStream outputStream = response.getOutputStream();
			ExportExcel<DepByDownLoad> exportExcel  = new ExportExcel<>();
			String[] tableNames = {"科室编号","科室名称","科室最大床位","科室描述"}; 
			
			exportExcel.exportExcel("科室导出文档",tableNames, deps, outputStream,"yyyy-MM-dd");
			outputStream.close();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}

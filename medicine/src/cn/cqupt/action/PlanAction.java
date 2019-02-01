package cn.cqupt.action;









import java.io.OutputStream;
import java.util.List;

import Modle.AddPlanModle;
import Modle.DelectPlanModle;
import Modle.ModifyPlanModel;
import Modle.PatientBydownLoad;
import Modle.PlanByDownLoad;
import Util.BaseAction;
import Util.ExportExcel;
import Util.JsonPluginsUtil;
import Util.ResultBean;
import Util.SendResponseJsonUtil;

import cn.cqupt.entity.Bed;
import cn.cqupt.entity.Dep;
import cn.cqupt.entity.Patient;
import cn.cqupt.entity.Plan;
import cn.cqupt.service.PlanService;

public class PlanAction extends BaseAction{
	//配置依赖
	PlanService planService;
	public void setPlanService(PlanService planService) {
		this.planService = planService;
	}
	/**
	 * 多条件查询计划
	 */
	public void moreCondition(){
		//封装查询参数到plan中
		getRequestMap();
		String pageStr = (String) map.get("currentPage");
		Integer page = null;
		//获取查询页数
		if(pageStr==null||pageStr.trim().isEmpty()){
			//无操作
		}else{
			page=Integer.valueOf(pageStr);
		}
		//获取每页显示数量
		String limitStr=(String) map.get("limit");
		Integer limit = null;
		if(limitStr==null||limitStr.trim().isEmpty()){
			//无操作
		}else{
			limit=Integer.valueOf(limitStr);
		}
		Plan plan = JsonPluginsUtil.jsonToBean(jsonStr_In, Plan.class);
		Bed bed = JsonPluginsUtil.jsonToBean(jsonStr_In, Bed.class);
		Patient patient = JsonPluginsUtil.jsonToBean(jsonStr_In, Patient.class);
		Dep dep = JsonPluginsUtil.jsonToBean(jsonStr_In, Dep.class);
		plan.setBed(bed);
		plan.setDep(dep);
		plan.setPatient(patient);
		//调用下层方法执行查询
		resultBean = planService.findByMoreCondition(plan,page,limit);
		String[] no= {"begin"};
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean, no,false);
	}
	/**
	 * 添加计划
	 * @throws Exception 
	 */
	public void addPlan() throws Exception{
		//一键封装参数到addPlanModle
		getRequestJsonStr();
		AddPlanModle addPlanModle = JsonPluginsUtil.jsonToBean(jsonStr_In, AddPlanModle.class);
		//调用下层方法执行批量保存
		resultBean = planService.addPlan(addPlanModle);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 修改计划
	 */
	public void modifyPlan(){
		//一键封装修改数据到plan
		getRequestJsonStr();
		System.out.println(jsonStr_In);
		ModifyPlanModel plan= JsonPluginsUtil.jsonToBean(jsonStr_In, ModifyPlanModel.class);
		//调用下层方法执行修改
		resultBean = planService.modifyPlan(plan);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	public void delectPlan(){
		//获取要删除的计划
		getRequestJsonStr();
		DelectPlanModle delectPlanModle = JsonPluginsUtil.jsonToBean(jsonStr_In, DelectPlanModle.class);
		resultBean=planService.delectPlan(delectPlanModle.getPlanIds());
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	public void downLoad(){
		try{
			   response.setContentType("application/x-execl");  
      //2.2 设置以下载方式打开文件  
  response.setHeader("Content-Disposition", "attachment;filename="+new String("计划列表.xls".getBytes(),"ISO-8859-1") ); 
			
			ResultBean<List<PlanByDownLoad>> bean =planService.findBydownLoad();
			List<PlanByDownLoad> dotorTel=bean.getData();
			OutputStream outputStream = response.getOutputStream();
			System.out.println(dotorTel.toString());
			ExportExcel<PlanByDownLoad> exportExcel  = new ExportExcel<>();
			String[] tableNames = {"计划ID","床号","姓名","住院号","科室","状态","计划时间","计划人","执行时间","执行人","计划有效时间"};
			exportExcel.exportExcel("计划导出列表",tableNames,dotorTel, outputStream,"yyyy-MM-dd");
			outputStream.close();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	public void findByPlanId() {
		getRequestMap();
		String planId = (String) map.get("planId");
		resultBean=planService.findByPlanId(planId);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
}

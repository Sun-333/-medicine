package cn.cqupt.action;





import java.io.OutputStream;
import java.util.List;

import Modle.PlanByDownLoad;
import Modle.RoleByDownLoad;
import Modle.RoleIds;
import Util.BaseAction;
import Util.ExportExcel;
import Util.JsonPluginsUtil;
import Util.ResultBean;
import Util.SendResponseJsonUtil;
import cn.cqupt.entity.Role;
import cn.cqupt.service.RoleService;

public class RoleAction extends BaseAction{
	//创建共同依赖
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	/***
	 * 查看所有角色名
	 */
	public void findAllName() {
		//调用下层方法实现查询
		resultBean = roleService.findAllName();
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	
	/***
	 * 添加角色信息
	 */
	public void addRole() {
		//获取新加入role的参数
		getRequestMap();
		String roleName = (String) map.get("roleName");
		String roleDetail = (String) map.get("roleDetail");
		Object rolePerm = map.get("rolePerm");
		Role role = new Role();
		role.setRoleName(roleName);
		role.setRoleDetail(roleDetail);
		role.setRolePerm(rolePerm.toString());
		resultBean = roleService.addRole(role);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	
	/***
	 * 修改角色信息
	 */
	public void modifyRole(){
		//获取角色信息封装到role
		getRequestMap();
		String roleName = (String) map.get("roleName");
		String roleDetail = (String) map.get("roleDetail");
		String roleIdStr = (String) map.get("roleId");
		Integer roleId = null;
		if(roleIdStr!=null&&!roleIdStr.trim().isEmpty()) {
			roleId = Integer.valueOf(roleIdStr);
		}
		Object rolePerm = map.get("rolePerm");
		Role role = new Role();
		role.setRoleName(roleName);
		role.setRoleDetail(roleDetail);
		role.setRolePerm(rolePerm.toString());
		role.setRoleId(roleId);
		//调用下层方法执行修改
		resultBean=roleService.modifyRole(role);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/***
	 * 执行角色删除
	 */
	public void delect() {
		getRequestJsonStr();
		RoleIds temp = JsonPluginsUtil.jsonToBean(jsonStr_In,RoleIds.class);
		List<Integer> roleIds = temp.getRoleIds();
		//调用下层方法执行删除
		StringBuffer stringBuffer = new StringBuffer();
		boolean control = true;
		for (Integer roleId : roleIds) {
			resultBean = roleService.delect(roleId);
			if(resultBean.getCode()!=200){
				stringBuffer.append(resultBean.getMsg()+"。");
				control=false;
			}
			
		}
		if(control) {
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);	
		}else {
			ResultBean<Boolean> result = new ResultBean<Boolean>(false);
			result.setCode(401);
			result.setMsg(stringBuffer.toString());
			SendResponseJsonUtil.sendResponseJsonUtil(response, result);
		}
	}
	public void findRole(){
		getRequestMap();
		String currentPageStr = (String) map.get("currentPage");
		Integer currentPage =  null;
		String limitStr = (String) map.get("limit");
		Integer limit = null;
		String roleIdStr = (String) map.get("roleId");
		Integer roleId = null;
		if(currentPageStr!=null&&!currentPageStr.trim().isEmpty()) {
			currentPage=Integer.valueOf(currentPageStr);
		}
		if(limitStr!=null&&!limitStr.trim().isEmpty()) {
			limit=Integer.valueOf(limitStr);
		}
		if(roleIdStr!=null&&!roleIdStr.trim().isEmpty()) {
			roleId=Integer.valueOf(roleIdStr);
		}
		resultBean = roleService.findRole(roleId,currentPage,limit);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	public void downLoad(){
		try{
			   response.setContentType("application/x-execl");  
			   //2.2 设置以下载方式打开文件  
			   response.setHeader("Content-Disposition", "attachment;filename="+new String("角色列表.xls".getBytes(),"ISO-8859-1") ); 
			
			ResultBean<List<RoleByDownLoad>> bean =roleService.findByDownLoad();
			List<RoleByDownLoad> dotorTel=bean.getData();
			OutputStream outputStream = response.getOutputStream();
			System.out.println(dotorTel.toString());
			ExportExcel<RoleByDownLoad> exportExcel  = new ExportExcel<>();
			String[] tableNames = {"角色名称","角色描述","角色号","角色权限"};
			exportExcel.exportExcel("角色导出文档",tableNames,dotorTel, outputStream,"yyyy-MM-dd");
			outputStream.close();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}

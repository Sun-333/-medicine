package cn.cqupt.action;



import java.util.List;

import Modle.AccountIds;
import Modle.findUserCondicion;
import Util.BaseAction;
import Util.JsonPluginsUtil;
import Util.PicktureCheckCodeUtil;
import Util.ResultBean;
import Util.SendResponseJsonUtil;
import Util.UserUtil;
import cn.cqupt.dao.UserDao;
import cn.cqupt.entity.Dep;
import cn.cqupt.entity.Role;
import cn.cqupt.entity.User;
import cn.cqupt.entity.UserToken;
import cn.cqupt.service.UserService;


public class UserAction extends  BaseAction{
	private static final long serialVersionUID = 2303323253703664843L;
		//创建依赖
		UserService userService;
		public void setUserService(UserService userService) {
			this.userService = userService;
		}
		/**
		 * 用户退出
		 */
		public void quit(){
			UserToken user= UserUtil.getUser();
			resultBean  = userService.quite(user);
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}
		/**
		 * 用户登录
		 */
		public void login() {
			//1.首先将接受的json字符串转化为map
			getRequestMap();
			//获取账号名与密码
			String userName = (String)map.get("accountName");
			String userPwd = (String)map.get("accountPwd");
			String code=(String) map.get("code");
			//调用UserService方法执行登录操作
			 resultBean = userService.login(userName,userPwd,code,(String) request.getSession().getAttribute("Code"));
			 request.getSession().removeAttribute("Code");
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}
		/**
		 * 获取验证码
		 */
		public void getCode() {
			PicktureCheckCodeUtil.getPickture(request, response);
		}
		
		/**
		 * 用户获取自己的信息
		 */
		public void findPrivateInf(){
			//得到参数map
			getRequestMap();
			//获取accountId,用于map中存的为String,不能装换成Interger所以需要转化判定
			String accountIdStr=(String) map.get("accountId");
			Integer accountId =null;
			if(accountIdStr==null||accountIdStr.trim().isEmpty()){
				//无操作
			}else {
				 accountId = Integer.valueOf((String) map.get("accountId"));
			}
			//调用下层方法查询
			resultBean = userService.findPerInfo(accountId);
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}
		
		/**
		 * 用户修改自己的信息
		 */
		public void modify() {
			//分装数据到User中
			getRequestJsonStr();
			User user = JsonPluginsUtil.jsonToBean(jsonStr_In, User.class);
			//调用下层方法修改
			resultBean = userService.modify(user);
			//返回结果
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}
		
		/***
		 * 多条件、分页查询账号
		 */
		public void moreCondicion(){
			//获取当前需要查询的条件
			getRequestMap();
				//查询条件
			findUserCondicion findUserCondicion = 
					JsonPluginsUtil.jsonToBean(jsonStr_In, findUserCondicion.class);
			//调用下层方法分页多条件查询
			resultBean = userService.moreCondicion(findUserCondicion);
			//发送结果
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}
		
		public void adminModify() {
			//获取当前修改的参数
			getRequestJsonStr();
			User user = JsonPluginsUtil.jsonToBean(jsonStr_In, User.class);
			Dep dep = JsonPluginsUtil.jsonToBean(jsonStr_In, Dep.class);
			Role role = JsonPluginsUtil.jsonToBean(jsonStr_In, Role.class);
			user.setDep(dep);
			user.setRole(role);
			//调用下层方法中心修改
			resultBean = userService.modifyByAdmin(user);
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}
		
		public void delect() {
			//获取账号Id
			getRequestJsonStr();
			AccountIds accounts =JsonPluginsUtil.jsonToBean(jsonStr_In, AccountIds.class);
			List<Integer> accountIds = accounts.getAccountIds();
			StringBuffer buffer  = new StringBuffer();
			boolean control = true;
			for (Integer accountId : accountIds) {
				//调用下层方法执行删除
				resultBean = userService.delect(accountId);
				if(resultBean.getCode()!=200){
					control=false;
					buffer.append(resultBean.getMsg()+"。");
				}
			}
			if(control) {
				//返回结果
				SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
			}else {
				ResultBean<Boolean> result = new ResultBean<Boolean>(false);
				result.setCode(401);
				result.setMsg(buffer.toString());
				SendResponseJsonUtil.sendResponseJsonUtil(response, result);
			}
		}
		
		/**
		 * 用户修改密码
		 */
		public void modifyPwd() {
			//获取参数map
			getRequestMap();
			String accountIdStr = (String) map.get("accountId");
			Integer accountId=null;
			if(accountIdStr==null||accountIdStr.trim().isEmpty()) {
				//无操作
			}else{
				accountId=Integer.valueOf(accountIdStr);
			}
			String pasPwd=(String) map.get("pasPwd");
			String newPwd=(String) map.get("newPwd");
			String ackPwd=(String) map.get("ackPwd");
			//调用下层方法执行密码修改
			resultBean = userService.modifyPwd(accountId,pasPwd,newPwd,ackPwd);
			//返回结果
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
			
		}
		/**
		 * 管理员添加账号
		 */
		public void addUser(){
			//首先回去新增加的账号信息
			getRequestMap();
			User user = JsonPluginsUtil.jsonToBean(jsonStr_In, User.class);
			Dep dep = JsonPluginsUtil.jsonToBean(jsonStr_In, Dep.class);
			Role role = JsonPluginsUtil.jsonToBean(jsonStr_In, Role.class);
			user.setDep(dep);
			user.setRole(role);
			System.out.println(user.getDep().getDepId());
			//调用下层方法执行账号添加
			resultBean = userService.save(user);
			//返回结果
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}
		public void getByDepId(){
			getRequestMap();
			String depIdStr = (String) map.get("depId");
			Integer depId = null;
			if(!depIdStr.trim().isEmpty()&&depIdStr!=null){
				depId = Integer.valueOf(depIdStr);
			}
			resultBean = userService.findByDepId(depId);
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
		}
}

package cn.cqupt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.annotations.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import Modle.TableValueChangeNull;
import Modle.findUserCondicion;
import Util.CheckUtil;
import Util.JsonPluginsUtil;
import Util.PageBean;
import Util.ParamTool;
import Util.PermissionUtil;
import Util.ResultBean;
import Util.UserUtil;
import cn.cqupt.Exception.NoPermissionException;
import cn.cqupt.dao.DepDao;
import cn.cqupt.dao.PlanDao;
import cn.cqupt.dao.RoleDao;
import cn.cqupt.dao.UserDao;
import cn.cqupt.entity.Dep;
import cn.cqupt.entity.Plan;
import cn.cqupt.entity.Role;
import cn.cqupt.entity.User;
import cn.cqupt.entity.UserToken;
import net.sf.json.JSONObject;
import perm.RolePerm;
import perm.Temp;

@Transactional
public class UserService {
	//配置依赖
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	private UserTokenService userTokenService;
	public void setUserTokenService(UserTokenService userTokenService) {
		this.userTokenService = userTokenService;
	}
	private PlanDao planDao;
	public void setPlanDao(PlanDao planDao) {
		this.planDao = planDao;
	}
	private DepDao depDao;
	public void setDepDao(DepDao depDao) {
		this.depDao = depDao;
	}
	private RoleDao roleDao;
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
		
		/***
		 * 用户登录
		 * @param username
		 * @param userPwd
		 * @param code
		 * @param code_rel 
		 * @return <code>ResultBean</code>返回用户真实姓名、角色权限
		 */
		public ResultBean<Map<String, Object>> login(String username,String userPwd, String code, String code_rel) {
			
			logger.info("用户登录开始,accountName:{},accountPwd:{}",username,userPwd);
			
			/**
			 * 
			 * 验证码校验
		     */
			
			//获取验证码结果
			//String code_rel = (String) ServletActionContext.getRequest().getSession().getAttribute("Code");
			//判断是否首先获取了验证码
			//PermissionUtil.notEmpty(code_rel,"请先获取验证码");
			//将验证码删除
			//ServletActionContext.getRequest().getSession().removeAttribute("Code");
			//判断输入验证码是否匹配
			//CheckUtil.check(code.equals(code_rel),"验证码错误");
			//调用下层方法查询
			User user = userDao.UserLogin(username, userPwd);
			//判断用户名与密码是否输入正确
			Boolean condition=true;
			if(user==null) {
				condition=false;
			} 
			CheckUtil.check(condition,"账号或密码错误");
			//判断此账号是否已经登录
			//Boolean userCondicion = userTokenService.ifHasToken(user.getAccountId().intValue());
			//PermissionUtil.check(!userCondicion,"不能重复登录");
			/**
			 * 构建返回结果集
			 */
			Map<String, Object> map = new LinkedHashMap<>();
			Map<String, Object> user_msg=new LinkedHashMap<>();	
			//token创建
			UserToken userToken = new UserToken();
				//构建查询结果,并返回
				userToken.setAccountId(user.getAccountId());
				userToken.setAccountName(user.getAccountName());
				userToken.setAccountPwd(user.getAccountPwd());
				userToken.setDepId(user.getDep().getDepId());
				userToken.setDepName(user.getDep().getDepName());
				userToken.setRoleId(user.getRole().getRoleId());
				userToken.setRoleName(user.getRole().getRoleName());
				//userToken.setRolePerm(user.getRole().getRolePerm());
				userToken.setUserEmail(user.getUserEmail());
				userToken.setUserId(user.getUserId());
				userToken.setUserName(user.getUserName());
				userToken.setUserTel(user.getUserTel());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE,1);
				userToken.setValidTime(calendar.getTime());
				//创建token
				String tokenStr = UserUtil.setToken(userToken);
				
				//向数据库保存登录用户
				//userTokenService.add(userToken);
				/**
				 * 构建返回结果集
				 */
				map.put("accountId",user.getAccountId());
				user_msg.put("accountName",user.getAccountName());
				user_msg.put("depId",user.getDep().getDepId());
				user_msg.put("depName",user.getDep().getDepName());
				user_msg.put("roleId", user.getRole().getRoleId());
				user_msg.put("roleName",user.getRole().getRoleName());
				//1.封装jsonStr到对应的类中
				String roleStr = user.getRole().getRolePerm();
				//RolePerm rolePerm = JsonPluginsUtil.jsonToBean(roleStr, RolePerm.class);
				user_msg.put("rolePerm",new Temp(user.getRole().getRolePerm()));
				//user_msg.put("rolePerm",rolePerm);
				user_msg.put("userEmail", user.getUserEmail());
				user_msg.put("userId",user.getUserId());
				user_msg.put("userName",user.getUserName());
				user_msg.put("userTel",user.getUserTel());
				user_msg.put("token",tokenStr);
				map.put("user_msg",user_msg);
				//返回结果
			return new ResultBean<Map<String,Object>>(map);
		}
		
		
		/***
		 * 以accountId查询信息
		 * @param accountId
		 * <code>ResultBean</code>
		 */
		public ResultBean<Map> findPerInfo(Integer accountId) {
			/*
			 * 直接调用下层方法查询
			 */
			User user =  userDao.findOne(accountId);
			CheckUtil.notNull(user,"此账号已近不存在");
			/*
			 * 构建返回map
			 */
			Map<String, Object> map = new LinkedHashMap<>();
			//创建返回结果表头
			String[] tableNames= {"账号","姓名","科室","角色","电话","邮箱","工号"};
			map.put("tableNames", tableNames);//加入表头数据
			
			map.put("accountName", ParamTool.getParam("账号",user.getAccountName(),false,true));//账号
			map.put("userName", ParamTool.getParam("姓名",user.getUserName(),false,true));//姓名
			map.put("depName", ParamTool.getParam("科室",user.getDep().getDepName(),false,false));//科室
			map.put("roleName", ParamTool.getParam("角色",user.getRole().getRoleName(),false,false));//账号
			map.put("userTel", ParamTool.getParam("电话", user.getUserTel(),true,true));//电话
			map.put("userEmail", ParamTool.getParam("邮箱",user.getUserEmail(),true,true));//工号
			map.put("accountId",ParamTool.getParam("",user.getAccountId(),false,false));
			/*
			 * 返回结果
			 */
			return new ResultBean<Map>(map);
		}
		
		
		/***
		 * 用户修改自己的信息
		 * @param user
		 * @return <code>ResultBean</code>
		 */
		public ResultBean<Boolean> modify(User user) {
			/**
			 * 参数校验
			 */
			UserUtil.getUser();
			//非空校验
			CheckUtil.notNull(user.getAccountId(),"账号ID为空");
			CheckUtil.notEmpty(user.getAccountName(),"账号为空");
			CheckUtil.notEmpty(user.getUserName(),"用户姓名为空");
			//账号重名校验
			List<User> users = userDao.findAll();
			for (User user_find : users) {
				//当不是本用户账号时比校
				if(!user_find.getAccountId().equals(user.getAccountId())){
					PermissionUtil.check(!user_find.getAccountName().equals(user.getAccountName()),"此账号名已经存在");
				}
			}
			/**
			 * 调用下层方法进行修改
			 */
			//首先获取原始数据
			User user_pas = userDao.findOne(user.getAccountId());
			//更新数据
			user_pas.setAccountName(user.getAccountName());
			user_pas.setUserName(user.getUserName());
			user_pas.setUserEmail(user.getUserEmail());
			user_pas.setUserTel(user.getUserTel());
			user_pas.setUserId(user.getUserId());
			//保存数据
			userDao.update(user_pas);
			//返回结果
			return new ResultBean<Boolean>(true);
		}

		/***
		 * 用户修改自己的密码
		 * @param accountId 
		 * @param pasPwd
		 * @param newPwd
		 * @param ackPwd
		 * @return <code>ResultBean</code>
		 */
		public ResultBean<Boolean> modifyPwd(Integer accountId, String pasPwd,
				String newPwd, String ackPwd) {
			/**
			 * 参数校验
			 */
			UserUtil.getUser();
			//1.非空校验
			CheckUtil.notEmpty(pasPwd,"原始密码不能为空");
			CheckUtil.notEmpty(newPwd,"新密码不能为空");
			CheckUtil.notEmpty(ackPwd,"确认新密码不能为空");
			//2.长度合法性校验
			//3.两次密码是否相同
			CheckUtil.check(newPwd.equals(ackPwd),"两次密码不同");
			//4.原始密码是否匹配
			User user_find = userDao.findOne(accountId);
			CheckUtil.check(user_find.getAccountPwd().equals(pasPwd),"原始密码错误");
			/**
			 * 保存修改密码
			 */
			user_find.setAccountPwd(newPwd);
			userDao.update(user_find);
			//从数据库中删除此token,用户需要重新登录
			//UserToken userToken = userTokenService.findOne(accountId.intValue());
			//userTokenService.delect(userToken);
			//返回结果
			return new ResultBean<Boolean>(true);
		}

		/***
		 * 多条件分页查询账号信息
		 * @param findUserCondicion
		 * @return
		 */
		public ResultBean<PageBean<Map<String,Object>>> moreCondicion(findUserCondicion findUserCondicion) {
			
			/**
			 * 构建pageBean
			 */
			PageBean<Map<String, Object>> pageBean = new PageBean<>();
			//参数合法校验
			CheckUtil.notNull(findUserCondicion.getCurrentPage(),"页数为空");
			CheckUtil.notNull(findUserCondicion.getLimit(),"每页查询数量不能为空");
			//设置查询起始页
			pageBean.setPage(findUserCondicion.getCurrentPage().intValue());
			//设置每页查询数量
			pageBean.setLimit(findUserCondicion.getLimit());
			//获取总数量
			int totalCount = userDao.findMoreCondicionCnt(findUserCondicion);
			pageBean.setTotalCount(totalCount);
			
			/**
			 * 实现分页查询
			 */
			List<User> users = userDao.findMoreCondicionPage(findUserCondicion,
					pageBean.getBegin(),pageBean.getLimit());
			
			/**
			 * 构建返回结果集
			 */
			List<Map<String, Object>> list = new LinkedList<>();
			String[] tableNames = {"账号","姓名","科室","角色","电话","邮箱","工号","密码"};
			pageBean.setTableNames(Arrays.asList(tableNames));
			for (User user : users) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("accountName", new TableValueChangeNull("账号",user.getAccountName(),false,true));
				map.put("userName",new TableValueChangeNull("姓名",user.getUserName(),false,true));
				map.put("depName", new TableValueChangeNull("科室",user.getDep().getDepName(),false,true));
				map.put("roleName", new TableValueChangeNull("角色",user.getRole().getRoleName(),false,true));
				map.put("userTel", new TableValueChangeNull("电话",user.getUserTel(),true,true));
				map.put("userEmail", new TableValueChangeNull("邮箱",user.getUserEmail(),true,true));
				map.put("userId",new TableValueChangeNull("工号",user.getUserId(),true,true));
				map.put("accountPwd",new TableValueChangeNull("密码",user.getAccountPwd(),true,true));
				//附加信息便于后台数据定位
				map.put("accountId",new TableValueChangeNull("",user.getAccountId(),false,false));
				map.put("roleId",new TableValueChangeNull("",user.getRole().getRoleId(),false,true));
				map.put("depId",new TableValueChangeNull("",user.getDep().getDepId(),false,true));
				list.add(map);
			}
			pageBean.setList(list);
			//构建返回结果集
			return new ResultBean<PageBean<Map<String,Object>>>(pageBean);
		}

		/**
		 * 管理员修改用户信息
		 * @param user
		 * @return
		 */
		public ResultBean<Boolean> modifyByAdmin(User user) {
			/**
			 * 参数校验
			 */
			CheckUtil.notNull(user.getAccountId(),"账号ID不能为空");
			CheckUtil.notEmpty(user.getAccountName(),"账号名不能为空");
			CheckUtil.notNull(user.getDep().getDepId(),"科室编号不能为空");
			CheckUtil.notNull(user.getRole().getRoleId(),"角色编号不能为空");
			CheckUtil.notEmpty(user.getAccountPwd(),"密码不能为空");
			//密码校验
			Boolean contro=true;
			String newPwd=user.getAccountPwd();//旧密码
			User user_find = userDao.findOne(user.getAccountId());
			CheckUtil.notNull(user_find,"此账号不存在");
			String pasPwd =user_find.getAccountPwd();
			//如果新密码不为111111且新密码不为原始密码就异常
			if(!"111111".equals(newPwd)&&!newPwd.equals(pasPwd)){
				contro=false;
			}
			CheckUtil.check(contro,"管理员初始化密码只能为111111");
			Dep dep = depDao.findOne(user.getDep().getDepId());
			Role role =roleDao.findOne(user.getRole().getRoleId());
			CheckUtil.notNull(dep,"对应科室已经不存在，请刷新页面重新选择");
			CheckUtil.notNull(role,"对应角色已经不存在，请刷新页面重新选择");
			//将修改信息保存到数据库
			User userFind = userDao.findOne(user.getAccountId());
			userFind.setAccountName(user.getAccountName());
			userFind.setAccountPwd(user.getAccountPwd());
			userFind.setUserEmail(user.getUserEmail());
			userFind.setUserId(user.getUserId());
			userFind.setAccountPwd(user.getAccountPwd());
			userFind.setUserName(user.getUserName());
			userFind.setDep(user.getDep());
			userFind.setRole(user.getRole());
			//保存
			userDao.update(userFind);
			//从数据库中删除token,用户信息已被管理员修改需要重新登录
			//UserToken userToken = userTokenService.findOne(user.getAccountId().intValue());
			//userTokenService.delect(userToken);
			//返回结果
			return new ResultBean<Boolean>(true);
		}

		/**
		 * 管理员删除账号
		 * @param accountId
		 * @return
		 */
		public ResultBean<Boolean> delect(Integer accountId) {
			//参数校验
			CheckUtil.notNull(accountId,"账号ID不能为空");
			/**
			 * 删除校验(未完成)
			 */
			//判断用户是否登陆
			UserToken token = userTokenService.findOne(accountId);
			PermissionUtil.check(token==null,"用户已经在线不能删除");
			//判断目标是否还存在
			User user = userDao.findOne(accountId.intValue());
			PermissionUtil.check(user!=null,"该账号已经不存在");
			//判断该账号下是否还存在Plan
			List<Plan> plans = planDao.findByExcuteNuserId(accountId);
			PermissionUtil.check(plans!=null&&plans.size()!=0,"该账号下还存在执行计划不能删除");
			userDao.delete(user);
			return new ResultBean<Boolean>(true);
		}

		/**
		 * 添加账号
		 * @param user
		 * @return
		 */
		public ResultBean<Boolean> save(User user) {
			/**
			 * 参数校验
			 */
			CheckUtil.notEmpty(user.getUserName(),"用户名不能为空");
			CheckUtil.notNull(user.getDep().getDepId(),"科室不能为空");
			CheckUtil.notNull(user.getRole().getRoleId(),"角色不能为空");
			//判断账号名是否重复
			List<User> list = userDao.findAll();
			for (User user_find : list) {
				CheckUtil.check(!user.getAccountName().equals(user_find.getAccountName()),"账号名已存在");
			}
			//设置默认密码
			user.setAccountPwd("111111");
			//执行添加操作
			userDao.add(user);
			return new ResultBean<Boolean>(true);
		}
		/**
		 * 用户退出
		 * @param user
		 * @return
		 */
		public ResultBean<Boolean> quite(UserToken user) {
			Integer accountId = user.getAccountId();
			UserToken token = userTokenService.findOne(accountId);
			CheckUtil.notNull(token,"此账号处于未登录状态");
			userTokenService.delect(token);
			return new ResultBean<Boolean>(true);
		}


		public ResultBean<List<Map<String,Object>>> findByDepId(Integer depId) {
			CheckUtil.notNull(depId,"科室编号不能为空");
			List<User> users = userDao.findByDepId(depId);
			List<Map<String, Object>> list = new ArrayList<>(); 
			for (User user : users) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("accountId",user.getAccountId());
				map.put("userName",user.getUserName());
				list.add(map);
			}
			return new ResultBean<List<Map<String,Object>>>(list);
		}	
}

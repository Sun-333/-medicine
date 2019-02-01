package cn.cqupt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


import Modle.PlanByDownLoad;
import Modle.RoleByDownLoad;
import Modle.TableValueChangeNull;
import Util.CheckUtil;
import Util.PageBean;
import Util.ResultBean;
import cn.cqupt.dao.RoleDao;
import cn.cqupt.dao.UserDao;
import cn.cqupt.entity.Role;
@Transactional
public class RoleService {
	
	private RoleDao roleDao;
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	/**
	 * 获取所有角色名
	 * @return
	 */
	public ResultBean<List<Map<String, Object>>> findAllName() {
		//直接调用下层方法执行查询
		List<Role> roles = roleDao.findAll();
		List<Map<String, Object>> list = new LinkedList<>();
		for (Role role : roles) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("roleId", role.getRoleId());
			map.put("roleName", role.getRoleName());
			list.add(map);
		}
		return new ResultBean<List<Map<String,Object>>>(list);
	}
	/***
	 * 查询角色
	 * @param roleId
	 * @param limit 
	 * @param currentPage 
	 * @return
	 */
	public ResultBean<PageBean<Map<String,Object>>> findRole(Integer roleId, Integer currentPage, Integer limit) {
		//非空校验
		CheckUtil.notNull(limit,"每页显示数量不能为空");
		CheckUtil.notNull(currentPage,"当前显示页数不能为空");
		PageBean<Map<String, Object>> pageBean = new PageBean<>();
		pageBean.setLimit(limit);//每页显示数量
		pageBean.setPage(currentPage);//设置当前显示页数
		int Cnt = roleDao.findByPageCnt(roleId);
		pageBean.setTotalCount(Cnt);
		//查询所有
		List<Role> roles = roleDao.moreCondition(roleId,limit,pageBean.getBegin());
		List<Map<String,Object>> maps = new ArrayList<>();
		String[] tableNames = {"角色名称","角色描述","角色号","角色权限"};
		pageBean.setTableNames(Arrays.asList(tableNames));
		for (Role role : roles) {
			Map<String,Object> map = new LinkedHashMap<>();
			map.put("roleName",new TableValueChangeNull("角色名称",role.getRoleName(),false,true));
			map.put("roleDetail",new TableValueChangeNull("角色描述",role.getRoleDetail(),false, true));
			map.put("roleId", new TableValueChangeNull("角色号",role.getRoleId(),false,false));
			map.put("rolePerm", new TableValueChangeNull("角色权限",role.getRolePerm(),false,true));
			maps.add(map);
		}
		pageBean.setList(maps);
		return new ResultBean<PageBean<Map<String,Object>>>(pageBean);
	}
	/***
	 * 将角色list转化成maplist
	 * @param roles
	 * @return
	 */
	private List<Map<String, Object>> changeRolesToList(List<Role> roles) {
		List<Map<String, Object>> list = new LinkedList<>();
		Map<String, Object> map = new LinkedHashMap<>();
		for (Role role : roles) {
			map=changeRoleToMap(role);
			list.add(map);
		}
		return list;
	}
	/***
	 * 将role转化成map
	 * @param role
	 * @return <code>map</code>
	 */
	private Map<String, Object> changeRoleToMap(Role role) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("roleId", role.getRoleId());
		map.put("roleName", role.getRoleName());
		map.put("roleDetail", role.getRoleDetail());
		map.put("rolePerm", role.getRolePerm());
		return map;
	}
	/***
	 * 添加新角色信息
	 * @param role
	 * @return
	 */
	public ResultBean<Boolean> addRole(Role role) {
		/**
		 * 首先进行参数校验
		 */
		//1.非空校验
		CheckUtil.notEmpty(role.getRoleName(),"角色名不能为空");
		CheckUtil.notEmpty(role.getRolePerm(),"角色权限不能为空");
		CheckUtil.notEmpty(role.getRoleDetail(),"角色描述不能为空");
		//2.角色重名校验
		List<Role> list = roleDao.findAll();
		for (Role role_find : list) {
			CheckUtil.check(!role_find.getRoleName().equals(role.getRoleName()),
					"角色名已存在");
		}
		//添加角色
		roleDao.add(role);
		return new ResultBean<Boolean>(true);
	}
	/***
	 * 修改角色信息
	 * @param role
	 * @return
	 */
	public ResultBean<Boolean> modifyRole(Role role) {
		/**
		 * 参数校验
		 */
		//1.非空校验
		CheckUtil.notNull(role.getRoleId(),"角色Id不能为空");
		CheckUtil.notEmpty(role.getRoleName(),"角色名不能为空");
		CheckUtil.notEmpty(role.getRolePerm(),"角色权限不能为空");
		//2.判断是否存在
		Role role_save = roleDao.findOne(role.getRoleId().intValue());
		CheckUtil.notNull(role_save,"此角色已经不存，请刷新页面");
		//2.重名校验
		List<Role> list = roleDao.findAll();
		for (Role role_find : list) {
			if(!role_find.getRoleId().equals(role.getRoleId())) {
				CheckUtil.check(!role_find.getRoleName().equals(role.getRoleName()),
						"角色名已存在");
			}
		}
		//3.保存修改
		
		
		role_save.setRoleName(role.getRoleName());
		role_save.setRoleDetail(role.getRoleDetail());
		role_save.setRolePerm(role.getRolePerm());
		roleDao.update(role_save);
		return new ResultBean<Boolean>(true);
	}
	public ResultBean<Boolean> delect(Integer roleId) {
		/**
		 * 参数校验
		 */
		CheckUtil.notNull(roleId,"角色ID不能为空");
		//判断该角色是否在存在
		Role role_find = roleDao.findOne(roleId.intValue());
		CheckUtil.notNull(role_find,"roleId"+":"+roleId+"的角色已经不存在");
		//判断该角色下是否还存在账号
		int cnt = userDao.findCnt(roleId.intValue());
		CheckUtil.check(cnt==0,role_find.getRoleName()+":"+"角色下还存在账号不能删除");
		//执行删除
		roleDao.delete(role_find);
		return new ResultBean<Boolean>(true);
	}
	public ResultBean<List<RoleByDownLoad>> findByDownLoad() {
		List<Role> list =roleDao.findAll();
		List<RoleByDownLoad> roleByDownLoads = new ArrayList<>();
		for (Role role : list) {
			RoleByDownLoad roleByDownLoad = new RoleByDownLoad();
			roleByDownLoad.setRoleDetail(role.getRoleDetail());
			roleByDownLoad.setRoleName(role.getRoleName());
			roleByDownLoad.setRolePerm(role.getRolePerm());
			roleByDownLoad.setRoleId(role.getRoleId());
			roleByDownLoads.add(roleByDownLoad);
		}
		
		return new ResultBean<List<RoleByDownLoad>>(roleByDownLoads);
	}
	public ResultBean<Boolean> upLoad(List<Map<String, Object>> maps) {
		for (Map<String, Object> map : maps) {
			Role role = new Role();
			if(map.get("角色名称")!=null) {
				role.setRoleName((String) map.get("角色名称"));
			}
			if(map.get("角色描述")!=null){
				role.setRoleDetail((String) map.get("角色描述"));
			}
			if(map.get("角色权限")!=null){
				role.setRolePerm((String) map.get("角色权限"));
			}
			ResultBean<Boolean> resultBean = addRole(role);
		}
		return new ResultBean<Boolean>(true);
		
	}
	
}

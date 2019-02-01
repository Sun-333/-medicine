package cn.cqupt.dao;

import java.util.ArrayList; 
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;


import Modle.findUserCondicion;
import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.Dep;
import cn.cqupt.entity.Role;
import cn.cqupt.entity.User;

public class UserDaoImp extends BaseDaoImpl<User> implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public User UserLogin(String username, String userPwd) {
		String sql="select user from User user where user.accountName=? and user.accountPwd=?";
		List<User> list = (List<User>)this.getHibernateTemplate().find(sql,username,userPwd );
		if(list==null||list.size()==0){
			System.out.println("Null");
			return null;
		}else{
			 return list.get(0);
		}
	}
	@Override
	public int findCnt(int roleId) {
		System.out.println("执行");
		List<Object> list =  (List<Object>) this.getHibernateTemplate().find("select count(*) from User where roleId=?",roleId);
		if(list!=null&&list.size()!=0) {
			 Object obj = list.get(0);
			 Long lobj = (Long) obj;
			 return lobj.intValue();
		}else {
			return 0;
		}
	}
	@Override
	public int findMoreCondicionCnt(findUserCondicion findUserCondicion) {
		//创建查询模板
		StringBuffer sql=new StringBuffer("select count(*) from User where 1=1");
		List<Object> list =new ArrayList<>();
		//账号名条件
		if(findUserCondicion.getAccountName()!=null&&
				!"".equals(findUserCondicion.getAccountName())) {
			sql.append(" and accountName=?");
			list.add(findUserCondicion.getAccountName());
		}
		//用户名条件
		if(findUserCondicion.getUserName()!=null&&
				!"".equals(findUserCondicion.getUserName())) {
			sql.append(" and userName=?");
			list.add(findUserCondicion.getUserName());
		}
		//部门Id
		if(findUserCondicion.getDepId()!=null) {
			sql.append(" and depId=?");
			list.add(findUserCondicion.getDepId());
		}
		//角色ID
		if(findUserCondicion.getRoleId()!=null){
			sql.append(" and roleId=?");
			list.add(findUserCondicion.getRoleId());
		}
		@SuppressWarnings("unchecked")
		List<Object> answers = (List<Object>) this.getHibernateTemplate().find(sql.toString(), list.toArray());
		if(answers.size()==0) {
			return 0;
		}else {
			Long out =(Long) answers.get(0);
			return out.intValue();
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findMoreCondicionPage(findUserCondicion findUserCondicion, int begin, int limit) {
		//创建示例模板
		StringBuffer sql=new StringBuffer("from User user where 1=1 ");
		List<Object> list =new ArrayList<>();
		//账号名条件
		if(findUserCondicion.getAccountName()!=null&&
				!"".equals(findUserCondicion.getAccountName())) {
			sql.append(" and user.accountName=?");
			list.add(findUserCondicion.getAccountName());
		}
		//用户名条件
		if(findUserCondicion.getUserName()!=null&&
				!"".equals(findUserCondicion.getUserName())) {
			sql.append(" and user.userName=?");
			list.add(findUserCondicion.getUserName());
		}
		//部门Id
		if(findUserCondicion.getDepId()!=null) {
			sql.append(" and user.dep.depId=?");
			list.add(findUserCondicion.getDepId());
		}
		//角色ID
		if(findUserCondicion.getRoleId()!=null){
			sql.append(" and user.role.roleId=?");
			list.add(findUserCondicion.getRoleId());
		}
		//设置排序方式
		sql.append(" order by accountId");
		return this.getHibernateTemplate().execute(new PageHibernateCallback<User>(sql.toString()
				,list.toArray(), begin,limit));
		
	}
	@Override
	public List<User> findByDepId(Integer depId) {
		String sql = "from User user where user.dep.depId=?";
		return (List<User>) this.getHibernateTemplate().find(sql, depId);
	}
}

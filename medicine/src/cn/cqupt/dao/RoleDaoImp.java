package cn.cqupt.dao;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.stylesheets.LinkStyle;

import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.Role;

public class RoleDaoImp  extends BaseDaoImpl<Role> implements RoleDao {

	@Override
	public int findByPageCnt(Integer roleId) {
		StringBuffer sql = new StringBuffer("select count(*) from Role role where 1=1");
		List<Object> list = new ArrayList<>();
		if(roleId!=null) {
			sql.append(" and roleId=?");
			list.add(roleId);
		}
		List<Object> objects = (List<Object>) this.getHibernateTemplate().find(sql.toString(),list.toArray());
		if(objects!=null&&objects.size()!=0){
			Long lobj = (Long) objects.get(0);
			return lobj.intValue();
		}else {
			return 0;
		}
	}

	@Override
	public List<Role> moreCondition(Integer roleId, Integer limit, int begin) {
		StringBuffer buffer = new StringBuffer("from Role role where 1=1");
		List<Object> list = new ArrayList<>();
		if(roleId!=null) {
			buffer.append(" and role.roleId=?");
			list.add(roleId);
		}
		buffer.append(" order by role.roleId");
		return this.getHibernateTemplate().execute(new PageHibernateCallback<>(buffer.toString(),list.toArray(),begin,limit));
	}

}

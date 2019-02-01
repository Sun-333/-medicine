package cn.cqupt.dao;


import java.util.ArrayList;
import java.util.List;

import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.Alarm;
import cn.cqupt.entity.Dep;

public class DepDaoImp extends BaseDaoImpl<Dep> implements DepDao{

	@SuppressWarnings("unchecked")
	@Override
	public int findCnt(Integer depId) {
		List<Object> list = (List<Object>) this.getHibernateTemplate().find("select count(*) from User where depId=?", depId.intValue());
		if(list!=null&&list.size()!=0) {
			//从list中把值得到
			Object object = list.get(0);
			//变为int类型
			Long lobj = (Long) object;
			System.out.println(lobj);
			return lobj.intValue();
		}else {
			System.out.println("执行");
			return 0;
		}
	}

	@Override
	public int findCntByName(String depName) {
		StringBuffer sql =new StringBuffer( "select count(*) from Dep where 1=1");
		List<Object> list = new ArrayList<>();
		if(depName!=null&&!depName.trim().isEmpty()) {
			sql.append(" and depName=?");
			list.add(depName);
		}
		List<Object> objects = (List<Object>) this.getHibernateTemplate().find(sql.toString(),list.toArray());
		if(objects!=null&objects.size()!=0){
			Long loObj =  (Long) objects.get(0);
			return loObj.intValue();
		}else{
			return 0;
		}
	}

	@Override
	public List<Dep> findDepByCondition(String depName, int begin, int limit) {
		System.out.println(depName+"科室");
		System.out.println(begin);
		System.out.println(limit);
		StringBuffer sql =new StringBuffer( "from Dep dep where 1=1");
		List<Object> list = new ArrayList<>();
		if(depName!=null&&!depName.trim().isEmpty()) {
			sql.append(" and dep.depName=?");
			list.add(depName);
		}
		sql.append(" order by dep.depId");
		return  this.getHibernateTemplate().execute(new PageHibernateCallback<Dep>(sql.toString(),
				list.toArray(), begin, limit));
	}

	@Override
	public Dep finByDepName(String string) {
		String sql = "from Dep dep where dep.depName=?";
		List<Dep> list =  (List<Dep>) this.getHibernateTemplate().find(sql, string);
		if(list!=null&&list.size()!=0) {
			return list.get(0);
		}else {
			return null;
		}
	}


}

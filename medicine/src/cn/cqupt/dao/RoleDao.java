package cn.cqupt.dao;

import java.util.List;

import Util.BaseDao;
import cn.cqupt.entity.Role;

public interface RoleDao extends BaseDao<Role>{

	int findByPageCnt(Integer roleId);

	List<Role> moreCondition(Integer roleId, Integer limit, int begin);

}

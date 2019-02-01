package cn.cqupt.dao;

import java.util.List;

import Util.BaseDao;
import cn.cqupt.entity.Dep;

public interface DepDao extends BaseDao<Dep> {

	int findCnt(Integer depId);

	int findCntByName(String depName);

	List<Dep> findDepByCondition(String depName, int begin, int limit);

	Dep finByDepName(String string);


}

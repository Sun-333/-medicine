package cn.cqupt.dao;

import java.util.List;

import Modle.findPasAlam;
import Util.BaseDao;
import cn.cqupt.entity.PasAlarm;

public interface PasAlarmDao extends BaseDao<PasAlarm> {

	int findCntByMoreCondition(findPasAlam findPasAlam);

	List<PasAlarm> findByMoreCondition(int begin, int limit, findPasAlam findPasAlam);

}

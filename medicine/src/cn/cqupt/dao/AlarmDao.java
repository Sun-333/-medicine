package cn.cqupt.dao;

import java.util.List;

import Modle.findAlarmByMoreCondition;
import Util.BaseDao;
import cn.cqupt.entity.Alarm;

public interface AlarmDao extends BaseDao<Alarm>{

	int findCntByMoreCondition(findAlarmByMoreCondition condition);

	List<Alarm> findByMoreCondition(findAlarmByMoreCondition condition, int begin, int limit);

	Alarm findByAlarmId(String alarmId);

}

package cn.cqupt.dao;

import java.util.Date;
import java.util.List;

import Util.BaseDao;
import cn.cqupt.entity.Plan;

public interface PlanDao extends BaseDao<Plan> {

	int findCntByMoreCondition(Plan plan);

	List<Plan> findByMoreCondition(Plan plan, int begin, int limit);
	Plan findByPlanId(String planId);

	int planTypeCnt(Integer patientId, Integer planType, Date planBeginTime);
	List<Plan> findPlanByPatientIdAndType(Integer patientId, Integer planType,Date planBeforeTime);

	List<Plan> findByExcuteNuserId(Integer accountId);

	int findMaxState(Integer integer);
}

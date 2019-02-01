package cn.cqupt.dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.Plan;

public class PlanDaoImp extends BaseDaoImpl<Plan> implements PlanDao {

	
	public int findCntByMoreCondition(Plan plan) {
		StringBuffer sql = new  StringBuffer("select count(*) from Plan plan where 1=1");
		List<Object> list = new ArrayList<>();
		//科室条件
		if(plan.getDep().getDepId()!=null){
			sql.append(" and plan.dep.depId=?");
			list.add(plan.getDep().getDepId());
		}
		//床号条件
		if(plan.getBed().getBedNumber()!=null){
			sql.append(" and plan.bed.bedNumber=?");
			list.add(plan.getBed().getBedNumber());
		}
		//住院号
		if(plan.getPatient().getHospitalId()!=null&&!plan.getPatient().getHospitalId().trim().isEmpty()){
			sql.append(" and plan.patient.hospitalId=?");
			list.add(plan.getPatient().getHospitalId());
		}
		//计划状态
		if(plan.getPlanState()!=null){
			sql.append(" and plan.planState=?");
			list.add(plan.getPlanState());
		}
		//执行护士
		if(plan.getExcuteNurseId()!=null){
			sql.append(" and plan.excuteNurseId=?");
			list.add(plan.getExcuteNurseId());
		}
		//执行查询
		List<Object> objects = (List<Object>) this.getHibernateTemplate().find(sql.toString(), list.toArray());
		if(objects!=null){
			Long lobj= (Long) objects.get(0);
			return lobj.intValue();
		}else{
			return 0;
		}
	}

	@Override
	public List<Plan> findByMoreCondition(Plan plan, int begin, int limit) {
		StringBuffer sql = new  StringBuffer("from Plan plan where 1=1");
		List<Object> list = new ArrayList<>();
		//科室条件
		if(plan.getDep().getDepId()!=null){
			sql.append(" and plan.dep.depId=?");
			list.add(plan.getDep().getDepId());
		}
		//床号条件
		if(plan.getBed().getBedNumber()!=null){
			sql.append(" and plan.bed.bedNumber=?");
			list.add(plan.getBed().getBedNumber());
		}
		//住院号
		if(plan.getPatient().getHospitalId()!=null&&!plan.getPatient().getHospitalId().trim().isEmpty()){
			sql.append(" and plan.patient.hospitalId=?");
			list.add(plan.getPatient().getHospitalId());
		}
		//计划状态
		if(plan.getPlanState()!=null){
			sql.append(" and plan.planState=?");
			list.add(plan.getPlanState());
		}
		//执行护士
		if(plan.getExcuteNurseId()!=null){
			sql.append(" and plan.excuteNurseId=?");
			list.add(plan.getExcuteNurseId());
		}
		//设置排序方式
		sql.append(" order by plan.planId");
		return this.getHibernateTemplate().execute( new PageHibernateCallback<>(sql.toString(), 
				list.toArray(),begin,limit));
	}

	@Override
	public Plan findByPlanId(String planId) {
		String sql = "from Plan where planId=?";
		List<Plan> list = (List<Plan>) this.getHibernateTemplate().find(sql, planId);
		if(list!=null&&list.size()>=1){
			return list.get(0);
		}else {
			return null;
		}
	}

	@Override
	public int planTypeCnt(Integer patientId, Integer planType,Date planBeforeTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String bef = format.format(planBeforeTime)+" "+"00:00:00";
			String end = format.format(planBeforeTime)+" "+"23:59:59";
			Date befDate = format2.parse(bef);
			Date endDate = format2.parse(end);
			String sql = "select count(*) from Plan where patientId=? and planType=? and planBeginTime between ? and ?";
			List<Object> list = (List<Object>) this.getHibernateTemplate().find(sql,patientId,planType,befDate,endDate);
			if(list!=null&&list.size()!=0){
				Long lobj = (long) list.get(0);
				return lobj.intValue();
			}else{
				return 0;	
			}
		}catch(ParseException e){
			e.printStackTrace();
			throw new RuntimeException("日期解析错误");
		}
	}

	@Override
	public List<Plan> findByExcuteNuserId(Integer accountId) {
		String sql = "from Plan where excuteNurseId=?";
		return (List<Plan>) this.getHibernateTemplate().find(sql, accountId);
	}
	
	/**
	 * 查询病人对应计划状态（即所有计划中最大值）
	 */
	@Override
	public int findMaxState(Integer patientId) {
		String sql = " from Plan plan where plan.patient.patientId=?";
		List<Plan> plans = (List<Plan>) this.getHibernateTemplate().find(sql, patientId);
		if(plans!=null){
			int maxState=0;
			for (Plan plan : plans) {
				if(plan.getPlanState()>maxState){
					maxState=plan.getPlanState();
				}
			}
			return maxState;
		}else{
			return 0;
		}
		
	}

	@Override
	public List<Plan> findPlanByPatientIdAndType(Integer patientId, Integer planType,Date planBeforeTime) {
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String bef = format.format(planBeforeTime)+" "+"00:00:00";
			String end = format.format(planBeforeTime)+" "+"23:59:59";
			Date befDate = format2.parse(bef);
			Date endDate = format2.parse(end);
			String sql = "from Plan plan where plan.patient.patientId=? and plan.planType=?  and planBeginTime between ? and ?";
			return (List<Plan>) this.getHibernateTemplate().find(sql,patientId,planType,befDate,endDate);
		}catch(ParseException e){
			e.printStackTrace();
			throw new RuntimeException("日期格式错误");
		}
	}
}

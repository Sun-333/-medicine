package cn.cqupt.dao;

import java.util.ArrayList;
import java.util.List;

import Modle.findAlarmByMoreCondition;
import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.Alarm;

public class AlarmDaoImp extends BaseDaoImpl<Alarm> implements AlarmDao {

	/**
	 * 多条件查询告警计划数量
	 */
	public int findCntByMoreCondition(findAlarmByMoreCondition condition) {
		StringBuffer sql = new StringBuffer("select count(*) from Alarm alarm where 1=1 ");
		List<Object> list = new ArrayList<>();
		//科室条件
		if(condition.getDepId()!=null){
			sql.append(" and alarm.dep.depId=?");
			list.add(condition.getDepId());
		}
		//病床条件
		if(condition.getBedNumber()!=null){
			sql.append(" and alarm.bed.bedNumber=?");
			list.add(condition.getBedNumber());
		}
		//住院号
		if(condition.getHospitalId()!=null&&!condition.getHospitalId().trim().isEmpty()){
			sql.append(" and alarm.patient.hospitalId=?");
			list.add(condition.getHospitalId());
		}
		//病人姓名
		if(condition.getPatientName()!=null&&!condition.getPatientName().trim().isEmpty()){
			sql.append(" and alarm.patient.patientName=?");
			list.add(condition.getPatientName());
		}
		//告警种类
		if(condition.getAlarmType()!=null){
			sql.append(" and alarm.alarmType=?");
			list.add(condition.getAlarmType());
		}
		List<Object> alarms = (List<Object>) this.getHibernateTemplate().find(sql.toString(),list.toArray());
		if(alarms!=null&&alarms.size()!=0){
			Long lobj = (Long) alarms.get(0);
			return lobj.intValue();
		}else{
			return 0;	
		}
	}

	@Override
	public List<Alarm> findByMoreCondition(findAlarmByMoreCondition condition, int begin, int limit) {
		StringBuffer sql = new StringBuffer("select alarm from Alarm alarm where 1=1 ");
		List<Object> list = new ArrayList<>();
		//科室条件
		if(condition.getDepId()!=null){
			sql.append(" and alarm.dep.depId=?");
			list.add(condition.getDepId());
		}
		//病床条件
		if(condition.getBedNumber()!=null){
			sql.append(" and alarm.bed.bedNumber=?");
			list.add(condition.getBedNumber());
		}
		//住院号
		if(condition.getHospitalId()!=null&&!condition.getHospitalId().trim().isEmpty()){
			sql.append(" and alarm.patient.hospitalId=?");
			list.add(condition.getHospitalId());
		}
		//病人姓名
		if(condition.getPatientName()!=null&&!condition.getPatientName().trim().isEmpty()){
			sql.append(" and alarm.patient.patientName=?");
			list.add(condition.getPatientName());
		}
		//告警种类
		if(condition.getAlarmType()!=null){
			sql.append(" and alarm.alarmType=?");
			list.add(condition.getAlarmType());
		}
		//设置排序方式
		sql.append(" order by alarm.alarmId");
		//执行分页查询
		return  this.getHibernateTemplate().execute(new PageHibernateCallback<Alarm>(sql.toString(),
				list.toArray(), begin, limit));
	}
	/**
	 * 以AlarmId查询告警
	 */
	public Alarm findByAlarmId(String alarmId) {
		String sql = "from Alarm where alarmId=?";
		List<Alarm> list =  (List<Alarm>)this.getHibernateTemplate().find(sql,alarmId);
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
}

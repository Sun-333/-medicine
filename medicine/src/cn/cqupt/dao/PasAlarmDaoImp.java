package cn.cqupt.dao;

import java.util.ArrayList;
import java.util.List;

import Modle.findPasAlam;
import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.PasAlarm;

public class PasAlarmDaoImp extends BaseDaoImpl<PasAlarm> implements PasAlarmDao {

	@Override
	public int findCntByMoreCondition(findPasAlam findPasAlam) {
		StringBuffer sql = new StringBuffer("select count(*) from PasAlarm where 1=1");
		List<Object> list = new ArrayList<>();
		//住院号条件
		if(findPasAlam.getHospitalId()!=null&&!findPasAlam.getHospitalId().trim().isEmpty()){
			sql.append(" and hospitalId=?");
			list.add(findPasAlam.getHospitalId());
		}
		//姓名条件
		if(findPasAlam.getPatientName()!=null&&!findPasAlam.getPatientName().trim().isEmpty()){
			sql.append(" and patientName=?");
			list.add(findPasAlam.getPatientName());
		}
		//告警种类
		if(findPasAlam.getHandleStr()!=null&&!findPasAlam.getHandleStr().trim().isEmpty()){
			sql.append(" and alarmType=?");
			list.add(findPasAlam.getAlarmType());
		}
		//恢复原因值
		if(findPasAlam.getHandleStr()!=null&&!findPasAlam.getHandleStr().trim().isEmpty()){
			sql.append(" and handleStr=?");
			list.add(findPasAlam.getHandleStr());
		}
		List<Object> objects = (List<Object>) this.getHibernateTemplate().find(sql.toString(), list.toArray());
		if(objects!=null&&objects.size()!=0){
			Long lobj = (long) objects.get(0);
			return lobj.intValue();
		}else{
			return 0;
		}
	}

	@Override
	public List<PasAlarm> findByMoreCondition(int begin, int limit, findPasAlam findPasAlam) {
		StringBuffer sql = new StringBuffer("select pasAlarm from PasAlarm pasAlarm where 1=1");
		List<Object> list = new ArrayList<>();
		//住院号条件
		if(findPasAlam.getHospitalId()!=null&&!findPasAlam.getHospitalId().trim().isEmpty()){
			sql.append(" and hospitalId=?");
			list.add(findPasAlam.getHospitalId());
		}
		//姓名条件
		if(findPasAlam.getPatientName()!=null&&!findPasAlam.getPatientName().trim().isEmpty()){
			sql.append(" and patientName=?");
			list.add(findPasAlam.getPatientName());
		}
		//告警种类
		if(findPasAlam.getHandleStr()!=null&&!findPasAlam.getHandleStr().trim().isEmpty()){
			sql.append(" and alarmType=?");
			list.add(findPasAlam.getAlarmType());
		}
		//恢复原因值
		if(findPasAlam.getHandleStr()!=null&&!findPasAlam.getHandleStr().trim().isEmpty()){
			sql.append(" and handleStr=?");
			list.add(findPasAlam.getHandleStr());
		}
		//设置排序方式
		sql.append("order by alarmId");
		return this.getHibernateTemplate().execute(new PageHibernateCallback<PasAlarm>(sql.toString(),
				list.toArray(), begin, limit));
		
	}

	

}

package cn.cqupt.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.Bed;
import cn.cqupt.entity.Dep;
import cn.cqupt.entity.Patient;

public class BedDaoImp extends BaseDaoImpl<Bed> implements BedDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Bed> moreCondition(Patient patientCondition) {
		StringBuffer sql  =new StringBuffer(" from Bed bed  where 1=1 ");
		List<Object> list = new ArrayList<>();
		//科室条件
		if(patientCondition.getDep().getDepId()!=null) {
			sql.append(" and bed.dep.depId=?");
			list.add(patientCondition.getDep().getDepId());
		}
		//病人是否高温
		if(patientCondition.getHyperthermia()!=null){
			sql.append(" and bed.patient.hyperthermia=?");
			list.add(patientCondition.getHyperthermia());
		}
		//病人是否为新病人
		if(patientCondition.getNewPatient()!=null){
			sql.append(" and bed.patient.newPatient=?");
			list.add(patientCondition.getNewPatient());
		}
		//病人是否过敏
		if(patientCondition.getAllergic()!=null){
			sql.append(" and bed.patient.allergic=?");
			list.add(patientCondition.getAllergic());
		}
		//病人是否为手术病人
		if(patientCondition.getOperation()!=null){
			sql.append(" and bed.patient.operation=?");
			list.add(patientCondition.getOperation());
		}
		//病人护理级别
		if(patientCondition.getLevel()!=null){
			sql.append(" and bed.patient.level=?");
			list.add(patientCondition.getLevel().intValue());
		}
		/**
		 * 病人的服药状态（未写）
		 */
		//服药状态
		if(patientCondition.getPlanState()!=null){
			sql.append(" and bed.patient.planState=?");
			list.add(patientCondition.getPlanState());
		}
		//设置排序方式
		sql.append(" order by bedNumber");
		return (List<Bed>) this.getHibernateTemplate().find(sql.toString(), list.toArray());

		
	}

	/**
	 * 判断病床是否被使用
	 */
	public Boolean ifUsed(Integer bedId) {
		Bed bed = this.findOne(bedId);
		return bed.getUseState();
		
	}

	/**
	 * 以DepId查询病床
	 */
	public List<Bed> findBedByDepId(Integer depId) {
		String sql = " from Bed where depId=?";
		return (List<Bed>) this.getHibernateTemplate().find(sql, depId);
		
	}

	@Override
	public List<Bed> findAllEmptyBeds(Integer depId) {
		System.out.println(depId);
		String sql = "from Bed where depId=? and useState=false";
		return (List<Bed>) this.getHibernateTemplate().find(sql, depId.intValue());
	}

}

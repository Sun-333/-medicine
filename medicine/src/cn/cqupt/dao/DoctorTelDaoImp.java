package cn.cqupt.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Modle.DoctorTelFind;
import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.DoctorTel;

public class DoctorTelDaoImp extends BaseDaoImpl<DoctorTel> implements DoctorTelDao {
	
	/**
	 * 以patientId查询医嘱
	 */
	@Override
	public List<DoctorTel> findByPatientId(Integer patientId,boolean eatType) {
		String sql = "from DoctorTel doctorTel where doctorTel.patient.patientId=? and doctorTel.eatType=? and ( doctorTel.state=2 or doctorTel.state=3)";
		return (List<DoctorTel>) this.getHibernateTemplate().find(sql,patientId,eatType);
	}

	@Override
	public List<DoctorTel> findState3() {
		String sql = "from DoctorTel doctorTel where doctorTel.state=3";
		return (List<DoctorTel>) this.getHibernateTemplate().find(sql,null);
	}

	@Override
	public Integer findCntByCnt(DoctorTelFind doctorTelFind) {
		StringBuffer sql = new  StringBuffer("select count(*) from DoctorTel doctorTel where 1=1");
		List<Object> list = new ArrayList<>();
		//科室条件
		if(doctorTelFind.getDepId()!=null){
			sql.append(" and doctorTel.patient.dep.depId=?");
			list.add(doctorTelFind.getDepId());
		}
		//床号条件
		if(doctorTelFind.getBedNumber()!=null){
			sql.append(" and doctorTel.patient.bed.bedNumber=?");
			list.add(doctorTelFind.getBedNumber());
		}
		//住院号
		if(doctorTelFind.getHospitalId()!=null&&!doctorTelFind.getHospitalId().trim().isEmpty()){
			sql.append(" and doctorTel.patient.hospitalId=?");
			list.add(doctorTelFind.getHospitalId());
		}
		//计划状态
		if(doctorTelFind.getState()!=null){
			sql.append(" and doctorTel.state=?");
			list.add(doctorTelFind.getState());
		}
		//6.入院时间
		if(doctorTelFind.getAdminTime()!=null&&!doctorTelFind.getAdminTime().trim().isEmpty()){
			String timeBegin = doctorTelFind.getAdminTime().replaceAll("/","-")+" 00:00:00";
			System.out.println(timeBegin);
			String timeEnd = doctorTelFind.getAdminTime().replaceAll("/","-")+" 23:59:59";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql.append(" and doctorTel.patient.admissionTime between ? and ?");
			sql.append(" order by doctorTelId");
			try {
				list.add(format.parse(timeBegin));
				list.add(format.parse(timeEnd));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
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
	public List<DoctorTel> moreCondition(int begin, int limit,DoctorTelFind doctorTelFind) {
		StringBuffer sql = new  StringBuffer("from DoctorTel doctorTel where 1=1");
		List<Object> list = new ArrayList<>();
		//科室条件
		if(doctorTelFind.getDepId()!=null){
			sql.append(" and doctorTel.patient.dep.depId=?");
			list.add(doctorTelFind.getDepId());
		}
		//床号条件
		if(doctorTelFind.getBedNumber()!=null){
			sql.append(" and doctorTel.patient.bed.bedNumber=?");
			list.add(doctorTelFind.getBedNumber());
		}
		//住院号
		if(doctorTelFind.getHospitalId()!=null&&!doctorTelFind.getHospitalId().trim().isEmpty()){
			sql.append(" and doctorTel.patient.hospitalId=?");
			list.add(doctorTelFind.getHospitalId());
		}
		//计划状态
		if(doctorTelFind.getState()!=null){
			sql.append(" and doctorTel.state=?");
			list.add(doctorTelFind.getState());
		}
		//6.入院时间
		if(doctorTelFind.getAdminTime()!=null&&!doctorTelFind.getAdminTime().trim().isEmpty()){
			String timeBegin = doctorTelFind.getAdminTime().replaceAll("/","-")+" 00:00:00";
			String timeEnd = doctorTelFind.getAdminTime().replaceAll("/","-")+" 23:59:59";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql.append(" and doctorTel.patient.admissionTime between ? and ?");
			sql.append(" order by doctorTelId");
			try {
				list.add(format.parse(timeBegin));
				list.add(format.parse(timeEnd));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		//执行查询
		return this.getHibernateTemplate().execute(new PageHibernateCallback<DoctorTel>(sql.toString(),
				list.toArray(), begin, limit));
	}

	@Override
	public List<DoctorTel> findState0() {
		String sql="from DoctorTel doctorTel where doctorTel.state=0";
		return (List<DoctorTel>) this.getHibernateTemplate().find(sql,null);
	}
}

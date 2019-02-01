package cn.cqupt.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Modle.findPatientCondition;
import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.Patient;
import cn.cqupt.entity.User;

public class PatientDaoImp extends BaseDaoImpl<Patient> implements PatientDao {

	@SuppressWarnings("unchecked")
	@Override
	public int findCnt(findPatientCondition condicine) {
		StringBuffer sql = new StringBuffer("select count(*) from Patient patient where 1=1");
		List<Object> params = new ArrayList<>();
		/**
		 * 多条件查询配置
		 */
		//1.科室条件
		if(condicine.getDepId()!=null) {
			sql.append(" and patient.dep.depId=?");
			params.add(condicine.getDepId());
		}
		//2.床位条件
		if(condicine.getBedNumber()!=null) {
			sql.append(" and patient.bed.bedNumber=?");
			params.add(condicine.getBedNumber());
		}
		//3.姓名
		if(condicine.getPatientName()!=null&&!condicine.getPatientName().trim().isEmpty()){
			sql.append(" and patientName=?");
			params.add(condicine.getPatientName());
		}
		//4.住院号
		if(condicine.getHospitalId()!=null&&!condicine.getHospitalId().trim().isEmpty()){
			sql.append(" and hospitalId=?");
			params.add(condicine.getHospitalId());
		}
		//5.护理级别
		if(condicine.getLevel()!=null){
			sql.append(" and level=?");
			params.add(condicine.getLevel());
		}
		//6.入院时间
		if(condicine.getAdmissionTime()!=null&&!condicine.getAdmissionTime().trim().isEmpty()){
			String timeBegin = condicine.getAdmissionTime().replaceAll("/","-")+" 00:00:00";
			System.out.println(timeBegin);
			String timeEnd = condicine.getAdmissionTime().replaceAll("/","-")+" 23:59:59";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql.append(" and patient.admissionTime between ? and ?");
			try {
				params.add(format.parse(timeBegin));
				params.add(format.parse(timeEnd));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		/**
		 * 未完成
		 */
		//8.查询
		List<Object> obj = (List<Object>) this.getHibernateTemplate().find(sql.toString(), params.toArray());
		if(obj.size()!=0&&obj!=null) {
			Long lobj = (Long) obj.get(0);
			return lobj.intValue();
		}else {
			return 0;
		}
	}

	@Override
	public List<Patient> findMoreCondition(findPatientCondition condicine, int begin, int limit) {
		StringBuffer sql = new StringBuffer("select patient from Patient patient where 1=1");
		List<Object> params = new ArrayList<>();
		/**
		 * 多条件查询配置
		 */
		//1.科室条件
		if(condicine.getDepId()!=null) {
			sql.append(" and patient.dep.depId=?");
			params.add(condicine.getDepId());
		}
		//2.床位条件
		if(condicine.getBedNumber()!=null) {
			sql.append(" and patient.bed.bedNumber=?");
			params.add(condicine.getBedNumber());
		}
		//3.姓名
		if(condicine.getPatientName()!=null&&!condicine.getPatientName().trim().isEmpty()){
			sql.append(" and patientName=?");
			params.add(condicine.getPatientName());
		}
		//4.住院号
		if(condicine.getHospitalId()!=null&&!condicine.getHospitalId().trim().isEmpty()){
			sql.append(" and hospitalId=?");
			params.add(condicine.getHospitalId());
		}
		//5.护理级别
		if(condicine.getLevel()!=null){
			sql.append(" and level=?");
			params.add(condicine.getLevel());
		}
		//6.入院时间
		if(condicine.getAdmissionTime()!=null&&!condicine.getAdmissionTime().trim().isEmpty()){
			String timeBegin = condicine.getAdmissionTime().replaceAll("/","-")+" 00:00:00";
			System.out.println(timeBegin);
			String timeEnd = condicine.getAdmissionTime().replaceAll("/","-")+" 23:59:59";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql.append(" and patient.admissionTime between ? and ?");
			try {
				params.add(format.parse(timeBegin));
				params.add(format.parse(timeEnd));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		//设置排序方式
		if(condicine.getSortType().equals("入院时间")){
			sql.append(" order by patient.admissionTime desc");
		}else {
			sql.append(" order by patient.bed.bedNumber");
		}
		return this.getHibernateTemplate().execute(new PageHibernateCallback<Patient>(sql.toString()
				,params.toArray(), begin,limit));
	}

	/**
	 * 查询科室下对应的病人数量
	 */
	public int findCntByDepId(Integer depId) {
		String sql = "select count(*) from Patient where depId=?";
		List<Object> list = (List<Object>) this.getHibernateTemplate().find(sql, depId);
		if(list!=null&&list.size()!=0){
			Long lobj = (Long)list.get(0);
			return lobj.intValue();
		}else{
			return 0;
		}
	}

	/***
	 * 以bedId查询病人编号
	 */
	public Integer findPatientByBedId(Integer bedId) {
		String sql ="select patient.patientId from Patient patient where patient.bed.bedId=?";
		List<Object> list = (List<Object>) this.getHibernateTemplate().find(sql, bedId);
		System.out.println(list.toArray());
		if(list!=null&&list.size()!=0){
			Integer Iobj=(Integer) list.get(0);
			return Iobj;
		}else{
			return null;
		}
	}

	@Override
	public List<Patient> findByHospitalId(String hopitalId) {
		String sql="from Patient patient where patient.hospitalId=?";
		return (List<Patient>) this.getHibernateTemplate().find(sql, hopitalId);
	}
}

package cn.cqupt.dao;


import java.util.List;

import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.TemporaryDoctorTel;

public class TempDoctorTelDaoImp extends BaseDaoImpl<TemporaryDoctorTel> implements TempDoctorTelDao {

	/**
	 * 查询特定病人的草稿医嘱
	 */
	public List<TemporaryDoctorTel> findByPatientId(Integer patientId, Integer limit, int begin) {
		String sql = "from TemporaryDoctorTel temp where temp.patient.patientId=?";
		Object[] object = {patientId};
		List<TemporaryDoctorTel> doctorTels = (List<TemporaryDoctorTel>) this.getHibernateTemplate().execute
				(new PageHibernateCallback<TemporaryDoctorTel>(sql,object,begin, limit));
		return doctorTels;
	}

	@Override
	public int findCnt(Integer patientId) {
		String sql = "select count(*) from TemporaryDoctorTel temp where temp.patient.patientId=?";
		List<Object> obj = (List<Object>) this.getHibernateTemplate().find(sql,patientId);
		if(obj!=null&&obj.size()!=0) {
			Long lobj= (long) obj.get(0);
			return lobj.intValue();
		}
		return 0;
	}

}

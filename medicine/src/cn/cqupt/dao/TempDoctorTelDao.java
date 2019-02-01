package cn.cqupt.dao;

import java.util.List;

import Util.BaseDao;
import cn.cqupt.entity.TemporaryDoctorTel;

public interface TempDoctorTelDao extends BaseDao<TemporaryDoctorTel> {

	List<TemporaryDoctorTel> findByPatientId(Integer patientId, Integer limit, int i);

	int findCnt(Integer patientId);

}

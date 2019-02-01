package cn.cqupt.dao;

import java.util.List;

import Modle.findPatientCondition;
import Util.BaseDao;
import cn.cqupt.entity.Patient;

public interface PatientDao extends BaseDao<Patient> {

	int findCnt(findPatientCondition condicine);
	List<Patient> findMoreCondition(findPatientCondition condicine, int begin, int limit);
	int findCntByDepId(Integer depId);
	Integer findPatientByBedId(Integer bedId);
	List<Patient> findByHospitalId(String hopitalId);
}

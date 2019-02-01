package cn.cqupt.dao;

import java.util.List;

import Util.BaseDao;
import cn.cqupt.entity.Bed;
import cn.cqupt.entity.Patient;


public interface BedDao extends BaseDao<Bed>{

	List<Bed> moreCondition(Patient patientCondition);

	Boolean ifUsed(Integer depId);

	List<Bed> findBedByDepId(Integer depId);

	List<Bed> findAllEmptyBeds(Integer depId);

}

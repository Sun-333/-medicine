package cn.cqupt.dao;

import java.util.List;

import Modle.DoctorTelFind;
import Util.BaseDao;
import cn.cqupt.entity.DoctorTel;

public interface DoctorTelDao extends BaseDao<DoctorTel> {

	List<DoctorTel> findByPatientId(Integer patientId, boolean eatType);

	List<DoctorTel> findState3();

	Integer findCntByCnt(DoctorTelFind doctorTelFind);

	List<DoctorTel> moreCondition(int begin, int limit, DoctorTelFind doctorTelFind);

	List<DoctorTel> findState0();

}

package cn.cqupt.dao;

import java.util.List;

import Modle.FindMedicineInf;
import Util.BaseDao;
import cn.cqupt.entity.MedicineInf;

public interface MedicineInfDao extends BaseDao<MedicineInf>{

	Integer findCntByMoreCondicine(FindMedicineInf findMedicineInf);

	List<MedicineInf> findByMoreCondition(int begin, FindMedicineInf findMedicineInf);

}

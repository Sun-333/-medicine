package cn.cqupt.dao;

import java.util.ArrayList;
import java.util.List;

import Modle.FindMedicineInf;
import Util.BaseDaoImpl;
import Util.PageHibernateCallback;
import cn.cqupt.entity.MedicineInf;

public class MedicineInfDaoImpl extends BaseDaoImpl<MedicineInf> implements MedicineInfDao {

	@Override
	public Integer findCntByMoreCondicine(FindMedicineInf findMedicineInf) {
		StringBuffer sql = new StringBuffer("select count(*) from MedicineInf medicineInf where 1=1");
		List<Object> params = new ArrayList<>();
		
		if(findMedicineInf.getMedicineType()!=null&&!findMedicineInf.getMedicineType().trim().isEmpty()){
			sql.append(" and medicineInf.medicineType=?");
			params.add(findMedicineInf.getMedicineType());
		}
		
		if(findMedicineInf.getMedicineName()!=null&&!findMedicineInf.getMedicineName().trim().isEmpty()) {
			sql.append(" and medicineInf.medicineName Like ?");
			params.add("%"+findMedicineInf.getMedicineName()+"%");
		}
		List<Object> obj =(List<Object>) this.getHibernateTemplate().find(sql.toString(),params.toArray());
		if(obj.size()!=0&&obj!=null) {
			Long lobj = (Long) obj.get(0);
			return lobj.intValue();
		}else{
			return 0;
		}
	}

	@Override
	public List<MedicineInf> findByMoreCondition(int begin, FindMedicineInf findMedicineInf) {
		StringBuffer sql = new StringBuffer("from MedicineInf medicineInf where 1=1");
		List<Object> params = new ArrayList<>();
		
		if(findMedicineInf.getMedicineType()!=null&&!findMedicineInf.getMedicineType().trim().isEmpty()){
			sql.append(" and medicineInf.medicineType=?");
			params.add(findMedicineInf.getMedicineType());
		}
		
		if(findMedicineInf.getMedicineName()!=null&&!findMedicineInf.getMedicineName().trim().isEmpty()) {
			sql.append(" and medicineInf.medicineName Like ?");
			params.add("%"+findMedicineInf.getMedicineName()+"%");
		}
		List<MedicineInf> infs = this.getHibernateTemplate().execute(new PageHibernateCallback<MedicineInf>(sql.toString(), 
				params.toArray(), begin,findMedicineInf.getLimit()));
		return infs;
	}

}

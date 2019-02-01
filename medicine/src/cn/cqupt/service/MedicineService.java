package cn.cqupt.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import Util.ResultBean;
import cn.cqupt.dao.MedicineDao;
import cn.cqupt.entity.Medicine;

@Transactional
public class MedicineService {
	
	MedicineDao medicineDao;

	public void setMedicineDao(MedicineDao medicineDao) {
		this.medicineDao = medicineDao;
	}

	public ResultBean<List<Medicine>> findAll() {
		List<Medicine> medicines = medicineDao.findAll();
		return new ResultBean<List<Medicine>>(medicines);
	}

	public void upLoad(List<Map<String, Object>> maps) {
		for (Map<String, Object> map : maps) {
			Medicine medicine = new Medicine();
			medicine.setMedicineName((String)map.get("药品名称"));
			medicineDao.add(medicine);
		}
		
	}
	
	
}

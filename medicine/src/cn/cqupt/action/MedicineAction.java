package cn.cqupt.action;

import Util.BaseAction;
import Util.SendResponseJsonUtil;
import cn.cqupt.service.MedicineService;

public class MedicineAction  extends BaseAction{

	MedicineService medicineService;

	public void setMedicineService(MedicineService medicineService) {
		this.medicineService = medicineService;
	}
	
	public void findAllMedicine(){
		resultBean = medicineService.findAll();
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
}

package cn.cqupt.action;



import Util.BaseAction;
import cn.cqupt.service.PasPatientService;

public class PasPatientAction extends BaseAction{
	//配置依赖
	PasPatientService pasPatientService;
	public void setPasPatientService(PasPatientService pasPatientService) {
		this.pasPatientService = pasPatientService;
	}
	public void findByMoreCondition(){
	}
}

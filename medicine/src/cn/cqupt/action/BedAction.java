package cn.cqupt.action;
import Util.BaseAction;
import Util.SendResponseJsonUtil;
import cn.cqupt.entity.Bed;
import cn.cqupt.entity.Dep;
import cn.cqupt.service.BedService;

public class BedAction extends BaseAction{
	//创建依赖
	BedService bedService;
	public void setBedService(BedService bedService) {
		this.bedService = bedService;
	}
	public void findAllBedByDepId(){
		//调用下层方法执行查询
		getRequestMap();
		String depIdStr = (String) map.get("depId");
		Integer depId = null;
		if(depIdStr!=null&&!depIdStr.trim().isEmpty()){
			depId=Integer.valueOf(depIdStr);
		}
		resultBean = bedService.findAllName(depId);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	public void findEmptyBeds(){
		//获取depId
		getRequestMap();
		String depIdStr = (String) map.get("depId");
		Integer depId = null;
		if(depIdStr!=null&&!depIdStr.trim().isEmpty()){
			depId=Integer.valueOf(depIdStr);
		}
		//调用下层方法执行查询
		resultBean = bedService.findEmptyBeds(depId);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	public void saveBed(){
		//调用下层方法添加病床
		for(int i=1;i<=50;i++){
			Bed bed = new Bed();
			bed.setBedNumber(i);
			bed.setUseState(false);;
			Dep dep = new Dep();
			dep.setDepId(1);
			bed.setDep(dep);
			bedService.save(bed);
		}
	}
}

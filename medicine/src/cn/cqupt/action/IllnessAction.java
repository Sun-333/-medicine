package cn.cqupt.action;

import Util.BaseAction;
import Util.JsonPluginsUtil;
import Util.SendResponseJsonUtil;
import cn.cqupt.entity.Illness;
import cn.cqupt.service.IllnessService;

public class IllnessAction extends BaseAction{
	IllnessService illnessService;
	public void setIllnessService(IllnessService illnessService) {
		this.illnessService = illnessService;
	}
	
public void add(){
	getRequestJsonStr();
	Illness illness = JsonPluginsUtil.jsonToBean(jsonStr_In, Illness.class);
	resultBean = illnessService.add(illness);
	SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	
	}
public void findAll(){
	resultBean = illnessService.findAll();
	SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
}
public void delect(){
	getRequestJsonStr();
	Illness illness = JsonPluginsUtil.jsonToBean(jsonStr_In,Illness.class);
	resultBean = illnessService.delect(illness);
	SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
}
	
	
}

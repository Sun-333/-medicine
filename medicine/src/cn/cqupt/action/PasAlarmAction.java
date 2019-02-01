package cn.cqupt.action;






import Modle.findPasAlam;
import Util.BaseAction;

import Util.JsonPluginsUtil;
import Util.SendResponseJsonUtil;
import cn.cqupt.service.PasAlarmService;

public class PasAlarmAction extends BaseAction{
	//配置依赖
	PasAlarmService pasAlarmService;
	public void setPasAlarmService(PasAlarmService pasAlarmService) {
		this.pasAlarmService = pasAlarmService;
	}
	/**
	 * 多条件查询历史告警
	 */
	public void findByMoreCondition(){
		getRequestJsonStr();
		findPasAlam findPasAlam = JsonPluginsUtil.jsonToBean(jsonStr_In, findPasAlam.class);
		System.out.println(findPasAlam.toString());
		resultBean = pasAlarmService.findByMoreCondition(findPasAlam);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
}

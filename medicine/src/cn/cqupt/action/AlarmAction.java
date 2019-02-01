package cn.cqupt.action;
import Modle.findAlarmByMoreCondition;
import Util.BaseAction;
import Util.JsonPluginsUtil;
import Util.SendResponseJsonUtil;
import cn.cqupt.service.AlarmService;

public class AlarmAction extends BaseAction{
	//配置依赖
	AlarmService alarmService;
	public void setAlarmService(AlarmService alarmService) {
		this.alarmService = alarmService;
		
	}
	/**
	 * 多条件查询告警
	 */
	public void findByMorcondition(){
		//一键封装查询条件
		getRequestMap();
		findAlarmByMoreCondition condition = JsonPluginsUtil.jsonToBean(jsonStr_In, findAlarmByMoreCondition.class);
		//调用下层方法执行查询
		resultBean = alarmService.findByMoreCondition(condition);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 告警处理
	 */
	public void handle(){
		//获取告警处理参数
		getRequestMap();
		String alarmId=(String) map.get("alarmId");
		String handleAlarmType = (String) map.get("handleStr");
		//调用下层方法处理
		resultBean = alarmService.handleAlarm(alarmId,handleAlarmType);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
}

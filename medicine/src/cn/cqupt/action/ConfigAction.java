package cn.cqupt.action;




import Util.BaseAction;

import Util.JsonPluginsUtil;

import Util.SendResponseJsonUtil;
import cn.cqupt.entity.Configuration;
import cn.cqupt.service.ConfigService;

public class ConfigAction extends BaseAction{
	
	//配置依赖
	ConfigService configService;
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	/**
	 * 设置主页参数
	 */
	public void setHomePage(){
		getRequestJsonStr();
		//封装配置参数
		Configuration configuration = JsonPluginsUtil.jsonToBean(jsonStr_In, Configuration.class);
		//调用下层方法执行保存
		resultBean = configService.saveHomePage(configuration);
		//返回结果
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 获取主页参数
	 */
	public void getHomePage() {
		//直接调用下层方法执行查询
		resultBean = configService.findHomePage();
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 设置告警参数
	 */
	public void setAlarm(){
		//封装告警参数
		getRequestJsonStr();
		Configuration configuration = JsonPluginsUtil.jsonToBean(jsonStr_In, Configuration.class);
		//直接调用下层方法执行查询
		resultBean = configService.setAlarmPage(configuration);
		//返回结果集
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	/**
	 * 得到告警参数
	 */
	@SuppressWarnings("unused")
	public void getAlarm(){
		//直接调用下层方法执行查询
		resultBean = configService.findAlarmPage();
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
}

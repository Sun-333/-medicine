package cn.cqupt.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.transaction.annotation.Transactional;

import Util.CheckUtil;
import Util.ResultBean;
import cn.cqupt.dao.ConfigDao;
import cn.cqupt.entity.Configuration;


public class ConfigService {
	// 配置依赖
	ConfigDao configDao;

	public void setConfigDao(ConfigDao configDao) {
		this.configDao = configDao;
	}

	/**
	 * 保存主页参数
	 * 
	 * @param configuration
	 * @return
	 */
	@Transactional
	public ResultBean<Boolean> saveHomePage(Configuration configuration) {
		/**
		 * 参数校验
		 */
		CheckUtil.notEmpty(configuration.getWheelConf(), "滚轮配置不能为空");
		CheckUtil.notEmpty(configuration.getRefreshConf(), "刷新配置不能为空");
		// 执行修改
		Configuration conf_find = configDao.findOne(1);
		// 设置修改内容
		conf_find.setRefreshConf(configuration.getRefreshConf());
		conf_find.setWheelConf(configuration.getWheelConf());
		// 保存
		configDao.update(conf_find);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 查询主页配置参数
	 * 
	 * @return
	 */
	public ResultBean<Map<String, Object>> findHomePage() {
		// 调用下层方法执行查询
		Configuration conf = configDao.findOne(1);
		// 判断是否为空
		if (conf == null) {
			CheckUtil.check(false, "后台数据库异常,请修改参数ID为1");
		}
		// 构建返回结构集
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("wheelConf", conf.getWheelConf());
		map.put("refreshConf", conf.getRefreshConf());
		// 返回
		return new ResultBean<Map<String, Object>>(map);
	}

	/**
	 * 查询告警配置参数
	 * 
	 * @return
	 */
	public ResultBean<Map<String, Object>> findAlarmPage() {
		// 调用下层方法执行查询
		Configuration config = configDao.findOne(1);
		/**
		 * 意外情况校验
		 */
		if (config == null) {
			CheckUtil.check(false, "后台数据库异常,请修改参数ID为1");
		}
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("morningTime", config.getMorningTime());
		map.put("noonTime", config.getNoonTime());
		map.put("nightTime", config.getNightTime());
		map.put("timeoutDrug", config.getTimeoutDrug());
		map.put("timeoutPlan", config.getTimeoutPlan());
		return new ResultBean<Map<String, Object>>(map);
	}

	/**
	 * 设置告警参数
	 * 
	 * @param configuration
	 * @return
	 */
	@Transactional
	public ResultBean<Boolean> setAlarmPage(Configuration configuration) {
		// 调用下层方法执行查询
		Configuration config_find = configDao.findOne(1);
		// 意外情况判断
		if (config_find == null) {
			CheckUtil.check(false, "后台数据库异常,请修改参数ID为1");
		}
		CheckUtil.notEmpty(configuration.getMorningTime(),"早上参数不能为空");
		CheckUtil.notEmpty(configuration.getNoonTime(),"中午参数不能为空");
		CheckUtil.notEmpty(configuration.getNightTime(), "晚上参数不能为空");
		CheckUtil.check(check(configuration.getMorningTime()),"早上整点错误");
		CheckUtil.check(check(configuration.getNoonTime()),"中午整点错误");
		CheckUtil.check(check(configuration.getNightTime()),"晚上整点错误");

		// 更新参数
		config_find.setMorningTime(configuration.getMorningTime());
		config_find.setNoonTime(configuration.getNoonTime());
		config_find.setNightTime(configuration.getNightTime());
		config_find.setTimeoutDrug(configuration.getTimeoutDrug());
		config_find.setTimeoutPlan(configuration.getTimeoutPlan());
		// 保存
		configDao.update(config_find);
		return new ResultBean<Boolean>(true);
	}

	private boolean check(String str) {
		String[] data = str.split(":");
		if (data.length != 3) {
			return false;
		}
		for (String string : data) {

			if (string.length() != 2) {
				return false;
			} else {
				if (!isInteger(string)) {
					return false;
				}
			}
			
		}
		return true;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
}

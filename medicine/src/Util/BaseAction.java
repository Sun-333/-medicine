package Util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class BaseAction {
	public String jsonStr_In;
	public String jsonStr_out;
	public Map<String, Object> map;
	public ResultBean<?> resultBean;
	public HttpServletRequest request = ServletActionContext.getRequest();
	public HttpServletResponse response = ServletActionContext.getResponse();
	
	/**
	 * 公用方法，从requst中获取参数map
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public void getRequestMap() {
			//获取jsonStr_in
			getRequestJsonStr();
			map = JsonPluginsUtil.jsonToMap(jsonStr_In);
	}
	/**
	 * 公用方法，从request中获取json字符串
	 */
	public void getRequestJsonStr() {
		try {
			jsonStr_In=GetRequestJsonUtils.getRequestJsonString(request);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

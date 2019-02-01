package Util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class SendResponseJsonUtil {
	
	public static void sendResponseJsonUtil(HttpServletResponse response,ResultBean<?> resultBean){
		response.setContentType("application/json;charset=UTF-8");//处理响应编码
		try {
			response.getWriter().write(JsonPluginsUtil.beanToJson(resultBean));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendResponseJsonUtil(HttpServletResponse response,ResultBean<?> resultBean,String[] _nory_changes,Boolean nory){
		response.setContentType("application/json;charset=UTF-8");//处理响应编码
		try {
			response.getWriter().write(JsonPluginsUtil.beanToJson(resultBean, _nory_changes, nory));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void SendResponseJsonUtil(HttpServletResponse response,String res) {
		response.setContentType("application/json;charset=UTF-8");//处理响应编码
		try {
			response.getWriter().write(res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

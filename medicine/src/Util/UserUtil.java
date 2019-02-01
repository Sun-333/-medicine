package Util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.MDC;

import cn.cqupt.Exception.UnloginException;
import cn.cqupt.entity.User;
import cn.cqupt.entity.UserToken;
import io.jsonwebtoken.Claims;


/**
 * 用户工具类
 *
 */
public class UserUtil {

	private final static ThreadLocal<UserToken> tlUser = new ThreadLocal<UserToken>();

	public static final String KEY_USER = "user";

	public static void setUser(UserToken user) {
		tlUser.set(user);

		// 把用户信息放到log4j
		MDC.put(KEY_USER, user.getUserName());
	}
	/**
	 * 如果没有登录，返回null
	 * 
	 * @return
	 */
	public static String getUserIfLogin() {
		return tlUser.get().getUserName();
	}

	/**
	 * 如果没有登录会抛出异常
	 * 
	 * @return
	 */
	public static UserToken getUser() {
		UserToken user = tlUser.get();

		if (user == null) {
			return null;
		}
		return user;
	}
	public static void clearAllUserInfo() {
		tlUser.remove();

		MDC.remove(KEY_USER);
	}
	/***
	 * 获取用户的登录信息
	 * @param request
	 * @return
	 * @throws RuntimeException
	 */
	 private static Map<String, Object> getClientLoginInfo(HttpServletRequest request){
	        Map<String, Object> r = new HashMap<>();
	        //String sessionId = request.getHeader("sessionId");
	        String sessionId =request.getParameter("sessionId");
	        if (sessionId != null) {
	            r = decodeSession(sessionId);
	            return r;
	        }else{
	        	return null;
	        }
	    }
	 /**
	  * 得到sub的map结构
	  * @param request
	  * @return
	  */
	 public static UserToken subTouserToken(HttpServletRequest request){
		 Map<String, Object> map =  getClientLoginInfo(request);
		 if(map==null){
			 return null;
		 }else{
			String jsonStr = (String) map.get("sub");
			UserToken user = JsonPluginsUtil.jsonToBean(jsonStr, UserToken.class);
			return user;
		 }
	 }
	 /**
	  * 从token中获取accountId
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 public static Integer getAccountId(HttpServletRequest request) {
	        return  subTouserToken(request).getAccountId();
	    }
	 /**
	  * 获取账号名称
	  * @param request
	  * @return
	  */
	 public static String getAccountName(HttpServletRequest request) {
	        return subTouserToken(request).getAccountName();
	    }
	 /**
	  * 解析token为map
	  * @param sessionId
	  * @return
	  */
	 public static Map<String, Object> decodeSession(String sessionId) {
	        try {
	        	JavaWebToken token = new JavaWebToken();
	            return token.parseJWT(sessionId);
	        } catch (Exception e) {
	            return null;
	        }
	   }
	 /**
	  * 以cookie形式发送token
	  * @param response
	  * @param user
	  */
	 public static String setToken(UserToken user){
		 //1.将user转化为subject
		 JavaWebToken tokenUtil = new JavaWebToken();
		 String subject = JavaWebToken.generalSubject(user);
		 String sessionId;
		 try {
			sessionId = tokenUtil.createJWT(Constant.JWT_ID, subject,Constant.JWT_TTL);
			return sessionId;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	 }
}
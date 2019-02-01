package Filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.UserUtil;
import cn.cqupt.entity.UserToken;



/**
 * 用户信息相关的filter
 * 
 * @author json
 *
 */
public class UserFilter implements Filter {
	public FilterConfig filterChain;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	public static boolean isContains(String container, String[] regx) {
		for (int i = 0; i < regx.length; i++) { 
			if (container.indexOf(regx[i]) != -1) { 
				return true;              
			}           
		}           
		return false;       
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		// 得到用户个人相关的信息（登陆的用户，用户的语言）
		fillUserInfo((HttpServletRequest) req);
		HttpServletRequest request = (HttpServletRequest) req;
		request.setCharacterEncoding("UTF-8");
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			chain.doFilter(request, response);
		} finally {
			// 由于tomcat线程重用，记得清空
			clearAllUserInfo();
		}
	}

	private void clearAllUserInfo() {
		UserUtil.clearAllUserInfo();
	}

	private void fillUserInfo(HttpServletRequest request) {
		// 用户信息
		UserToken user = getUserFromSession(request);

		if (user != null) {
			UserUtil.setUser(user);
		}
	}
	

	private UserToken getUserFromSession(HttpServletRequest request) {
		
		UserToken user = UserUtil.subTouserToken(request);
		 if(user==null){
			 return null;
		 }else{
			 return user;
		 }
	}
	@Override
	public void destroy() {

	}

}
package Filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Util.CheckUtil;
import Util.JsonPluginsUtil;
import Util.ResultBean;
import Util.SendResponseJsonUtil;
import Util.UserUtil;
import cn.cqupt.Exception.UnloginException;
import cn.cqupt.dao.UserTokenDao;
import cn.cqupt.entity.User;
import cn.cqupt.entity.UserToken;
import cn.cqupt.service.UserTokenService;

/**
 * 判断用户是否登录,未登录则退出系统
 */
public class LoginFilter implements Filter {

	public FilterConfig config;
	private static UserTokenService userTokenService;
	private static ApplicationContext applicationContext;

	static {
		applicationContext = new ClassPathXmlApplicationContext("bean.xml");
		userTokenService = (UserTokenService) applicationContext.getBean("userTokenService");
	}

	public void destroy() {
		this.config = null;
	}

	public static boolean isContains(String container, String[] regx) {
		boolean result = false;
		for (int i = 0; i < regx.length; i++) {
			if (container.indexOf(regx[i]) != -1) {
				return true;
			}
		}
		return result;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponse hresponse = (HttpServletResponse) response;
		HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response);
		System.out.println("执行登录过滤器");
		String includeStrings = config.getInitParameter("includeStrings"); // 过滤资源后缀参数
		String paramSessionCheckInclude = config.getInitParameter("includeStrings2"); // 过滤资源后缀参数
		String redirectPath = config.getInitParameter("redirectPath");// 没有登陆转向页面
		String disabletestfilter = config.getInitParameter("disabletestfilter");// 过滤器是否有效

		if (disabletestfilter.toUpperCase().equals("Y")) { // 过滤无效
			chain.doFilter(request, response);
			return;
		}

		String[] includeList = includeStrings.split(";");
		if (this.isContains(hrequest.getRequestURI(), includeList)) {// 只对指定过滤参数后缀进行过滤
			System.out.println("未执行过滤");
			chain.doFilter(request, response);
			return;
		}

		String[] includeList2 = paramSessionCheckInclude.split(";");
		if (this.isContains(hrequest.getRequestURI(), includeList2)) {// 对文件下载接口单独做参数校验
			System.out.println("执行文件下载功能,是否登陆校验");
			String sessionId = hrequest.getParameter("sessionId");
			if (sessionId == null || sessionId.trim().isEmpty()) {
				ResultBean<Boolean> resultBean = new ResultBean<>();
				resultBean.setCode(ResultBean.NO_LOGIN);
				resultBean.setMsg("参数sessionId为空");
				SendResponseJsonUtil.sendResponseJsonUtil(hresponse, resultBean);
				return;
			} else {
				Map<String, Object> map = UserUtil.decodeSession(sessionId);
				if (map == null) {
					ResultBean<Boolean> resultBean = new ResultBean<>();
					resultBean.setCode(ResultBean.NO_LOGIN);
					resultBean.setMsg("登陆过期");
					SendResponseJsonUtil.sendResponseJsonUtil(hresponse, resultBean);
					return;
				} else {
					String jsonStr = (String) map.get("sub");
					UserToken user = JsonPluginsUtil.jsonToBean(jsonStr, UserToken.class);
					UserUtil.setUser(user);
					chain.doFilter(request, response);
					return;

				}

			}
		}

		if (UserUtil.getUser() == null) {
			ResultBean<Boolean> resultBean = new ResultBean<>();
			resultBean.setCode(ResultBean.NO_LOGIN);
			resultBean.setMsg("未由token或则token过期");
			SendResponseJsonUtil.sendResponseJsonUtil(hresponse, resultBean);
			return;
		}

		/*
		 * //查询数据库中中对应accountId的token UserToken token_find =
		 * userTokenService.findOne(UserUtil.getUser().getAccountId());
		 */
		// 任意一条件不满足都拒绝请求接口
		// *if(token_find==null){
		if (UserUtil.getUser() == null) {
			System.out.println("执行过滤返回");
			ResultBean<Boolean> resultBean = new ResultBean<>();
			resultBean.setData(false);
			resultBean.setCode(ResultBean.NO_LOGIN);
			resultBean.setMsg("用户未登陆，或者登陆失效，请先登录");
			SendResponseJsonUtil.sendResponseJsonUtil(hresponse, resultBean);
			return;
		} else {
			chain.doFilter(request, response);
			return;
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}
}

package cn.cqupt.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import cn.cqupt.entity.UserToken;
import cn.cqupt.service.UserTokenService;

@WebServlet(name="CleanTokenServlet",urlPatterns = "/CleanTokenServlet",loadOnStartup=0)
public class CleanTokenServlet extends HttpServlet implements Runnable{

	private static final long serialVersionUID = -5560017062542405516L;
	ApplicationContext applicationContext;
	UserTokenService userTokenService;
	
	public void init(ServletConfig config){
		startup();
	}
	
	public  void startup(){
		applicationContext = 
				new ClassPathXmlApplicationContext("bean.xml");
		userTokenService = (UserTokenService) applicationContext.getBean("userTokenService");
		new Thread(this).start();
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("执行");
		List<UserToken> tokens = userTokenService.findAll();
		for (UserToken userToken : tokens) {
			if(userToken.getValidTime().before(new Date())){
				userTokenService.delect(userToken);
			}
		}
		}
	}
}
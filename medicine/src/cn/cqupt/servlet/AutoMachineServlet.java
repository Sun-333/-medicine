package cn.cqupt.servlet;



import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Util.ResultBean;
import cn.cqupt.service.AutoMachineService;
import cn.cqupt.service.DoctorTelService;

/*@WebServlet(name = "AutoMachineServlet", urlPatterns = "/AutoMachineServlet", loadOnStartup = 0)*/
public class AutoMachineServlet extends HttpServlet implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(AutoMachineServlet.class);
	private static final long serialVersionUID = -5506409900103311329L;
	
	ApplicationContext applicationContext;
	DoctorTelService doctorTelService;
	AutoMachineService autoMachineService;
	public void init(ServletConfig config) {
		startup();
	}

	public void startup() {
		applicationContext = new ClassPathXmlApplicationContext("bean.xml");
		doctorTelService = (DoctorTelService) applicationContext.getBean("doctorTelService");
		autoMachineService = (AutoMachineService) applicationContext.getBean("autoMachineService");
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {

			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ResultBean<Boolean> resultBean = doctorTelService.autoChange();
			if(!resultBean.getData()) {
				logger.info("医嘱管理状态机失败");
			}
			autoMachineService.autoMachine();
		}
	}
}

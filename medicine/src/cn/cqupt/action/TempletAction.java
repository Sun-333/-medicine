package cn.cqupt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import Util.BaseAction;
import Util.ResultBean;
import Util.SendResponseJsonUtil;

public class TempletAction extends BaseAction {
	
   
    public void downLoad() {
    	try {
    		String filename = request.getParameter("path");
    		// GET请求中，参数中包含中文需要自己动手来转换。
    		// 当然如果你使用了“全局编码过滤器”，那么这里就不用处理了
    		//filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
    		System.out.println(filename);
    		//String filepath =  ServletActionContext.getServletContext().getRealPath("/downLoad"+filename);
    		//String basePath = "C:\\Users\\Jason\\Desktop\\download";
    		String basePath = "C:\\Users\\Administrator\\Desktop\\downLoad";
    		String filepath = basePath+"\\"+filename;
    		System.out.println(filepath);
    		File file = new File(filepath);
    		if(!file.exists()) {
    			ResultBean<Boolean> resultBean = new ResultBean<>();
				resultBean.setCode(ResultBean.CHECK_FAIL);
				resultBean.setMsg("文件不存在");
				SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
    			return;
    		}
    		// 所有浏览器都会使用本地编码，即中文操作系统使用GBK
    		// 浏览器收到这个文件名后，会使用iso-8859-1来解码
    		filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
    		response.addHeader("content-disposition", "attachment;filename=" + filename);
    		IOUtils.copy(new FileInputStream(file), response.getOutputStream());
    	}catch(Exception e){
    		throw new RuntimeException(e);
    	}
    	
    }

}
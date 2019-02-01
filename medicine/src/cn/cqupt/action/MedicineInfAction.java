package cn.cqupt.action;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;

import Modle.FindMedicineInf;
import Util.BaseAction;
import Util.JsonPluginsUtil;
import Util.SendResponseJsonUtil;
import cn.cqupt.service.MedicineInfService;
import cn.itcast.commons.CommonUtils;

public class MedicineInfAction extends BaseAction{
	MedicineInfService medicineInfService;
	public void setMedicineInfService(MedicineInfService medicineInfService) {
		this.medicineInfService = medicineInfService;
	}
	
	public void findByCondicine(){
		getRequestJsonStr();
		FindMedicineInf findMedicineInf = JsonPluginsUtil.jsonToBean(jsonStr_In,FindMedicineInf.class);
		resultBean = medicineInfService.findByPageBean(findMedicineInf);
		SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
	}
	
	public void upLoad() {
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(dfif);
		// 设置上传的单个文件的上限为10KB
		fileUpload.setFileSizeMax(1024 * 100);
		try {
			List<FileItem> list = fileUpload.parseRequest(request);
			System.out.println(list.size());
			// 获取第二个表单项，因为第一个表单项是username，第二个才是file表单项
			FileItem fileItem = list.get(0);
			String name = fileItem.getName();// 获取文件名称
			// 如果客户端使用的是IE6，那么需要从完整路径中获取文件名称
			int lastIndex = name.lastIndexOf("\\");
			if (lastIndex != -1) {
				name = name.substring(lastIndex + 1);
			}

			// 获取上传文件的保存目录
			String savepath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload");
			System.out.println(savepath);
			String uuid = CommonUtils.uuid();// 生成uuid
			String filename = uuid + "_" + name;// 新的文件名称为uuid + 下划线 + 原始名称

			int hCode = name.hashCode();// 获取文件名的hashCode
			// 获取hCode的低4位，并转换成16进制字符串
			String dir1 = Integer.toHexString(hCode & 0xF);
			// 获取hCode的低5~8位，并转换成16进制字符串
			String dir2 = Integer.toHexString(hCode >>> 4 & 0xF);
			// 与文件保存目录连接成完整路径
			savepath = savepath + "/" + dir1 + "/" + dir2;
			// 因为这个路径可能不存在，所以创建成File对象，再创建目录链，确保目录在保存文件之前已经存在
			new File(savepath).mkdirs();

			// 创建file对象，下面会把上传文件保存到这个file指定的路径
			// savepath，即上传文件的保存目录
			// filename，文件名称
			File file = new File(savepath, filename);

			// 保存文件
			fileItem.write(file);
		} catch (Exception e) {
			// 判断抛出的异常的类型是否为FileUploadBase.FileSizeLimitExceededException
			// 如果是，说明上传文件时超出了限制。
			if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
				e.printStackTrace();
			}
		}
	}
}

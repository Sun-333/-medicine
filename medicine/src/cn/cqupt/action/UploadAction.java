package cn.cqupt.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

import Util.ExcelUtil;
import Util.JsonPluginsUtil;
import Util.ResultBean;
import Util.SendResponseJsonUtil;
import Util.UserUtil;
import cn.cqupt.entity.Medicine;
import cn.cqupt.entity.UserToken;
import cn.cqupt.service.DepService;
import cn.cqupt.service.DoctorTelService;
import cn.cqupt.service.IllnessService;
import cn.cqupt.service.MedicineInfService;
import cn.cqupt.service.MedicineService;
import cn.cqupt.service.PatientService;
import cn.cqupt.service.PlanService;
import cn.cqupt.service.RoleService;
import cn.itcast.commons.CommonUtils;

public class UploadAction extends ActionSupport {

	// 表单上提供的字段
	private File myfile;
	// struts2在文件上传时提供的属性
	private String myfileFileName;// 上传的文件名。上传字段名称+FileName 注意大小写
	private String myfileContentType;// 上传文件的MIME类型。上传字段名称+ContentType 注意大小写
	private String method;
	private String sessionId;
	private MedicineInfService medicineInfService;
	private DepService depService;
	private PatientService patientService;
	private PlanService planService;
	private DoctorTelService doctorTelService;
	private MedicineService medicineService;
	private IllnessService illnessService;
	private RoleService roleService;
	private ResultBean<?> resultBean;
	private HttpServletResponse response = ServletActionContext.getResponse();

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setMedicineInfService(MedicineInfService medicineInfService) {
		this.medicineInfService = medicineInfService;
	}

	public void setDepService(DepService depService) {
		this.depService = depService;
	}

	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}

	public void setPlanService(PlanService planService) {
		this.planService = planService;
	}

	public void setDoctorTelService(DoctorTelService doctorTelService) {
		this.doctorTelService = doctorTelService;
	}

	public void setMedicineService(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	public void setIllnessService(IllnessService illnessService) {
		this.illnessService = illnessService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void upload() {
		try {
			if (sessionId == null || sessionId.trim().isEmpty()) {
				ResultBean<Boolean> resultBean = new ResultBean<>();
				resultBean.setCode(ResultBean.NO_LOGIN);
				resultBean.setMsg("参数sessionId为空");
				SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
				return;
			} else {
				
				Map<String, Object> map = UserUtil.decodeSession(sessionId);
				if (map == null) {
					ResultBean<Boolean> resultBean = new ResultBean<>();
					resultBean.setCode(ResultBean.NO_LOGIN);
					resultBean.setMsg("登陆过期");
					SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
					return;
				} 
				
				String jsonStr = (String) map.get("sub");
				UserToken user = JsonPluginsUtil.jsonToBean(jsonStr, UserToken.class);
				UserUtil.setUser(user);
					
				// 1.拿到ServletContext
				ServletContext servletContext = ServletActionContext.getServletContext();
				// 2.调用realPath方法，获取根据一个虚拟目录得到的真实目录
				String savepath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/upload");
				System.out.println(savepath);
				System.out.println(myfileFileName);
				String uuid = CommonUtils.uuid();// 生成uuid
				String filename = uuid + "_" + myfileFileName;// 新的文件名称为uuid + 下划线 + 原始名称

				/**
				 * 解析表格
				 */
				if (myfileFileName.equals("常见药物列表.xls")) {
					List<Map<String, Object>> map_1 = ExcelUtil.readExcelFileToDB(myfile, "口服药");
					List<Map<String, Object>> map_2 = ExcelUtil.readExcelFileToDB(myfile, "静脉药");
					List<Map<String, Object>> map_3 = ExcelUtil.readExcelFileToDB(myfile, "肌注");
					List<Map<String, Object>> map_4 = ExcelUtil.readExcelFileToDB(myfile, "皮下");
					List<Map<String, Object>> map_5 = ExcelUtil.readExcelFileToDB(myfile, "外用药");
					medicineInfService.upLoad(map_1, "口服药");
					medicineInfService.upLoad(map_2, "静脉药");
					medicineInfService.upLoad(map_3, "肌注");
					medicineInfService.upLoad(map_4, "皮下");
					medicineInfService.upLoad(map_5, "外用药");
				}

				else if (myfileFileName.equals("科室导入表格.xls")) {
					List<Map<String, Object>> maps = ExcelUtil.readExcelFileToDB(myfile, "Sheet1");
					System.out.println(maps.toString());
					resultBean = depService.upLoad(maps);
					SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
				} else if (myfileFileName.equals("角色导入表格.xls")) {
					List<Map<String, Object>> maps = ExcelUtil.readExcelFileToDB(myfile, "Sheet1");
					resultBean = roleService.upLoad(maps);
					SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);

				} else if (myfileFileName.equals("计划导入表格.xls")) {
					List<Map<String, Object>> maps = ExcelUtil.readExcelFileToDB(myfile, "Sheet1");
					resultBean = planService.upLoad(maps);
					SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);

				} else if (myfileFileName.equals("医嘱导入表格.xls")) {
					List<Map<String, Object>> maps = ExcelUtil.readExcelFileToDB(myfile, "Sheet1");
					resultBean = doctorTelService.upLoadDoctorTel(maps);
					SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);

				} else if (myfileFileName.equals("病人导入表格.xls")) {
					List<Map<String, Object>> maps = ExcelUtil.readExcelFileToDB(myfile, "Sheet1");
					resultBean = patientService.upLoad(maps);
					SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
				} else if (myfileFileName.equals("列表药物导入表格.xls")) {
					List<Map<String, Object>> maps = ExcelUtil.readExcelFileToDB(myfile, "Sheet1");
					medicineService.upLoad(maps);
				} else if (myfileFileName.equals("病情导入表格.xls")) {
					List<Map<String, Object>> maps = ExcelUtil.readExcelFileToDB(myfile, "Sheet1");
					illnessService.upLoad(maps);
				}

				int hCode = myfileFileName.hashCode();// 获取文件名的hashCode
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
				// 剪切：辅复制，并且给他重命名。 注意：临时文件没有了
				FileUtils.copyFile(myfile, new File(file, myfileFileName));
			}
		} catch (Exception e) {
			ResultBean<Boolean> resultBean = new ResultBean<>();
			resultBean.setCode(ResultBean.CHECK_FAIL);
			resultBean.setData(false);
			resultBean.setMsg(e.getMessage());
			SendResponseJsonUtil.sendResponseJsonUtil(response, resultBean);
			
		}
	}

	public File getMyfile() {
		return myfile;
	}

	public void setMyfile(File myfile) {
		this.myfile = myfile;
	}

	public String getMyfileFileName() {
		return myfileFileName;
	}

	public void setMyfileFileName(String myfileFileName) {
		this.myfileFileName = myfileFileName;
	}

	public String getMyfileContentType() {
		return myfileContentType;
	}

	public void setMyfileContentType(String myfileContentType) {
		this.myfileContentType = myfileContentType;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
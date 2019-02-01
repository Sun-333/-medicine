package cn.cqupt.action;
import java.io.File;  
  
import org.apache.commons.io.FileUtils;  
import org.apache.struts2.ServletActionContext;  
  
import com.opensymphony.xwork2.ActionContext;  
import com.opensymphony.xwork2.ActionSupport;  
  
public class FileUploadAction extends ActionSupport{  
    private File image;  
    //获得文件的文件名，格式为：nameFileName，这里格式中的name为jsp页面中的name属性  
    private String imageFileName;  
      
    public String getImageFileName() {  
        return imageFileName;  
    }  
    public void setImageFileName(String imageFileName) {  
        this.imageFileName = imageFileName;  
    }  
    public File getImage() {  
        return image;  
    }  
    public void setImage(File image) {  
        this.image = image;  
    }  
    public String addUI(){  
          
        return "success";  
    }  
    public String execute() throws Exception{  
        //获得要存放图片的绝对路径  
        String realpath = ServletActionContext.getServletContext().getRealPath("/images");  
        System.out.println(realpath);  
        //在路径下创建上传的文件  
        File savefile = new File(new File(realpath),imageFileName);  
        if(image!=null){  
            if(!savefile.getParentFile().exists()){  
                //如果路径不存在，则创建一个  
                savefile.getParentFile().mkdirs();  
                //把上传的文件保存在路径中  
                FileUtils.copyFile(image, savefile);  
                ActionContext.getContext().put("message", "上传成功");  
            }  
        }  
          
        return "success";  
    }  
}  
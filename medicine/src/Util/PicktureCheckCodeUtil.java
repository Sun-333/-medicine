package Util;


import java.awt.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PicktureCheckCodeUtil extends HttpServlet {

	public static void getPickture(HttpServletRequest request, HttpServletResponse response)
	
	{

      //禁止页面缓存

      response.setHeader("Pragma", "No-cache");

      response.setHeader("Cache-Control", "No-cache");

      response.setDateHeader("Expires", 0);

      response.setContentType("image/jpeg");        //设置响应正文的MIME类型为图片

      int width=60, height=20;  

      /**创建一个位于缓存中的图像，宽度60，高度20 */  

      BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

      Graphics g = image.getGraphics();           //获取用于处理图形上下文的对象，相当于画笔

      Random random = new Random();        //创建生成随机数的对象

      g.setColor(getRandomColor(200,250));        //设置图像的背景色

      g.fillRect(0, 0, width, height);                 //画一个矩形  ，坐标（0，0），宽度60，高度20 

      g.setFont(new Font("Times New Roman",Font.PLAIN,18));       //设定字体格式

      g.setColor(getRandomColor(160,200));

      for(int i=0;i<130;i++){                         //产生130条随机干扰线

            int x = random.nextInt(width);   

            int y = random.nextInt(height);   

            int xl = random.nextInt(12);   

            int yl = random.nextInt(12);   

            g.drawLine(x,y,x+xl,y+yl);            //在图象的坐标（x,y）和坐标（x+x1,y+y1）之间画干扰线 

      } 

      String strCode="";  

      for (int i=0;i<4;i++){   

            String strNumber=String.valueOf(random.nextInt(10));                                                                                                             strCode=strCode+strNumber;

            //设置字体的颜色

            g.setColor(new Color(15+random.nextInt(120),15+random.nextInt(120),15+random.nextInt(120)));

            g.drawString(strNumber,13*i+6,16);      //将验证码依次画到图像上,坐标（x=13*i+6,y=16）

      }

      request.getSession().setAttribute("Code",strCode);             //把验证码保存到Session中   

      g.dispose();                                        //释放此图像的上下文以及它使用的所有系统资源
      
      try {
    	  
      ImageIO.write(image, "JPEG", response.getOutputStream());      //输出JPEG格式的图像    

      response.getOutputStream().flush();                          //刷新输出流 

      response.getOutputStream().close();                          //关闭输出流 
      }catch(Exception e) {
    	  throw new RuntimeException("发送验证码图片出错");
      }
}

	 public static  Color getRandomColor(int fc,int bc){

         Random random = new Random();

         Color randomColor = null;

         if(fc>255) fc=255;

         if(bc>255) bc=255;

         //设置个0-255之间的随机颜色值

         int r=fc+random.nextInt(bc-fc);

         int g=fc+random.nextInt(bc-fc);

         int b=fc+random.nextInt(bc-fc);

         randomColor = new Color(r,g,b);

         return randomColor;//返回具有指定红色、绿色和蓝色值的不透明的sRGB颜色

   }






	

}

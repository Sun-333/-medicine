package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.CheckUtil;
import Util.ExcelUtil;
import Util.JavaWebToken;
import Util.JsonPluginsUtil;
import Util.UserUtil;
import cn.cqupt.entity.Configuration;
import cn.cqupt.entity.UserToken;
import cn.cqupt.service.PlanService;
import io.jsonwebtoken.Claims;

public class Test2 {
	public static void  fun1(){
		UserToken userToken = new UserToken();
		userToken.setAccountId(1);
		userToken.setAccountName("123");
		String string = UserUtil.setToken(userToken);
		System.out.println(string);
	}
	public static void fun2() throws Exception{
		UserToken userToken = new UserToken();
		JavaWebToken webToken = new JavaWebToken();
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MTk4ODI4MTYsInN1YiI6I"
				+ "ntcImFjY291bnRJZFwiOjEsXCJ1c2VyTmFtZVwiOlwi5p2O5ZubXCI"
				+ "sXCJhY2NvdW50TmFtZVwiOlwi5bCP6LGG6LGGMlwiLFwiYWNjb3VudFB3Z"
				+ "FwiOlwiMTIzXCIsXCJ1c2VyRW1haWxcIjpcIjEyMEBxcVwiLFwidXNlclRlbF"
				+ "wiOlwiMTEwXCIsXCJyb2xlTmFtZVwiOlwi5oqk5aOrXCIsXCJyb2xlSWRcIjoxLFw"
				+ "icm9sZVBlcm1cIjpcIuaKpOWjq-iBjOi0o2NvZGVcIixcImRlcElkXCI6MX0iLCJleHA"
				+ "iOjE1MTk4ODY0MTZ9.PViRzQJNL5Q3bXBBCsXRyTAq4d9Y7Z_fjmVCVnUVwOE";
		Claims claims = webToken.parseJWT(jwt);
		String jsonStr = (String) claims.get("sub");
		System.out.println(jsonStr);
		Map<String, Object> map = JsonPluginsUtil.jsonToMap(jsonStr);
		System.out.println(map.get("accountId"));
	}
	public static void main(String[] args) throws Exception {
		fun2();
	}
	@org.junit.Test
	public void fun5() throws ParseException{
		Map<String,Object> map = new HashMap<>();
		map.put("sex","男");
		/*System.out.println(map.get("sex"));*/
	}
	private void  TimeCompare(Configuration configuration, Integer planType, Date planEndTime) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeBefPart =format.format(planEndTime);
		System.out.println(timeBefPart);
			Date mornAlarm = format2.parse(timeBefPart+" "+configuration.getMorningTime());
			Date noonAlarm = format2.parse(timeBefPart+" "+configuration.getNoonTime());
			Date nightAlarm = format2.parse(timeBefPart+" "+configuration.getNightTime());
			if(planType==1){
				CheckUtil.check(mornAlarm.after(planEndTime),"计划截止时间必须在早上服药之前");
			}
			else if(planType==2){
				CheckUtil.check(noonAlarm.after(planEndTime),"计划截止时间必须在中午服药之前");
			}
			else if(planType==3){
				CheckUtil.check(nightAlarm.after(planEndTime),"计划截止时间必须在晚上服药之前");
			}else {
				CheckUtil.check(false,"计划类型必须为：早上/中午/晚上");
			}
		}
	@org.junit.Test
	public void fun10(){
		String time = "2018-04-18 08:00:00";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			System.out.println(format.parse(time));
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void fun11(){
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime());
	}
	@org.junit.Test
	public void fun12() throws Exception{
		/*ExcelUtil excelUtil = new ExcelUtil();
		List<Map<String,Object>> map = excelUtil.readExcelFileToDB("D://测试.xlsx");
		System.out.println(map.toString());*/
		Map<String,Object> map = new HashMap<>();
		map.put("sex","男");
		/*System.out.println(map.get("sex"));*/
	}
}

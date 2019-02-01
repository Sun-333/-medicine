package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Modle.PlanTypeInfo;
import cn.cqupt.Exception.CheckException;
import cn.cqupt.entity.Configuration;

public class PlanUtil {
	public static Map<String,Integer> getHandleCondition(String handleStr){
		String[] strings = handleStr.split("-");
		String planState = strings[0];
		String change2State = strings[1];
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("alarmState",Integer.valueOf(planState));
		map.put("change2State", Integer.valueOf(change2State));
		return map;
	}
	public static String changHandleStr2Reason(String handleStr){
		if(handleStr.equals("5-2")){
			return "发药确认";
		}else if(handleStr.equals("5-3")){
			return "强制关闭";
		}else if(handleStr.equals("4-3")){
			return "服药确认";
		}else{
			return "强制删除";
		}
	}
	public static Integer planType2Number(String planType){
		if(planType.equals("早上")) {
			return Constant.plan_morning;
		}else if(planType.equals("中午")){
			return Constant.plan_noon;
		}else if(planType.equals("晚上")){
			return Constant.plan_night;
		}else{
			return null;
		}
	}
	/**
	 * 获取计划的类型，加入饭前饭后，control为true为饭前，false饭后
	 * @param planType
	 * @param control
	 * @return
	 */
	public static int getPlanType(int planType,boolean control){
		CheckUtil.notNull(control,"控制逻辑不能为空");
		CheckUtil.notNull(planType!=0,"计划类早中晚不能为空");
		if(control){
			return (planType-1)*2+1;
		}else{
			return planType*2;
		}
	}
	/**
	 * 获取计划时间
	 * @param configuration
	 * @param planType
	 * @param beforeTime
	 * @return
	 */
	public static Date getPlanTime(Configuration configuration,int planType,Date beforeTime){
		try{
			SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String beforeStr = format.format(beforeTime);
			System.out.println(beforeStr);
			Date timeMorning = format2.parse(beforeStr+" "+configuration.getMorningTime());
			System.out.println(beforeStr+" "+configuration.getMorningTime());
			Date timeNoon = format2.parse(beforeStr+" "+configuration.getNightTime());
			Date timeNight =format2.parse(beforeStr+" "+configuration.getNightTime()); 
			Calendar calendar = Calendar.getInstance();
			if(planType==1){
				calendar.setTime(timeMorning);
				calendar.add(Calendar.MINUTE,-30);
			}else if(planType==2){
				calendar.setTime(timeMorning);
				calendar.add(Calendar.MINUTE,30);
			}else if(planType==3){
				calendar.setTime(timeNoon);
				calendar.add(Calendar.MINUTE,-30);
			}else if(planType==4){
				calendar.setTime(timeNoon);
				calendar.add(Calendar.MINUTE,30);
			}else if(planType==5){
				calendar.setTime(timeNight);
				calendar.add(Calendar.MINUTE,-30);
			}else if(planType==6){
				calendar.setTime(timeNight);
				calendar.add(Calendar.MINUTE,30);
			}else {
				throw new RuntimeException("计划类型未有匹配");
			}
            return calendar.getTime();
		}catch(ParseException e) {
			throw new RuntimeException(e.toString());
		}
	}
	public static  PlanTypeInfo getPlanTypeInfo(int planType) {
		PlanTypeInfo info = new PlanTypeInfo();
		if(planType%2==0){
			info.setEatType(true);
		}else{
			info.setEatType(false);
		}
		
		if(planType==1||planType==2) {
			info.setTimeName("早上");
		}else if(planType==3||planType==4){
			info.setTimeName("中午");
		}else if(planType==5||planType==6) {
			info.setTimeName("晚上");
		}else {
			throw new RuntimeException("计划类型未匹配");
		}
		return info;
	}
	public static String gethandleStr(String handleStr) {
		if(handleStr.equals("发药确认")) return "5-2";
		else if(handleStr.equals("服药确认")) return "4-3";
		else if(handleStr.equals("强制关闭")) return "5-3";
		else if(handleStr.equals("强制删除")) return "4-3";
		else return null;
	}
	
	public static Boolean changeStr2Bool(String str){
		if(str.equals("是")) {
			return true;
		}else if(str.equals("否")){
			return false;
		}else {
			CheckUtil.check(false,"校验导入不和规范");
			return null;
		}
	}
}

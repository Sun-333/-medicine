package Util;

public class Constant {
	/**
     * jwt
     * 
     */
    public static final String JWT_ID = "jwt";
    public static final String JWT_SECRET = "hong1mu2zhi3ruan4jian5";
    public static final int JWT_TTL = 60*60*1000*12;  //millisecond
    public static final int JWT_REFRESH_INTERVAL = 55*60*1000;  //millisecond
    public static final int JWT_REFRESH_TTL = 12*60*60*1000;  //millisecond
    public static final int plan_morning =1;//早上计划
    public static final int plan_noon =2;//中午计划
    public static final int plan_night=3;//下午计划
    
    public static final String[] medicineInf = {"药瓶名称","商品名","适应症","用法用量","不良反应","禁忌症","注意事项"};
}

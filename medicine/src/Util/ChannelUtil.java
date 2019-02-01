package Util;

public class ChannelUtil {
	private static ThreadLocal<Boolean> beginControl = new ThreadLocal<Boolean>();
	private  static ThreadLocal<Integer> tlGateWayId = new ThreadLocal<Integer>();// 保存网关编号
	private  static ThreadLocal<Integer> tlsendseq = new ThreadLocal<Integer>();//发送序列号
	private  static ThreadLocal<Integer> tlgetSeq = new ThreadLocal<Integer>();//接收序列号
	public void  setBeginControl(boolean control) {
		beginControl.set(control);
	}
	public static boolean getBeginControl(){
		return beginControl.get();
	}
	
	public static void setGateWayId(Integer gateWayId){
		tlGateWayId.set(gateWayId);
	}
	public static Integer getGateWayId(){
		return tlGateWayId.get();
	}
	public static Integer getSendSeq(){
		return tlgetSeq.get();
	}
	public static void setSendSeq(Integer sendSeq){
		tlsendseq.set(sendSeq);
	}
	public static void setGetSeq(Integer getSeq){
		 tlgetSeq.set(getSeq);
	}
	public static Integer getGetSeq(){
		return tlgetSeq.get();
	}
	
}

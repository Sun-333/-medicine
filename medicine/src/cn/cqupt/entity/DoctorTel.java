package cn.cqupt.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorTel {
	private Integer doctorTelId;
	private Date sendTime;
	private String doctorName;
	private String beginTimeStr;
	private Date beginTime;
	private Date endTime;
	private String endTimeStr;
	private Integer state;
	private String medicine;
	private String quantity;
	private String frequence;
	private String detail;
	private Integer excuteTimes;
	private Boolean eatType;
	private Date excuteTime;
	private Integer excuteNurseId;
	private String excuteNurseName;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Patient patient;
	
	public String getBeginTimeStr() {
		return beginTimeStr;
	}
	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public Boolean getEatType() {
		return eatType;
	}
	public void setEatType(Boolean eatType) {
		this.eatType = eatType;
	}
	public Date getBeginTime() {
		if(beginTimeStr!=null&&!beginTimeStr.trim().isEmpty()){
			try {
				return format.parse(beginTimeStr);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}else {
			return beginTime;	
		}
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Integer getExcuteNurseId() {
		return excuteNurseId;
	}
	public void setExcuteNurseId(Integer excuteNurseId) {
		this.excuteNurseId = excuteNurseId;
	}
	public String getExcuteNurseName() {
		return excuteNurseName;
	}
	public void setExcuteNurseName(String excuteNurseName) {
		this.excuteNurseName = excuteNurseName;
	}
	public Date getExcuteTime() {
		return excuteTime;
	}
	public void setExcuteTime(Date excuteTime) {
		this.excuteTime = excuteTime;
	}
	public Integer getExcuteTimes() {
		return excuteTimes;
	}
	public void setExcuteTimes(Integer excuteTimes) {
		this.excuteTimes = excuteTimes;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getFrequence() {
		return frequence;
	}
	public void setFrequence(String frequence) {
		this.frequence = frequence;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Integer getDoctorTelId() {
		return doctorTelId;
	}
	public void setDoctorTelId(Integer doctorTelId) {
		this.doctorTelId = doctorTelId;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public Date getEndTime() {
		if(endTimeStr==null||endTimeStr.trim().isEmpty()) {
			return endTime;
		}else {
			try {
				return format.parse(endTimeStr);
			} catch (ParseException e) {
				throw new RuntimeException("转化失败");
			}
		}
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getState() {
		return state;
	}
	@Override
	public String toString() {
		return "DoctorTel [doctorTelId=" + doctorTelId + ", sendTime=" + sendTime + ", doctorName=" + doctorName
				+ ", beginTimeStr=" + beginTimeStr + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", endTimeStr=" + endTimeStr + ", state=" + state + ", medicine=" + medicine + ", quantity="
				+ quantity + ", frequence=" + frequence + ", detail=" + detail + ", excuteTimes=" + excuteTimes
				+ ", eatType=" + eatType + ", excuteTime=" + excuteTime + ", excuteNurseId=" + excuteNurseId
				+ ", excuteNurseName=" + excuteNurseName + ", format=" + format + ", patient=" + patient + "]";
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public DoctorTel() {
		super();
		// TODO 自动生成的构造函数存根
	}
	
	
	
}

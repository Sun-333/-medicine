package cn.cqupt.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TemporaryDoctorTel {
	private Integer tempId;
	private Date beginTime;
	private String beginTimeStr;
	private String endTimeStr;
	private String medicine;
	private String quantity;
	private String frequence;
	private String doctorName;
	private Date endTime;
	private Boolean eatType;
	private String detail;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Patient patient;
	
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
	public Integer getTempId() {
		return tempId;
	}
	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Date getBeginTime() {
		if(beginTimeStr==null||beginTimeStr.trim().isEmpty()) {
			return beginTime;
		}else {
			try {
				return format.parse(beginTimeStr);
			} catch (ParseException e) {
				throw new RuntimeException("转化失败");
			}
		}
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
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
	public Boolean getEatType() {
		return eatType;
	}
	public void setEatType(Boolean eatType) {
		this.eatType = eatType;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}

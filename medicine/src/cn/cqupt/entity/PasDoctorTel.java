package cn.cqupt.entity;

import java.util.Date;

public class PasDoctorTel {
	Integer pasDoctorTelId;
	Date sendTime;
	String sendDoctor;
	Date beginTime;
	Date endTime;
	Integer state;
	String medicine;
	String quantity;
	String frequence;
	String detail;
	Integer excuteTimes;
	Boolean eatType;
	Date excuteTime;
	Integer excuteNurseId;
	String excuteNurseName;
	String patientName;
	public Integer getPasDoctorTelId() {
		return pasDoctorTelId;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public String getSendDoctor() {
		return sendDoctor;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public Integer getState() {
		return state;
	}
	public String getMedicine() {
		return medicine;
	}
	public String getQuantity() {
		return quantity;
	}
	public String getFrequence() {
		return frequence;
	}
	public String getDetail() {
		return detail;
	}
	public Integer getExcuteTimes() {
		return excuteTimes;
	}
	public Boolean getEatType() {
		return eatType;
	}
	public Date getExcuteTime() {
		return excuteTime;
	}
	public Integer getExcuteNurseId() {
		return excuteNurseId;
	}
	public String getExcuteNurseName() {
		return excuteNurseName;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPasDoctorTelId(Integer pasDoctorTelId) {
		this.pasDoctorTelId = pasDoctorTelId;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public void setSendDoctor(String sendDoctor) {
		this.sendDoctor = sendDoctor;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public void setFrequence(String frequence) {
		this.frequence = frequence;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public void setExcuteTimes(Integer excuteTimes) {
		this.excuteTimes = excuteTimes;
	}
	public void setEatType(Boolean eatType) {
		this.eatType = eatType;
	}
	public void setExcuteTime(Date excuteTime) {
		this.excuteTime = excuteTime;
	}
	public void setExcuteNurseId(Integer excuteNurseId) {
		this.excuteNurseId = excuteNurseId;
	}
	public void setExcuteNurseName(String excuteNurseName) {
		this.excuteNurseName = excuteNurseName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
}

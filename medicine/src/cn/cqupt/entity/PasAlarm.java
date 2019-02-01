package cn.cqupt.entity;

import java.util.Date;

public class PasAlarm {
	String alarmId;
	String hospitalId;
	String planId;
	Integer alarmType;
	String patientName;
	Date  alarmTime;
	String handlePerson;
	Date handleTime;
	String handleReason;
	String depName;
	Integer bedNumber;
	
	@Override
	public String toString() {
		return "PasAlarm [alarmId=" + alarmId + ", hospitalId=" + hospitalId + ", planId=" + planId + ", alarmType="
				+ alarmType + ", patientName=" + patientName + ", alarmTime=" + alarmTime + ", handlePerson="
				+ handlePerson + ", handleTime=" + handleTime + ", handleReason=" + handleReason + ", depName="
				+ depName + ", bedNumber=" + bedNumber + "]";
	}
	public Integer getBedNumber() {
		return bedNumber;
	}
	public void setBedNumber(Integer bedNumber) {
		this.bedNumber = bedNumber;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Integer getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public Date getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getHandlePerson() {
		return handlePerson;
	}
	public void setHandlePerson(String handlePerson) {
		this.handlePerson = handlePerson;
	}
	public Date getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	public String getHandleReason() {
		return handleReason;
	}
	public void setHandleReason(String handleReason) {
		this.handleReason = handleReason;
	}

}

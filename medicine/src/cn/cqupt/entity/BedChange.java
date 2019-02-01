package cn.cqupt.entity;

import java.util.Date;

public class BedChange {
	private Integer bedchangId;
	private Integer patientId;
	private Integer bedId;
	private Integer bedNumber;
	private String excutePersonName;
	private Integer excutePersonId;
	private Date time;
	public Integer getBedchangId() {
		return bedchangId;
	}
	public void setBedchangId(Integer bedchangId) {
		this.bedchangId = bedchangId;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public Integer getBedId() {
		return bedId;
	}
	public void setBedId(Integer bedId) {
		this.bedId = bedId;
	}
	public Integer getBedNumber() {
		return bedNumber;
	}
	public void setBedNumber(Integer bedNumber) {
		this.bedNumber = bedNumber;
	}
	public String getExcutePersonName() {
		return excutePersonName;
	}
	public void setExcutePersonName(String excutePersonName) {
		this.excutePersonName = excutePersonName;
	}
	public Integer getExcutePersonId() {
		return excutePersonId;
	}
	public void setExcutePersonId(Integer excutePersonId) {
		this.excutePersonId = excutePersonId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}

package Modle;

import java.sql.Date;

public class DoctorTelFind {
	private Integer depId;
	private Integer bedNumber;
	private String hospitalId;
	private Integer state;
	private String adminTime;
	public String getAdminTime() {
		return adminTime;
	}
	public void setAdminTime(String adminTime) {
		this.adminTime = adminTime;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public Integer getBedNumber() {
		return bedNumber;
	}
	public void setBedNumber(Integer bedNumber) {
		this.bedNumber = bedNumber;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}

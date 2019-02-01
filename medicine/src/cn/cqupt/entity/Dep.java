package cn.cqupt.entity;

import java.util.Set;

public class Dep {
	private Integer depId;
	private String depName;
	private Integer bedMaxCnt;
	private String depDetail;
	private Set<User> setUser;
	private Set<Bed> setBed;
	private Set<Patient> setPatient;
	private Set<Plan> setPlan;
	private Set<Alarm> setAlarm;
	public Set<Alarm> getSetAlarm() {
		return setAlarm;
	}
	public void setSetAlarm(Set<Alarm> setAlarm) {
		this.setAlarm = setAlarm;
	}
	public Set<Plan> getSetPlan() {
		return setPlan;
	}
	public void setSetPlan(Set<Plan> setPlan) {
		this.setPlan = setPlan;
	}
	public Set<Patient> getSetPatient() {
		return setPatient;
	}
	public void setSetPatient(Set<Patient> setPatient) {
		this.setPatient = setPatient;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public Integer getBedMaxCnt() {
		return bedMaxCnt;
	}
	public void setBedMaxCnt(Integer bedMaxCnt) {
		this.bedMaxCnt = bedMaxCnt;
	}
	public String getDepDetail() {
		return depDetail;
	}
	public void setDepDetail(String depDetail) {
		this.depDetail = depDetail;
	}
	public Set<User> getSetUser() {
		return setUser;
	}
	public void setSetUser(Set<User> setUser) {
		this.setUser = setUser;
	}
	
	public Set<Bed> getSetBed() {
		return setBed;
	}
	public void setSetBed(Set<Bed> setBed) {
		this.setBed = setBed;
	}
	@Override
	public String toString() {
		return "Dep [depId=" + depId + ", depName=" + depName + ", bedMaxCnt=" + bedMaxCnt + ", depDetail=" + depDetail
				+ ", setUser=" + setUser + "]";
	}
	
	
}

package cn.cqupt.entity;

import java.util.Set;

public class Bed {
	private Integer bedId;
	private Integer bedNumber;
	private String caseMac;
	private Boolean useState;
	private Boolean caseSate;
	private Dep dep;
	private Patient patient;
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
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
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
	public String getCaseMac() {
		return caseMac;
	}
	public void setCaseMac(String caseMac) {
		this.caseMac = caseMac;
	}
	public Boolean getUseState() {
		return useState;
	}
	public void setUseState(Boolean useState) {
		this.useState = useState;
	}
	public Boolean getCaseSate() {
		return caseSate;
	}
	public void setCaseSate(Boolean caseSate) {
		this.caseSate = caseSate;
	}
	public Dep getDep() {
		return dep;
	}
	public void setDep(Dep dep) {
		this.dep = dep;
	}
	
	
}

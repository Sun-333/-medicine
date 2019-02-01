package cn.cqupt.entity;

public class Configuration {
	Integer configId;
	String refreshConf;
	String wheelConf;
	String morningTime;
	String nightTime;
	String noonTime;
	Integer timeoutDrug;
	Integer timeoutPlan;
	
	public Integer getTimeoutDrug() {
		return timeoutDrug;
	}
	public Integer getTimeoutPlan() {
		return timeoutPlan;
	}
	public void setTimeoutDrug(Integer timeoutDrug) {
		this.timeoutDrug = timeoutDrug;
	}
	public void setTimeoutPlan(Integer timeoutPlan) {
		this.timeoutPlan = timeoutPlan;
	}
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	public String getRefreshConf() {
		return refreshConf;
	}
	public void setRefreshConf(String refreshConf) {
		this.refreshConf = refreshConf;
	}
	public String getWheelConf() {
		return wheelConf;
	}
	public void setWheelConf(String wheelConf) {
		this.wheelConf = wheelConf;
	}
	public String getMorningTime() {
		return morningTime;
	}
	public void setMorningTime(String morningTime) {
		this.morningTime = morningTime;
	}
	public String getNightTime() {
		return nightTime;
	}
	public void setNightTime(String nightTime) {
		this.nightTime = nightTime;
	}
	public String getNoonTime() {
		return noonTime;
	}
	public void setNoonTime(String noonTime) {
		this.noonTime = noonTime;
	}
	
}

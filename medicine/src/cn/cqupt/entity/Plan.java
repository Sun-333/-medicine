package cn.cqupt.entity;

import java.util.Date;
import java.util.Set;

public class Plan {
	String planId;//计划编号
	Integer planState;//计划状态
	Date planTime;//计划时间
	Date excuteTime;//执行时间
	Integer excuteNurseId;//执行护士账号编号
	String excuteNurseName;
	Integer releaseNurseId;//下发计划护士账号编号
	String releaseNurseName;
	Date planBeginTime;//计划开始时间
	Date planEndTime;//计划结束时间
	Integer planType;//计划类型 早 中 晚
	Dep dep;//计划捆绑科室
	Bed bed;//计划捆绑床号
	Patient patient;//计划捆绑病人
	Set<Alarm> setAlarm;
	public Integer getPlanType() {
		return planType;
	}
	public void setPlanType(Integer planType) {
		this.planType = planType;
	}
	public Set<Alarm> getSetAlarm() {
		return setAlarm;
	}
	public void setSetAlarm(Set<Alarm> setAlarm) {
		this.setAlarm = setAlarm;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Integer getPlanState() {
		return planState;
	}
	public void setPlanState(Integer planState) {
		this.planState = planState;
	}
	public Date getPlanTime() {
		return planTime;
	}
	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}
	public Date getExcuteTime() {
		return excuteTime;
	}
	public void setExcuteTime(Date excuteTime) {
		this.excuteTime = excuteTime;
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
	public Integer getReleaseNurseId() {
		return releaseNurseId;
	}
	public void setReleaseNurseId(Integer releaseNurseId) {
		this.releaseNurseId = releaseNurseId;
	}
	public String getReleaseNurseName() {
		return releaseNurseName;
	}
	public void setReleaseNurseName(String releaseNurseName) {
		this.releaseNurseName = releaseNurseName;
	}
	public Date getPlanBeginTime() {
		return planBeginTime;
	}
	public void setPlanBeginTime(Date planBeginTime) {
		this.planBeginTime = planBeginTime;
	}
	public Date getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}
	public Dep getDep() {
		return dep;
	}
	public void setDep(Dep dep) {
		this.dep = dep;
	}
	public Bed getBed() {
		return bed;
	}
	public void setBed(Bed bed) {
		this.bed = bed;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	@Override
	public String toString() {
		return "Plan [planId=" + planId + ", planState=" + planState + ", planTime=" + planTime + ", excuteTime="
				+ excuteTime + ", excuteNurseId=" + excuteNurseId + ", excuteNurseName=" + excuteNurseName
				+ ", releaseNurseId=" + releaseNurseId + ", releaseNurseName=" + releaseNurseName + ", planBeginTime="
				+ planBeginTime + ", planEndTime=" + planEndTime + ", dep=" + dep + ", bed=" + bed + ", patient="
				+ patient + "]";
	}
	
}

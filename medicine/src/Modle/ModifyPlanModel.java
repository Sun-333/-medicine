package Modle;

import java.util.Date;

public class ModifyPlanModel {
	String planId;//计划编号
	String planBeginTime;//计划开始时间
	String planEndTime;//计划结束时间
	Integer excuteNurseId;//执行护士账号编号
	String excuteNurseName;//计划执行护士
	String timeName;//计划时间
	Boolean eatType;//饭前饭后
	public Boolean getEatType() {
		return eatType;
	}
	public void setEatType(Boolean eatType) {
		this.eatType = eatType;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanBeginTime() {
		return planBeginTime;
	}
	public void setPlanBeginTime(String planBeginTime) {
		this.planBeginTime = planBeginTime;
	}
	public String getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
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
	public String getTimeName() {
		return timeName;
	}
	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}
}

package cn.cqupt.entity;

import java.util.Date;

public class PlanConstant {
	Integer constantId;
	Date planTime;
	Integer times;
	public Integer getConstantId() {
		return constantId;
	}
	public void setConstantId(Integer constantId) {
		this.constantId = constantId;
	}
	public Date getPlanTime() {
		return planTime;
	}
	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	@Override
	public String toString() {
		return "PlanConstant [constantId=" + constantId + ", planTime=" + planTime + ", times=" + times + "]";
	}
	
}

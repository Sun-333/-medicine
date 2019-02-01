package Modle;

import java.util.List;

public class AddPlanModle {
	Integer depId;
	String planBeginTime;
	String planEndTime;
	Integer excuteNurseId;//执行护士账号编号
	String excuteNurseName;
	Boolean beforeEat;
	Boolean afterEat;
	String planTime;
	String timeName;
	List<Integer> beds;
	public String getTimeName() {
		return timeName;
	}
	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
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
	
	public Boolean getBeforeEat() {
		return beforeEat;
	}
	public void setBeforeEat(Boolean beforeEat) {
		this.beforeEat = beforeEat;
	}
	public Boolean getAfterEat() {
		return afterEat;
	}
	public void setAfterEat(Boolean afterEat) {
		this.afterEat = afterEat;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public List<Integer> getBeds() {
		return beds;
	}
	public void setBeds(List<Integer> beds) {
		this.beds = beds;
	}
}

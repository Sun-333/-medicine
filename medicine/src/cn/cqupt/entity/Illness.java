package cn.cqupt.entity;

/**
 * 病情对象
 * @author Jason
 *
 */
public  class Illness {
	private Integer illnessId;
	private String illnessName;
	public Integer getIllnessId() {
		return illnessId;
	}
	public void setIllnessId(Integer illnessId) {
		this.illnessId = illnessId;
	}
	public String getIllnessName() {
		return illnessName;
	}
	public void setIllnessName(String illnessName) {
		this.illnessName = illnessName;
	}
}

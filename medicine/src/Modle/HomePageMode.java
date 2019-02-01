package Modle;

import java.util.List;

public class HomePageMode {
	Integer depId;
	Integer accountId;
	Boolean emptyBed;
	List<String> diseaseType;
	String drugType;
	String nurseType;
	
	@Override
	public String toString() {
		return "HomePageMode [depId=" + depId + ", accountId=" + accountId + ", emptyBed=" + emptyBed + ", diseaseType="
				+ diseaseType + ", drugType=" + drugType + ", nurseType=" + nurseType + "]";
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Boolean getEmptyBed() {
		return emptyBed;
	}
	public void setEmptyBed(Boolean emptyBed) {
		this.emptyBed = emptyBed;
	}
	public List<String> getDiseaseType() {
		return diseaseType;
	}
	public void setDiseaseType(List<String> diseaseType) {
		this.diseaseType = diseaseType;
	}
	
	public String getDrugType() {
		return drugType;
	}
	public void setDrugType(String drugType) {
		this.drugType = drugType;
	}
	public String getNurseType() {
		return nurseType;
	}
	public void setNurseType(String nurseType) {
		this.nurseType = nurseType;
	}
	
}

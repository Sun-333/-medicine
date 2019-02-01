package Modle;

public class findPasAlam {
	private String hospitalId;
	private String patientName;
	private Integer alarmType;
	private String handleStr;
	private Integer limit;
	private Integer currentPage;
	
	@Override
	public String toString() {
		return "findPasAlam [hospitalId=" + hospitalId + ", patientName=" + patientName + ", alarmType=" + alarmType
				+ ", handleStr=" + handleStr + ", limit=" + limit + ", currentPage=" + currentPage + "]";
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public Integer getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	public String getHandleStr() {
		return handleStr;
	}
	public void setHandleStr(String handleStr) {
		this.handleStr = handleStr;
	}
}

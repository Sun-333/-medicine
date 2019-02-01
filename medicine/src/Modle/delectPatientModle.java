package Modle;

import java.util.List;

public class delectPatientModle {
	List<Integer> patientIds;

	public List<Integer> getPatientIds() {
		return patientIds;
	}

	public void setPatientIds(List<Integer> patientIds) {
		this.patientIds = patientIds;
	}

	@Override
	public String toString() {
		return "delectPatientModle [patientIds=" + patientIds + "]";
	}
	
}

package cn.cqupt.entity;

import java.util.Date;
import java.util.Set;

public class Patient {
	//病人基础信息
	Integer patientId;
	String patientName;
	String patientAge;
	String patientSex;
	Date admissionTime;
	String idCardNumber;
	String patientAddress;
	String patientTel;
	//病人特征信息
	String attendingDoctor;
	String hospitalId;
	Dep  dep;
	String diagnose;
	Bed bed;
	Integer level;
	String diet;
	String allergichHistory;
	Boolean newPatient;
	Boolean allergic;
	Boolean hyperthermia;
	Boolean operation;
	//病人医保信息
	String healthCareNumber;
	String healthCareType;
	Integer totalMoney;
	Integer personPayMoney;
	Integer payMoney;
	//病人服药信息
	Integer totalTimes;
	Integer timeoutDisTimes;
	Integer timeoutEatTimes;
	Integer planState;
	Set<Plan> setPlan;
	Set<Alarm> setAlarm;
	Set<DoctorTel> setDoctorTel;
	Set<TemporaryDoctorTel> setTempDoctorTel;
	public Set<TemporaryDoctorTel> getSetTempDoctorTel() {
		return setTempDoctorTel;
	}
	public void setSetTempDoctorTel(Set<TemporaryDoctorTel> setTempDoctorTel) {
		this.setTempDoctorTel = setTempDoctorTel;
	}
	public Set<DoctorTel> getSetDoctorTel() {
		return setDoctorTel;
	}
	public void setSetDoctorTel(Set<DoctorTel> setDoctorTel) {
		this.setDoctorTel = setDoctorTel;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}
	public String getPatientSex() {
		return patientSex;
	}
	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}
	public Date getAdmissionTime() {
		return admissionTime;
	}
	public void setAdmissionTime(Date admissionTime) {
		this.admissionTime = admissionTime;
	}
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	public String getPatientTel() {
		return patientTel;
	}
	public void setPatientTel(String patientTel) {
		this.patientTel = patientTel;
	}
	public String getAttendingDoctor() {
		return attendingDoctor;
	}
	public void setAttendingDoctor(String attendingDoctor) {
		this.attendingDoctor = attendingDoctor;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public Dep getDep() {
		return dep;
	}
	public void setDep(Dep dep) {
		this.dep = dep;
	}
	public String getDiagnose() {
		return diagnose;
	}
	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}
	public Bed getBed() {
		return bed;
	}
	public void setBed(Bed bed) {
		this.bed = bed;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getDiet() {
		return diet;
	}
	public void setDiet(String diet) {
		this.diet = diet;
	}
	public String getAllergichHistory() {
		return allergichHistory;
	}
	public void setAllergichHistory(String allergichHistory) {
		this.allergichHistory = allergichHistory;
	}
	public Boolean getNewPatient() {
		return newPatient;
	}
	public void setNewPatient(Boolean newPatient) {
		this.newPatient = newPatient;
	}
	public Boolean getAllergic() {
		return allergic;
	}
	public void setAllergic(Boolean allergic) {
		this.allergic = allergic;
	}
	public Boolean getHyperthermia() {
		return hyperthermia;
	}
	public void setHyperthermia(Boolean hyperthermia) {
		this.hyperthermia = hyperthermia;
	}
	public Boolean getOperation() {
		return operation;
	}
	public void setOperation(Boolean operation) {
		this.operation = operation;
	}
	public String getHealthCareNumber() {
		return healthCareNumber;
	}
	public void setHealthCareNumber(String healthCareNumber) {
		this.healthCareNumber = healthCareNumber;
	}
	public String getHealthCareType() {
		return healthCareType;
	}
	public void setHealthCareType(String healthCareType) {
		this.healthCareType = healthCareType;
	}
	public Integer getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Integer getPersonPayMoney() {
		return personPayMoney;
	}
	public void setPersonPayMoney(Integer personPayMoney) {
		this.personPayMoney = personPayMoney;
	}
	public Integer getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}
	public Integer getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(Integer totalTimes) {
		this.totalTimes = totalTimes;
	}
	public Integer getTimeoutDisTimes() {
		return timeoutDisTimes;
	}
	public void setTimeoutDisTimes(Integer timeoutDisTimes) {
		this.timeoutDisTimes = timeoutDisTimes;
	}
	public Integer getTimeoutEatTimes() {
		return timeoutEatTimes;
	}
	public void setTimeoutEatTimes(Integer timeoutEatTimes) {
		this.timeoutEatTimes = timeoutEatTimes;
	}
	public Integer getPlanState() {
		return planState;
	}
	/*public Integer getPlanState(){
		int i =0;
		for (Plan plan : setPlan) {
			if(plan.getPlanState()>i){
				i=plan.getPlanState();
			}
		}
		return i;
	}*/
	public void setPlanState(Integer planState) {
		this.planState = planState;
	}
	public Set<Plan> getSetPlan() {
		return setPlan;
	}
	public void setSetPlan(Set<Plan> setPlan) {
		this.setPlan = setPlan;
	}
	public Set<Alarm> getSetAlarm() {
		return setAlarm;
	}
	public void setSetAlarm(Set<Alarm> setAlarm) {
		this.setAlarm = setAlarm;
	}
}

package Modle;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.poi.hssf.record.formula.functions.Frequency;

import cn.cqupt.entity.Patient;

public class DoctorTelDownLoad {
	private int doctorTelId;
	private String hospitalId;
	private String depName;
	private Integer bedNumber;
	private Integer state;
	private String stateTime;
	private String beginTime;
	private String endTime;
	private String excuteNurse;
	private String doctorName;
	private String medicineName;
	private String quantity;
	private String frequnce;
	private String eatType;
	private int excuteTimes;
	private String detail;
	
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public int getDoctorTelId() {
		return doctorTelId;
	}
	public void setDoctorTelId(int doctorTelId) {
		this.doctorTelId = doctorTelId;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public Integer getBedNumber() {
		return bedNumber;
	}
	public void setBedNumber(Integer bedNumber) {
		this.bedNumber = bedNumber;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStateTime() {
		return stateTime;
	}
	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getExcuteNurse() {
		return excuteNurse;
	}
	public void setExcuteNurse(String excuteNurse) {
		this.excuteNurse = excuteNurse;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public String getFrequnce() {
		return frequnce;
	}
	public void setFrequnce(String frequnce) {
		this.frequnce = frequnce;
	}
	public String getEatType() {
		return eatType;
	}
	public void setEatType(String eatType) {
		this.eatType = eatType;
	}
	public int getExcuteTimes() {
		return excuteTimes;
	}
	public void setExcuteTimes(int excuteTimes) {
		this.excuteTimes = excuteTimes;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public DoctorTelDownLoad(int doctorTelId, String hospitalId, String depName, Integer bedNumber, Integer state,
			String stateTime, String beginTime, String endTime, String excuteNurse, String doctorName,
			String medicineName, String frequnce, String eatType, int excuteTimes, String detail) {
		super();
		this.doctorTelId = doctorTelId;
		this.hospitalId = hospitalId;
		this.depName = depName;
		this.bedNumber = bedNumber;
		this.state = state;
		this.stateTime = stateTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.excuteNurse = excuteNurse;
		this.doctorName = doctorName;
		this.medicineName = medicineName;
		this.frequnce = frequnce;
		this.eatType = eatType;
		this.excuteTimes = excuteTimes;
		this.detail = detail;
	}
	public DoctorTelDownLoad() {
		super();
		// TODO 自动生成的构造函数存根
	}
	@Override
	public String toString() {
		return "DoctorTelDownLoad [doctorTelId=" + doctorTelId + ", hospitalId=" + hospitalId + ", depName=" + depName
				+ ", bedNumber=" + bedNumber + ", state=" + state + ", stateTime=" + stateTime + ", beginTime="
				+ beginTime + ", endTime=" + endTime + ", excuteNurse=" + excuteNurse + ", doctorName=" + doctorName
				+ ", medicineName=" + medicineName + ", frequnce=" + frequnce + ", eatType=" + eatType
				+ ", excuteTimes=" + excuteTimes + ", detail=" + detail + "]";
	}
	
	
}

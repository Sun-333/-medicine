package cn.cqupt.entity;

public class MedicineInf {
	private Integer medicineId;
	private String medicineType;
	private String medicineName;
	private String goodsName;
	private String standard;
	private String indications;
	private String useWay;
	private String adverse;
	private String contraindication;
	private String otherInf;
	
	public String getMedicineType() {
		return medicineType;
	}
	public void setMedicineType(String medicineType) {
		this.medicineType = medicineType;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Integer getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(Integer medicineId) {
		this.medicineId = medicineId;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public String getIndications() {
		return indications;
	}
	public void setIndications(String indications) {
		this.indications = indications;
	}
	public String getUseWay() {
		return useWay;
	}
	public void setUseWay(String useWay) {
		this.useWay = useWay;
	}
	public String getAdverse() {
		return adverse;
	}
	public void setAdverse(String adverse) {
		this.adverse = adverse;
	}
	public String getContraindication() {
		return contraindication;
	}
	public void setContraindication(String contraindication) {
		this.contraindication = contraindication;
	}
	public String getOtherInf() {
		return otherInf;
	}
	public void setOtherInf(String otherInf) {
		this.otherInf = otherInf;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}

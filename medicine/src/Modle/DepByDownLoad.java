package Modle;

public class DepByDownLoad {
	private int depId;
	private String depName;
	private int bedMaxCnt;
	private String depDetail;
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public int getBedMaxCnt() {
		return bedMaxCnt;
	}
	public void setBedMaxCnt(int bedMaxCnt) {
		this.bedMaxCnt = bedMaxCnt;
	}
	public String getDepDetail() {
		return depDetail;
	}
	public void setDepDetail(String depDetail) {
		this.depDetail = depDetail;
	}
	public DepByDownLoad(int depId, String depName, int bedMaxCnt, String depDetail) {
		super();
		this.depId = depId;
		this.depName = depName;
		this.bedMaxCnt = bedMaxCnt;
		this.depDetail = depDetail;
	}
	public DepByDownLoad() {
		super();
		// TODO 自动生成的构造函数存根
	}
	@Override
	public String toString() {
		return "DepByDownLoad [depId=" + depId + ", depName=" + depName + ", bedMaxCnt=" + bedMaxCnt + ", depDetail="
				+ depDetail + "]";
	}
}

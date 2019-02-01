package Modle;

public class findUserCondicion {
	private String accountName;
	private String userName;
	private Integer depId;
	private Integer roleId;
	private Integer currentPage;
	private Integer limit;
	
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
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "findUserCondicion [accountName=" + accountName + ", userName=" + userName + ", depId=" + depId
				+ ", roleId=" + roleId + ", currentPage=" + currentPage + "]";
	}
	
}

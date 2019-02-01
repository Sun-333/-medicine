package cn.cqupt.entity;

public class User {
	
	private Integer accountId;
	private String accountName;
	private String accountPwd;
	private String userName;
	private String userId;
	private String userTel;
	private String userEmail;
	private Role role;
	private Dep dep;
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Dep getDep() {
		return dep;
	}
	public void setDep(Dep dep) {
		this.dep = dep;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountPwd() {
		return accountPwd;
	}
	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	@Override
	public String toString() {
		return "User [accountId=" + accountId + ", accountName=" + accountName + ", accountPwd=" + accountPwd
				+ ", userName=" + userName + ", userId=" + userId + ", userTel=" + userTel + ", userEmail=" + userEmail
				+ ", role=" + role + ", dep=" + dep + "]";
	}
	

}

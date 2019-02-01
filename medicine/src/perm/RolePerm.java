package perm;

public class RolePerm {
	 AddDelectModify accountAdmin;
	 AddDelectModify depAdmin;
	 AddDelectModify roleAdmin;
	 FindHandle alarmAdmin;
	 ConfigAdmin configAdmin;
	 AddFindChangeDelectModify patientAdmin;
	 AddFindDelectModify planAdmin;
	 UserAdmin userAdmin;
	public AddDelectModify getAccountAdmin() {
		return accountAdmin;
	}
	public void setAccountAdmin(AddDelectModify accountAdmin) {
		this.accountAdmin = accountAdmin;
	}
	public AddDelectModify getDepAdmin() {
		return depAdmin;
	}
	public void setDepAdmin(AddDelectModify depAdmin) {
		this.depAdmin = depAdmin;
	}
	public AddDelectModify getRoleAdmin() {
		return roleAdmin;
	}
	public void setRoleAdmin(AddDelectModify roleAdmin) {
		this.roleAdmin = roleAdmin;
	}
	public FindHandle getAlarmAdmin() {
		return alarmAdmin;
	}
	public void setAlarmAdmin(FindHandle alarmAdmin) {
		this.alarmAdmin = alarmAdmin;
	}
	public ConfigAdmin getConfigAdmin() {
		return configAdmin;
	}
	public void setConfigAdmin(ConfigAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}
	public AddFindChangeDelectModify getPatientAdmin() {
		return patientAdmin;
	}
	public void setPatientAdmin(AddFindChangeDelectModify patientAdmin) {
		this.patientAdmin = patientAdmin;
	}
	public AddFindDelectModify getPlanAdmin() {
		return planAdmin;
	}
	public void setPlanAdmin(AddFindDelectModify planAdmin) {
		this.planAdmin = planAdmin;
	}
	public UserAdmin getUserAdmin() {
		return userAdmin;
	}
	public void setUserAdmin(UserAdmin userAdmin) {
		this.userAdmin = userAdmin;
	}
}

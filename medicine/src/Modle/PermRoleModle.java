package Modle;

public class PermRoleModle {
	String roleName;
	String roleId;
	String roleDetail;
	RolePermModle rolePerm;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleDetail() {
		return roleDetail;
	}
	public void setRoleDetail(String roleDetail) {
		this.roleDetail = roleDetail;
	}
	public RolePermModle getRolePerm() {
		return rolePerm;
	}
	public void setRolePerm(RolePermModle rolePerm) {
		this.rolePerm = rolePerm;
	}
	@Override
	public String toString() {
		return "PermRoleModle [roleName=" + roleName + ", roleId=" + roleId + ", roleDetail=" + roleDetail
				+ ", rolePerm=" + rolePerm + "]";
	}
	
}

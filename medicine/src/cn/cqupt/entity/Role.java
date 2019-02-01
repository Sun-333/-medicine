package cn.cqupt.entity;

import java.util.Set;

public class Role {
	private Integer roleId;
	private String roleName;
	private String roleDetail;
	private String rolePerm;
	private Set<User> setUser;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDetail() {
		return roleDetail;
	}
	public void setRoleDetail(String roleDetail) {
		this.roleDetail = roleDetail;
	}
	public String getRolePerm() {
		return rolePerm;
	}
	public void setRolePerm(String rolePerm) {
		this.rolePerm = rolePerm;
	}
	public Set<User> getSetUser() {
		return setUser;
	}
	public void setSetUser(Set<User> setUser) {
		this.setUser = setUser;
	}
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", roleDetail=" + roleDetail + ", rolePerm="
				+ rolePerm + ", setUser=" + setUser + "]";
	}
	
}

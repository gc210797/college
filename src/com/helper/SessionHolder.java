package com.helper;

import com.to.Role;

public class SessionHolder {
	private int empId;
	private Role role;
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public SessionHolder(int empId, Role role) {
		super();
		this.empId = empId;
		this.role = role;
	}
}

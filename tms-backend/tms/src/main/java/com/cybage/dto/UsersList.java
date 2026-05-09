package com.cybage.dto;

import com.cybage.model.Status;

public class UsersList {

	private Integer id;
	private String fullName;
	private Status status;
	private String role;

	public UsersList() {
		super();
	}

	public UsersList(Integer id, String fullName, Status status, String role) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.status = status;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UsersList [id=" + id + ", fullName=" + fullName + ", status=" + status + ", role=" + role + "]";
	}

}

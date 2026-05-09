package com.cybage.dto;

import org.springframework.beans.factory.annotation.Value;

public class AuthResponseModel {

	private String token;
	private String expiresIn;
	private String role;
	private int userId;

	public AuthResponseModel() {
		super();
	}

	public AuthResponseModel(String token, String role, int userId) {
		super();
		this.token = token;
		this.expiresIn = "18000";
		this.role = role;
		this.userId = userId;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	@Value("${spring.expiresInSeconds}")
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}

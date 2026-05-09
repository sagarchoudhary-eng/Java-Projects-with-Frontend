package com.cybage.dto;

import java.util.List;

import com.cybage.model.UserModel;

public class ResponseTemplate {
	private UserModel user;
	private List<Feedback> feedback;

	

	public ResponseTemplate(UserModel user, List<Feedback> feedback) {
		super();
		this.user = user;
		this.feedback = feedback;
	}

	public ResponseTemplate() {
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}

	

}

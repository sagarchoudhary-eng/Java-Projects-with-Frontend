package com.cybage.dto;

import com.cybage.model.UserModel;

public class SubscribedTutors 
{
	private UserModel tutor;
	private Boolean feedback;
	
	public SubscribedTutors() {
	}

	public SubscribedTutors(UserModel tutor, Boolean feedback) {
		super();
		this.tutor = tutor;
		this.feedback = feedback;
	}

	public UserModel getTutor() {
		return tutor;
	}

	public void setTutor(UserModel tutor) {
		this.tutor = tutor;
	}

	public Boolean getFeedback() {
		return feedback;
	}

	public void setFeedback(Boolean feedback) {
		this.feedback = feedback;
	}
	
	
	
}

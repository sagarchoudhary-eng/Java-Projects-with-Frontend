package com.cybage.dto;

import java.util.List;

import com.cybage.model.UserModel;

public class AllTutors {
	private List<SubscribedTutors> subscribedTutors;
	private List<UserModel> pendingTutors;
	private List<UserModel> otherTutors;

	public AllTutors() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AllTutors(List<SubscribedTutors> subscribedTutors, List<UserModel> pendingTutors, List<UserModel> otherTutors) {
		super();
		this.subscribedTutors = subscribedTutors;
		this.pendingTutors = pendingTutors;
		this.otherTutors = otherTutors;
	}

	public List<SubscribedTutors> getSubscribedTutors() {
		return subscribedTutors;
	}

	public void setSubscribedTutors(List<SubscribedTutors> subscribedTutors) {
		this.subscribedTutors = subscribedTutors;
	}

	public List<UserModel> getPendingTutors() {
		return pendingTutors;
	}

	public void setPendingTutors(List<UserModel> pendingTutors) {
		this.pendingTutors = pendingTutors;
	}

	public List<UserModel> getOtherTutors() {
		return otherTutors;
	}

	public void setOtherTutors(List<UserModel> otherTutors) {
		this.otherTutors = otherTutors;
	}

}

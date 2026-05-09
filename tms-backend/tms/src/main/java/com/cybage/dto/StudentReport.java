package com.cybage.dto;

import com.cybage.model.Status;

public class StudentReport {
	private int count;
	private Status statuss;
	
	public StudentReport() {
		// TODO Auto-generated constructor stub
	}

	public StudentReport(int count, Status statuss) {
		super();
		this.count = count;
		this.statuss = statuss;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Status getStatuss() {
		return statuss;
	}

	public void setStatuss(Status statuss) {
		this.statuss = statuss;
	}
	
	
}

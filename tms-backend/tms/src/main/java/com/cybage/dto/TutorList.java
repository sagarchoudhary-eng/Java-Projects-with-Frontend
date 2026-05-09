package com.cybage.dto;

import com.cybage.model.Status;

public class TutorList {
	
	private String firstName;
	
	private String lastName;
	
	private Status status;
	
	private Integer id;
	
	public TutorList() {
		
	}

	public TutorList(String firstName, String lastName, Status status, Integer id) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	

}

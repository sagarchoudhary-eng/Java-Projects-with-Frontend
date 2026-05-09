package com.cybage.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.cybage.model.Status;

public class TutorRegister {
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNo;
	private String password;
	private String role;
	private String technology;
	private String about;
	@Enumerated(EnumType.STRING)
	private Status status;
	private int questionId;
	private String answer;

	public TutorRegister() {
		super();
	}

	public TutorRegister(String firstName, String lastName, String email, String mobileNo, String password, String role,
			String technology, String about, Status status, int questionId, String answer) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNo = mobileNo;
		this.password = password;
		this.role = role;
		this.technology = technology;
		this.about = about;
		this.status = status;
		this.questionId = questionId;
		this.answer = answer;
	}

	public String getRole() {
		if (role == null) {
			return "ROLE_TUTOR";
		}
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Status getStatus() {
		if (status == null) {
			if (role == null) {
				return Status.PENDING;
			}
		}
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
package com.cybage.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String firstName;
	private String lastName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, name = "mobile_no", unique = true, length = 10)
	private String mobileNo;

	@Column(nullable = false)
	@JsonIgnore
	private String password;
	private String role;

	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToOne(targetEntity = Details.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Details extraDetails;

	@Column
	@Max(value = 3)
	private int questionId;

	@Column
	private String answer;

	public UserModel() {

	}

	public UserModel(String firstName, String lastName, String email, String mobileNo, String password, String role,
			Status status, Details extraDetails, @Max(3) int questionId, String answer) {
		super();

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNo = mobileNo;
		this.password = password;
		this.role = role;
		this.status = status;
		this.extraDetails = extraDetails;
		this.questionId = questionId;
		this.answer = answer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Details getExtraDetails() {
		return extraDetails;
	}

	public void setExtraDetails(Details extraDetails) {
		this.extraDetails = extraDetails;
	}

	public boolean isEnabled() {
		if (status == Status.ACCEPTED)
			return true;
		return false;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

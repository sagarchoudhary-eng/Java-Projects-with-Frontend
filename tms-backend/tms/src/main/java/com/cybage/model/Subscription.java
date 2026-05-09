package com.cybage.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	private UserModel student;
	@OneToOne
	private UserModel tutor;
	@Enumerated(EnumType.STRING)
	private Status status;

	public Subscription() {
	}

	public Subscription(UserModel student, UserModel tutor, Status status) {
		super();
		this.student = student;
		this.tutor = tutor;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserModel getStudent() {
		return student;
	}

	public void setStudent(UserModel student) {
		this.student = student;
	}

	public UserModel getTutor() {
		return tutor;
	}

	public void setTutor(UserModel tutor) {
		this.tutor = tutor;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", student=" + student + ", tutor=" + tutor + ", status=" + status + "]";
	}
	

}

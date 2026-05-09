package com.feedback.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long feedbackId;
	private String feedbackData;
	private int studentId;
	private int tutorId;
	private double rating;

	@Column(name = "feedback_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	private LocalDate feedbackDate;

	public Feedback() {
		// TODO Auto-generated constructor stub
	}

	public Feedback(String feedbackData, int studentId, int tutorId, double rating, LocalDate feedbackDate) {
		super();
		this.feedbackData = feedbackData;
		this.studentId = studentId;
		this.tutorId = tutorId;
		this.feedbackDate = LocalDate.now();
		this.rating = rating;
	}

	public LocalDate getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(LocalDate feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getTutorId() {
		return tutorId;
	}

	public void setTutorId(int tutorId) {
		this.tutorId = tutorId;
	}

	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getFeedbackData() {
		return feedbackData;
	}

	public void setFeedbackData(String feedbackData) {
		this.feedbackData = feedbackData;
	}

	public Feedback(Long feedbackId, String feedbackData) {
		super();
		this.feedbackId = feedbackId;
		this.feedbackData = feedbackData;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}

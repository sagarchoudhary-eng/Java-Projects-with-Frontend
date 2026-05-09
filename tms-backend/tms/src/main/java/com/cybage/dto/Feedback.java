package com.cybage.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

	private Long feedbackId;
	private String feedbackData;
	private int studentId;
	private int tutorId;
	private double rating;
	private LocalDate feedbackDate = LocalDate.now();

	public Feedback(String feedbackData, int studentId, int tutorId,double rating, LocalDate feedbackDate) {
		super();
		this.feedbackData = feedbackData;
		this.studentId = studentId;
		this.tutorId = tutorId;
		this.feedbackDate = feedbackDate;
		this.rating=rating;
	}
	

	public Feedback(double rating) {
		super();
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

	public Feedback() {
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {
		return "Feedback [feedbackId=" + feedbackId + ", feedbackData=" + feedbackData + ", studentId=" + studentId
				+ ", tutorId=" + tutorId + ", feedbackDate=" + feedbackDate + "]";
	}

}

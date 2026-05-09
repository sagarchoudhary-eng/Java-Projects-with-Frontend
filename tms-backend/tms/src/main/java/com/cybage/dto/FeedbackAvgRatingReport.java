package com.cybage.dto;

public class FeedbackAvgRatingReport {
	private Double rating;
	private String tutorName;
	public FeedbackAvgRatingReport() {
		// TODO Auto-generated constructor stub
	}
	public FeedbackAvgRatingReport(Double rating, String tutorName) {
		super();
		this.rating = rating;
		this.tutorName = tutorName;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getTutorName() {
		return tutorName;
	}
	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}
	@Override
	public String toString() {
		return "FeedbackAvgRatingReport [rating=" + rating + ", tutorName=" + tutorName + "]";
	}
	
	
}

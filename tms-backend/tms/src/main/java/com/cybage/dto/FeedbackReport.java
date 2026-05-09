package com.cybage.dto;

import java.time.LocalDate;

public class FeedbackReport {
	private LocalDate date;
	private Integer size;
	public FeedbackReport() {
		
	}
	public FeedbackReport(LocalDate date, Integer size) {
		super();
		this.date = date;
		this.size = size;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
}

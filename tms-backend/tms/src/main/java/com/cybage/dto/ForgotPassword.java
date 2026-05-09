package com.cybage.dto;

public class ForgotPassword {
	private String email;
	private int questionId;
	private String answer;
	
	public ForgotPassword() {
		// TODO Auto-generated constructor stub
	}

	public ForgotPassword(String email, int questionId, String answer) {
		super();
		this.email = email;
		this.questionId = questionId;
		this.answer = answer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

package com.ceir.CeirCode.model.app;
public class ForgotPassword {
	private String username;
	private int questionId;
	private String answer;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	@Override
	public String toString() {
		return "ForgotPassword [username=" + username + ", questionId=" + questionId + ", answer=" + answer + "]";
	}


}

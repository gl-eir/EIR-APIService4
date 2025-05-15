package com.ceir.CeirCode.model.app;

public class QuestionPair{ 
	  private long questionId;
	  private String question;
	  private String answer;
	  private long id;
	  
	  public QuestionPair(){};
	  
	  public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	} 
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "QuestionPair [questionId=" + questionId + ", question=" + question + ", answer=" + answer + ", id=" + id
				+ "]";
	}

}
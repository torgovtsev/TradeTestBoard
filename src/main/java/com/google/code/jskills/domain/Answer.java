package com.google.code.jskills.domain;

import java.io.Serializable;

public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Question questionEntity;
	private String answertext;
	private int point;

	public Answer() {

	}

	public Answer(Question question, String text, int point) {
		// this.id = id;
		this.answertext = text;
		this.point = point;
		this.questionEntity = question;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getAnswertext() {
		return answertext;
	}

	public void setAnswertext(String answertext) {
		this.answertext = answertext;
	}

	public Question getQuestionEntity() {
		return questionEntity;
	}

	public void setQuestionEntity(Question questionEntity) {
		this.questionEntity = questionEntity;
	}

}

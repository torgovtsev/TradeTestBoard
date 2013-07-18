package com.google.code.jskills.utils;

/**
 * This class contents message information
 * 
 * @author bobina_vlada
 * 
 */

public class Message {

	private String to;
	private String subject;
	private String content;
	
	public Message(String to, String subject, String content) {
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static Message dummyMessage() {
		Message msg = new Message();
		msg.setTo("John.Doe0@yandex.ru");
		msg.setSubject("Welcome");
		msg.setContent("Test message");
		return msg;

	}

}

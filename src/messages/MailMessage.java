package messages;

import java.io.Serializable;

public class MailMessage implements Serializable{
	
	private static final long serialVersionUID = 7611063856993365178L;
	private String senderEmail;
	private String recipientEmail;
	private String subject;
	private String messageContent;
	
	public MailMessage(String senderEmail, String recipientEmail, String subject, String messageContent) {
		super();
		this.senderEmail = senderEmail;
		this.recipientEmail = recipientEmail;
		this.subject = subject;
		this.messageContent = messageContent;
	}
	
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getRecipientEmail() {
		return recipientEmail;
	}
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	

}

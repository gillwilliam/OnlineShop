package messages;

import java.io.Serializable;
import java.util.UUID;

public class MailMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String senderEmail;
	private String senderName;
	private String recipientEmail;
	private String subject;
	private String messageContent;

	public MailMessage(String senderName, String senderEmail, String recipientEmail, String subject,
			String messageContent) {
		super();
		this.id = UUID.randomUUID().toString();
		this.senderEmail = senderEmail;
		this.recipientEmail = recipientEmail;
		this.subject = subject;
		this.messageContent = messageContent;
		this.senderName = senderName;
	}

	public String getId() {
		return id;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
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

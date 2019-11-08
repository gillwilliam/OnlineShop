package jms_handlers;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import request_handlers.RequestHandler;

public class SendMessageRequestHandler implements RequestHandler {
	
	 @Resource(mappedName="tiwconnectionfactory")
	 private ConnectionFactory tiwconnectionfactory;
	 @Resource(mappedName="tiwqueue")
	 private Queue queue;
	 
	 private String mEmailParamName;
	 private String mSenderEmail;
	 private String mRecipientEmail;
	 private String mSubject;
	 private String mMessage;
	 
	 private String mMailboxPagePath;
	
	public SendMessageRequestHandler(ServletContext context) {
		
		mEmailParamName = context.getInitParameter("email");
		mMailboxPagePath = context.getInitParameter("messages_path");
		
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		mSenderEmail = request.getParameter(mEmailParamName);
		mRecipientEmail = request.getParameter("recipientEmail");
		mSubject = request.getParameter("subject");
		mMessage = request.getParameter("message");
		
		System.out.println("Sending message...");
		System.out.println("Forwarding to " + mMailboxPagePath);
		
		// sendMessage(request, response);
		
		// Refreshes the page
		// TODO: Success/ Failure feedback message
		response.sendRedirect(request.getContextPath() + mMailboxPagePath);
		
	}
	
	protected void sendMessage(HttpServletRequest request, HttpServletResponse response) {
		// TODO: Null Pointer Exception
		try {
			
			Connection con = tiwconnectionfactory.createConnection();
			Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer prod = ses.createProducer(queue);
			
			Message mess = ses.createTextMessage(mMessage);
			mess.setStringProperty("sendTo", mRecipientEmail);
			mess.setStringProperty("subject", mSubject);
			mess.setStringProperty("sendBy", mSenderEmail);
			
			prod.send(mess);
			prod.close();
			ses.close();
			con.close();

		} catch (Exception e) {
			System.out.println("SendMessageRequestHandler Error:" + e);
			e.printStackTrace();
		}
	}

}

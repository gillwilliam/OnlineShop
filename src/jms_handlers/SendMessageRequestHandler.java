package jms_handlers;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.session.UserBean;
import messages.MailMessage;
import request_handlers.RequestHandler;

public class SendMessageRequestHandler implements RequestHandler {
	
//	 @Resource(mappedName="tiwconnectionfactory")
//	 private ConnectionFactory tiwconnectionfactory;
//	 @Resource(mappedName="tiwqueue")
//	 private Queue queue;
	 
	private ConnectionFactory cf;
	private Queue queue;
	private InitialContext ic;
	
	 private String mSenderName;
	 private String mSenderEmail;
	 private String mRecipientEmail;
	 private String mSubject;
	 private String mMessage;
	 
	 private String mMailboxPagePath;
	 private String mUserAttrName;
	
	public SendMessageRequestHandler(ServletContext context) {
		
		try {
			ic = new InitialContext();
			cf = (ConnectionFactory)ic.lookup("tiwconnectionfactory");
			queue = (Queue)ic.lookup("tiwqueue");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		mMailboxPagePath = context.getInitParameter("messages_path");
		mUserAttrName = context.getInitParameter("signed_user_attribute_name");
		
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UserBean user = (UserBean) request.getSession().getAttribute(mUserAttrName);
		mSenderEmail = user.getEmail();
		mSenderName = user.getName() + ' ' + user.getSurname();
		mRecipientEmail = request.getParameter("recipientEmail");
		mSubject = request.getParameter("subject");
		mMessage = request.getParameter("messageContent");
		
		
		System.out.println("Sending message...");
		System.out.println("Forwarding to " + mMailboxPagePath);
		
		MailMessage msg = new MailMessage(mSenderName, mSenderEmail, mRecipientEmail, mSubject, mMessage);
		
		sendMessage(msg);
		
		// Refreshes the page
		// TODO: Success/ Failure feedback message
		response.sendRedirect(request.getContextPath() + mMailboxPagePath);
		
	}
	
	protected void sendMessage(MailMessage msg) {
		// TODO: Null Pointer Exception
		try {
			
			Connection con = cf.createConnection();
			Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer prod = ses.createProducer(queue);
			
			ObjectMessage mess = ses.createObjectMessage();
			mess.setObject(msg);
			
//			TextMessage mess = ses.createTextMessage();
//			mess.setText(msg.getMessageContent());
			
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

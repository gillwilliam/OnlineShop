package jms_handlers;

import java.io.IOException;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import manager.UserManager;
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
	private String mSendAllBuyers;

	public SendMessageRequestHandler(ServletContext context) {

		try {
			ic = new InitialContext();
			cf = (ConnectionFactory) ic.lookup("tiwconnectionfactory");
			queue = (Queue) ic.lookup("tiwqueue");
		} catch (NamingException e) {
			e.printStackTrace();
		}

		mMailboxPagePath = context.getInitParameter("messages_path");
		mUserAttrName = context.getInitParameter("signed_user_attribute_name");

	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute(mUserAttrName);

		mSenderEmail = user.getEmail();
		mSenderName = user.getFirstName() + ' ' + user.getLastName();
		mRecipientEmail = request.getParameter("recipientEmail");
		mSubject = request.getParameter("subject");
		mMessage = request.getParameter("messageContent");
		mSendAllBuyers = request.getParameter("sendAllBuyers");
		System.out.println(mSendAllBuyers);

		MailMessage msg = new MailMessage(mSenderName, mSenderEmail, mRecipientEmail, mSubject, mMessage);
		
		if (mSendAllBuyers != null) {
			sendMessageToAllBuyers(msg);
		} else {
			sendMessage(msg);
		}
		
		// Refreshes the page
		// TODO: Success/ Failure feedback message
		// response.sendRedirect(request.getContextPath() + mMailboxPagePath);
		request.getRequestDispatcher("/readMessage.main").forward(request, response);

	}

	protected void sendMessage(MailMessage msg) {

		try {

			Connection con = cf.createConnection();
			Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer prod = ses.createProducer(queue);

			ObjectMessage mess = ses.createObjectMessage();
			mess.setObject(msg);
			mess.setStringProperty("id", msg.getId());
			mess.setStringProperty("sendTo", cleanEmailAddress(mRecipientEmail));

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

	public static String cleanEmailAddress(String email) {
		return email = email.replace("@", "_at_").replace(".", "dot");
	}
	
	protected void sendMessageToAllBuyers(MailMessage msg) {
		
		UserManager um = new UserManager();
		List<User> users = um.findAllType("BUYER");
		
		try {

			Connection con = cf.createConnection();
			Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer prod = ses.createProducer(queue);
			
			for (User user: users) {
				System.out.println(user.getEmail());
				mRecipientEmail = user.getEmail();
				ObjectMessage mess = ses.createObjectMessage();
				mess.setObject(msg);
				mess.setStringProperty("id", msg.getId());
				mess.setStringProperty("sendTo", cleanEmailAddress(mRecipientEmail));

				prod.send(mess);
				
			}


			prod.close();
			ses.close();
			con.close();

		} catch (Exception e) {
			System.out.println("SendMessageRequestHandler Error:" + e);
			e.printStackTrace();
		}
	}

}

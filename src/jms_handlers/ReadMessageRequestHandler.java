package jms_handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import messages.MailMessage;
import request_handlers.RequestHandler;

public class ReadMessageRequestHandler implements RequestHandler {

//	final InitialContext ic = new InitialContext();
//
//	final ConnectionFactory factory = (ConnectionFactory) ic.lookup("tiwconnectionfactory");
//	final Queue queue = (Queue) ic.lookup("tiwqueue");
//	 @Resource(mappedName="tiwconnectionfactory")
//	 private ConnectionFactory tiwconnectionfactory;
//	 @Resource(mappedName="tiwqueue")
//	 private Queue queue;

	private ConnectionFactory cf;
	private Queue queue;
	private InitialContext ic;

	private String mMailboxPagePath;
	private ArrayList<MailMessage> messageArray;
	private String mMessageArrayParamName;
	private String mUserAttrName;

	public ReadMessageRequestHandler(ServletContext context) {

		mMailboxPagePath = context.getInitParameter("messages_path");
		mMessageArrayParamName = context.getInitParameter("message_array");
		mUserAttrName = context.getInitParameter("signed_user_attribute_name");

		try {
			ic = new InitialContext();
			cf = (ConnectionFactory) ic.lookup("tiwconnectionfactory");
			queue = (Queue) ic.lookup("tiwqueue");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute(mUserAttrName);
		
		if (user.isAdmin()) {
			// Admin has access to read all messages in the queue.
			readAllMessages();
		} else {
			readMessages(user.getEmail());
		}
		

		// Place messages in request
		request.getSession().setAttribute(mMessageArrayParamName, messageArray);

		// request.getRequestDispatcher("/users/mailbox/mailbox.jsp").forward(request,
		// response);
		response.sendRedirect(request.getContextPath() + mMailboxPagePath);
	}

	private void readAllMessages() {
		
		try {

			Connection con = cf.createConnection();
			Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueBrowser browser = ses.createBrowser(queue);

			con.start();

			java.util.Enumeration enumMessages = browser.getEnumeration();

			messageArray = new ArrayList<MailMessage>();

			// Load latest 10 messages or less

			while (enumMessages.hasMoreElements()) {
				Message message = (Message) enumMessages.nextElement();
				if (message != null && message instanceof ObjectMessage) {
					ObjectMessage om = (ObjectMessage) message;
					MailMessage msg = (MailMessage) om.getObject();
					messageArray.add(msg);
				}
			}

			// Latest email at the top
			Collections.reverse(messageArray);
			if (messageArray.size() > 10) {
				messageArray = new ArrayList<MailMessage>(messageArray.subList(0, 9));
			}

			// con.stop(); This line is giving me error for some reason
			browser.close();
			ses.close();
			con.close();

		} catch (Exception e) {
			System.out.println("JMS ReadMessageRequestHandler Error: " + e);
			e.printStackTrace();
		}
		
	}

	protected void readMessages(String email) {

		try {

			Connection con = cf.createConnection();
			Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			String filter = "sendTo='" + SendMessageRequestHandler.cleanEmailAddress(email) + "'";
			QueueBrowser browser = ses.createBrowser(queue, filter);

			con.start();

			java.util.Enumeration enumMessages = browser.getEnumeration();

			messageArray = new ArrayList<MailMessage>();

			// Load latest 10 messages or less

			while (enumMessages.hasMoreElements()) {
				Message message = (Message) enumMessages.nextElement();
				if (message != null && message instanceof ObjectMessage) {
					ObjectMessage om = (ObjectMessage) message;
					MailMessage msg = (MailMessage) om.getObject();
					messageArray.add(msg);
				}
			}

			// Latest email at the top
			Collections.reverse(messageArray);
			if (messageArray.size() > 10) {
				messageArray = new ArrayList<MailMessage>(messageArray.subList(0, 9));
			}

			// con.stop(); This line is giving me error for some reason
			browser.close();
			ses.close();
			con.close();

		} catch (Exception e) {
			System.out.println("JMS ReadMessageRequestHandler Error: " + e);
			e.printStackTrace();
		}
	}

}

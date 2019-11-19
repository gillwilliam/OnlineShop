package jms_handlers;

import java.io.IOException;
import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
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

import messages.MailMessage;
import request_handlers.RequestHandler;

public class DeleteMessageRequestHandler implements RequestHandler {

	private String mMailboxPagePath;
	private InitialContext ic;
	private ConnectionFactory cf;
	private Queue queue;
	private String mMessageID;

	public DeleteMessageRequestHandler(ServletContext context) {
		mMailboxPagePath = context.getInitParameter("messages_path");

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
		// TODO Auto-generated method stub
		mMessageID = (String) request.getParameter("message-id");

		deleteMessage(mMessageID);

		request.getRequestDispatcher("/readMessage.main").forward(request, response);
		// response.sendRedirect(request.getContextPath() + mMailboxPagePath);;

	}

	private void deleteMessage(String messageID) {

		try {

			Connection con = cf.createConnection();
			Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueBrowser browser = ses.createBrowser(queue);

			con.start();

			Enumeration enumMessages = browser.getEnumeration();
			System.out.println(messageID);

			while (enumMessages.hasMoreElements()) {
				ObjectMessage om = (ObjectMessage) enumMessages.nextElement();
				System.out.println(om.getStringProperty("id"));
				if (om.getStringProperty("id").equals(messageID)) {
					System.out.println("Deleting this...");
					MessageConsumer consumer = ses.createConsumer(queue, "id='" + messageID + "'");
					consumer.receive(500);

				}

			}

			browser.close();
			ses.close();
			con.close();

		} catch (Exception e) {
			System.out.println("JMS DeleteMessageRequestHandler Error: " + e);
			e.printStackTrace();
		}

	}

}

package jms_handlers;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import request_handlers.RequestHandler;

public class ReadMessageRequestHandler implements RequestHandler {
	
	 @Resource(mappedName="tiwconnectionfactory")
	 private ConnectionFactory tiwconnectionfactory;
	 @Resource(mappedName="tiwqueue")
	 private Queue queue;
	 
	 private String mMailboxPagePath;
	
	public ReadMessageRequestHandler(ServletContext context) {
		
		mMailboxPagePath = context.getInitParameter("messages_path");
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		readMessages();
		
		request.getRequestDispatcher(mMailboxPagePath).forward(request, response);
	}
	
	protected void readMessages() {
		
		try {
			
			Connection con = tiwconnectionfactory.createConnection();
			Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueBrowser browser = ses.createBrowser(queue);
			
			con.start();
			
			java.util.Enumeration enumMessages = browser.getEnumeration();
			
			// Load latest 10 messages or less
			
			for (int i=0; i<10; i++) {
				
				TextMessage mess = (TextMessage) enumMessages.nextElement();
				// TODO: Pass to message page
				
				if (mess != null) {
					System.out.println(mess.getText());
				} else {
					break;
				}
			}
			
			con.stop();
			browser.close();
			ses.close();
			
		} catch (Exception e) {
			System.out.println("JMS ReadMessageRequestHandler Error: " + e);
		}
	}
	
	

}

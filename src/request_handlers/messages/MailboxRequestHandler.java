package request_handlers.messages;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import request_handlers.RequestHandler;

public class MailboxRequestHandler implements RequestHandler {

	// fields ///
	private ServletContext mContext;
	private String mUserMessagesPath;

	public MailboxRequestHandler(ServletContext context) {

		mContext = context;
		mUserMessagesPath = context.getInitParameter("messages_path");
		System.out.println("creating mailboxrh...");

	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("routing via mailboxrh... ");
		request.getRequestDispatcher("/readMessage").forward(request, response);

	}

}

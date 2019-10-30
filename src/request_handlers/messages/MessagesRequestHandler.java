package request_handlers.messages;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import request_handlers.RequestHandler;

public class MessagesRequestHandler implements RequestHandler {
	
	// fields ///
	private ServletContext mContext;
	private String mUserMessagesPath;

	public MessagesRequestHandler(ServletContext context) {
		// TODO Extract required parameters
		mContext = context;
		mUserMessagesPath = context.getInitParameter("messages_path");
		
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getRequestDispatcher(mUserMessagesPath).forward(request, response);

	}

}

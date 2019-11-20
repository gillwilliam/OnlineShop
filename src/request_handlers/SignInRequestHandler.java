package request_handlers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import manager.UserManager;

public class SignInRequestHandler implements RequestHandler {

	// fields
	// /////////////////////////////////////////////////////////////////////////
	private String mEmailParamName;
	private String mPasswordParamName;
	private String mUserAttrName;
	private String mHomepagePath;

	public SignInRequestHandler(ServletContext context) {
		mEmailParamName = context.getInitParameter("email");
		mPasswordParamName = context.getInitParameter("password");
		mUserAttrName = context.getInitParameter("signed_user_attribute_name");
		mHomepagePath = context.getInitParameter("homepage_path");

	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter(mEmailParamName);
		String password = request.getParameter(mPasswordParamName);

		UserManager um = new UserManager();
		User user = um.findById(email);
		
		if (user == null || !user.getPassword().equals(password)) {
			request.setAttribute("errorMessage", "Incorrect credentials. Please try again.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			request.getSession().setAttribute(mUserAttrName, user);
			request.getSession().setAttribute("shoppingcart", new HashMap<Integer, Integer>());
			request.getRequestDispatcher(mHomepagePath).forward(request, response);
		}
	}

}

package request_handlers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;

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

		EntityManager em = (EntityManager) request.getServletContext().getAttribute("entity_manager");
		User user = em.find(User.class, email);
		System.out.println(email);
		System.out.println(user);
		System.out.println(user.getPassword());
		System.out.println(password);
		if (user == null || !user.getPassword().equals(password)) {
			request.setAttribute("errorMessage", "Incorrect credentials. Please try again.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			request.getSession().setAttribute(mUserAttrName, user);
			request.getRequestDispatcher(mHomepagePath).forward(request, response);
		}
	}

}

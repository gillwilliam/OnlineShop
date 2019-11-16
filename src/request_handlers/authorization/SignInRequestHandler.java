package request_handlers.authorization;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.session.AdminBean;
import beans.session.BuyerBean;
import beans.session.SellerBean;
import beans.session.UserBean;
import request_handlers.RequestHandler;

public class SignInRequestHandler implements RequestHandler {

	// fields /////////////////////////////////////////////////////////////////////////
	private String mEmailParamName;
	private String mPasswordParamName;
	private String mUserAttrName;
	private String mHomepagePath;
	
	
	
	public SignInRequestHandler(ServletContext context)
	{
		mEmailParamName		= context.getInitParameter("email");
		mPasswordParamName 	= context.getInitParameter("password");
		mUserAttrName		= context.getInitParameter("signed_user_attribute_name");
		mHomepagePath		= context.getInitParameter("homepage_path");
		
	}
	
	
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String email 	= request.getParameter(mEmailParamName);
		String password = request.getParameter(mPasswordParamName);
		
		UserBean user = getUser(email, password);
		
		if (user == null)
		{
			// TODO Return error message
			request.setAttribute("errorMessage", "Incorrect credentials. Please try again.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		else
		{	
			request.getSession().setAttribute(mUserAttrName, user);
			request.getRequestDispatcher(mHomepagePath).forward(request, response);
		}
	}
	
	
	
	private UserBean getUser(String email, String password)
	{
		ArrayList<UserBean> users = new ArrayList<>();
		
		UserBean buyer = new BuyerBean("Johny", "Napalony", "+34 177 473 822", "calle de las Palmas 27, Madrid", "jnapal@gmail.com",
				"123456");
		
		UserBean seller = new SellerBean("Mirek", "Handlarz", "+48 888 777 555", "mira@gmail.com",
				"123456");
		
		UserBean admin = new AdminBean("Mateusz", "Kowalczyk", "+420222473822", "admin@shop.com",
				"123456");
		
		users.add(buyer);
		users.add(seller);
		users.add(admin);
		
		for (UserBean user : users)
		{
			if (user.getEmail().equals(email) && user.getPassword().equals(password))
				return user;
		}
		
		return null;
	}

}

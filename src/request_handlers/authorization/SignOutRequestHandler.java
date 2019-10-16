package request_handlers.authorization;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import request_handlers.RequestHandler;

public class SignOutRequestHandler implements RequestHandler {
	
	// fields //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String mUserInitParamName;
	private String mHomepagePath;

	
	
	public SignOutRequestHandler(ServletContext context)
	{
		mUserInitParamName = context.getInitParameter("signed_user_attribute_name");
		mHomepagePath	   = context.getInitParameter("homepage_path");
	}
	
	
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		request.getSession().setAttribute(mUserInitParamName, null);
		request.getRequestDispatcher(mHomepagePath).forward(request, response);
	}

}

package request_handlers.users;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import request_handlers.RequestHandler;

public class DeleteAdminRequestHandler implements RequestHandler {

	// CONST ///////////////////////////////////////////////////////////////////////////
	public static final String PARAM_SEARCHED_NAME 		= "search_name";
	public static final String PARAM_SEARCHED_SURNAME 	= "search_surname";
	public static final String PARAM_SEARCHED_EMAIL 	= "search_email";
	public static final String SEARCH_USERS_PATH		= "/searchUsers";

	// fields //////////////////////////////////////////////////////////////////////////
	private String mParamNameName;
	private String mParamSurnameName;
	private String mParamEmailName;
	private String mRequestExtension;
		
		
		
	public DeleteAdminRequestHandler(ServletContext context, String requestExtension)
	{
		mParamNameName 		= context.getInitParameter("name");
		mParamSurnameName 	= context.getInitParameter("surname");
		mParamEmailName 	= context.getInitParameter("email");
		mRequestExtension	= requestExtension;
	}
		
		
		
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		// restore search parameters, so that user can be redirected back to the same search
		String searchedName 	= request.getParameter(PARAM_SEARCHED_NAME);
		String searchedSurname 	= request.getParameter(PARAM_SEARCHED_SURNAME);
		String searchedEmail 	= request.getParameter(PARAM_SEARCHED_EMAIL);
			
		request.setAttribute(mParamNameName, searchedName);
		request.setAttribute(mParamSurnameName, searchedSurname);
		request.setAttribute(mParamEmailName, searchedEmail);

		response.sendRedirect(request.getContextPath() +
				SEARCH_USERS_PATH   + mRequestExtension + "?" + 
				mParamNameName 		+ "=" + searchedName + "&" + 
				mParamSurnameName 	+ "=" + searchedSurname + "&" + 
				mParamEmailName 	+ "=" + searchedEmail);
	}
}

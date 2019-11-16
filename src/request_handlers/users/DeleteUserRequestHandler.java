package request_handlers.users;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import entities.User;
import request_handlers.RequestHandler;
import request_handlers.users.EditUserProfileRequestHandler.InputValidationResult;
import request_handlers.users.EditUserProfileRequestHandler.UpdateInDBResult;

public class DeleteUserRequestHandler implements RequestHandler {
	
	// CONST ///////////////////////////////////////////////////////////////////////////
	public static final String PARAM_SEARCHED_NAME 		= "search_name";
	public static final String PARAM_SEARCHED_SURNAME 	= "search_surname";
	public static final String PARAM_SEARCHED_EMAIL 	= "search_email";
	public static final String SEARCH_USERS_PATH		= "/searchUsers";
	public static final String ADMIN_REQUESTER			= "admin";
	public static final String BUYER_REQUESTER			= "buyer";

	// fields //////////////////////////////////////////////////////////////////////////
	private String mParamSearchNameName;
	private String mParamSearchSurnameName;
	private String mParamSearchEmailName;
	private String mParamMessage;
	private String mParamEmailName;
	private String mParamRequester;
	private String mBuyerProfilePath;
	private String mUserProfileUpdateParam;
	private String mHomepagePath;
	private String mUserAttr;
	private String mDeleteResultParam;
	private String mSearchUsersRequest;
	private String mValidationResultParam;
	// persistence
	EntityManager mEntityManager;
	UserTransaction mUserTransaction;
	
	
	
	public DeleteUserRequestHandler(ServletContext context, String requestExtension, EntityManager entityManager, 
			UserTransaction userTransaction)
	{
		mParamSearchNameName 		= PARAM_SEARCHED_NAME;
		mParamSearchSurnameName 	= PARAM_SEARCHED_SURNAME;
		mParamSearchEmailName 		= PARAM_SEARCHED_EMAIL;
		mParamEmailName				= context.getInitParameter("email");
		mParamRequester				= "requester";
		mEntityManager				= entityManager;
		mUserTransaction			= userTransaction;
		mParamMessage				= "message";
		mBuyerProfilePath			= context.getInitParameter("buyer_profile_edit_path");
		mUserProfileUpdateParam		= context.getInitParameter("user_profile_update_result");
		mHomepagePath				= context.getInitParameter("homepage_path");
		mUserAttr					= context.getInitParameter("signed_user_attribute_name");
		mDeleteResultParam			= "deleteResult";
		mSearchUsersRequest			= SEARCH_USERS_PATH + requestExtension;
		mValidationResultParam		= context.getInitParameter("buyer_profile_edit_result");
	}
	
	
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String requester = request.getParameter(mParamRequester);
		String email 	 = request.getParameter(mParamEmailName);
		
		if (requester != null && email != null)
		{
			boolean deleteSuccess = deleteUser(email);
			
			String message;
			if (deleteSuccess)
				message = "successful deletion";
			else
				message = "error during deletion";
			
			if (requester.contentEquals(ADMIN_REQUESTER))
				processAdminRequester(deleteSuccess, message, request, response);
			else if (requester.contentEquals(BUYER_REQUESTER))
				processBuyerRequester(deleteSuccess, message, request, response);
		}
	}
	
	
	
	private boolean deleteUser(String email)
	{
		User user = mEntityManager.find(User.class, email);
		
		if (user != null)
		{
			try 
			{
				mUserTransaction.begin();
				user = mEntityManager.merge(user);
				mEntityManager.remove(user);
				mUserTransaction.commit();
				
				return true;
			} 
			catch (NotSupportedException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
					| HeuristicRollbackException | SystemException e) 
			{
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
	}
	
	
	
	private void processAdminRequester(boolean deleteSuccess, String message, HttpServletRequest request, 
			HttpServletResponse response) throws IOException, ServletException
	{
		request.setAttribute(mParamMessage, message);
		request.setAttribute(mDeleteResultParam, deleteSuccess);
		returnToUsersMaintenance(request, response);
	}
	
	
	
	private void processBuyerRequester(boolean deleteSuccess, String message, HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		if (deleteSuccess)
		{
			HttpSession session = request.getSession();
			session.setAttribute(mUserAttr, null);
			request.getRequestDispatcher(mHomepagePath).forward(request, response);
		}
		else
			forwardToBuyerProfileAfterError(request, response, message);
	}
	
	
	
	private void forwardToBuyerProfileAfterError(HttpServletRequest request, HttpServletResponse response, String message)
	throws IOException, ServletException
	{
		UpdateInDBResult updateResult 	= new UpdateInDBResult();
		updateResult.isUpdateSuccessful = false;
		updateResult.message 			= message;
		InputValidationResult validationResult = new InputValidationResult();
		
		request.setAttribute(mValidationResultParam, validationResult);
		request.setAttribute(mUserProfileUpdateParam, updateResult);
		request.getRequestDispatcher(mBuyerProfilePath).forward(request, response);
	}
	
	
	
	private void returnToUsersMaintenance(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException
	{
		// restore search parameters, so that user can be redirected back to the same search
		String searchedName 	= request.getParameter(PARAM_SEARCHED_NAME);
		String searchedSurname 	= request.getParameter(PARAM_SEARCHED_SURNAME);
		String searchedEmail 	= request.getParameter(PARAM_SEARCHED_EMAIL);

		request.setAttribute(mParamSearchNameName, searchedName);
		request.setAttribute(mParamSearchSurnameName, searchedSurname);
		request.setAttribute(mParamSearchEmailName, searchedEmail);
		request.getRequestDispatcher(mSearchUsersRequest).forward(request, response);;
	}
	

}

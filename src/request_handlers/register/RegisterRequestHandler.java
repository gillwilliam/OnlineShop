package request_handlers.register;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.session.BuyerBean;
import beans.session.UserBean;
import request_handlers.RequestHandler;
import request_handlers.users.EditUserProfileRequestHandler;
import utils.UserDataValidator.InputValidationResult;

public class RegisterRequestHandler implements RequestHandler {
	
	// fields //////////////////////////////////
	private String mLoginPath;
	private String mRegisterPath;
	private String mValidateResultParamName;
	private String mHomepagePath;
	private String mUserAttrName;
	
	public static final String VALIDATION_RESULT_PARAM  = "register_result";
	
	public RegisterRequestHandler(ServletContext context) {
		
		mLoginPath = context.getInitParameter("login_path");
		mRegisterPath = context.getInitParameter("register_path");
		mHomepagePath = context.getInitParameter("homepage_path");
		mUserAttrName = context.getInitParameter("signed_user_attribute_name");
		mValidateResultParamName = context.getInitParameter(VALIDATION_RESULT_PARAM);
		
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String name                 = request.getParameter(InputValidationResult.NAME_PARAM);
        String surname              = request.getParameter(InputValidationResult.SURNAME_PARAM);
        String phone                = request.getParameter(InputValidationResult.PHONE_PARAM);
        String address              = request.getParameter(InputValidationResult.ADDRESS_PARAM);
        String email                = request.getParameter(InputValidationResult.EMAIL_PARAM);
        String newPassword          = request.getParameter(InputValidationResult.NEW_PASSWORD_PARAM);
        String confirmedPassword    = request.getParameter(InputValidationResult.CONFIRMED_PASSWORD_PARAM);
		
		InputValidationResult result = InputValidationResult.validateInputs(request).validateUnusedEmail(email);

		request.setAttribute(mValidateResultParamName, result);
		
		if (result.isValid()) {

			// For now, all new members are buyers
			UserBean user = new BuyerBean(name, surname, phone, address, email, confirmedPassword);
			
			request.getSession().setAttribute(mUserAttrName, user);
			request.getRequestDispatcher(mHomepagePath).forward(request, response);
			
		} else {
			
			// Refresh the page with the error message
			request.getRequestDispatcher(mRegisterPath).forward(request, response);
			
		}

	}

}

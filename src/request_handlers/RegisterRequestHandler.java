package request_handlers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

import entities.Buyer;
import entities.User;
import utils.UserDataValidator;
import utils.UserDataValidator.InputValidationResult;

public class RegisterRequestHandler implements RequestHandler {

	// fields //////////////////////////////////
	private String mLoginPath;
	private String mRegisterPath;
	private String mValidateResultParamName;
	private String mHomepagePath;
	private String mUserAttrName;

	public static final String VALIDATION_RESULT_PARAM = "register_result";

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

		String name = request.getParameter(UserDataValidator.NAME_PARAM);
		String surname = request.getParameter(UserDataValidator.SURNAME_PARAM);
		String phone = request.getParameter(UserDataValidator.PHONE_PARAM);
		String address = request.getParameter(UserDataValidator.ADDRESS_PARAM);
		String email = request.getParameter(UserDataValidator.EMAIL_PARAM);
		String newPassword = request.getParameter(UserDataValidator.NEW_PASSWORD_PARAM);
		String confirmedPassword = request.getParameter(UserDataValidator.CONFIRMED_PASSWORD_PARAM);

		InputValidationResult result = UserDataValidator.validateInputs(request).validateUnusedEmail(email);

		request.setAttribute(mValidateResultParamName, result);

		if (result.isValid()) {

			// For now, all new members are buyers
			User user = new Buyer(name, surname, phone, address, email, confirmedPassword);
			EntityManager em = (EntityManager) request.getServletContext().getAttribute("entity_manager");
			UserTransaction ut = (UserTransaction) request.getServletContext().getAttribute("user_transaction");
			try {
				ut.begin();
				em.persist(user);
				ut.commit();
			} catch (Exception e) {
				// TODO handle database error
			}
			request.getSession().setAttribute(mUserAttrName, user);
			request.getRequestDispatcher(mHomepagePath).forward(request, response);

		} else {

			// Refresh the page with the error message
			request.getRequestDispatcher(mRegisterPath).forward(request, response);

		}

	}

}

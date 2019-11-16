package request_handlers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Seller;
import utils.Result;
import utils.UserDataValidator;
import utils.UserDataValidator.InputValidationResult;

public class CreateSellerRequestHandler implements RequestHandler {

	// CONST
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// params
	public static final String NAME_PARAM = "name";
	public static final String SURNAME_PARAM = "surname";
	public static final String PHONE_PARAM = "phone";
	public static final String EMAIL_PARAM = "email";
	public static final String NEW_PASSWORD_PARAM = "new_password";
	public static final String CONFIRMED_PASSWORD_PARAM = "confirmed_password";
	public static final String VALIDATION_RESULT_PARAM = "user_profile_edit_result";
	public static final String UPDATE_RESULT_PARAM = "user_profile_update_result";
	public static final String USER_ATTR_PARAM = "signed_user_attribute_name";
	// messages
	public static final String BAD_NAME_MESSAGE_EN = "Provided name is wrong";
	public static final String BAD_SURNAME_MESSAGE_EN = "Provided surname is wrong";
	public static final String BAD_PHONE_MESSAGE_EN = "Provided phone is wrong";
	public static final String BAD_ADDRESS_MESSAGE_EN = "Provided address is wrong";
	public static final String BAD_EMAIL_MESSAGE_EN = "Provided email is wrong";
	public static final String BAD_PASSWORD_MESSAGE_EN = "Password must have at least " + UserDataValidator.MIN_PASS_LEN
			+ " non white characters and can't be longer than " + UserDataValidator.MAX_PASS_LEN;
	public static final String BAD_CONFIRMED_PASSWORD_MESSAGE_EN = "Passwords must be the same!";

	// jsp
	public static final String SELLER_CREATION_PATH_PARAM = "seller_creation_path";
	public static final String USERS_MAINTENANCE_PATH_PARAM = "users_maintenance_path";

	// fields
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String mNameParamName;
	private String mSurnameParamName;
	private String mPhoneParamName;
	private String mEmailParamName;
	private String mNewPassParamName;
	private String mConfirmedPassParamName;
	private String mValidatResultParamName;
	private String mUpdateResultParamName;
	private String mSellerCreationPath;
	private String mUsersMaintenancePath;

	public CreateSellerRequestHandler(ServletContext context) {
		initParams(context);
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Seller newSeller = createSellerBeanFromRequest(request);
		UserDataValidator.removeUnwantedWhiteCharacters(newSeller);

		// data validation
		InputValidationResult validationResult = validateInputs(newSeller,
				request.getParameter(mConfirmedPassParamName));
		request.setAttribute(mValidatResultParamName, validationResult);
		Result addResult = new Result(true, "success");

		if (validationResult.isValid()) {
			EntityManager em = (EntityManager) request.getServletContext().getAttribute("entity_manager");
			try {
				em.getTransaction().begin();
				em.persist(newSeller);
				em.getTransaction().commit();
			} catch (Exception e) {
				addResult.message = e.getMessage();
				addResult.success = false;
			}
			request.setAttribute(mUpdateResultParamName, addResult);

		} // update in database

		// redirecting to appropriate page
		if (!validationResult.isValid() || !addResult.success)
			request.getRequestDispatcher(mSellerCreationPath).forward(request, response);
		else
			request.getRequestDispatcher(mUsersMaintenancePath).forward(request, response);
	}

	private void initParams(ServletContext context) {
		mNameParamName = context.getInitParameter(NAME_PARAM);
		mSurnameParamName = context.getInitParameter(SURNAME_PARAM);
		mPhoneParamName = context.getInitParameter(PHONE_PARAM);
		mEmailParamName = context.getInitParameter(EMAIL_PARAM);
		mNewPassParamName = context.getInitParameter(NEW_PASSWORD_PARAM);
		mConfirmedPassParamName = context.getInitParameter(CONFIRMED_PASSWORD_PARAM);
		mValidatResultParamName = context.getInitParameter(VALIDATION_RESULT_PARAM);
		mUpdateResultParamName = context.getInitParameter(UPDATE_RESULT_PARAM);
		mSellerCreationPath = context.getInitParameter(SELLER_CREATION_PATH_PARAM);
		mUsersMaintenancePath = context.getInitParameter(USERS_MAINTENANCE_PATH_PARAM);
	}

	private InputValidationResult validateInputs(Seller seller, String confirmedPassword) {
		InputValidationResult res = new InputValidationResult();

		res.setIsNameValid(UserDataValidator.isNameValid(seller.getFirstName()));
		res.setIsSurnameValid(UserDataValidator.isSurnameValid(seller.getLastName()));
		res.setIsPhoneValid(UserDataValidator.isPhoneValid(seller.getPhone()));
		res.setIsEmailValid(UserDataValidator.isEmailValid(seller.getEmail()));

		if (confirmedPassword != null) {
			res.setIsPasswordValid(UserDataValidator.isPasswordValid(seller.getPassword()));
			res.setIsConfirmedPasswordValid(
					UserDataValidator.isNewPasswordValid(seller.getPassword(), confirmedPassword));
		} else {
			res.setIsPasswordValid(false);
			res.setIsConfirmedPasswordValid(false);
		}

		return res;
	}

	/**
	 * should be called only after data in request was validated. Updates user data
	 * in database
	 * 
	 * @param request
	 * @return
	 */

	private Seller createSellerBeanFromRequest(HttpServletRequest request) {
		String name = request.getParameter(mNameParamName);
		String surname = request.getParameter(mSurnameParamName);
		String phone = request.getParameter(mPhoneParamName);
		String email = request.getParameter(mEmailParamName);
		String newPassword = request.getParameter(mNewPassParamName);

		name = name == null ? "" : name;
		surname = surname == null ? "" : surname;
		phone = phone == null ? "" : phone;
		email = email == null ? "" : email;
		newPassword = newPassword == null ? "" : newPassword;

		Seller seller = new Seller();

		seller.setFirstName(name);
		seller.setLastName(surname);
		seller.setPhone(phone);
		seller.setEmail(email);
		seller.setPassword(newPassword);

		return seller;
	}

}

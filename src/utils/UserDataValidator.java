package utils;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import entities.User;

public class UserDataValidator {

	// CONST
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final int MIN_NAME_LEN = 2;
	public static final int MAX_NAME_LEN = 60;
	public static final int MIN_SURNAME_LEN = 2;
	public static final int MAX_SURNAME_LEN = 60;
	public static final int MIN_PHONE_LEN = 5;
	public static final int MAX_PHONE_LEN = 15;
	public static final int MIN_ADDR_LEN = 5;
	public static final int MAX_ADDR_LEN = 200;
	public static final int MIN_PASS_LEN = 6;
	public static final int MAX_PASS_LEN = 50;

	// params
	public static final String NAME_PARAM = "name";
	public static final String SURNAME_PARAM = "sur";
	public static final String PHONE_PARAM = "phone";
	public static final String ADDRESS_PARAM = "address";
	public static final String EMAIL_PARAM = "email";
	public static final String NEW_PASSWORD_PARAM = "new_password";
	public static final String CONFIRMED_PASSWORD_PARAM = "confirmed_password";
	public static final String VALIDATION_RESULT_PARAM = "buyer_profile_edit_result";

	// messages
	public static final String BAD_NAME_MESSAGE_EN = "Provided name is wrong";
	public static final String BAD_SURNAME_MESSAGE_EN = "Provided surname is wrong";
	public static final String BAD_PHONE_MESSAGE_EN = "Provided phone is wrong";
	public static final String BAD_ADDRESS_MESSAGE_EN = "Provided address is wrong";
	public static final String BAD_EMAIL_MESSAGE_EN = "Provided email is wrong";
	public static final String BAD_PASSWORD_MESSAGE_EN = "Password must have at least " + UserDataValidator.MIN_PASS_LEN
			+ " non white characters and can't be longer than " + UserDataValidator.MAX_PASS_LEN;
	public static final String BAD_CONFIRMED_PASSWORD_MESSAGE_EN = "Passwords must be the same!";

	public static void removeUnwantedWhiteCharacters(User seller) {
		seller.setEmail(seller.getEmail().trim());
		seller.setFirstName(seller.getFirstName().trim());
		seller.setPassword(seller.getPassword().trim());
		seller.setPhone(seller.getPhone().replaceAll("\\s+", ""));
		seller.setFirstName(seller.getLastName().trim());
	}

	/**
	 * Checks whether provided name is valid.
	 * 
	 * @param name - must be trimmed and have redundant spaces removed
	 * @return true - name is valid, false - name is invalid
	 */
	public static boolean isNameValid(String name) {
		boolean isLengthValid = StringUtils.isInRange(name, MIN_NAME_LEN, MAX_NAME_LEN);
		boolean lettersAndSpacesOnly = name.matches("[\\p{L}\\s]+");

		return isLengthValid && lettersAndSpacesOnly;
	}

	/**
	 * Checks whether provided surname is valid.
	 * 
	 * @param surname - must be trimmed and have redundant spaces removed
	 * @return true - surname is valid, false - surname is invalid
	 */
	public static boolean isSurnameValid(String surname) {
		boolean isLengthValid = StringUtils.isInRange(surname, MIN_SURNAME_LEN, MAX_SURNAME_LEN);
		boolean lettersAndSpacesOnly = surname.matches("[\\p{L}\\s-]+");

		return isLengthValid && lettersAndSpacesOnly;
	}

	/**
	 * Checks whether provided phone number is valid
	 * 
	 * @param phone - must be trimmed and have redundant spaces removed
	 * @return true - phone number is valid, false - phone number is invalid
	 */
	public static boolean isPhoneValid(String phone) {
		phone = phone.replaceAll("\\s+", "");

		boolean isLengthValid = StringUtils.isInRange(phone, MIN_PHONE_LEN, MAX_PHONE_LEN);

		if (isLengthValid) {
			if (phone.charAt(0) == '+')
				return StringUtils.isNumber(phone.substring(1));
			else
				return StringUtils.isNumber(phone);
		} else
			return false;
	}

	/**
	 * Checks whether provided address is valid
	 * 
	 * @param address - must be trimmed and have redundant spaces removed
	 * @return true - address is valid, false - address is invalid
	 */
	public static boolean isAddressValid(String address) {
		return StringUtils.isInRange(address, MIN_ADDR_LEN, MAX_ADDR_LEN);
	}

	public static boolean isEmailValid(String email) {
		String emailRegex = "[\\w+-]+(?:\\.[\\w+-]+)*@[\\w+-]+(?:\\.[\\w+-]+)*(?:\\.[a-zA-Z]{2,4})";
		return email.matches(emailRegex);
	}

	/**
	 * Checks whether provided password is valid.
	 * 
	 * @param password - password to check
	 * @return true - password is valid, false - password is invalid
	 */
	public static boolean isPasswordValid(String password) {
		return StringUtils.isInRange(password.trim(), MIN_PASS_LEN, MAX_PASS_LEN);
	}

	/**
	 * Checks whether password is valid and if it is the same as confirmedPassword
	 * 
	 * @param password          password to check
	 * @param confirmedPassword confirmed password
	 * @return true - new password is valid, false - new password is invalid
	 */
	public static boolean isNewPasswordValid(String password, String confirmedPassword) {
		return isPasswordValid(password) && password.equals(confirmedPassword);
	}

	/**
	 * class for returning result of validation
	 */

	public static class InputValidationResult {

		private boolean mIsValid;
		private boolean mIsNameValid;
		private boolean mIsSurnameValid;
		private boolean mIsPhoneValid;
		private boolean mIsAddressValid;
		private boolean mIsEmailValid;
		private boolean mIsNewPasswordValid;
		private boolean mIsConfirmedPasswordValid;
		private String mNameMessage;
		private String mSurnameMessage;
		private String mPhoneMessage;
		private String mAddressMessage;
		private String mEmailMessage;
		private String mNewPasswordMessage;
		private String mConfirmedPasswordMessage;

		public InputValidationResult() {
			mIsValid = mIsNameValid = mIsSurnameValid = mIsPhoneValid = mIsAddressValid = mIsEmailValid = mIsNewPasswordValid = mIsConfirmedPasswordValid = true;

			mNameMessage = mSurnameMessage = mPhoneMessage = mAddressMessage = mEmailMessage = mNewPasswordMessage = mConfirmedPasswordMessage = "";
		}

		public void setIsNameValid(boolean isNameValid) {
			if (!isNameValid) {
				mIsValid = false;
				mIsNameValid = false;
				mNameMessage = BAD_NAME_MESSAGE_EN;
			}
		}

		public void setIsSurnameValid(boolean isSurnameValid) {
			if (!isSurnameValid) {
				mIsValid = false;
				mIsSurnameValid = false;
				mSurnameMessage = BAD_SURNAME_MESSAGE_EN;
			}
		}

		public void setIsPhoneValid(boolean isPhoneValid) {
			if (!isPhoneValid) {
				mIsValid = false;
				mIsPhoneValid = false;
				mPhoneMessage = BAD_PHONE_MESSAGE_EN;
			}
		}

		public void setIsAddressValid(boolean isAddressValid) {
			if (!isAddressValid) {
				mIsValid = false;
				mIsAddressValid = false;
				mAddressMessage = BAD_ADDRESS_MESSAGE_EN;
			}
		}

		public void setIsEmailValid(boolean isEmailValid) {
			if (!isEmailValid) {
				mIsValid = false;
				mIsEmailValid = false;
				mEmailMessage = BAD_EMAIL_MESSAGE_EN;
			}
		}

		public void setIsPasswordValid(boolean isNewPasswordValid) {
			if (!isNewPasswordValid) {
				mIsValid = false;
				mIsNewPasswordValid = false;
				System.out.println(mIsNewPasswordValid);
				mNewPasswordMessage = BAD_PASSWORD_MESSAGE_EN;
			}
		}

		public void setIsConfirmedPasswordValid(boolean isConfirmedPasswordValid) {
			if (!isConfirmedPasswordValid) {
				mIsValid = false;
				mIsConfirmedPasswordValid = false;
				mConfirmedPasswordMessage = BAD_CONFIRMED_PASSWORD_MESSAGE_EN;
			}
		}

		// getters & setters //////////////////////////////////////////////////////////

		public boolean isValid() {
			return mIsValid;
		}

		public boolean isNameValid() {
			return mIsNameValid;
		}

		public boolean isSurnameValid() {
			return mIsSurnameValid;
		}

		public boolean isPhoneValid() {
			return mIsPhoneValid;
		}

		public boolean isAddressValid() {
			return mIsAddressValid;
		}

		public boolean isEmailValid() {
			return mIsEmailValid;
		}

		public boolean isNewPasswordValid() {
			return mIsNewPasswordValid;
		}

		public boolean isConfirmedPasswordValid() {
			return mIsConfirmedPasswordValid;
		}

		public String getNameMessage() {
			return mNameMessage;
		}

		public String getSurnameMessage() {
			return mSurnameMessage;
		}

		public String getPhoneMessage() {
			return mPhoneMessage;
		}

		public String getAddressMessage() {
			return mAddressMessage;
		}

		public String getEmailMessage() {
			return mEmailMessage;
		}

		public String getNewPasswordMessage() {
			return mNewPasswordMessage;
		}

		public String getConfirmedPasswordMessage() {
			return mConfirmedPasswordMessage;
		}

		public InputValidationResult validateUnusedEmail(String email) {
			// TODO check with database if email is unused
			ArrayList<String> emails = new ArrayList<String>();
			emails.add("admin@shop.com");
			emails.add("mira@gmail.com");
			emails.add("jnapal@gmail.com");

			if (emails.contains(email)) {
				this.mIsValid = false;
				this.mIsEmailValid = false;
				this.mEmailMessage = "This email has already been used.";
			}

			return this;
		}

	}

	public static InputValidationResult validateInputs(HttpServletRequest request) {
		String name = request.getParameter(NAME_PARAM);
		String surname = request.getParameter(SURNAME_PARAM);
		String phone = request.getParameter(PHONE_PARAM);
		String address = request.getParameter(ADDRESS_PARAM);
		String email = request.getParameter(EMAIL_PARAM);
		String newPassword = request.getParameter(NEW_PASSWORD_PARAM);
		String confirmedPassword = request.getParameter(CONFIRMED_PASSWORD_PARAM);

		InputValidationResult res = new InputValidationResult();

		if (name != null)
			res.setIsNameValid(UserDataValidator.isNameValid(name));
		if (surname != null)
			res.setIsSurnameValid(UserDataValidator.isSurnameValid(surname));
		if (phone != null)
			res.setIsPhoneValid(UserDataValidator.isPhoneValid(phone));
		if (address != null)
			res.setIsAddressValid(UserDataValidator.isAddressValid(address));
		if (email != null)
			res.setIsEmailValid(UserDataValidator.isEmailValid(email));
		if (newPassword != null
				&& confirmedPassword != null & (!newPassword.isEmpty() || !confirmedPassword.isEmpty())) {
			res.setIsPasswordValid(UserDataValidator.isPasswordValid(newPassword));
			res.setIsConfirmedPasswordValid(UserDataValidator.isNewPasswordValid(newPassword, confirmedPassword));
		}

		return res;
	}

}

package request_handlers.users;

import request_handlers.RequestHandler;
import utils.UserDataValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserProfileRequestHandler implements RequestHandler {
	
	// CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // params
    public static final String NAME_PARAM               = "name";
    public static final String SURNAME_PARAM            = "surname";
    public static final String PHONE_PARAM              = "phone";
    public static final String ADDRESS_PARAM            = "address";
    public static final String EMAIL_PARAM              = "email";
    public static final String NEW_PASSWORD_PARAM       = "new_password";
    public static final String CONFIRMED_PASSWORD_PARAM = "confirmed_password";
    public static final String VALIDATION_RESULT_PARAM  = "buyer_profile_edit_result";

    // messages
    public static final String BAD_NAME_MESSAGE_EN               = "Provided name is wrong";
    public static final String BAD_SURNAME_MESSAGE_EN            = "Provided surname is wrong";
    public static final String BAD_PHONE_MESSAGE_EN              = "Provided phone is wrong";
    public static final String BAD_ADDRESS_MESSAGE_EN            = "Provided address is wrong";
    public static final String BAD_EMAIL_MESSAGE_EN              = "Provided email is wrong";
    public static final String BAD_PASSWORD_MESSAGE_EN           = "Password must have at least " + UserDataValidator.MIN_PASS_LEN + " non white characters and can't be longer than " + UserDataValidator.MAX_PASS_LEN;
    public static final String BAD_CONFIRMED_PASSWORD_MESSAGE_EN = "Passwords must be the same!";

    // jsp
    public static final String BUYER_PROFILE_PATH_PARAM 	= "buyer_profile_edit_path";
    public static final String SELLER_PROFILE_PATH_PARAM 	= "seller_profile_edit_path";
    public static final String ADMIN_PROFILE_PATH_PARAM 	= "admin_profile_edit_path";

    // fields //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String          mNameParamName;
    private String          mSurnameParamName;
    private String          mPhoneParamName;
    private String          mAddrParamName;
    private String          mEmailParamName;
    private String          mNewPassParamName;
    private String          mConfirmedPassParamName;
    private String          mValidatResultParamName;
    private String 			mBuyerProfilePath;
    private String			mSellerProfilePath;
    private String			mAdminProfilePath;
    private String 			mRequestExtension;
    private String			mEditBuyerRequest;
    private String			mEditSellerRequest;
    private String			mEditAdminRequest;
    
    
    
    public EditUserProfileRequestHandler(ServletContext context, String requestExtension)
    {
    	initParams(context);
    	
    	mRequestExtension 	= requestExtension;
    	mEditBuyerRequest 	= "editBuyerProfile" 	+ mRequestExtension;
    	mEditSellerRequest 	= "editSellerProfile" 	+ mRequestExtension;
    	mEditAdminRequest	= "editAdminProfile"	+ mRequestExtension;
    }



    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    { 
    	InputValidationResult validationResult = validateInputs(request);
        request.setAttribute(mValidatResultParamName, validationResult);
  
        String servletPath = request.getServletPath();
        
        String[] pathParts = servletPath.split("/");
        if (pathParts.length > 0)
        {
        	servletPath = pathParts[pathParts.length - 1];
        	
            if (servletPath.contentEquals(mEditBuyerRequest))
            	request.getRequestDispatcher(mBuyerProfilePath).forward(request, response);
            else if (servletPath.contentEquals(mEditSellerRequest))
            	request.getRequestDispatcher(mSellerProfilePath).forward(request, response);
            else if (servletPath.contentEquals(mEditAdminRequest))
            	request.getRequestDispatcher(mAdminProfilePath).forward(request, response);
        }
    }
    
    
    
    private void initParams(ServletContext context)
    {
        mNameParamName          = context.getInitParameter(NAME_PARAM);
        mSurnameParamName       = context.getInitParameter(SURNAME_PARAM);
        mPhoneParamName         = context.getInitParameter(PHONE_PARAM);
        mAddrParamName          = context.getInitParameter(ADDRESS_PARAM);
        mEmailParamName         = context.getInitParameter(EMAIL_PARAM);
        mNewPassParamName       = context.getInitParameter(NEW_PASSWORD_PARAM);
        mConfirmedPassParamName = context.getInitParameter(CONFIRMED_PASSWORD_PARAM);
        mValidatResultParamName = context.getInitParameter(VALIDATION_RESULT_PARAM);
        mBuyerProfilePath       = context.getInitParameter(BUYER_PROFILE_PATH_PARAM);
        mSellerProfilePath		= context.getInitParameter(SELLER_PROFILE_PATH_PARAM);
        mAdminProfilePath		= context.getInitParameter(ADMIN_PROFILE_PATH_PARAM);
    }
    
    
    private InputValidationResult validateInputs(HttpServletRequest request)
    {
        String name                 = request.getParameter(mNameParamName);
        String surname              = request.getParameter(mSurnameParamName);
        String phone                = request.getParameter(mPhoneParamName);
        String address              = request.getParameter(mAddrParamName);
        String email                = request.getParameter(mEmailParamName);
        String newPassword          = request.getParameter(mNewPassParamName);
        String confirmedPassword    = request.getParameter(mConfirmedPassParamName);

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
        if (newPassword != null && confirmedPassword != null && (!newPassword.isEmpty() || !confirmedPassword.isEmpty()))
        {
        	res.setIsPasswordValid(UserDataValidator.isPasswordValid(newPassword));
            res.setIsConfirmedPasswordValid(UserDataValidator.isNewPasswordValid(newPassword, confirmedPassword));
        }
        
        return res;
    }
    
    
    
    /**
     * class for returning result of validation
     */
    public class InputValidationResult {

        private boolean mIsValid;
        private boolean mIsNameValid;
        private boolean mIsSurnameValid;
        private boolean mIsPhoneValid;
        private boolean mIsAddressValid;
        private boolean mIsEmailValid;
        private boolean mIsNewPasswordValid;
        private boolean mIsConfirmedPasswordValid;
        private String  mNameMessage;
        private String  mSurnameMessage;
        private String  mPhoneMessage;
        private String  mAddressMessage;
        private String  mEmailMessage;
        private String  mNewPasswordMessage;
        private String  mConfirmedPasswordMessage;


        InputValidationResult()
        {
            mIsValid = mIsNameValid = mIsSurnameValid = mIsPhoneValid = mIsAddressValid = 
            		mIsEmailValid = mIsNewPasswordValid = mIsConfirmedPasswordValid = true;

            mNameMessage = mSurnameMessage = mPhoneMessage = mAddressMessage = mEmailMessage = mNewPasswordMessage =
                    mConfirmedPasswordMessage = "";
        }



        public void setIsNameValid(boolean isNameValid)
        {
            if (!isNameValid)
            {
                mIsValid        = false;
                mIsNameValid    = false;
                mNameMessage    = BAD_NAME_MESSAGE_EN;
            }
        }



        public void setIsSurnameValid(boolean isSurnameValid)
        {
            if (!isSurnameValid)
            {
                mIsValid        = false;
                mIsSurnameValid = false;
                mSurnameMessage = BAD_SURNAME_MESSAGE_EN;
            }
        }



        public void setIsPhoneValid(boolean isPhoneValid)
        {
            if (!isPhoneValid)
            {
                mIsValid        = false;
                mIsPhoneValid   = false;
                mPhoneMessage   = BAD_PHONE_MESSAGE_EN;
            }
        }



        public void setIsAddressValid(boolean isAddressValid)
        {
            if (!isAddressValid)
            {
                mIsValid        = false;
                mIsAddressValid = false;
                mAddressMessage = BAD_ADDRESS_MESSAGE_EN;
            }
        }



        public void setIsEmailValid(boolean isEmailValid)
        {
            if (!isEmailValid)
            {
                mIsValid        = false;
                mIsEmailValid   = false;
                mEmailMessage   = BAD_EMAIL_MESSAGE_EN;
            }
        }



        public void setIsPasswordValid(boolean isNewPasswordValid)
        {
            if (!isNewPasswordValid)
            {
                mIsValid            = false;
                mIsNewPasswordValid = false;
                mNewPasswordMessage = BAD_PASSWORD_MESSAGE_EN;
            }
        }



        public void setIsConfirmedPasswordValid(boolean isConfirmedPasswordValid)
        {
            if (!isConfirmedPasswordValid)
            {
                mIsValid                    = false;
                mIsConfirmedPasswordValid   = false;
                mConfirmedPasswordMessage   = BAD_CONFIRMED_PASSWORD_MESSAGE_EN;
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
        
    }
}

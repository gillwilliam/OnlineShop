package request_handlers.users;

import request_handlers.RequestHandler; 
import utils.UserDataValidator;
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
import beans.session.UserBean;
import entities.User;
import java.io.IOException;
import javax.persistence.EntityManager;

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
    public static final String UPDATE_RESULT_PARAM  	= "user_profile_update_result";
    public static final String USER_ATTR_PARAM			= "signed_user_attribute_name";
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
    private String mNameParamName;
    private String mSurnameParamName;
    private String mPhoneParamName;
    private String mAddrParamName;
    private String mEmailParamName;
    private String mNewPassParamName;
    private String mConfirmedPassParamName;
    private String mValidatResultParamName;
    private String mUpdateResultParamName;
    private String mUseOtherUserParamName;
    private String mUserAttr;
    private String mBuyerProfilePath;
    private String mSellerProfilePath;
    private String mAdminProfilePath;
    private String mRequestExtension;
    private String mEditBuyerRequest;
    private String mEditSellerRequest;
    private String mEditAdminRequest;
    
    // persistence
    private EntityManager mEntityManager;
    private UserTransaction mUserTransaction;
    
    
    
    public EditUserProfileRequestHandler(ServletContext context, String requestExtension, EntityManager entityManager, 
    		UserTransaction userTransaction)
    {
    	initParams(context);
    	
    	mRequestExtension 	= requestExtension;
    	mEditBuyerRequest 	= "editBuyerProfile" 	+ mRequestExtension;
    	mEditSellerRequest 	= "editSellerProfile" 	+ mRequestExtension;
    	mEditAdminRequest	= "editAdminProfile"	+ mRequestExtension;
    	
    	mEntityManager 		= entityManager;
    	mUserTransaction 	= userTransaction;
    }



    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    { 
    	// data validation
    	InputValidationResult validationResult = validateInputs(request);
        request.setAttribute(mValidatResultParamName, validationResult);
        
        // update in database
        UpdateInDBResult updateResult = null;      
        if (validationResult.isValid())
        	updateResult = updateInDB(request);  
        request.setAttribute(mUpdateResultParamName, updateResult);
        
        // redirecting to a proper jsp
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
        mUpdateResultParamName	= context.getInitParameter(UPDATE_RESULT_PARAM);
        mUseOtherUserParamName	= "otherUser";
        mBuyerProfilePath       = context.getInitParameter(BUYER_PROFILE_PATH_PARAM);
        mSellerProfilePath		= context.getInitParameter(SELLER_PROFILE_PATH_PARAM);
        mAdminProfilePath		= context.getInitParameter(ADMIN_PROFILE_PATH_PARAM);
        mUserAttr				= context.getInitParameter(USER_ATTR_PARAM);
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
     * should be called only after data in request was validated. Updates
     * user data in database
     * @param request
     * @return
     */
    private UpdateInDBResult updateInDB(HttpServletRequest request)
    {
    	UpdateInDBResult res 	= new UpdateInDBResult();
    	String email 			= request.getParameter(mEmailParamName);
    	User user 				= mEntityManager.find(User.class, email);
    	
    	if (user != null)
    	{
    		changeUserEntityData(user, request);
    		
    		try 
    		{
				mUserTransaction.begin();
				mEntityManager.merge(user);
				mUserTransaction.commit();
				
				updateInSession(request, user);	// if user edited it's profile then change data in session
				
				res.isUpdateSuccessful 	= true;
				res.isUserInDatabase	= true;
				res.message				= "successful update";
			} 
    		catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | 
    				HeuristicMixedException | HeuristicRollbackException e) 
    		{
				res.isUpdateSuccessful 	= false;
				res.isUserInDatabase	= true;
				res.message				= e.getClass() + ":" + e.getMessage();
				e.printStackTrace();
			}
    	}
    	else
    	{
    		res.isUserInDatabase	= false;
    		res.isUpdateSuccessful 	= false;
    		res.message 			= "User is no longer in the database";
    	}
    	
    	return res;
    }
    
    
    
    private void changeUserEntityData(User user, HttpServletRequest request)
    {
    	String name                 = request.getParameter(mNameParamName);
        String surname              = request.getParameter(mSurnameParamName);
        String phone                = request.getParameter(mPhoneParamName);
        String address              = request.getParameter(mAddrParamName);
        String email                = request.getParameter(mEmailParamName);
        String newPassword          = request.getParameter(mNewPassParamName);
        
        user.setFirstName(name);
        user.setLastName(surname);
        user.setPhone(phone);
        user.setAddress(address);
        user.setEmail(email);
        if (newPassword != null && !newPassword.isEmpty())
        	user.setPassword(newPassword);
    }
    
    
    
    private void updateInSession(HttpServletRequest request, User user)
    {
    	String otherUserStr = request.getParameter(mUseOtherUserParamName);
    	boolean otherUser 	= false;
    	
    	if (otherUserStr != null)
    		otherUser = Boolean.parseBoolean(otherUserStr);
    	
    	if (!otherUser)
    	{
    		HttpSession session = request.getSession();
    		UserBean currUser = (UserBean) session.getAttribute(mUserAttr);
    		
    		if (currUser != null)
    			currUser.initWithEntity(user);
    	}
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
    
    
    
    // case classes ///////////////////////////////////////////////////////////////////////////////////////
    
    
    
    public class UpdateInDBResult {
    	public boolean isUserInDatabase;
    	public boolean isUpdateSuccessful;
    	public String message;
    }
}

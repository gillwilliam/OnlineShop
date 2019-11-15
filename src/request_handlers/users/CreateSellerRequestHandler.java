package request_handlers.users;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import beans.session.SellerBean;
import entities.User;
import request_handlers.RequestHandler;
import utils.UserDataValidator;

public class CreateSellerRequestHandler implements RequestHandler {

	// CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // params
    public static final String NAME_PARAM               = "name";
    public static final String SURNAME_PARAM            = "surname";
    public static final String PHONE_PARAM              = "phone";
    public static final String EMAIL_PARAM              = "email";
    public static final String NEW_PASSWORD_PARAM       = "new_password";
    public static final String CONFIRMED_PASSWORD_PARAM = "confirmed_password";
    public static final String VALIDATION_RESULT_PARAM  = "user_profile_edit_result";
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
    public static final String SELLER_CREATION_PATH_PARAM 	= "seller_creation_path";
    public static final String USERS_MAINTENANCE_PATH_PARAM	= "users_maintenance_path";

    // fields //////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    
    // persistence
    private EntityManager mEntityManager;
    private UserTransaction mUserTransaction;
    
    
    
    public CreateSellerRequestHandler(ServletContext context, EntityManager entityManager, 
    		UserTransaction userTransaction)
    {
    	initParams(context);
    	
    	mEntityManager 		= entityManager;
    	mUserTransaction 	= userTransaction;
    }



    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    { 
    	SellerBean newSeller = createSellerBeanFromRequest(request);
    	
    	// data validation
    	InputValidationResult validationResult = validateInputs(newSeller, request.getParameter(mConfirmedPassParamName));
        request.setAttribute(mValidatResultParamName, validationResult);
        
        // update in database
        AddToDBResult addResult = null;      
        if (validationResult.isValid())
        	addResult = addToDB(newSeller);  
        request.setAttribute(mUpdateResultParamName, addResult);
        
        // redirecting to appropriate page
        if (!validationResult.isValid())
        	request.getRequestDispatcher(mSellerCreationPath).forward(request, response);
        else if (!addResult.isUpdateSuccessful)
        	request.getRequestDispatcher(mSellerCreationPath).forward(request, response);
        else
        	request.getRequestDispatcher(mUsersMaintenancePath).forward(request, response);
    }
    
    
    
    private void initParams(ServletContext context)
    {
        mNameParamName          = context.getInitParameter(NAME_PARAM);
        mSurnameParamName       = context.getInitParameter(SURNAME_PARAM);
        mPhoneParamName         = context.getInitParameter(PHONE_PARAM);
        mEmailParamName         = context.getInitParameter(EMAIL_PARAM);
        mNewPassParamName       = context.getInitParameter(NEW_PASSWORD_PARAM);
        mConfirmedPassParamName = context.getInitParameter(CONFIRMED_PASSWORD_PARAM);
        mValidatResultParamName = context.getInitParameter(VALIDATION_RESULT_PARAM);
        mUpdateResultParamName	= context.getInitParameter(UPDATE_RESULT_PARAM);
        mSellerCreationPath		= context.getInitParameter(SELLER_CREATION_PATH_PARAM);
        mUsersMaintenancePath	= context.getInitParameter(USERS_MAINTENANCE_PATH_PARAM);
    }
    
    
    private InputValidationResult validateInputs(SellerBean seller, String confirmedPassword)
    {
        InputValidationResult res = new InputValidationResult();
        
    	res.setIsNameValid(UserDataValidator.isNameValid(seller.getName()));
    	res.setIsSurnameValid(UserDataValidator.isSurnameValid(seller.getSurname()));
    	res.setIsPhoneValid(UserDataValidator.isPhoneValid(seller.getPhone()));
    	res.setIsEmailValid(UserDataValidator.isEmailValid(seller.getEmail()));
    	
        if (confirmedPassword != null)
        {
        	res.setIsPasswordValid(UserDataValidator.isPasswordValid(seller.getPassword()));
            res.setIsConfirmedPasswordValid(UserDataValidator.isNewPasswordValid(seller.getPassword(), confirmedPassword));
        }
        else
        {
        	res.setIsPasswordValid(false);
        	res.setIsConfirmedPasswordValid(false);
        }
        
        return res;
    }
    
    
    
    /**
     * should be called only after data in request was validated. Updates
     * user data in database
     * @param request
     * @return
     */
    private AddToDBResult addToDB(SellerBean userBean)
    {
    	AddToDBResult res 	= new AddToDBResult();
    	String email 		= userBean.getEmail();
    	User userEntity 	= mEntityManager.find(User.class, email);
    	
    	if (userEntity == null)
    	{
    		userEntity = new User();
			userEntity.updateFromUserBean(userBean);
			
    		try 
    		{
				mUserTransaction.begin();
//				mEntityManager.persist(userEntity);
				mEntityManager.createNativeQuery("INSERT INTO users (email, password, firstName, lastName, address, phone, type) VALUES (?,?,?,?,?,?,?)")
			      .setParameter(1, userEntity.getEmail())
			      .setParameter(2, userEntity.getPassword())
			      .setParameter(3, userEntity.getFirstName())
			      .setParameter(4, userEntity.getLastName())
			      .setParameter(5, userEntity.getAddress())
			      .setParameter(6, userEntity.getPhone())
			      .setParameter(7, userEntity.getType())
			      .executeUpdate();
				mUserTransaction.commit();

				res.isEmailUnique		= true;
				res.isUpdateSuccessful 	= true;
				res.message				= "You successfully created the new seller";
			} 
    		catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | 
    				HeuristicMixedException | HeuristicRollbackException e) 
    		{
				res.isUpdateSuccessful 	= false;
				res.isEmailUnique		= true;
				res.message				= "error occurred during adding user to the data base. Try later";
				e.printStackTrace();
			}
    	}
    	else
    	{
    		res.isEmailUnique		= false;
    		res.isUpdateSuccessful 	= false;
    		res.message 			= "User with such email already exists in the database";
    	}
    	
    	return res;
    }
    
    
    
    private SellerBean createSellerBeanFromRequest(HttpServletRequest request)
    {
    	String name        = request.getParameter(mNameParamName);
        String surname     = request.getParameter(mSurnameParamName);
        String phone       = request.getParameter(mPhoneParamName);
        String email       = request.getParameter(mEmailParamName);
        String newPassword = request.getParameter(mNewPassParamName);
        
        name 		= name == null ? "" : name;
        surname 	= surname == null ? "" : surname;
        phone 		= phone == null ? "" : phone;
        email 		= email == null ? "" : email;
        newPassword = newPassword == null ? "" : newPassword;
        
        SellerBean seller = new SellerBean();
        
        seller.setName(name);
        seller.setSurname(surname);
        seller.setPhone(phone);
        seller.setEmail(email);
        seller.setPassword(newPassword);
        
        return seller;
    }
    
    
    
    /**
     * class for returning result of validation
     */
    public class InputValidationResult {

        private boolean mIsValid;
        private boolean mIsNameValid;
        private boolean mIsSurnameValid;
        private boolean mIsPhoneValid;
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
            mIsValid = mIsNameValid = mIsSurnameValid = mIsPhoneValid = 
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
    
    
    
    public class AddToDBResult {
    	public boolean isEmailUnique;
    	public boolean isUpdateSuccessful;
    	public String message;
    }
}

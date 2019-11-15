package request_handlers.users;

import request_handlers.RequestHandler;
import utils.UserDataValidator;
import utils.UserDataValidator.InputValidationResult;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditUserProfileRequestHandler implements RequestHandler {
	
    // params
    public static final String NAME_PARAM               = "name";
    public static final String SURNAME_PARAM            = "surname";
    public static final String PHONE_PARAM              = "phone";
    public static final String ADDRESS_PARAM            = "address";
    public static final String EMAIL_PARAM              = "email";
    public static final String NEW_PASSWORD_PARAM       = "new_password";
    public static final String CONFIRMED_PASSWORD_PARAM = "confirmed_password";
    public static final String VALIDATION_RESULT_PARAM  = "buyer_profile_edit_result";
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

    	InputValidationResult validationResult = UserDataValidator.validateInputs(request);

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
    
}
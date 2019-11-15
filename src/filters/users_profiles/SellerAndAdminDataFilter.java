package filters.users_profiles;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class SellerAndAdminDataFilter
 */
@WebFilter("/SellerAndAdminDataFilter")
public class SellerAndAdminDataFilter implements Filter {

	// CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String NAME_PARAM               = "name";
    public static final String SURNAME_PARAM            = "surname";
    public static final String PHONE_PARAM              = "phone";
    public static final String EMAIL_PARAM              = "email";
    public static final String HOMEPAGE_PATH_PARAM		= "homepage_path";
    
	// fields /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String mNameParamName;
    private String mSurnameParamName;
    private String mPhoneParamName;
    private String mEmailParamName;
    private String mHomepagePath;
    


	public void init(FilterConfig fConfig) throws ServletException 
	{
		ServletContext context = fConfig.getServletContext();
		
		mNameParamName          = context.getInitParameter(NAME_PARAM);
        mSurnameParamName       = context.getInitParameter(SURNAME_PARAM);
        mPhoneParamName         = context.getInitParameter(PHONE_PARAM);
        mEmailParamName         = context.getInitParameter(EMAIL_PARAM);
        mHomepagePath			= context.getInitParameter(HOMEPAGE_PATH_PARAM);
	}
	
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		String name 	= request.getParameter(mNameParamName);
		String surname 	= request.getParameter(mSurnameParamName);
		String phone	= request.getParameter(mPhoneParamName);
		String email	= request.getParameter(mEmailParamName);
		
		if (name == null || surname == null || phone == null || email == null)
			request.getRequestDispatcher(mHomepagePath).forward(request, response);
		else
			chain.doFilter(request, response);
	}


}

package filters.authentication;

import java.io.IOException; 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.session.AdminBean;
import beans.session.UserBean;


@WebFilter("/OnlyAdminFilter")
public class OnlyAdminFilter implements Filter {

	// fields ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String mUserSessionAttrParamName;
	private String mLoginPagePath;
	
	
	
	public void init(FilterConfig fConfig) throws ServletException 
	{
		ServletContext context 		= fConfig.getServletContext();
		mUserSessionAttrParamName 	= context.getInitParameter("signed_user_attribute_name");
		mLoginPagePath				= context.getInitParameter("login_path");
	}
	

	/**
	 * checks if user is signed in and if is admin. If no, then redirects to login page
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		if (request instanceof HttpServletRequest)
		{
			HttpServletRequest httpReq 	= (HttpServletRequest) request;	
			HttpSession session			= httpReq.getSession(true);
			UserBean user 				= (UserBean) session.getAttribute(mUserSessionAttrParamName);
			
			if (user != null && user instanceof AdminBean)
				chain.doFilter(request, response);
			else
				request.getRequestDispatcher(mLoginPagePath).forward(request, response);
		}
		else
			request.getRequestDispatcher(mLoginPagePath).forward(request, response);
	}



}

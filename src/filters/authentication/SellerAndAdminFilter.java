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
import beans.session.SellerBean;
import beans.session.UserBean;

@WebFilter("/SellerAndAdminFilter")
public class SellerAndAdminFilter implements Filter {
	
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
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		if (request instanceof HttpServletRequest)
		{
			HttpServletRequest httpReq 	= (HttpServletRequest) request;	
			HttpSession session			= httpReq.getSession(true);
			UserBean user 				= (UserBean) session.getAttribute(mUserSessionAttrParamName);
			
			if (user != null && (user instanceof AdminBean || user instanceof SellerBean))
				chain.doFilter(request, response);
			else
				request.getRequestDispatcher(mLoginPagePath).forward(request, response);
		}
		else
			request.getRequestDispatcher(mLoginPagePath).forward(request, response);
	}

}

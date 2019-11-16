package filters;

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

import entities.Admin;
import entities.User;

@WebFilter("/OnlyAdminFilter")
public class OnlyAdminFilter implements Filter {

	// fields
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String mUserSessionAttrParamName;
	private String mLoginPagePath;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext context = fConfig.getServletContext();
		mUserSessionAttrParamName = context.getInitParameter("signed_user_attribute_name");
		mLoginPagePath = context.getInitParameter("login_path");
	}

	/**
	 * checks if user is signed in and if is admin. If no, then redirects to login
	 * page
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpReq = (HttpServletRequest) request;
			HttpSession session = httpReq.getSession(true);
			User user = (User) session.getAttribute(mUserSessionAttrParamName);

			if (user != null && user instanceof Admin)
				chain.doFilter(request, response);
			else
				request.getRequestDispatcher(mLoginPagePath).forward(request, response);
		} else
			request.getRequestDispatcher(mLoginPagePath).forward(request, response);
	}

}

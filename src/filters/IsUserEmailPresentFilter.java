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

@WebFilter("/IsUserEmailPresentFilter")
public class IsUserEmailPresentFilter implements Filter {

	// fields
	// /////////////////////////////////////////////////////////////////////////////////////////
	private String mEmailParamName;
	private String mHomepagePath;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext context = fConfig.getServletContext();

		mEmailParamName = context.getInitParameter("email");
		mHomepagePath = context.getInitParameter("homepage_path");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request.getParameter(mEmailParamName) == null)
			request.getRequestDispatcher(mHomepagePath).forward(request, response);
		else
			chain.doFilter(request, response);
	}

}

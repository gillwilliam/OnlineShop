package request_handlers;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainRequestHandler implements RequestHandler {
	private static final long serialVersionUID = 1L;

	// private static final String CHECKOUT_JSP = "/product/checkout.jsp";
	private static final String MAIN_JSP = "/main.jsp";

	private ServletConfig config;

	private String mRequestExtension;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainRequestHandler(ServletContext context, String requestExtension) {
		super();

		mRequestExtension = requestExtension;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// get the other params and insert them?

		// redirect to home?
		response.sendRedirect(request.getContextPath() + MAIN_JSP);

	}

}

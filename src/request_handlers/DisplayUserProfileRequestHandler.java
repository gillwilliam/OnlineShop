package request_handlers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayUserProfileRequestHandler implements RequestHandler {
	// CONST
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// jsp
	public static final String BUYER_PROFILE_PATH_PARAM = "buyer_profile_edit_path";
	public static final String SELLER_PROFILE_PATH_PARAM = "seller_profile_edit_path";
	public static final String ADMIN_PROFILE_PATH_PARAM = "admin_profile_edit_path";

	// fields
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String mBuyerProfilePath;
	private String mSellerProfilePath;
	private String mAdminProfilePath;
	private String mRequestExtension;
	private String mDisplayBuyerRequest;
	private String mDisplaySellerRequest;
	private String mDisplayAdminRequest;

	public DisplayUserProfileRequestHandler(ServletContext context, String requestExtension) {
		initParams(context);

		mRequestExtension = requestExtension;
		mDisplayBuyerRequest = "displayBuyerProfile" + mRequestExtension;
		mDisplaySellerRequest = "displaySellerProfile" + mRequestExtension;
		mDisplayAdminRequest = "displayAdminProfile" + mRequestExtension;
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirecting to a proper jsp
		String servletPath = request.getServletPath();

		String[] pathParts = servletPath.split("/");
		if (pathParts.length > 0) {
			servletPath = pathParts[pathParts.length - 1];

			if (servletPath.contentEquals(mDisplayBuyerRequest))
				request.getRequestDispatcher(mBuyerProfilePath).forward(request, response);
			else if (servletPath.contentEquals(mDisplaySellerRequest))
				request.getRequestDispatcher(mSellerProfilePath).forward(request, response);
			else if (servletPath.contentEquals(mDisplayAdminRequest))
				request.getRequestDispatcher(mAdminProfilePath).forward(request, response);
		}
	}

	private void initParams(ServletContext context) {
		mBuyerProfilePath = context.getInitParameter(BUYER_PROFILE_PATH_PARAM);
		mSellerProfilePath = context.getInitParameter(SELLER_PROFILE_PATH_PARAM);
		mAdminProfilePath = context.getInitParameter(ADMIN_PROFILE_PATH_PARAM);
	}
}

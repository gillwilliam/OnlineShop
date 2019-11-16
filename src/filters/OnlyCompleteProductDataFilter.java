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
import javax.servlet.http.Part;

@WebFilter("/OnlyCompleteProductDataFilter")
public class OnlyCompleteProductDataFilter implements Filter {

	// fields
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private String mIdParamName;
	private String mNameParamName;
	private String mCategoryIdParamName;
	private String mPriceParamName;
	private String mDescParamName;
	private String mQuantityParamName;
	private String mImageParamName;
	private String mHomepagePath;
	private String mOnlyPassParamName;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		ServletContext context = fConfig.getServletContext();

		mIdParamName = context.getInitParameter("id");
		mNameParamName = context.getInitParameter("name");
		mCategoryIdParamName = "category_id";
		mPriceParamName = context.getInitParameter("price");
		mDescParamName = context.getInitParameter("description");
		mQuantityParamName = context.getInitParameter("quantity");
		mImageParamName = context.getInitParameter("image");
		mHomepagePath = context.getInitParameter("homepage_path");
		mOnlyPassParamName = "pass_only";
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!(req instanceof HttpServletRequest)) {
			req.getRequestDispatcher(mHomepagePath).forward(req, response);
			return;
		}

		HttpServletRequest request = (HttpServletRequest) req;

		String onlyPassStr = request.getParameter(mOnlyPassParamName);
		if (onlyPassStr != null && Boolean.parseBoolean(onlyPassStr)) {
			String product = request.getParameter(mIdParamName);
			if (product != null)
				chain.doFilter(req, response);
			else
				request.getRequestDispatcher(mHomepagePath).forward(req, response);
		} else {
			String id = request.getParameter(mIdParamName);
			String name = request.getParameter(mNameParamName);
			String category = request.getParameter(mCategoryIdParamName);
			String price = request.getParameter(mPriceParamName);
			String desc = request.getParameter(mDescParamName);
			String quantity = request.getParameter(mQuantityParamName);

			Part image = null;
			String contentTypePre = request.getContentType();
			String[] contentTypeParts = contentTypePre == null ? new String[0] : contentTypePre.split(";");
			String contentType = null;
			if (contentTypeParts.length >= 1)
				contentType = contentTypeParts[0];

			if (contentType != null && contentType.equals("multipart/form-data"))
				image = request.getPart(mImageParamName);

			if (id == null || name == null || category == null || price == null || desc == null || quantity == null
					|| image == null)
				request.getRequestDispatcher(mHomepagePath).forward(req, response);
			else
				chain.doFilter(req, response);
		}
	}

}

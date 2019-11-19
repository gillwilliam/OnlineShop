package request_handlers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entities.Category;
import entities.Product;
import manager.CategoryManager;
import manager.ProductManager;
import utils.Price;
import utils.Result;

public class EditProductRequestHandler implements RequestHandler {

	// CONST
	// ///////////////////////////////////////////////////////////////////////////////////////
	public static long MAX_IMG_SIZE_IN_BYTES = 10000000L;

	// fields
	// //////////////////////////////////////////////////////////////////////////////////////
	private String mIdParamName;
	private String mNameParamName;
	private String mCategoryIdParamName;
	private String mPriceParamName;
	private String mDescParamName;
	private String mQuantityParamName;
	private String mOnlyPassParamName;
	private String mImageParamName;
	private String mEditPagePath;
	private String mResultAttr;
	private String mProductAttr;

	public EditProductRequestHandler(ServletContext context) {
		mIdParamName = context.getInitParameter("id");
		mNameParamName = context.getInitParameter("name");
		mCategoryIdParamName = "category_id";
		mPriceParamName = context.getInitParameter("price");
		mDescParamName = context.getInitParameter("description");
		mQuantityParamName = context.getInitParameter("quantity");
		mOnlyPassParamName = "pass_only";
		mImageParamName = context.getInitParameter("image");
		mEditPagePath = context.getInitParameter("product_edition_path");
		mResultAttr = context.getInitParameter("result");
		mProductAttr = context.getInitParameter("product_attr");
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String passOnlyStr = request.getParameter(mOnlyPassParamName);
		// this request handler may be used to edit product or just go to product
		// edition page,
		// because ProductBean is not as simple object to generate it from strings in
		// jsp, and
		// this request has all methods required to do that. If the request parameter
		// passOnly
		// is true, then this handler will just generate ProductBean from id and pass it
		// to jsp

		int id = Integer.parseInt(request.getParameter(mIdParamName));
		CategoryManager cm = new CategoryManager();
		ProductManager pm = new ProductManager();
		Product p = pm.findById(id);
		if (p != null && (passOnlyStr == null || !Boolean.parseBoolean(passOnlyStr))) {
			String name = request.getParameter(mNameParamName);
			Price price = obtainPrice(request.getParameter(mPriceParamName));
			int categoryId = Integer.parseInt(request.getParameter(mCategoryIdParamName));
			Category c = cm.findById(categoryId);
			String desc = request.getParameter(mDescParamName);
			int quantity = Integer.parseInt(request.getParameter(mQuantityParamName));
			Part filePart = request.getPart(mImageParamName);

			byte[] data = new byte[(int) filePart.getSize()];
			filePart.getInputStream().read(data, 0, data.length);
			pm.edit(p, name, c, price, desc, quantity, data);

			request.setAttribute(mResultAttr, new Result(true, "good"));
		}

		request.setAttribute(mProductAttr, p);
		request.getRequestDispatcher(mEditPagePath).forward(request, response);
	}

	static Price obtainPrice(String price) {
		String[] parts = price.split("\\.");

		int mainPart = 0;
		int fracPart = 0;

		if (parts.length == 2) {
			mainPart = Integer.parseInt(parts[0]);
			fracPart = Integer.parseInt(parts[1]);
		} else if (parts.length == 1) {
			mainPart = Integer.parseInt(parts[0]);
		}

		return new Price(mainPart, fracPart, "EUR");
	}

}

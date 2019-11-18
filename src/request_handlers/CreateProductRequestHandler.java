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
import entities.Image;
import utils.Price;
import utils.Result;

public class CreateProductRequestHandler implements RequestHandler {
	// fields
	// //////////////////////////////////////////////////////////////////////////////////////
	private String mNameParamName;
	private String mCategoryIdParamName;
	private String mPriceParamName;
	private String mDescParamName;
	private String mQuantityParamName;
	private String mImageParamName;
	private String mEditPagePath;
	private String mResultAttr;
	private String mProductAttr;

	public CreateProductRequestHandler(ServletContext context) {
		mNameParamName = context.getInitParameter("name");
		mCategoryIdParamName = "category_id";
		mPriceParamName = context.getInitParameter("price");
		mDescParamName = context.getInitParameter("description");
		mQuantityParamName = context.getInitParameter("quantity");
		mImageParamName = context.getInitParameter("image");
		mEditPagePath = context.getInitParameter("product_edition_path");
		mResultAttr = context.getInitParameter("result");
		mProductAttr = context.getInitParameter("product_attr");
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryManager cm = new CategoryManager();
		ProductManager pm = new ProductManager();

		String name = request.getParameter(mNameParamName);
		Price price = EditProductRequestHandler.obtainPrice(request.getParameter(mPriceParamName));
		int categoryId = Integer.parseInt(request.getParameter(mCategoryIdParamName));
		Category cat = cm.findById(categoryId);
		String desc = request.getParameter(mDescParamName);
		int quantity = Integer.parseInt(request.getParameter(mQuantityParamName));
		Part filePart = request.getPart(mImageParamName);

		ProductValidationResult validationResult = validateProduct(name, cat, desc, quantity);
		Product product = new Product(name, cat, price, desc, quantity, null);

		byte[] data = new byte[(int) filePart.getSize()];
		filePart.getInputStream().read(data, 0, data.length);
		Image img = new Image();
		img.setImage(data);
		product.setImage(img);
		
		pm.create(product);

		request.setAttribute(mProductAttr, product);
		request.setAttribute(mResultAttr, validationResult);
		request.getRequestDispatcher(mEditPagePath).forward(request, response);
	}

	private ProductValidationResult validateProduct(String name, Category category, String desc, int quantity) {
		boolean success = true;
		StringBuilder message = new StringBuilder();

		if (category == null) {
			success = false;
			message.append("category id is not present in the database<br>");
		}

		if (desc == null || desc.isEmpty()) {
			success = false;
			message.append("You must provide description<br>");
		}

		if (quantity < 0) {
			success = false;
			message.append("quantity must be greater or equal to 0");
		}

		return new ProductValidationResult(success, message.toString());
	}

	// case classes
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////

	public class ProductValidationResult extends Result {

		public ProductValidationResult(boolean isValid, String message) {
			super(isValid, message);
		}
	}

}
package request_handlers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entities.Category;
import entities.Product;
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
	private String mImgFolderPath;

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
		mImgFolderPath = context.getInitParameter("prod_img_folder_path");
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
		EntityManager em = (EntityManager) request.getServletContext().getAttribute("entity_manager");

		Product p = em.find(Product.class, id);
		if (p != null && (passOnlyStr == null || !Boolean.parseBoolean(passOnlyStr))) {
			String name = request.getParameter(mNameParamName);
			Price price = obtainPrice(request.getParameter(mPriceParamName));
			int categoryId = Integer.parseInt(request.getParameter(mCategoryIdParamName));
			Category c = em.find(Category.class, categoryId);
			String desc = request.getParameter(mDescParamName);
			int quantity = Integer.parseInt(request.getParameter(mQuantityParamName));
			Part img = request.getPart(mImageParamName);

			em.getTransaction().begin();
			editProduct(p, name, c, price, desc, quantity, img.toString());
			em.getTransaction().commit();

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

	private void editProduct(Product product, String name, Category category, Price price, String desc, int quantity,
			String imgName) {

		if (product.getName() != name) {
			product.setName(name);
		}

		if (!product.getCategory().equals(category)) {
			product.setCategory(category);
		}

		if (!product.getPrice().equals(price)) {
			product.setPrice(price);
		}

		if (!product.getDescription().equals(desc)) {
			product.setDescription(desc);
		}

		if (product.getQuantity() != quantity) {
			product.setQuantity(quantity);
		}

		if (imgName != null) {
			product.setImage(mImgFolderPath + imgName);
		}
	}
}

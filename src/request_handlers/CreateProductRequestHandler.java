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
import utils.Image;
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
	private String mImgFolderPath;

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
		mImgFolderPath = context.getInitParameter("prod_img_folder_path");
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EntityManager em = (EntityManager) request.getServletContext().getAttribute("entity_manager");

		String name = request.getParameter(mNameParamName);
		Price price = EditProductRequestHandler.obtainPrice(request.getParameter(mPriceParamName));
		int categoryId = Integer.parseInt(request.getParameter(mCategoryIdParamName));
		Category cat = em.find(Category.class, categoryId);
		String desc = request.getParameter(mDescParamName);
		int quantity = Integer.parseInt(request.getParameter(mQuantityParamName));
		Part img = request.getPart(mImageParamName);

		ProductValidationResult validationResult = validateProduct(name, cat, desc, quantity, img);
		Product product = new Product(name, cat, price, desc, quantity, null);

		if (validationResult.success) {
			// TODO image storage
			Result imgStoreRes = Image.storeImage(img, desc);
			if (imgStoreRes.success) {
				product.setImage(mImgFolderPath + imgStoreRes.message);
				validationResult.message = "You created a new product";
				try {
					em.getTransaction().begin();
					em.persist(product);
					em.getTransaction().commit();
				} catch (Exception e) {
					validationResult.message = e.getMessage();
				}
			} else {
				validationResult.message = imgStoreRes.message;
			}
		}

		request.setAttribute(mProductAttr, product);
		request.setAttribute(mResultAttr, validationResult);
		request.getRequestDispatcher(mEditPagePath).forward(request, response);
	}

	private ProductValidationResult validateProduct(String name, Category category, String desc, int quantity,
			Part img) {
		boolean success = true;
		StringBuilder message = new StringBuilder();

		if (!Product.isNameValid(name)) {
			success = false;
			message.append("name is invalid<br>");
		}

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

		if (img.getSize() > EditProductRequestHandler.MAX_IMG_SIZE_IN_BYTES) {
			success = false;
			message.append("image is too big. It cannot be bigger than "
					+ (int) (EditProductRequestHandler.MAX_IMG_SIZE_IN_BYTES / 1000000) + "MB");
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
package request_handlers.product;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import beans.general.ProductBean;
import categories.Category;
import categories.CategoryTree;
import payments.Price;
import request_handlers.RequestHandler;

public class EditProductRequestHandler implements RequestHandler {
	
	// fields //////////////////////////////////////////////////////////////////////////////////////
	private String mIdParamName;
	private String mNameParamName;
	private String mCategoryIdParamName;
	private String mPriceParamName;
	private String mDescParamName;
	private String mQuantityParamName;
	private String mOnlyPassParamName;
	private String mImageParamPath;
	private String mEditPagePath;
	private String mResultAttr;
	private String mProductAttr;
	private CategoryTree mCategoryTree;
	
	
	public EditProductRequestHandler(ServletContext context)
	{
		mIdParamName 			= context.getInitParameter("id");
		mNameParamName 			= context.getInitParameter("name");
		mCategoryIdParamName 	= "category_id";
		mPriceParamName 		= context.getInitParameter("price");
		mDescParamName 			= context.getInitParameter("description");
		mQuantityParamName 		= context.getInitParameter("quantity");
		mOnlyPassParamName		= "pass_only";
		mImageParamPath 		= context.getInitParameter("image");
		mEditPagePath			= context.getInitParameter("product_edition_path");
		mResultAttr				= context.getInitParameter("result");
		mProductAttr			= context.getInitParameter("product_attr");
		mCategoryTree			= (CategoryTree) context.getAttribute(context.getInitParameter("category_tree_attr"));
		if (mCategoryTree == null)
		{
			mCategoryTree = new CategoryTree();
			mCategoryTree.loadFromDatabase();
			context.setAttribute(context.getInitParameter("category_tree_attr"), mCategoryTree);
		}
	}
	

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String passOnlyStr = request.getParameter(mOnlyPassParamName);
		// this request handler may be used to edit product or just go to product edition page,
		// because ProductBean is not as simple object to generate it from strings in jsp, and
		// this request has all methods required to do that. If the request parameter passOnly
		// is true, then this handler will just generate ProductBean from id and pass it to jsp
		
		int id = Integer.parseInt(request.getParameter(mIdParamName));
		
		if (passOnlyStr == null || !Boolean.parseBoolean(passOnlyStr))
		{
			String name 	= request.getParameter(mNameParamName);
			Price price 	= obtainPrice(request.getParameter(mPriceParamName));
			int categoryId 	= Integer.parseInt(request.getParameter(mCategoryIdParamName));
			String desc 	= request.getParameter(mDescParamName);
			int quantity 	= Integer.parseInt(request.getParameter(mQuantityParamName));
			Part img 		= request.getPart(mImageParamPath);
			
			ProductEditionResult editionResult = editProduct(id, name, categoryId, price, desc, quantity, img);
			
			request.setAttribute(mResultAttr, editionResult);
		}
		
		request.setAttribute(mProductAttr, getProductFromDB(id));
		request.getRequestDispatcher(mEditPagePath).forward(request, response);
	}
	
	
	
	private Price obtainPrice(String price)
	{
		String[] parts 	= price.split("\\.");
		int mainPart 	= Integer.parseInt(parts[0]);
		int fracPart 	= Integer.parseInt(parts[1]);
		
		return new Price(mainPart, fracPart, "EUR");
	}
	
	
	
	private ProductEditionResult editProduct(int id, String name, int categoryId, Price price, String desc, int quantity, Part img)
	{
		ProductBean product = getProductFromDB(id);
		Category category 	= obtainCategory(categoryId);
		
		ProductEditionResult validationResult = validateProductData(product, id, category, name, quantity);
		
		if (validationResult.success)
		{
			String imgPath = storeImage(img);
			return updateProductInDB(product, name, category, price, desc, quantity, imgPath);
		}
		else
			return validationResult;
		
	}
	
	
	
	private ProductEditionResult validateProductData(ProductBean product, int productId, Category category, String name, int quantity)
	{
		boolean success 		= true;
		StringBuilder message 	= new StringBuilder();
		
		if (product == null)
			return new ProductEditionResult(false, "There is no product with id '" + productId + "' in the database");
		
		if (!product.isNameValid(name))
		{
			success = false;
			message.append("name is invalid<br>");
		}
		
		if (category == null)
		{
			success = false;
			message.append("category id is not present in the database<br>");
		}
		
		if (quantity < 0)
		{
			success = false;
			message.append("quantity must be greater or equal to 0");
		}
		
		return new ProductEditionResult(success, message.toString());
	}
	
	
	
	private ProductEditionResult updateProductInDB(ProductBean product, String name, Category category, Price price, String desc, 
			int quantity, String imgPath)
	{
		boolean wasChanged = false;
		
		if (product.getName() != name)
		{
			product.setName(name);
			wasChanged = true;
		}
		
		if (!product.getCategory().equals(category))
		{
			product.setCategory(category);
			wasChanged = true;
		}
		
		if (!product.getPrice().equals(price))
		{
			product.setPrice(price);
			wasChanged = true;
		}
		
		if (!product.getDescription().equals(desc))
		{
			product.setDescription(desc);
			wasChanged = true;
		}
		
		if (product.getQuantity() != quantity)
		{
			product.setQuantity(quantity);
			wasChanged = true;
		}
		
		if (imgPath != null)
		{
			product.setImagePath(imgPath);
			wasChanged = true;
		}
		
		if (wasChanged)
		{
			boolean updateSuccess = product.updateInDB();
			
			if (updateSuccess)
				return new ProductEditionResult(true, "successful product edition");
			else
				return new ProductEditionResult(false, "database error occurred");
		}
		else
			return new ProductEditionResult(true, "No things to change");
	}
	
	
	
	private ProductBean getProductFromDB(int productId)
	{
		return new ProductBean(productId, "test product", obtainCategory(3), new Price(100, 3, "EUR"), 
				"test description", 10, "asdf");
	}
	
	
	
	private Category obtainCategory(int id)
	{
		return mCategoryTree.find(id);
	}
	
	
	
	/**
	 * if image is != null then stores it and returns path to the image
	 * @param img
	 * @return 
	 */
	private String storeImage(Part img)
	{
		// TODO implement
		return null;
	}
	
	
	
	// case classes //////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public class ProductEditionResult {
		public boolean success;
		public String message;
		
		public ProductEditionResult(boolean success, String message)
		{
			this.success = success;
			this.message = message;
		}
	}

}

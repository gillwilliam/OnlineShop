package request_handlers.product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
	
	// CONST ///////////////////////////////////////////////////////////////////////////////////////
	public static long MAX_IMG_SIZE_IN_BYTES = 10000000L;
	
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
	private String mImgFolderPath;
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
		mImgFolderPath			= context.getInitParameter("prod_img_folder_path");
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
	
	
	
	static Price obtainPrice(String price)
	{
		String[] parts 	= price.split("\\.");
		
		int mainPart = 0;
		int fracPart = 0;
		
		if (parts.length == 2)
		{
			mainPart = Integer.parseInt(parts[0]);
			fracPart = Integer.parseInt(parts[1]);
		}
		else if (parts.length == 1)
		{
			mainPart = Integer.parseInt(parts[0]);
		}
		
		return new Price(mainPart, fracPart, "EUR");
	}
	
	
	
	private ProductEditionResult editProduct(int id, String name, int categoryId, Price price, String desc, int quantity, Part img)
	{
		ProductBean product = getProductFromDB(id);
		Category category 	= obtainCategory(categoryId);
		
		ProductEditionResult validationResult = validateProductData(product, id, category, name, desc, quantity);
		
		if (validationResult.success)
		{
			StoreImageResult imgStoreRes = storeImage(img, product.getImagePath());
			String imgName				 = imgStoreRes.name;
			if (imgStoreRes.success)
				return updateProductInDB(product, name, category, price, desc, quantity, imgName);
			else
				return new ProductEditionResult(false, imgStoreRes.message);
		}
		else
			return validationResult;
		
	}
	
	
	
	private ProductEditionResult validateProductData(ProductBean product, int productId, Category category, String name, String desc,
			int quantity)
	{
		boolean success 		= true;
		StringBuilder message 	= new StringBuilder();
		
		if (product == null)
			return new ProductEditionResult(false, "There is no product with id '" + productId + "' in the database");
		
		if (!ProductBean.isNameValid(name))
		{
			success = false;
			message.append("name is invalid<br>");
		}
		
		if (category == null)
		{
			success = false;
			message.append("category id is not present in the database<br>");
		}
		
		if (desc == null || desc.isEmpty())
		{
			success = false;
			message.append("You must provide description<br>");
		}
		
		if (quantity < 0)
		{
			success = false;
			message.append("quantity must be greater or equal to 0");
		}
		
		return new ProductEditionResult(success, message.toString());
	}
	
	
	
	private ProductEditionResult updateProductInDB(ProductBean product, String name, Category category, Price price, String desc, 
			int quantity, String imgName)
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
		
		if (imgName != null)
		{
			product.setImagePath(mImgFolderPath + imgName);
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
				"test description", 10, "path");
	}
	
	
	
	private Category obtainCategory(int id)
	{
		return mCategoryTree.find(id);
	}
	
	
	
	/**
	 * if image is set then stores it and returns path to the image and also
	 * removes old image if present
	 * @param img
	 * @return 
	 */
	private StoreImageResult storeImage(Part img, String oldImgPath)
	{
		if (getFileName(img).isEmpty())
			return new StoreImageResult(true, "No image was specified", null);
		
		long imgSize = img.getSize();
		if (imgSize <= MAX_IMG_SIZE_IN_BYTES)
		{
			int imgSizeInt 		= (int) imgSize;
			byte[] imageBytes 	= new byte[imgSizeInt];
			InputStream in		= null;
			
			try 
			{
		 		in = img.getInputStream();
		 		
				if (in != null)
					in.read(imageBytes, 0, imgSizeInt);
				else
					return new StoreImageResult(false, "Cannot read image due to null input stream", null);
			}
			catch (IOException e)
			{
				return new StoreImageResult(false, "Cannot read image", null);
			}
			finally
			{
				try
				{
					if (in != null)
						in.close();
				}
				catch (IOException e)
				{
					return new StoreImageResult(false, "Cannot load file properly", null);
				}
			}
			
			StoreImageResult saveRes = saveImageOnDrive(imageBytes);
			
			if (saveRes.success)
			{
				StoreImageResult delOldImgRes = deleteOldImage(oldImgPath);
				
				if (delOldImgRes.success)
					return saveRes;
				else
					return delOldImgRes;
			}
			else
				return saveRes;
		}
		else
			return new StoreImageResult(false, "Image is too big. It cannot be bigger that " + 
					(int) (MAX_IMG_SIZE_IN_BYTES / 1000000) + "MB", null);
	}
	
	
	
	private StoreImageResult deleteOldImage(String oldImgPath)
	{
		// TODO test if works when connected to db
		if (oldImgPath == null || oldImgPath.isEmpty())
			return new StoreImageResult(true, "No old image", null);
		
		File oldImg = new File(oldImgPath);
		
		if (oldImg.delete())
			return new StoreImageResult(true, "successful delete", null);
		else
			return new StoreImageResult(false, "cannot remove previous image", null);
	}
	
	
	
	static String getFileName(final Part part) 
	{
	    final String partHeader = part.getHeader("content-disposition");
	    
	    for (String content : partHeader.split(";")) 
	    {
	        if (content.trim().startsWith("filename")) 
	        {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    
	    return null;
	}
	
	
	
	static String generateUniqueName()
	{
		long currNanos = System.nanoTime();
		
		return Long.toString(currNanos);
	}
	
	
	
	private StoreImageResult saveImageOnDrive(byte[] image)
	{
		FileOutputStream out = null;
		
		try
		{
			String name = generateUniqueName() + ".jpg";
			String path = mImgFolderPath + name;
			out = new FileOutputStream(new File(path));
			out.write(image);
			out.flush();
			
			return new StoreImageResult(true, "Successfuly stored image", name);
		}
		catch (IOException e)
		{
			return new StoreImageResult(false, "Cannot store image. Error during saving on the drive", null);
		}
		finally
		{
			try
			{
				if (out != null)
					out.close();
			}
			catch (IOException e)
			{
				return new StoreImageResult(false, "Image stored but a failure occured during closing a stream", null);
			}
		}
	}
	
	
	
	// case classes //////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public class ProductEditionResult extends Result {
		
		public ProductEditionResult(boolean success, String message)
		{
			super(success, message);
		}
	}
	
	
	
	public static class StoreImageResult {
		public boolean success;
		public String message;
		public String name;
		
		public StoreImageResult(boolean success, String message, String name) 
		{
			this.success = success;
			this.message = message;
			this.name    = name;
		}
	}

}

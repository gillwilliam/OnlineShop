package request_handlers.product;

import java.io.File;
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
import request_handlers.product.EditProductRequestHandler.StoreImageResult;

public class CreateProductRequestHandler implements RequestHandler {
	// fields //////////////////////////////////////////////////////////////////////////////////////
	private String mNameParamName;
	private String mCategoryIdParamName;
	private String mPriceParamName;
	private String mDescParamName;
	private String mQuantityParamName;
	private String mImageParamPath;
	private String mEditPagePath;
	private String mResultAttr;
	private String mProductAttr;
	private String mImgFolderPath;
	private CategoryTree mCategoryTree;
	
	
	public CreateProductRequestHandler(ServletContext context)
	{
		mNameParamName 			= context.getInitParameter("name");
		mCategoryIdParamName 	= "category_id";
		mPriceParamName 		= context.getInitParameter("price");
		mDescParamName 			= context.getInitParameter("description");
		mQuantityParamName 		= context.getInitParameter("quantity");
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
		String name 	= request.getParameter(mNameParamName);
		Price price 	= EditProductRequestHandler.obtainPrice(request.getParameter(mPriceParamName));
		int categoryId 	= Integer.parseInt(request.getParameter(mCategoryIdParamName));
		Category cat	= mCategoryTree.find(categoryId);
		String desc 	= request.getParameter(mDescParamName);
		int quantity 	= Integer.parseInt(request.getParameter(mQuantityParamName));
		Part img 		= request.getPart(mImageParamPath);
		
		ProductValidationResult validationResult = validateProduct(name, cat, desc, quantity, img);
		ProductBean product 	= new ProductBean(-1, name, cat, price, desc, quantity, null);
		
		if (validationResult.success)
		{
			StoreImageResult imgStoreRes = storeImage(img);
			if (imgStoreRes.success)
			{
				boolean dbUpdateSuccess = product.updateInDB();
				
				if (dbUpdateSuccess)
				{
					product.setImagePath(mImgFolderPath + imgStoreRes.name);
					validationResult.message = "You created a new product";
				}
				else
					validationResult.message = "cannot create product in database";
			}
			else
				validationResult.message = imgStoreRes.message;
		}
		
		System.out.println(price.toString());
		request.setAttribute(mProductAttr, product);
		request.setAttribute(mResultAttr, validationResult);
		request.getRequestDispatcher(mEditPagePath).forward(request, response);
	}
	
	
	
	private ProductValidationResult validateProduct(String name, Category category, String desc, int quantity, Part img)
	{
		boolean success 		= true;
		StringBuilder message 	= new StringBuilder();
		
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
		
		if (img.getSize() > EditProductRequestHandler.MAX_IMG_SIZE_IN_BYTES)
		{
			success = false;
			message.append("imgae is too big. It cannot be bigger than " + 
					(int) (EditProductRequestHandler.MAX_IMG_SIZE_IN_BYTES / 1000000) + "MB");
		}
		
		return new ProductValidationResult(success, message.toString());
	}
	
	
	
	private StoreImageResult storeImage(Part img)
	{
		if (EditProductRequestHandler.getFileName(img).isEmpty())
			return new StoreImageResult(true, "No image was specified", null);
		
		long imgSize 		= img.getSize();
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
		
		return saveImageOnDrive(imageBytes);
	}
	
	
	
	private StoreImageResult saveImageOnDrive(byte[] image)
	{
		FileOutputStream out = null;
		
		try
		{
			String name = EditProductRequestHandler.generateUniqueName() + ".jpg";
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
	
	
	
	// case classes ///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public class ProductValidationResult extends Result {
		
		public ProductValidationResult(boolean isValid, String message) 
		{
			super(isValid, message);
		}
	}
}

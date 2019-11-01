package request_handlers.categories;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.NotNull;

import categories.Category;
import categories.Category.NameValidationResult;
import categories.CategoryTree;
import request_handlers.RequestHandler;
import request_handlers.categories.exceptions.CategoryTreeNotPresentException;

public class RenameCategoryRequestHandler implements RequestHandler {
	
	// fields
	CategoryTree mCategoryTree;
	String mIdParamName;
	String mNameParamName;
	String mResultParamName;
	String mResultSuccessParamName;
	String mCategoryMaintenancePath;
	
	
	
	public RenameCategoryRequestHandler(@NotNull ServletContext context)
	{
		mCategoryTree = (CategoryTree) context.getAttribute("category_tree_attr");
		if (mCategoryTree == null) throw new CategoryTreeNotPresentException();
		
		mIdParamName 				= context.getInitParameter("id");
		mNameParamName 				= context.getInitParameter("name");
		mResultParamName 			= context.getInitParameter("category_maintenance_result_param");
		mCategoryMaintenancePath 	= context.getInitParameter("category_maintenance_path");
		mResultSuccessParamName		= context.getInitParameter("category_maintenance_result");
	}
	
	

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String idString		= request.getParameter(mIdParamName);
		if (idString == null)
		{
			dealWithNullCategory(request, response, -1, "unknown");
		}
		else
		{
			int id 				= Integer.parseInt(idString);
			Category category 	= mCategoryTree.find(id);
			String name 		= request.getParameter(mNameParamName);
			
			if (category != null)
			{
				NameValidationResult valRes = category.isNameValid(name);
				
				if (valRes.isValid())
					changeName(request, response, category, name);
				else
					dealWithIncorrectName(request, response, valRes);
			}
			else
				dealWithNullCategory(request, response, id, name);
		}
	}
	
	
	
	private void dealWithNullCategory(
			HttpServletRequest request, 
			HttpServletResponse response, 
			int id, 
			String name) throws ServletException, IOException
	{
		String errorMessage = "Category with id \"" + id + "\" and name \"" + name + "\" could not be found"; 
		request.setAttribute(mResultParamName, errorMessage);
		request.setAttribute(mResultSuccessParamName, "false");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}
	
	
	
	private void dealWithIncorrectName(
			HttpServletRequest request, 
			HttpServletResponse response, 
			NameValidationResult valRes) throws ServletException, IOException
	{
		StringBuilder message = new StringBuilder();
		
		if (!valRes.isContentValid)
			message.append("Name must contain only letters and cannot be empty or longer than " + Category.MAX_CATEGORY_NAME_LEN
					+ " characters. ");
		
		if (!valRes.isUnique)
			message.append("Name cannot be the same as other category's name that is in the same parent category");
		
		request.setAttribute(mResultParamName, message.toString());
		request.setAttribute(mResultSuccessParamName, "false");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}
	
	
	
	private void changeName(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Category category, 
			String name) throws ServletException, IOException
	{
		category.setName(name);
		
		String message = "Name successfully changed";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "true");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}

}

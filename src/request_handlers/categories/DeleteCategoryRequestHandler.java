package request_handlers.categories;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.NotNull;

import categories.Category;
import categories.CategoryTree;
import request_handlers.RequestHandler;
import request_handlers.categories.exceptions.CategoryTreeNotPresentException;

public class DeleteCategoryRequestHandler implements RequestHandler {

	// fields
	CategoryTree mCategoryTree;
	String mIdParamName;
	String mResultParamName;
	String mResultSuccessParamName;
	String mCategoryMaintenancePath;
			
			
			
	public DeleteCategoryRequestHandler(@NotNull ServletContext context)
		{
			mCategoryTree = (CategoryTree) context.getAttribute("category_tree_attr");
			if (mCategoryTree == null) throw new CategoryTreeNotPresentException();
				
			mIdParamName 				= context.getInitParameter("id");
			mResultParamName 			= context.getInitParameter("category_maintenance_result_param");
			mCategoryMaintenancePath 	= context.getInitParameter("category_maintenance_path");
			mResultSuccessParamName		= context.getInitParameter("category_maintenance_result");
		}
		
		

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String idStr = request.getParameter(mIdParamName);
		if (idStr == null)
		{
			dealWithWrongId(request, response, -1);
			return;
		}
		
		int id = Integer.parseInt(idStr);
		
		boolean isInTree = mCategoryTree.find(id) != null;
		if (!isInTree)
		{
			dealWithWrongId(request, response, id);
			return;
		}
			
		boolean isNotLastCategory = mCategoryTree.removeCategory(id);
		if (isNotLastCategory)
			successfulRemove(request, response);
		else
			dealWithLastCategory(request, response);		
	}
	
	
	
	private void dealWithWrongId(
			HttpServletRequest request, 
			HttpServletResponse response,
			int id) throws ServletException, IOException
	{
		String message = "the id \"" + id + "\" is incorrect";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "false");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}
	
	
	
	private void dealWithLastCategory(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String message = "you cannot remove all categories";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "false");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}
	
	
	
	private void successfulRemove(
			HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String message = "You succesfully deleted the category";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "true");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}

}

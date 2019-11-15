package request_handlers.categories;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.NotNull;

import categories.Category;
import categories.CategoryTree;
import request_handlers.RequestHandler;
import request_handlers.categories.exceptions.CategoryTreeNotPresentException;

public class AddCategoryRequestHandler implements RequestHandler {
	
	// fields
	CategoryTree mCategoryTree;
	String mParentIdParamName;
	String mCategoryNameParamName;
	String mResultParamName;
	String mResultSuccessParamName;
	String mCategoryMaintenancePath;
		
		
		
	public AddCategoryRequestHandler(@NotNull ServletContext context)
	{
		mCategoryTree = (CategoryTree) context.getAttribute("category_tree_attr");
		if (mCategoryTree == null) throw new CategoryTreeNotPresentException();
			
		mParentIdParamName 			= context.getInitParameter("id");
		mCategoryNameParamName 		= context.getInitParameter("name");
		mResultParamName 			= context.getInitParameter("category_maintenance_result_param");
		mCategoryMaintenancePath 	= context.getInitParameter("category_maintenance_path");
		mResultSuccessParamName		= context.getInitParameter("category_maintenance_result");
	}
	
	

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String name = obtainName(request, response);
		if (name == null) return;
		
		Category parent = obtainParent(request, response);
		
		int id 					= mCategoryTree.getUniqueId();
		Category newCategory 	= new Category(id, name, mCategoryTree);
		
		if (parent == null)
		{
			mCategoryTree.addRootCategory(newCategory);
			successfulFinish(request, response);
		}
		else
		{
			if (parent.addSubcategory(newCategory))
				successfulFinish(request, response);
			else
				dealWithWrongNameOrDBError(request, response, name);	// name is not unique in parent
		}
	}
	
	
	
	/**
	 * @param request
	 * @param response
	 * @return parent or null
	 * @throws ServletException
	 * @throws IOException
	 */
	private Category obtainParent(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		String parentIdStr = request.getParameter(mParentIdParamName);
		if (parentIdStr == null) return null;
		
		int parentId = Integer.parseInt(parentIdStr);
		
		Category parent = mCategoryTree.find(parentId);
		
		return parent;
	}
	
	
	
	/**
	 * tries to obtain name from request and checks if the name matches category
	 * name regular expression. If not, then takes control over redirection to jsp
	 * @param request
	 * @param response
	 * @return name or null. If null then also deals with redirection to jsp
	 * @throws ServletException
	 * @throws IOException
	 */
	private String obtainName(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		String name = request.getParameter(mCategoryNameParamName);
		
		if (name == null || !name.matches(Category.CATEGORY_NAME_REGEX))
		{
			dealWithWrongNameOrDBError(request, response, name);
			return null;
		}
		else
			return name;
	}
	
	
	
	private void dealWithWrongNameOrDBError(
			HttpServletRequest request, 
			HttpServletResponse response,
			String name) throws ServletException, IOException
	{
		String message = "The name \"" + name + "\" is wrong. It must have 1 - " + Category.MAX_CATEGORY_NAME_LEN 
				+ " characters and must contain only letters and spaces. If it meets requirements then try later";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "false");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}
	
	
	
	private void successfulFinish(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		String message = "You successfully added a new category";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "true");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}
	

}

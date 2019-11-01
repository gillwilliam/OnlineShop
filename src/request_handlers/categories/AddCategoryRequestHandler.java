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
		Category parent = obtainParent(request, response);
		if (parent == null) return;
		
		String name = obtainName(request, response);
		if (name == null) return;
		
		int id 					= mCategoryTree.getUniqueId();
		Category newCategory 	= new Category(id, name, mCategoryTree);
		
		if (parent.addSubcategory(newCategory))
			successfulFinish(request, response);
		else
			dealWithWrongName(request, response, name);	// name is not unique in parent
	}
	
	
	
	/**
	 * tries to obtain parent from category tree, if can't then takes control of redirection to jsp
	 * @param request
	 * @param response
	 * @return parent or null. If returns null then also deals with redirections to jsps
	 * @throws ServletException
	 * @throws IOException
	 */
	private Category obtainParent(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		String parentIdStr = request.getParameter(mParentIdParamName);
		if (parentIdStr == null)
		{
			dealWithWrongParent(request, response, -1);
			return null;
		}
		
		int parentId = Integer.parseInt(parentIdStr);
		
		Category parent = mCategoryTree.find(parentId);
		if (parent == null)
		{
			dealWithWrongParent(request, response, parentId);
			return null;
		}
		
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
			dealWithWrongName(request, response, name);
			return null;
		}
		else
			return name;
	}
	
	
	
	private void dealWithWrongParent(
			HttpServletRequest request,
			HttpServletResponse response,
			int parentId) throws ServletException, IOException
	{
		String message = "Couldn't find parent category with id \"" + parentId + "\"";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "false");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}
	
	
	
	private void dealWithWrongName(
			HttpServletRequest request, 
			HttpServletResponse response,
			String name) throws ServletException, IOException
	{
		String message = "The name \"" + name + "\" is wrong. It must have 1 - " + Category.MAX_CATEGORY_NAME_LEN 
				+ " characters and must contain only letters";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "false");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}
	
	
	
	private void successfulFinish(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		String message = "You successfully added a new subcategory";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "true");
		
		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}
	

}

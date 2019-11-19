package request_handlers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.NotNull;

import entities.Category;
import manager.CategoryManager;

public class AddCategoryRequestHandler implements RequestHandler {

	// fields
	String mParentIdParamName;
	String mCategoryNameParamName;
	String mResultParamName;
	String mResultSuccessParamName;
	String mCategoryMaintenancePath;

	public AddCategoryRequestHandler(@NotNull ServletContext context) {

		mParentIdParamName = context.getInitParameter("id");
		mCategoryNameParamName = context.getInitParameter("name");
		mResultParamName = context.getInitParameter("category_maintenance_result_param");
		mCategoryMaintenancePath = context.getInitParameter("category_maintenance_path");
		mResultSuccessParamName = context.getInitParameter("category_maintenance_result");
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = obtainName(request, response);
		if (name == null)
			return;
		CategoryManager cm = new CategoryManager("OnlineShop");
		String parentIdStr = request.getParameter(mParentIdParamName);
		Category parent = parentIdStr != null ? cm.findById(Integer.parseInt(parentIdStr)) : null;

		Category newCategory = new Category(name, parent);
		cm.create(newCategory);
		successfulFinish(request, response);
	}

	/**
	 * tries to obtain name from request and checks if the name matches category
	 * name regular expression. If not, then takes control over redirection to jsp
	 * 
	 * @param request
	 * @param response
	 * @return name or null. If null then also deals with redirection to jsp
	 * @throws ServletException
	 * @throws IOException
	 */
	private String obtainName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter(mCategoryNameParamName);

		if (name == null) {
			dealWithWrongNameOrDBError(request, response, name);
			return null;
		} else
			return name;
	}

	private void dealWithWrongNameOrDBError(HttpServletRequest request, HttpServletResponse response, String name)
			throws ServletException, IOException {
		String message = "Error creating category";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "false");

		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}

	private void successfulFinish(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "You successfully added a new category";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "true");

		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}

}

package request_handlers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.NotNull;

import entities.Category;
import manager.CategoryManager;

public class RenameCategoryRequestHandler implements RequestHandler {

	// fields
	String mIdParamName;
	String mNameParamName;
	String mResultParamName;
	String mResultSuccessParamName;
	String mCategoryMaintenancePath;

	public RenameCategoryRequestHandler(@NotNull ServletContext context) {
		mIdParamName = context.getInitParameter("id");
		mNameParamName = context.getInitParameter("name");
		mResultParamName = context.getInitParameter("category_maintenance_result_param");
		mCategoryMaintenancePath = context.getInitParameter("category_maintenance_path");
		mResultSuccessParamName = context.getInitParameter("category_maintenance_result");
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter(mIdParamName);
		CategoryManager cm = new CategoryManager();

		if (idString == null) {
			dealWithNullCategory(request, response, -1, "unknown");
		} else {
			int id = Integer.parseInt(idString);
			Category category = cm.findById(id);
			String name = request.getParameter(mNameParamName);
			if (name != null && category != null) {
				cm.edit(category, name);
				request.setAttribute(mResultParamName, "Name successfully changed");
				request.setAttribute(mResultSuccessParamName, "true");

				request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
			} else {
				dealWithNullCategory(request, response, id, name);
			}
		}

	}

	private void dealWithNullCategory(HttpServletRequest request, HttpServletResponse response, int id, String name)
			throws ServletException, IOException {
		String errorMessage = "Category with id \"" + id + "\" and name \"" + name + "\" could not be found";
		request.setAttribute(mResultParamName, errorMessage);
		request.setAttribute(mResultSuccessParamName, "false");

		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}

}

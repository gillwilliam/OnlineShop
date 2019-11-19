package request_handlers;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.istack.NotNull;

import entities.Category;
import manager.CategoryManager;

public class DeleteCategoryRequestHandler implements RequestHandler {

	// fields
	String mIdParamName;
	String mResultParamName;
	String mResultSuccessParamName;
	String mCategoryMaintenancePath;

	public DeleteCategoryRequestHandler(@NotNull ServletContext context) {
		mIdParamName = context.getInitParameter("id");
		mResultParamName = context.getInitParameter("category_maintenance_result_param");
		mCategoryMaintenancePath = context.getInitParameter("category_maintenance_path");
		mResultSuccessParamName = context.getInitParameter("category_maintenance_result");
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter(mIdParamName);
		if (idStr == null) {
			dealWithWrongId(request, response, -1);
			return;
		}

		CategoryManager cm = new CategoryManager();

		int id = Integer.parseInt(idStr);

		Category c = cm.findById(id);
		if (c == null) {
			dealWithWrongId(request, response, id);
			return;
		}

		cm.delete(c);
		successfulRemove(request, response);
	}

	private void dealWithWrongId(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		String message = "the id \"" + id + "\" is incorrect";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "false");

		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}

	private void successfulRemove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "You succesfully deleted the category";
		request.setAttribute(mResultParamName, message);
		request.setAttribute(mResultSuccessParamName, "true");

		request.getRequestDispatcher(mCategoryMaintenancePath).forward(request, response);
	}

}

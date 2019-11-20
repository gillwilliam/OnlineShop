package request_handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Product;
import manager.ProductManager;

public class SearchProductsRequestHandler implements RequestHandler {

	// CONST
	// //////////////////////////////////////////////////////////////////////////////////////
	// initial parameters names
	public static final String PRODUCT_INIT_PARAM_NAME = "product";


	public static final String MAX_NUM_OF_RESULTS_INIT_PARAM_NAME = "max_num_of_results";
	public static final String PRODUCTS_MAINTENANCE_PATH_PARAM_NAME = "search_products";
	// request attributes
	public static final String ATTR_FOUND_PRODUCTS = "found_products";
	// defaults
	public static final int DEFAULT_MAX_NUM_OF_RESULTS = 40;

	// fields
	// /////////////////////////////////////////////////////////////////////////////////////

	public SearchProductsRequestHandler(ServletContext context) {

	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// int maxNumOfResults = acquireMaxNumOfResults(request);
		String searchedProduct = request.getParameter(PRODUCT_INIT_PARAM_NAME);
		int category = request.getParameter("category") == null ? -1
				: Integer.parseInt(request.getParameter("category"));
		System.out.println("CAT" + category);

		// if search was initiated in request handler, not jsp form
		ProductManager pm = new ProductManager();
		List<Product> searchResult = new ArrayList<>();
		if (searchedProduct != null) {
			searchResult = pm.findByName(searchedProduct);
		} else if (category != -1) {
			searchResult = pm.findByCategory(category);
		}

		request.setAttribute(ATTR_FOUND_PRODUCTS, searchResult);
		request.getRequestDispatcher("/main.jsp").forward(request, response);
	}

}

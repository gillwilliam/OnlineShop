package request_handlers.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import beans.general.ProductBean;
import beans.session.AdminBean;
import beans.session.BuyerBean;
import beans.session.SellerBean;
import beans.session.UserBean;
import categories.Category;
import categories.CategoryTree;
import enums.UserType;
import payments.Price;
import request_handlers.RequestHandler;

public class SearchProductsRequestHandler implements RequestHandler {
	
	// CONST //////////////////////////////////////////////////////////////////////////////////////
	// initial parameters names
	public static final String PRODUCT_INIT_PARAM_NAME 				= "product";
	private static final String MAIN_JSP = "/main.jsp";
	
	public static final String MAX_NUM_OF_RESULTS_INIT_PARAM_NAME 	= "max_num_of_results";
	public static final String PRODUCTS_MAINTENANCE_PATH_PARAM_NAME	= "search_products";
	// request attributes
	public static final String ATTR_FOUND_PRODUCTS = "found_products";
	// defaults
	public static final int DEFAULT_MAX_NUM_OF_RESULTS = 40;
	
	// fields /////////////////////////////////////////////////////////////////////////////////////
	private ServletContext mContext;
	private String mProductsMaintenancePath;
	
	
	
	public SearchProductsRequestHandler(ServletContext context)
	{
		mContext = context;
		mProductsMaintenancePath = context.getInitParameter(PRODUCTS_MAINTENANCE_PATH_PARAM_NAME);
	}
	


	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		// int maxNumOfResults		= acquireMaxNumOfResults(request);
		String searchedProduct 	= request.getParameter(PRODUCT_INIT_PARAM_NAME);
		// String searchedSurname 	= request.getParameter(mContext.getInitParameter(SURNAME_INIT_PARAM_NAME));
		// String searchedEmail 	= request.getParameter(mContext.getInitParameter(EMAIL_INIT_PARAM_NAME));
		
		// if search was initiated in request handler, not jsp form
		if (searchedProduct == null)
			searchedProduct = (String) request.getAttribute(PRODUCT_INIT_PARAM_NAME);
		
		ArrayList<ProductBean> searchResult = searchForProducts(searchedProduct);
		
		request.setAttribute(ATTR_FOUND_PRODUCTS, searchResult);
		request.getRequestDispatcher("/main.jsp").forward(request, response);
		//response.sendRedirect(request.getContextPath() + MAIN_JSP);
	}
	
	
	
	/**
	 * 
	 * @param request request containting maximum number of results parameters
	 * @return maximum number of results acquired from request. In case that no such parameter was found
	 * or it wasn't greater than 0 then DEFAULT_MAX_NUM_OF_RESULTS is returned
	 */
	private int acquireMaxNumOfResults(HttpServletRequest request)
	{
		String 	maxNumOfResultsObj 	= request.getParameter(mContext.getInitParameter(MAX_NUM_OF_RESULTS_INIT_PARAM_NAME));
		int maxNumOfResults 		= maxNumOfResultsObj != null ? 
					Integer.parseInt(maxNumOfResultsObj) : DEFAULT_MAX_NUM_OF_RESULTS;
		
		return maxNumOfResults > 0 ? maxNumOfResults : DEFAULT_MAX_NUM_OF_RESULTS;
	}
	
	
	
	/**
	 * 
	 * @param name name to be searched, if == "" then any value will be matched
	 * @param surname surname to be searched, if == "" then any value will be matched
	 * @param email email to be searched, if == "" then any value will be matched
	 * @param maxNumOfResults maximum number of users which will be found
	 * @return HashMaps containing ArrayLists with users matching searching parameters
	 */
	private ArrayList<ProductBean> searchForProducts(String product)
	{
		// TODO conntect to database, at this moment just simulate
		
		
		
//		int id, @NotNull String name, @NotNull Category category, @NotNull Price price,
//        @NotNull String description, int quantity, @NotNull String imageStoragePath
		
		CategoryTree mt = new CategoryTree();
		mt.loadFromDatabase();
		
		ArrayList<ProductBean> products	= new ArrayList<>();

        Price price1 = new Price(10, 50, "EUR");
        
        
//        int id, @NotNull String name, @NotNull Category category, @NotNull Price price,
//        @NotNull String description, int quantity, @NotNull String imageStoragePath

        ProductBean p1 = new ProductBean(
        		1,
        		"Jordans",
        		mt.getRootCategories().get(0),
        		price1,
        		"These are shoes",
                69,
                "img/product04.jpg"
                );
        
        
        for(int i = 0; i < 10; i++) {
        	products.add(p1);
        }
		
		
		return products;
	}

}

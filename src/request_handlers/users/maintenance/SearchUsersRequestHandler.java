package request_handlers.users.maintenance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.session.AdminBean;
import beans.session.BuyerBean;
import beans.session.SellerBean;
import beans.session.UserBean;
import entities.UserType;
import request_handlers.RequestHandler;

public class SearchUsersRequestHandler implements RequestHandler {
	
	// CONST //////////////////////////////////////////////////////////////////////////////////////
	// initial parameters names
	public static final String NAME_INIT_PARAM_NAME 				= "name";
	public static final String SURNAME_INIT_PARAM_NAME 				= "surname";
	public static final String EMAIL_INIT_PARAM_NAME 				= "email";
	public static final String MAX_NUM_OF_RESULTS_INIT_PARAM_NAME 	= "max_num_of_results";
	public static final String USERS_MAINTENANCE_PATH_PARAM_NAME	= "users_maintenance_path";
	// request attributes
	public static final String ATTR_FOUND_USERS	= "found_users";
	// defaults
	public static final int DEFAULT_MAX_NUM_OF_RESULTS = 40;
	
	// fields /////////////////////////////////////////////////////////////////////////////////////
	private ServletContext 	mContext;
	private String			mUsersMaintenancePath;
	
	
	
	public SearchUsersRequestHandler(ServletContext context)
	{
		mContext 				= context;
		mUsersMaintenancePath 	= context.getInitParameter(USERS_MAINTENANCE_PATH_PARAM_NAME);
	}
	


	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		int maxNumOfResults		= acquireMaxNumOfResults(request);
		String searchedName 	= request.getParameter(mContext.getInitParameter(NAME_INIT_PARAM_NAME));
		String searchedSurname 	= request.getParameter(mContext.getInitParameter(SURNAME_INIT_PARAM_NAME));
		String searchedEmail 	= request.getParameter(mContext.getInitParameter(EMAIL_INIT_PARAM_NAME));
		
		HashMap<UserType, ArrayList<UserBean>> searchResult = searchForUsers(searchedName, searchedSurname, searchedEmail, 
				maxNumOfResults);
		
		request.setAttribute(ATTR_FOUND_USERS, searchResult);
		request.getRequestDispatcher(mUsersMaintenancePath).forward(request, response);
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
	private HashMap<UserType, ArrayList<UserBean>> searchForUsers(String name, String surname, String email, int maxNumOfResults)
	{
		// TODO conntect to database, at this moment just simulate
		HashMap<UserType, ArrayList<UserBean>> res = new HashMap<>();
		
		ArrayList<UserBean> buyers		= new ArrayList<>();
		ArrayList<UserBean> sellers	= new ArrayList<>();
		ArrayList<UserBean> admins		= new ArrayList<>();
		
		BuyerBean buyer1 	= new BuyerBean("Jan", "Kowalski", "", "", "", "");
		BuyerBean buyer2 	= new BuyerBean("Anna", "Krzak", "", "", "", "");	
		BuyerBean buyer3	= new BuyerBean("Justyna", "Kowalczyk", "+48 696 463 622", "Folwarczna 23, 50-013 Wrocław, Moldawia", 
				"justynakowalska@gmail.com", "qurwa123");
		BuyerBean buyer4	= new BuyerBean("Zbyszko", "Z Bogdanca", "123 765 322", "Bolesława Chrobrego, Bogdaniec, Slovakia", 
				"zibidibi@grunwald.pl", "1234567");
		SellerBean seller1 	= new SellerBean("Mirek", "Handlarz", "", "", "");
		AdminBean admin1 	= new AdminBean("ad", "min", "", "", "");
		
		buyers.add(buyer1);
		buyers.add(buyer2);
		buyers.add(buyer3);
		buyers.add(buyer4);
		sellers.add(seller1);
		admins.add(admin1);
		
		res.put(UserType.BUYER, buyers);
		res.put(UserType.SELLER, sellers);
		res.put(UserType.ADMIN, admins);
		
		return res;
	}

}

package request_handlers.users.maintenance;

import java.io.IOException; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import beans.session.AdminBean;
import beans.session.BuyerBean;
import beans.session.SellerBean;
import beans.session.UserBean;
import enums.UserType;
import request_handlers.RequestHandler;
import utils.SQLUtils;

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
	public static final String ATTR_MESSAGE		= "message";
	// defaults
	public static final int DEFAULT_MAX_NUM_OF_RESULTS = 40;
	// DB
	public static final String USERS_TABLE_NAME = "users";
	public static final String NAME_COL			= "firstName";
	public static final String SURNAME_COL		= "lastName";
	public static final String EMAIL_COL	  	= "email";
	public static final String PHONE_COL		= "phone";
	public static final String ADDR_COL			= "address";
	public static final String TYPE_COL			= "type";
	
	// fields /////////////////////////////////////////////////////////////////////////////////////
	private ServletContext 	mContext;
	private String			mUsersMaintenancePath;
	private DataSource		mDataSource;
	
	
	
	public SearchUsersRequestHandler(ServletContext context, DataSource dataSource)
	{
		mContext 				= context;
		mUsersMaintenancePath 	= context.getInitParameter(USERS_MAINTENANCE_PATH_PARAM_NAME);
		
		try 
		{
			Context ctx 	= new InitialContext();
			mDataSource 	= (DataSource) ctx.lookup(mContext.getInitParameter("data_source_name"));
		} 
		catch (NamingException e) 
		{
			e.printStackTrace();
			throw new RuntimeException("Cannot obtain datasource in SearchUsersRequestHandler");
		}
	}
	


	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		int maxNumOfResults		= acquireMaxNumOfResults(request);
		String searchedName 	= request.getParameter(mContext.getInitParameter(NAME_INIT_PARAM_NAME));
		String searchedSurname 	= request.getParameter(mContext.getInitParameter(SURNAME_INIT_PARAM_NAME));
		String searchedEmail 	= request.getParameter(mContext.getInitParameter(EMAIL_INIT_PARAM_NAME));
		
		// if search was initiated in request handler, not jsp form
		if (searchedName == null)
			searchedName = (String) request.getAttribute(mContext.getInitParameter(NAME_INIT_PARAM_NAME));
		if (searchedSurname == null)
			searchedSurname = (String) request.getAttribute(mContext.getInitParameter(SURNAME_INIT_PARAM_NAME));
		if (searchedEmail == null)
			searchedEmail = (String) request.getAttribute(mContext.getInitParameter(EMAIL_INIT_PARAM_NAME));
		
		SearchResult searchResult = searchForUsers(searchedName, searchedSurname, searchedEmail, maxNumOfResults);
		
		request.setAttribute(ATTR_FOUND_USERS, searchResult.results);
		if (!searchResult.isSuccessful)
			request.setAttribute(ATTR_MESSAGE, searchResult.message);
		request.getRequestDispatcher(mUsersMaintenancePath).forward(request, response);
	}
	
	
	
	/**
	 * obtains maximum number of results from requests or assigns default value if obtained value doesn't exist
	 * or is less than 0
	 * @param request request containing maximum number of results parameters
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
	private SearchResult searchForUsers(String name, String surname, String email, int maxNumOfResults)
	{
		SearchResult res = new SearchResult();
		HashMap<UserType, ArrayList<UserBean>> foundUsers = new HashMap<>();
		res.results = foundUsers;
		
		Connection con 		= null;
		Statement statement = null;
		
		try 
		{
			con = mDataSource.getConnection();
			
			if (con == null)
			{
				res.isSuccessful 	= false;
				res.message 		= "Cannot obtain connection";
			}
			else
			{
				statement 			= con.createStatement();
				ResultSet results 	= performQuery(con, statement, name, surname, email, maxNumOfResults);
				res.results 		= convertResultSetToFinalResult(results);
				res.isSuccessful	= true;
				res.message			= "successful search";
			}	
		} 
		catch (SQLException e) 
		{
			res.isSuccessful = false;
			res.message	     = "SQLError occurred: " + e.getMessage();
			e.printStackTrace();
		}
		finally
		{
			closeConnectionAndStatement(con, statement);
		}
		
		return res;
	}
	
	
	
	private void closeConnectionAndStatement(Connection con, Statement statement)
	{
		try 
		{
			if (statement != null) statement.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
	
		try 
		{
			if (con != null) con.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	
	
	private ResultSet performQuery(Connection con, Statement statement, String name, String surname, String email, int maxNumOfResults) 
			throws SQLException
	{
		String andStatement = SQLUtils.getSQLAnd(getConditionsMap(name, surname, email));
		String sqlQuery = "SELECT * FROM " + USERS_TABLE_NAME + "\n" +
				(andStatement.length() > 0 ?
						"WHERE " + andStatement
						:
						""
				) + "\n" +
				"LIMIT " + maxNumOfResults + ";";
		
		ResultSet res = statement.executeQuery(sqlQuery);
				
		return res;
	}
	
	
	
	private HashMap<String, String> getConditionsMap(String name, String surname, String email)
	{
		HashMap<String, String> res = new HashMap<String, String>();
		
		if (name != null && !name.isEmpty())
			res.put(NAME_COL, name);
		
		if (surname != null && !surname.isEmpty())
			res.put(SURNAME_COL, surname);
		
		if (email != null && !email.isEmpty())
			res.put(EMAIL_COL, email);
		
		return res;
	}
	
	
	
	private HashMap<UserType, ArrayList<UserBean>> convertResultSetToFinalResult(ResultSet rs) throws SQLException
	{
		HashMap<UserType, ArrayList<UserBean>> result = new HashMap<>();
		
		ArrayList<UserBean> buyers 	= new ArrayList<>();
		ArrayList<UserBean> sellers = new ArrayList<>();
		ArrayList<UserBean> admins 	= new ArrayList<>();
		
		result.put(UserType.BUYER, buyers);
		result.put(UserType.SELLER, sellers);
		result.put(UserType.ADMIN, admins);
		
		UserType userType	= null;
		
		while (rs.next())
		{
			userType = UserType.getInstance(rs.getString(TYPE_COL));
			
			switch (userType)
			{
			case BUYER : buyers.add(
					new BuyerBean(
							rs.getString(NAME_COL), 
							rs.getString(SURNAME_COL), 
							rs.getString(PHONE_COL),
							rs.getString(ADDR_COL), 
							rs.getString(EMAIL_COL), "")
					); break;
			case SELLER : sellers.add(
					new SellerBean(
							rs.getString(NAME_COL), 
							rs.getString(SURNAME_COL), 
							rs.getString(PHONE_COL), 
							rs.getString(EMAIL_COL), "")
					); break;
			case ADMIN : admins.add(
					new AdminBean(
							rs.getString(NAME_COL), 
							rs.getString(SURNAME_COL), 
							rs.getString(PHONE_COL), 
							rs.getString(EMAIL_COL), "")
					); break;
			default : break;
			}
		}
		
		return result;
	}
	
	
	
	// case classes /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	private class SearchResult {
		private boolean isSuccessful;
		private String message;
		private HashMap<UserType, ArrayList<UserBean>> results;
		
		public SearchResult()
		{
			message = "";
		}
	}

}

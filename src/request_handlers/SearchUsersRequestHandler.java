package request_handlers;

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

import entities.User;
import utils.SQLUtils;
import utils.UserType;

public class SearchUsersRequestHandler implements RequestHandler {

	// CONST
	// //////////////////////////////////////////////////////////////////////////////////////
	// initial parameters names
	public static final String NAME_INIT_PARAM_NAME = "name";
	public static final String SURNAME_INIT_PARAM_NAME = "surname";
	public static final String EMAIL_INIT_PARAM_NAME = "email";
	public static final String MAX_NUM_OF_RESULTS_INIT_PARAM_NAME = "max_num_of_results";
	public static final String USERS_MAINTENANCE_PATH_PARAM_NAME = "users_maintenance_path";
	// request attributes
	public static final String ATTR_FOUND_USERS = "found_users";
	public static final String ATTR_MESSAGE = "message";
	public static final String ATTR_SUCCESS = "deleteResult"; // No it's not an error, I want deleteResult here, maybe
																// it's not a
																// very nice solution, but at least it doesn't require
																// changes in
																// users_maintenance.jsp to change message bg color
	// defaults
	public static final int DEFAULT_MAX_NUM_OF_RESULTS = 40;
	// DB

	// fields
	// /////////////////////////////////////////////////////////////////////////////////////
	private ServletContext mContext;
	private String mUsersMaintenancePath;
	private DataSource mDataSource;

	public SearchUsersRequestHandler(ServletContext context) {
		mContext = context;
		mUsersMaintenancePath = context.getInitParameter(USERS_MAINTENANCE_PATH_PARAM_NAME);

		try {
			Context ctx = new InitialContext();
			mDataSource = (DataSource) ctx.lookup("OnlineShopDataSource");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot obtain datasource in SearchUsersRequestHandler");
		}
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchedName = request.getParameter(mContext.getInitParameter(NAME_INIT_PARAM_NAME));
		String searchedSurname = request.getParameter(mContext.getInitParameter(SURNAME_INIT_PARAM_NAME));
		String searchedEmail = request.getParameter(mContext.getInitParameter(EMAIL_INIT_PARAM_NAME));

		// if search was initiated in request handler, not jsp form
		if (searchedName == null)
			searchedName = (String) request.getAttribute(mContext.getInitParameter(NAME_INIT_PARAM_NAME));
		if (searchedSurname == null)
			searchedSurname = (String) request.getAttribute(mContext.getInitParameter(SURNAME_INIT_PARAM_NAME));
		if (searchedEmail == null)
			searchedEmail = (String) request.getAttribute(mContext.getInitParameter(EMAIL_INIT_PARAM_NAME));

		SearchResult searchResult = searchForUsers(searchedName, searchedSurname, searchedEmail);

		request.setAttribute(ATTR_FOUND_USERS, searchResult.results);
		if (!searchResult.isSuccessful) {
			request.setAttribute(ATTR_MESSAGE, searchResult.message);
			request.setAttribute(ATTR_SUCCESS, false);
		}
		request.getRequestDispatcher(mUsersMaintenancePath).forward(request, response);
	}

	/**
	 * 
	 * @param name    name to be searched, if == "" then any value will be matched
	 * @param surname surname to be searched, if == "" then any value will be
	 *                matched
	 * @param email   email to be searched, if == "" then any value will be matched
	 * @return HashMaps containing ArrayLists with users matching searching
	 *         parameters
	 */
	private SearchResult searchForUsers(String name, String surname, String email) {
		SearchResult res = new SearchResult();
		HashMap<UserType, ArrayList<User>> foundUsers = new HashMap<>();
		res.results = foundUsers;

		Connection con = null;
		Statement statement = null;

		try {
			con = mDataSource.getConnection();

			if (con == null) {
				res.isSuccessful = false;
				res.message = "Cannot obtain connection";
			} else {
				statement = con.createStatement();
				ResultSet results = performQuery(con, statement, name, surname, email);
				res.results = convertResultSetToFinalResult(results);
				res.isSuccessful = true;
				res.message = "successful search";
			}
		} catch (SQLException e) {
			res.isSuccessful = false;
			res.message = "SQLError occurred: " + e.getMessage();
			e.printStackTrace();
		} finally {
			closeConnectionAndStatement(con, statement);
		}

		return res;
	}

	private void closeConnectionAndStatement(Connection con, Statement statement) {
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private ResultSet performQuery(Connection con, Statement statement, String name, String surname, String email)
			throws SQLException {
		String andStatement = SQLUtils.getSQLAnd(getConditionsMap(name, surname, email));
		String sqlQuery = "SELECT * FROM " + USERS_TABLE_NAME + "\n"
				+ (andStatement.length() > 0 ? "WHERE " + andStatement : "") + ";";

		ResultSet res = statement.executeQuery(sqlQuery);

		return res;
	}

	private HashMap<String, String> getConditionsMap(String name, String surname, String email) {
		HashMap<String, String> res = new HashMap<String, String>();

		if (name != null && !name.isEmpty())
			res.put(NAME_COL, name);

		if (surname != null && !surname.isEmpty())
			res.put(SURNAME_COL, surname);

		if (email != null && !email.isEmpty())
			res.put(EMAIL_COL, email);

		return res;
	}

	public static final String USERS_TABLE_NAME = "users";
	public static final String NAME_COL = "firstName";
	public static final String SURNAME_COL = "lastName";
	public static final String EMAIL_COL = "email";
	public static final String PHONE_COL = "phone";
	public static final String ADDR_COL = "address";
	public static final String TYPE_COL = "type";
	public static final String PASSWORD_COL = "password";

	private HashMap<UserType, ArrayList<User>> convertResultSetToFinalResult(ResultSet rs) throws SQLException {
		HashMap<UserType, ArrayList<User>> result = new HashMap<>();

		ArrayList<User> buyers = new ArrayList<>();
		ArrayList<User> sellers = new ArrayList<>();
		ArrayList<User> admins = new ArrayList<>();

		while (rs.next()) {
			UserType userType = UserType.getInstance(rs.getString(TYPE_COL));
			User user = new User(rs.getString(NAME_COL), rs.getString(SURNAME_COL), rs.getString(PHONE_COL),
					rs.getString(EMAIL_COL), rs.getString(PASSWORD_COL), rs.getString(TYPE_COL),
					rs.getString(ADDR_COL));
			switch (userType) {
			case BUYER:
				buyers.add(user);
				break;
			case SELLER:
				sellers.add(user);
				break;
			case ADMIN:
				admins.add(user);
				break;
			default:
				break;
			}
		}

		result.put(UserType.BUYER, buyers);
		result.put(UserType.SELLER, sellers);
		result.put(UserType.ADMIN, admins);

		return result;
	}

	// case classes
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private class SearchResult {
		private boolean isSuccessful;
		private String message;
		private HashMap<UserType, ArrayList<User>> results;

		public SearchResult() {
			message = "";
		}
	}

}

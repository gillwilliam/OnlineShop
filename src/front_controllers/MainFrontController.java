package front_controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

import categories.CategoryTree;
import request_handlers.AddCategoryRequestHandler;
import request_handlers.CheckoutRequestHandler;
import request_handlers.CreateProductRequestHandler;
import request_handlers.CreateSellerRequestHandler;
import request_handlers.DeleteCategoryRequestHandler;
import request_handlers.DeleteUserRequestHandler;
import request_handlers.DisplayUserProfileRequestHandler;
import request_handlers.EditProductRequestHandler;
import request_handlers.EditUserProfileRequestHandler;
import request_handlers.RegisterRequestHandler;
import request_handlers.RenameCategoryRequestHandler;
import request_handlers.RequestHandler;
import request_handlers.SearchProductsRequestHandler;
import request_handlers.SearchUsersRequestHandler;
import request_handlers.SignInRequestHandler;
import request_handlers.SignOutRequestHandler;

@WebServlet(name = "MainFrontController")
@MultipartConfig
public class MainFrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// CONST
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String PAGE_NOT_FOUND_PATH = "/page_not_found.jsp";
	public static final String REQUEST_EXTENSION_PARAM_NAME = "main_front_controller_request_extension";

	// fields
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	private HashMap<String, RequestHandler> mRequestHandlers;
	private String mRequestExtension;

	// persistence - I'm creating this fields here because otherwise resources would
	// have to be declared in web.xml,
	// data injection is possible only in servlets and EJBs
	@PersistenceContext(unitName = "OnlineShop")
	EntityManager mEntityManager;
	@Resource
	UserTransaction mUserTransaction;
	@Resource(lookup = "OnlineShopDS")
	DataSource mDataSource;

	@Override
	public void init() {
		mRequestHandlers = new HashMap<String, RequestHandler>();
		mRequestExtension = getServletContext().getInitParameter(REQUEST_EXTENSION_PARAM_NAME);

		initCategoryTree();
		addUrlPatternsAndHandlers();

		getServletContext().setAttribute("entity_manager", mEntityManager);
		getServletContext().setAttribute("user_transaction", mUserTransaction);

	}

	private void initCategoryTree() {
		ServletContext context = getServletContext();
		CategoryTree tree = (CategoryTree) context.getAttribute("category_tree_attr");

		if (tree == null) {
			tree = new CategoryTree();
			tree.loadFromDatabase();

			getServletContext().setAttribute("category_tree_attr", tree);
		}
	}

	/**
	 * Add your url patterns here
	 */
	private void addUrlPatternsAndHandlers() {
		ServletContext context = getServletContext();
		mRequestHandlers.put("/editBuyerProfile" + mRequestExtension,
				new EditUserProfileRequestHandler(context, mRequestExtension));
		mRequestHandlers.put("/editSellerProfile" + mRequestExtension,
				new EditUserProfileRequestHandler(context, mRequestExtension));
		mRequestHandlers.put("/editAdminProfile" + mRequestExtension,
				new EditUserProfileRequestHandler(context, mRequestExtension));
		mRequestHandlers.put("/displayBuyerProfile" + mRequestExtension,
				new DisplayUserProfileRequestHandler(context, mRequestExtension));
		mRequestHandlers.put("/displaySellerProfile" + mRequestExtension,
				new DisplayUserProfileRequestHandler(context, mRequestExtension));
		mRequestHandlers.put("/displayAdminProfile" + mRequestExtension,
				new DisplayUserProfileRequestHandler(context, mRequestExtension));
		mRequestHandlers.put("/searchUsers" + mRequestExtension, new SearchUsersRequestHandler(context, mDataSource));
		mRequestHandlers.put("/deleteBuyer" + mRequestExtension,
				new DeleteUserRequestHandler(context, mRequestExtension));
		mRequestHandlers.put("/deleteSeller" + mRequestExtension,
				new DeleteUserRequestHandler(context, mRequestExtension));
		mRequestHandlers.put("/signIn" + mRequestExtension, new SignInRequestHandler(context));
		mRequestHandlers.put("/product/checkout" + mRequestExtension,
				new CheckoutRequestHandler(context, mRequestExtension));
		mRequestHandlers.put("/addCategory" + mRequestExtension, new AddCategoryRequestHandler(context));
		mRequestHandlers.put("/renameCategory" + mRequestExtension, new RenameCategoryRequestHandler(context));
		mRequestHandlers.put("/deleteCategory" + mRequestExtension, new DeleteCategoryRequestHandler(context));
		mRequestHandlers.put("/signOut" + mRequestExtension, new SignOutRequestHandler(context));
		mRequestHandlers.put("/register" + mRequestExtension, new RegisterRequestHandler(context));
		mRequestHandlers.put("/editProduct" + mRequestExtension, new EditProductRequestHandler(context));
		mRequestHandlers.put("/createProduct" + mRequestExtension, new CreateProductRequestHandler(context));
		mRequestHandlers.put("/createSeller" + mRequestExtension,
				new CreateSellerRequestHandler(context, mEntityManager, mUserTransaction));
		mRequestHandlers.put("/searchProducts" + mRequestExtension, new SearchProductsRequestHandler(context));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestHandler handler = mRequestHandlers.get(request.getServletPath());

		if (handler == null) {
			request.setAttribute("page", request.getServletPath());
			request.getRequestDispatcher(PAGE_NOT_FOUND_PATH).forward(request, response);
		} else
			handler.handleRequest(request, response);
	}
}

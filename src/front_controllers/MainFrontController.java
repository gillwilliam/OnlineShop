package front_controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import categories.CategoryTree;
import request_handlers.RequestHandler;
import request_handlers.authorization.SignInRequestHandler;
import request_handlers.authorization.SignOutRequestHandler;
import request_handlers.product.CheckoutRequestHandler;
import request_handlers.categories.AddCategoryRequestHandler;
import request_handlers.categories.DeleteCategoryRequestHandler;
import request_handlers.categories.RenameCategoryRequestHandler;
import request_handlers.users.DeleteAdminRequestHandler;
import request_handlers.users.DeleteBuyerRequestHandler;
import request_handlers.users.DeleteSellerRequestHandler;
import request_handlers.users.EditUserProfileRequestHandler;
import request_handlers.users.maintenance.SearchUsersRequestHandler;

@WebServlet(name = "MainFrontController")
public class MainFrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String PAGE_NOT_FOUND_PATH 				= "/page_not_found.jsp";
    public static final String REQUEST_EXTENSION_PARAM_NAME 	= "main_front_controller_request_extension";

    // fields //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private HashMap<String, RequestHandler> mRequestHandlers;
    private String 							mRequestExtension;



    @Override
    public void init()
    {
        mRequestHandlers 	= new HashMap<>();
        mRequestExtension 	= getServletContext().getInitParameter(REQUEST_EXTENSION_PARAM_NAME);
        
        initCategoryTree();
        addUrlPatternsAndHandlers();
    }
    
    
    
    private void initCategoryTree()
    {
    	ServletContext context 	= getServletContext();
    	CategoryTree tree 		= (CategoryTree) context.getAttribute("category_tree_attr");
    	
    	if (tree == null)
    	{
    		tree = new CategoryTree();
    		tree.loadFromDatabase();
    		
            getServletContext().setAttribute("category_tree_attr", tree);
    	}
    }


    /**
     * Add your url patterns here
     */
    private void addUrlPatternsAndHandlers()
    {
    	ServletContext context = getServletContext();
    	mRequestHandlers.put("/editBuyerProfile" + mRequestExtension, 
    		   new EditUserProfileRequestHandler(context, mRequestExtension));
    	mRequestHandlers.put("/editSellerProfile" + mRequestExtension, 
    		   new EditUserProfileRequestHandler(context, mRequestExtension));
    	mRequestHandlers.put("/users/seller/editSellerProfile" + mRequestExtension, 
     		   new EditUserProfileRequestHandler(context, mRequestExtension));
    	mRequestHandlers.put("/editAdminProfile" + mRequestExtension, 
     		   new EditUserProfileRequestHandler(context, mRequestExtension));
     	mRequestHandlers.put("/users/admin/editAdminProfile" + mRequestExtension, 
      		   new EditUserProfileRequestHandler(context, mRequestExtension));
     	mRequestHandlers.put("/searchUsers" + mRequestExtension,
     			new SearchUsersRequestHandler(context));
     	mRequestHandlers.put("/deleteBuyer" + mRequestExtension,
     			new DeleteBuyerRequestHandler(context, mRequestExtension));
     	mRequestHandlers.put("/deleteSeller" + mRequestExtension,
     			new DeleteSellerRequestHandler(context, mRequestExtension));
     	mRequestHandlers.put("/deleteAdmin" + mRequestExtension,
     			new DeleteAdminRequestHandler(context, mRequestExtension));
     	mRequestHandlers.put("/signIn" + mRequestExtension,
     			new SignInRequestHandler(context));
     	mRequestHandlers.put("/product/checkout" + mRequestExtension,
     			new CheckoutRequestHandler(context, mRequestExtension));
     	mRequestHandlers.put("/addCategory" + mRequestExtension,
     			new AddCategoryRequestHandler(context));
     	mRequestHandlers.put("/renameCategory" + mRequestExtension,
     			new RenameCategoryRequestHandler(context));
     	mRequestHandlers.put("/deleteCategory" + mRequestExtension,
     			new DeleteCategoryRequestHandler(context));
     	mRequestHandlers.put("/signOut" + mRequestExtension,
     			new SignOutRequestHandler(context));
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        handleRequest(request, response);
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        handleRequest(request, response);
    }



    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        RequestHandler handler = mRequestHandlers.get(request.getServletPath());

        if (handler == null)
        {
        	request.setAttribute("page", request.getServletPath());
        	request.getRequestDispatcher(PAGE_NOT_FOUND_PATH).forward(request, response);
        }
        else
            handler.handleRequest(request, response);
    }
}

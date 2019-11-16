package front_controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import categories.CategoryTree;
import request_handlers.RequestHandler;
import request_handlers.authorization.SignInRequestHandler;
import request_handlers.authorization.SignOutRequestHandler;
import request_handlers.product.CheckoutRequestHandler;
import request_handlers.product.CreateProductRequestHandler;
import request_handlers.product.EditProductRequestHandler;
import request_handlers.product.SearchProductsRequestHandler;
import request_handlers.categories.AddCategoryRequestHandler;
import request_handlers.categories.DeleteCategoryRequestHandler;
import request_handlers.categories.RenameCategoryRequestHandler;
import request_handlers.users.DeleteAdminRequestHandler;
import request_handlers.users.DeleteBuyerRequestHandler;
import request_handlers.users.DeleteSellerRequestHandler;
import request_handlers.users.EditUserProfileRequestHandler;
import request_handlers.users.maintenance.SearchUsersRequestHandler;

@WebServlet(name = "MainFrontController")
@MultipartConfig
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
        mRequestHandlers 	= new HashMap<String, RequestHandler>();
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
    		   new EditUserProfileRequestHandler(context, mRequestExtension));	// should be post only
    	mRequestHandlers.put("/editSellerProfile" + mRequestExtension, 
    		   new EditUserProfileRequestHandler(context, mRequestExtension));	// should be post only
    	mRequestHandlers.put("/editAdminProfile" + mRequestExtension, 
     		   new EditUserProfileRequestHandler(context, mRequestExtension));	// should be post only
     	mRequestHandlers.put("/searchUsers" + mRequestExtension,
     			new SearchUsersRequestHandler(context));
     	mRequestHandlers.put("/deleteBuyer" + mRequestExtension,
     			new DeleteBuyerRequestHandler(context, mRequestExtension));		// should be post only
     	mRequestHandlers.put("/deleteSeller" + mRequestExtension,
     			new DeleteSellerRequestHandler(context, mRequestExtension));	// should be post only
     	mRequestHandlers.put("/deleteAdmin" + mRequestExtension,
     			new DeleteAdminRequestHandler(context, mRequestExtension));		// should be post only
     	mRequestHandlers.put("/signIn" + mRequestExtension,
     			new SignInRequestHandler(context));								// should be post only
     	mRequestHandlers.put("/product/checkout" + mRequestExtension,
     			new CheckoutRequestHandler(context, mRequestExtension));
     	mRequestHandlers.put("/addCategory" + mRequestExtension,
     			new AddCategoryRequestHandler(context));						// should be post only
     	mRequestHandlers.put("/renameCategory" + mRequestExtension,
     			new RenameCategoryRequestHandler(context));						// should be post only
     	mRequestHandlers.put("/deleteCategory" + mRequestExtension,
     			new DeleteCategoryRequestHandler(context));						// should be post only
     	mRequestHandlers.put("/signOut" + mRequestExtension,
     			new SignOutRequestHandler(context));
     	mRequestHandlers.put("/editProduct" + mRequestExtension, 
     			new EditProductRequestHandler(context));						// should be post only
//     	mRequestHandlers.put("/createProduct" + mRequestExtension,
//     			new CreateProductRequestHandler(context));						// should be post only
     	mRequestHandlers.put("/searchProducts" + mRequestExtension,
     			new SearchProductsRequestHandler(context));
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

package front_controllers;

import request_handlers.EditBuyerProfileRequestHandler;
import request_handlers.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

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
        mRequestExtension 	= this.getInitParameter(REQUEST_EXTENSION_PARAM_NAME);
        
        addUrlPatternsAndHandlers();
    }


    /**
     * Add your url patterns here
     */
    private void addUrlPatternsAndHandlers()
    {
       mRequestHandlers.put("/editBuyerProfile" + mRequestExtension, new EditBuyerProfileRequestHandler());
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
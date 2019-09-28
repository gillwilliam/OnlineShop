package request_handlers.users;

import request_handlers.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditSellerProfileRequestHandler implements RequestHandler {


    // CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String SELLER_PROFILE_EDIT_PAGE_PATH = "users/seller/seller_profile.jsp";



    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.getRequestDispatcher(SELLER_PROFILE_EDIT_PAGE_PATH).forward(request, response);
    }
}

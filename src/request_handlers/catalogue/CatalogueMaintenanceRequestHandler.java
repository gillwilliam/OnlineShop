package request_handlers.catalogue;

import request_handlers.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CatalogueMaintenanceRequestHandler implements RequestHandler {

    // CONST ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String CATALOGUE_MAINTENANCE_PATH = "catalogue/catalogue_maintenance.jsp";



    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.getRequestDispatcher(CATALOGUE_MAINTENANCE_PATH).forward(request, response);
    }
}

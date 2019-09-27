package request_handlers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RequestHandler {

    void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}

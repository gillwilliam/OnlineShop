package request_handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestHandler {

	void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}

package request_handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Product;
import manager.ProductManager;

public class DisplayImage implements RequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Got request");
		ProductManager pm = new ProductManager();
		Product p = pm.findById(Integer.parseInt(request.getParameter("id")));
		response.setContentType("image/png");
		response.getOutputStream().write(p.getImage());
	}

}

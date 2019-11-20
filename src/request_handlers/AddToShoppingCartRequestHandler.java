package request_handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Product;
import manager.ProductManager;

public class AddToShoppingCartRequestHandler implements RequestHandler {
	private static final long serialVersionUID = 1L;

	private static final String MAIN_JSP = "/main.jsp";

	private ServletConfig config;

	private String mRequestExtension;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddToShoppingCartRequestHandler(ServletContext context, String requestExtension) {
		super();

		mRequestExtension = requestExtension;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// get the other params and insert them?
		
		HttpSession ms = request.getSession();
		
		// request.getSession().setAttribute("shoppingcart", new ArrayList<Product>());
		
		if(ms.getAttribute("shoppingcarts") == null) {
			request.getSession().setAttribute("shoppingcarts", new HashMap<Product, Integer>());
		}
		
		HashMap<Integer, Integer> shoppingcart = ((HashMap<Integer, Integer>) ms.getAttribute("shoppingcarts"));
		
		System.out.println(request.getParameter("product"));
		
		//shoppingcart.add((Product) request.getParameter("product"));
		
		ProductManager pm = new ProductManager();
		Product product = pm.findById(Integer.parseInt(request.getParameter("product")));
		shoppingcart.put(product.getId(), shoppingcart.getOrDefault(product.getId(), 0) + 1);
		
		request.getSession().setAttribute("shoppingcarts", shoppingcart);

		// redirect to home?
		response.sendRedirect(request.getContextPath() + MAIN_JSP);

	}

}

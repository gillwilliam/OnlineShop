package request_handlers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Product;
import manager.ProductManager;

public class WishListRequestHandler implements RequestHandler {
	private static final long serialVersionUID = 1L;

	private static final String WISHLIST_JSP = "/product/wishlist.jsp";

	private ServletConfig config;

	private String mRequestExtension;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WishListRequestHandler(ServletContext context, String requestExtension) {
		super();

		mRequestExtension = requestExtension;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// get the other params and insert them?
		
		System.out.println(request.getParameter("product"));
		
		String param = request.getParameter("product");
		
		
		
		HttpSession ms = request.getSession();
		
		// request.getSession().setAttribute("shoppingcart", new ArrayList<Product>());
		
		if(ms.getAttribute("wishlist") == null) {
			request.getSession().setAttribute("wishlist", new HashSet<Integer>());
		}
		
		Set<Integer> wishlist = ((HashSet<Integer>) ms.getAttribute("wishlist"));
		
		System.out.println(request.getParameter("product"));
		
		//shoppingcart.add((Product) request.getParameter("product"));
		
		ProductManager pm = new ProductManager();

		
		if(param.startsWith("remove")) {
			
			Product product = pm.findById(Integer.parseInt(param.split(" ")[1]));
			wishlist.remove(product.getId());
			
			request.getSession().setAttribute("wishlist", wishlist);
			
			response.sendRedirect(request.getContextPath() + WISHLIST_JSP);
			
		}
		
		//response.sendRedirect(request.getContextPath() + CHECKOUT_JSP);


	}

}

package request_handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Order;
import entities.Product;
import entities.ProductList;
import entities.User;
import manager.OrderManager;
import manager.ProductListManager;
import manager.ProductManager;

public class CheckoutRequestHandler implements RequestHandler {
	private static final long serialVersionUID = 1L;

	private static final String CHECKOUT_JSP = "/product/checkout.jsp";
	private static final String MAIN_JSP = "/main.jsp";

	private ServletConfig config;

	private String mRequestExtension;
	private ServletContext mContext;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckoutRequestHandler(ServletContext context, String requestExtension) {
		super();

		mRequestExtension = requestExtension;
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		System.out.println(name);

		// get the other params and insert them?

		HttpSession ms = request.getSession();

		HashMap<Integer, Integer> orderedProductsIds = ((HashMap<Integer, Integer>) ms.getAttribute("shoppingcarts"));

		OrderManager om = new OrderManager();
		ProductManager pm = new ProductManager();
		ProductListManager plm = new ProductListManager();

		List<Product> orderedProducts = new ArrayList<Product>();

		for (HashMap.Entry<Integer, Integer> entry : orderedProductsIds.entrySet()) {
			int key = entry.getKey();
			int value = entry.getValue();

			Product cur = pm.findById(key);
			orderedProducts.add(cur);
		}

		User self = (User) ms.getAttribute(mContext.getInitParameter("signed_user_attribute_name"));

		ProductList curProductList = new ProductList(orderedProducts, self);
		plm.create(curProductList);

		Order curOrder = new Order(self, curProductList);
		om.create(curOrder);

		// redirect to home?
		response.sendRedirect(request.getContextPath() + MAIN_JSP);

	}

}

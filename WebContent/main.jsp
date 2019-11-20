<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Product"%>
<%@ page import="manager.ProductManager"%>
<%@ page import="java.util.List"%>
<%@ page import="utils.Price"%><!DOCTYPE html>
<html>
<jsp:include page="Header.jsp" />
<body>
	<%
		//replace with db call to all products in db
		ProductManager pm = new ProductManager();
		List<Product> products;

		if (request.getAttribute("found_products") != null) {
			products = (List<Product>) request.getAttribute("found_products");
		} else if(request.getParameter("category") != null) {
			products = pm.findByCategory(Integer.parseInt(request.getParameter("category")));
		} else {
			products = pm.findAll();
		}
	%>
	<jsp:include page="Navigation.jsp" />
	<!-- HOME -->
	<div class=container>
		<div class="row">
			<%
				for (Product product : products) {
			%>
			<div class="col-md-4 text-center">
				<div class="row">
					<h3><%=product.getName()%></h3>
				</div>
				<div class="row">
					<img id="product_image" width="100" height="100" src="${pageContext.request.contextPath}/getImage.main?id=<%=product.getId()%>"
						alt="product photo" />
				</div>
				<div class="row">
					<p><%=product.getDescription()%></p>
				</div>
				<div class="row">
					<b><%=product.getPrice()%></b>
				</div>
				<div class="row">
					<form id="product"
					action="${pageContext.request.contextPath}/addToShoppingCart<%= application.getInitParameter("main_front_controller_request_extension") %>"
					method="post">
					
					<input id="show" type="submit" class="btn btn-success" style="margin-bottom: 10px"
								name="show"
								value="Add to cart"/>
	
					<input id="product"
						style="display: none"
						name="product"
						value=<%=product.getId()%> />
					</form>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>
	<!-- /section -->

	<!-- jQuery Plugins -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/slick.min.js"></script>
	<script src="js/nouislider.min.js"></script>
	<script src="js/jquery.zoom.min.js"></script>
	<script src="js/main.js"></script>

</body>

</html>

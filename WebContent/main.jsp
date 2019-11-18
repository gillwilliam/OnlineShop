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
					<img id="product_image" src="<%=product.getImage()%>"
						alt="product photo" />
				</div>
				<div class="row">
					<p><%=product.getDescription()%></p>
				</div>
				<div class="row">
					<b><%=product.getPrice()%></b>
				</div>
				<div class="row">
					<button class="primary-btn">Add to Cart</button>
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

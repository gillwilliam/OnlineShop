<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="beans.general.ProductBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="payments.Price" %><!DOCTYPE html>
<html>
	<jsp:include page="Header.jsp"/>
<body>
    <%
		//replace with db call to all products in db
		
        ArrayList<ProductBean> products = new ArrayList<ProductBean>();
    
   		if(request.getAttribute("found_products") != null) {
   			products = (ArrayList<ProductBean>) request.getAttribute("found_products");
   		}
       
    %>
	<jsp:include page="Navigation.jsp"/>
	<!-- HOME -->
        <div class=container>
       		<div class="row">
       		<%for(ProductBean product : products) { %>
        		<div class="col-md-4 text-center">
        			<div class="row">
	        		<h3><%=product.getName()%></h3>
	        		</div>
	        		 <div class="row">
	        		<img id="product_image" src="<%= product.getImagePath()%>" alt="product photo"/>
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
	        	<% } %>
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

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="beans.general.ProductBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="payments.Price" %><!DOCTYPE html>
<html>
	<jsp:include page="Header.jsp"/>
<body>
    <%
        ArrayList<ProductBean> products = new ArrayList<ProductBean>();


        Price price1 = new Price(10, 50, "EUR");
        Price price2 = new Price(30, 12, "EUR");
        Price price3 = new Price(11, 34, "EUR");
        Price price4 = new Price(130, 0, "EUR");

        ProductBean p1 = new ProductBean("Mokasyny", "shoes", price4,
                "description lorem ipsum et ...", 69, application.getRealPath("/") + "img/product04.jpg",
                "", "");
        
        ProductBean p2 = new ProductBean("Mokasyny", "shoes", price4,
                "description lorem ipsum et ...", 69, application.getRealPath("/") + "img/product04.jpg",
                "", "");
        ProductBean p3 = new ProductBean("Mokasyny", "shoes", price4,
                "description lorem ipsum et ...", 69, application.getRealPath("/") + "img/product04.jpg",
                "", "");
        ProductBean p4 = new ProductBean("Mokasyny", "shoes", price4,
                "description lorem ipsum et ...", 69, application.getRealPath("/") + "img/product04.jpg",
                "", "");
        
        
        for(int i = 0; i < 10; i++) {
        	products.add(p1);
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

<%@ page import="entities.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="payments.Price" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<jsp:include page="../Header.jsp"/>
<body>

    <%
    	ArrayList<Product> products = new ArrayList<Product>();


            Price price1 = new Price(10, 50, "EUR");
            Price price2 = new Price(30, 12, "EUR");
            Price price3 = new Price(11, 34, "EUR");
            Price price4 = new Price(130, 0, "EUR");

            Product product = new Product("Mokasyny", "shoes", price4,
                    "description lorem ipsum et ...", 69, application.getRealPath("/") + "img/product04.jpg",
                    "", "");
    %>

    <!-- /HEADER -->
	<jsp:include page="../Header.jsp"/>
    <!-- div containing content of the page -->
    <section id="content">
        
        <div class=container>
        		<div class="row">
	        		<div class="col-md-6">
		        		<div class="text-center"><h3><%=product.getName()%></h1></div>
		        	</div>
		        </div>
		        <div class="row">
		        	<div class="col-md-6 text-center">
		        		<img id="product_image" src="<%= product.getImagePath()%>" alt="product photo"/>
		        	</div>	
		        	<div class="col-md-6">
	        			<p><%=product.getDescription()%></p>
	        			<div class="row">
	        				<div class="col-md-4">
	        					<b><%=product.getPrice()%></b>
	        				</div>
	        				<div class="col">
	        					<p><%=product.getQuantity() + " remaining"%></p>
	        				</div>
	        			</div>
	        			<button class="primary-btn">Add to Cart</button>
		        	</div>
	        	</div>
        
        </div>
       
    </section> <!-- section content -->
</body>
</html>


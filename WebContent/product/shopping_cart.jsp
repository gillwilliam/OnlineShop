<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="entities.Product" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="payments.Price" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping Cart</title>

    <!-- Google font -->
    <link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet">

    <!-- Bootstrap -->
<%--     <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
 --%>
 	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Slick -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/slick.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/slick-theme.css" />

    <!-- nouislider -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/nouislider.min.css" />

    <!-- Font Awesome Icon -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">

    <!-- Custom stlylesheet -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- my custom css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product_details.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/catalogue_maintenance.css"/>

</head>
<body>
<%
	// replace with pulling the products in the shopping cart from db
	    ArrayList<Product> products = new ArrayList<Product>();


        Price price1 = new Price(10, 50, "EUR");
        Price price2 = new Price(30, 12, "EUR");
        Price price3 = new Price(11, 34, "EUR");
        Price price4 = new Price(130, 0, "EUR");

        Product product1 = new Product("p1", "c1", price1,
                "description lorem ipsum et ...", 1, application.getRealPath("/") + "img/product01.jpg",
                "", "");
       
        Product product2 = new Product("p2", "c2", price2,
                "description lorem ipsum et ...", 2, application.getRealPath("/") + "img/product02.jpg",
                "", "");
        
        Product product3 = new Product("p3", "c3", price3,
                "description lorem ipsum et ...", 3, application.getRealPath("/") + "img/product03.jpg",
                "", "");
        
        // amount purchased instead of amount remaining
        
        products.add(product1);
        products.add(product2);
        products.add(product3);
%>
<div class="container">
	<jsp:include page="../Header.jsp"/>
</div>
<div class="container">
		<div style="margin-top:100px">
		<%
			for(Product product : products) {
		%>
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
       				<div class="col-md-4">
       					<p><%=product.getQuantity() + " items"%></p>
       				</div>
       				<div class="col-md-4">
       					<button class="btn btn-danger">Remove</button>
       				</div>
       			</div>
        	</div>
       	</div>
       	<hr />
       	<%}%>
       	</div>
       	<div class="row">
	      	<div class="col-md-3 offset-md-10" style="margin-bottom: 10px">
	      		<a class="btn btn-primary" href=<%=request.getContextPath() +
	      				"/product/checkout.jsp"%>>Check out</a>
	      	</div>
       	</div>
</div>
	<!-- FOOTER TODO: Remove this-->
	<footer id="footer" class="section section-grey">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<!-- footer widget -->
				<div class="col-md-3 col-sm-6 col-xs-6">
					<div class="footer">
						<!-- footer logo -->
						<div class="footer-logo">
							<a class="logo" href="#">
		            <img src="./img/logo.png" alt="">
		          </a>
						</div>
						<!-- /footer logo -->

						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna</p>

						<!-- footer social -->
						<ul class="footer-social">
							<li><a href="#"><i class="fa fa-facebook"></i></a></li>
							<li><a href="#"><i class="fa fa-twitter"></i></a></li>
							<li><a href="#"><i class="fa fa-instagram"></i></a></li>
							<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
							<li><a href="#"><i class="fa fa-pinterest"></i></a></li>
						</ul>
						<!-- /footer social -->
					</div>
				</div>
				<!-- /footer widget -->

				<!-- footer widget -->
				<div class="col-md-3 col-sm-6 col-xs-6">
					<div class="footer">
						<h3 class="footer-header">My Account</h3>
						<ul class="list-links">
							<li><a href="#">My Account</a></li>
							<li><a href="#">My Wishlist</a></li>
							<li><a href="#">Compare</a></li>
							<li><a href="#">Checkout</a></li>
							<li><a href="#">Login</a></li>
						</ul>
					</div>
				</div>
				<!-- /footer widget -->

				<div class="clearfix visible-sm visible-xs"></div>

				<!-- footer widget -->
				<div class="col-md-3 col-sm-6 col-xs-6">
					<div class="footer">
						<h3 class="footer-header">Customer Service</h3>
						<ul class="list-links">
							<li><a href="#">About Us</a></li>
							<li><a href="#">Shiping & Return</a></li>
							<li><a href="#">Shiping Guide</a></li>
							<li><a href="#">FAQ</a></li>
						</ul>
					</div>
				</div>
				<!-- /footer widget -->

				<!-- footer subscribe -->
				<div class="col-md-3 col-sm-6 col-xs-6">
					<div class="footer">
						<h3 class="footer-header">Stay Connected</h3>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor.</p>
						<form>
							<div class="form-group">
								<input class="input" placeholder="Enter Email Address">
							</div>
							<button class="primary-btn">Join Newslatter</button>
						</form>
					</div>
				</div>
				<!-- /footer subscribe -->
			</div>
			<!-- /row -->
			<hr>
			<!-- row -->
			<div class="row">
				<div class="col-md-8 col-md-offset-2 text-center">
					<!-- footer copyright -->
					<div class="footer-copyright">
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</div>
					<!-- /footer copyright -->
				</div>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</footer>
	<!-- /FOOTER -->

</body>
</html>
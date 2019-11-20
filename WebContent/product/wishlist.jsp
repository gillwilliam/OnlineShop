<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entities.Product"%>
<%@ page import="java.util.*"%>
<%@ page import="utils.Price"%>
<%@ page import="manager.ProductManager"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shopping Cart</title>

<!-- Google font -->
<link href="https://fonts.googleapis.com/css?family=Hind:400,700"
	rel="stylesheet">

<!-- Bootstrap -->
<%--     <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
 --%>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<!-- Slick -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/slick.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/slick-theme.css" />

<!-- nouislider -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/nouislider.min.css" />

<!-- Font Awesome Icon -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-awesome.min.css">

<!-- Custom stlylesheet -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" />

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- my custom css -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/product_details.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/catalogue_maintenance.css" />

</head>
<body>
	<%
		// replace with pulling the products in the shopping cart from db
		ProductManager pm = new ProductManager();
		Set<Integer> products = new HashSet<Integer>();
		if(request.getSession().getAttribute("wishlist") != null) {
			products = (HashSet<Integer>) request.getSession().getAttribute("wishlist");
		}

	%>
	<div class="container">
		<jsp:include page="../Header.jsp" />
	</div>
	<div class="container">
		<div style="margin-top: 100px">
			<%if(products.isEmpty()) {%>
				<h2>Wishlist is currently empty, please add items to the wishlist.</h2>
			<%} %>	
			<%
				for (Integer productId : products) {
					Product product = pm.findById(productId);
			%>
			<div class="row">
				<div class="col-md-6">
					<div class="text-center">
						<h3><%=product.getName()%></h3>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6 text-center">
					<img id="product_image" src="${pageContext.request.contextPath}/getImage.main?id=<%=product.getId()%>"
						alt="product photo" />
				</div>
				<div class="col-md-6">
					<p><%=product.getDescription()%></p>
					<div class="row">
						<div class="col-md-4">
							<b><%=product.getPrice()%></b>
						</div>
						<div class="col-md-4">							
							<form id="product"
							action="${pageContext.request.contextPath}/editWishList<%= application.getInitParameter("main_front_controller_request_extension") %>"
							method="post">
							
							<input id="show" type="submit" class="btn btn-danger"
								name="show"
								value="Remove"/>
			
							<input id="product" style="display: none"
								name="product"
								value="remove <%=product.getId()%>"/>
							</form>
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
				</div>
			</div>
			<hr />
			<%
				}
			%>
		</div>
	</div>

</body>
</html>
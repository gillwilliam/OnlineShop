<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entities.Order"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Product"%>
<%@ page import="entities.ProductList"%>
<%@ page import="java.util.*"%>
<%@ page import="utils.Price"%>
<%@ page import="manager.OrderManager"%>
<%@ page import="manager.ProductListManager"%>

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
		OrderManager om = new OrderManager();
		
		User user = (User) request.getSession().getAttribute(application.getInitParameter("signed_user_attribute_name"));
		List<Order> orders = om.findByUserId(user);

	%>
	<div class="container">
		<jsp:include page="../Header.jsp" />
	</div>
	<div class="container">
		<div style="margin-top: 100px">
			<%if(orders.isEmpty()) {%>
				<h2>No orders placed.</h2>
			<%} %>	
			<%
				for (Order order : orders) {
			%>
			<div class="row">
				<div class="col-md-6">
					<div class="text-center">
						<h3><%=order.getId()%></h3>
					</div>
				</div>
				<%
					ProductList pl = order.getProductList();
					for(Product p : pl.getProducts()) {
				%>
					<span><%=p.getName() + " $" + p.getPrice()%></span>
				<% } %>	
			</div>
			<hr />
			<%
				}
			%>
		</div>
	</div>

</body>
</html>
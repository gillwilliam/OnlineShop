<%@ page import="entities.Product"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="utils.Price"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>

<title>Product Details</title>

<!-- header -->
<!-- Google font -->
<link href="https://fonts.googleapis.com/css?family=Hind:400,700"
	rel="stylesheet">

<!-- Bootstrap -->
<%--     <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
 --%>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
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
	href="${pageContext.request.contextPath}/css/checkout.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/catalogue_maintenance.css" />


</head>
<body>

	<!-- /HEADER -->
	<jsp:include page="../Header.jsp" />
	<!-- div containing content of the page -->


	<div class="col-75">
		<div class="container">
			<form action="checkout.main" method="post">

				<div class="row">
					<div class="col-50">
						<h3>Billing Address</h3>
						<label for="fname"><i class="fa fa-user"></i> Full Name</label> <input
							type="text" id="fname" name="name" placeholder="John M. Doe">
						<label for="email"><i class="fa fa-envelope"></i> Email</label> <input
							type="text" id="email" name="email"
							placeholder="john@example.com"> <label for="adr"><i
							class="fa fa-address-card-o"></i> Address</label> <input type="text"
							id="adr" name="address" placeholder="542 W. 15th Street">
						<label for="city"><i class="fa fa-institution"></i> City</label> <input
							type="text" id="city" name="city" placeholder="New York">

					</div>

					<div class="col-50">
						<h3>Payment</h3>
						<label for="fname">Accepted Cards</label>
						<div class="icon-container">
							<i class="fa fa-cc-visa" style="color: navy;"></i> <i
								class="fa fa-cc-amex" style="color: blue;"></i> <i
								class="fa fa-cc-mastercard" style="color: red;"></i> <i
								class="fa fa-cc-discover" style="color: orange;"></i>
						</div>
						<label for="cname">Name on Card</label> <input type="text"
							id="cname" name="cardname" placeholder="John More Doe"> <label
							for="ccnum">Credit card number</label> <input type="text"
							id="ccnum" name="cardnumber" placeholder="1111-2222-3333-4444">
						<label for="expmonth">Exp MM/YY</label> <input type="text"
							id="expmonth" name="expmonth" placeholder="05/22">

					</div>

				</div>
				<label> <input type="checkbox" checked="checked"
					name="sameadr"> Shipping address same as billing
				</label> <input type="submit" class="primary-btn" value="Confirm Checkout" />
			</form>
		</div>
	</div>

</body>
</html>


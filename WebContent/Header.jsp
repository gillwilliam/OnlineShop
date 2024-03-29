<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="entities.User"%>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>Totally l33t Shop</title>

<!-- Google font -->
<link href="https://fonts.googleapis.com/css?family=Hind:400,700"
	rel="stylesheet">

<!-- Bootstrap -->
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />

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
</head>
<body>
	<header>

		<%
			User user = (User) session.getAttribute(application.getInitParameter("signed_user_attribute_name"));
		%>

		<!-- header -->
		<div id="header">
			<div class="container-fluid">
				<div class="pull-left">
					<!-- Logo -->
					<div class="header-logo">
						<a class="logo" href="${pageContext.request.contextPath}"> <img
							src="${pageContext.request.contextPath}/img/logo.png" alt="">
						</a>
					</div>
					<!-- /Logo -->

					<!-- Search -->
					<div class="header-search">
						<form id="search_user_form"
							action="${pageContext.request.contextPath}/searchProducts
						<%= application.getInitParameter("main_front_controller_request_extension") %>"
							method="get">
							<input class="input search-input" id="product" type="text"
								name="<%=application.getInitParameter("product_attr")%>"
								placeholder="Enter your keyword" />

							<button class="search-btn">
								<i class="fa fa-search"></i>
							</button>
						</form>
					</div>
					<!-- /Search -->
				</div>
				<div class="pull-right">
					<ul class="header-btns">

						<!-- messaging -------------------------------------------------------------------------------------- -->
						<li style="display:<%=user != null ? "inline" : "none"%>"><a
							id="but_messages" href="${pageContext.request.contextPath}<%=application.getInitParameter("messages_path")%>"> <b class="text-uppercase">Messages</b>
						</a></li>
						<!-- messaging -------------------------------------------------------------------------------------- -->

						<!-- categories maintenance ------------------------------------------------------------------------- -->
						<li
							style="display:<%=user != null && user.isAdmin() ? "inline" : "none"%>">
							<a id="but_categories_maintenance"
							href="${pageContext.request.contextPath}<%= application.getInitParameter("category_maintenance_path")%>">
								<b class="text-uppercase">Categories maintenance</b>
						</a>
						</li>
						<!-- /categories maintenance ------------------------------------------------------------------------ -->

						<!-- users maintenance ------------------------------------------------------------------------------ -->
						<li
							style="display:<%=user != null && user.isAdmin() ? "inline" : "none"%>">
							<a id="but_users_maintenance"
							href="${pageContext.request.contextPath}<%= application.getInitParameter("users_maintenance_path")%>">
								<b class="text-uppercase">Users maintenance</b>
						</a>
						</li>
						<!-- /users maintenance ----------------------------------------------------------------------------- -->

						<!-- catalog maintenance ---------------------------------------------------------------------------- -->
						<li
							style="display:<%=user != null && (user.isAdmin() || user.isSeller()) ? "inline" : "none"%>">
							<a id="but_catalog_maintenance"
							href="${pageContext.request.contextPath}<%= application.getInitParameter("catalogue_maintenance_path") %>">
								<b class="text-uppercase">Catalog Maintenance</b>
						</a>
						</li>
						
						<li
							style="display:<%=user != null && (user.isBuyer()) ? "inline" : "none"%>">
							<a id="but_catalog_maintenance"
							href="${pageContext.request.contextPath}/product/orders.jsp">
								<b class="text-uppercase">My Orders</b>
						</a>
						</li>
						<!-- /catalog maintenance --------------------------------------------------------------------------- -->

						<!-- Account ---------------------------------------------------------------------------------------- -->
						<li style="display:<%=user == null ? "none" : "inline"%>"><a
							id="but_my_account"
							href="${pageContext.request.contextPath}<% 
								if (user == null || user.isBuyer())
                     				out.print(application.getInitParameter("buyer_profile_edit_path"));
                     			else if (user.isSeller())
                     				out.print(application.getInitParameter("seller_profile_edit_path"));
                     			else if (user.isAdmin())
                     				out.print(application.getInitParameter("admin_profile_edit_path"));
						 %>">
								<b class="text-uppercase">My Account</b>
						</a></li>
						<!-- /Account ------------------------------------------------------------------------------------- -->

						<!-- wish list ------------------------------------------------------------------------------------ -->
						<li
							style="display:<%=user != null && user.isBuyer() ? "inline" : "none"%>">
							<a id="but_wish_list" href="${pageContext.request.contextPath}/product/wishlist.jsp"> <b class="text-uppercase">Wishlist</b>
						</a>
						</li>
						<!-- /wish list ----------------------------------------------------------------------------------- -->

						<!-- Sign in button ------------------------------------------------------------------------------- -->
						<li style="display:<%=user == null ? "inline" : "none"%>"><a
							id="but_sign_in"
							href="${pageContext.request.contextPath}<%= 
                    		application.getInitParameter("login_path") %>">
								<b class="text-uppercase">Sign in</b>
						</a></li>
						<!-- /Sign in button ------------------------------------------------------------------------------ -->

						<!-- sign up button ------------------------------------------------------------------------------- -->
						<li style="display:<%=user == null ? "inline" : "none"%>"><a
							id="but_sign_up"
							href="${pageContext.request.contextPath}<%=
                    		application.getInitParameter("register_path") %>">
								<b class="text-uppercase">Sign up</b>
						</a></li>
						<!-- sign up button ------------------------------------------------------------------------------- -->

						<!-- Sign out button ------------------------------------------------------------------------------ -->
						<li style="display:<%=user != null ? "inline" : "none"%>"><a
							id="but_sign_out"
							href="${pageContext.request.contextPath}/signOut<%= 
                    		application.getInitParameter("main_front_controller_request_extension") %>">
								<b class="text-uppercase">Sign out</b>
						</a></li>
						<!-- /Sign out button ----------------------------------------------------------------------------- -->

						<!-- Cart -->
						<li
							style="display:<%=user != null && user.isBuyer() ? "inline" : "none"%>">
							<a
							href="${pageContext.request.contextPath}/product/shopping_cart.jsp">
								<b class="text-uppercase">My Cart</b>
						</a>
						</li>
						<!-- /Cart -->
						<!-- Mobile nav toggle-->
						<li class="nav-toggle">
							<button class="nav-toggle-btn main-btn icon-btn">
								<i class="fa fa-bars"></i>
							</button>
						</li>
						<!-- / Mobile nav toggle -->
					</ul>
				</div>
			</div>
			<!-- header -->
		</div>
		<!-- container -->
	</header>

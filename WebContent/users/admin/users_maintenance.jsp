<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="entities.UserBean" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="beans.session.UserType" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	
	<title>Users maintenance</title>
	
	<!-- header -->
    <!-- Google font -->
    <link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet">

    <!-- Bootstrap -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />

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


    <!-- Custom stlylesheet -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/users_maintenance.css" />
</head>
<body>
	
	<%
		Object maxNumOfDisplayedUsersObj 	= request.getAttribute("max_disp_users");
		int maxNumOfDisplayedUsers 			= maxNumOfDisplayedUsersObj != null ? (Integer) maxNumOfDisplayedUsersObj : 40;
		ArrayList<UserBean> buyers;
		ArrayList<UserBean> sellers;
		ArrayList<UserBean> admins;
		
	%>


	<!-- HEADER -->
	<header>
	    <!-- header -->
	    <div id="header">
	        <div class="container">
	            <div class="pull-left">
	                <!-- Logo -->
	                <div class="header-logo">
	                    <a class="logo" href="#">
	                        <img src="${pageContext.request.contextPath}/img/logo.png" alt="">
	                    </a>
	                </div>
	                <!-- /Logo -->
	
	                <!-- Search -->
	                <div class="header-search">
	                    <form>
	                        <input class="input search-input" type="text" placeholder="Enter your keyword">
	                        <select class="input search-categories">
	                            <option value="0">All Categories</option>
	                            <option value="1">Category 01</option>
	                            <option value="1">Category 02</option>
	                        </select>
	                        <button class="search-btn"><i class="fa fa-search"></i></button>
	                    </form>
	                </div>
	                <!-- /Search -->
	            </div>
	            <div class="pull-right">
	                <ul class="header-btns">
	                	<!-- users maintenance -->
	                	<a id="but_users_maintenance"
	                	 href="${pageContext.request.contextPath}<%= application.getInitParameter("users_maintenance_path") %>">
	                		<b class="text-uppercase">Users maintenance</b>
	                	</a>
	                	<!-- /users maintenance -->
	                	
	                    <!-- catalog maintenance -->
	                    <a id="but_catalog_maintenance"
	                     href="${pageContext.request.contextPath}<%= application.getInitParameter("catalogue_maintenance_path") %>">
	                        <b class="text-uppercase">Catalog Maintenance</b>
	                    </a>
	                    <!-- /catalog maintenance -->
	
	                    <!-- Account -->
	                    <a id="but_my_account"
	                     href="${pageContext.request.contextPath}<%= application.getInitParameter("admin_profile_edit_path") %>">
	                        <b class="text-uppercase">My Account</b>
	                    </a>
	                    <!-- /Account -->
	                </ul>
	            </div>
	        </div>
	        <!-- header -->
	    </div>
	    <!-- container -->
	</header>
	<!-- /HEADER -->


	<!--  content  -->
	<div id="content">
	
		<!--  section for searching users -->
		<section id="section_search_user">
			<h1>Search</h1>
			
			<!--  search user form  -->
			<form id="search_user_form">
				<input id="input_user_name" type="text" name="<%= application.getInitParameter("name") %>" 
					placeholder="name"/>
				<input id="input_user_surname" type="text" name="<%= application.getInitParameter("surname") %>"
					placeholder="surname"/>
				<input id="input_user_email" type="email" name="<%= application.getInitParameter("email") %>"
					placeholder="email"/>	
			</form><!--  /search user form  -->
		</section><!--  /section for searching users -->
		
		<!--  displaying users list  -->
		<section id="section_users_list">
			<!--  bar for choosing user type  -->
			<nav>
				<ul>
					<li>Buyers</li>
					<li>Sellers</li>
					<li>Administrators</li>
				</ul>
			</nav><!--  /bar for choosing user type  -->
			
			<!--  users list  -->
			<div id="users_list">
			</div><!--  /users list  -->
			
		</section><!--  /displaying users list  -->
		
	</div> <!--  /content  -->
</body>
</html>
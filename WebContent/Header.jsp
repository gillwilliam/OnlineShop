<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="beans.session.AdminBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%--     <!-- Google font -->
    <link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet">

    <!-- Bootstrap -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />

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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/catalogue_maintenance.css"/> --%>
</head>
<body>
<header>
    <!-- header -->
    <div id="header" style="padding-bottom: 100px">
        <div class="container-fluid">
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
	                	href="${pageContext.request.contextPath}<%= application.getInitParameter("users_maintenance_path") %>"
	                	style="display:<%= session.getAttribute(application.getInitParameter("signed_user_attribute_name"))
	                		instanceof AdminBean ? "inline" : "none" %>">
	                	<b class="text-uppercase">Users maintenance</b>
	                </a>
	                <!-- /users maintenance -->
	                	
                    <!-- catalog maintenance -->
                    <a id="but_catalog_maintenance" href="${pageContext.request.contextPath}<%= application.getInitParameter("catalogue_maintenance_path") %>">
                        <b class="text-uppercase">Catalog Maintenance</b>
                    </a>
                    <!-- /catalog maintenance -->

                    <!-- Account -->
                    <a id="but_my_account"
                     href="${pageContext.request.contextPath}<%= 
                     	session.getAttribute(application.getInitParameter("signed_user_attribute_name")) instanceof AdminBean ?
                     			application.getInitParameter("admin_profile_edit_path")
                     			:
                     			application.getInitParameter("seller_profile_edit_path") %>">
                        <b class="text-uppercase">My Account</b>
                    </a>
                    <!-- /Account -->

                    <!-- Mobile nav toggle-->
                    <li class="nav-toggle">
                        <button class="nav-toggle-btn main-btn icon-btn"><i class="fa fa-bars"></i></button>
                    </li>
                    <!-- / Mobile nav toggle -->
                </ul>
            </div>
        </div>
        <!-- header -->
    </div>
    <!-- container -->
</header>
</body>
</html>
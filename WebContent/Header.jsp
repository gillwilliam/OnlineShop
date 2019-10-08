<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="beans.session.AdminBean" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
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
                     href="${pageContext.request.contextPath}<%= application.getInitParameter("admin_profile_edit_path") %>">
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
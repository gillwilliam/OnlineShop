<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="beans.session.UserBean" %>
    <%@ page import="beans.session.BuyerBean" %>
    <%@ page import="beans.session.SellerBean" %>
    <%@ page import="beans.session.AdminBean" %>
    <%@ page import="entities.UserType" %>
    <%@ page import="java.util.HashMap" %>
    <%@ page import="java.util.ArrayList" %>
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
		ArrayList<BuyerBean> buyers		= null;
		ArrayList<SellerBean> sellers 	= null;
		ArrayList<AdminBean> admins		= null;
	
		Object usersToDisplayObj = request.getAttribute("found_users");
		if (usersToDisplayObj != null && usersToDisplayObj instanceof HashMap)
		{
			HashMap<UserType, ArrayList<UserBean>> usersToDisplay = (HashMap) usersToDisplayObj;
			buyers 	= (ArrayList) usersToDisplay.get(UserType.BUYER);
			sellers = (ArrayList) usersToDisplay.get(UserType.SELLER);
			admins 	= (ArrayList) usersToDisplay.get(UserType.ADMIN);
		}
		
		if (buyers == null)
			buyers = new ArrayList<BuyerBean>();
		if (sellers == null)
			sellers = new ArrayList<SellerBean>();
		if (admins == null)
			admins = new ArrayList<AdminBean>();
		
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
				<div id="search_inputs_container">
					<input id="input_user_name" type="text" name="<%= application.getInitParameter("name") %>" 
						placeholder="name"/>
					<input id="input_user_surname" type="text" name="<%= application.getInitParameter("surname") %>"
						placeholder="surname"/>
					<input id="input_user_email" type="email" name="<%= application.getInitParameter("email") %>"
						placeholder="email"/>	
				</div>
				
				<input id="but_search" type="submit" value="Search"/>
			</form><!--  /search user form  -->
		</section><!--  /section for searching users -->
		
		<!--  displaying users list  -->
		<section id="section_users_list">
			<!--  bar for choosing user type  -->
			<nav>
				<ul id="ul_navigation_between_user_types">
					<li>Buyers</li>
					<li>Sellers</li>
					<li>Administrators</li>
				</ul>
			</nav><!--  /bar for choosing user type  -->
			
			<!--  users list  -->
			<table id="buyers_table" class="users_table">
				<tr>
					<th>Name</th>
					<th>Surname</th>
					<th>Phone</th>
					<th>Address</th>
					<th>Email</th>
					<th>Password</th>
					<th>Action</th>
				</tr>
				<%
					for (BuyerBean buyer : buyers)
					{
						%>
							<tr>
								<td><%= buyer.getName() %></td>
								<td><%= buyer.getSurname() %></td>
								<td><%= buyer.getPhone() %></td>
								<td><%= buyer.getAddress() %></td>
								<td><%= buyer.getEmail() %></td>
								<td><%= buyer.getPassword() %></td>
								<td></td>
							</tr>
						<%
					}
				%>
			</table>
			
			<table id="sellers_table" class="users_table">
				<tr>
					<th>Name</th>
					<th>Surname</th>
					<th>Phone</th>
					<th>Email</th>
					<th>Password</th>
					<th>Action</th>
				</tr>
				<%
					for (SellerBean seller : sellers)
					{
						%>
							<tr>
								<td><%= seller.getName() %></td>
								<td><%= seller.getSurname() %></td>
								<td><%= seller.getPhone() %></td>
								<td><%= seller.getEmail() %></td>
								<td><%= seller.getPassword() %></td>
								<td></td>
							</tr>
						<%
					}
				%>
			</table>
			
			<table id="admins_table" class="users_table">
				<tr>
					<th>Name</th>
					<th>Surname</th>
					<th>Phone</th>
					<th>Email</th>
					<th>Password</th>
					<th>Action</th>
				</tr>
				<%
					for (AdminBean admin : admins)
					{
						%>
							<tr>
								<td><%= admin.getName() %></td>
								<td><%= admin.getSurname() %></td>
								<td><%= admin.getPhone() %></td>
								<td><%= admin.getEmail() %></td>
								<td><%= admin.getPassword() %></td>
								<td></td>
							</tr>
						<%
					}
				%>
			</table>
			
		</section><!--  /displaying users list  -->
		
	</div> <!--  /content  -->
</body>
</html>
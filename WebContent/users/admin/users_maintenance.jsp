<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="beans.session.UserBean" %>
    <%@ page import="beans.session.BuyerBean" %>
    <%@ page import="beans.session.SellerBean" %>
    <%@ page import="beans.session.AdminBean" %>
    <%@ page import="enums.UserType" %>
    <%@ page import="java.util.HashMap" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="java.lang.Math" %>
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
		
		int currMaxAmountOfDisplayedUsers = 0;
	
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
		
		currMaxAmountOfDisplayedUsers = Math.max(buyers.size(), Math.max(sellers.size(), admins.size()));
	%>


	<!-- HEADER -->
	<jsp:include page="../../Header.jsp"/>
	<!-- /HEADER -->


	<!--  content  -->
	<div id="content">
	
		<!--  section for searching users -->
		<section id="section_search_user">
			<h1>Search</h1>
			<p>Leave field empty if you want it to match any value</p>
			<!--  search user form  -->
			<form id="search_user_form" action="${pageContext.request.contextPath}/searchUsers
				<%= application.getInitParameter("main_front_controller_request_extension") %>" method="get">
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
					<li id="buyers_nav" class="users_nav" onclick="showBuyers()">Buyers</li>
					<li id="sellers_nav" class="users_nav" onclick="showSellers()">Sellers</li>
					<li id="admins_nav" class="users_nav" onclick="showAdmins()">Administrators</li>
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
								<td class="td_action">
									
									<!--  button edit profile  -->
									<form action="${pageContext.request.contextPath}/editBuyerProfile
										<%= application.getInitParameter("main_front_controller_request_extension") %>">
										<!-- just to make buyers page display this user -->
										<input type="text" name="otherUser" value="true" style="display:none"/>
										<input type="text" name="<%= application.getInitParameter("name") %>" 
											value="<%= buyer.getName() %>" style="display:none"/>
										<input type="text" name="<%= application.getInitParameter("surname") %>"
											value="<%= buyer.getSurname() %>" style="display:none"/>
										<input type="text" name="<%= application.getInitParameter("phone") %>"
											value="<%= buyer.getPhone() %>" style="display:none" />
										<input type="text" name="<%= application.getInitParameter("address") %>"
											value="<%= buyer.getAddress() %>" style="display:none" />
										<input type="text" name="<%= application.getInitParameter("email") %>"
											value="<%= buyer.getEmail() %>" style="display:none" />
										
										<input class="but_action but_edit_user" type="image"
											src="${pageContext.request.contextPath}/img/edit_icon.png" alt="edit"/>
									</form>

									<!--  button delete  -->
									<form action="${pageContext.request.contextPath}/deleteBuyer
										<%= application.getInitParameter("main_front_controller_request_extension") %>" method="post">
										<input type="text" name="<%= application.getInitParameter("email") %>" 
											value="<%= buyer.getEmail() %>" style="display:none"/>
			
										<!-- inputs that keep value from previous search -->										
										<input type="text" name="search_name" 
											value="<%= request.getParameter(application.getInitParameter("name")) %>" 
											style="display:none"/>
										
										<input type="text" name="search_surname"
											value="<%= request.getParameter(application.getInitParameter("surname")) %>"
											style="display:none" />
										
										<input type="text" name="search_email"
											value="<%= request.getParameter(application.getInitParameter("email")) %>"
											style="display:none" />
										
										
										<!-- button for beginning user deletion (after clicking confirmation button will appear -->
										<div class="but_action but_delete_user" 
											onclick="showFlex('<%= "confirm_" + buyer.getEmail() %>')">
											<img src="${pageContext.request.contextPath}/img/delete.png" alt="del" />
										</div>
										
										<!-- box for confirming user deletion -->
										<div id="confirm_<%= buyer.getEmail() %>" class="confirm_delete_box">
											<img src="${pageContext.request.contextPath}/img/close_icon.png" alt="close"
												onclick="hideClass('confirm_delete_box')"/>
											<p>Are you sure that you want to delete 
											<%= buyer.getName() + " " + buyer.getSurname() %>?</p>
											<input type="submit" value="Yes" />
										</div>
										
									</form>
                                    
								</td>
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
								<td class="td_action">
									<!--  button edit profile  -->
									<form action="${pageContext.request.contextPath}/editSellerProfile
										<%= application.getInitParameter("main_front_controller_request_extension") %>">
										<!-- just to make buyers page display this user -->
										<input type="text" name="otherUser" value="true" style="display:none"/>
										<input type="text" name="<%= application.getInitParameter("name") %>" 
											value="<%= seller.getName() %>" style="display:none"/>
										<input type="text" name="<%= application.getInitParameter("surname") %>"
											value="<%= seller.getSurname() %>" style="display:none" />
										<input type="text" name="<%= application.getInitParameter("phone") %>"
											value="<%= seller.getPhone() %>" style="display:none" />
										<input type="text" name="<%= application.getInitParameter("email") %>"
											value="<%= seller.getEmail() %>" style="display:none" />
										
										<input class="but_action but_edit_user" type="image"
											src="${pageContext.request.contextPath}/img/edit_icon.png" alt="edit"/>
									</form>
									
									<!--  button delete  -->
									<form action="${pageContext.request.contextPath}/deleteSeller
										<%= application.getInitParameter("main_front_controller_request_extension") %>" method="post">
										<input type="text" name="<%= application.getInitParameter("email") %>" 
											value="<%= seller.getEmail() %>" style="display:none"/>
			
										<!-- inputs that keep value from previous search -->										
										<input type="text" name="search_name" 
											value="<%= request.getParameter(application.getInitParameter("name")) %>" 
											style="display:none"/>
										
										<input type="text" name="search_surname"
											value="<%= request.getParameter(application.getInitParameter("surname")) %>"
											style="display:none" />
										
										<input type="text" name="search_email"
											value="<%= request.getParameter(application.getInitParameter("email")) %>"
											style="display:none" />
										
										
										<!-- button for beginning user deletion (after clicking confirmation button will appear -->
										<div class="but_action but_delete_user" 
											onclick="showFlex('<%= "confirm_" + seller.getEmail() %>')">
											<img src="${pageContext.request.contextPath}/img/delete.png" alt="del" />
										</div>
										
										<!-- box for confirming user deletion -->
										<div id="confirm_<%= seller.getEmail() %>" class="confirm_delete_box">
											<img src="${pageContext.request.contextPath}/img/close_icon.png" alt="close"
												onclick="hideClass('confirm_delete_box')"/>
											<p>Are you sure that you want to delete 
											<%= seller.getName() + " " + seller.getSurname() %>?</p>
											<input type="submit" value="Yes" />
										</div>
										
									</form>
								</td>
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
								<td class="td_action">
									<!--  button edit profile  -->
									<form action="${pageContext.request.contextPath}/editAdminProfile
										<%= application.getInitParameter("main_front_controller_request_extension") %>">
										<!-- just to make buyers page display this user -->
										<input type="text" name="otherUser" value="true" style="display:none"/>
										<input type="text" name="<%= application.getInitParameter("name") %>" 
											value="<%= admin.getName() %>" style="display:none"/>
										<input type="text" name="<%= application.getInitParameter("surname") %>"
											value="<%= admin.getSurname() %>" style="display:none" />
										<input type="text" name="<%= application.getInitParameter("phone") %>"
											value="<%= admin.getPhone() %>" style="display:none" />
										<input type="text" name="<%= application.getInitParameter("email") %>"
											value="<%= admin.getEmail() %>" style="display:none" />
										
										<input class="but_action but_edit_user" type="image"
											src="${pageContext.request.contextPath}/img/edit_icon.png" alt="edit"/>
									</form>
									
									<!--  button delete  -->
									<form action="${pageContext.request.contextPath}/deleteAdmin
										<%= application.getInitParameter("main_front_controller_request_extension") %>" method="post">
										<input type="text" name="<%= application.getInitParameter("email") %>" 
											value="<%= admin.getEmail() %>" style="display:none"/>
			
										<!-- inputs that keep value from previous search -->										
										<input type="text" name="search_name" 
											value="<%= request.getParameter(application.getInitParameter("name")) %>" 
											style="display:none"/>
										
										<input type="text" name="search_surname"
											value="<%= request.getParameter(application.getInitParameter("surname")) %>"
											style="display:none" />
										
										<input type="text" name="search_email"
											value="<%= request.getParameter(application.getInitParameter("email")) %>"
											style="display:none" />
										
										
										<!-- button for beginning user deletion (after clicking confirmation button will appear -->
										<div class="but_action but_delete_user" 
											onclick="showFlex('<%= "confirm_" + admin.getEmail() %>')">
											<img src="${pageContext.request.contextPath}/img/delete.png" alt="del" />
										</div>
										
										<!-- box for confirming user deletion -->
										<div id="confirm_<%= admin.getEmail() %>" class="confirm_delete_box">
											<img src="${pageContext.request.contextPath}/img/close_icon.png" alt="close"
												onclick="hideClass('confirm_delete_box')"/>
											<p>Are you sure that you want to delete 
											<%= admin.getName() + " " + admin.getSurname() %>?</p>
											<input type="submit" value="Yes" />
										</div>
										
									</form>
								</td>
							</tr>
						<%
					}
				%>
			</table>
			
		</section><!--  /displaying users list  -->
			
			<form action="${pageContext.request.contextPath}/searchUsers<%= application.getInitParameter("main_front_controller_request_extension") %>" 
				method="post">
				<input type="text" name="<%= application.getInitParameter("name") %>" 
					value="<%= request.getParameter(application.getInitParameter("name")) %>"
					style="display:none" />
				<input type="text" name="<%= application.getInitParameter("surname") %>" 
					value="<%= request.getParameter(application.getInitParameter("surname")) %>"
					style="display:none" />
				<input type="text" name="<%= application.getInitParameter("email") %>" 
					value="<%= request.getParameter(application.getInitParameter("email")) %>"
					style="display:none" />	
				<input name="<%= application.getInitParameter("max_num_of_results") %>" 
					value="<%= currMaxAmountOfDisplayedUsers + 200 %>" style="display: none;"/>
					
				<input id="but_load_more" type="submit" value="Load more"/>
			</form>
		
	</div> <!--  /content  -->
	
	

<script>
	function showBuyers()
	{
		hideAllTables();
		resetAllColors();
		document.getElementById("buyers_table").style.display = "table";
		setSelectedColor(document.getElementById("buyers_nav"));
	}
	
	
	
	function showSellers()
	{
		hideAllTables();
		resetAllColors();
		document.getElementById("sellers_table").style.display = "table";
		setSelectedColor(document.getElementById("sellers_nav"));
	}
	
	
	
	function showAdmins()
	{
		hideAllTables();
		resetAllColors();
		document.getElementById("admins_table").style.display = "table";
		setSelectedColor(document.getElementById("admins_nav"));
	}
	
	
	
	function hideAllTables()
	{
		var tables = document.getElementsByClassName("users_table");
		var i;
		for (i = 0; i < 3; i++)
		{
			tables[i].style.display = "none";
		}
	}
	
	
	function resetAllColors()
	{
		var headers = document.getElementsByClassName("users_nav");
		var i;
		for (i = 0; i < 3; i++)
		{
			headers[i].style.backgroundColor = "#f8694a";
		}
	}
	
	
	
	function setSelectedColor(item)
	{
		item.style.backgroundColor = "#6f7387";
	}
	
	
	
	function showFlex(id)
	{
		document.getElementById(id).style.display = "flex";
	}
	
	
	
	function hideClass(className)
	{
		toHide = document.getElementsByClassName(className);
		var i;
		for (i = 0; i < toHide.length; i++)
		{
			toHide[i].style.display = "none"
		}
	}

</script>


</body>
</html>
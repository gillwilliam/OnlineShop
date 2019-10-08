<%@ page import="request_handlers.users.EditUserProfileRequestHandler" %>
<%@ page import="beans.session.SellerBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seller's profile</title>

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


    <!-- my custom css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/user_profile.css"/>
</head>
<body>

<!-- beans |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||-->
<jsp:useBean id="user" class="beans.session.SellerBean" scope="session">
	<jsp:setProperty name="user" property="*" />
</jsp:useBean>

<%
	boolean useOtherUser = false;	// if false, then this page concerns currently signed in user. 
	// if true, then the page concerns user, that was passed in
	// request. This is used when admin edits some other user data
	
	if (request != null)
	{
		String param = request.getParameter("otherUser");
		useOtherUser = param != null ? param.equals("true") : false;
	}
	
	SellerBean otherSeller = new SellerBean();
	
	if (useOtherUser)
	{
		otherSeller.setName(request.getParameter(application.getInitParameter("name")));
		otherSeller.setSurname(request.getParameter(application.getInitParameter("surname")));
		otherSeller.setPhone(request.getParameter(application.getInitParameter("phone")));
		otherSeller.setEmail(request.getParameter(application.getInitParameter("email")));
	}
	
	// after data edit /////////////////////////////////////////////////////////////////////////////
	// if user has changed data, then he is redirected to this page again and in case some errors
	// occured he is informed about that. Information 
	EditUserProfileRequestHandler.InputValidationResult validationResult =
	(EditUserProfileRequestHandler.InputValidationResult) request
	.getAttribute(application.getInitParameter("buyer_profile_edit_result"));
	
	String nameMessage, surnameMessage, phoneMessage, addressMessage, emailMessage, passwordMessage,
	confirmedPasswordMessage;
	
	if (validationResult != null)
	{
		nameMessage                 = validationResult.getNameMessage();
		surnameMessage              = validationResult.getSurnameMessage();
		phoneMessage                = validationResult.getPhoneMessage();
		addressMessage              = validationResult.getAddressMessage();
		emailMessage                = validationResult.getEmailMessage();
		passwordMessage             = validationResult.getNewPasswordMessage();
		confirmedPasswordMessage    = validationResult.getConfirmedPasswordMessage();
	}
	else
	{
		nameMessage = surnameMessage = phoneMessage = addressMessage = emailMessage = passwordMessage =
		confirmedPasswordMessage = "";
	}
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
                    <!-- catalog maintenance -->
                    <a href="${pageContext.request.contextPath}<%= application.getInitParameter("catalogue_maintenance_path") %>">
                        <b id="but_catalog_maintenance" class="text-uppercase">Catalog Maintenance</b>
                    </a>
                    <!-- /catalog maintenance -->

                    <!-- Account -->
                    <a href="${pageContext.request.contextPath}<%= application.getInitParameter("seller_profile_edit_path") %>">
                        <b id="but_my_account" class="text-uppercase">My Account</b>
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
<!-- /HEADER -->

<!-- site content container -->
<div id="container">

    <!-- contains message about successful edit. Initially it's invisible -->
    <div id="message_box" class="<%= validationResult == null || validationResult.isValid() ? "invisible" : "" %>">
            <span id="message">
                <%= validationResult != null && validationResult.isValid() ? "Successful data update" :
                        "Errors occurred during data update" %>
            </span>
    </div>

    <!--  form for editing user's data -->
    <form id="user_data_form" action="editSellerProfile.main" method="post">
        <!--  section with users's personal data used for order purposes -->
        <section id="personal_data_section">
            <h1>Personal data</h1>
            <input id="name" 	type="text" name="<%= application.getInitParameter("name")%>"		placeholder="Name"
            value="<%= useOtherUser ? otherSeller.getName() : user.getName() %>">
    
            <span class="error"><%= nameMessage %></span>

            <input id="surname" type="text" name="<%= application.getInitParameter("surname")%>"	placeholder="Surname"
            value="<%= useOtherUser ? otherSeller.getSurname() : user.getSurname() %>"/>
            
            <span class="error"><%= surnameMessage %></span>

            <input id="phone" 	type="tel" 	name="<%= application.getInitParameter("phone")%>"	    placeholder="Phone number"
            value="<%= useOtherUser ? otherSeller.getPhone() : user.getPhone() %>"/>
            
            <span class="error"><%= phoneMessage %></span>
            
        </section>
        <!-- sections with data used to sign in -->
        <section id="login_data_section">
            <h1>Login data</h1>
            <input id="email" type="email" 	name="<%= application.getInitParameter("email")%>"
                   placeholder="Email" value="<%= useOtherUser ? otherSeller.getEmail() : user.getEmail() %>" readonly/>
            <span class="error"><%= emailMessage %></span>

            <input id="new_password" type="password" name="<%= application.getInitParameter("new_password")%>"
                   placeholder="New password"/>
            <span class="error"><%= passwordMessage %></span>

            <input id="confirm_password" type="password" name="<%= application.getInitParameter("confirmed_password")%>"
                   placeholder="Confirm password"/>
            <span class="error"><%= confirmedPasswordMessage %></span>
        </section>

		<!-- hidden input for passing useOtherUser variable value -->
		<input type="text" name="otherUser" value="<%= useOtherUser %>" style="display:none"/>
			
		<!-- submit -->	
        <input id="but_save" type="submit" value="save"/>
    </form>

    <!-- delete account button -->
    <div id="but_delete_account">
        <img src="${pageContext.request.contextPath}/img/delete.png" alt=""/>
        <span>Delete account</span>
    </div>

</div>
</body>
</html>

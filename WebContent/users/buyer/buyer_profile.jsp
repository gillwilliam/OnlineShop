<%@ page import="request_handlers.users.EditUserProfileRequestHandler" %>
<%@ page import="beans.session.BuyerBean" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Buyer's Profile</title>
    <!-- Custom stlylesheet -->

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/user_profile.css"/>

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

</head>
<body>

    <%
		boolean useOtherUser = false;	// if false, then this page concerns currently signed in user. 
		// if true, then the page concerns user, that was passed in
		// request. This is used when admin edits some other user data
		
		if (request != null)
		{
			String param = request.getParameter("otherUser");
			useOtherUser = param != null ? param.equals("true") : false;
		}
		
		BuyerBean otherBuyer = new BuyerBean();
		BuyerBean user	     = new BuyerBean();
		
		if (useOtherUser)
		{
			otherBuyer.setName(request.getParameter(application.getInitParameter("name")));
			otherBuyer.setSurname(request.getParameter(application.getInitParameter("surname")));
			otherBuyer.setAddress(request.getParameter(application.getInitParameter("address")));
			otherBuyer.setPhone(request.getParameter(application.getInitParameter("phone")));
			otherBuyer.setEmail(request.getParameter(application.getInitParameter("email")));
		}
		else
			user = (BuyerBean) session.getAttribute("user");
		
		// after data edit /////////////////////////////////////////////////////////////////////////////
		// if user has changed data, then he is redirected to this page again and in case some errors
		// occured he is informed about that. Information 
		EditUserProfileRequestHandler.InputValidationResult validationResult =
		(EditUserProfileRequestHandler.InputValidationResult) request.getAttribute(
		application.getInitParameter("buyer_profile_edit_result"));
		
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

    <!-- header |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||-->
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
						<!-- Account -->
						<li class="header-account dropdown default-dropdown">
							<div class="dropdown-toggle" role="button" data-toggle="dropdown" aria-expanded="true">
								<div class="header-btns-icon">
									<i class="fa fa-user-o"></i>
								</div>
								<strong class="text-uppercase">My Account <i class="fa fa-caret-down"></i></strong>
							</div>
							<ul class="custom-menu">
								<li><a href="#"><i class="fa fa-user-o"></i> My Account</a></li>
								<li><a href="#"><i class="fa fa-heart-o"></i> My Wishlist</a></li>
								<li><a href="#"><i class="fa fa-exchange"></i> Compare</a></li>
								<li><a href="#"><i class="fa fa-check"></i> Checkout</a></li>
								<li><a href="#"><i class="fa fa-unlock-alt"></i> Login</a></li>
								<li><a href="#"><i class="fa fa-user-plus"></i> Create An Account</a></li>
							</ul>
						</li>
						<!-- /Account -->

						<!-- Cart -->
						<li class="header-cart dropdown default-dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
								<div class="header-btns-icon">
									<i class="fa fa-shopping-cart"></i>
									<span class="qty">3</span>
								</div>
								<strong class="text-uppercase">My Cart:</strong>
								<br>
								<span>35.20$</span>
							</a>
							<div class="custom-menu">
								<div id="shopping-cart">
									<div class="shopping-cart-list">
										<div class="product product-widget">
											<div class="product-thumb">
												<img src="./img/thumb-product01.jpg" alt="">
											</div>
											<div class="product-body">
												<h3 class="product-price">$32.50 <span class="qty">x3</span></h3>
												<h2 class="product-name"><a href="#">Product Name Goes Here</a></h2>
											</div>
											<button class="cancel-btn"><i class="fa fa-trash"></i></button>
										</div>
										<div class="product product-widget">
											<div class="product-thumb">
												<img src="./img/thumb-product01.jpg" alt="">
											</div>
											<div class="product-body">
												<h3 class="product-price">$32.50 <span class="qty">x3</span></h3>
												<h2 class="product-name"><a href="#">Product Name Goes Here</a></h2>
											</div>
											<button class="cancel-btn"><i class="fa fa-trash"></i></button>
										</div>
									</div>
									<div class="shopping-cart-btns">
										<button class="main-btn">View Cart</button>
										<button class="primary-btn">Checkout <i class="fa fa-arrow-circle-right"></i></button>
									</div>
								</div>
							</div>
						</li>
						<!-- /Cart -->

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
        <form id="user_data_form" action="editBuyerProfile.main" method="post">
            <!--  section with users's personal data used for order purposes -->
            <section id="personal_data_section">
                <h1>Personal data</h1>
                <input id="name" 	type="text" name="<%= application.getInitParameter("name")%>"		placeholder="Name"
                value="<%= useOtherUser ? otherBuyer.getName() : user.getName() %>"/>
                
                <span class="error"><%= nameMessage %></span>

                <input id="surname" type="text" name="<%= application.getInitParameter("surname")%>"	placeholder="Surname"
                value="<%= useOtherUser ? otherBuyer.getSurname() : user.getSurname() %>"/>
                
                <span class="error"><%= surnameMessage %></span>

                <input id="phone" 	type="tel" 	name="<%= application.getInitParameter("phone")%>"	    placeholder="Phone number"
                value="<%= useOtherUser ? otherBuyer.getPhone() : user.getPhone() %>"/>
                <span class="error"><%= phoneMessage %></span>

                <input id="address" type="text" name="<%= application.getInitParameter("address")%>"	placeholder="Address"
                value="<%= useOtherUser ? otherBuyer.getAddress() : user.getAddress() %>"/>
                
                <span class="error"><%= addressMessage %></span>
                
            </section>
            <!-- sections with data used to sign in -->
            <section id="login_data_section">
                <h1>Login data</h1>
                <input id="email" type="email" 	name="<%= application.getInitParameter("email")%>"
                       placeholder="Email" 
                       value="<%= useOtherUser ? otherBuyer.getEmail() : user.getEmail() %>"
                       readonly/>
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
			
			<!--  submit -->
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

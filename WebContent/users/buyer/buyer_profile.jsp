<%@ page import="request_handlers.users.EditUserProfileRequestHandler" %>
<%@ page import="beans.session.BuyerBean" %>
<%@ page import="utils.UserDataValidator" %>
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
		UserDataValidator.InputValidationResult validationResult =
		(UserDataValidator.InputValidationResult) request.getAttribute(
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
		
		EditUserProfileRequestHandler.UpdateInDBResult updateResult = 
				(EditUserProfileRequestHandler.UpdateInDBResult) request.
				getAttribute(application.getInitParameter("user_profile_update_result"));
    %>

    <!-- header |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||-->
    <!-- HEADER -->
	<jsp:include page="../../Header.jsp"/>
	<!-- /HEADER -->

    <!-- site content container -->
    <div id="container">

        <!-- contains message about successful edit. Initially it's invisible -->
        <div id="message_box" class="<%= validationResult == null && updateResult == null ? "invisible" : "" %>"
    		style="background-color:<%= validationResult != null && validationResult.isValid() &&
                updateResult != null && updateResult.isUpdateSuccessful ? "#4ed93f" : "#f8694a" %>">
            <span id="message">
                <%= validationResult != null && validationResult.isValid() &&
                updateResult != null && updateResult.isUpdateSuccessful ? "Successful data update" :
                        "Errors occurred during data update. " + (updateResult != null ? updateResult.message : "") %>
            </span>
    	</div>

        <!--  form for editing user's data -->
        <form id="user_data_form" action="${pageContext.request.contextPath}/editBuyerProfile.main" method="post">
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
        <form action="
        ${pageContext.request.contextPath}/deleteBuyer<%= application.getInitParameter("main_front_controller_request_extension") %>">
        
        	<input type="hidden" name="<%= application.getInitParameter("email") %>" value="<%= user.getEmail() %>"/>
        	<input type="hidden" name="requester" value="buyer" />
	        <input id="but_delete_account" type="submit" value="Delete account"/>
        </form>

    </div>

</body>
</html>

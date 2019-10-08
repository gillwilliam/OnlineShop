<%@ page import="request_handlers.users.EditUserProfileRequestHandler" %>
<%@ page import="beans.session.AdminBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin's profile</title>
    
	<!-- header -->
    <!-- Google font -->
    <link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet">

    <!-- Bootstrap -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />

    

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
<jsp:useBean id="user" class="beans.session.AdminBean">
	<jsp:setProperty name="user" property="name" value="John"/>
	<jsp:setProperty name="user" property="surname" value="Doe"/>
	<jsp:setProperty name="user" property="phone" value="+34 875 764 322"/>
	<jsp:setProperty name="user" property="email" value="ggg@gmail.com"/>
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
	
	AdminBean otherAdmin = new AdminBean();
	
	if (useOtherUser)
	{
		otherAdmin.setName(request.getParameter(application.getInitParameter("name")));
		otherAdmin.setSurname(request.getParameter(application.getInitParameter("surname")));
		otherAdmin.setPhone(request.getParameter(application.getInitParameter("phone")));
		otherAdmin.setEmail(request.getParameter(application.getInitParameter("email")));
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
<jsp:include page="../../Header.jsp"/>
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
    <form id="user_data_form" action="editAdminProfile.main" method="post">
        <!--  section with users's personal data used for order purposes -->
        <section id="personal_data_section">
            <h1>Personal data</h1>
            <input id="name" 	type="text" name="<%= application.getInitParameter("name")%>"		placeholder="Name"
            value="<%= useOtherUser ? otherAdmin.getName() : user.getName() %>">
    
            <span class="error"><%= nameMessage %></span>

            <input id="surname" type="text" name="<%= application.getInitParameter("surname")%>"	placeholder="Surname"
            value="<%= useOtherUser ? otherAdmin.getSurname() : user.getSurname() %>"/>
            
            <span class="error"><%= surnameMessage %></span>

            <input id="phone" 	type="tel" 	name="<%= application.getInitParameter("phone")%>"	    placeholder="Phone number"
            value="<%= useOtherUser ? otherAdmin.getPhone() : user.getPhone() %>"/>
            
            <span class="error"><%= phoneMessage %></span>
            
        </section>
        <!-- sections with data used to sign in -->
        <section id="login_data_section">
            <h1>Login data</h1>
            <input id="email" type="email" 	name="<%= application.getInitParameter("email")%>"
                   placeholder="Email" value="<%= useOtherUser ? otherAdmin.getEmail() : user.getEmail() %>" readonly/>
            <span class="error"><%= emailMessage %></span>

            <input id="new_password" type="password" name="<%= application.getInitParameter("new_password")%>"
                   placeholder="New password"/>
            <span class="error"><%= passwordMessage %></span>

            <input id="confirm_password" type="password" name="<%= application.getInitParameter("confirmed_password")%>"
                   placeholder="Confirm password"/>
            <span class="error"><%= confirmedPasswordMessage %></span>
        </section>

		<!-- hidden input for passing useOtherUser variable value -->
		<input type="text" name="otherUser" value="<%= useOtherUser %>" />
			
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
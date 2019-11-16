<%@ page import="request_handlers.EditUserProfileRequestHandler" %>
<%@ page import="utils.UserDataValidator" %>
<%@ page import="entities.Admin" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin's profile</title>
    
    <!-- custom css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/user_profile.css"/>
</head>
<body>

<!-- beans |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||-->
<jsp:useBean id="user" class="entities.Admin" scope="session">
	<jsp:setProperty name="user" property="*"/>
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
	
	Admin otherAdmin = new Admin();
	
	if (useOtherUser)
	{
		otherAdmin.setFirstName(request.getParameter(application.getInitParameter("name")));
		otherAdmin.setLastName(request.getParameter(application.getInitParameter("surname")));
		otherAdmin.setPhone(request.getParameter(application.getInitParameter("phone")));
		otherAdmin.setEmail(request.getParameter(application.getInitParameter("email")));
	}
	
	// after data edit /////////////////////////////////////////////////////////////////////////////
	// if user has changed data, then he is redirected to this page again and in case some errors
	// occured he is informed about that. Information 
	UserDataValidator.InputValidationResult validationResult =
	(UserDataValidator.InputValidationResult) request
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
	
	EditUserProfileRequestHandler.UpdateInDBResult updateResult = 
	(EditUserProfileRequestHandler.UpdateInDBResult) request.
	getAttribute(application.getInitParameter("user_profile_update_result"));
%>

<!-- HEADER -->
<jsp:include page="../../Header.jsp"/>
<!-- /HEADER -->

<!-- site content container -->
<div id="container">

    <!-- contains message about successful edit. Initially it's invisible -->
    <div id="message_box" class="<%= validationResult == null ? "invisible" : "" %>"
    	style="background-color:<%= validationResult != null && validationResult.isValid() &&
                updateResult != null && updateResult.isUpdateSuccessful ? "#4ed93f" : "#f8694a" %>">
            <span id="message">
                <%= validationResult != null && validationResult.isValid() &&
                updateResult != null && updateResult.isUpdateSuccessful ? "Successful data update" :
                        "Errors occurred during data update. " + (updateResult != null ? updateResult.message : "") %>
            </span>
    </div>

    <!--  form for editing user's data -->
    <form id="user_data_form" action="${pageContext.request.contextPath}/editAdminProfile.main" method="post">
        <!--  section with users's personal data used for order purposes -->
        <section id="personal_data_section">
            <h1>Personal data</h1>
            <input id="name" 	type="text" name="<%= application.getInitParameter("name")%>"		placeholder="Name"
            value="<%= useOtherUser ? otherAdmin.getFirstName() : user.getFirstName() %>">
    
            <span class="error"><%= nameMessage %></span>

            <input id="surname" type="text" name="<%= application.getInitParameter("surname")%>"	placeholder="Surname"
            value="<%= useOtherUser ? otherAdmin.getLastName() : user.getLastName() %>"/>
            
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
		<input type="text" name="otherUser" value="<%= useOtherUser %>" style="display:none"/>
			
		<!-- submit -->
        <input id="but_save" type="submit" value="save"/>
    </form>

</div>
</body>
</html>
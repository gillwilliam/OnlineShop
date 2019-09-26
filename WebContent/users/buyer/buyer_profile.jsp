<%@ page import="servlets.users.buyer.BuyerProfileEditServlet" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Buyer's Profile</title>
    <!-- Custom stlylesheet -->

    <link rel="stylesheet" type="text/css" href="css/buyer_profile.css"/>


</head>
<body>

    <!-- beans |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||-->
    <jsp:useBean id="user" class="beans.session.UserBean">

    </jsp:useBean>

    <%
        BuyerProfileEditServlet.InputValidationResult validationResult =
                (BuyerProfileEditServlet.InputValidationResult) request.getAttribute(
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
    <section id="header">
        <div id="my_cart">
            <span id="my_cart_caption">MY CART</span>
            <span id="cart_sum">32.50$</span>
        </div>
    </section>

    <!-- site content container -->
    <div id="container">

        <!-- contains message about successful edit. Initially it's invisible -->
        <div id="message_box" class="<%= validationResult == null ? "invisible" : "" %>">
            <span id="message">
                <%= validationResult != null && validationResult.isValid() ? "Successful data update" :
                        "Errors occurred during data update" %>
            </span>
        </div>

        <!--  form for editing user's data -->
        <form id="user_data_form" action="editBuyerProfile" method="post">
            <!--  section with users's personal data used for order purposes -->
            <section id="personal_data_section">
                <h1>Personal data</h1>
                <input id="name" 	type="text" name="<%= application.getInitParameter("name")%>"		placeholder="Name"/>
                <span class="error"><%= nameMessage %></span>

                <input id="surname" type="text" name="<%= application.getInitParameter("surname")%>"	placeholder="Surname"/>
                <span class="error"><%= surnameMessage %></span>

                <input id="phone" 	type="tel" 	name="<%= application.getInitParameter("phone")%>"	    placeholder="Phone number"/>
                <span class="error"><%= phoneMessage %></span>

                <input id="address" type="text" name="<%= application.getInitParameter("address")%>"	placeholder="Address"/>
                <span class="error"><%= addressMessage %></span>
            </section>
            <!-- sections with data used to sign in -->
            <section id="login_data_section">
                <h1>Login data</h1>
                <input id="email" type="email" 	name="<%= application.getInitParameter("email")%>"
                       placeholder="Email" readonly/>
                <span class="error"><%= emailMessage %></span>

                <input id="new_password" type="password" name="<%= application.getInitParameter("new_password")%>"
                       placeholder="New password"/>
                <span class="error"><%= passwordMessage %></span>

                <input id="confirm_password" type="password" name="<%= application.getInitParameter("confirmed_password")%>"
                       placeholder="Confirm password"/>
                <span class="error"><%= confirmedPasswordMessage %></span>
            </section>

            <input id="but_save" type="submit" value="save"/>
        </form>

        <!-- delete account button -->
        <div id="but_delete_account">
            <img src="img/delete.png" alt=""/>
            <span>Delete account</span>
        </div>

    </div>

</body>
</html>

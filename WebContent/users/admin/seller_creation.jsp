<%@ page import="request_handlers.CreateSellerRequestHandler"%>
<%@ page import="entities.User"%>
<%@ page import="utils.UserDataValidator"%>
<%@ page import="utils.Result"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Seller creation</title>

<!-- my custom css -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/user_profile.css" />
</head>
<body>
<body>

	<%
		User seller = new User();

		seller.setFirstName(request.getParameter(application.getInitParameter("name")));
		seller.setLastName(request.getParameter(application.getInitParameter("surname")));
		seller.setPhone(request.getParameter(application.getInitParameter("phone")));
		seller.setEmail(request.getParameter(application.getInitParameter("email")));
		seller.setPassword(request.getParameter(application.getInitParameter("password")));

		if (seller.getFirstName() == null)
			seller.setFirstName("");
		if (seller.getLastName() == null)
			seller.setLastName("");
		if (seller.getPhone() == null)
			seller.setPhone("");
		if (seller.getEmail() == null)
			seller.setEmail("");
		if (seller.getPassword() == null)
			seller.setPassword("");

		// after data edit /////////////////////////////////////////////////////////////////////////////
		// if user has changed data, then he is redirected to this page again and in case some errors
		// occured he is informed about that.
		UserDataValidator.InputValidationResult validationResult = (UserDataValidator.InputValidationResult) request
				.getAttribute(application.getInitParameter("user_profile_edit_result"));

		String nameMessage, surnameMessage, phoneMessage, emailMessage, passwordMessage, confirmedPasswordMessage;

		if (validationResult != null) {
			nameMessage = validationResult.getNameMessage();
			surnameMessage = validationResult.getSurnameMessage();
			phoneMessage = validationResult.getPhoneMessage();
			emailMessage = validationResult.getEmailMessage();
			passwordMessage = validationResult.getNewPasswordMessage();
			confirmedPasswordMessage = validationResult.getConfirmedPasswordMessage();
		} else {
			nameMessage = surnameMessage = phoneMessage = emailMessage = passwordMessage = confirmedPasswordMessage = "";
		}

		Result updateResult = (Result) request
				.getAttribute(application.getInitParameter("user_profile_update_result"));
	%>

	<!-- HEADER -->
	<jsp:include page="../../Header.jsp" />
	<!-- /HEADER -->

	<!-- site content container -->
	<div id="container">

		<!-- contains message about successful edit. Initially it's invisible -->
		<div id="message_box"
			class="<%=validationResult == null ? "invisible" : ""%>"
			style="background-color:<%=validationResult != null && validationResult.isValid() && updateResult != null
					&& updateResult.success ? "#4ed93f" : "#f8694a"%>">
			<span id="message"> <%=validationResult != null && validationResult.isValid() && updateResult != null
					&& updateResult.success ? ""
							: "Errors occurred during data update. "
									+ (updateResult == null ? "" : updateResult.message)%>
			</span>
		</div>

		<!--  form for editing user's data -->
		<form id="user_data_form"
			action="${pageContext.request.contextPath}/createSeller.main"
			method="post">
			<!--  section with users's personal data used for order purposes -->
			<section id="personal_data_section">
				<h1>Personal data</h1>
				<input id="name" type="text"
					name="<%=application.getInitParameter("name")%>" placeholder="Name"
					value="<%=seller.getFirstName()%>" /> <span class="error"><%=nameMessage%></span>
				<input id="surname" type="text"
					name="sur"
					placeholder="Surname" value="<%=seller.getLastName()%>" /> <span
					class="error"><%=surnameMessage%></span> <input id="phone"
					type="tel" name="<%=application.getInitParameter("phone")%>"
					placeholder="Phone number" value="<%=seller.getPhone()%>" /> <span
					class="error"><%=phoneMessage%></span>

			</section>
			<!-- sections with data used to sign in -->
			<section id="login_data_section">
				<h1>Login data</h1>
				<input id="email" type="email"
					name="<%=application.getInitParameter("email")%>"
					placeholder="Email" value="<%=seller.getEmail()%>" /> <span
					class="error"><%=emailMessage%></span> <input id="new_password"
					type="password"
					name="<%=application.getInitParameter("new_password")%>"
					value="<%=seller.getPassword()%>" placeholder="New password" /> <span
					class="error"><%=passwordMessage%></span> <input
					id="confirm_password" type="password"
					name="<%=application.getInitParameter("confirmed_password")%>"
					placeholder="Confirm password" /> <span class="error"><%=confirmedPasswordMessage%></span>
			</section>

			<!-- submit -->
			<input id="but_save" type="submit" value="create" />
		</form>

	</div>
</body>
</html>
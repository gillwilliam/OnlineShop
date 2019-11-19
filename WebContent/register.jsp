<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="request_handlers.RegisterRequestHandler"%>
<%@ page import="utils.UserDataValidator"%>
<%@ page import="entities.User"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Register Account</title>

</head>
<jsp:include page="Header.jsp" />
<body>

	<!-- beans |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||-->
	<jsp:useBean id="user" class="entities.User" scope="session">
		<jsp:setProperty name="user" property="*" />
	</jsp:useBean>

	<%
		// After Data Edit
		// Show Error Messages
		UserDataValidator.InputValidationResult validationResult = (UserDataValidator.InputValidationResult) request
				.getAttribute(application.getInitParameter("register_result"));

		String nameMessage, surnameMessage, phoneMessage, addressMessage, emailMessage, passwordMessage,
				confirmedPasswordMessage;

		if (validationResult != null) {
			nameMessage = validationResult.getNameMessage();
			surnameMessage = validationResult.getSurnameMessage();
			phoneMessage = validationResult.getPhoneMessage();
			addressMessage = validationResult.getAddressMessage();
			emailMessage = validationResult.getEmailMessage();
			passwordMessage = validationResult.getNewPasswordMessage();
			confirmedPasswordMessage = validationResult.getConfirmedPasswordMessage();
		} else {
			nameMessage = surnameMessage = phoneMessage = addressMessage = emailMessage = passwordMessage = confirmedPasswordMessage = "";
		}
	%>
	<jsp:include page="Navigation.jsp" />


	<!-- section -->
	<div class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<div class="col-md-6">
					<form id="login-form"
						action="${pageContext.request.contextPath}/register.main"
						method="post">
						<h3>Register Account</h3>
						<div style="color: #FF0000;">${ registerErrorMessage }</div>
						<div class="form-group">
							<input class="input" type="text"
								name="<%=application.getInitParameter("name")%>"
								placeholder="Name"> <span style="color: red"><%=nameMessage%></span>
						</div>
						<div class="form-group">
							<input class="input" type="text"
								name="<%=application.getInitParameter("surname")%>"
								placeholder="Surname"> <span style="color: red"><%=surnameMessage%></span>
						</div>
						<div class="form-group">
							<input class="input" type="text" name="address"
								placeholder="Address"> <span style="color: red"><%=addressMessage%></span>
						</div>
						<div class="form-group">
							<input class="input" type="text" name="phone"
								placeholder="Phone Number"> <span style="color: red"><%=phoneMessage%></span>
						</div>
						<div class="form-group">
							<input class="input" type="text" name="email" placeholder="Email">
							<span style="color: red"><%=emailMessage%></span>
						</div>
						<div class="form-group">
							<input class="input" type="password" name="new_password"
								placeholder="Password"> <span style="color: red"><%=passwordMessage%></span>
						</div>
						<div class="form-group">
							<input class="input" type="password" name="confirmed_password"
								placeholder="Repeat password"> <span style="color: red"><%=confirmedPasswordMessage%></span>
						</div>
						<input type="submit" class="primary-btn" value="Register" /> <a
							href="${pageContext.request.contextPath}/login.jsp"
							class="main-btn">Login</a>
					</form>

				</div>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /section -->

	<!-- jQuery Plugins -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/slick.min.js"></script>
	<script src="js/nouislider.min.js"></script>
	<script src="js/jquery.zoom.min.js"></script>
	<script src="js/main.js"></script>

</body>

</html>

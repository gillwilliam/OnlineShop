<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Register Account</title>

</head>
	<jsp:include page="Header.jsp"/>
<body>
	<jsp:include page="Navigation.jsp"/>
	
	<!-- section -->
	<div class="section">
		<!-- container -->
		<div class="container">
			<!-- row -->
			<div class="row">
				<div class="col-md-6">
					<form id="login-form" action="${pageContext.request.contextPath}/signIn.main" method="post">
						<h3>Register Account</h3>
						<div style="color: #FF0000;">${ registerErrorMessage } </div>
						<div class="form-group">
							<input class="input" type="text" name="<%= application.getInitParameter("name") %>" 
								placeholder="Name">
						</div>
						<div class="form-group">
							<input class="input" type="text" name="<%= application.getInitParameter("surname") %>" 
								placeholder="Surname">
						</div>
						<div class="form-group">
							<input class="input" type="text" name="email" placeholder="Email">
						</div>												
						<div class="form-group">
							<input class="input" type="password" name="password" placeholder="Password">
						</div>
						<div class="form-group">
							<input class="input" type="password" name="repeatPassword" placeholder="Repeat password">
						</div>
						<input type="submit" class="primary-btn" value="Register"/>
						<button class="main-btn">Login</button>		
					</form>

				</div>
			</div>
			<!-- /row -->
		</div>
		<!-- /container -->
	</div>
	<!-- /section -->



	<jsp:include page="Footer.jsp"/>

	<!-- jQuery Plugins -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/slick.min.js"></script>
	<script src="js/nouislider.min.js"></script>
	<script src="js/jquery.zoom.min.js"></script>
	<script src="js/main.js"></script>

</body>

</html>

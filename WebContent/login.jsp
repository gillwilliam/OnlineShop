<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
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
						<h3>Login Page</h3>
						<div style="color: #FF0000;">${ loginErrorMessage }</div>
						<div class="form-group">
							<input class="input" type="text" name="<%= application.getInitParameter("email") %>" 
								placeholder="Email">
						</div>
						<div class="form-group">
							<input class="input" type="password" name="<%= application.getInitParameter("password") %>" 
								placeholder="Password">
						</div>
						<input type="submit" class="primary-btn" value="Login"/>
						<a type="button" class="main-btn" href="${pageContext.request.contextPath}/register.jsp">Register</a>		
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
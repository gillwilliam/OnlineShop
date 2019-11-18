<%@ page import="entities.Product"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="utils.Price"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<jsp:include page="../Header.jsp" />
<body>

	<%
		Product product = new Product("Mokasyny", null, null, "description lorem ipsum et ...", 69, null);
	%>

	<!-- /HEADER -->
	<jsp:include page="../Header.jsp" />
	<!-- div containing content of the page -->
	<section id="content">

		<div class=container>
			<div class="row">
				<div class="col-md-6">
					<div class="text-center">
						<h3><%=product.getName()%></h1>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6 text-center">
					<img id="product_image"
						src="${pageContext.request.contextPath}/getImage.main?id=<%=product.getImage().getId()%>"
						alt="product photo" />
				</div>
				<div class="col-md-6">
					<p><%=product.getDescription()%></p>
					<div class="row">
						<div class="col-md-4">
							<b><%=product.getPrice()%></b>
						</div>
						<div class="col">
							<p><%=product.getQuantity() + " remaining"%></p>
						</div>
					</div>
					<button class="primary-btn">Add to Cart</button>
				</div>
			</div>

		</div>

	</section>
	<!-- section content -->
</body>
</html>


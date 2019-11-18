<%@ page import="entities.Product"%>
<%@ page import="manager.ProductManager"%>
<%@ page import="java.util.List"%>
<%@ page import="utils.Price"%>
<%@ page import="entities.User"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>

<title>Catalogue maintenance</title>

<!-- my custom css -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/catalogue_maintenance.css" />

</head>
<body>

	<%
		ProductManager pm = new ProductManager();
		List<Product> products = pm.findAll();
	%>

	<!-- HEADER -->
	<jsp:include page="../Header.jsp" />
	<!-- /HEADER -->

	<!-- div containing content of the page -->
	<section id="content">
		<section id="search_section"></section>
		<!-- section search -->

		<a id="but_add_new_product"
			href="${pageContext.request.contextPath}/product/product_edition.jsp">Add
			new product</a>

		<section id="products_list">
			<table id="products_table">
				<tr id="products_table_header">
					<th>Image</th>
					<th>Name</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Category</th>
					<th>Action</th>
				</tr>
				<%
					for (Product product : products) {
				%>
				<tr>
					<td><img id="product_image" src="<%=product.getImage()%>"
						alt="product photo" /></td>
					<td><%=product.getName()%></td>
					<td><%=product.getPrice()%></td>
					<td><%=product.getQuantity()%></td>
					<td><%=product.getCategory().getName()%></td>
					<td id="td_action"><a id="but_display_product"
						class="but_action" href="<%="#"%>"> <img
							src="${pageContext.request.contextPath}/img/display_product_icon.png"
							alt="disp" />
					</a>

						<form
							action='${pageContext.request.contextPath}/editProduct<%= application.getInitParameter("main_front_controller_request_extension") %>'>
							<input type="hidden"
								name='<%=application.getInitParameter("id")%>'
								value="<%=product.getId()%>" /> <input type="hidden"
								name="pass_only" value="true" /> <input id="but_edit_product"
								class="but_action" type="image"
								src="${pageContext.request.contextPath}/img/edit_icon.png"
								alt="edit" />
						</form> <a id="but_delete_product" class="but_action"> <img
							src="${pageContext.request.contextPath}/img/delete.png" alt="del" />
					</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<div id="but_load_more">Load More</div>
		</section>
		<!-- section products list -->

	</section>
	<!-- section content -->
</body>
</html>

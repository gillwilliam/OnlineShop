<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Product"%>
<%@ page import="entities.Category"%>
<%@ page import="manager.CategoryManager"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.util.List"%>
<%@ page import="utils.Result"%>
<%@ page
	import="request_handlers.CreateProductRequestHandler.ProductValidationResult"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product edition</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/category_tree.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/product_edition.css" />
</head>
<body>

	<%!// function, that creates hierarchy of categories for specific category
	// and shows only top category
	private void createHierarchy(Category category, JspWriter out, int indentation) throws IOException {
		// printing category
		// div that contains category and all it's descendants
		out.println("<div id='" + category.getCategoryId() + "' style='margin-left:" + indentation + "px;display:"
				+ (category.isRoot() ? "block" : "none") + "' " + " class='category'>");

		// category name
		out.println("<span class='category_name' " + "onclick=\"chooseCategory('" + category.getCategoryId() + "','"
				+ category.getName() + "')\"" + ">" + category.getName() + "</span>");

		// icon to show descendants
		out.println("<div class='icon_extend'" + "onclick='showCategories(this,"
				+ category.getSubcategoriesAsJavascriptArray() + ")'" + " style='display:"
				+ (category.isLeaf() ? "none" : "inline-block") + "'></div>");

		// stop condition
		if (!category.isLeaf()) {
			// printing children
			List<Category> children = category.getChildren();

			for (Category child : children)
				createHierarchy(child, out, indentation);
		}

		// close div that contains category and all it's descendants
		out.println("</div>");

	}%>

	<%
		Object productObj = request.getAttribute(application.getInitParameter("product_attr"));
		Product product = null;

		if (productObj instanceof Product)
			product = (Product) productObj;

		// if product was just edited obtain result
		Result result = (Result) request.getAttribute(application.getInitParameter("result"));
		
		CategoryManager cm = new CategoryManager();
	%>

	<jsp:include page="../Header.jsp" />


	<div id="container">
		<!-- contains message about successful edit. Initially it's invisible -->
		<section id="message_box"
			class="<%=result == null ? "invisible" : ""%>"
			style="background-color: <%=result != null && result.success ? "#4ed93f" : "#f8694a"%>">
			<span id="message"> <%=result != null ? result.message : ""%>
			</span>
		</section>

		<!-- product data form ------------------------------------------------------------------------------------------------------- -->
		<form id="product_data_form" method="POST"
			enctype="multipart/form-data"
			action='${pageContext.request.contextPath}<%= product != null ? 
					"/editProduct" + application.getInitParameter("main_front_controller_request_extension")
					:
					"/createProduct" + application.getInitParameter("main_front_controller_request_extension")
					%>'>

			<!-- id -->
			<input type="hidden" name="<%=application.getInitParameter("id")%>"
				value="<%=product == null ? -1 : product.getId()%>" />

			<!-- name -->
			<label for="name">Name</label> <input id="name" type="text"
				name="<%=application.getInitParameter("name")%>"
				value="<%=product == null ? "" : product.getName()%>" required />

			<!-- description -->
			<label for="description">Description</label>
			<textarea id="description"
				name="<%=application.getInitParameter("description")%>"
				maxlength="10000" required><%=product == null ? "" : product.getDescription().trim()%></textarea>

			<!-- price -->
			<label for="price">Price</label> <input id="price" type="number"
				step="0.01" min="0"
				name="<%=application.getInitParameter("price")%>"
				value="<%=product == null ? "" : product.getPrice()%>" required />

			<!-- quantity -->
			<label for="quantity">Quantity</label> <input id="quantity"
				type="number" min="0"
				name="<%=application.getInitParameter("quantity")%>"
				value="<%=product == null ? 1 : product.getQuantity()%>" required />

			<!-- category -->
			<label for="category">Category</label> <input id="category_id"
				type="hidden" name="category_id"
				value="<%=product == null ? cm.getRoots().get(0).getCategoryId() : product.getCategory().getCategoryId()%>" />
			<input id="category" type="text"
				value="<%=product == null ? cm.getRoots().get(0).getName() : product.getCategory().getName()%>"
				disabled readonly required />

			<!-- user must choose category from this tree by clicking it -->
			<div id="category_tree">
				<%
					List<Category> rootCategories = cm.getRoots();

					for (Category rootCategory : rootCategories) {
						createHierarchy(rootCategory, out, 10);
					}
				%>
			</div>

			<!-- image -->
			<label for="image">Image</label> <input id="image" type="file"
				name="<%=application.getInitParameter("image")%>"
				accept="image/png, image/jpeg">

			<!-- submit -->
			<input id="but_save" type="submit" value="save" />

		</form>
		<!-- /product_data_form -->
	</div>



	<script>
		function chooseCategory(categoryId, categoryName) {
			var categoryIdInput = document.getElementById("category_id");
			var categoryNameInput = document.getElementById("category");

			categoryIdInput.value = categoryId;
			categoryNameInput.value = categoryName;
		}

		function showCategories(extendBut, categoriesIds) {
			var i;
			for (i = 0; i < categoriesIds.length; i++) {
				document.getElementById(categoriesIds[i]).style.display = "block";
			}

			extendBut.style.backgroundImage = "url('${pageContext.request.contextPath}/img/icon_extended.png')";
			extendBut.onclick = function() {
				hideCategories(extendBut, categoriesIds)
			}
		}

		function hideCategories(extendBut, categoriesIds) {
			var i;
			for (i = 0; i < categoriesIds.length; i++) {
				document.getElementById(categoriesIds[i]).style.display = "none";
			}

			extendBut.style.backgroundImage = "url('${pageContext.request.contextPath}/img/icon_extend.png')";
			extendBut.onclick = function() {
				showCategories(extendBut, categoriesIds)
			}
		}
	</script>

</body>
</html>
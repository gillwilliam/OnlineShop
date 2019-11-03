<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="beans.general.ProductBean" %>
<%@ page import="categories.Category" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="categories.CategoryTree" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Product edition</title>
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/category_tree.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product_edition.css"/>
</head>
<body>

	<%!
		// function, that creates hierarchy of categories for specific category
		// and shows only top category
		private void createHierarchy(Category category, JspWriter out, int indentation) throws IOException
		{
			// printing category
			// div that contains category and all it's descendants
			out.println("<div id='" + category.getId() + 
				"' style='margin-left:" + indentation + "px;display:" + (category.isRootCategory() ? "block" : "none") + "' " +
				" class='category'>");
			
				// category name
				out.println("<span class='category_name' " +
					"onclick=\"chooseCategory('" + category.getId() + "','" + category.getName() + "')\"" + 
				">" + category.getName() + "</span>");
				
				// icon to show descendants
				out.println("<div class='icon_extend'" +
						"onclick='showCategories(this," + category.getSubcategoriesAsJavascriptArray() + ")'"
						+
						" style='display:" + (
								 category.isLeaf() ?
								 "none"
								 :
								 "inline-block"
								)
						+ "'></div>");
				
				// stop condition
				if (!category.isLeaf())
				{
					// printing children
					ArrayList<Category> children = category.getSubcategories();
					
					for (Category child : children)
						createHierarchy(child, out, indentation);
				}
				
			// close div that contains category and all it's descendants
			out.println("</div>");

		}
	%>

	<%
		Object productObj 	= request.getAttribute(application.getInitParameter("product_attr"));
		ProductBean product = null;
		
		if (productObj instanceof ProductBean)
			product = (ProductBean) productObj;
		
		// obtain current category tree
		CategoryTree tree = (CategoryTree) application.getAttribute("category_tree_attr");
		if (tree == null)
		{
			tree = new CategoryTree();
			tree.loadFromDatabase();
			application.setAttribute("category_tree_attr", tree);
		}
	%>
	
	<jsp:include page="../Header.jsp" />
	
	
	<div id="container">
		<!-- product data form ------------------------------------------------------------------------------------------------------- -->
		<form id="product_data_form">
		
			<!-- name -->
			<label for="name">Name</label>
			<input id="name" type="text" name="<%= application.getInitParameter("name") %>" 
				pattern="<%= ProductBean.NAME_REGEX %>" required />
			
			<!-- description -->
			<label for="description">Description</label>
			<textarea id="description" name="<%= application.getInitParameter("description") %>" maxlength="10000" required ></textarea>
			
			<!-- price -->
			<label for="price">Price</label>
			<input id="price" type="number" step="0.01" name="<%= application.getInitParameter("price") %>" required />
			
			<!-- quantity -->
			<label for="quantity">Quantity</label>
			<input id="quantity" type="number" name="<%= application.getInitParameter("quantity") %>" value="1" required />
			
			<!-- category -->
			<label for="category">Category</label>
			<input id="category_id" type="hidden" value="<%= tree.getRootCategories().get(0).getId() %>"/>
			<input id="category" type="text" value="<%= tree.getRootCategories().get(0).getName() %>" disabled readonly required/>
			
			<!-- user must choose category from this tree by clicking it -->
			<div id="category_tree">
				<%
					ArrayList<Category> rootCategories = tree.getRootCategories();
					
					for (Category rootCategory : rootCategories)
					{
						createHierarchy(rootCategory, out, 10);
					}
				%>
			</div>
			
			<!-- image -->
			<label for="image">Image</label>
			<input id="image" type="file" name="avatar" accept="image/png, image/jpeg">
			
			<!-- submit -->
			<input id="but_save" type="submit" value="save" />
			
		</form> <!-- /product_data_form -->
	</div>
	
	
	
	<script>
	
		function chooseCategory(categoryId, categoryName)
		{
			var categoryIdInput 	= document.getElementById("category_id");
			var categoryNameInput 	= document.getElementById("category");
			
			categoryIdInput.value 	= categoryId;
			categoryNameInput.value = categoryName;
		}
	
		
		
		function showCategories(extendBut, categoriesIds)
		{
			var i;
			for (i = 0; i < categoriesIds.length; i++)
			{
				document.getElementById(categoriesIds[i]).style.display = "block";
			}
			
			extendBut.style.backgroundImage = "url('${pageContext.request.contextPath}/img/icon_extended.png')";
			extendBut.onclick = function() {hideCategories(extendBut, categoriesIds)}
		}
		
		
		
		function hideCategories(extendBut, categoriesIds)
		{
			var i;
			for (i = 0; i < categoriesIds.length; i++)
			{
				document.getElementById(categoriesIds[i]).style.display = "none";
			}
			
			extendBut.style.backgroundImage = "url('${pageContext.request.contextPath}/img/icon_extend.png')";
			extendBut.onclick = function() {showCategories(extendBut, categoriesIds)}
		}
	
	</script>
	
</body>
</html>
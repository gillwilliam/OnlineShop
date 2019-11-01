<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="categories.CategoryTree" %>
<%@ page import="categories.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.servlet.jsp.JspWriter" %>
<%@ page import="java.io.IOException" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Category tree</title>
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/category_tree.css"/>
</head>
<body>
	
	<%
		// obtain category tree
		CategoryTree tree =  (CategoryTree) application.getAttribute("category_tree_attr");
		if (tree == null)
		{
			tree = new CategoryTree();
			tree.loadFromDatabase();
			application.setAttribute("category_tree_attr", tree);
		}
	%>
	
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
					"onclick=\"editCategory('" + category.getId() + "','" + category.getName() + "')\"" + 
				">" + category.getName() + "</span>");
				
				// icon to show descendants
				out.println("<div class='icon_extend'" +
						"onclick=" + (
								category.isLeaf() ? 
								 "showProducts(" + category.getId() + ")" 
								 :
								 "showCategories(this," + category.getSubcategoriesAsJavascriptArray() + ")"
								 ) + 
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
					createHierarchy(child, out, indentation + 10);
				}
				
			// close div that contains category and all it's descendants
			out.println("</div>");

		}
	%>
	
	<%
		ArrayList<Category> rootCategories = tree.getRootCategories();
	
		for (Category rootCategory : rootCategories)
		{
			createHierarchy(rootCategory, out, 0);
		}
	%>
	
	
	<div id="category_edit_dialog">
	
		<img id="close_dialog" onclick="closeDialog()" src="${pageContext.request.contextPath}/img/close_icon.png"/>
		
		<form id="form_rename" 
			action="${pageContext.request.contextPath}
			/renameCategory<%= application.getInitParameter("main_front_controller_request_extension") %>"
			method="post">
			
			<input id="input_id_of_category_to_rename" type="text" name="<%= application.getInitParameter("id") %>" 
				style="display:none"/>
			<input id="input_category_current_name" type="text" name="<%= application.getInitParameter("name") %>" 
				placeholder="new category name"/>
			<input type="submit" value="rename"/>
		</form>
		
		<form id="form_add_subcategory"
			action="${pageContext.request.contextPath}
			/addCategory<%= application.getInitParameter("main_front_controller_request_extension") %>"
			method="post">
			
			<!-- id of parent category -->
			<input id="input_id_of_parent_category" type="text" name="id" style="display:none"/>
			<input type="text" name="<%= application.getInitParameter("name") %>" placeholder="subcategory name"/>
			<input type="submit" value="add subcategory"/>
		</form>
		
		<form id="form_delete"
			action="${pageContext.request.contextPath}
			/deleteCategory<%= application.getInitParameter("main_front_controller_request_extension") %>"
			method="post">
			
			<input id="input_id_of_category_to_delete" type="text" name="id" style="display:none"/>
			<input type="submit" value="delete"/>
		</form>

	</div>
	
	
	<script>
	
		function showProducts(categoryId)
		{
			
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
		
		
		
		function editCategory(categoryId, categoryName)
		{
			var dialog = document.getElementById("category_edit_dialog");
			dialog.style.display = "block";
			
			prepareRenameCategory(categoryId, categoryName);
			prepareDeleteCategory(categoryId);
			prepareCreateSubcategory(categoryId);
		}
		
		
		
		function prepareRenameCategory(categoryId, categoryName)
		{
			var inputIdOfCategoryToRename 	= document.getElementById("input_id_of_category_to_rename");
			inputIdOfCategoryToRename.value = categoryId;
			
			var inputCurrentNameOfCategory 		= document.getElementById("input_category_current_name");
			inputCurrentNameOfCategory.value 	= categoryName;
		}
		
		
		
		function prepareDeleteCategory(categoryId)
		{
			var inputIdOfCategoryToDelete 	= document.getElementById("input_id_of_category_to_delete");
			inputIdOfCategoryToDelete.value = categoryId;
		}
		
		
		
		function prepareCreateSubcategory(categoryId)
		{
			var inputIdOfParentCategory 	= document.getElementById("input_id_of_parent_category");
			inputIdOfParentCategory.value 	= categoryId;
		}
		
		
		
		function closeDialog()
		{
			var dialog = document.getElementById("category_edit_dialog");
			dialog.style.display = "none";
		}
		
	</script>
</body>
</html>
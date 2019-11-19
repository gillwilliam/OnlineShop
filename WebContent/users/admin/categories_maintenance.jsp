<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Category"%>
<%@ page import="manager.CategoryManager"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.servlet.jsp.JspWriter"%>
<%@ page import="java.io.IOException"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Categories maintenance</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/category_tree.css" />
</head>
<body>

	<%
		String message = (String) request.getAttribute(application.getInitParameter("category_maintenance_result_param"));
		boolean success = Boolean.parseBoolean((String) request.getAttribute(application.getInitParameter("category_maintenance_result")));
	%>

	<!-- header -->
	<jsp:include page="../../Header.jsp" />
	<!-- /header -->

	<%!
		// function, that creates hierarchy of categories for specific category
		// and shows only top category
		private void createHierarchy(Category category, JspWriter out, int indentation) throws IOException {
		// printing category
		// div that contains category and all it's descendants
		out.println("<div id='" + category.getCategoryId() + "' style='margin-left:" + indentation + "px;display:"
				+ (category.isRoot() ? "block" : "none") + "' " + " class='category'>");

		// category name
		out.println("<span class='category_name' " + "onclick=\"editCategory('" + category.getCategoryId() + "','"
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


	<!-- site content ------------------------------------------------------------------------------------------------------- -->
	<div id="content">

		<!-- contains message about successful edit. Initially it's invisible -->
		<section id="message_box"
			class="<%= message == null ? "invisible" : "" %>"
			style="background-color: <%= success ? "#4ed93f" : "#f8694a" %>">
			<span id="message"> <%= message %>
			</span>
		</section>

		<div id="add_root_category_but" onclick="showRootCategoryDialog()">
			<span>add root category</span>
		</div>

		<section id="category_tree">
			<%
	    		CategoryManager cm = new CategoryManager();
				List<Category> rootCategories = cm.getRoots();
		
				for (Category rootCategory : rootCategories)
				{
					createHierarchy(rootCategory, out, 10);
				}
			%>
		</section>

		<div id="category_edit_dialog">

			<img id="close_dialog" onclick="closeDialog()"
				src="${pageContext.request.contextPath}/img/close_icon.png" />

			<form id="form_rename"
				action="${pageContext.request.contextPath}
				/renameCategory<%= application.getInitParameter("main_front_controller_request_extension") %>"
				method="post">

				<input id="input_id_of_category_to_rename" type="text"
					name="<%= application.getInitParameter("id") %>"
					style="display: none" /> <input id="input_category_current_name"
					type="text" name="<%= application.getInitParameter("name") %>"
					placeholder="new category name" /> <input type="submit"
					value="rename" />
			</form>

			<form id="form_add_subcategory"
				action="${pageContext.request.contextPath}
				/addCategory<%= application.getInitParameter("main_front_controller_request_extension") %>"
				method="post">

				<!-- id of parent category -->
				<input id="input_id_of_parent_category" type="text"
					name="<%= application.getInitParameter("id") %>"
					style="display: none" /> <input type="text"
					name="<%= application.getInitParameter("name") %>"
					placeholder="subcategory name" /> <input type="submit"
					value="add subcategory" />
			</form>

			<form id="form_delete"
				action="${pageContext.request.contextPath}
				/deleteCategory<%= application.getInitParameter("main_front_controller_request_extension") %>"
				method="post">

				<input id="input_id_of_category_to_delete" type="text"
					name="<%= application.getInitParameter("id") %>"
					style="display: none" /> <input type="submit" value="delete" />
			</form>

		</div>
		<!-- category edit dialog -->

		<div id="root_category_creation_dialog">

			<img id="close_dialog" onclick="closeDialog()"
				src="${pageContext.request.contextPath}/img/close_icon.png" />

			<form id="form_add_root_category"
				action="${pageContext.request.contextPath}
				/addCategory<%= application.getInitParameter("main_front_controller_request_extension") %>"
				method="post">

				<!-- id of parent category -->
				<input id="input_id_of_parent_category" type="text"
					name="<%= application.getInitParameter("id") %>"
					style="display: none" value="-100" /> <input type="text"
					name="<%= application.getInitParameter("name") %>"
					placeholder="subcategory name" /> <input type="submit"
					value="add root category" />
			</form>
		</div>

	</div>
	<!-- content -->


	<script>
		
		
		
		function showCategories(extendBut, categoriesIds)
		{
			for (var i = 0; i < categoriesIds.length; i++)
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
			var dialogCategoryEdit = document.getElementById("category_edit_dialog");
			var dialogRootCategoryCreation = document.getElementById("root_category_creation_dialog");
			dialogCategoryEdit.style.display = "none";
			dialogRootCategoryCreation.style.display = "none";
		}
		
		
		
		function showRootCategoryDialog()
		{
			var dialog = document.getElementById("root_category_creation_dialog");
			dialog.style.display = "inline-block";
		}
		
	</script>

</body>
</html>
<%@ page import="entities.Category"%>
<%@ page import="manager.CategoryManager"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.IOException"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/category_tree.css" />
<div id="navigation">
	<!-- container -->
	<div class="container">
		<div id="responsive-nav">
			<!-- category nav -->
			<%!private void createHierarchy(Category category, JspWriter out, int indentation, String path) throws IOException {
		// printing category
		// div that contains category and all it's descendants
		out.println("<div id='" + category.getCategoryId() + "' style='margin-left:" + indentation + "px;display:"
				+ (category.isRoot() ? "block" : "none") + "' " + " class='category'>");

		// category name
		out.println("<a class='category_name' " + "href=\"" + path + "/main.jsp?category="
				+ category.getCategoryId() + "\">" + category.getName() + "</a>");

		// icon to show descendants
		out.println("<div class='icon_extend'" + "onclick='navShowCategories(this,"
				+ category.getSubcategoriesAsJavascriptArray() + ")'" + " style='display:"
				+ (category.isLeaf() ? "none" : "inline-block") + "'></div>");

		// stop condition
		if (!category.isLeaf()) {
			// printing children
			List<Category> children = category.getChildren();

			for (Category child : children) {
				createHierarchy(child, out, indentation, path);
			}
		}
		out.println("</div>");
	}
	// close div that contains category and all it's descendan%>
			<div class="category-nav show-on-click">
				<span class="category-header">Categories <i
					class="fa fa-list"></i></span>
				<ul class="category-list">
					<%
						CategoryManager pm = new CategoryManager();
						List<Category> roots = pm.findAllRoots();
						for (Category root : roots) {
							createHierarchy(root, out, 10, request.getContextPath());
						}
					%>

				</ul>
			</div>
			<!-- /category nav -->
		</div>
	</div>

	<script>
		function navShowCategories(extendBut, categoriesIds) {
			for (var i = 0; i < categoriesIds.length; i++) {
				document.getElementById(categoriesIds[i]).style.display = "block";
			}

			extendBut.style.backgroundImage = "url('${pageContext.request.contextPath}/img/icon_extended.png')";
			extendBut.onclick = function() {
				hideCategories(extendBut, categoriesIds)
			}
		}

		function navHideCategories(extendBut, categoriesIds) {
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
	<!-- /container -->
</div>
<!-- /NAVIGATION -->
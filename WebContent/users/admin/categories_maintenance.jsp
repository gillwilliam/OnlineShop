<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="categories.CategoryTree" %>
<%@ page import="categories.Category" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
	// obtain current category tree
	CategoryTree tree = new CategoryTree();
	tree.loadFromDatabase();
%>

	<!-- header -->
	<jsp:include page="../../Header.jsp"/>
	<!-- /header -->
	
	<!-- site content -->
	<div id="content">

	</div>

</body>
</html>
<%@ page import="beans.general.ProductBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="payments.Price" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <title>Product Details</title>

    <!-- header -->
    <!-- Google font -->
    <link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet">

    <!-- Bootstrap -->
<%--     <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
 --%>
 	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Slick -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/slick.css" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/slick-theme.css" />

    <!-- nouislider -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/nouislider.min.css" />

    <!-- Font Awesome Icon -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">

    <!-- Custom stlylesheet -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- my custom css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product_details.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/catalogue_maintenance.css"/>
    

</head>
<body>

    <%
        ArrayList<ProductBean> products = new ArrayList<ProductBean>();


        Price price1 = new Price(10, 50, "EUR");
        Price price2 = new Price(30, 12, "EUR");
        Price price3 = new Price(11, 34, "EUR");
        Price price4 = new Price(130, 0, "EUR");

        ProductBean product = new ProductBean("Mokasyny", "shoes", price4,
                "description lorem ipsum et ...", 69, application.getRealPath("/") + "img/product04.jpg",
                "", "");
        
       
    %>

    <!-- /HEADER -->

    <!-- div containing content of the page -->
    <section id="content">
        
        <div class=container>
        		<div class="row">
	        		<div class="col-md-6">
		        		<div class="text-center"><h3><%=product.getName()%></h1></div>
		        	</div>
		        </div>
		        <div class="row">
		        	<div class="col-md-6 text-center">
		        		<img id="product_image" src="<%= product.getImagePath()%>" alt="product photo"/>
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
       
    </section> <!-- section content -->
</body>
</html>


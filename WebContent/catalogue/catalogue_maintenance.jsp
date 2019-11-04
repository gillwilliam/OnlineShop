<%@ page import="beans.general.ProductBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="payments.Price" %>
<%@ page import="beans.session.UserBean" %>
<%@ page import="beans.session.AdminBean" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <title>Catalogue maintenance</title>

    <!-- my custom css -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/catalogue_maintenance.css"/>

</head>
<body>

    <%
        ArrayList<ProductBean> products = new ArrayList<ProductBean>();

//        Object productsObj = (Object) request.getAttribute(application.getInitParameter("products_list_attr"));
//
//        if (productsObj instanceof ArrayList)
//        {
//            ArrayList list = (ArrayList) productsObj;
//            if (!list.isEmpty() && list.get(0) instanceof ProductBean)
//            {
//                products = (ArrayList) list;
//            }
//        }


        Price price1 = new Price(10, 50, "EUR");
        Price price2 = new Price(30, 12, "EUR");
        Price price3 = new Price(11, 34, "EUR");
        Price price4 = new Price(130, 0, "EUR");

        ProductBean prod1 = new ProductBean("Mokasyny", "shoes", price4,
                "description lorem ipsum et ...", 69, application.getRealPath("/") + "img/product04.jpg",
                "", "");
        ProductBean prod2 = new ProductBean("Kalesony", "underwear", price1, "description lorem ipsum et ...", 10,
                "${pageContext.request.contextPath}/img/product05.jpg",
                "", "");
        ProductBean prod3 = new ProductBean("Kuboty", "shoes", price2, "description lorem ipsum et ...", 11,
                "${pageContext.request.contextPath}/img/product06.jpg",
                "", "");
        ProductBean prod4 = new ProductBean("Wyborowa", "alcohol", price3, "description lorem ipsum et ...", 1000,
                "${pageContext.request.contextPath}/img/product07.jpg",
                "", "");

        products.add(prod1);
        products.add(prod2);
        products.add(prod3);
        products.add(prod4);
    %>

    <!-- HEADER -->
	<jsp:include page="../Header.jsp" />
    <!-- /HEADER -->

    <!-- div containing content of the page -->
    <section id="content">
        <section id="search_section">

        </section> <!-- section search -->
        
        <a id="but_add_new_product">Add new product</a>

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
                    for (ProductBean product : products)
                    {
                        %>
                            <tr>
                                <td><img id="product_image" src="<%= product.getImagePath()%>" alt="product photo"/></td>
                                <td><%= product.getName() %></td>
                                <td><%= product.getPrice() %></td>
                                <td><%= product.getQuantity() %></td>
                                <td><%= product.getCategory() %></td>
                                <td id="td_action">
                                    <a id="but_display_product" class="but_action"
                                       href="<%= product.getDisplayPagePath() %>">
                                        <img src="${pageContext.request.contextPath}/img/display_product_icon.png" alt="disp"/>
                                    </a>

									<form action='${pageContext.request.contextPath}/editProduct<%= application.getInitParameter("main_front_controller_request_extension") %>'>
										<input type="hidden" name='<%= application.getInitParameter("id") %>'
											value="<%= product.getId() %>" />
										<input type="hidden" name="pass_only" value="true" />
										
										<input id="but_edit_product" class="but_action"
											type="image" src="${pageContext.request.contextPath}/img/edit_icon.png" alt="edit" />
									</form>

                                    <a id="but_delete_product" class="but_action">
                                        <img src="${pageContext.request.contextPath}/img/delete.png" alt="del"/>
                                    </a>
                                </td>
                            </tr>
                        <%
                    }
                %>
            </table>

            <div id="but_load_more">Load More</div>
        </section> <!-- section products list -->

    </section> <!-- section content -->
</body>
</html>

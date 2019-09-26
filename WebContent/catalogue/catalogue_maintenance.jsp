<%@ page import="beans.general.ProductBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="payments.Price" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <title>Catalogue maintenance</title>

    <!-- header -->
    <!-- Google font -->
    <link href="https://fonts.googleapis.com/css?family=Hind:400,700" rel="stylesheet">

    <!-- Bootstrap -->
    <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />

    <!-- Slick -->
    <link type="text/css" rel="stylesheet" href="css/slick.css" />
    <link type="text/css" rel="stylesheet" href="css/slick-theme.css" />

    <!-- nouislider -->
    <link type="text/css" rel="stylesheet" href="css/nouislider.min.css" />

    <!-- Font Awesome Icon -->
    <link rel="stylesheet" href="css/font-awesome.min.css">

    <!-- Custom stlylesheet -->
    <link type="text/css" rel="stylesheet" href="css/style.css" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- my custom css -->
    <link rel="stylesheet" type="text/css" href="css/catalogue_maintenance.css"/>

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
                "description lorem ipsum et ...", 69, "img/product04.jpg",
                "", "");
        ProductBean prod2 = new ProductBean("Kalesony", "underwear", price1, "description lorem ipsum et ...", 10,
                "img/product05.jpg",
                "", "");
        ProductBean prod3 = new ProductBean("Kuboty", "shoes", price2, "description lorem ipsum et ...", 11,
                "img/product06.jpg",
                "", "");
        ProductBean prod4 = new ProductBean("Wyborowa", "alcohol", price3, "description lorem ipsum et ...", 1000,
                "img/product07.jpg",
                "", "");

        products.add(prod1);
        products.add(prod2);
        products.add(prod3);
        products.add(prod4);
    %>

    <!-- HEADER -->
    <header>
        <!-- top Header -->
        <div id="top-header">
            <div class="container">
                <div class="pull-right">
                    <ul class="header-top-links">
                        <li class="dropdown default-dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">ENG <i class="fa fa-caret-down"></i></a>
                            <ul class="custom-menu">
                                <li><a href="#">English (ENG)</a></li>
                                <li><a href="#">Spanish (Es)</a></li>
                            </ul>
                        </li>
                        <li class="dropdown default-dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">USD <i class="fa fa-caret-down"></i></a>
                            <ul class="custom-menu">
                                <li><a href="#">USD ($)</a></li>
                                <li><a href="#">EUR (€)</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- /top Header -->

        <!-- header -->
        <div id="header">
            <div class="container">
                <div class="pull-left">
                    <!-- Logo -->
                    <div class="header-logo">
                        <a class="logo" href="#">
                            <img src="./img/logo.png" alt="">
                        </a>
                    </div>
                    <!-- /Logo -->

                    <!-- Search -->
                    <div class="header-search">
                        <form>
                            <input class="input search-input" type="text" placeholder="Enter your keyword">
                            <select class="input search-categories">
                                <option value="0">All Categories</option>
                                <option value="1">Category 01</option>
                                <option value="1">Category 02</option>
                            </select>
                            <button class="search-btn"><i class="fa fa-search"></i></button>
                        </form>
                    </div>
                    <!-- /Search -->
                </div>
                <div class="pull-right">
                    <ul class="header-btns">
                        <!-- catalog maintenance -->
                        <a>
                            <b id="but_catalog_maintenance" class="text-uppercase" href="#">Catalog Maintenance</b>
                        </a>
                        <!-- /catalog maintenance -->

                        <!-- Account -->
                        <a>
                            <b id="but_my_account" class="text-uppercase">My Account</b>
                        </a>
                        <!-- /Account -->

                        <!-- Mobile nav toggle-->
                        <li class="nav-toggle">
                            <button class="nav-toggle-btn main-btn icon-btn"><i class="fa fa-bars"></i></button>
                        </li>
                        <!-- / Mobile nav toggle -->
                    </ul>
                </div>
            </div>
            <!-- header -->
        </div>
        <!-- container -->
    </header>
    <!-- /HEADER -->

    <!-- div containing content of the page -->
    <section id="content">
        <section id="search_section">

        </section> <!-- section search -->

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
                                        <img src="img/display_product_icon.png" alt="disp"/>
                                    </a>

                                    <a id="but_edit_product" class="but_action" href="<%= product.getEditPagePath() %>">
                                        <img src="img/edit_icon.png" alt="edit"/>
                                    </a>

                                    <a id="but_delete_product" class="but_action">
                                        <img src="img/delete.png" alt="del"/>
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

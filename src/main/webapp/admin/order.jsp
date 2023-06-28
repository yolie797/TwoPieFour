
<%@page import="java.text.DecimalFormat"%>
<%@page import="za.co.bakery.serviceImpl.OrderLineItemServiceImpl"%>
<%@page import="za.co.bakery.service.OrderLineItemService"%>
<%@page import="za.co.bakery.db.manager.DBManager"%>
<%@page import="za.co.bakery.serviceImpl.OrderServiceImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="za.co.bakery.model.OrderLineItem"%>
<%@page import="za.co.bakery.model.Product"%>
<%@page import="za.co.bakery.model.Invoice"%>
<%@page import="za.co.bakery.model.Order"%>
<%@page import="za.co.bakery.model.User"%>
<%@page import="java.util.List"%>
<%@page import="za.co.bakery.model.Role"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<Order> order = (List<Order>) request.getAttribute("orders");
    List<OrderLineItem> oli = new ArrayList<>();
    User user = (User) session.getAttribute("theUser");
    Invoice inv = (Invoice) request.getAttribute("invoice");
    List<Product> prod = (List<Product>) request.getAttribute("products");
    DBManager dbManager = (DBManager) request.getAttribute("dbmanager");
    OrderLineItemService orderLineItemService = new OrderLineItemServiceImpl(dbManager);
    DecimalFormat df = new DecimalFormat("#.00");
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../components/header1.jsp"/>
        <title>Order Page</title>
    </head>
    <body>
    <body>
        <div class="banner_bg_main">
            <!-- header top section start -->
            <div class="container">
                <div class="header_section_top">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="custom_menu">
                                <ul>
                                    <li><a href="#">Best Sellers</a></li>
                                    <li><a href="#">New Releases</a></li>
                                    <li><a href="#">Specials</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- header top section start -->
            <!-- logo section start -->
            <div class="logo_section">
                <span class="toggle_icon" onclick="openNav()"><img src="images/toggle-icon-white.png"></span>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="logo"><a href="index.jsp"><img src="images/logo-transparent-white.png" ></a></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- header section start -->
            <div class="header_section">
                <div class="container">
                    <div class="containt_main">
                        <div id="mySidenav" class="sidenav">
                            <a href="#" class="closebtn" onclick="closeNav()">&times;</a>
                            <a href="index.jsp">Home</a>
                            <%if (user != null) {%>
                            <a href="bakery?pro=gau"><i class="fa fa-users"></i> User</a>                           
                            <a href="bakery?pro=gp"><i class="fa fa-pizza"></i>Products</a>
                            <a href="bakery?pro=gc"> Category</a>
                            <a href="bakery?pro=gi"> Ingredients</a>
                            <a href="bakery?pro=gr"> Recipes</a>
                            <a href="bakery?pro=gao"><i class="fa fa-receipt"></i> Orders</a>
                            <a href="bakery?pro=aor"><i class="fa fa-file-text"></i> Reports</a>
                            <a href="bakery?pro=ea">My Profile</a>
                            <a href="bakery?pro=lo">Logout</a>
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <table class="table table-loght">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Username</th>
                        <th scope="col">Order ID</th>
                        <th scope="col">Product Name</th>  
                        <th scope="col">Product Price</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">TotalOrder Price</th>
                        <th scope="col">Order Date</th>
                        <th scope="col">Delivery Status</th>
                        <th scope="col">Payment Status</th>
                        <th scope="col">Action</th>

                    </tr>
                </thead>
                <tbody>

                    <%      if (order != null && !order.isEmpty() && prod != null) {
                            for (Order o : order) {
                                oli = orderLineItemService.getItemsByOrderId(o.getOrderId());
//                                if (o.getInvoiceId() == inv.getInvoiceId()) {
%>
                    <tr>
                        <td><%=o.getInvoiceId()%></td>

                        <td><%=o.getEmail()%></td>
                        <td><%=o.getOrderId()%></td>
                        <%  for (OrderLineItem ol : oli) {
                                if (o.getOrderId() == ol.getOrderId()) {
                                    for (Product p : prod) {
                                        if (p.getProductId() == ol.getProductId()) {%>
                        <td><%=p.getTitle()%></td>
                        <td><%=df.format(p.getPrice())%></td>
                        <td><%=ol.getQuantity()%></td>
                        <td><%=ol.getTotalPrice()%></td>
                        <%break;
                                    }
                                }
                                break;
                            }
                        %>
                        <%}%>
                        <td><%=o.getDate()%></td>
                        <td><%=o.getDeliveryStatus()%></td>
                        <td><%=o.getPaymentStatus()%></td>
                    </tr>
                    <%}%>
                </tbody>
                <%} else {
                        out.println("There are no users");
                    }
                %>


            </table>
        </div>
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.bundle.min.js"></script>
        <script src="js/jquery-3.0.0.min.js"></script>
        <script src="js/plugin.js"></script>
        <!-- sidebar -->
        <script src="js/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="js/custom.js"></script>
        <script>
                                function openNav() {
                                    document.getElementById("mySidenav").style.width = "250px";
                                }

                                function closeNav() {
                                    document.getElementById("mySidenav").style.width = "0";
                                }
        </script>
    </body>
</html>

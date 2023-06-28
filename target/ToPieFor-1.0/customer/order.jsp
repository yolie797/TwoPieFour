<%@page import="za.co.bakery.model.User"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="za.co.bakery.model.Address"%>
<%@page import="za.co.bakery.model.Order"%>
<%@page import="java.util.List"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("theUser");
    List<Order> lstOrder = (List<Order>) request.getAttribute("orders");
    List<Address> lstAddress = (List<Address>) request.getAttribute("addresses");
    DecimalFormat df = new DecimalFormat("#.##");
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
                            <a href="bakery?pro=gco">My Orders</a>
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
                        <th scope="col">Date</th>
                        <th scope="col">Delivery Address</th>
                        <th scope="col">Delivery Status</th>
                        <th scope="col">Payment Status</th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <%if (lstOrder != null) {
                        for (Order order : lstOrder) {%>
                    <tr>
                        <td><%=order.getDate()%></td>
                        <%for(Address address : lstAddress) {
                            if(order.getAddressId() == address.getAddressId()){%>
                                <td><%=address.getAddressLine1() + ", " +address.getAddressLine2()%></td>
                            <%}
                        }%>
                        <td><%=order.getDeliveryStatus()%></td>
                        <td><%=order.getPaymentStatus()%></td>
                        <td><a class=" btn btn-light" href="bakery?pro=gooli&orderId=<%=order.getOrderId()%>">details</a></td>
                        <%if(order.getPaymentStatus().equals("not paid")) {%>
                        <td><a class=" btn btn-light" href="bakery?pro=po&orderId=<%=order.getOrderId()%>">pay</a></td>
                        <%}
                        else if(ChronoUnit.DAYS.between(order.getDate(), LocalDate.now()) < 7 && !order.getDeliveryStatus().equals("delivered")){%>
                            <td><a class=" btn btn-light" href="bakery?pro=zzz&productname=<%=order.getOrderId()%>">cancel</a></td>
                        <%}
                        else {%>
                        <td></td>
                        <%}%>
                    </tr>
                        <%}
                    } 
                    else {%>
                        <tr>
                            <td>no order date</td>
                            <td>n/a</td>
                            <td>n/a</td>
                            <td>n/a</td>
                            <td></td>
                        </tr>
                    <%}%> 
                </tbody>
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

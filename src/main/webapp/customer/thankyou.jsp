<%@page import="za.co.bakery.model.Order"%>
<%@page import="za.co.bakery.model.OrderLineItem"%>
<%@page import="java.util.List"%>
<%@page import="za.co.bakery.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("theUser");
    List<Order> lstOrder = (List<Order>) request.getAttribute("orders");
    List<OrderLineItem> lstOrderLineItem = (List<OrderLineItem>) request.getAttribute("orderlineitems");
    int orderId = -2;
    for (OrderLineItem oli : lstOrderLineItem) {
        orderId = oli.getOrderId();
        break;
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../components/header1.jsp"%>
        <title>Thank You Page</title>
    </head>
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
        <div class="header_section">
            <h1 class="product_taital">Your order was placed successfully</h1>
        </div>
        <div class="logo_section">
            <div class="row">
                <div class="col-sm-12">
                    <div class="logo"><a href="index.jsp"><img src="images/thankyou.jpg" ></a></div>
                </div>
            </div>
        </div>
       
        <div class="fashion_section" style=" display: flex; justify-content: center; align-items: center;">
            <form action="bakery" method="POST"> 

                <div class="overlay" id="overlay">
                    <div class="popup">

                        <p>Thank you for choosing ToPieFor. You can choose "HOME" to pay. Navigate to "My Orders" to see your order.</p>
                        <a style="text-align: left;" class="btn btn-dark btn-lg btn-blockbottom:" href="bakery?pro=gp">HOME</a>

                        <!--<a style="text-align: right;" class="btn btn-dark btn-lg btn-blockbottom:" href="bakery?pro=po&orderId=">PAY</a>-->

                    </div>

                </div>

            </form>
        </div>
       
    </body>
    <jsp:include page="../components/footer1.jsp"/>
</html>

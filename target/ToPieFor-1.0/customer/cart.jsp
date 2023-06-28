<%@page import="za.co.bakery.model.Product"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Set"%>
<%@page import="za.co.bakery.model.CartItem"%>
<%@page import="za.co.bakery.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Set<CartItem> setCart = (Set<CartItem>) session.getAttribute("cart");
    User user = (User) session.getAttribute("theUser");
    DecimalFormat df = new DecimalFormat("#.##");
    double subTotal = 0.00;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <jsp:include page="../components/header1.jsp"/>
        <title>Cart Page</title>
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
                                    <%if (setCart != null && !setCart.isEmpty()) {
                                            for (CartItem ci : setCart) {
                                                subTotal += ci.getTotal();
                                            }%>
                                    <li style="position: absolute; left: 30px">Amount Due: R<%=subTotal%></li>
                                        <%} else {%>
                                    <li style="position: absolute; left: 30px">Amount Due: R<%=subTotal%></li>
                                        <%}%>
                                    <li><a href="bakery?pro=gp">Continue Shopping</a></li>
                                        <%if (subTotal < 1.0) {%>
                                    <li style="color: #FF0000;">Check Out</li>
                                        <%} else {%>
                                    <li><a href="bakery?pro=sa&default=false">Check Out</a></li>
                                        <%}%>
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
                            <a href="../customer/profile.jsp">My Profile</a>
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
                        <th scope="col">Product title</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Product Price</th>
                        <th scope="col">Total</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <%if (setCart != null) {
                            for (CartItem ci : setCart) {
                                Product p = ci.getProduct();%>
                    <tr>
                        <td><%=p.getTitle()%></td>
                        <td>
                            <form action="" method="POST" class="form-inline">
                                <input type="hidden" name="id" value="<%=p.getTitle()%>" class="form-input">
                                <div class="form-group d-flex justify-content-between" >
                                    <a class="btn btn-sml btn-decre" href="bakery?pro=dec&productname=<%=p.getTitle()%>"><i class="fa fa-minus-square"></i></a>
                                    <input type="text" name="quantity" class="form-control" value="<%=ci.getQuantity()%>" readonly>
                                    <a class="btn btn-sml btn-incre" href="bakery?pro=inc&productname=<%=p.getTitle()%>"><i class="fa fa-plus-square"></i></a>
                                </div>
                            </form>
                        </td>
                        <td><%=df.format(p.getPrice())%></td>
                        <td><%=ci.getTotal()%></td>
                        <td><a class=" btn btn-light" href="bakery?pro=ri&productname=<%=p.getTitle()%>">Remove</a></td>
                    </tr>
                    <%}
                                } else {%>
                    <tr>
                        <td>no product</td>
                        <td>0</td>
                        <td>0.00</td>
                        <td>0.00</td>
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

<%@page import="za.co.bakery.model.Address"%>
<%@page import="za.co.bakery.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("theUser");
    int orderId = (int)request.getAttribute("orderId");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../components/header1.jsp"%>
        <title>Payment Page</title>
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
                            <%if(user != null) {%>
                                <a href="bakery?pro=gco">My Orders</a>
                                <a href="">My Profile</a>
                                <a href="bakery?pro=lo">Logout</a>
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="header_section">
            <h1 class="product_taital">Payment</h1>
        </div>
        <div class="fashion_section" style=" display: flex; justify-content: center; align-items: center;">
            <form action="bakery" method="POST">
                <div class="row">
                    <div class="col-sm-12" style="text-align: left;">
                        <label for="cards">Accepted Cards</label>
                        <div class="icon-container">
                            <i class="fa fa-cc-visa" style="color:navy;"></i>
                            <i class="fa fa-cc-amex" style="color:blue;"></i>
                            <i class="fa fa-cc-mastercard" style="color:red;"></i>
                            <i class="fa fa-cc-paypal" style="color:orange;"></i>
                        </div>
                        <br>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12" style="text-align: right;">
                        <label for="cname">Name on Card</label>
                        <input class="form-control" type="text" id="cname" name="cardname" placeholder="John More Doe">
                        <label for="ccnum">Card number</label>
                        <input class="form-control" type="number" id="ccnum" name="cardnumber" placeholder="1111-2222-3333-4444">
                        <label for="expmonth">Exp Month</label>
                        <input class="form-control" type="number" id="expmonth" name="expmonth" placeholder="September">

                        <div class="row">
                            <div class="col-sm-12">
                                <label for="expyear">Exp Year</label>
                                <input class="form-control" style="justify-content: flex-end" type="number" id="expyear" name="expyear" placeholder="2026">
                            </div>
                            <div class="col-sm-12">
                                <label for="cvv">CVV</label>
                                <input class="form-control" style="justify-content: flex-end" type="number" id="cvv" name="cvv" placeholder="352">
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="pt-1 mb-4">
                    <input class="btn btn-dark btn-lg btn-block" style="top: 30px" type="submit" value="Pay">
                </div>
                <input type="hidden" name="pro" value="p">
                <input type="hidden" name="orderId" value="<%=orderId%>">
            </form>
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

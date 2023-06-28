<%@page import="java.text.DecimalFormat"%>
<%@page import="za.co.bakery.model.User"%>
<%@page import="za.co.bakery.model.Category"%>
<%@page import="za.co.bakery.model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<Product> lstProducts = (List<Product>) request.getAttribute("products");
    List<Category> lstCategory = (List<Category>) request.getAttribute("categories");
    User user = (User) session.getAttribute("theUser");
    DecimalFormat df = new DecimalFormat("#.00");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../components/header1.jsp"%>
        <title>Home Page</title>
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
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">All Category 
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <%if (lstCategory != null) {
                                        for (Category category : lstCategory) {%>
                                <a class="dropdown-item" href="bakery?pro=gpc&category=<%=category.getCategoryName()%>"><%=category.getCategoryName()%></a>
                                <%}
                                        }%>
                            </div>
                        </div>
                        <div class="main">
                            <!-- Another variation with a button -->
                            <div class="input-group">
                                <input type="text" class="form-control" id="pro" name="title" placeholder="Search products">
                                <div class="input-group-append">
                                    <button class="btn btn-secondary" type="button" onclick="redirectToURL('bakery?pro=gps')" style="background-color: #f26522; border-color:#f26522 ">
                                        <i class="fa fa-search"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="header_box">
                            <div class="login_menu">
                                <ul>
                                    <li><a href="bakery?pro=sc">
                                            <%if (user != null) {%>
                                            <i class="fa fa-shopping-cart" aria-hidden="true" style="font-size: 30px"></i>
                                            ${cart.size()}<span class="padding_10" style="color: #FFA500;">Cart</span></a>
                                            <%}%>
                                    </li>
                                    <li><i class="fa fa-user-o" aria-hidden="true" style="font-size: 30px"></i>
                                        <%if (user != null) {%>
                                        <span class="padding_10" style="color: #FFA500;"><%=user.getfName()%></span>
                                        <%} else {%>
                                        <a href="jsp/login.jsp">
                                            <span class="padding_10" style="color: #FFA500;">login</span></a>
                                            <%}%>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="header_section">
            <h1 class="product_taital">Our Products</h1>
        </div>
        <%if (lstProducts != null && lstCategory != null) {
                int x = 0;%>
        <div class="banner_section" >
            <div class="container" style="align-items: flex-start; justify-content: center;">
                <div id="main_slider" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner">
                        <%for (Product product : lstProducts) {
                                    if (x < 1) {%>
                        <div class="carousel-item active">
                            <div class="row">
                                <div class="col-sm-4 mx-auto">
                                    <%for (Category category : lstCategory) {
                                                    if (product.getCategoryId() == category.getCategoryId()) {%>
                                    <h1 class="price_text" style="align-items: flex-start; justify-content: center; font-size: 20px;"><%=category.getCategoryName()%></h1>
                                    <%break;
                                                            }
                                                        }%>
                                    <div class="product_taital"><img src="images/<%=product.getPicture()%>"></div>
                                </div>
                            </div>
                        </div>
                        <%x += 1;
                                    } else {%>
                        <div class="carousel-item" >
                            <div class="row">
                                <div class="col-sm-4 mx-auto">
                                    <%for (Category category : lstCategory) {
                                                    if (product.getCategoryId() == category.getCategoryId()) {%>
                                    <h1 class="price_text" style="align-items: flex-start; justify-content: center; font-size: 20px"><%=category.getCategoryName()%></h1>
                                    <%break;
                                                            }
                                                        }%>
                                    <div class="banner_taital"><img src="images/<%=product.getPicture()%>"></div>
                                </div>
                            </div>
                        </div>
                        <%}%>
                        <%}%>
                    </div>

                </div>
            </div>
        </div>
        <%}%>
        <div class="fashion_section">
            <div class="row">
                <%if (lstProducts != null) {
                        for (Product product : lstProducts) {%>
                <div class="col-lg-4 col-sm-4">
                    <div class="box_main">
                        <h4 class="product_text"><%=product.getTitle()%></h4>
                        <p class="price_text">Price  <span style="color: #262626;">R <%=df.format(product.getPrice())%></span></p>
                        <div class="product_img"><img src="images/<%=product.getPicture()%>"></div>
                        <div class="btn_main">
                            <%if (user != null) {%>
                            <div class="buy_bt"><a href="bakery?pro=atc&productname=<%=product.getTitle()%>">Add To Cart</a></div>
                            <%} else {%>

                            <%}%>
                            <div class="seemore_bt"><a href="#" data-toggle="modal" data-target="#exampleModalLong">See More</a></div>
                        </div>
                    </div>
                </div>
                <%}
                        }%>
            </div>
        </div>
        <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        ...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function redirectToURL(url) {
                var inputValue = document.getElementById('pro').value;
                var parameter = 'title=' + encodeURIComponent(inputValue);
                var urlWithParams = url + '&' + parameter;
                window.location.href = urlWithParams;
            }
        </script>
        <jsp:include page="../components/footer1.jsp"/>
    </body>
</html>

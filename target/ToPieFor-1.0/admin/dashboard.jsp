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
            <div class="header_section">
                <div class="container">
                    <div class="containt_main">
                        <div id="mySidenav" class="sidenav">
                            <a href="#" class="closebtn" onclick="closeNav()">&times;</a>
                            <a href="index.jsp">Home</a>
                            <%if (user != null) {%>
                            <a href="bakery?pro=gau"><i class="fa fa-user"></i> User</a>                           
                            <a href="bakery?pro=gp"><i class="fa fa-cutlery"></i> Products</a>
                            <a href="bakery?pro=gc"> Category</a>
                            <a href="bakery?pro=gi"> Ingredients</a>
                            <a href="bakery?pro=gr"> Recipes</a>
                            <a href="bakery?pro=gao"> Orders</a>
                            <a href="bakery?pro=aor"><i class="fa fa-file-text"></i> Reports</a>
                            <a href="bakery?pro=ea">My Profile</a>
                            <a href="bakery?pro=lo">Logout</a>
                            <%}%>
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
                    </div>
                </div>

            </div>


        </div>
        <div class="header_section">
            <h1 class="product_taital">Welcome Back <span> ${theUser.getfName()}</span></h1>
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

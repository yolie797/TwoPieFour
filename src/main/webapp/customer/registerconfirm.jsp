<%@page import="za.co.bakery.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("user");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../components/header1.jsp"%>
        <title>Registration Confirmation Page</title>
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
                                    <li><a href="#"></a></li>
                                    <li><a href="index.jsp">HOME</a></li>
                                    <li><a href="#"></a></li>
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
            <h1 class="product_taital">Registration Confirmation</h1>
        </div>
        <div class="fashion_section" style=" display: flex; justify-content: center; align-items: center;">
            <form action="bakery" method="POST" style="width: 60%;">
                    <div class="form-group">
                        <p>We have sent a confirmation code to the following email. Please paste the confirmation code below to activate your account</p>
                        <label for="addressline1">Email</label>
                        <%if(user != null) {%>
                            <input type="text" class="form-control" id="addressline1" name="addressline1" value="<%=user.getEmail()%>" readonly>
                        <%}%>
                    </div>
                    <div class="form-group">
                        <label for="addressline2">Confirmation Code</label>
                        <input type="text" class="form-control" id="enteredCode" name="enteredCode" placeholder="paste here...">
                    </div>
                <button type="submit" class="btn btn-primary">Activate</button>
                    <input type="hidden" id="pro" name="pro" value="rcp">
            </form>
        </div>
        <jsp:include page="../components/footer1.jsp"/>
    </body>
</html>

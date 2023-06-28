<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../components/header2.jsp"%>
        <title>Login Page</title>
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
                                    <li><a href="../customer/register.jsp">Register New Account</a></li>
                                    <li><a href="../customer/recovery.jsp">Forgot Password</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- logo section start -->
            <div class="logo_section">
                <span class="toggle_icon" onclick="openNav()"><img src="../images/toggle-icon-white.png"></span>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="logo"><a href="../index.jsp"><img src="../images/logo-transparent-white.png" ></a></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="header_section">
                <div class="container">
                    <div class="containt_main">
                        <div id="mySidenav" class="sidenav">
                            <a href="../index.jsp" class="closebtn" onclick="closeNav()">&times;</a>
                            <a href="../index.jsp">Home</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="header_section">
            <h1 class="product_taital">Account</h1>
        </div>
        <div class="fashion_section">
            <div class="container">
                <form method="POST"  action="../bakery">
                    <div class="form-outline mb-4">
                        <label class="form-label" for="username">Email address</label>
                        <input type="text" name="username"  id="username" class="form-control form-control-lg"  required/>
                    </div>
                    <div class="form-outline mb-4">
                        <label class="form-label" for="password">Password</label>
                        <input type="password" name="password" id="form2Example27" class="form-control form-control-lg"  required/>
                    </div>
                    <div class="pt-1 mb-4">
                        <input class="btn btn-dark btn-lg btn-block"  type="submit" value="Login">
                    </div>
                    <input type="hidden" name="pro" value="li">
                </form>
            </div>
        </div>
        <jsp:include page="../components/footer2.jsp"/>
    </body>
</html>

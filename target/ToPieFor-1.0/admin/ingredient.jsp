
<%@page import="za.co.bakery.model.Ingredient"%>
<%@page import="za.co.bakery.model.User"%>
<%@page import="java.util.List"%>
<%@page import="za.co.bakery.model.Role"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<Ingredient> ing = (List<Ingredient>) request.getAttribute("ingredients");
    User user = (User) session.getAttribute("theUser");
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../components/header1.jsp"/>
        <title>Ingredient Page</title>
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
                            <a href="bakery?pro=gau"><i class="fa fa-user"></i> User</a>                           
                            <a href="bakery?pro=gp"><i class="fa fa-pizza"></i>Products</a>
                            <a href="bakery?pro=gc"> Category</a>
                            <a href="bakery?pro=gi"> Ingredients</a>
                            <a href="bakery?pro=gr"> Recipes</a>
                            <a href="bakery?pro=gao"> Orders</a>
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
                        <th class="border-top-0">Ingredient Name</th>
                        <th class="border-top-0">Quantity in Stock</th>

                    </tr>
                </thead>
                <tbody>
                    <%      if (ing != null && !ing.isEmpty()) {
                            for (Ingredient i : ing) {
                    %>
                    <tr>
                        <td><%=i.getName()%></td>
                        <td><%=i.getQuantityInStock()%></td>
                    </tr>
                    <% }
                        } else {
                            out.println("There are no products");
                        }
                    %>
                </tbody>
            </table>
            <a
                href="pages/addIngredient.jsp"
                class="btn btn-primary text-uppercase mb-3">Add new ingredient</a>

        </div>
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

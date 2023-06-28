<%@page import="za.co.bakery.model.Recipe"%>
<%@page import="za.co.bakery.model.Nutrition"%>
<%@page import="za.co.bakery.model.Product"%>
<%@page import="za.co.bakery.model.OrderLineItem"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="za.co.bakery.model.Address"%>
<%@page import="java.util.List"%>
<%@page import="za.co.bakery.model.Order"%>
<%@page import="za.co.bakery.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("theUser");
    List<OrderLineItem> lstOrderLineItem= (List<OrderLineItem>) request.getAttribute("orderlineitems");
    List<Product> lstProduct = (List<Product>) request.getAttribute("products");
    List<Nutrition> lstNutrition = (List<Nutrition>) request.getAttribute("nutritions");
    List<Recipe> lstRecipe = (List<Recipe>) request.getAttribute("recipes");
    Order order = (Order) request.getAttribute("order");
    DecimalFormat df = new DecimalFormat("#.00");
    double subtotal = 0.0;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../components/header1.jsp"/>
        <title>Order Details Page</title>
    </head>
    <body>
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
                        <th scope="col">Product Name</th>
                        <th scope="col">Nutrition</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Total</th>
                    </tr>
                </thead>
                <tbody>
                    <%if (lstOrderLineItem != null && lstProduct != null && lstRecipe != null && lstNutrition != null) {
                        int countChk = 0;%>
                        <tr>
                        <%for (OrderLineItem oli : lstOrderLineItem) {%>
                            <%for(Product product : lstProduct) {
                                if(product.getProductId() == oli.getProductId()) {%>
                                    <td><%=product.getTitle()%></td>
                                    <%for(Recipe recipe : lstRecipe) {
                                        if(recipe.getRecipeId() == product.getProductId()) {%>
                                            <td>
                                            <%for(Nutrition nutrition : lstNutrition) {
                                                if(nutrition.getRecipeId() == recipe.getRecipeId()) {
                                                    countChk += 1;%>
                                                    <%=nutrition.getNutritionName() + ": " + nutrition.getNutritionValue() + ", "%>
                                                <%}
                                            }%>
                                            </td>
                                        <%}
                                    }
                                }
                            }%>
                            <%if(countChk == 0) {%>
                                <td>no nutritional information</td>
                            <% countChk = 0;
                            }subtotal += oli.getTotalPrice();%>
                            <td><%=oli.getQuantity()%></td>
                            <td>R <%=df.format(oli.getTotalPrice())%></td>
                        </tr>
                        <%}%>
                        <tr>
                            <td></td>
                            <td></td>
                            <td>SubTotal:</td>
                            <td>R <%=df.format(subtotal)%></td>
                        </tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <%if(!order.getDeliveryStatus().equals("delivered")) {%>
                            <td><a class=" btn btn-light" href="bakery?pro=po&orderId=<%=order.getOrderId()%>">pay</a></td>
                        <%} 
                        else {%>
                        <td></td>
                        <%}
                    }
                    else {%>
                        <tr>
                            <td>No Product</td>
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

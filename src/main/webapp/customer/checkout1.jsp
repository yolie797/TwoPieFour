<%@page import="za.co.bakery.model.Address"%>
<%@page import="za.co.bakery.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("theUser");
    Address address = (Address)session.getAttribute("deliveryaddress");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../components/header1.jsp"%>
        <title>Checkout Page</title>
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
                                    <%if(address != null) {%>
                                        <li style="color: #FF0000;"><a href="">Use default Delivery Address</a></li>
                                        <li><a href="bakery?pro=sa&default=false">Add New Delivery Address</a></li>
                                    <%}
                                    else{%>
                                        <li><a href="bakery?pro=sa&default=true">Use default Delivery Address</a></li>
                                        <li style="color: #FF0000;"><a href="">Add New Delivery Address</a></li>
                                    <%}%>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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
                                <a href="bakery?pro=ea">My Profile</a>
                                <a href="bakery?pro=lo">Logout</a>
                            <%}%>
                        </div>
                        </div>
                </div>
            </div>
        </div>
        <div class="header_section">
            <h1 class="product_taital">Checkout</h1>
        </div>
        <div class="fashion_section" style=" display: flex; justify-content: center; align-items: center;">
        <form action="bakery" method="POST" style="width: 60%;">
            <%if(address != null) {%>
            <div class="form-group">
                <label for="addressline1">Address Line 1</label>
                <input type="text" class="form-control" id="addressline1" name="addressline1" value="<%=address.getAddressLine1()%>" readonly>
            </div>
            <div class="form-group">
                <label for="addressline2">Address Line 2</label>
                <input type="text" class="form-control" id="addressline2" name="addressline2" value="<%=address.getAddressLine2()%>" readonly>
            </div>
            <div class="form-group">
                <label for="suburb">Suburb</label>
                <input type="text" class="form-control" id="suburb" name="suburb" value="<%=address.getSuburb()%>" readonly>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="city">City</label>
                    <input type="text" class="form-control" id="city" name="city" value="<%=address.getCity()%>" readonly>
                </div>
                <div class="form-group col-md-4">
                    <label for="province">Province</label>
                    <select id="province" name="province" class="form-control" >
                        <option selected><%=address.getProvince()%></option>
                        <option>Eastern Cape</option>
                        <option>Free State</option>
                        <option>Gauteng</option>
                        <option>KwaZulu-Natal</option>
                        <option>Limpopo</option>
                        <option>Mpumalanga</option>
                        <option>North West</option>
                        <option>Northern Cape</option>
                        <option>Western Cape</option>
                    </select>
                </div>
                <div class="form-group col-md-2">
                    <label for="postalcode">Postal Code</label>
                    <input type="text" class="form-control" id="postalcode" name="postalcode" value="<%=address.getPostalCode()%>" readonly>
                </div>
            </div>
            <div class="pt-1 mb-4">
                    <input class="btn btn-dark btn-lg btn-block" type="submit" value="Place Order">
            </div>
            <input type="hidden" name="pro" value="das">
            <%}
            else if(address == null) {%>
            <div class="form-group">
                <label for="addressline1">Address Line 1</label>
                <input type="text" class="form-control" id="addressline1" name="addressline1" required>
            </div>
            <div class="form-group">
                <label for="addressline2">Address Line 2</label>
                <input type="text" class="form-control" id="addressline2" name="addressline2" required>
            </div>
            <div class="form-group">
                <label for="suburb">Surburb</label>
                <input type="text" class="form-control" id="suburb" name="suburb" required>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="city">City</label>
                    <input type="text" class="form-control" id="city" name="city" required>
                </div>
                <div class="form-group col-md-4">
                    <label for="province">Province</label>
                    <select id="province" name="province" class="form-control" required>
                        <option selected>Choose...</option>
                        <option>Eastern Cape</option>
                        <option>Free State</option>
                        <option>Gauteng</option>
                        <option>KwaZulu-Natal</option>
                        <option>Limpopo</option>
                        <option>Mpumalanga</option>
                        <option>North West</option>
                        <option>Northern Cape</option>
                        <option>Western Cape</option>
                    </select>
                </div>
                <div class="form-group col-md-2">
                    <label for="postalcode">Postal Code</label>
                    <input type="text" class="form-control" id="postalcode" name="postalcode" required>
                </div>
            </div>
            <div class="pt-1 mb-4">
                    <input class="btn btn-dark btn-lg btn-block" type="submit" value="Place Order">
            </div>
            <input type="hidden" name="pro" value="aas">
            <%}%>
            </form>
        </div>
        <script>
            function resetAttributeAndRefresh() {
                <% request.setAttribute("deliveryaddress", null); %>
                window.location.reload();
            }
        </script>
        <jsp:include page="../components/footer1.jsp"/>
    </body>
</html>

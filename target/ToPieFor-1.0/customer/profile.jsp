<%@page import="za.co.bakery.model.Address"%>
<%@page import="za.co.bakery.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("theUser");
    Address address = (Address)session.getAttribute("address");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../components/header1.jsp"%>
        <title>Profile Page</title>
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
                                <a href="#">My Profile</a>
                                <a href="bakery?pro=lo">Logout</a>
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="header_section">
            <h1 class="product_taital">Profile</h1>
        </div>
        <div class="fashion_section" style=" display: flex; justify-content: center; align-items: center;">
            <form action="bakery" method="POST" style="width: 60%;">
            <div class="form-row" >
                <div class="form-group col-md-6">
                    <label for="username">Email</label>
                    <input type="email" class="form-control" id="username" name="username" value="<%=user.getEmail()%>" readonly>
                </div>
                <div class="form-group col-md-6">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" value="<%=user.getPassword()%>" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="firstname">First name</label>
                    <input type="text" class="form-control" id="firstname" name="firstname" value="<%=user.getfName()%>" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="lastname">Last Name</label>
                    <input type="text" class="form-control" id="lastname" name="lastname" value="<%=user.getLname()%>" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="title">Title</label>
                    <input type="text" class="form-control" id="title" name="title" value="<%=user.getTitle()%>" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="phonenumber">Mobile Number</label>
                    <input type="text" class="form-control" id="phonenumber" name="phonenumber" value="<%=user.getPhoneNo()%>" required>
                </div>
            </div>
            <%if(address != null) {%>
                <div class="form-group">
                    <label for="addressline1">Address Line 1</label>
                    <input type="text" class="form-control" id="addressline1" name="addressline1" placeholder="1234 Main St" value="<%=address.getAddressLine1()%>">
                </div>
                <div class="form-group">
                    <label for="addressline2">Address Line 2</label>
                    <input type="text" class="form-control" id="addressline2" name="addressline2" placeholder="Apartment, studio, or floor" value="<%=address.getAddressLine2()%>">
                </div>
                <div class="form-group">
                    <label for="suburb">Suburb</label>
                    <input type="text" class="form-control" id="suburb" name="suburb" placeholder="Apartment, studio, or floor" value="<%=address.getSuburb()%>">
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="city">City</label>
                        <input type="text" class="form-control" id="city" name="city" value="<%=address.getCity()%>">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="province">Province</label>
                        <select id="province" name="province" class="form-control">
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
                        <input type="text" class="form-control" id="postalcode" name="postalcode" value="<%=address.getPostalCode()%>">
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="defaultdeliveryaddress" name="defaultdeliveryaddress">
                        <label class="form-check-label" for="defaultdeliveryaddress">
                            Use as default delivery address
                        </label>
                    </div>
                </div>
            <%}
            else{%>
                <div class="form-group">
                    <label for="addressline1">Address Line 1</label>
                    <input type="text" class="form-control" id="addressline1" name="addressline1" placeholder="1234 Main St">
                </div>
                <div class="form-group">
                    <label for="addressline2">Address Line 2</label>
                    <input type="text" class="form-control" id="addressline2" name="addressline2" placeholder="Apartment, studio, or floor">
                </div>
                <div class="form-group">
                    <label for="suburb">Suburb</label>
                    <input type="text" class="form-control" id="suburb" name="suburb" placeholder="Soshanguve">
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="city">City</label>
                        <input type="text" class="form-control" id="city" name="city">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="province">Province</label>
                        <select id="province" name="province" class="form-control">
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
                        <input type="text" class="form-control" id="postalcode" name="postalcode">
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="defaultdeliveryaddress" name="defaultdeliveryaddress">
                        <label class="form-check-label" for="defaultdeliveryaddress">
                            Use as default delivery address
                        </label>
                    </div>
                </div>
            <%}%>
            <button type="submit" class="btn btn-primary">Save</button>
            <input type="hidden" id="pro" name="pro" value="eas">
            </form>
        </div>
        <jsp:include page="../components/footer1.jsp"/>
    </body>
</html>

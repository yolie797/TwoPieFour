
<%@page import="za.co.bakery.model.Product"%>
<%@page import="java.util.List"%>
<%
    List<Product> prod = (List<Product>) request.getAttribute("products");
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <!--        <title>ADD NEW PRODUCT</title>-->

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js" ></script>
    </head>
    <body>

        <%--<jsp:include page="adminHeader.jsp"></jsp:include>--%>
        <div class="content-wrapper">
            <div class="container">
                <div class="row pad-botm">
                    <div class="col-md-12">
                        <h4 class="header-line">Edit Product</h4>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <!--<div class="panel-heading">Add Product</div>-->
                            <div class="panel-body">
                                <form  action="../bakery" method="POST" enctype="multipart/form-data" >
                                    <div class="form-group">
                                        <label>Enter Name</label> 
                                        <input class="form-control" type="text" name="title" id="title" value="${product.getTitle()}" required />
                                    </div>
                                    <div class="form-group">
                                        <label>Price</label>
                                        <input class="form-control" type="number" name="price" id="price" value="${product.getPrice()}" required/>
                                    </div>
                                    <div class="form-group">
                                        <label>Description</label>
                                        <input class="form-control" type="text" style="min-height: 100px;" name="description" value="${product.getDescription()}"/>
                                    </div>
                                    <!--                                        <div class="form-group">
                                                                                <label>MRP Price</label> <input class="form-control" type="number" name="mprice" required/>
                                                                            </div>-->
                                    <div class="form-group">
                                        <label>Warnings</label>
                                        <input class="form-control" type="text" name="warnings" value="${product.getWarnings()}"/>
                                    </div>
                                    <div class="form-group">
                                        <label>Product Category</label>
                                        <select class="form-control" id="${product.getCategoryId()}" value="" required>
                                            <option></option>
                                            <option value="cookies">Cookie</option>
                                            <option value="cupcakes">Cupcake</option>
                                            <option value="cake">Cake</option>
                                            <option value="bread">Bread</option>
                                            <option value="brownies">Brownies</option>
                                            <option value="pastries">Pastries</option>
                                            <option value="pie">Pie</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label>Attach Product Image</label> 
                                        <input type="file" name="picture" id="picture" value="${product.getPicture()}"required/>
                                    </div>

                                    <button type="submit" class="btn btn-success" onclick="return confirm('Are you sure Do you want to save this product?');">Save Product</button>
                                    <button type="reset" class="btn btn-danger">Reset</button>
                                    <input type="hidden" id="pro" name="pro" value="eps">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </body>
</html>
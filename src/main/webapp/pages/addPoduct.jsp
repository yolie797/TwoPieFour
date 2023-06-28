<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <title>ADD NEW PRODUCT</title>

        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <link href="assets/css/style.css" rel="stylesheet" />
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
        <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
        <script src="js/simpleCart.min.js"></script>
        <script type="text/javascript" src="js/bootstrap-3.1.1.min.js"></script>
        <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,900,900italic,700italic' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
        <script src="js/jquery.easing.min.js"></script>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>



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
                        <h4 class="header-line">Add Product</h4>
                    </div>
                </div>
            
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <!--<div class="panel-heading">Add Product</div>-->
                            <div class="panel-body">
                                <form  action="../bakery" method="POST" enctype="multipart/form-data"
                                       >
                                    <div class="form-group">
                                        <label>Enter Name</label> <input class="form-control" type="text" name="title" id="title" equired />
                                    </div>
                                    <div class="form-group">
                                        <label>Price</label> <input class="form-control" type="number" name="price" id="price" required/>
                                    </div>
                                    <div class="form-group">
                                        <label>Description</label> <input class="form-control" type="text" style="min-height: 100px;" name="description" id="description"/>
                                    </div>
                                    <!--                                        <div class="form-group">
                                                                                <label>MRP Price</label> <input class="form-control" type="number" name="mprice" required/>
                                                                            </div>-->
                                    <div class="form-group">
                                        <label>Warnings</label>
                                        <input class="form-control" type="text" name="warnings" id="warnings"/>
                                    </div>
                                    <div class="form-group">
                                        <label>Product Category</label>
                                        <select class="form-control" name="category" id="category" required>
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
                                        <label>Attach Product Image</label> <input type="file" name="picture" id="picture" required/>
                                    </div>

                                    <button type="submit" class="btn btn-success" onclick="return confirm('Are you sure Do you want to add this product?');">Add Product</button>
                                    <button type="reset" class="btn btn-danger">Reset</button>
                                    <input type="hidden" id="pro" name="pro" value="aps">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </body>
</html>
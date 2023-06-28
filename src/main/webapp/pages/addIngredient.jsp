<%-- 
    Document   : addRecipe
    Created on : Jun 2, 2023, 10:54:38 AM
    Author     : Train
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <link href="assets/css/style.css" rel="stylesheet" />
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
        <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" src="css/jquery-2.1.4.min.js"></script>
        <script src="css/simpleCart.min.js"></script>
        <script type="text/javascript" src="css/bootstrap-3.1.1.min.js"></script>
        <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,900,900italic,700italic' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
        <script src="css/jquery.easing.min.js"></script>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>



        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js" ></script>
        <title>INGREDIENT PAGE</title>
    </head>
    <body>
        <div >
            <div class="container">
                <div class="row pad-botm">
                    <!--                    <div class="col-md-12">
                                            <h4 class="header-line" align="center">Add Recipe</h4>
                                        </div>-->
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-info">
                            <!--<div class="panel-heading">Add Product</div>-->
                            <div class="panel-body">
                                <form role="form" action="../bakery?pro=ais" method="post"
                                      enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label>Enter Name</label>
                                        <input class="form-control" type="text" name="ingredientname" id="ingredientname" required />
                                    </div>                             
                                    <div class="form-group">
                                        <label>Minimum Amount</label> 
                                        <input class="form-control" type="text" name="minimumquantity" id="minimumquantity" required/>
                                    </div>
                                    <button type="submit" class="btn btn-success" onclick="return confirm('Are you sure you want to add this ingredient?');">Add Ingredient</button>
                                    <button type="submit" class="btn btn-danger">Save Ingredient</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>

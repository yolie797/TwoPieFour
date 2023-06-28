<%-- 
    Document   : addRecipe
    Created on : Jun 2, 2023, 10:54:38 AM
    Author     : Train
--%>

<%@page import="java.util.Set"%>
<%@page import="za.co.bakery.model.Ingredient"%>
<%@page import="za.co.bakery.model.Recipe"%>
<%@page import="java.util.List"%>
<%@page import="za.co.bakery.model.Unit"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Recipe> recipe = (List<Recipe>) request.getAttribute("recipes");
    List<Ingredient> ingr = (List<Ingredient>) request.getAttribute("ingredients");
    List<Unit> unit = (List<Unit>) request.getAttribute("units");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--        <link href="assets/css/bootstrap.css" rel="stylesheet" />
        <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <link href="assets/css/style.css" rel="stylesheet" />
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
        <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />-->

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>

        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js" ></script>

<!--        <script type="text/javascript" src="css/jquery-2.1.4.min.js"></script>
        <script src="css/simpleCart.min.js"></script>
        <script type="text/javascript" src="css/bootstrap-3.1.1.min.js"></script>
        <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,900,900italic,700italic' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
        <script src="css/jquery.easing.min.js"></script>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>-->
        <title>RECIPE PAGE</title>
    </head>
    <body>
        <div  style="background: url(../images/bg.jpg) 0 0 no-repeat;background-size: cover">
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
                                <form role="form" action="../bakery" method="post"
                                      enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label>Enter Name</label>
                                        <input class="form-control" type="text" name="pname" required />
                                    </div>

                                    <div class="form-group">
                                        <label>Description</label> 
                                        <input class="form-control" type="text" style="min-height: 100px;" name="description" required/>
                                    </div>
                                    <div class="form-group">
                                        <label>Units</label> 
                                        <div class="btn-group">
                                            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                Units
                                            </button>
                                            <div class="dropdown-menu">
                                                <% if (unit != null && !unit.isEmpty()) {
                                                        for (Unit u : unit) {
                                                %>
                                                <a class="dropdown-item"><%=u.getUnitName()%></a>
                                                <% }
                                                    }
                                                %>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="form-group">
                                        <label>Ingredients</label> 
                                        <select size="1.5">
                                            <option>Select an ingredient</option>
                                            <%
                                                if (ingr != null && !ingr.isEmpty()) {
                                                    for (Ingredient i : ingr) {
                                            %>
                                            <option value="<%=i.getName()%>"><%=i.getName()%></option>

                                            <% }
                                                } else {
                                                    out.println("There are no ingredients");
                                                }
                                            %>
                                        </select>

                                    </div>
                                    <div class="form-group">
                                        <label>Amount</label> 
                                        <input class="form-control" type="text" name="description" required/>
                                    </div>
                                    <button type="submit" class="btn btn-success" onclick="return confirm('Are you sure you want to add this recipe?');">Add Recipe</button>
                                    <button type="save" class="btn btn-danger">Save Ingredient</button>
                                    <!--<input type="hidden" id="pro" name="pro" value="arsi">-->
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>

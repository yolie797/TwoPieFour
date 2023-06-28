<%@page import="za.co.bakery.model.Category"%>
<%@page import="za.co.bakery.model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="components/header1.jsp"%>
        <title>Bakery</title>
    </head>

    <body>
        <% response.sendRedirect("bakery?pro=gp");%>
    </body>
</html>

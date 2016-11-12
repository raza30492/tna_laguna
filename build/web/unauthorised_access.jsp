<%-- 
    Document   : anauthorised_access.jsp
    Created on : 16 Jan, 2016, 2:53:34 AM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="include/head.jsp" %>
        <title>Un-authorised access!</title>
    </head>
    <body>
        <div class="jumbotron">
        <h2 style="color:red"> Warning: You do not have privilege to access this page. </h2>
        you must <a href="login.jsp">login </a> to access this page
        </div>
        
    </body>
</html>

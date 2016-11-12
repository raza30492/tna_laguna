<%-- 
    Document   : index
    Created on : 3 Jan, 2016, 8:25:58 AM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>TroubleShoot</title>
        <%@include file="include/head.jsp" %>
        
        <link rel="stylesheet" type="text/css" href="index.css" />
    </head>
    <body>
        <div class="container-fluid">
            <div id="wrapper" class="container"  >
                <%@include file="include/header.jsp" %>
                <%@include file="include/navbar.jsp" %>
            
                <style>
                    #content{
                        width: 100%;
                        margin: 0px;
                        padding: 50px 10px;
                        background-color:aliceblue;
                        border-radius: 30px 30px 0px 0px;
                    }
                </style>
                
                
            <div id="content" >
                
                <p id="message">${requestScope.message}</p>
                
                <a href="debug" role="button" class="btn btn-success" >Send Server log File to Developer</a>      

            </div>
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

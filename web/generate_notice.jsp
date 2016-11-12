<%-- 
    Document   : index
    Created on : 3 Jan, 2016, 8:25:58 AM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.user.role != 'ADMIN'}" >
    <jsp:forward page="login.jsp" />
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <title>Manage Notice</title>
        <%@include file="include/head.jsp" %>
        <link type="text/css" href="css/admin.css" rel="stylesheet" />
        <link rel="stylesheet" type="text/css" href="css/info.css" />
        <link rel="stylesheet" type="text/css" href="css/main.css" />
        <link rel="stylesheet" type="text/css" href="css/widgEditor.css" />
        
        <script type="text/javascript" src="scripts/notice.js" ></script>
        <script type="text/javascript" src="scripts/widgEditor.js"></script>
    </head>
    <body>
        <div class="container-fluid">
        <div id="wrapper" class="container" >
            <%@include file="include/header.jsp" %>
            <%@include file="include/navbar.jsp" %>
            <div class="row">
                <div class="col-md-2">
                    <%@include file="include/sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    
                    <div id="admin-content">
                        <div class="btn-group">
                            <a role="button" class="btn btn-primary" href="view_notice?redirect=one&curr=1">View Notice</a>
                            <a role="button" class="btn btn-primary active" href="generate_notice.jsp">Generate Notice</a>
                            <a role="button" class="btn btn-primary" href="delete_notice?curr=1">Delete Notice</a>
                        </div>
                        
                        <div id="notice" style="width:85%;margin: 20px auto">
                            <h4>Notice Generation Page</h4>
                            <p id="message" >${requestScope.message} </p>
                            <p id="errorBox" style="font-size:18px;"></p>

                            <c:if test="${requestScope.message == null}" >
                                <form name="form" action="generate_notice" method="post" onsubmit="return checkNotice()">
                                    <div style="margin-bottom:20px">
                                        
                                        <input class="form-control align-center" type="text" name="title" value="" placeholder="Title of Notice" />
                                    </div>
                                    <textarea id="noise" name="notice" value="" class="widgEditor nothing"></textarea>
                                    <input type="submit" value="Generate" class="btn btn-block btn-info" style="margin-top: 10px" />
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
            
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>


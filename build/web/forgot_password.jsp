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
        <title>Welcome</title>
        <%@include file="include/head.jsp" %>
        
        <link rel="stylesheet" type="text/css" href="css/tna.css" />
        <script type="text/javascript" src="index.js"></script>
    </head>
    <body>
        <div class="container-fluid">
        <div id="wrapper" class="container" >
            <%@include file="include/header.jsp" %>
            <%@include file="include/navbar.jsp" %>
            
            
            
            <section id="tna-content" >
               
                <p id="message" >${requestScope.message}</p>
                
                <c:if test="${requestScope.message == null && requestScope.reset == null}" >
                        
                    <div style="width:30%;margin: 20px auto">
                        <h4>Forgot Password</h4>
                        <p id="message"> ${requestScope.userNotExist} </p>
                        <p id="errorBox"></p>
                        
                        <form role="form" name="form" action="forgot_password" method="get" onsubmit="return forgotPassword()" >
                            
                            <SELECT name="role" class="form-control">
                                <option value="" selected > Select Role </option>
                                <option value="MERCHANDISER">Merchandiser</option>
                                <option value="PURCHASE">Purchase</option>
                                <option value="FABRIC_SOURCING">Fabric Sourcing</option>
                                <option value="LOGISTICS">Logistics</option>
                                <option value="ADMIN">Admin</option>

                            </SELECT>
                            <input type="text" name="eid" value="" placeholder="Enter Employee ID" class="form-control" />
                            <input type="submit" name="forgotPassword" value="Submit" class="btn btn-block btn-primary" onclick="return check()" />

                            
                        </form>


                    </div>
                   
                </c:if>
                <c:if test="${requestScope.reset != null}" >
                    <div style="width: 30%;margin: 20px auto">
                        <h4>Reset Password</h4>
                        <p id="errorBox"></p>
                        <form role="form" name="resetForm" action="forgot_password" method="post" onsubmit="return resetPassword()">
                            <input type="password" name="newPassword" placeholder="New Password" class="form-control">
                            <input type="password" name="newPassword2" placeholder="Confirm Password" class="form-control" />
                            <input type="hidden" name="eid" value="${requestScope.eid}" />
                            <input type="hidden" name="role" value="${requestScope.role}" />
                            <input type="hidden" name="linkPreserved" value="${requestScope.link}" />
                            <input type="submit" name="changePassword" value="Submit" class="btn btn-block btn-primary" />                      
                        </form>
                        
                    </div>
                    
                    
                </c:if>
                
                
            </section>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        <div class="container-fluid">
    </body>
</html>

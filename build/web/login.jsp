<%-- 
    Document   : admin_login
    Created on : 13 Jan, 2016, 4:41:53 PM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>User Login</title>
        <%@include file="include/head.jsp" %>
        
        <link rel="stylesheet" type="text/css" href="css/login.css" />
        <script type="text/javascript" src="index.js" ></script>
        
    </head>
    <body>
        <div class="container-fluid">
        <div id="wrapper" class="container" >
            <%@include file="include/header.jsp" %>
            <%@include file="include/navbar.jsp" %>
            
            
            <div id="login-content" >
                
                <div id="login-box">
                    
                    <h4>Login Page</h4>
                    
                    <p id="errorBox">    
                    </p>
                    
                    <c:if test="${requestScope.loginMessage != null}" >
                        <p id="message"> ${requestScope.loginMessage} </p>
                    </c:if>
                    <form name="form" action="login" method="post" onsubmit="return Login()">  
                        
                        <table id="login_table">                       
                            <tr>
                                <td width=120><label id="log_label">Employee ID</label></td>
                                <td><input type="text" value="" name="eid" placeholder="Enter Employee ID"></td>
                            </tr>
                            <tr>
                                <td ><label id="log_label">Password</label></td>
                                <td> <input type="password" value="" name="pass" placeholder="Password"></td>
                            </tr>
                            <tr>
                                <td ><label id="log_label">Role</label></td>
                                <td> 
                                    <SELECT name="role" style="width: 250px; padding: 3px; font-size: 16px;margin: 5px 0px;">
                                        <option value="" selected > Select </option>
                                        <option value="MERCHANDISER">Merchandiser</option>
                                        <option value="PURCHASE">Purchase</option>
                                        <option value="FABRIC_SOURCING">Fabric Sourcing</option>
                                        <option value="LOGISTICS">Logistics</option>
                                        <option value="ADMIN">Admin</option>
                                    </SELECT>
                                </td>
                            </tr>

                            <tr>
                                <td colspan=2 style="text-align: center;padding: 20px"> <a href="forgot_password.jsp">Forgot Password?</a></td>
                            </tr>
                            <tr>
                                <td align="center" colspan=2 >
                                    <button  type="submit" class="btn btn-primary" >Login</button>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                
            </div>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

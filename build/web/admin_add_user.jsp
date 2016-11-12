<%-- 
    Document   : index
    Created on : 3 Jan, 2016, 8:25:58 AM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.user.role != 'ADMIN' }" >
    <jsp:forward page="unauthorised_access.jsp" />
</c:if>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Register User</title>
        <%@include file="include/head.jsp" %>
        <link type="text/css" href="css/admin.css" rel="stylesheet" />
        <script type="text/javascript" src="scripts/admin_user.js" ></script>
    </head>
    <body>
        <div class="container-fluid">
        <div id="wrapper" class="container" >
            <%@include file="include/header.jsp" %>
            <%@include file="include/navbar.jsp" %>
            <div class="row" >
                <div class="col-md-2">
                    <%@include file="include/sidebar.jsp" %>
                </div>
                <div class="col-md-10">
                    
                    <div id="admin-content" >
                        <div class="btn-group">
                            <a href="admin_view_user.jsp" class="btn btn-primary " role="button">View User</a>
                            <a href="admin_add_user.jsp" class="btn btn-primary active" role="button">Add User</a>
                            <a href="admin_remove_user.jsp" class="btn btn-primary" role="button">Remove User</a>
                        </div>
                        
                        
                        <div id="reg_user">
		
                            <h2>User Registration Form</h2>
                            <div >
                                <p id="errorBox"></p>
                                <p id="message"> ${requestScope.registerMessage} </p>

                                <form role="form" name="form" method="post" action="register_user" onsubmit="return registerUser()">                        
                                    <input type="text" name="name"  class="form-control" placeholder="Full Name"><br>
                                    <input type="text" name="eid"   class="form-control" placeholder="Employee ID" ><br>
                                    <input type="email" name="email" class="form-control" placeholder="Email ID" ><br>  
                                    <input type="text" name="dob" class="form-control"   placeholder="Date of Birth [DD/MM/YYYY]" ><br>

                                              
                                        <SELECT name="role" class="form-control">
                                            <option value="" selected > Select Role </option>
                                            <option value="MERCHANDISER">Merchandiser</option>
                                            <option value="PURCHASE">Purchase</option>
                                            <option value="FABRIC_SOURCING">Fabric Sourcing</option>
                                            <option value="LOGISTICS">Logistics</option>
                                            <option value="ADMIN">Admin</option>

                                        </SELECT><br>

                                    <input class="btn btn-block btn-info" type="submit" name="register"  value="Register" />

                                </form>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
            
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>


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
        <title>Welcome</title>
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
                            <a href="admin_view_user.jsp" class="btn btn-primary active " role="button">View Users</a>
                            <a href="admin_add_user.jsp" class="btn btn-primary" role="button">Add User</a>
                            <a href="admin_remove_user.jsp" class="btn btn-primary" role="button">Remove User</a>
                        </div>
                        
                        <div style="width: 50%;margin: 20px auto"> <!--  User Display Div-->
                    
                            <c:if test="${requestScope.user != null}"> 
                                <h2> User Details </h2>

                                <table id="display_table">
                                        <tr>
                                            <td >Employee Name</td>
                                            <td>${requestScope.user.employeeName}</td>                           
                                        </tr>
                                        <tr>
                                            <td >Employee Id</td>
                                            <td>${requestScope.user.employeeId}</td>                           
                                        </tr>
                                        <tr>
                                            <td >Email</td>
                                            <td>${requestScope.user.email}</td>                           
                                        </tr>
                                        <tr>
                                            <td >Date Of Birth</td>
                                            <td>${requestScope.user.dob}</td>                           
                                        </tr>
                                        <tr>
                                            <td>Role</td>
                                            <td>${requestScope.user.role}</td>                           
                                        </tr>
                                </table>
                            </c:if>

                            <c:if test="${requestScope.user.role == 'MERCHANDISER'}">

                                <h2>Buyer Access</h2>

                                <p id="message" >${requestScope.userBuyerAddMessage}</p>

                                
                                <c:if test="${requestScope.buyerListSize == 0}" >
                                    <p id="message">No Buyers allocated</p>
                                </c:if>

                                <c:if test="${requestScope.buyerListSize != 0}" >
                                    <table class="table-bordered" id="bordered-table" style="width:100%; margin-bottom: 20px">

                                        <tr>
                                            <th class="align-center">Buyer</th>
                                            <th class="align-center">Action</th>
                                        </tr>
                                        <c:forEach items="${requestScope.buyerList}" var="buyer" >
                                            <tr>
                                                <td>${buyer}</td>
                                                <td>
                                                    <a href="view_user?redirect=three&role=MERCHANDISER&eid=${requestScope.user.employeeId}&removeUserBuyer=${buyer}" role="button" class="btn btn-danger">
                                                        Remove
                                                    </a>
                                                </td>

                                            </tr>
                                        </c:forEach>

                                    </table>
                                </c:if>  
                                <form class="form-inline" role="form" action="view_user" method="get" >
                                    <SELECT name="buyerId" class="form-control">
                                        <option value="" selected > Select Buyer </option>
                                        <c:forEach items="${sessionScope.allBuyerMap}" var="entry" >                                  
                                            <option value="${entry.key}"  > ${entry.value} </option>
                                        </c:forEach>                       
                                    </SELECT>
                                    <input type="hidden" name="redirect" value="three" />
                                    <input type="hidden" name="role" value="MERCHANDISER" />
                                    <input type="hidden" name="eid" value="${requestScope.user.employeeId}" />
                                    <button type="submit" name="addUserBuyer" value="NOT NULL" class="btn btn-default" >Add buyer access </button>

                                </form>

                                
                            </c:if>        

                        </div>
                    </div>
                </div>
            </div>
            
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        <div class="container-fluid">
    </body>
</html>


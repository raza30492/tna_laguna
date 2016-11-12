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
        <title>Remove User</title>
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
                            <a href="admin_add_user.jsp" class="btn btn-primary" role="button">Add User</a>
                            <a href="admin_remove_user.jsp" class="btn btn-primary active" role="button">Remove User</a>
                        </div>
                        
                        <div id="div1">
                            
                            <p id="message">${requestScope.userRemoveMessage}</p>


                            <div>   <!--Search div  --> 

                                <p id="errorBox" ></p>
                                <form class="form-inline" role="form" name="form" action="remove_user" method="get" onsubmit="return selectRole()">
                                    <SELECT name="role" class="form-control">
                                        <option value="" selected > Select Role </option>
                                        <option value="MERCHANDISER">Merchandiser</option>
                                        <option value="PURCHASE">Purchase</option>
                                        <option value="FABRIC_SOURCING">Fabric Sourcing</option>
                                        <option value="LOGISTICS">Logistics</option>
                                        <option value="ADMIN">Admin</option>
                                    </SELECT>
                                    <button type="submit" name="view" value="all" class="btn btn-default">select </button>
                                    <input type="hidden" name="redirect" value="one" />
                                    <input type="hidden" name="curr" value="1" />
                                </form>
                                <c:if test="${requestScope.userList != null}">

                                    <table class="table-bordered" id="bordered-table" style="width: 75%;margin: 20px auto">
                                        <tr>
                                            <th class="align-center">Employee ID</th>
                                            <th class="align-center">Employee Name</th>
                                            <th class="align-center">Action</th>
                                        </tr>

                                        <c:forEach items="${requestScope.userList}" var="user" >
                                            <tr>
                                                <td>${user.key}</td>
                                                <td>${user.value}</td>
                                                <td>
                                                    <a class="btn btn-danger" href="remove_user?redirect=two&eid=${user.key}&role=${requestScope.role}" onclick="return confirm('Are you sure, You want to remove this user?')">remove</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>

                                    <div id="paging" >   <!-- Pagination Div -->

                                        <c:if test="${requestScope.curr == 1}">
                                            <div id="pagination" > <span class="disabled">Previous </span></div>
                                        </c:if>
                                        <c:if test="${requestScope.curr != 1}">
                                            <div id="pagination" > <a href="remove_user?redirect=one&role=${requestScope.role}&curr=${(requestScope.curr - 1)}">Previous </a></div>
                                        </c:if>

                                        <c:forEach var="i" begin="1" end="${requestScope.total}" >
                                            <c:choose >
                                                <c:when test="${(i < requestScope.curr) && (i <= 3 || (requestScope.curr - i) <= 2)}">
                                                    <div id="pagination" ><a href="remove_user?redirect=one&role=${requestScope.role}&curr=${i}">${i}</a></div>
                                                </c:when>
                                                <c:when test="${(i <= requestScope.curr) && ((requestScope.curr - i) > 2) && i == 4}">
                                                    <div id="pagination" ><a >...</a></div>
                                                </c:when>
                                                <c:when test="${(i == requestScope.curr)}">
                                                    <div id="pagination" ><span class="current">${i}</span></div>
                                                </c:when>
                                                <c:when test="${(i <= requestScope.total && i > requestScope.curr) && ((requestScope.total - i) >= 3 && (i - requestScope.curr) == 3)}">
                                                    <div id="pagination" ><a >...</a></div>
                                                </c:when>
                                                <c:when test="${(i <= requestScope.total && i > requestScope.curr) && ((i - requestScope.curr) <= 2 || (requestScope.total - i) < 3)}">
                                                    <div id="pagination" ><a href="remove_user?redirect=one&role=${requestScope.role}&curr=${i}">${i}</a></div>
                                                </c:when>
                                            </c:choose>

                                        </c:forEach>

                                        <c:if test="${(requestScope.curr == requestScope.total)}">
                                            <div id="pagination" > <span class="disabled">Next </span></div>
                                        </c:if>
                                        <c:if test="${(requestScope.curr != requestScope.total)}">
                                            <div id="pagination" > <a href="remove_user?redirect=one&role=${requestScope.role}&curr=${(requestScope.curr + 1)}">Next </a></div>
                                        </c:if>

                                    </div>

                                </c:if>


                            </div>

                        </div>
                        
                        
                    </div>
                </div>
            </div>
            
            
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        <div class="container-fluid">
    </body>
</html>


<%-- 
    Document   : delete_notice
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
        <title>Manage Notice</title>
        <%@include file="include/head.jsp" %>
        <link type="text/css" href="css/admin.css" rel="stylesheet" />
        <script type="text/javascript" src="scripts/admin_profile.js" ></script>
        
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
                            <a role="button" class="btn btn-primary" href="generate_notice.jsp">Generate Notice</a>
                            <a role="button" class="btn btn-primary active" href="delete_notice?curr=1">Delete Notice</a>
                        </div>
                        
                        <div style="width: 80%;margin: 20px auto" >
                            <h4>All Notices</h4>
                            
                            <c:if test="${requestScope.total == 0}" >
                                <p id="message" >No Notice exist in DataBase</p>
                            </c:if>
                            
                            <c:if test="${requestScope.total != 0}" >
                                
                                <p id="message">${requestScope.message}</p>

                                <table class="table-bordered" id="bordered-table" style="width:100%">
                                    <tr>
                                        <th class="align-center" width="60%">notice</th>
                                        <th class="align-center" width="25%">published on</th>
                                        <th class="align-center" width="15%">Action</th>
                                    </tr>

                                    <c:forEach items="${requestScope.noticeList}" var="notice" >
                                        <tr>
                                            <td>${notice.value.key}</td>
                                            <td>${notice.value.value}</td>
                                            <td><a class="btn btn-danger" href="delete_notice?redirect=two&id=${notice.key}&curr=${requestScope.curr}" onclick="return confirm('are you sure, you want to delete this notice?')">Delete</a></td>
                                        </tr>
                                    </c:forEach>

                                </table>

                                <div id="paging" style="margin-top: 10px" >   <!-- Pagination Div -->

                                <c:if test="${requestScope.curr == 1}">
                                    <div id="pagination" > <span class="disabled">Previous </span></div>
                                </c:if>
                                <c:if test="${requestScope.curr != 1}">
                                    <div id="pagination" > <a href="delete_notice?curr=${(requestScope.curr - 1)}">Previous </a></div>
                                </c:if>

                                <c:forEach var="i" begin="1" end="${requestScope.total}" >
                                    <c:choose >
                                        <c:when test="${(i < requestScope.curr) && (i <= 3 || (requestScope.curr - i) <= 2)}">
                                            <div id="pagination" ><a href="delete_notice?curr=${i}">${i}</a></div>
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
                                            <div id="pagination" ><a href="delete_notice?curr=${i}">${i}</a></div>
                                        </c:when>
                                    </c:choose>

                                </c:forEach>

                                <c:if test="${(requestScope.curr == requestScope.total)}">
                                    <div id="pagination" > <span class="disabled">Next </span></div>
                                </c:if>
                                <c:if test="${(requestScope.curr != requestScope.total)}">
                                    <div id="pagination" > <a href="delete_notice?curr=${(requestScope.curr + 1)}">Next </a></div>
                                </c:if>

                            </div>
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


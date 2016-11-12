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
        <title>TNA</title>
        <%@include file="include/head.jsp" %>
        
        <link rel="stylesheet" type="text/css" href="index.css" />
    </head>
    <body>
        <div class="container-fluid">
            <div id="wrapper" class="container"  >
                <%@include file="include/header.jsp" %>
                <%@include file="include/navbar.jsp" %>
            
            <div class="row">
                
                <div class="col-md-12">
                    
                    <div id="index-content" >
                        
                        <h4>All Notices</h4>
                        <c:if test="${requestScope.total == 0}" >
                            <p id="message" >No Notice Found</p>
                        </c:if>

                        <c:if test="${requestScope.total != 0}" >
                            <table class="table-bordered" id="bordered-table" style="width:85%;margin: 20px auto">
                                <tr>
                                    <th class="align-center" width="60%">Notice</th>
                                    <th class="align-center" width="40%">Published on</th>
                                </tr>

                                <c:forEach items="${requestScope.noticeList}" var="notice" >
                                    <tr>
                                        <td><a class="link1" href="view_notice?redirect=two&id=${notice.key}">${notice.value.key}</a></td>
                                        <td>${notice.value.value}</td>
                                    </tr>
                                </c:forEach>

                            </table>

                            <div id="paging" >   <!-- Pagination Div -->

                                <c:if test="${requestScope.curr == 1}">
                                    <div id="pagination" > <span class="disabled">Previous </span></div>
                                </c:if>
                                <c:if test="${requestScope.curr != 1}">
                                    <div id="pagination" > <a href="view_notice?redirect=one&curr=${(requestScope.curr - 1)}">Previous </a></div>
                                </c:if>

                                <c:forEach var="i" begin="1" end="${requestScope.total}" >
                                    <c:choose >
                                        <c:when test="${(i < requestScope.curr) && (i <= 3 || (requestScope.curr - i) <= 2)}">
                                            <div id="pagination" ><a href="view_notice?redirect=one&curr=${i}">${i}</a></div>
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
                                            <div id="pagination" ><a href="view_notice?redirect=one&curr=${i}">${i}</a></div>
                                        </c:when>
                                    </c:choose>

                                </c:forEach>

                                <c:if test="${(requestScope.curr == requestScope.total)}">
                                    <div id="pagination" > <span class="disabled">Next </span></div>
                                </c:if>
                                <c:if test="${(requestScope.curr != requestScope.total)}">
                                    <div id="pagination" > <a href="view_notice?redirect=one&curr=${(requestScope.curr + 1)}">Next </a></div>
                                </c:if>

                            </div>
                        </c:if>

                    </div>
                </div>
                
            </div>
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

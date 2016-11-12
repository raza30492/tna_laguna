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
                <div class="col-md-3">
                    
                    <aside id="index_side_bar">
                        <h4>BUYERS</h4>
                        <table id="buyer-table" class="table">
                            <c:if test="${requestScope.allBuyerList == null}" >
                                <c:forEach items="${applicationScope.allBuyerList}" var="buyer" varStatus="status" >
                                    <tr>
                                        <td><a href="select_buyer?curr=1&curr2=1&more=${requestScope.more}&buyer=${buyer}">${buyer}</a></td>
                                    </tr>
                                </c:forEach>
                                    <tr><td></td></tr>
                            </c:if>
                            <c:if test="${requestScope.allBuyerList != null}" >
                                <c:forEach items="${requestScope.allBuyerList}" var="buyer" varStatus="status" >
                                    <tr>
                                        <td><a href="select_buyer?curr=1&curr2=1&more=${requestScope.more}&buyer=${buyer}">${buyer}</a></td>
                                    </tr>
                                </c:forEach>
                                    <tr><td></td></tr>
                            </c:if>
                        </table>
                        <div id="paging">
                            <c:if test="${requestScope.more != 1}" >
                                <div id="pagination"><a class="buyer" href="index?page=index&curr=0&curr2=0&more=${(requestScope.more-1)}" >Back</a></div>
                            </c:if>
                            <c:if test="${requestScope.more != requestScope.totalBuyer}" >
                                <div id="pagination"><a class="buyer" href="index?page=index&curr=0&curr2=0&more=${(requestScope.more+1)}" >more</a></div>
                            </c:if>
                        </div>
                    </aside>
                    
                </div>
                
                <div class="col-md-9">
                    
                    <div id="index-content">
                            
                        <p id="message" >${requestScope.message}</p>

                        <c:if test="${requestScope.total != 0}">
                           
                            <div>  <!-- Recent TNA -->      
                                <h4 >Recent TNAs of ${requestScope.buyer}</h4>

                                <table class="table-bordered" id="bordered-table" style="width: 80%;margin: 20px auto">
                                    <tr>
                                        <th class="align-center">PO Reference</th>
                                        <th class="align-center">Order Date</th>

                                    </tr>

                                    <c:forEach items="${requestScope.buyerTNAList}" var="tna">
                                        <tr>
                                            <td><a href="view_po?redirect=three&from=buyer&poRef=${tna.key}">${tna.key}</a></td>
                                            <td>${tna.value}</td>
                                        </tr>
                                    </c:forEach>

                                </table>
                            

                                <div id="paging" >   <!-- Pagination Div -->

                                    <c:if test="${requestScope.curr == 1}">
                                        <div id="pagination" > <span class="disabled">Previous </span></div>
                                    </c:if>
                                    <c:if test="${requestScope.curr != 1}">
                                        <div id="pagination" > <a href="select_buyer?curr=${(requestScope.curr - 1)}&curr2=${requestScope.curr2}&more=${requestScope.more}&buyer=${requestScope.buyer}">Previous </a></div>
                                    </c:if>

                                    <c:forEach var="i" begin="1" end="${requestScope.total}" >
                                        <c:choose >
                                            <c:when test="${(i < requestScope.curr) && (i <= 3 || (requestScope.curr - i) <= 2)}">
                                                <div id="pagination" ><a href="select_buyer?curr=${i}&curr2=${requestScope.curr2}&more=${requestScope.more}&buyer=${requestScope.buyer}">${i}</a></div>
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
                                                <div id="pagination" ><a href="select_buyer?curr=${i}&curr2=${requestScope.curr2}&more=${requestScope.more}&buyer=${requestScope.buyer}">${i}</a></div>
                                            </c:when>
                                        </c:choose>

                                    </c:forEach>

                                    <c:if test="${(requestScope.curr == requestScope.total)}">
                                        <div id="pagination" > <span class="disabled">Next </span></div>
                                    </c:if>
                                    <c:if test="${(requestScope.curr != requestScope.total)}">
                                        <div id="pagination" > <a href="select_buyer?curr=${(requestScope.curr + 1)}&curr2=${requestScope.curr2}&more=${requestScope.more}&buyer=${requestScope.buyer}">Next </a></div>
                                    </c:if>

                                </div>
                            </div> 
                                 
                            <div>  <!-- Delayed TNA -->   
                                <h4 style="margin-top: 50px">Delayed TNAs of ${requestScope.buyer}</h4>
                                
                                <c:if test="${requestScope.totalDelayedTNA == 0}" >
                                    <p id="message"> No TNA Delayed</p>
                                </c:if>
                                
                                <c:if test="${requestScope.totalDelayedTNA != 0}" >

                                    <table class="table-bordered" id="bordered-table" style="width: 80%;margin: 20px auto">
                                        <tr>
                                            <th class="align-center">PO Reference</th>
                                            <th class="align-center">Delayed Activity</th>

                                        </tr>

                                        <c:forEach items="${requestScope.delayedTNA}" var="tna">
                                            <tr>
                                                <td><a href="view_po?redirect=three&from=buyer&poRef=${tna.key}">${tna.key}</a></td>
                                                <td>${tna.value}</td>
                                            </tr>
                                        </c:forEach>

                                    </table>
                                    
                                    <div id="paging" >   <!-- Pagination Div -->

                                        <c:if test="${requestScope.curr2 == 1}">
                                            <div id="pagination" > <span class="disabled">Previous </span></div>
                                        </c:if>
                                        <c:if test="${requestScope.curr2 != 1}">
                                            <div id="pagination" > <a href="select_buyer?curr=${requestScope.curr}&curr2=${(requestScope.curr2 - 1)}&more=${requestScope.more}&buyer=${requestScope.buyer}">Previous </a></div>
                                        </c:if>

                                        <c:forEach var="i" begin="1" end="${requestScope.totalDelayedTNA}" >
                                            <c:choose >
                                                <c:when test="${(i < requestScope.curr2) && (i <= 3 || (requestScope.curr2 - i) <= 2)}">
                                                    <div id="pagination" ><a href="select_buyer?curr=${requestScope.curr}&curr2=${i}&more=${requestScope.more}&buyer=${requestScope.buyer}">${i}</a></div>
                                                </c:when>
                                                <c:when test="${(i <= requestScope.curr2) && ((requestScope.curr2 - i) > 2) && i == 4}">
                                                    <div id="pagination" ><a >...</a></div>
                                                </c:when>
                                                <c:when test="${(i == requestScope.curr2)}">
                                                    <div id="pagination" ><span class="current">${i}</span></div>
                                                </c:when>
                                                <c:when test="${(i <= requestScope.totalDelayedTNA && i > requestScope.curr2) && ((requestScope.totalDelayedTNA - i) >= 3 && (i - requestScope.curr2) == 3)}">
                                                    <div id="pagination" ><a >...</a></div>
                                                </c:when>
                                                <c:when test="${(i <= requestScope.totalDelayedTNA && i > requestScope.curr2) && ((i - requestScope.curr2) <= 2 || (requestScope.totalDelayedTNA - i) < 3)}">
                                                    <div id="pagination" ><a href="select_buyer?curr=${requestScope.curr}&curr2=${i}&more=${requestScope.more}&buyer=${requestScope.buyer}">${i}</a></div>
                                                </c:when>
                                            </c:choose>

                                        </c:forEach>

                                        <c:if test="${(requestScope.curr2 == requestScope.totalDelayedTNA)}">
                                            <div id="pagination" > <span class="disabled">Next </span></div>
                                        </c:if>
                                        <c:if test="${(requestScope.curr2 != requestScope.totalDelayedTNA)}">
                                            <div id="pagination" > <a href="select_buyer?curr=${requestScope.curr}&curr2=${(requestScope.curr2 + 1)}&more=${requestScope.more}&buyer=${requestScope.buyer}">Next </a></div>
                                        </c:if>

                                    </div>
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

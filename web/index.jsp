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
        <title>Time and Action Calender</title>
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
                                <c:forEach items="${applicationScope.allBuyerList}" begin="0" end="24" var="buyer" varStatus="status" >
                                    <tr>
                                        <td><a class="link" href="select_buyer?curr=1&curr2=1&more=${requestScope.more}&buyer=${buyer}">${buyer}</a></td>
                                    </tr>
                                </c:forEach>
                                    <tr><td></td></tr>
                            </c:if>
                            <c:if test="${requestScope.allBuyerList != null}" >
                                <c:forEach items="${requestScope.allBuyerList}" begin="0" end="24" var="buyer" varStatus="status" >
                                    <tr>
                                        <td><a class="link" href="select_buyer?curr=1&curr2=1&more=${requestScope.more}&buyer=${buyer}">${buyer}</a></td>
                                    </tr>
                                </c:forEach>
                                    <tr><td></td></tr>
                            </c:if>
                        </table>
                        <div id="paging">
                            <c:if test="${requestScope.more != 1}" >
                                <div id="pagination"><a class="buyer" href="index?page=index&curr2=0&curr=0&more=${(requestScope.more-1)}" >Back</a></div>
                            </c:if>
                            <c:if test="${(requestScope.totalBuyer != 0) && (requestScope.more != requestScope.totalBuyer)}" >
                                <div id="pagination"><a class="buyer" href="index?page=index&curr2=0&curr=0&more=${(requestScope.more+1)}" >more</a></div>
                            </c:if>
                            <c:if test="${requestScope.totalBuyer == 0}" >
                                <p id="message">No Buyers exist in DataBase</p>
                            </c:if>
                        </div>
                    </aside>
                    
                </div>
                
                <div class="col-md-9">
                    
                    <div id="index-content" >
                        <div>
                            <h4>Summary</h4>
                            <table class="table-bordered" id="bordered-table" style="width: 95%;margin: 20px auto">
                                <tr>
                                    <th class="align-center">Number of Buyers</th>
                                    <th class="align-center">Number of TNAs</th>
                                    <th class="align-center">Number of Delayed TNAs</th>
                                    <th class="align-center">Delayed TNA percantage</th>
                                </tr>
                                <tr>
                                    <td>${requestScope.noOfBuyers}</td>
                                    <td>${requestScope.noOfAllTNA}</td>
                                    <td>${requestScope.noOfDelayedTNA}</td>
                                    <td>${requestScope.delayedPercent}</td>
                                </tr>
                            </table>
                        </div>
                        
                        <div> <!--Recent TNA-->
                            
                            <h4>Recent TNAs</h4>
                            <c:if test="${requestScope.total == 0}" >
                                <p id="message" >No TNA exist in DataBase</p>
                            </c:if>
                            <c:if test="${requestScope.total != 0}" >

                                <table  class="table-bordered" id="bordered-table" style="width: 95%;margin: 20px auto">
                                    <tr>
                                        <th style="text-align: center;">PO Reference</th>
                                        <th style="text-align: center;" >Buyer</th>

                                    </tr>
                                    <c:if test="${requestScope.recentTNAList == null}" >
                                        <c:forEach items="${applicationScope.recentTNAList}" var="tna">
                                            <tr>
                                                <td><a href="view_po?redirect=three&from=index&poRef=${tna.key}">${tna.key}</a></td>
                                                <td>${tna.value}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${requestScope.recentTNAList != null}" >
                                        <c:forEach items="${requestScope.recentTNAList}" var="tna">
                                            <tr>
                                                <td><a href="view_po?redirect=three&from=index&poRef=${tna.key}">${tna.key}</a></td>
                                                <td>${tna.value}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>

                                </table>


                                <div id="paging" >   <!-- Pagination Div -->

                                    <c:if test="${requestScope.curr == 1}">
                                        <div id="pagination" > <span class="disabled">Previous </span></div>
                                    </c:if>
                                    <c:if test="${requestScope.curr != 1}">
                                        <div id="pagination" > <a href="index?page=index&curr2=0&curr=${(requestScope.curr - 1)}&more=0">Previous </a></div>
                                    </c:if>

                                    <c:forEach var="i" begin="1" end="${requestScope.total}" >
                                        <c:choose >
                                            <c:when test="${(i < requestScope.curr) && (i <= 3 || (requestScope.curr - i) <= 2)}">
                                                <div id="pagination" ><a href="index?page=index&curr2=0&curr=${i}&more=0">${i}</a></div>
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
                                                <div id="pagination" ><a href="index?page=index&curr2=0&curr=${i}&more=0">${i}</a></div>
                                            </c:when>
                                        </c:choose>

                                    </c:forEach>

                                    <c:if test="${(requestScope.curr == requestScope.total)}">
                                        <div id="pagination" > <span class="disabled">Next </span></div>
                                    </c:if>
                                    <c:if test="${(requestScope.curr != requestScope.total)}">
                                        <div id="pagination" > <a href="index?page=index&curr2=0&curr=${(requestScope.curr + 1)}&more=0">Next </a></div>
                                    </c:if>

                                </div>
                            </c:if>
                        </div>
                            
                        <div>  <!-- Delayed TNA -->   
                            <h4>Delayed TNAs</h4>

                            <c:if test="${requestScope.totalDelayedTNA == 0}" >
                                <p id="message"> No TNA Delayed</p>
                            </c:if>

                            <c:if test="${requestScope.totalDelayedTNA != 0}" >

                                <table class="table-bordered" id="bordered-table" style="width: 100%;margin: 20px auto">
                                    <tr>
                                        <th class="align-center">PO Reference</th>
                                        <th class="align-center">Buyer</th>
                                        <th class="align-center">Delayed Activity</th>

                                    </tr>
                                    <c:if test="${requestScope.delayedTNAList == null}">
                                        <c:forEach items="${applicationScope.delayedTNAList}" var="tna">
                                            <tr>
                                                <td><a href="view_po?redirect=three&from=index&poRef=${tna.poRef}">${tna.poRef}</a></td>
                                                <td>${tna.buyerName}</td>
                                                <td>${tna.activity}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${requestScope.delayedTNAList != null}">
                                        <c:forEach items="${requestScope.delayedTNAList}" var="tna">
                                            <tr>
                                                <td><a href="view_po?redirect=three&from=index&poRef=${tna.poRef}">${tna.poRef}</a></td>
                                                <td>${tna.buyerName}</td>
                                                <td>${tna.activity}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    

                                </table>

                                <div id="paging" >   <!-- Pagination Div -->

                                    <c:if test="${requestScope.curr2 == 1}">
                                        <div id="pagination" > <span class="disabled">Previous </span></div>
                                    </c:if>
                                    <c:if test="${requestScope.curr2 != 1}">
                                        <div id="pagination" > <a href="index?page=index&curr=0&curr2=${(requestScope.curr2 - 1)}&more=0">Previous </a></div>
                                    </c:if>

                                    <c:forEach var="i" begin="1" end="${requestScope.totalDelayedTNA}" >
                                        <c:choose >
                                            <c:when test="${(i < requestScope.curr2) && (i <= 3 || (requestScope.curr2 - i) <= 2)}">
                                                <div id="pagination" ><a href="index?page=index&curr=0&curr2=${i}&more=0">${i}</a></div>
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
                                                <div id="pagination" ><a href="index?page=index&curr=0&curr2=${i}&more=0">${i}</a></div>
                                            </c:when>
                                        </c:choose>

                                    </c:forEach>

                                    <c:if test="${(requestScope.curr2 == requestScope.totalDelayedTNA)}">
                                        <div id="pagination" > <span class="disabled">Next </span></div>
                                    </c:if>
                                    <c:if test="${(requestScope.curr2 != requestScope.totalDelayedTNA)}">
                                        <div id="pagination" > <a href="index?page=index&curr=0&curr2=${(requestScope.curr2 + 1)}&more=0">Next </a></div>
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

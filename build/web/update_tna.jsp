<%-- 
    Document   : index
    Created on : 3 Jan, 2016, 8:25:58 AM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.user == null}" >
    <jsp:forward page="login.jsp" />
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <title>Update TNA</title>
        <%@include file="include/head.jsp" %>
        <link type="text/css" href="css/tna.css" rel="stylesheet" />
        <script type="text/javascript" src="scripts/tna.js" ></script>
    </head>
    <body>
        <div class="container-fluid">
        <div id="wrapper" class="container" >
            <%@include file="include/header.jsp" %>
            <%@include file="include/navbar.jsp" %>
          
            <section id="tna-content" >
                <%@include file="include/user_nav.jsp" %><br><br>
                
                <div class="btn-group">
                    <a  class="btn btn-default" href="update_tna?redirect=one&viewBy=activity">update TNA activity wise</a>
                    <a  class="btn btn-default" href="update_tna?redirect=one&viewBy=poRef">update TNA po reference wise</a>
                    <a  class="btn btn-default" href="update_tna?redirect=one&viewBy=search">search TNA</a>
                </div>
                
                
                <div style="margin: 20px auto" >   <!--Activity Wise TNA div-->
                    <c:if test="${requestScope.activityWise != null}" >
                        
                        <form class="form-inline" role="form" name="activityform" action="update_tna" method="get" onsubmit="return selectActivity()">                              
                            <select name="activityNo" class="form-control">
                                <option value="" selected>Select Activity</option>
                                    <c:forEach items="${sessionScope.activityMap}" var="activity" >
                                        <option value="${activity.key}" >${activity.value}</option>
                                    </c:forEach>
                            </select>
                            <select name="buyer" class="form-control">
                                <option value="" selected>All Buyers</option>
                                    <c:forEach items="${sessionScope.userBuyerList}" var="buyer" >
                                        <option value="${buyer}" >${buyer}</option>
                                    </c:forEach>
                            </select>
                            <button type="submit" name="view" value="allActivity"  class="btn btn-default" >update</button>
                            <input type="hidden" name="redirect" value="two" />                                                            
                        </form>
                        
                    </c:if>
                
                </div>
                
                <div style="margin: 20px auto">  <!-- PORef Wise Option Div-->  
                    <c:if test="${requestScope.poRefWise != null}" >
                        <div >
                            <form class="form-inline" name="form" action="update_tna" method="get" onsubmit="return selectBuyer()">
                                <select name="buyer" class="form-control" >
                                    <option value="" selected>Select Buyer</option>
                                    <c:if test="${sessionScope.user.role != 'MERCHANDISER'}" >
                                        <c:forEach items="${applicationScope.allBuyerList}" var="buyer" >
                                            <option value="${buyer}" >${buyer}</option>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${sessionScope.user.role == 'MERCHANDISER'}" >
                                        <c:forEach items="${sessionScope.userBuyerList}" var="buyer" >
                                            <option value="${buyer}" >${buyer}</option>
                                        </c:forEach>
                                    </c:if>

                                </select>
                                <button type="submit" name="view" value="allPORef"  class="btn btn-default" >Select</button>
                                <input type="hidden" name="curr" value="1" />
                                <input type="hidden" name="redirect" value="two" />
                            </form>
                            <!--Displaying search result of TNA buyer wise and displaying page wise-->    
                            <c:if test="${requestScope.buyerTNAList != null}" >
                                <p id="message" >${requestScope.message}</p>


                                <c:if test="${requestScope.message == null}">
                                    <div style="width: 50%;margin: 20px auto">
                                        <h4>TNAs of ${requestScope.buyer}</h4>

                                        <table class="table-bordered" id="bordered-table" width="100%">
                                            <tr>
                                                <th class="align-center">PO Reference</th>
                                                <th class="align-center">Order Date</th>
                                            </tr>
                                            <c:forEach items="${requestScope.buyerTNAList}" var="tna">
                                                <tr>
                                                    <td><a href="update_tna?redirect=three&poRef=${tna.key}">${tna.key}</a></td>
                                                    <td>${tna.value}</td>
                                                </tr>
                                            </c:forEach>

                                        </table>
                                    </div>

                                    <div id="paging" >   <!-- Pagination Div -->

                                        <c:if test="${requestScope.curr == 1}">
                                            <div id="pagination" > <span class="disabled">Previous </span></div>
                                        </c:if>
                                        <c:if test="${requestScope.curr != 1}">
                                            <div id="pagination" > <a href="update_tna?redirect=two&view=allPORef&curr=${(requestScope.curr - 1)}&buyer=${requestScope.buyer}">Previous </a></div>
                                        </c:if>

                                        <c:forEach var="i" begin="1" end="${requestScope.total}" >
                                            <c:choose >
                                                <c:when test="${(i < requestScope.curr) && (i <= 3 || (requestScope.curr - i) <= 2)}">
                                                    <div id="pagination" ><a href="update_tna?redirect=two&view=allPORef&curr=${i}&buyer=${requestScope.buyer}">${i}</a></div>
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
                                                    <div id="pagination" ><a href="update_tna?redirect=two&view=allPORef&curr=${i}&buyer=${requestScope.buyer}">${i}</a></div>
                                                </c:when>
                                            </c:choose>

                                        </c:forEach>

                                        <c:if test="${(requestScope.curr == requestScope.total)}">
                                            <div id="pagination" > <span class="disabled">Next </span></div>
                                        </c:if>
                                        <c:if test="${(requestScope.curr != requestScope.total)}">
                                            <div id="pagination" > <a href="update_tna?redirect=two&view=allPORef&curr=${(requestScope.curr + 1)}&buyer=${requestScope.buyer}">Next </a></div>
                                        </c:if>

                                    </div>
                                </c:if>
                            </c:if>                           
                        </div>
                    </c:if>
                </div>
                
                <div style="margin: 20px auto">   <!--Search TNA viv-->
                    <c:if test="${requestScope.searchPO != null}" >
                        
                        <p id="errorBox"></p>
                        <form class="form-inline" name="searchform" action="update_tna" method="get" onsubmit="return searchTNA()">
                            <input type="text" name="poRef" placeholder="PO Reference Number" class="form-control"/>

                            <input type="submit" name="search" value="search"  class="btn btn-default" />
                            <input type="hidden" name="redirect" value="three" />
                        </form>
                        
                        
                        <p id="message" > ${requestScope.poSearchMessage}</p>
                    </c:if>
                    
                </div>
              
                
            </section>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

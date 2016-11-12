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
        <title>View TNA</title>
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
                <%@include file="include/user_nav.jsp" %> <br><br>
                
                <div class="btn-group">
                    <a  class="btn btn-default" href="view_po?redirect=one&viewBy=activity">view all TNA activity wise</a>
                    <a  class="btn btn-default" href="view_po?redirect=one&viewBy=poRef">view all TNA po reference wise</a>
                    <a  class="btn btn-default" href="view_po?redirect=one&viewBy=search">search TNA</a>
                </div>
                   
                <div style="width: 98%;margin: 20px auto">

                        <h4><Label>Activity Name: </label> ${requestScope.activityName}</h4>
                        
                        <c:choose>
                            <c:when test="${requestScope.total == 0}" >
                                <p id="message">No TNA found for ${requestScope.buyer}</p>
                            </c:when>
                            <c:when test="${requestScope.activityListSize == 0}" >
                                <p id="message">This activity is not applicable for ${requestScope.buyer}</p>
                            </c:when>
                            <c:otherwise>

                                <table class="table-bordered" id="bordered-table" style="width: 100%;margin: 20px auto">
                                    <tr>
                                        <th class="align-center" width="15%" >PORef No.</th>
                                        <th class="align-center" width="15%">Buyer Name</th>
                                        <th class="align-center" width="13%">Order Date</th>
                                        <th class="align-center" width="10%">Timeline</th>
                                        <th class="align-center" width="13%">Due Date</th>
                                        <th class="align-center" width="16%">Completion Date</th>
                                        <th class="align-center" width="18%">Remarks</th>
                                    </tr>

                                    <c:forEach items="${requestScope.activityList}" var="activity" varStatus="status" >
                                        <tr>
                                                <td>${activity.poRef}<input type="hidden" name="poRef${status.index}" value="${activity.poRef}" /></td>                          
                                                <td>${activity.buyerName}</td>
                                                <td>${activity.orderDate}</td>
                                                <td>${activity.timeline}</td>
                                                <td>${activity.dueDate}</td>
                                                <c:choose>
                                                    <c:when test="${requestScope.onTime[status.index] == 1}">
                                                        <td><p style="color: blue">${activity.completionDate}</p></td>
                                                        <td><p style="color: blue">${activity.remarks}</p></td>
                                                    </c:when>
                                                    <c:when test="${requestScope.onTime[status.index] == -1}">
                                                        <td><p style="color: orange">${activity.completionDate}</p></td>
                                                        <td><p style="color: orange">${activity.remarks}</p></td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>${activity.completionDate}</td>
                                                        <td>${activity.remarks}</td>
                                                    </c:otherwise>
                                                </c:choose>

                                        </tr>
                                    </c:forEach>


                                </table>

                                <div id="paging" >   <!-- Pagination Div -->

                                    <c:if test="${requestScope.curr == 1}">
                                        <div id="pagination" > <span class="disabled">Previous </span></div>
                                    </c:if>
                                    <c:if test="${requestScope.curr != 1}">
                                        <div id="pagination" > <a href="view_po2?activityNo=${requestScope.activityNo}&buyer=${requestScope.buyer}&curr=${(requestScope.curr - 1)}">Previous </a></div>
                                    </c:if>

                                    <c:forEach var="i" begin="1" end="${requestScope.total}" >
                                        <c:choose >
                                            <c:when test="${(i < requestScope.curr) && (i <= 3 || (requestScope.curr - i) <= 2)}">
                                                <div id="pagination" ><a href="view_po2?activityNo=${requestScope.activityNo}&buyer=${requestScope.buyer}&curr=${i}">${i}</a></div>
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
                                                <div id="pagination" ><a href="view_po2?activityNo=${requestScope.activityNo}&buyer=${requestScope.buyer}&curr=${i}">${i}</a></div>
                                            </c:when>
                                        </c:choose>

                                    </c:forEach>

                                    <c:if test="${(requestScope.curr == requestScope.total)}">
                                        <div id="pagination" > <span class="disabled">Next </span></div>
                                    </c:if>
                                    <c:if test="${(requestScope.curr != requestScope.total)}">
                                        <div id="pagination" > <a href="view_po2?activityNo=${requestScope.activityNo}&buyer=${requestScope.buyer}&curr=${(requestScope.curr + 1)}">Next </a></div>
                                    </c:if>

                                </div>

                            </c:otherwise>
                        </c:choose>
                    
                </div>
              
                
            </section>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

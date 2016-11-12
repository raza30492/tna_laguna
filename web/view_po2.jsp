<%-- 
    Document   : index
    Created on : 3 Jan, 2016, 8:25:58 AM
    Author     : Md Zahid Raza
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.tna.services.DateService"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.tna.dto.Activity"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                
                <div class="btn-group">
                    <a  class="btn btn-default" href="view_po?redirect=one&from=tna&viewBy=activity">view all TNA activity wise</a>
                    <a  class="btn btn-default" href="view_po?redirect=one&from=tna&viewBy=poRef">view all TNA po reference wise</a>
                    <a  class="btn btn-default" href="view_po?redirect=one&from=tna&viewBy=search">search TNA</a>
                </div>
                
                <div style="width: 60%;margin: 20px auto">
                    <table class="table-bordered" id="bordered-table" width="100%">
                        <tr>
                            <td width="15%"><label>Buyer</label></td>
                            <td width="35%">${requestScope.poObj.buyerName}</td>
                            <td width="20%"><label>PO Ref No</label></td>
                            <td width="30%">${requestScope.poObj.poRef}</td>
                        </tr>
                        <tr>
                            <td><label>Style</label></td>
                            <td>${requestScope.poObj.style}</td>
                            <td><label>Season</label></td>
                            <td>${requestScope.poObj.season}</td>
                        </tr>
                        <tr>
                            <td><label>Quantity</label></td>
                            <td>${requestScope.poObj.quantity}</td>
                            <td><label>Order Date</label></td>
                            <td>${requestScope.poObj.orderDate}</td>
                        </tr>
                    </table>

                </div>  
                    
                <div style="width: 95%;margin: 20px auto">
                    <p id="message" style="margin-bottom: 30px;">${requestScope.updateRequestMessage}</p>
                    <table class="table-bordered" id="bordered-table" width="100%">
                        <tr>
                            <th class="align-center" width="30%">Activity</th>
                            <th class="align-center" width="13%">Timeline</th>
                            <th class="align-center" width="16%">Due Date</th>
                            <th class="align-center" width="16%">Completion Date</th>
                            <th class="align-center" width="25%">Remarks</th>
                        </tr>
                       
                        <c:forEach items="${requestScope.activityList}" var="activity" varStatus="status" >
                            <c:if test="${activity.timeline != 'NA'}" >
                                <tr>
                                        <td>${activity.activityName}</td>
                                        <td>${activity.timeline}</td>
                                        <td>${activity.dueDate}</td>
                                        <c:if test="${requestScope.onTime[status.index] == -1}" >
                                            <td style="color: orange">${activity.completionDate}</td>
                                            <td style="color: orange">${activity.remarks}</td>
                                        </c:if>
                                        <c:if test="${requestScope.onTime[status.index] == 1}" >
                                            <td style="color: blue">${activity.completionDate}</td>
                                            <td style="color: blue">${activity.remarks}</td>
                                        </c:if>
                                        <c:if test="${requestScope.onTime[status.index] == 0}" >
                                            <td >${activity.completionDate}</td>
                                            <td >${activity.remarks}</td>
                                        </c:if>
                                </tr>
                            </c:if>
                        </c:forEach>
                        
                       
                    </table>
                </div>
              
                
            </section>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

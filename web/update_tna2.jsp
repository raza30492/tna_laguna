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
<c:if test="${requestScope.poObj == null }" >
    <jsp:forward page="general_error.html"  />
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <title>Update TNA</title>
        <%@include file="include/head.jsp" %>
        <link type="text/css" href="css/tna.css" rel="stylesheet" />
        <script type="text/javascript" src="scripts/update_tna.js" ></script>
    </head>
    <body>
        <div class="container-fluid">
        <div id="wrapper" class="container" >
            <%@include file="include/header.jsp" %>
            <%@include file="include/navbar.jsp" %>
          
            <section id="tna-content" >
                <%@include file="include/user_nav.jsp" %><br><br>
                
                <div style="width: 60%; margin: 20px auto" >
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
                    
                <div  style="width: 98%;margin: 20px auto">
                    
                    <form name="updateForm" action="update_tna2" method="get" onsubmit="return tna2()" >
                        <p id="errorBox" >Note: Correct Date Format is [DD/MM/YYYY]. Enter <mark>NULL</mark> to set blank date.</p>
                        <table class="table-bordered" id="bordered-table" width="100%">
                            <tr>
                                <th class="align-center" width="30%">Activity</th>
                                <th class="align-center" width="10%">Timeline</th>
                                <th class="align-center" width="15%">Due Date</th>
                                <th class="align-center" width="15%">Completion Date</th>
                                <th class="align-center" width="30%">Remarks</th>
                            </tr>

                            <c:forEach items="${requestScope.activityList}" var="activity" varStatus="status" >
                                <c:if test="${activity.timeline != 'NA'}" >
                                    <tr>
                                            <td>${activity.activityName}</td>
                                            <td>${activity.timeline}</td>
                                            <td>${activity.dueDate}</td>
                                            <c:if test="${requestScope.canUpdate[status.index] == 2}" >
                                                <td><input type="text" class="form-control align-center" name="date${activity.activityNo}" placeholder="${activity.completionDate}"  /></td>
                                                <td><input type="text" class="form-control align-center" name="remarks${activity.activityNo}" value="${activity.remarks}"  /></td>

                                            </c:if>
                                            <c:if test="${requestScope.canUpdate[status.index] == 1}" >
                                                <td><input type="text" class="form-control align-center red" name="date${activity.activityNo}" placeholder="${activity.completionDate}"  /></td>
                                                <td><input type="text" class="form-control align-center red" name="remarks${activity.activityNo}" value="${activity.remarks}"  /></td>
                                            </c:if>
                                            <c:if test="${requestScope.canUpdate[status.index] == 0}" >
                                                <td >${activity.completionDate}</td>
                                                <td >${activity.remarks}</td>
                                            </c:if>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            
                      
                        </table>
                        <input type="hidden" name="user" value="${sessionScope.user.role}" /> <!--This field for javaScript form validation according to users-->
                        <input type="hidden" name="poRef" value="${requestScope.poObj.poRef}" />
                        <input type="submit" value="Update" class="btn btn-info" style="margin-top: 20px"  />
                    </form>
                </div>
              
                
            </section>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

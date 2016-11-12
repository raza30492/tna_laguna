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
        <title>View Buyer</title>
        <%@include file="include/head.jsp" %>
        <link type="text/css" href="css/admin.css" rel="stylesheet" />
        <script type="text/javascript" src="scripts/admin_buyer.js" ></script>
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
                    <div id="admin-content">
                        <div class="btn-group">
                            <a href="admin_view_buyer.jsp" class="btn btn-primary active " role="button">View Buyer</a>
                            <a href="admin_update_buyer.jsp" class="btn btn-primary" >Update Buyer</a>
                            <a href="add_buyer?redirect=one" class="btn btn-primary" role="button">Add Buyer</a>
                            <a href="admin_remove_buyer.jsp" class="btn btn-primary" role="button">Remove Buyer</a>
                        </div>
                    
                        <div style="margin: 20px auto">   <!--Buyer Search div  --> 
                            <p id="errorBox" ></p>

                            <form class="form-inline" role="form" name="form" action="view_buyer" method="get" onsubmit="return selectBuyer()" >

                                <SELECT name="buyerId" class="form-control">
                                    <option value="">select buyer </option>
                                    <c:forEach items="${sessionScope.allBuyerMap}" var="entry" >                                  
                                        <option value="${entry.key}"  > ${entry.value} </option>
                                    </c:forEach>                  

                                </SELECT>
                                <button type="submit" name="redirect" value="one" class="btn btn-default" >Select</button>
                            </form>
                        </div>
                        <div id="div1" >
                
                                   
                            <div > <!--Message div  -->       
                                <c:if test="${requestScope.buyerUpdateMessage != null}" >
                                    <p id="message" >${requestScope.buyerUpdateMessage}</p>
                                </c:if>
                            </div> 

                            <div><!--Activity Timeline Display div  -->  
                                <c:if test="${requestScope.activityTimelineObj != null}" >

                                    <h4 style="margin-top: 10px;">Buyer Name :  ${requestScope.activityTimelineObj.buyerName}</h4>

                                    

                                        <table class="table-bordered" id="bordered-table" style="width:80%;margin: 20px auto" >
                                            <tr>
                                                <th class="align-center">SL No.</th>
                                                <th class="align-center">Activity</th>
                                                <th class="align-center">Time line</th>
                                            </tr>
                                            <tr>
                                                <td>1</td>
                                                <td>${requestScope.activityNames[0]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[0]}</td>
                                            </tr>
                                            <tr>
                                                <td>2</td>
                                                <td>${requestScope.activityNames[1]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[1]}</td>
                                            </tr>
                                            <tr>
                                                <td>3</td>
                                                <td>${requestScope.activityNames[2]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[2]}</td>
                                            </tr>
                                            <tr>
                                                <td>4</td>
                                                <td>${requestScope.activityNames[3]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[3]}</td>
                                            </tr>
                                            <tr>
                                                <td>5</td>
                                                <td>${requestScope.activityNames[4]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[4]}</td>
                                            </tr>
                                            <tr>
                                                <td>6</td>
                                                <td>${requestScope.activityNames[5]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[5]}</td>
                                            </tr>
                                            <tr>
                                                <td>7</td>
                                                <td>${requestScope.activityNames[6]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[6]}</td>
                                            </tr>
                                            <tr>
                                                <td>8</td>
                                                <td>${requestScope.activityNames[7]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[7]}</td>
                                            </tr>
                                            <tr>
                                                <td>9</td>
                                                <td>${requestScope.activityNames[8]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[8]}</td>
                                            </tr>
                                            <tr>
                                                <td>10</td>
                                                <td>${requestScope.activityNames[9]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[9]}</td>
                                            </tr>
                                            <tr>
                                                <td>11</td>
                                                <td>${requestScope.activityNames[10]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[10]}</td>
                                            </tr>
                                            <tr>
                                                <td>12</td>
                                                <td>${requestScope.activityNames[11]}</td>
                                                <td>${requestScope.activityTimelineObj.activities[11]}</td>
                                            </tr>
                                        </table>
                                    <form action="view_buyer" method="post"  >
                                        <input type="hidden" name="buyerId" value="${requestScope.activityTimelineObj.buyerId}" />
                                        <button type="submit" name="redirect" value="two" class="btn btn-info" >Update Timeline</button>
                                    </form>
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


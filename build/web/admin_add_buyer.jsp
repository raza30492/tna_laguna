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
       
        <title>Add Buyer</title>
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
                            <a href="admin_view_buyer.jsp" class="btn btn-primary " role="button">View Buyer</a>
                            <a href="admin_update_buyer.jsp" class="btn btn-primary  " >Update Buyer</a>
                            <a href="add_buyer?redirect=one" class="btn btn-primary active" role="button">Add Buyer</a>
                            <a href="admin_remove_buyer.jsp" class="btn btn-primary" role="button">Remove Buyer</a>
                        </div>
                        
                        <div >
                
                            <div>   <!-- Message Div --> 
                                <c:if test="${requestScope.buyerAddMessage != null}" >
                                    <p id="message" >  ${requestScope.buyerAddMessage} </p><br>
                                    <a href="add_buyer?redirect=one" style="font-size: 20px;"> Add Another Buyer</a>
                                </c:if>
                            </div>

                            <div><!-- Buyer Add Table Div --> 
                                <c:if test="${requestScope.buyerAddMessage == null}" >
                                    <h4>Add Buyer</h4>

                                    <p id="errorBox"></p>

                                    <form name="form" action="add_buyer" method="post" onsubmit="return addBuyer()" >
                                        <input type="text" name="buyer" value="" class="form-control align-center" placeholder="Buyer Name" style="width: 40%;margin: 5px auto" />
                                        <table class="table-bordered" id="bordered-table" style="width: 80%; margin: 20px auto" >
                                            <tr>
                                                <th class="align-center">SL No.</th>
                                                <th class="align-center">Activity</th>
                                                <th class="align-center">Time line</th>
                                            </tr>
                                            <tr>
                                                <td>1</td>
                                                <td>${requestScope.activityNames[0]}</td>
                                                <td><input type="text" name="activity1" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>2</td>
                                                <td>${requestScope.activityNames[1]}</td>
                                                <td><input type="text" name="activity2" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>3</td>
                                                <td>${requestScope.activityNames[2]}</td>
                                                <td><input type="text" name="activity3" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>4</td>
                                                <td>${requestScope.activityNames[3]}</td>
                                                <td><input type="text" name="activity4" value="" class="form-control align-center"class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>5</td>
                                                <td>${requestScope.activityNames[4]}</td>
                                                <td><input type="text" name="activity5" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>6</td>
                                                <td>${requestScope.activityNames[5]}</td>
                                                <td><input type="text" name="activity6" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>7</td>
                                                <td>${requestScope.activityNames[6]}</td>
                                                <td><input type="text" name="activity7" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>8</td>
                                                <td>${requestScope.activityNames[7]}</td>
                                                <td><input type="text" name="activity8" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>9</td>
                                                <td>${requestScope.activityNames[8]}</td>
                                                <td><input type="text" name="activity9" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>10</td>
                                                <td>${requestScope.activityNames[9]} </td>
                                                <td><input type="text" name="activity10" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>11</td>
                                                <td>${requestScope.activityNames[10]}</td>
                                                <td><input type="text" name="activity11" value="" class="form-control align-center" /></td>
                                            </tr>
                                            <tr>
                                                <td>12</td>
                                                <td>${requestScope.activityNames[11]}</td>
                                                <td><input type="text" name="activity12" value="" class="form-control align-center" /></td>
                                            </tr>
                                        </table>
                                                <button type="submit" name="redirect" value="two" class="btn btn-info">Add Buyer</button>
                                    </form>

                                </c:if>
                            </div>


                        </div>
                        
                    </div>
                </div>
            </div>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
    <div>
    </body>
</html>


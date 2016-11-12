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
        <title>Remove Buyer</title>
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
                            <a href="add_buyer?redirect=one" class="btn btn-primary" role="button">Add Buyer</a>
                            <a href="admin_remove_buyer.jsp" class="btn btn-primary active" role="button">Remove Buyer</a>
                        </div>
                        
                        <div>   <!--Select Buyer div  --> 
                
                            <h4>Remove Buyer</h4>

                            <c:if test="${requestScope.removeMessage == null}" >
                                <p id="message" >Note: if you remove a buyer,then all TNA associated with this buyer will also be deleted </p>
                            </c:if>
                            <p id="message" >${requestScope.removeMessage} </p>

                            <p id="errorBox"> </p>

                            <form class="form-inline" role="form" name="form" action="remove_buyer" method="get" onsubmit="return removeBuyer()" >

                                <SELECT name="buyerName" class="form-control">
                                    <option value="">select buyer </option>
                                    <c:forEach items="${sessionScope.allBuyerMap}" var="entry" >                                  
                                        <option value="${entry.value}"  > ${entry.value} </option>
                                    </c:forEach>

                                </SELECT>
                                <input type="submit" name="remove" value="Remove" class="btn btn-default"  />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            
            
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>


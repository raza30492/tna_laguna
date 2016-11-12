<%-- 
    Document   : index
    Created on : 3 Jan, 2016, 8:25:58 AM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:if test="${sessionScope.user.role != 'ADMIN' && sessionScope.user.role != 'MERCHANDISER' }" >
    <jsp:forward page="unauthorised_access.jsp" />
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <title>Generate TNA</title>
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
                <%@include file="include/user_nav.jsp" %>
                                
                <p id="message"> ${requestScope.poGenerateMessage}</p>
                
                <div id="gen-tna">
                    <c:if test="${sessionScope.userBuyerListSize == 0}" >
                        <p id="message" >You cannot generate Purchase Order because no Buyers are Allocated to you. Contact admin!</p>
                    </c:if>

                    <p id="errorBox"></p>

                    <form name="form" action="generate_tna" method="post" onsubmit="return genTNA()">
                        <table id="gen-tna-table">
                            <tr>
                                <td width="12%"><label>Buyer</label></td>
                                <td width="38%">
                                    <SELECT name="buyer" class="form-control">                                      
                                        <c:forEach items="${sessionScope.userBuyerList}" var="buyer" >                                  
                                            <option value="${buyer}"  > ${buyer} </option>
                                        </c:forEach>                                        
                                    </SELECT>
                                </td>
                                <td width="12%"><label>PO Ref No</label></td>
                                <td width="38%"><input type="text" class="form-control" name="poRef" value="" placeholder="PO Reference Number" /></td>
                            </tr>
                            <tr>
                                <td><label>Style</label></td>
                                <td><input type="text" class="form-control" name="style" value="" placeholder="Style" /></td>
                                <td><label>Season</label></td>
                                <td><input type="text" class="form-control" name="season" value="" placeholder="Season" /></td>
                            </tr>
                            <tr>
                                <td><label>Quantity</label></td>
                                <td><input type="text" class="form-control" name="quantity" value="" placeholder="Quantity" /></td>
                                <td><label>Order Date</label></td>
                                <td><input type="text" class="form-control" name="orderDate" value="" placeholder="[DD/MM/YYYY]" /></td>
                            </tr>
                        </table>

                        <input type="submit" value="Generate TNA" class="btn btn-info"/>

                    </form>                    
                </div>  
            </section>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        <div class="container-fluid">
    </body>
</html>

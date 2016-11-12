<%-- 
    Document   : index
    Created on : 3 Jan, 2016, 8:25:58 AM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.user.role == 'ADMIN' && sessionScope.user.role == 'MERCHANDISER'}" >
    <jsp:forward page="unauthorised_access.jsp" />
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <title>Edit TNA</title>
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
                    
                    <a  class="btn btn-default" role="button" href="edit_tna?redirect=one&viewBy=poRef">view all TNA </a>
                    <a  class="btn btn-default" role="button" href="edit_tna?redirect=one&viewBy=search">search TNA</a>
                    
                </div>
               
                
               
                
                <div>  <!-- PORef Wise Option Div-->  
                    <c:if test="${requestScope.poRefWise != null}" >
                        
                        <div style="margin: 20px auto">
                            <form class="form-inline" role="form" name="form" action="edit_tna" method="get" >
                                <select name="buyer" class="form-control" >
                                    <option value="" selected>Select Buyer</option>
                                    <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                        <c:forEach items="${applicationScope.allBuyerList}" var="buyer" >
                                            <option value="${buyer}" >${buyer}</option>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${sessionScope.user.role == 'MERCHANDISER'}">
                                        <c:forEach items="${sessionScope.userBuyerList}" var="buyer" >
                                            <option value="${buyer}" >${buyer}</option>
                                        </c:forEach>
                                    </c:if>

                                </select>
                                <button type="submit" name="view" value="allPORef"  class="btn btn-default" onclick="return selectBuyer()" >Select</button>
                                <input type="hidden" name="curr" value="1" />
                                <input type="hidden" name="redirect" value="two" />
                            </form>
                            
                            <p id="message" >${requestScope.deleteMessage}</p>
                            
                            <!--Displaying search result of TNA buyer wise and displaying page wise-->    
                            <c:if test="${requestScope.buyerTNAList != null}" >
                                <div style="margin: 20px auto">
                                    <p id="message" >${requestScope.message}</p>


                                    <c:if test="${requestScope.message == null}">

                                            <h4>TNAs of ${requestScope.buyer}</h4>

                                            <table class="table-bordered" id="bordered-table" style="width: 40%;margin: 20px auto">
                                                <tr>
                                                    <th class="align-center">PO Reference</th>
                                                    <th class="align-center">Order Date</th>
                                                </tr>
                                                <c:forEach items="${requestScope.buyerTNAList}" var="tna">
                                                    <tr>
                                                        <td><a href="edit_tna?redirect=three&poRef=${tna.key}">${tna.key}</a></td>
                                                        <td>${tna.value}</td>
                                                    </tr>
                                                </c:forEach>

                                            </table>


                                        <div id="paging" >   <!-- Pagination Div -->

                                            <c:if test="${requestScope.curr == 1}">
                                                <div id="pagination" > <span class="disabled">Previous </span></div>
                                            </c:if>
                                            <c:if test="${requestScope.curr != 1}">
                                                <div id="pagination" > <a href="edit_tna?redirect=two&view=allPORef&curr=${(requestScope.curr - 1)}&buyer=${requestScope.buyer}">Previous </a></div>
                                            </c:if>

                                            <c:forEach var="i" begin="1" end="${requestScope.total}" >
                                                <c:choose >
                                                    <c:when test="${(i < requestScope.curr) && (i <= 3 || (requestScope.curr - i) <= 2)}">
                                                        <div id="pagination" ><a href="edit_tna?redirect=two&view=allPORef&curr=${i}&buyer=${requestScope.buyer}">${i}</a></div>
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
                                                        <div id="pagination" ><a href="edit_tna?redirect=two&view=allPORef&curr=${i}&buyer=${requestScope.buyer}">${i}</a></div>
                                                    </c:when>
                                                </c:choose>

                                            </c:forEach>

                                            <c:if test="${(requestScope.curr == requestScope.total)}">
                                                <div id="pagination" > <span class="disabled">Next </span></div>
                                            </c:if>
                                            <c:if test="${(requestScope.curr != requestScope.total)}">
                                                <div id="pagination" > <a href="edit_tna?redirect=two&view=allPORef&curr=${(requestScope.curr + 1)}&buyer=${requestScope.buyer}">Next </a></div>
                                            </c:if>

                                        </div>
                                    </c:if>
                                </div>
                            </c:if> 
                        </div>
                            
                        <c:if test="${requestScope.poObj != null}">
                            <div id="div1" style="width: 90%">
                                <!--    Displaying Purchase Order(TNA)     -->
                                <p id="message"> ${requestScope.editTNAMessage}</p>
                                <c:if test="${requestScope.editTNAMessage == null}" >
                                    <p id="message">Note: Enter date in [DD/MM/YYYY] format.</p>
                                </c:if>

                                <div style="width: 65%;margin: 20px auto">

                                    <p class="red-color" id="errorBox1"></p>

                                    <form name="editform" action="edit_tna" method="get" onsubmit="return editTNA()">
                                        <table  id="gen-tna-table" >
                                            <tr>
                                                <td><label>Buyer</label></td>
                                                <td>${requestScope.poObj.buyerName}</td>
                                                <td><label>PO Ref No</label></td>
                                                <td><input type="text" class="form-control" name="poRef2" value="${requestScope.poObj.poRef}" /></td>
                                            </tr>
                                            <tr>
                                                <td><label>Style</label></td>
                                                <td><input type="text" class="form-control" name="style" value="${requestScope.poObj.style}"  /></td>
                                                <td><label>Season</label></td>
                                                <td><input type="text" class="form-control" name="season" value="${requestScope.poObj.season}"  /></td>
                                            </tr>
                                            <tr>
                                                <td><label>Quantity</label></td>
                                                <td><input type="text" class="form-control" name="quantity" value="${requestScope.poObj.quantity}" /></td>
                                                <td><label>Order Date</label></td>
                                                <td><input type="text" class="form-control" name="orderDate" placeholder="${requestScope.poObj.orderDate}"  /></td>
                                            </tr>
                                        </table>

                                        <input type="submit" value="save TNA" class="btn btn-info" />
                                        <input type="hidden" name="redirect" value="four" />
                                        <input type="hidden" name="buyer" value="${requestScope.poObj.buyerName}" />
                                        <input type="hidden" name="poRef1" value="${requestScope.poObj.poRef}" />
                                        <input type="hidden" name="orderDate1" value="${requestScope.poObj.orderDate}" />
                                    </form>                    
                                </div>   

                            </div>
                        </c:if>
                    </c:if>
                </div>
                
                <div>   <!--Search TNA viv-->
                    <c:if test="${requestScope.searchPO != null}" >
                        <p id="errorBox"></p>
                        <form class="form-inline" name="searchform" action="edit_tna" method="get" onsubmit="return searchTNA()">
                            <input type="text" name="poRef" placeholder="PO Reference Number" class="form-control" />

                            <input type="submit" name="search" value="search" class="btn btn-default" />
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

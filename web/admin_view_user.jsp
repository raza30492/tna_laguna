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
        <title>View User</title>
        <%@include file="include/head.jsp" %>
        
        <link type="text/css" href="css/admin.css" rel="stylesheet" />
        <script type="text/javascript" src="scripts/admin_user.js" ></script>
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
                    
                    <div id="admin-content" >
                        <div class="btn-group">
                            <a href="admin_view_user.jsp" class="btn btn-primary active " role="button">View Users</a>
                            <a href="admin_add_user.jsp" class="btn btn-primary" role="button">Add User</a>
                            <a href="admin_remove_user.jsp" class="btn btn-primary" role="button">Remove User</a>
                        </div>
                         
                        
                            
                        <div >
                            <div style="margin:20px auto" class="btn-group">
                                <a href="view_user?redirect=one&allUser=all" class="btn btn-default" role="button">View all users</a>
                                <a href="view_user?redirect=one&searchUser=search" class="btn btn-default" role="button">Search users</a>
                            </div>

                            <div>   <!--Search div  --> 

                                <p id="errorBox"></p>
                                
                                <c:if test="${requestScope.search != null}" >

                                    <form class="form-inline" role="form" name="form" action="view_user" method="get" onsubmit="return searchUser()">
                                        <SELECT  name="role" class="form-control">
                                            <option value="" selected > Select Role </option>
                                            <option value="MERCHANDISER">Merchandiser</option>
                                            <option value="PURCHASE">Purchase</option>
                                            <option value="FABRIC_SOURCING">Fabric Sourcing</option>
                                            <option value="LOGISTICS">Logistics</option>
                                            <option value="ADMIN">Admin</option>
                                        </SELECT>

                                        <input type="text" name="eid" value="${requestScope.user.employeeId}" placeholder="Enter Employee ID" class="form-control" />
                                        <input type="submit" name="search" value="Search" class="btn btn-default" />
                                        <input type="hidden" name="redirect" value="three" />
                                    </form>
                                </c:if>

                                <c:if test="${requestScope.all != null}" >
                                    <form class="form-inline" role="form" name="form" action="view_user" method="get" onsubmit="return selectRole()">
                                        <SELECT name="role" class="form-control">
                                            <option value="" selected > Select Role </option>
                                            <option value="MERCHANDISER">Merchandiser</option>
                                            <option value="PURCHASE">Purchase</option>
                                            <option value="FABRIC_SOURCING">Fabric Sourcing</option>
                                            <option value="LOGISTICS">Logistics</option>
                                            <option value="ADMIN">Admin</option>
                                        </SELECT>
                                        <button type="submit" name="view" value="all" class="btn btn-default">select </button>
                                        <input type="hidden" name="redirect" value="two" />
                                        <input type="hidden" name="curr" value="1" />
                                    </form>
                                    <c:if test="${requestScope.userList != null}">

                                            <table class="table-bordered" id="bordered-table" style="width: 75%;margin: 30px auto;">
                                                <tr>
                                                    <th class="align-center">Employee ID</th>
                                                    <th class="align-center">Employee Name</th>

                                                </tr>

                                                <c:forEach items="${requestScope.userList}" var="user" >
                                                    <tr>
                                                        <td>${user.key}</td>
                                                        <td><a class="link1" href="view_user?redirect=three&eid=${user.key}&role=${requestScope.role}">${user.value}</a></td>

                                                    </tr>
                                                </c:forEach>
                                            </table>

                                                <div id="paging" >   <!-- Pagination Div -->

                                                    <c:if test="${requestScope.curr == 1}">
                                                        <div id="pagination" > <span class="disabled">Previous </span></div>
                                                    </c:if>
                                                    <c:if test="${requestScope.curr != 1}">
                                                        <div id="pagination" > <a href="view_user?redirect=two&role=${requestScope.role}&curr=${(requestScope.curr - 1)}">Previous </a></div>
                                                    </c:if>

                                                    <c:forEach var="i" begin="1" end="${requestScope.total}" >
                                                        <c:choose >
                                                            <c:when test="${(i < requestScope.curr) && (i <= 3 || (requestScope.curr - i) <= 2)}">
                                                                <div id="pagination" ><a href="view_user?redirect=two&role=${requestScope.role}&curr=${i}">${i}</a></div>
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
                                                                <div id="pagination" ><a href="view_user?redirect=two&role=${requestScope.role}&curr=${i}">${i}</a></div>
                                                            </c:when>
                                                        </c:choose>

                                                    </c:forEach>

                                                    <c:if test="${(requestScope.curr == requestScope.total)}">
                                                        <div id="pagination" > <span class="disabled">Next </span></div>
                                                    </c:if>
                                                    <c:if test="${(requestScope.curr != requestScope.total)}">
                                                        <div id="pagination" > <a href="view_user?redirect=two&role=${requestScope.role}&curr=${(requestScope.curr + 1)}">Next </a></div>
                                                    </c:if>

                                                </div>

                                    </c:if>
                                    
                                </c:if>

                            </div>

                            <div >  <!-- Message Div--> 
                                <p id="message" >  ${requestScope.findUserMessage}</p>
                            </div>

                            <div style="width: 50%;margin: 20px auto"> <!--  User Display Div-->

                                <c:if test="${requestScope.user != null}"> 
                                    <h4><u> User Details</u> </h4>

                                    <table id="display_table">
                                            <tr>
                                                <td >Employee Name</td>
                                                <td>${requestScope.user.employeeName}</td>                           
                                            </tr>
                                            <tr>
                                                <td >Employee Id</td>
                                                <td>${requestScope.user.employeeId}</td>                           
                                            </tr>
                                            <tr>
                                                <td >Email</td>
                                                <td>${requestScope.user.email}</td>                           
                                            </tr>
                                            <tr>
                                                <td >Date Of Birth</td>
                                                <td>${requestScope.user.dob}</td>                           
                                            </tr>
                                            <tr>
                                                <td>Role</td>
                                                <td>${requestScope.user.role}</td>                           
                                            </tr>
                                    </table>
                                </c:if>

                                <c:if test="${requestScope.user.role == 'MERCHANDISER'}">

                                    <h4><u>Buyer Access</u></h4>

                                    <p id="message" >${requestScope.userBuyerAddMessage}</p>

                                    
                                    <c:if test="${requestScope.buyerListSize == 0}" >
                                        <p id="message">No Buyers allocated</p>
                                    </c:if>

                                    <c:if test="${requestScope.buyerListSize != 0}" >
                                        <table class="table-bordered" id="bordered-table" style="width:100%; margin-bottom: 20px">
                                            <tr>
                                                <th class="align-center">Buyer</th>
                                                <th class="align-center">Action</th>
                                            </tr>
                                            <c:forEach items="${requestScope.buyerList}" var="buyer" >
                                                <tr>
                                                    <td>${buyer}</td>
                                                    <td>
                                                        <a href="view_user?redirect=three&role=MERCHANDISER&eid=${requestScope.user.employeeId}&removeUserBuyer=${buyer}" role="button" class="btn btn-danger">
                                                            Remove
                                                        </a>
                                                    </td>

                                                </tr>
                                            </c:forEach>

                                        </table>
                                    </c:if> 
                                    <form class="form-inline" role="form" action="view_user" method="get" >
                                        <SELECT name="buyerId" class="form-control">
                                            <option value="" selected > Select Buyer </option>
                                            <c:forEach items="${sessionScope.allBuyerMap}" var="entry" >                                  
                                                <option value="${entry.key}"  > ${entry.value} </option>
                                            </c:forEach>                       
                                        </SELECT>
                                        <input type="hidden" name="redirect" value="three" />
                                        <input type="hidden" name="role" value="MERCHANDISER" />
                                        <input type="hidden" name="eid" value="${requestScope.user.employeeId}" />
                                        <button type="submit" name="addUserBuyer" value="NOT NULL" class="btn btn-default" >Add buyer access </button>

                                    </form>
                                </c:if>        



                            </div>
                        </div>   
                
             
                 
                    </div>
                    
                </div>
            </div>
            
            
            
            <section id="content2" >
              
               
            </section>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        <div class="container-fluid">
    </body>
</html>


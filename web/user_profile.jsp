<%-- 
    Document   : index
    Created on : 3 Jan, 2016, 8:25:58 AM
    Author     : Md Zahid Raza
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<c:if test="${sessionScope.user == null }" >
    <jsp:forward page="login.jsp" />
</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN' }" >
    <jsp:forward page="unauthorised_access.jsp" />
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <title>User Profile</title>
        <%@include file="include/head.jsp" %>
        <link type="text/css" href="css/tna.css" rel="stylesheet" />
        <script type="text/javascript" src="scripts/profile.js" ></script> 
        
    </head>
    <body>
        <div class="container-fluid">
        <div id="wrapper" class="container" >
            <%@include file="include/header.jsp" %>
            <%@include file="include/navbar.jsp" %>
          
            <section id="tna-content" >
                <%@include file="include/user_nav.jsp" %>
                
                <c:choose>
                    <c:when test="${requestScope.firstLogin != null}">
                        <div style="width: 40%;margin: 20px auto" >
                            <h4><label>Change Password</label></h4>
                            <p id="errorBox"></p>
                            <form role="form" name="form" action="user_profile" method="post" onsubmit="return checkFirstLogin()">
                                <input type="password" class="form-control"  name="pass1" value="" placeholder="New Password" />
                                <input type="password" class="form-control" name="pass2" value="" placeholder="Confirm New Password" />
                                <button type="submit" name="save" value="password" class="btn btn-block btn-info" > change Password</button>
                                <input type="hidden" name="firstLogin" value="firstLogin" />
                                
                            </form>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div style="width: 50%;margin: 20px auto">
                            <h4>Welcome to Profile Page </h4>

                            <p id="message" > ${requestScope.passwordChangeMessage}</p>
                            <p id="errorBox"></p>
                            
                                <table id="display_table">
                                    <tr>
                                        <td>Name</td>
                                        <td>${sessionScope.user.employeeName}</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>Employee ID</td>
                                        <td>${sessionScope.user.employeeId}</td>
                                        <td></td>
                                    </tr>
                                    <form name="emailForm" action="user_profile" method="get" onsubmit="return checkEmail()"
                                        <tr>
                                            <td>Email</td>
                                            <c:choose >
                                                <c:when test="${requestScope.editEmail != null}" >
                                                    <td><input type="email"  name="email" value="" placeholder="Enter new email" /></td>
                                                    <td><button type="submit" name="save" value="email" id="edit_button"> save</button></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>${sessionScope.user.email}</td>
                                                    <td><button type="submit" name="edit" value="email" id="edit_button"> edit</button></td>
                                                </c:otherwise>
                                            </c:choose>

                                        </tr>
                                    </form>
                                    <form name="dobForm" action="user_profile" method="post" onsubmit="return checkDob()">
                                        <tr>
                                            <td>Date of Birth</td>
                                            <c:choose >
                                                <c:when test="${requestScope.editDob != null}" >
                                                    <td><input type="text"  name="dob" placeholder="DD/MM/YYYY" /></td>
                                                    <td><button type="submit" name="save" value="dob" id="edit_button"> save</button></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>${sessionScope.user.dob}</td>
                                                    <td><button type="submit" name="edit" value="dob" id="edit_button"> edit</button></td>
                                                </c:otherwise>
                                            </c:choose>

                                        </tr>
                                    </form>
                                    <tr>
                                        <td>Role</td>
                                        <td>${sessionScope.user.role}</td>
                                        <td></td>
                                    </tr>
                                    <c:if test="${sessionScope.user.role == 'MERCHANDISER'}" >
                                        <tr>
                                            <td>Buyer access</td>
                                            <td>
                                                <c:forEach items="${sessionScope.userBuyerList}" var="buyer" varStatus="status">
                                                    ${status.index + 1}. ${buyer}<br>
                                                </c:forEach>
                                            </td>
                                            <td></td>
                                        </tr>
                                    </c:if>
                                    <tr >
                                    <form name="passForm" action="user_profile" method="post" onsubmit="return checkPassword()" >
                                        <c:choose >
                                            <c:when test="${requestScope.editPassword != null}" >
                                                <td colspan="3">
                                                    <table style="margin: 10px auto;" id="pass_table">

                                                        <tbody>
                                                            <tr>
                                                                <td><input type="password" name="currentPass" value="" placeholder="Current Password" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><input type="password"  name="pass1" value="" placeholder="New Password" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><input type="password" name="pass2" value="" placeholder="Confirm New Password" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td><button type="submit" name="save" value="password" id="edit_button" > change Password</button></td>
                                                            </tr>

                                                        </tbody>
                                                    </table>
                                                </td>
                                                <td></td>


                                            </c:when>
                                            <c:otherwise>
                                                <td style="padding : 30px"></td>
                                                <td><button type="submit" name="edit" value="password" id="edit_button" > change password</button></td>
                                                <td></td>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                    </tr>
                                </table>
                            
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>
            
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

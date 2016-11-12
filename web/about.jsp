<%-- 
    Document   : index
    Created on : 3 Jan, 2016, 8:25:58 AM
    Author     : Md Zahid Raza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>TNA</title>
        <%@include file="include/head.jsp" %>
        
        <link rel="stylesheet" type="text/css" href="index.css" />
    </head>
    <body>
        <div class="container-fluid">
            <div id="wrapper" class="container"  >
                <%@include file="include/header.jsp" %>
                <%@include file="include/navbar.jsp" %>
            
            <div class="row">
                
                <div class="col-md-12">
                    
                    <div id="index-content" >
                        <!--<h4> About Time and Action Calender Web Application</h4> -->
                        <div style="width: 75%;margin: 20px auto">
                        
                            <p class="align-left font-md">
                                Time and Action Calendar is a Web Application built especially for Garment Manufacturing
                                units. Time and Action calendar (TNA) management is the key to business success. This
                                application aims to keep track of timelines of different activities involved in garment
                                manufacturing. 
                            </p>
                            
                            <h3 class="align-left">Salient Features:</h3>
                            
                            <ul class="align-left font-md" style="padding-left: 25px">
                                <li>User friendly interface.</li>
                                <li>
                                    <p>Easy review, management and tracking of orders throughout the Organisation</p>
                                    <ul style="padding-left: 50px">
                                        <li>Fabric and Trim Purchase to update / track defined activity</li>
                                        <li>Merchandiser to create/ update / delete etc TNA for new orders and control Merchandising related activities. </li>
                                        <li>Management to control new user / buyer creation / access / restrictions / delays / Risks</li>
                                    </ul>
                                </li>
                                <li>Unlimited number of user access possible at a given time.</li>
                                <li>Very friendly tool for order review meeting by Management.</li>
                                <li>
                                    <p>OTIF ( One Time In Full)</p>
                                    <ul style="padding-left: 50px">
                                        <li>Helps achieving <b>Zero Occurrence</b> where timeline deviation is notified in advance.</li>
                                        <li>Separates delayed TNA from <b>ON-TIME </b> &nbsp; TNA- highlights the delayed activity.</li>
                                    </ul>
                                </li>
                                <li>Separate Account for each Merchandiser where they can edit TNA for those Buyers only for which they have been given access.</li>
                                <li>Separate Account for Trims and fabric sourcing departments as well where they can update their activities.</li>
                                <li>Database is well secured and non-editable by unauthorised users.</li>
                                <li>Activities and user-level can be customized according to the factory requirement.</li>
                                <li>
                                    <p>Main Technologies involved in Application Development are:</p>
                                    <ul style="padding-left: 50px">
                                        <li>Front End: &nbsp;&nbsp;&nbsp; HTML5, CSS3, JavaScript</li>
                                        <li>Back End: &nbsp;&nbsp;&nbsp; MySQL, JSP and Servlet</li>
                                    </ul>
                                </li>
                                
                            </ul>
                            
                            <h3 class="align-left">Future Scope</h3>
                            
                            <ul class="align-left font-md" style="padding-left: 25px">
                                <li>Web app can be integrated with Android App for easy access at anytime and anywhere.</li>                                
                                <li>Deviation notification on mobile through message.</li>
                                <li>Planning board can be accommodated. </li>
                            </ul>
                            
                            
                        </div>
                    </div>
                </div>
                
            </div>
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

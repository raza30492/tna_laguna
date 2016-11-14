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
                        
                        <div style="width: 75%;margin: 20px auto" >
                            <p class="align-left font-md">
                                Functions and a Standard Operating Procedure (SOP) for each user is described below.
                            </p>
                            <h3>Functions of User</h3>
                            
                            <h4 class="align-left heading1">Administrator (Admin)</h4>
                            
                            <ul class="font-md align-left" style="padding-left: 20px">
                                <li>He has got all the administrative privileges.</li>
                                <li>He is responsible for managing of buyers which includes addition, updation and deletion </li>
                                <li>He is responsible for registration of users in the application. He can view,register and remove users. </li>
                                <li>He has also got complete privilege for TNA (order) management. He can generate, edit and delete TNA as required.</li>
                                <li>He can update TNA completion date without any restriction i.e of all activities and all buyers. </li>
                                <li>He can also publish notice in the application meant for each and every users.</li>
                            </ul>
                            
                            <h4 class="align-left heading1">Merchandiser</h4>
                            <ul class="font-md align-left" style="padding-left: 20px">
                                <li>He is responsible for generation of order. Buyers assigned to him/her are shown in dropdown.He can later edit or delete TNA.  </li>
                                <li>Once TNA is generated, it is visible to all the users who can update the activities for which they are responsible.</li>
                                <li>He can view all the TNAs, either generated by him or someone else.</li>
                                <li>He can update the completion date of activities for which he is responsible and the buyers for he has access to.</li>    
                            </ul>
                            <h4 class="align-left heading1">Purchase, Fabric Sourcing, Logistics</h4>
                            <ul class="font-md align-left" style="padding-left: 25px">
                                <li>These three have got similar features only the difference being that their activities are different.</li>
                                <li>They can view all the TNA in various ways.</li>
                                <li>They can update the completion date of activities for which they are responsible.</li>
                                <li>They have updation privilege for all buyers. </li>

                            </ul>
                            
                            
                            <h3>Standard Operating Procedure (SOP)</h3>
                            
                            <h4 class="align-left heading1">Administrator (Admin)</h4>
                            
                            <ul class="font-md align-left" style="padding-left: 20px">
                                <li>
                                    Click <b>My Account </b> and All privileges you have got is listed in side menu.
                                </li>
                                <li>
                                    Click <b>Manage Buyers</b>. under <b> Add Buyers </b> section, fill buyer name and timeline for all activities.
                                    Note that acticity timeline for each buyer is different according to buyer priority. Add as many of buyers as your company
                                    deals with.All the buyers added can be seen on Home page. You can also view, Update and Remove any buyers. Note that removal
                                    of buyer will result in removal of all TNAs associated with that buyer
                                </li>
                                <li>
                                    Once Buyers are added, Click on <b>Manage Users</b>. In add users section fill the details of users and 
                                    click register. Note that Employee Id must be 5 digit numeric value only. For the Role of Merchandiser,
                                    There is additional step asking you to add buyer access since a Merchandiser is responsible for only
                                    few of buyers.Provide access to limited buyers from drop down menu for which he/she is responsible.
                                    You can also view and remove any user.
                                </li>
                                <li>
                                    Once all buyers and users are added, Now, a TNA(order) can be generated. To do so, Click <b>Manage TNA</b>.
                                    Under <b>Generate TNA</b> you can Select buyer from drop down list, fill other details and click
                                    <b>Generate TNA</b>. A TNA is successfully generated and is visible on Home page under recent TNAs. Apart from TNA 
                                    generation there are additional features : Delete TNA, Edit TNA, View TNA, Update TNA 
                                    (update completion date of activities). However, Note that Generation of TNA is privilege of Merchandiser.
                                    you need not generate TNA. This is just additional feature provided to you. 
                                </li>
                                <li>
                                    You can publish a notice meant for all users of this application under <b>Manage Notice</b> Section.
                                    These notices will be visible to all users.
                                </li>
                                <li>You can view and manage your Profile.</li>
                            </ul>
                            
                            <h4 class="align-left heading1">Merchandiser</h4>
                            <ul class="font-md align-left" style="padding-left: 20px">
                                <li>
                                    You can view your profile and update few of the details. You can see all the buyers for which you are responsible
                                    here in Buyer Access field.
                                </li>
                                <li>
                                    You are main user responsible for generation of TNA of the buyers to which you have access. To do so, 
                                    click <b>Generate TNA</b>, Select buyers from drop down list, fill other details and Click Generate TNA button.
                                    A TNA has been successfully generated and will be visible on Home Page under Recent TNAs.
                                    You can also edit TNA or Delete TNA if required.
                                </li>
                                <li>
                                    You are also responsible for updation of Completion date of certain activities of a TNA (Refer to table below). To update completion date,
                                    Click  <b>Update TNA</b>, Here you have three ways to update. If You want to update multiple activities of a particular
                                    TNA, then click <b>Update TNA po reference Wise</b> or <b>Search TNA</b> if you know the PO refrence number. If you want
                                    to update a particular activity of multiple TNAs at once, then Click <b>Update TNA activity wise</b>. Depending on the option selected
                                    you are provided with editable field of TNA of only activities for which you are responsible. You should fill completion 
                                    date and remarks if any and click Update.
                                </li>
                                
                            </ul>
                            <h4 class="align-left heading1">Purchase, Fabric Sourcing, Logistics</h4>
                            <ul class="font-md align-left" style="padding-left: 20px">
                                <li>All the three users have similar function, only the activities for which they are responsible is different.Refer to table below to see activities you are responsible.</li>
                                <li>
                                    Your main function is to update the completion date of few activities for which you are responsible. To update completion date,
                                    Click  <b>Update TNA</b>, Here you have three ways to update. If You want to update multiple activities of a particular
                                    TNA, then click <b>Update TNA po reference Wise</b> or <b>Search TNA</b> if you know the PO refrence number. If you want
                                    to update a particular activity of multiple TNAs at once, then Click <b>Update TNA activity wise</b>. Depending on the option selected
                                    you are provided with editable field of TNA of only activities for which you are responsible. You should fill completion 
                                    date and remarks if any and click Update.
                                </li>
                                
                                <li>You can view you profile and update few of details.</li>

                            </ul>
 
                        </div>
                        
                        <div style="width: 70%;margin: 15px auto">
                            <h3>Table of Activity and Users Responsible</h3>
                        
                            <table class="table-bordered" id="bordered-table" style="width: 100%;" >
                                <tr>
                                    <th class="align-center">Sl No.</th>
                                    <th class="align-center">Activities</th>
                                    <th class="align-center">User Responsible</th>
                                </tr>
                                <tr>
                                    <td>1.</td>
                                    <td>Fabric Launch</td>
                                    <td>FABRIC SOURCING</td>
                                </tr>
                                <tr>
                                    <td>2.</td>
                                    <td>Interlining Order</td>
                                    <td>PURCHASE</td>
                                </tr>
                                <tr>
                                    <td>3.</td>
                                    <td>Fit Sample Dispatch</td>
                                    <td>MERCHANDISER</td>
                                </tr>
                                <tr>
                                    <td>4.</td>
                                    <td>Trims Development completion, if required</td>
                                    <td>MERCHANDISER</td>
                                </tr>
                                <tr>
                                    <td>5.</td>
                                    <td>BOM updation</td>
                                    <td>MERCHANDISER</td>
                                </tr>
                                <tr>
                                    <td>6.</td>
                                    <td>Trims PO Raise</td>
                                    <td>PURCHASE</td>
                                </tr>
                                <tr>
                                    <td>7.</td>
                                    <td>PP + Pre-run Yardage In-house</td>
                                    <td>FABRIC SOURCING</td>
                                </tr><tr>
                                    <td>8.</td>
                                    <td>PP sample completion</td>
                                    <td>MERCHANDISER</td>
                                </tr>
                                <tr>
                                    <td>9.</td>
                                    <td>PP Sample Approval</td>
                                    <td>MERCHANDISER</td>
                                </tr>
                                <tr>
                                    <td>10.</td>
                                    <td>Bulk Fabric I/H</td>
                                    <td>FABRIC SOURCING</td>
                                </tr>
                                <tr>
                                    <td>11.</td>
                                    <td>Production file release with PP approval</td>
                                    <td>MERCHANDISER</td>
                                </tr>
                                <tr>
                                    <td>12.</td>
                                    <td>EX-Factory</td>
                                    <td>LOGISTICS, MERCHANDISER</td>
                                </tr>
                                
                            </table>
                        </div>
                        
                        
                        
                        You can mail to Webmaster in case you still have problem.<br>
                        <a href="mailto:zahid7292@gmail.com?Subject=TNA%20FAQ">click here</a> 
                    </div>
                </div>
                
            </div>
            
            <%@include file="include/footer.jsp" %>
        </div>
        </div>
    </body>
</html>

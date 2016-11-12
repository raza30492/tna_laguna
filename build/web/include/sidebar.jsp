<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    #side_bar{
        width: 95%;
        margin-left: 10px;
        padding: 20px 10px;
        border-radius:  15px;
        background-color: lightgray;
    }
    #side_bar li{
        display: block;
        width:100%;
        margin:15px auto;
        line-height: 40px;      
        list-style: none;
        background: #9acfea;
        
    }
    #side_bar li:hover{
        background: #AAAADD;
    }
    #side_bar a{
        font: bold 16px Tahoma;
        text-decoration: none;
        color: #262626;
    }

    #side_bar li:hover ul{
            display: block;
    }
</style>
<aside id="side_bar">

    <c:if test="${sessionScope.user.role == 'ADMIN'}" >
        
    <ul>
        <li><a href="admin_profile.jsp"> Profile </a></li>

        <li ><a href="admin_view_user.jsp">Manage User</a></li>
        <li><a href="admin_view_buyer.jsp"> Manage Buyer </a></li>
        <li><a href="generate_tna.jsp"> Manage TNA </a></li>
        <li><a href="view_notice?redirect=one&curr=1">Manage Notice</a></li>
    </ul>
    </c:if>     
            
</aside>

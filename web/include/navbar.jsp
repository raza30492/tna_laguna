<nav id="nav" class="navbar navbar-inverse ">
    
    <div class="row">
        <div class="col-md-8">
            <ul class="nav navbar-nav">
                <li class="${pageContext.request.requestURI eq '/tna/index.jsp' ? 'active' : ''}" >
                    <a href="index?page=index&curr=0&curr2=0&more=0">Home</a>
                </li>
                <c:if test="${sessionScope.user.role == 'ADMIN'}" >
                    <li class="${pageContext.request.requestURI eq '/tna/admin_profile.jsp' ? 'active' : ''}" >
                        <a href="admin_profile.jsp">My Account</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user.role != 'ADMIN'}" >
                    <li class="${pageContext.request.requestURI eq '/tna/user_profile.jsp' ? 'active' : ''}">
                        <a href="user_profile.jsp">My Account</a>
                    </li>
                </c:if>
                <li class="${pageContext.request.requestURI eq '/tna/view_po.jsp' ? 'active' : ''}">
                    <a href="view_po.jsp">TNA</a>
                </li>
                <li class="${pageContext.request.requestURI eq '/tna/display_notice.jsp' ? 'active' : ''}">
                    <a href="view_notice?redirect=one&curr=1&page=index">Notice</a>
                </li>
                <li >
                    <a href="index?page=faq&curr=0&curr2=0&more=0">FAQ</a>
                </li>
                <li >
                    <a href="index?page=about&curr=0&curr2=0&more=0">About</a>
                </li>
            </ul>
        
        </div>
        <div class="col-md-4">
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${sessionScope.user.employeeName != null}">
                       
                        <li><a > Hello, ${sessionScope.user.employeeName} </a></li>
                        <li><a href="logout">Logout&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                    </c:when>
                    <c:otherwise>
                        
                        <li align="left"><a href="login.jsp">Login&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>



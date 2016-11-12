<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="btn-group">
    <c:if test="${sessionScope.user.role != 'ADMIN'}" >
        <a href="user_profile.jsp" role="button" class="btn btn-primary ${pageContext.request.requestURI eq '/tna/user_profile.jsp' ? 'active' : ''}" >
            Profile
        </a>
    </c:if>
    <c:if test="${sessionScope.user.role == 'MERCHANDISER' || sessionScope.user.role == 'ADMIN'}" >
        <a href="generate_tna.jsp" role="button" class="btn btn-primary ${pageContext.request.requestURI eq '/tna/generate_tna.jsp' ? 'active' : ''}">
            Generate TNA
        </a>
        <a href="delete_tna.jsp" role="button" class="btn btn-primary ${pageContext.request.requestURI eq '/tna/delete_tna.jsp' ? 'active' : ''}">Delete TNA</a>
        <a href="edit_tna.jsp" role="button" class="btn btn-primary ${pageContext.request.requestURI eq '/tna/edit_tna.jsp' ? 'active' : ''}">Edit TNA</a>
    </c:if>
    <a href="view_tna.jsp" role="button" class="btn btn-primary ${pageContext.request.requestURI eq '/tna/view_tna.jsp' ? 'active' : ''}">View TNA</a>
    <a href="update_tna.jsp" role="button" class="btn btn-primary ${pageContext.request.requestURI eq '/tna/update_tna.jsp' ? 'active' : ''}">Update TNA</a>
</div>

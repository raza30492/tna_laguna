<aside id="index_notification">
            
    <h4>NOTICES</h4>
    <c:if test="${applicationScope.noticeListSize == 0}" >
        <p id="message" >No notice available</p>
    </c:if>
    <c:if test="${applicationScope.noticeListSize != 0}" >
        <table class="table" style="margin: 10px auto">
            <c:forEach items="${applicationScope.noticeList}" var="notice">
            <tr>
                <td><a class="link" href="view_notice?redirect=two&id=${notice.key}">${notice.value}</a></td>
            </tr>
            </c:forEach>
            <tr><td></td></tr>
        </table>
        
        <a href="view_notice?redirect=one&curr=1&page=index" style="color: blue">view all notices</a>
    </c:if>


</aside>
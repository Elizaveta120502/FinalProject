<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shipments</title>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/account/usersStyle.css"%>
    <%@include file="/WEB-INF/css/common/welcomebackground.css"%>
</style>
<ul>
    <c:forEach var="shipment" items="${requestScope.shipments}">
        <div class="user-wrapper">
            <c:if test="${not empty sessionScope.user }">
               <b class="user">${shipment.id})
                   Expected date:${shipment.expectedDate}<br>
                   Shipment cost:${shipment.cost}$</b>
            </c:if>
        </div>
    </c:forEach>


</ul>
</body>
</html>

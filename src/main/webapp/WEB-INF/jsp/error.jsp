<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.jwd.model.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/errorStyle.css"%>
</style>
<div class="land_error">
    <div class="text_block_error">
    <h2 class="text_error">Access denied</h2>
        <h4 class="text_error">Your role does not match the access level for this page
        Your role:
        <c:if test="${empty sessionScope.user }">
            ${Role.UNAUTHORIZED_USER.name().toLowerCase()}
        </c:if>
        <c:if test="${not empty sessionScope.user && sessionScope.user.role eq Role.ADMINISTRATOR}">
            ${Role.ADMINISTRATOR.name().toLowerCase()}
        </c:if>
        <c:if test="${not empty sessionScope.user && sessionScope.user.role eq Role.CLIENT }">
            ${Role.CLIENT.name().toLowerCase()}
        </c:if>
    </h4>
    </div>
</div>
</body>
</html>

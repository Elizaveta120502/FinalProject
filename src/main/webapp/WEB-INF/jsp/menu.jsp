<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/mainStyle.css"%>
    <%@include file="/WEB-INF/css/navigateMenu.css"%>
</style>
<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <c:if test="${not empty sessionScope.user }">
        <a href="/controller?command=show_lots">Lots</a>
    </c:if>
    <c:if test="${not empty sessionScope.user}">
        <a href="/controller?command=show_items">Auction items</a>
    </c:if>
    <c:if test="${not empty sessionScope.user}">
        <a href="/controller?command=show_add_item">Add product</a>
    </c:if>
    <c:choose>
        <c:when test="${not empty sessionScope.user && sessionScope.user.role eq Role.ADMINISTRATOR}">

            <a href="/controller?command=show_users">Users list</a>
            <a href="/controller?command=show_delete_item">Delete product</a>
            <a href="/controller?command=show_delete_lot">Delete lot</a>
        </c:when>
        <c:otherwise>
            <a href ="/controller?command=show_create_lot">Create lot</a>
        </c:otherwise>
    </c:choose>
    <c:if test="${not empty sessionScope.user}">
        <a href="/controller?command=show_delete_account">Delete account</a>
    </c:if>
</div>
<span class="menu" onclick="openNav()">Menu❀</span>
<br>
</body>
</html>

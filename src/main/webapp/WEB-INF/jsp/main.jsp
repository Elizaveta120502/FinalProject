<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.jwd.model.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>World Hello</h1>
<p>sent from jsp</p>

<c:if test="${not empty sessionScope.user}">
    <p>Hello, ${sessionScope.user.login}</p>
</c:if>
<br>
<c:if test="${not empty sessionScope.user && sessionScope.user.role eq Role.ADMINISTRATOR}">
    <a href="/controller?command=show_users">users page</a>
    <br>
</c:if>

<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <a href="/controller?command=logout">logout</a>
    </c:when>
    <c:otherwise>
        <a href="/controller?command=login">login</a>
    </c:otherwise>

</c:choose>
</body>
</html>

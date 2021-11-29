<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>All Users</h3>
<ul>
    <c:forEach var="user" items="${requestScope.users}">
        <li>${user.login} ${user.role.roleName} ${user.status}</li>
    </c:forEach>


</ul>
</body>
</html>

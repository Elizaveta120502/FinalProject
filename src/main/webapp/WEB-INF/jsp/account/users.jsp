<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users list</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>
</head>
<body>

<style>
    <%@include file="/WEB-INF/css/account/usersStyle.css"%>
    <%@include file="/WEB-INF/css/common/welcomebackground.css"%>
</style>
<ul>
    <c:forEach var="user" items="${requestScope.users}">
        <div class="user-wrapper">
            <c:if test="${not empty sessionScope.user }">
                <a class="user" href="/controller?command=show_user_block">

                        ${user.login} ${user.email} ${user.role.roleName} ${user.status.name().toLowerCase()}
                </a>

            </c:if>
        </div>
    </c:forEach>


</ul>
</body>
</html>

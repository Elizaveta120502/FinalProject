<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>

    <title>Sign in</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/mainStyle.css"%>
    <%@include file="/WEB-INF/css/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/loginStyle.css"%>

</style>


<div class="login">
    <h3 class="please_text">Please log in:</h3>
    <div class="login_text">
        <form name="login-form" action="/controller?command=login" method="post">
            <label for="login-input">Login:</label>
            <input id="login-input" type="text" name="login" value=""/>
    </div>
    <br>
    <div class="password_text">
        <label for="password-input">Password:</label>
        <input id="password-input" type="password" name="password" value=""/>
        <br/>
    </div>
    <c:if test="${not empty requestScope.errorLoginPassMessage}">
        <b class="error_login">${requestScope.errorLoginPassMessage}</b>
        <br>
    </c:if>
    <div>
        <button id="login" class="login_button" >
            <span>Log in</span>
        </button>
    </div>
</div>


</form>

</body>
</html>

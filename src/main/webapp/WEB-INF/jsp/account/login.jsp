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
    <%@include file="/WEB-INF/css/common/mainStyle.css"%>
    <%@include file="/WEB-INF/css/common/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/account/loginStyle.css"%>

</style>


<div class="login">
    <h3 class="please_text">Please log in:</h3>

    <form name="login-form" action="/controller?command=login" method="post">
        <div class="login_text">
            <label for="login-input">Login:</label>
            <input id="login-input" type="text" name="login" required value=""/>
        </div>
        <br>
        <div class="password_text">
            <label for="password-input">Password:</label>
            <input id="password-input" type="password" name="password" pattern="[0-9a-zA-Z]+" maxlength=6 required
                   value=""/>
            <br/>
        </div>
        <c:if test="${not empty requestScope.errorLoginPassMessage}">
        <b class="error_login">${requestScope.errorLoginPassMessage}</b>
        <br>
        </c:if>
        <div>
            <button id="login" class="login_button">
                <span>Log in</span>
            </button>
        </div>
</div>


</form>

</body>
</html>

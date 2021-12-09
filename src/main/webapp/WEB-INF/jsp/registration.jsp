<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <title>Registration</title>
    <link rel="shortcut icon" type="image/png" href= "https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/registrationStyle.css"%>
    <%@include file="/WEB-INF/css/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/mainStyle.css"%>
    <%@include file="/WEB-INF/css/loginStyle.css"%>

</style>


<div class="registration">
    <h3 class="please_registration">Please register</h3>
<form name="registration-form" action="/controller?command=registration" method="post">
    <div class="login_reg_text">
    <label for="login-register">Login:</label>
    <input id="login-register" type="text" name="login" required value=""/>
    </div>
    <br>
    <div class="password_reg_text">
    <label for="password-input" >Password:</label>
    <input id="password-input" type="password" name="password" pattern="[0-9a-zA-Z]+" maxlength=6 required value=""/>
    </div>
    <br/>
    <br>
    <div class="email_reg_text">
    <label for="login-input" >email:</label>
    <input id="login-input" type="text" name="email" pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$" required value=""/>
    </div>
    <br/>
    <c:if test="${not empty requestScope.errorLoginPassMessage}">
        <b class="error_register">${requestScope.errorLoginPassMessage}</b>
        <br>
    </c:if>


    <div>
        <button  id="login" class="registration_button" >
            <span>Join</span>
        </button>
    </div>
</div>

</form>

</body>
</html>

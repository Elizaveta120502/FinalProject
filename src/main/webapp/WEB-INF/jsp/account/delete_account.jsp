<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete account</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>

</head>
<body>
<style>
    <%@include file="/WEB-INF/css/common/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/account/deleteAccountStyle.css"%>
</style>
<div class="delete_account">
    <form name="delete-form" action="/controller?command=delete_account" method="post">

        <div class="delete_account_text">
            <div>
                <b>Confirm account deletion</b><br>
                <label for="login-input">Login:</label>
                <input id="login-input" type="text" name="login" readonly required value="${user.login}"/>

                <br>

                <label for="password-input">Password:</label>
                <input id="password-input" type="password" name="password" pattern="[0-9a-zA-Z]+" maxlength=6 required
                       value=""/>
                <br/>
            </div>
            <c:if test="${not empty requestScope.errorUserMessage}">
                <b>${requestScope.errorUserMessage}</b>
                <br>
            </c:if>
            <c:if test="${not empty requestScope.successUserMessage}">
                <b>${requestScope.successUserMessage}</b>
                <br>
            </c:if>
            <div>
                <button class="delete_account_button">
                    <span>Delete<br>
                        <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/icon-cornflower.png?raw=true">
                    </span>
                </button>
            </div>
        </div>

    </form>
    </form>
</div>
</body>
</html>

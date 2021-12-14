<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Block user</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>

</head>
<body>
<style>
    <%@include file="/WEB-INF/css/common/mainStyle.css"%>
    <%@include file="/WEB-INF/css/common/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/account/loginStyle.css"%>
    <%@include file="/WEB-INF/css/account/blockUser.css"%>
</style>

<div class="login">
    <h3 class="please_text">Do you want to block this user?</h3>

    <div>

        <form name="login-form" action="/controller?command=block_user" method="post">
            <div class="login_block">
            <label for="login-input" >User's login:</label>
            <input id="login-input" type="text" name="login" required value=""/>
            </div>
            <br>
            <div class="email_block">
                <label >User's email:</label>
                <input type="text" name="email" required
                       pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$" value=""/>
                <br/>
            </div>
            <c:if test="${not empty requestScope.errorBlockMessage}">
                <b class="error_login">${requestScope.errorBlockMessage}</b>
                <br>
            </c:if>
            <c:if test="${not empty requestScope.successBlockMessage}">
                <b class="error_login">${requestScope.successBlockMessage}</b>
                <br>
            </c:if>
            <div>
                <button class = "user_block_button">
                    <span>Block</span>
                </button>
            </div>
        </form>
    </div>

</div>

</div>
</div>
</form>

</body>

</html>

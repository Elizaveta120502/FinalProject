<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<style>
<%@include file="/WEB-INF/css/mainStyle.css"%>
<%@include file="/WEB-INF/css/welcomebackground.css"%>
<%@include file="/WEB-INF/css/loginStyle.css"%>
</style>
<div class="login">
    <h3 class="please_text">Do you want to block this user?</h3>
<c:if test="${not empty sessionScope.user }">
    <div>

        <form name="login-form" action="/controller?command=block_user" method="post">
            <label for="login-input">Login:</label>
            <input id="login-input" type="text" name="login" value=""/>


    <br>
    <div>
        <label>email:</label>
        <input type="text" name="password" value=""/>
        <br/>
    </div>
    <div>
        <button id="login" class="login_button" >
            <span>Block</span>
        </button>
    </div>
        </form>
    </div>
</c:if>
</div>
<%--<c:if test="${not empty requestScope.errorBlockMessage}">--%>
<%--    <b class="error_login">${requestScope.errorBlockMessage}</b>--%>
<%--    <br>--%>
<%--</c:if>--%>
</div>
</div>
</form>

</body>
<head>
    <title>Block user</title>
</head>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<Resources className="org.apache.catalina.webresources.StandardRoot">
<PreResources className="org.apache.catalina.webresources.DirResourceSet"
              base="${catalina.base}"
              internalPath="/"
              webAppMount="/WEB-INF/classes"/>
</Resources>
<html>
<head>
    <title>Block user</title>

</head>
<body>
<style>
<%@include file="/WEB-INF/css/mainStyle.css"%>
<%@include file="/WEB-INF/css/welcomebackground.css"%>
<%@include file="/WEB-INF/css/loginStyle.css"%>
</style>

<div class="login">
    <h3 class="please_text">Do you want to block this user?</h3>

    <div>

        <form name="login-form" action="/controller?command=block_user" method="post">
            <label for="login-input">Login:</label>
            <input id="login-input" type="text" name="login" required value=""/>

    <br>
    <div>
        <label>email:</label>
        <input type="text" name="email" required pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$" value=""/>
        <br/>
    </div>
    <div>
        <button>
            <span>Block</span>
        </button>
    </div>
        </form>
    </div>

</div>
<c:if test="${not empty requestScope.errorBlockMessage}">
    <b class="error_login">${requestScope.errorBlockMessage}</b>
    <br>
</c:if>
</div>
</div>
</form>

</body>

</html>

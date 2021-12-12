<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete account</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>

</head>
<body>

<form name="delete-form" action="/controller?command=delete_account" method="post">
    <div>
        <label for="login-input">Login:</label>
        <input id="login-input" type="text" name="login" required value="${user.login}"/>
    </div>
    <br>
    <div class="password_text">
        <label for="password-input">Password:</label>
        <input id="password-input" type="password" name="password" pattern="[0-9a-zA-Z]+" maxlength=6 required
               value=""/>
        <br/>
    </div>
    <c:if test="${not empty requestScope.errorUserMessage}">
        <b >${requestScope.errorUserMessage}</b>
        <br>
    </c:if>
    <div>
        <button id="login">
            <span>Delete</span>
        </button>
    </div>
    </div>


</form>

</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>

  <title>Log in</title>
</head>
<body>
<h3>Please log in:</h3>
<form name="login-form" action="/controller?command=login" method="post">
  <label for="login-input">Login:</label>
  <input id="login-input" type="text" name="login" value=""/>
  <br>
  <label for="password-input">Password:</label>
  <input id="password-input" type="password" name="password" value=""/>
  <br/>
  <c:if test="${ empty requestScope.errorLoginPassMessage}">
    <b>${requestScope.errorLoginPassMessage}</b>
    <br>
  </c:if>

  <style>
    <%@include file="/WEB-INF/css/loginStyle.css"%>
  </style>

  <div>
    <button  id="login" class="button blue">
      <i class="fa fa-unlock"></i>
      <span>Log in</span>
    </button>
  </div>
  <%-- <input type="submit" value="Log in">--%>

</form>

</body>
</html>

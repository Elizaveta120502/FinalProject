<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Making bet</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>

</head>
<body>
<style>
<%@include file="/WEB-INF/css/makeBetStyle.css"%>
<%@include file="/WEB-INF/css/welcomebackground.css"%>
</style>
<div class="bet">
    <h3 class="main_text">Make bet</h3>

        <form  name="login-form" action="/controller?command=make_bet" method="post">
            <div class="login_style">
            <label for="login-input">Login:</label>
            <input id="login-input" type="text" name="login" required  readonly value="${user.login}"/>
    </div>
    <br>
    <div class="number">
        <label >Lot №:</label>
        <input type="number" name="id" min=1  required value=""/>
        <br/>
    </div>
    <div class="price_style">
        <label for="price-input">new current price:</label>
        <input id="price-input" type="number" name="newCurrentPrice" min=1 required value=""/>
        <br/>
    </div>
    <div>
        <button  class="bet_button" >
            <span>Make bet!</span>
        </button>
    </div>
</div>
</form>

</div>
</body>
</html>

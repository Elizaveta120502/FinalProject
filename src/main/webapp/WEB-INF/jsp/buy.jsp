<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Buy lot</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>
</head>
<body>

<style>
    <%@include file="/WEB-INF/css/buyLotStyle.css"%>
    <%@include file="/WEB-INF/css/welcomebackground.css"%>


</style>
<div class="buy">
    <h3 class="do_text">What lot do you want to buy?</h3>


    <form name="buy-form" action="/controller?command=buy_lot" method="post">
        <div class="lot_id">
            <label for="lotId">Lot №:</label>
            <input id="lotId" type="number" name="lotId" min=1 required value=""/>
        </div>
        <br>
        <div class="shipment">
            <label>Choose shipment type:</label>
            <p><input name="shipment" type="radio" value="by mail" checked>by mail</p>
            <p><input name="shipment" type="radio" value="delivery" checked>delivery</p>
            <p><input name="shipment" type="radio" value="pickup" checked>pickup</p>
            <p><input name="shipment" type="radio" value="euromail" checked>euromail</p>
        </div>
        <div class="payment">
            <label>Choose payment type:</label>
            <p><input name="payment" type="radio" value="ERIP" checked>ERIP</p>
            <p><input name="payment" type="radio" value="mastercard" checked>Mastercard</p>
            <p><input name="payment" type="radio" value="on Credit" checked>on Credit</p>
            <p><input name="payment" type="radio" value="Maestro" checked>Maestro</p>
        </div>
        <div class="account">
            <label for="account-input">Your login:</label>
            <input id="account-input" type="text" name="account" required readonly value="${user.login}"/>
        </div>
        <c:if test="${not empty requestScope.errorLotMessage}">
            <b>${requestScope.errorLotMessage}</b>
            <br>
        </c:if>
        <div>
            <button class="buy_button">
                Buy
            </button>
        </div>
    </form>

</div>


</body>

</html>

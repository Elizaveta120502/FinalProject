<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lots</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>
</head>
<body>

<style>
    <%@include file="/WEB-INF/css/common/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/lot/lotsStyle.css"%>
    <%@include file="/WEB-INF/css/lot/makeBetStyle.css"%>
</style>

<div class="main_button">
    <a class="make_bet_text" href="/controller?command=show_bet">Make bet</a>
</div>
<div class="buy_button">
    <a class="make_bet_text" href="/controller?command=show_buy">Buy lot</a>
</div>
<ul>

    <c:forEach var="lot" items="${requestScope.lots}">

        <div class="lot-wrapper">
            <div class="lot"><pre> № ${lot.id} ${lot.auctionItem.title}

  Starting price: ${lot.startingPrice}$

  Current price: ${lot.currentPrice}$

  Amount of items:${lot.itemsAmount}

  Winner: ${lot.account.login}
               </pre>

                <img class="photo" src="${lot.auctionItem.picture.pictureURL}">

            </div>

        </div>

    </c:forEach>
</ul>

</body>
</html>

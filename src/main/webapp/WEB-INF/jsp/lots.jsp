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
    <%@include file="/WEB-INF/css/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/lotsStyle.css"%>
    <%@include file="/WEB-INF/css/makeBetStyle.css"%>
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

  Starting price: ${lot.startingPrice}

  Current price: ${lot.currentPrice}$

  Items in stoke:${lot.auctionItem.inStoke}

  Winner: ${lot.account.login}
               </pre>
                <img class="photo" src="${lot.auctionItem.picture.pictureURL}">

            </div>

        </div>

    </c:forEach>
</ul>


<%--<ul class="products clearfix">--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/alstromeria.jpeg?raw=true"--%>
<%--                     alt="">--%>

<%--            </div>--%>

<%--        </div>--%>
<%--    </li>--%>

<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/begonia.webp?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/lemon-tree.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/lily1.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/gypsofila.jpeg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/rom1.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/tulip.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/roses1.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/lotus.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/lavanda2.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/kalanhoe.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/aloejpg.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/cherry blossom.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/orhid.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/sunflower.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/lavanda.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/begonia.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/marg.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/maki2.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>
<%--    <li class="lot-wrapper">--%>
<%--        <div class="lot">--%>
<%--            <div class="lot-photo">--%>
<%--                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/cornflower.jpg?raw=true"--%>
<%--                     alt="">--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </li>--%>


<%--</ul>--%>
</body>
</html>

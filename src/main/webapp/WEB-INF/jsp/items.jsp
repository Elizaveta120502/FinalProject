<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auction items</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>

</head>
<body>
<style>
    <%@include file="/WEB-INF/css/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/itemsListStyle.css"%>
</style>
<div>
    <c:forEach var="aitem" items="${requestScope.auction_items}">
        <div class="item-wrapper">
            <div class="item">
                Title: ${aitem.title}
                Price: ${aitem.price}$<br>
                Amount of items: ${aitem.inStoke}
                <img class="item_picture" src="${aitem.picture.pictureURL}">
            </div>
        </div>
        </div>
    </c:forEach>
</div>
</body>
</html>

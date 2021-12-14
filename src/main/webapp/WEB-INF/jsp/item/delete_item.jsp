<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Delete auction item</title>
<link rel="shortcut icon" type="image/png"
      href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>
</head>
<body>
<style>
    <%@include file="/WEB-INF/css/item/deleteItemStyle.css"%>
    <%@include file="/WEB-INF/css/common/welcomebackground.css"%>
</style>
<div class="delete_item">
    <form name="item-form" action="/controller?command=delete_item" method="post">
        <div class="delete_item_text">
            <label for="delete_item">Enter item №:</label>
            <input id="delete_item" type="number" name="id" min="1" required value=""/>
        </div>

        <div>
            <button class="delete_button_item">
                <span>Delete
                <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/icon-sunflower.png?raw=true"></span>
            </button>
        </div>
        <c:if test="${not empty requestScope.errorItemMessage}">
        <b>${requestScope.errorItemMessage}</b>
        <br>
        </c:if>
</div>

</body>
</html>

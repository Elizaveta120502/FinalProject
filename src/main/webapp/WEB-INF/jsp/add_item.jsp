<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding auction item</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>

</head>
<form>
    <style>
        <%@include file="/WEB-INF/css/welcomebackground.css"%>
        <%@include file="/WEB-INF/css/addLotStyle.css"%>
    </style>

    <div class="add_lot_div">
        <form name="login-form" action="/controller?command=add_item" method="post">
            <div class="add_lot_text">
                <div>
                    <label for="add_item">Title:</label>
                    <input id="add_item" type="text" name="title" required value=""/>
                </div>
                <br>
                <div>
                    <label for="price_input">Item price:</label>
                    <input id="price_input" type="number" name="price" min="1" required value=""/>
                </div>
                <br/>
                <br>
                <div>
                    <label for="amount_input">Enter amount of items:</label>
                    <input id="amount_input" type="number" name="inStock" min="1" required value=""/>
                </div>
                <br/>
                <div>
                    <label for="url_input">Enter url of picture:</label>
                    <input id="url_input" type="url" name="pictureURL" required value=""/>
                </div>
                <div>
                    <button class="create_lot_button">

                        <span>Add
                            <br><img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/icon-daisy.png?raw=true">
                        </span>

                    </button>
                </div>
                <c:if test="${not empty requestScope.errorItemMessage}">
                    <b>${requestScope.errorItemMessage}</b>
                    <br>
                </c:if>
            </div>
    </div>
</form>

</body>
</html>

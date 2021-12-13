<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create lot</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>

</head>
<body>
<form name="create-lot-form" action="/controller?command=create_lot" method="post">


    <div>
        <label for="price_input">Starting price:</label>
        <input id="price_input" type="number" name="startingPrice" min="1" required value=""/>
    </div>

    <br>
    <div>
        <label for="amount_input">Enter amount of items:</label>
        <input id="amount_input" type="number" name="itemsAmount" min="1" required value=""/>
    </div>
    <br/>
    <div>
        <label for="item_name_input">Enter auction item name:</label>
        <input id="item_name_input"  name="auctionItem" type="text" required value=""/>
    </div>
    <br>
    <div>
        <label for="url_input">Your login:</label>
        <input id="url_input" type="text" name="login" readonly required value="${user.login}"/>
    </div>
    <c:if test="${not empty requestScope.errorLotCreateMessage}">
        <b>${requestScope.errorLotCreateMessage}</b>
        <br>
    </c:if>
    <c:if test="${not empty requestScope.successLotCreateMessage}">
        <b>${requestScope.successLotCreateMessage}</b>
        <br>
    </c:if>
    <div>
        <button>
            <span>Create lot</span>
        </button>
    </div>

</form>
</body>
</html>

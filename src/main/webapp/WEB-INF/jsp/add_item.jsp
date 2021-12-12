<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding auction item</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>
</head>
<body>

<div>
    <form name="login-form" action="/controller?command=add_item" method="post">
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
            <input id="url_input" type="url" name="pictureURL"  required value=""/>
        </div>
        <div>
            <button>
                <span>Add</span>
            </button>
        </div>
    <c:if test="${not empty requestScope.errorItemMessage}">
        <b>${requestScope.errorItemMessage}</b>
        <br>
    </c:if>
</div>

</body>
</html>

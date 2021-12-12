<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Delete auction item</title>
<link rel="shortcut icon" type="image/png"
      href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>
</head>
<body>

<div>
    <form name="item-form" action="/controller?command=delete_item" method="post">
        <div>
            <label for="delete_item">Enter lot №:</label>
            <input id="delete_item" type="number" name="id"  min="1"  required value=""/>
        </div>

        <div>
            <button>
                <span>Delete</span>
            </button>
        </div>
        <c:if test="${not empty requestScope.errorItemMessage}">
        <b>${requestScope.errorItemMessage}</b>
        <br>
        </c:if>
</div>

</body>
</html>

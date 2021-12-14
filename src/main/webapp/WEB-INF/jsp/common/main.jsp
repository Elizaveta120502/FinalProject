<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jwds" uri="jwd.epam.com" %>
<%@ page import="com.epam.jwd.model.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cherry Blossom</title>
    <link rel="shortcut icon" type="image/png"
          href="https://img.icons8.com/external-konkapp-flat-konkapp/50/000000/external-sakura-japan-konkapp-flat-konkapp.png"/>

</head>
<body>

<style>
    <%@include file="/WEB-INF/css/common/welcomebackground.css"%>
    <%@include file="/WEB-INF/css/common/mainStyle.css"%>
    <%@include file="/WEB-INF/css/common/navigateMenu.css"%>
    <%@include file="/WEB-INF/css/common/deleteAccountMenuLine.css"%>
</style>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <c:if test="${not empty sessionScope.user }">
        <a href="/controller?command=show_lots">Lots</a>
    </c:if>
    <c:if test="${not empty sessionScope.user}">
        <a href="/controller?command=show_items">Auction items</a>
    </c:if>
    <c:if test="${not empty sessionScope.user}">
        <a href="/controller?command=show_add_item">Add product</a>
    </c:if>
    <c:choose>
        <c:when test="${not empty sessionScope.user && sessionScope.user.role eq Role.ADMINISTRATOR}">

            <a href="/controller?command=show_users">Users list</a>
            <a href="/controller?command=show_delete_item">Delete product</a>
            <a href="/controller?command=show_delete_lot">Delete lot</a>
            <a href="/controller?command=show_shipments">All shipments</a>
        </c:when>
        <c:when test="${not empty sessionScope.user && sessionScope.user.role eq Role.CLIENT}">
            <a href="/controller?command=show_create_lot">Create lot</a>
        </c:when>
    </c:choose>

    <c:if test="${not empty sessionScope.user}">
        <div class="delete_account_line">
            <a href="/controller?command=show_delete_account">Delete account</a>
        </div>
    </c:if>
</div>
<span class="menu" onclick="openNav()">Menu❀</span>


<c:choose>
    <c:when test="${not empty sessionScope.user}">

        <div>
            <a id="logout" class="button button1" href="/controller?command=logout">
                <i class="fa fa-unlock"></i>
                <span>Log out</span>
            </a>
        </div>

    </c:when>
    <c:otherwise>
<span>
        <a id="login" class="button button1" href="/controller?command=show_login">Sign in</a>
        <a id="registration" class="button button2" href="/controller?command=show_registration">Registration</a>
</span>
    </c:otherwise>
</c:choose>

<div class="welcome_picture">
    <img width="450" height="450"
         src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/land2.jpg?raw=true">
</div>

<div class="welcome">
    Welcome to our flower auction!
    The company <span style="color:#B22222">Cherry Blossom</span> is always glad to new customers.
    We have a wide range of products. If you have any difficulties,
    our administrators will certainly help you.
    We wish you good luck with the auction.
    <div class="welcome_remark"> Life smells like cherry blossom.</div>
</div>

<div class="start">
    <c:choose>
        <c:when test="${empty sessionScope.user }">
            <div class="start_text">
                To start you need to register or sign in
            </div>
        </c:when>
        <c:when test="${not empty sessionScope.user && sessionScope.user.role eq Role.ADMINISTRATOR}">

            <img src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/icon-checked-user.png?raw=true">
            (${Role.ADMINISTRATOR.name().toLowerCase()})
            <jwds:welcomeUser text="Hello"/>

        </c:when>
        <c:when test="${not empty sessionScope.user && sessionScope.user.role eq Role.CLIENT }">

            <img height="30px"
                 src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/icon-checked-user.png?raw=true">
            (${Role.CLIENT.name().toLowerCase()})
            <jwds:welcomeUser text="Hello"/>
            </br>
        </c:when>
    </c:choose>

</div>


<div class="contacts">
    <img width="30" length="30"
         src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/icon-telephone.png?raw=true"/>+375
    (44) 720-12-81
    <br>
    <img width="30" length="30"
         src="https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/icon-address.png?raw=true">
    Minsk, st. Academician Kuprevich, 25/1
    <br>
    <img width="30" length="30"
         src='https://github.com/Elizaveta120502/PicturesFinalProject/blob/master/pictures/icon-inst.png?raw=true'/>
    <a class="inst" href="https://www.instagram.com/cherry_blossom.cool/">cherry_blossom.cool</a>
</div>

<script>
    function openNav() {
        document.getElementById("mySidenav").style.display = "block";
    }

    function closeNav() {
        document.getElementById("mySidenav").style.display = "none";
    }
</script>


</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Добавление пользователей</title>
</head>
<body>
<h1>Добавление пользователей (${usersCount})</h1>

<form action="/save" method="post">
    <c:forEach var="i" begin="0" end="${usersCount - 1}">
        <p>
            Пользователь ${i + 1}:<br>
            <input type="text" name="firstName_${i}" placeholder="Имя"><br>
            <input type="text" name="lastName_${i}" placeholder="Фамилия"><br>
            <input type="email" name="email_${i}" placeholder="Email"><br>
        </p>
    </c:forEach>

    <button type="submit">Сохранить</button>
</form>
</body>
</html>
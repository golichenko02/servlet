<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="/auth">
    <table>
        <tr>
            <td>
                Имя: <input type="text" name="usernameParam">
            </td>
            <td>
                Пароль: <input type="password" name="passwordParam">
            </td>
        </tr>
    </table>
    <input type="submit" value="Вход">
</form>
</body>
</html>

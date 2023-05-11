<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        html{
            background-color: #FFFFFF;
        }
    </style>
    <script src="<c:url value="/js/fiber_page_change_colors.js"/>"></script>
    <title>admin</title>
</head>
<body>
    <form method="post" action="/admin.auth">
        <input type="text" placeholder="login" name="login"
               style="padding: 10px; margin-bottom: 10px">
        <br>
        <input type="password" placeholder="password" name="password"
               style="padding: 10px; margin-bottom: 10px">
        <br>
        <input type="submit" value="submit" style="padding: 10px">
    </form>
</body>
</html>

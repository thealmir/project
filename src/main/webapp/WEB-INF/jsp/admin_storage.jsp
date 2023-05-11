<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script src="<c:url value="/js/IdError.js"/>"></script>
    <script src="<c:url value="/js/fiber_page_change_colors.js"/>"></script>
    <script src="<c:url value="/js/delete_fiber.js"/>"></script>

    <title>admin</title>
    <style>
        html{
            background-color: #FFFFFF;
        }
    </style>
</head>
<body>

<form id="fiber-id">
    <input type="number" placeholder="id" name="id" style="padding: 10px; margin-bottom: 5px" required/>
</form>
<button id="delete-button" style="padding: 10px; margin-bottom: 10px">delete</button>

<table id="fibers_list">
    <tr>
        <th>id</th>
        <th>creation_date</th>
        <th>section</th>
        <th>comment_to</th>
    </tr>
    <c:forEach items="${fibers}" var="fiber">
        <tr>
            <td> ${fiber.getId()} </td>
            <td> ${fiber.creationDateToString()}</td>
            <td> ${fiber.getSection()}</td>
            <td> ${fiber.getCommentTo()}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>

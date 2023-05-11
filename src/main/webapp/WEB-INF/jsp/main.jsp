<%@ page import="ru.fedusiv.models.Fiber" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.fedusiv.models.File" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script src="<c:url value="/js/fiber_page_change_colors.js"/>"></script>
    <script src="<c:url value="/js/fiber_page_open_close_buttons.js"/>"></script>
    <script src="<c:url value="/js/create_thread.js"/>"></script>
    <script src="<c:url value="/js/audio_player.js"/>"></script>
    <script src="<c:url value="/js/get_date.js"/>"></script>
    <script src="<c:url value="/js/fibers_composer.js"/>"></script>

    <style><%@include file="/css/style_main_page.css"%></style>

    <title>Fibers</title>
</head>
<body>
    <h5 class="page_title">Fibers</h5>

    <div class="buttons">
        <button class="modal-open item_1" data-id="change-colors">Reverse colors</button>
        <button class="modal-open item_16" data-id="new-fiber-modal">New Fiber</button>
    </div>

    <div id="new-fiber-modal" class="modal" role="dialog" tabindex="-1">
        <div class="modal-inner">
            <div class="modal-header">
                <h1 class="modal-title">New Fiber</h1>
            </div>
            <form id="new_fiber">
                <input class="input" name="section" placeholder="Text" type="text" required/>
                <br>
                <input type="file" name="files" multiple>
            </form>
            <br>
            <button id="create-button" class="modal-create">Create</button>
            <button class="modal-close" data-id="new-fiber-modal">Close</button>
        </div>
    </div>

    <div class="container" id="fibers">
        <c:forEach items="${fibers}" var="fiber">
            <div class="item">
                ${fiber.creationDateToString()}
                #${fiber.getId()}
                <br>
                <c:if test="${fiber.getFiles() != null}">
                    <c:forEach items="${fiber.getFiles()}" var="file">
                        <c:choose>
                            <c:when test = "${file.getType().equals(\"jpg\") || file.getType().equals(\"png\")}">
                                <img class="image" src="<c:url value="/file?name=${file.getName()}"/>" loading="lazy"/>
                            </c:when>
                            <c:when test = "${file.getType().equals(\"mp3\")}">
                                <div class="audio" id="${file.getId()}">
                                        ${file.getClearName()}
                                </div>
                            </c:when>
                            <c:when test = "${file.getType().equals(\"mp4\")}">
                                <video class="video" controls loading="lazy">
                                    <source src="<c:url value="/file?name=${file.getName()}"/>" type="video/mp4">
                                </video>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </c:if>
                <a href="<c:url value="/fiber?fiber_id=${fiber.getId()}"/>" target="_self">
                    <br>${fiber.getSection()}
                </a>
            </div>
        </c:forEach>
    </div>
</body>
</html>

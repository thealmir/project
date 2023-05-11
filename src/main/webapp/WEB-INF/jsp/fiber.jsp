<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>

    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <style><%@include file="/css/style_fiber_page.css"%></style>
    <title>Fiber</title>
    <script src="<c:url value="/js/IdError.js"/>"></script>
    <script src="<c:url value="/js/audio_player.js"/>"></script>
    <script src="<c:url value="/js/fiber_page_open_close_buttons.js"/>"></script>
    <script src="<c:url value="/js/fiber_page_change_colors.js"/>"></script>
    <script src="<c:url value="/js/create_fiber.js"/>"></script>
    <script src="<c:url value="/js/fibers_composer.js"/>"></script>

</head>
<body>
<h5 class="page_title">Fiber</h5>

<div class="buttons">
    <button class="modal-open item_1" data-id="change-colors">Reverse colors</button>
    <button class="modal-open item_16" data-id="new-fiber-modal">New Fiber</button>
</div>

<div id="new-fiber-modal" class="modal" role="dialog" tabindex="-1">
    <div class="modal-inner">
        <div class="modal-header">
            <h1 class="modal-title">New Fiber</h1>
        </div>
        <form id="new_comment">
            <input class="input" name="comment_to" placeholder="Comment to" type="text" required/>
            <br>
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
    <div class="opening-fiber">
        ${fiber.creationDateToString()}
        #${fiber.getId()}

        <c:if test="${fiber.getFiles() != null}">
            <br>
            <c:forEach items="${fiber.getFiles()}" var="file">
                <c:choose>
                    <c:when test = "${file.getType().equals(\"jpg\") || file.getType().equals(\"png\")}">
                        <img src="<c:url value="/file?name=${file.getName()}"/>" width="250" height="200"
                             style="margin-bottom: 5px; cursor: pointer" loading="lazy"/>
                    </c:when>
                    <c:when test = "${file.getType().equals(\"mp3\")}">
                        <div class="audio" id="${file.getId()}" style="color:purple; margin-bottom: 5px">
                                ${file.getClearName()}
                        </div>
                    </c:when>
                    <c:when test = "${file.getType().equals(\"mp4\")}">
                        <video width="250" height="200" controls loading="lazy">
                            <source src="<c:url value="/file?name=${file.getName()}"/>" type="video/mp4">
                        </video>
                    </c:when>
                </c:choose>
            </c:forEach>
        </c:if>
        <br>
        ${fiber.getSection()}
    </div>
    <div class="container" >
        <c:forEach items="${comments}" var="comment">
            <div class="item">
                ${comment.creationDateToString()}
                #${comment.getId()}
                <c:if test="${comment.getFiles() != null}">
                    <br>
                    <c:forEach items="${comment.getFiles()}" var="file">
                        <c:choose>
                            <c:when test = "${file.getType().equals(\"jpg\") || file.getType().equals(\"png\")}">
                                <img src="<c:url value="/file?name=${file.getName()}"/>" width="250" height="200"
                                     style="margin-bottom: 5px; cursor: pointer" loading="lazy"/>
                            </c:when>
                            <c:when test = "${file.getType().equals(\"mp3\")}">
                                <div class="audio" id="${file.getId()}" style="color:purple; margin-bottom: 5px">
                                        ${file.getClearName()}
                                </div>
                            </c:when>
                            <c:when test = "${file.getType().equals(\"mp4\")}">
                                <video width="250" height="200" controls loading="lazy">
                                    <source src="<c:url value="/file?name=${file.getName()}"/>" type="video/mp4">
                                </video>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </c:if>
                <br>
                ${comment.getSection()}
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
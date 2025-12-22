<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    </head>
    <body style="margin: 0; padding: 0.5rem;">

        <c:if test="${empty horarios}">
            <p>Este monitor não cadastrou horários.</p>
        </c:if>

        <ul>
            <c:forEach var="h" items="${horarios}">
                <li>
                    <strong>${h.diaSemana}</strong><br>
                    ${h.horaInicio} - ${h.horaTermino}
                    <small>(${h.duracaoMedia} min)</small>
                </li>
            </c:forEach>
        </ul>
    </body>
</html>
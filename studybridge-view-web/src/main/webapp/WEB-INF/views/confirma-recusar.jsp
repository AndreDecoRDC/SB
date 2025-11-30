<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Confirmar Recusa</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    </head>
    <body>
        <div class="container" style="max-width:600px;margin:4rem auto;">
            <div class="card">
                <h3>Confirmar Recusa</h3>
                <p>Você está prestes a recusar a solicitação:</p>
                <ul>
                    <li><b>ID da solicitação:</b> ${param.idAula}</li>
                    <li><b>Disciplina:</b> ${aula.disciplina}</li>
                    <li><b>Data solicitada:</b> ${aula.data_aula}</li>
                </ul>

                <form action="${pageContext.request.contextPath}/monitor/aula/recusar" method="post">
                    <input type="hidden" name="idAula" value="${param.idAula}" />
                    <div class="toolbar" style="justify-content:center;">
                        <button class="btn" type="submit">Sim, recusar</button>
                        <a class="btn ghost" href="${pageContext.request.contextPath}/monitor/aulas">Voltar</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>

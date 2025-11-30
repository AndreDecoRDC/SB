<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Confirmar Cancelamento</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    </head>
    <body>
        <div class="container" style="max-width:600px;margin:4rem auto;">
            <div class="card">
                <h3>Confirmar Cancelamento</h3>
                <p>Você está prestes a cancelar a aula:</p>
                <ul>
                    <li><b>ID da aula:</b> ${param.idAula}</li>
                    <li><b>Disciplina:</b> ${aula.disciplina}</li>
                    <li><b>Data:</b> ${aula.data_aula}</li>
                </ul>

                <form action="${pageContext.request.contextPath}/estudante/aula/cancelar" method="post">
                    <input type="hidden" name="idAula" value="${param.idAula}" />
                    <div class="toolbar" style="justify-content:center;">
                        <button class="btn" type="submit">Sim, cancelar aula</button>
                        <a class="btn ghost" href="${pageContext.request.contextPath}/estudante/aulas">Voltar</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>

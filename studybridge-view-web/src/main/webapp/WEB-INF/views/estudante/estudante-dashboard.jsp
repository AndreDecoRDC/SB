<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="pt-BR">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Dashboard do Estudante — StudyBridge</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    </head>

    <body>

        <header class="header">
            <a href="${pageContext.request.contextPath}/estudante/dashboard" class="brand">
                <div class="logo">SB</div><strong>StudyBridge</strong>
            </a>
            <nav class="nav">
                <a class="btn" href="${pageContext.request.contextPath}/estudante/busca">Buscar Monitores</a>
                <a class="btn" href="${pageContext.request.contextPath}/estudante/aulas-estudante">Aulas</a>
                <a class="btn" href="${pageContext.request.contextPath}/estudante/perfil">Perfil</a>

                <a class="notif" href="#">
                    <img src="${pageContext.request.contextPath}/images/notifications.svg" alt="Notificações">
                </a>
                <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
            </nav>
        </header>

        <main class="container">
            <h2>Bem-vindo(a), Estudante</h2>
            <p class="subtle">Gerencie suas aulas e monitores no CEFET-MG — Campus Belo Horizonte.</p>

            <div class="grid-3">

                <div class="card">
                    <h3>Próxima Aula</h3>
                    <c:choose>
                        <c:when test="${not empty proximaAula}">
                            <p><b>${proximaAula.disciplina} com ${proximaAula.nomeUsuarioAssociado}</b></p>
                            <p>${proximaAula.dataAulaFormatada}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="subtle">Nenhuma aula marcada.</p>
                        </c:otherwise>
                    </c:choose>
                    <a class="btn" href="${pageContext.request.contextPath}/estudante/aulas-estudante">Ver aulas marcadas</a>
                </div>

                <div class="card">
                    <h3>Monitor Recomendado</h3>
                    <c:choose>
                        <c:when test="${not empty monitorBom}">
                            <p><b>${monitorBom.disciplina} com ${monitorBom.nome}</b></p>
                            <p>Nota média: <b>${monitorBom.mediaAvaliacao}</b></p>
                        </c:when>
                        <c:otherwise>
                            <p class="subtle">Ainda não temos recomendações.</p>
                        </c:otherwise>
                    </c:choose>
                    <a class="btn" href="${pageContext.request.contextPath}/estudante/busca">Buscar Monitores</a>
                </div>

                <div class="card">
                    <h3>Histórico de Aulas</h3>
                    <c:choose>
                        <c:when test="${not empty ultimaAula}">
                            <p><b>Última aula:</b> ${ultimaAula.disciplina} com ${ultimaAula.nomeUsuarioAssociado}</p>
                            <p>Data: ${ultimaAula.dataAulaFormatada}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="subtle">Você não concluiu nenhuma aula.</p>
                        </c:otherwise>
                    </c:choose>
                    <a class="btn" href="#">Ver histórico completo</a>
                </div>

            </div>
        </main>

<footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>

</body>
</html>

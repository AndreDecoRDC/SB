<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Editar Horários | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<style>

    main.container {
        flex: 1;
    }

    .footer {
        margin-top: auto;
    }

</style>

<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="#">Aulas</a>
        <a class="btn" href="${pageContext.request.contextPath}/horarios">Horários</a>
        <a class="btn" href="#">Perfil</a>
        <a class="btn" href="${pageContext.request.contextPath}/">Sair</a>
    </nav>
</header>

<main class="container" style="max-width: 600px; margin-top: 2rem;">
    <h2 style="text-align:center;">Editar ou Remover Horários</h2>

    <c:if test="${not empty param.sucesso}">
        <div class="alert success">${param.sucesso}</div>
    </c:if>


    <c:if test="${not empty erro}">
        <div class="alert error" style="color: red">${erro}</div>
    </c:if>

    <div class="card" style="margin-top:2rem;">
        <h3>Seus Horários</h3>
        <ul class="subtle" style="margin-top:0.8rem; line-height:1.6;">
            <c:forEach var="h" items="${horarios}">
                <li>
                    <b>${h.diaSemana}:</b> ${h.horaInicio} — ${h.horaTermino} · ${h.duracaoMedia} min
                    <div style="margin-top:0.5rem; display:flex; gap:0.5rem;">
                        <form action="${pageContext.request.contextPath}/editar-horario" method="get" style="display:inline;">
                            <input type="hidden" name="id" value="${h.id}">
                            <button type="submit" class="btn ghost">Editar</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/confirmar-remocao" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${h.id}">
                            <button type="submit" class="btn ghost">Excluir</button>
                        </form>
                    </div>
                </li>
            </c:forEach>

            <c:if test="${empty horarios}">
                <li>Nenhum horário cadastrado.</li>
            </c:if>
        </ul>
        <div class="toolbar" style="display:flex; justify-content:center; margin-top:1.5rem;">
            <a href="${pageContext.request.contextPath}/horarios" class="btn ghost">Voltar</a>
        </div>
    </div>
</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

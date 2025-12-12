<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Dashboard do Monitor — StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>

<body>

<header class="header">
    <a href="${pageContext.request.contextPath}/monitor/dashboard" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="${pageContext.request.contextPath}/monitor/aulas-monitor">Aulas</a>
        <a class="btn" href="#">Horários</a>
        <a class="btn" href="${pageContext.request.contextPath}/monitor/perfil">Perfil</a>
        <a class="notif" href="#">
            <img src="${pageContext.request.contextPath}/images/notifications.svg" alt="Notificações">
        </a>
        <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
    </nav>
</header>

<main class="container">
    <h2>Bem-vindo(a), Monitor</h2>
    <p class="subtle">Gerencie suas monitorias — CEFET-MG Campus BH</p>

    <div class="grid-3">

        <div class="card">
            <h3>Próxima Aula</h3>
            <p><b>Com Maria Souza</b> — Física</p>
            <p>18/10/2025 às 15:00</p>
            <a class="btn" href="${pageContext.request.contextPath}/monitor/aulas-monitor">Ver aulas</a>
        </div>

        <div class="card">
            <h3>Ver Horários</h3>
            <p>Próximo horário: <b>14:00</b></p>
            <a class="btn" href="#">Ver Horários</a>
        </div>

    </div>
</main>

<footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>

</body>
</html>

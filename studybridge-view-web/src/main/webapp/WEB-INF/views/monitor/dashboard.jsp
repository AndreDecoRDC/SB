<%@ page import="com.studybridge.domain.model.Aula" %>
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
        <a class="btn" href="${pageContext.request.contextPath}/monitor/horarios">Horários</a>
        <a class="btn" href="${pageContext.request.contextPath}/monitor/perfil">Perfil</a>
        <a class="notif" href="#notifPanel">
            <img src="Imagens/notifications_24dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Notificações">
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
            <%
                Aula proxima = (Aula) request.getAttribute("proximaAula");
                if (proxima == null) {
            %>
            <p>Nenhuma aula agendada.</p>
            <%
            } else {
            %>
            <p><b><%= proxima.getId_estudante() %></b> — <%= proxima.getDisciplina() %></p>
            <p>
                <%= proxima.getData_aula().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")
                ) %>
            </p>
            <a class="btn" href="${pageContext.request.contextPath}/monitor/aulas">Ver aulas</a>
            <%
                }
            %>
        </div>


        <div class="card">
            <h3>Ver Horários</h3>

            <%
                if (proxima == null) {
            %>

            <p>Nenhum horário cadastrado.</p>

            <%
            } else {
            %>

            <p>Próximo horário: <b><%= proxima.getData_aula().toLocalTime() %></b></p>
            <a class="btn" href="${pageContext.request.contextPath}/monitor/aulas">Ver Horários</a>

            <%
                }
            %>
        </div>


    </div>
</main>

<footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>

</body>
</html>

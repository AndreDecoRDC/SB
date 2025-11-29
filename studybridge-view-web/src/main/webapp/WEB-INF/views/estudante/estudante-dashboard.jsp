<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
        <a class="btn" href="#">Buscar Monitores</a>
        <a class="btn" href="#">Aulas</a>
        <a class="btn" href="#">Perfil</a>
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
            <p><b>Matemática com Carla Mendes</b></p>
            <p>15/10/2025 às 14:00</p>
            <a class="btn" href="#">Ver aulas marcadas</a>
        </div>

        <div class="card">
            <h3>Monitores Recomendados</h3>
            <p><b>Física com Rafael Costa</b></p>
            <p>Nota média: <b>4.7</b></p>
            <a class="btn" href="#">Buscar Monitores</a>
        </div>

        <div class="card">
            <h3>Histórico de Aulas</h3>
            <p><b>Última aula:</b> Programação com Ana Paula</p>
            <p>05/10/2025 — Avaliação: ⭐ 4.5</p>
            <a class="btn" href="#">Ver histórico completo</a>
        </div>

    </div>
</main>

<footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>

</body>
</html>

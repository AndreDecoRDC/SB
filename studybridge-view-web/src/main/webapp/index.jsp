<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>StudyBridge — CEFET-MG Campus BH</title>
    <link rel="stylesheet"href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/index.jsp" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn ghost" href="${pageContext.request.contextPath}/login.jsp">Entrar</a>
        <a class="btn" href="${pageContext.request.contextPath}/register.jsp">Registrar</a>
    </nav>
</header>

<section class="hero">
    <img src="https://cdn-icons-png.flaticon.com/512/1041/1041916.png" alt="StudyBridge">
    <h1>Conecte estudantes e monitores<br>do CEFET-MG — Campus Belo Horizonte</h1>
    <p>O StudyBridge é uma plataforma interna que conecta alunos e monitores voluntários do CEFET-MG para reforço e apoio nas disciplinas do ensino médio.</p>
    <div class="toolbar" style="display:flex;justify-content:center;gap:1rem;">
        <a class="btn" href="${pageContext.request.contextPath}/register.jsp">Começar agora</a>
        <a class="btn ghost" href="${pageContext.request.contextPath}/login.jsp">Já tenho conta</a>
    </div>
</section>

<main class="container">
    <div class="features">
        <div class="feature">
            <h3>Apoio entre colegas</h3>
            <p>Encontre monitores do CEFET-MG dispostos a te ajudar nas matérias em que você tem mais dificuldade.</p>
        </div>
        <div class="feature">
            <h3>Seja monitor voluntário</h3>
            <p>Cadastre-se para oferecer reforço nas disciplinas que você domina e ajudar outros estudantes do campus.</p>
        </div>
        <div class="feature">
            <h3>Aprendizado colaborativo</h3>
            <p>As avaliações e comentários fortalecem o espírito de cooperação e a qualidade das monitorias no CEFET-BH.</p>
        </div>
    </div>
</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

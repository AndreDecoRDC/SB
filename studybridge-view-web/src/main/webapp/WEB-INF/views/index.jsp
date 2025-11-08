<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>StudyBridge — CEFET-MG Campus BH</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="${pageContext.request.contextPath}/register">Registrar</a>
        <a class="btn ghost" href="${pageContext.request.contextPath}/login">Entrar</a>
    </nav>
</header>

<section class="hero">
    <img src="https://cdn-icons-png.flaticon.com/512/1041/1041916.png" alt="StudyBridge">
    <h1>Conecte estudantes e monitores<br>do CEFET-MG — Campus Belo Horizonte</h1>
    <p>O StudyBridge é uma plataforma interna que conecta alunos e monitores voluntários do CEFET-MG para reforço e apoio nas disciplinas do ensino médio.</p>
    <div class="toolbar" style="display:flex;justify-content:center;gap:1rem;">
        <a class="btn" href="${pageContext.request.contextPath}/register">Começar agora</a>
        <a class="btn ghost" href="${pageContext.request.contextPath}/login">Já tenho conta</a>
    </div>
</section>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

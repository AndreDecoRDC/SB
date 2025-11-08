<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Erro na Confirmação | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="text-align:center; padding-top:3rem;">
    <h2 style="color:#dc2626;">❌ Erro ao confirmar e-mail</h2>

    <% String erro = (String) request.getAttribute("erro");
        if (erro != null) { %>
    <p style="margin-top:1rem; color:#555;"><%= erro %></p>
    <% } else { %>
    <p style="margin-top:1rem; color:#555;">Ocorreu um erro desconhecido ao tentar confirmar sua conta.</p>
    <% } %>

    <div style="margin-top:2rem;">
        <a class="btn" href="${pageContext.request.contextPath}/">Voltar ao início</a>
    </div>
</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

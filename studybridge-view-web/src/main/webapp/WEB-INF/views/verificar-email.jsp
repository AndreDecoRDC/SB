<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Verificação de E-mail — StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="max-width:480px;">
    <div class="card" style="text-align:center;">
        <h2>Confirme seu e-mail</h2>
        <p class="subtle">
            Enviamos um link de confirmação para <b>${requestScope.email}</b>.<br>
            Verifique sua caixa de entrada e clique no link para ativar sua conta.
        </p>

        <div style="margin-top:1.5rem;">
            <form action="${pageContext.request.contextPath}/reenviar-email" method="post">
                <input type="hidden" name="email" value="${requestScope.email}">
                <button type="submit" class="btn ghost">Reenviar e-mail</button>
            </form>
        </div>

        <p class="subtle" style="margin-top:1rem;">
            Após confirmar o e-mail, você poderá completar seu perfil e começar a usar o StudyBridge.
        </p>
    </div>
</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

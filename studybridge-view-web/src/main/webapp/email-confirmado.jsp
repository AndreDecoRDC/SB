<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>E-mail Confirmado | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="header">
    <a href="index.jsp" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="text-align:center; padding-top:3rem;">
    <h2>✅ E-mail confirmado com sucesso!</h2>
    <p style="margin-top:1rem;">Sua conta já foi ativada e agora você pode acessar o StudyBridge normalmente.</p>

    <%-- mostra o tipo de conta caso o servlet tenha enviado (só pra curiosidade do usuário) --%>
    <%
        String tipoConta = (String) request.getAttribute("tipoConta");
        if (tipoConta != null) {
    %>
    <p style="margin-top:0.5rem; color:#555;">Tipo de conta: <b><%= tipoConta %></b></p>
    <% } %>

    <div style="margin-top:2rem;">
        <a class="btn" href="login.jsp">Fazer login</a>
    </div>
</main>

<footer class="footer">
    © 2025 StudyBridge. Todos os direitos reservados.
</footer>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Criar nova senha — StudyBridge</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>

<body>
<header class="header">
    <a href="<%= request.getContextPath() %>/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="max-width:420px;">

    <div class="card">

        <h2>Criar nova senha</h2>
        <p class="subtle">Digite sua nova senha para concluir a redefinição.</p>

        <% if (request.getAttribute("erro") != null) { %>
        <p class="error"><%= request.getAttribute("erro") %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/nova-senha-action" method="post">

            <!-- token escondido -->
            <input type="hidden" name="token" value="<%= request.getAttribute("token") %>" />

            <label class="field">
                <span>Nova senha</span>
                <input type="password" class="input" name="senha1" minlength="6" required>
            </label>

            <label class="field">
                <span>Confirmar senha</span>
                <input type="password" class="input" name="senha2" minlength="6" required>
            </label>

            <div style="text-align:center;margin-top:1rem;">
                <button class="btn" type="submit">Redefinir senha</button>
                <a class="btn ghost" href="<%= request.getContextPath() %>/login">Cancelar</a>
            </div>
        </form>

    </div>
</main>

<footer class="footer">
    © 2025 StudyBridge. Todos os direitos reservados.
</footer>
</body>
</html>

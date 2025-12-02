<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Redefinir Senha — StudyBridge</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css" />
</head>
<body>
<header class="header">
    <a href="<%= request.getContextPath() %>/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="max-width:420px;">
    <div class="card">

        <h2>Redefinir senha</h2>
        <p class="subtle">Digite o e-mail cadastrado para receber o link de redefinição.</p>

        <% if (request.getAttribute("msg") != null) { %>
        <p class="success"><%= request.getAttribute("msg") %></p>
        <% } %>

        <% if (request.getAttribute("erro") != null) { %>
        <p class="error"><%= request.getAttribute("erro") %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/redefinir-senha-action" method="post">
            <label class="field">
                <span>E-mail</span>
                <input type="email" name="email" class="input" required placeholder="seu@email.com">
            </label>

            <div style="text-align:center;margin-top:1rem;">
                <button class="btn" type="submit">Enviar link</button>
                <a class="btn ghost" href="<%= request.getContextPath() %>/login">Voltar</a>
            </div>
        </form>

    </div>
</main>

<footer class="footer">
    © 2025 StudyBridge. Todos os direitos reservados.
</footer>
</body>
</html>

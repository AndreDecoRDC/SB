<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Login — StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="max-width:420px;">
    <h2>Entrar</h2>
    <p class="subtle">Acesse com seu e-mail</p>

    <%-- MENSAGEM DE SUCESSO DEPOIS DA REDEFINIÇÃO --%>
    <% if ("1".equals(request.getParameter("senhaRedefinida"))) { %>
    <p class="success" style="text-align:center; margin-bottom:1rem;">
        Sua senha foi redefinida com sucesso! Faça login para continuar.
    </p>
    <% } %>

    <form class="card" method="post" action="${pageContext.request.contextPath}/autenticar">

        <label class="field">
            <span>Email</span>
            <input class="input" type="email" name="email"
                   value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                   placeholder="exemplo@gmail.com" required>
        </label>

        <label class="field">
            <span>Senha</span>
            <input class="input" type="password" name="senha"
                   value="<%= request.getAttribute("senha") != null ? request.getAttribute("senha") : "" %>"
                   placeholder="Digite sua senha" required>
        </label>

        <div class="toolbar">
            <button class="btn" type="submit">Entrar</button>
        </div>

        <%-- ERRO DO LOGIN --%>
        <% if (request.getAttribute("erro") != null) { %>
        <p style="color:#c62828; margin-top:1rem; text-align:center;">
            <%= request.getAttribute("erro") %>
        </p>
        <% } %>

        <p class="subtle">
            Esqueceu sua senha?
            <a href="${pageContext.request.contextPath}/redefinir-senha">Redefinir</a>
        </p>

        <p class="subtle">
            Não tem uma conta?
            <a href="${pageContext.request.contextPath}/register">Cadastrar</a>
        </p>

    </form>
</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

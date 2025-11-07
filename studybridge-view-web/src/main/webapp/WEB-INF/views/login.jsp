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
    <a href="${pageContext.request.contextPath}/index.jsp" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="max-width:420px;">
    <h2>Entrar</h2>
    <p class="subtle">Acesse com seu e-mail</p>
    <form class="card">
        <label class="field">
            <span>Email</span>
            <input class="input" type="email" placeholder="exemplo@gmail.com">
        </label>

        <label class="field">
            <span>Senha</span>
            <input class="input" type="password" placeholder="Digite sua senha">
        </label>

        <div class="toolbar">
            <a class="btn" href="${pageContext.request.contextPath}/verificar-login.jsp">Entrar</a>
        </div>

        <p class="subtle">
            Esqueceu sua senha?
            <a href="${pageContext.request.contextPath}/recuperar.jsp">Redefinir</a>
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

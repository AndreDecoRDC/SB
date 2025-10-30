<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Cadastrar — StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/index.jsp" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="max-width:450px;">
    <h2>Crie sua conta</h2>

    <!-- Formulário envia para o servlet -->
    <form class="card" action="${pageContext.request.contextPath}/cadastrar" method="post">
        <label class="field">
            <span>Email</span>
            <input class="input" name="email" type="email" placeholder="exemplo@gmail.com" required>
        </label>

        <label class="field">
            <span>Senha</span>
            <input class="input" name="senha" type="password" placeholder="••••••••" required>
        </label>

        <label class="field">
            <span>Confirmar Senha</span>
            <input class="input" name="confirmarSenha" type="password" placeholder="••••••••" required>
        </label>

        <label class="field">
            <span>Tipo de conta</span>
            <select class="select" name="tipoConta" required>
                <option value="Estudante">Estudante</option>
                <option value="Monitor">Monitor</option>
            </select>
        </label>

        <div class="toolbar" style="justify-content:center;">
            <button type="submit" class="btn">Cadastrar</button>
        </div>
    </form>

    <%
        String mensagem = (String) request.getAttribute("mensagem");
        if (mensagem != null) {
    %>
        <p style="color:#dc2626;text-align:center;margin-top:1rem;"><%= mensagem %></p>
    <%
        }
    %>

    <%
        String erro = (String) request.getAttribute("erro");
        if (erro != null) {
    %>
        <p style="color:red; text-align:center;"><%= erro %></p>
    <%
        }
    %>


</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

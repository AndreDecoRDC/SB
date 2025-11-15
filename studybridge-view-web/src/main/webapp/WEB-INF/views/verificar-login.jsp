<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Verificar Login — StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>

<body>

<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <div class="logo">SB</div>
        <strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="max-width:400px">
    <div class="card">

        <h2 class="card-title">Digite o código</h2>
        <p class="subtle">Enviamos um código de 6 dígitos para seu e-mail.</p>

        <form method="post" action="${pageContext.request.contextPath}/verificar-login-action">

            <label class="field">
                <span>Código de verificação</span>
                <input class="input" name="codigo" maxlength="6" required />
            </label>

            <button class="button primary" style="width:100%; margin-top:1rem;">
                Confirmar
            </button>

            <% if (request.getAttribute("erro") != null) { %>
            <p class="erro" style="color:#c62828; margin-top:1rem;">
                <%= request.getAttribute("erro") %>
            </p>
            <% } %>

        </form>

    </div>
</main>

</body>
</html>

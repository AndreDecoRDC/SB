<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Solicitar Aula — StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/busca.jsp" class="brand">
        <div class="logo">SB</div>
        <strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="max-width:600px">
    <div class="card">
        <h2 class="card-title">Solicitar aula com Carla Mendes</h2>
        <p class="subtle">Preencha os detalhes e envie uma mensagem para o monitor.</p>

        <form action="${pageContext.request.contextPath}/estudante/solicitarAula" method="post">
            <label class="field">
                <span>Disciplina</span>
                <input class="input" name="disciplina" value="Matemática" required />
            </label>

            <label class="field">
                <span>Data e horário sugeridos</span>
                <input class="text" name="dataHora" placeholder="Ex: 15/10/2025 14:00" required />
            </label>

            <label class="field">
                <span>Mensagem ao monitor</span>
                <textarea class="textarea" name="mensagem" placeholder="Explique suas dificuldades e combine a aula..." required></textarea>
            </label>

            <div class="toolbar" style="margin-top:16px">
                <button type="submit" class="btn">Enviar solicitação</button>
                <a class="btn ghost" href="${pageContext.request.contextPath}/busca.jsp">Cancelar</a>
            </div>
        </form>
    </div>
    <% String erro = (String) request.getAttribute("erro");
        if (erro != null) { %>
    <p style="color:red; text-align:center; margin-top:1rem;"><%= erro %></p>
    <% } %>
</main>

<footer class="footer">© 2025 StudyBridge. Todos os direitos reservados.</footer>
</body>
</html>

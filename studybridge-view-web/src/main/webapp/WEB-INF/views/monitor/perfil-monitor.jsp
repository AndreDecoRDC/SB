<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.studybridge.domain.model.Monitor" %>

<%
    Monitor monitor = (Monitor) request.getAttribute("monitor");
%>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <title>Perfil — StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<header class="header">
    <a href="${pageContext.request.contextPath}/monitor/dashboard" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="${pageContext.request.contextPath}/monitor/aulas-monitor">Aulas</a>
        <a class="btn" href="${pageContext.request.contextPath}/monitor/horarios">Horários</a>
        <a class="btn active" href="${pageContext.request.contextPath}/monitor/perfil">Perfil</a>
        <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
    </nav>
</header>

<main class="container" style="max-width:600px;">
    <h2>Perfil</h2>
    <p class="subtle">Edite suas informações pessoais.</p>

    <%
        String msgSucesso = (String) session.getAttribute("msgSucesso");
        if (msgSucesso != null) {
    %>
    <div class="alert success">
        <%= msgSucesso %>
    </div>
    <%
            session.removeAttribute("msgSucesso"); // remove após mostrar
        }
    %>

    <form class="card" method="post"
          action="${pageContext.request.contextPath}/salvar-perfil-monitor">

        <label class="field">
            <span>Nome completo</span>
            <input class="input" name="nome"
                   value="<%= monitor != null && monitor.getNome() != null ? monitor.getNome() : "" %>">
        </label>

        <label class="field">
            <span>Telefone</span>
            <input class="input"
                   type="tel"
                   name="telefone"
                   id="telefone-monitor"
                   inputmode="numeric"
                   placeholder="(31) 9 9999-9999"
                   maxlength="15"
                   value="<%= monitor != null && monitor.getTelefone() != null ? monitor.getTelefone() : "" %>">
        </label>

        <label class="field">
            <span>Disciplina</span>
            <input class="input" name="disciplina"
                   value="<%= monitor != null && monitor.getDisciplina() != null ? monitor.getDisciplina() : "" %>">
        </label>

        <label class="field">
            <span>Campus</span>
            <select class="select" name="campus">
                <option value="">Selecione</option>
                <option value="Nova Suica"
                        <%= monitor != null && "Nova Suica".equals(monitor.getCampus()) ? "selected" : "" %>>
                    Nova Suíça
                </option>
                <option value="Nova Gameleira"
                        <%= monitor != null && "Nova Gameleira".equals(monitor.getCampus()) ? "selected" : "" %>>
                    Nova Gameleira
                </option>
            </select>
        </label>

        <label class="field">
            <span>Descrição</span>
            <textarea class="textarea" name="descricao"><%=
            monitor != null && monitor.getDescricao() != null ? monitor.getDescricao() : ""
            %></textarea>
        </label>

        <div class="toolbar" style="justify-content:center;">
            <button class="btn" type="submit">Salvar</button>
            <a class="btn ghost" href="${pageContext.request.contextPath}/monitor/dashboard">Cancelar</a>
        </div>

        <%
            String erro = (String) request.getAttribute("erro");
            if (erro != null) {
        %>
        <p class="error"><%= erro %></p>
        <%
            }
        %>
    </form>
</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG
</footer>

<script>
    function aplicarMascaraTelefone(input) {
        input.addEventListener("input", function (e) {
            let valor = e.target.value.replace(/\D/g, "");

            if (valor.length > 11) valor = valor.slice(0, 11);

            if (valor.length >= 2)
                valor = "(" + valor.substring(0, 2) + ") " + valor.substring(2);

            if (valor.length >= 7)
                valor = valor.substring(0, 10) + "-" + valor.substring(10);

            e.target.value = valor;
        });
    }

    const telMonitor = document.getElementById("telefone-monitor");
    if (telMonitor) aplicarMascaraTelefone(telMonitor);
</script>

</body>
</html>

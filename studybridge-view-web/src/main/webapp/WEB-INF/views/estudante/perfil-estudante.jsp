<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.studybridge.domain.model.Estudante" %>

<%
    Estudante estudante = (Estudante) request.getAttribute("estudante");
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
    <a href="${pageContext.request.contextPath}/estudante/dashboard" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="${pageContext.request.contextPath}/estudante/busca">Buscar Monitores</a>
        <a class="btn" href="${pageContext.request.contextPath}/estudante/aulas-estudante">Aulas</a>
        <a class="btn active" href="${pageContext.request.contextPath}/estudante/perfil">Perfil</a>
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
          action="${pageContext.request.contextPath}/salvar-perfil-estudante">

        <label class="field">
            <span>Nome completo</span>
            <input class="input" name="nome"
                   value="<%= estudante != null && estudante.getNome() != null ? estudante.getNome() : "" %>">
        </label>

        <label class="field">
            <span>Telefone</span>
            <input class="input"
                   type="tel"
                   name="telefone"
                   id="telefone-estudante"
                   inputmode="numeric"
                   placeholder="(31) 9 9999-9999"
                   maxlength="15"
                   value="<%= estudante != null && estudante.getTelefone() != null ? estudante.getTelefone() : "" %>">
        </label>

        <label class="field">
            <span>Curso</span>
            <input class="input" name="curso"
                   value="<%= estudante != null && estudante.getCurso() != null ? estudante.getCurso() : "" %>">
        </label>

        <label class="field">
            <span>Ano/Turma</span>
            <input class="input" name="turma"
                   value="<%= estudante != null && estudante.getAnoTurma() != null ? estudante.getAnoTurma() : "" %>">
        </label>

        <label class="field">
            <span>Campus</span>
            <select class="select" name="campus">
                <option value="">Selecione</option>
                <option value="Nova Suica"
                        <%= estudante != null && "Nova Suica".equals(estudante.getCampus()) ? "selected" : "" %>>
                    Nova Suíça
                </option>
                <option value="Nova Gameleira"
                        <%= estudante != null && "Nova Gameleira".equals(estudante.getCampus()) ? "selected" : "" %>>
                    Nova Gameleira
                </option>
            </select>
        </label>

        <label class="field">
            <span>Descrição</span>
            <textarea class="textarea" name="descricao"><%=
            estudante != null && estudante.getDescricao() != null ? estudante.getDescricao() : ""
            %></textarea>
        </label>

        <div class="toolbar" style="justify-content:center;">
            <button class="btn" type="submit">Salvar</button>
            <a class="btn ghost" href="${pageContext.request.contextPath}/estudante/dashboard">Cancelar</a>
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

    const telEstudante = document.getElementById("telefone-estudante");
    if (telEstudante) aplicarMascaraTelefone(telEstudante);
</script>

</body>
</html>

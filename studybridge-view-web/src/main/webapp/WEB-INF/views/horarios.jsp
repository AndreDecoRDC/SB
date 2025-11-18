<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Gerenciar Horários | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script>
        function limparCampos() {
            document.getElementById("formHorario").reset();
        }
    </script>
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="#">Aulas</a>
        <a class="btn" href="${pageContext.request.contextPath}/horarios">Horários</a>
        <a class="btn" href="#">Perfil</a> 
        <img src="${pageContext.request.contextPath}/imagens/notifications_24dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" 
             alt="Notificações" width="24" height="24">

        <a class="btn" href="${pageContext.request.contextPath}/">Sair</a>
    </nav>
</header>

<main class="container" style="max-width: 600px; margin-top: 2rem;">
    <h2 style="text-align:center;">Gerenciar Horários</h2>
    <p style="text-align:center;">Cadastre seus horários disponíveis e defina a duração média das suas aulas de monitoria.</p>

    <c:if test="${not empty erro}">
        <div class="alert error" style="color: red">${erro}</div>
    </c:if>
    <c:if test="${not empty sucesso}">
        <div class="alert success">${sucesso}</div>
    </c:if>

    <form id="formHorario" action="${pageContext.request.contextPath}/horarios" method="post" class="card" style="margin-top:1.5rem;">
        <label class="field">
            <span>Dia da Semana</span>
            <select name="diaSemana" class="select" required>
                <option ${diaSemana == 'Segunda-feira' ? 'selected' : ''}>Segunda-feira</option>
                <option ${diaSemana == 'Terça-feira' ? 'selected' : ''}>Terça-feira</option>
                <option ${diaSemana == 'Quarta-feira' ? 'selected' : ''}>Quarta-feira</option>
                <option ${diaSemana == 'Quinta-feira' ? 'selected' : ''}>Quinta-feira</option>
                <option ${diaSemana == 'Sexta-feira' ? 'selected' : ''}>Sexta-feira</option>
            </select>

        </label>

        <label class="field">
            <span>Horário de Início</span>
            <input type="time" name="horaInicio" class="input" value="${horaInicio}" required>
        </label>

        <label class="field">
            <span>Horário de Término</span>
            <input type="time" name="horaTermino" class="input" value="${horaTermino}" required>
        </label>


        <div class="toolbar" style="display:flex; justify-content:center; gap:1rem; margin-top:1.2rem;">
            <button type="submit" class="btn ghost">Adicionar</button>
            <button type="button" class="btn ghost" onclick="limparCampos()">Limpar</button>
        </div>

    </form>

    <div class="card" style="margin-top:2rem;">
        <h3>Horários Cadastrados</h3>             
        <ul class="subtle" style="margin-top:0.8rem; line-height:1.6;">
            <c:forEach var="h" items="${horarios}">
                <li><b>${h.diaSemana}:</b> ${h.horaInicio} — ${h.horaTermino} · ${h.duracaoMedia} min</li>
            </c:forEach>
            <c:if test="${empty horarios}">
                <li>Nenhum horário cadastrado ainda.</li>
            </c:if>
                <div class="toolbar" style="display:flex; justify-content:center; gap:1rem; margin-top:1.2rem;">
                    <a href="${pageContext.request.contextPath}/editar-horarios" class="btn">
                        ✎ 
                    </a>
                </div> 
        </ul>
    </div>
</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

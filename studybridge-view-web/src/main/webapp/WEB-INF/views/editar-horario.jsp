<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Editar Horário | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<style>
    html, body {
        height: 100%;
        display: flex;
        flex-direction: column;
    }
    main.container {
        flex: 1;
    }
    .footer {
        margin-top: auto;
        text-align: center;
        padding: 1rem;
        background-color: #f8f8f8;
        border-top: 1px solid #ddd;
    }
</style>

<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="#">Aulas</a>
        <a class="btn" href="${pageContext.request.contextPath}/horarios">Horários</a>
        <a class="btn" href="#">Perfil</a>
        <a class="btn" href="${pageContext.request.contextPath}/">Sair</a>
    </nav>
</header>

<main class="container" style="max-width: 600px; margin-top: 2rem;">
    <h2 style="text-align:center;">Editar Horário</h2>

    <c:if test="${not empty erro}">
        <div class="alert error" style="color:red">${erro}</div>
    </c:if>
    <c:if test="${not empty sucesso}">
        <div class="alert success">${sucesso}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/editar-horario" method="post" class="card" style="margin-top:1.5rem;">
        <input type="hidden" name="id" value="${horario.id}">

        <label class="field">
            <span>Dia da Semana</span>
            <select name="diaSemana" class="select" required>
                <option ${horario.diaSemana == 'Segunda-feira' ? 'selected' : ''}>Segunda-feira</option>
                <option ${horario.diaSemana == 'Terça-feira' ? 'selected' : ''}>Terça-feira</option>
                <option ${horario.diaSemana == 'Quarta-feira' ? 'selected' : ''}>Quarta-feira</option>
                <option ${horario.diaSemana == 'Quinta-feira' ? 'selected' : ''}>Quinta-feira</option>
                <option ${horario.diaSemana == 'Sexta-feira' ? 'selected' : ''}>Sexta-feira</option>
            </select>
        </label>

        <label class="field">
            <span>Horário de Início</span>
            <input type="time" name="horaInicio" class="input" value="${horario.horaInicio}" required>
        </label>

        <label class="field">
            <span>Horário de Término</span>
            <input type="time" name="horaTermino" class="input" value="${horario.horaTermino}" required>
        </label>

        <label class="field">
            <span>Duração Média (minutos)</span>
            <input type="number" name="duracaoMedia" class="input" min="15" max="180" value="${horario.duracaoMedia}" required>
        </label>

        <div class="toolbar" style="display:flex; justify-content:center; gap:1rem; margin-top:1.2rem;">
            <button type="submit" class="btn">Salvar Alterações</button>
            <a href="${pageContext.request.contextPath}/editar-horarios" class="btn ghost">Cancelar</a>
        </div>
    </form>
</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

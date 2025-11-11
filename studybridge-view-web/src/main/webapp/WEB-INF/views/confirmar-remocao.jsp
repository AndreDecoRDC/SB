<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Confirmar Remoção</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<main class="container" style="max-width: 500px; margin-top: 4rem;">
    <div class="card" style="text-align:center;">
        <h2>Excluir Horário</h2>
        <p>Você tem certeza que deseja excluir este horário?</p>
        <p><b>${horario.diaSemana}</b>: ${horario.horaInicio} — ${horario.horaTermino}</p>

        <form action="${pageContext.request.contextPath}/remover-horario" method="post">
            <input type="hidden" name="id" value="${horario.id}">
            <div class="toolbar" style="display:flex; justify-content:center; gap:1rem; margin-top:1.5rem;">
                <a href="${pageContext.request.contextPath}/editar-horarios" class="btn ghost">Cancelar</a>
                <button type="submit" class="btn" style="background-color:black;">Confirmar</button>
            </div>
        </form>
    </div>
</main>
</body>
</html>

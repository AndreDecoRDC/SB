<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title>Painel Administrativo — StudyBridge</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    </head>

    <body>

        <header class="header">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">
                <div class="logo">SB</div><strong>StudyBridge</strong>
            </a>
            <nav class="nav">
                <a class="btn" href="${pageContext.request.contextPath}/admin/busca">Buscar Usuários</a>
                <a class="btn" href="${pageContext.request.contextPath}/admin/denuncias     ">Denúncias</a>
                <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
            </nav>
        </header>

        <main class="container">
            <h2>Painel Administrativo</h2>
            <p class="subtle">Visão geral da plataforma.</p>

            <div class="grid-3">

                <div class="card">
                    <h3>Usuários Ativos</h3>
                    <p class="number">${totalAtivos}</p>
                    <p class="subtext">
                        Estudantes: ${estudantesAtivos} · 
                        Monitores: ${monitoresAtivos} · 
                        Suspensos: ${suspensos}
                    </p>
                </div>

                <div class="card">
                    <h3>Aulas Registradas</h3>
                    <p class="number">${totalAulas}</p>
                    <p class="subtext">
                        Marcadas: ${aulasMarcadas} · 
                        Concluídas: ${aulasConcluidas}
                    </p>
                </div>

                <div class="card">
                    <h3>Denúncias Ativas</h3>
                    <p class="number">${denunciasAtivas}</p>
                    <p class="subtext">Em análise</p>
                </div>

            </div>
        </main>

        <footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>

    </body>
</html>

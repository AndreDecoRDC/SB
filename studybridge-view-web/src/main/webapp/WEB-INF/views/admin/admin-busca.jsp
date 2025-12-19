<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title>Buscar Usuários — StudyBridge</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
        <style>
            .filters {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 1.2rem;
                margin-top: 1rem;
            }
            .filters .field {
                flex: 1 1 220px;
                min-width: 220px;
            }
            .results {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                gap: 1.5rem;
                margin-top: 2rem;
            }
        </style>
    </head>

    <body>

        <header class="header">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">
                <div class="logo">SB</div><strong>StudyBridge</strong>
            </a>
            <nav class="nav">
                <a class="btn" href="${pageContext.request.contextPath}/admin/busca">Buscar Usuários</a>
                <a class="btn" href="#">Denúncias</a>
                <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
            </nav>
        </header>

        <main class="container">
            <h2>Buscar Usuário</h2>

            <div class="card">
                <form action="${pageContext.request.contextPath}/admin/busca" method="get">

                    <div class="filters">
                        <label class="field">
                            <span>Nome do Usuário</span>
                            <input class="input" name="nome" placeholder="Busque aqui">
                        </label>

                        <label class="field">
                            <span>Tipo</span>
                            <select class="select" name="tipo">
                                <option value="">Todos</option>
                                <option value="Estudante">Estudante</option>
                                <option value="Monitor">Monitor</option>
                            </select>
                        </label>

                        <label class="field">
                            <span>Ordenar</span>
                            <select class="select" name="ordenar">
                                <option value="alfabetica">Alfabeticamente</option>
                                <option value="avaliacao">Maior Avaliação</option>
                            </select>
                        </label>
                    </div>

                    <div class="toolbar" style="justify-content:center;margin-top:1.5rem;">
                        <button type="submit" class="btn">Aplicar filtros</button>
                        <button type="reset" class="btn ghost">Limpar</button>
                    </div>

                </form>
            </div>

            <div class="results">
                <div class="card">
                    <!--
                        aqui vou listar os usuários retornados do servlet
                        <c:forEach var="u" items="${usuarios}">
                        </c:forEach> -->
                </div>
            </div>
        </main>

        <footer class="footer">
            © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
        </footer>

    </body>
</html>

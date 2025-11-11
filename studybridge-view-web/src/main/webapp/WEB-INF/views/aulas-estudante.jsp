<%@ page contentType="text/html;charset=UTF-8" language="java" %>
< !DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Aulas — Estudante | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
    <header class="header">
        <a href="estudante-dashboard.html" class="brand">
            <div class="logo">SB</div><strong>StudyBridge</strong>
        </a>
        <nav class="nav">
            <a class="btn" href="busca.html">Buscar Monitores</a>
            <a class="btn" href="aulas-estudante.html">Aulas</a>
            <a class="btn" href="perfil-estudante.html">Perfil</a>
            <a class="notif" href="#notifPanel"><img src="Imagens/notifications_24dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Notificações"></a>
            <a class="btn" href="index.html">Sair</a>
        </nav>
    </header>

    <main class="container">
        <h2>Suas Aulas</h2>
        <p class="subtle">Veja suas aulas, detalhes e avaliações de monitores.</p>

        <div class="aulas-container">
            <div class="aulas-section">
                <h3>Histórico</h3>
                <table>
                    <thead>
                    <tr><th>Monitor</th><th>Disciplina</th><th>Data</th><th>Avaliação</th><th>Ações</th></tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Rafael Costa</td>
                        <td>Física</td>
                        <td>02/10/2025</td>
                        <td>
                            <% Double media = (Double) request.getAttribute("media");
                                if (media != null) { %>
                            ⭐ <%= String.format("%.1f", media) %>
                            <% } else { %>
                            —
                            <% } %>
                        </td>
                        <td><a class="btn light" href="#avaliar1">Avaliar</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </main>

    <div id="avaliar1" class="modal">
        <div class="modal-content">
            <h3>Avaliar Monitor</h3>
            <form action="${pageContext.request.contextPath}/avaliar" method="post">
                <div class="stars">
                    <input type="radio" id="star5" name="nota" value="5"><label for="star5">★</label>
                    <input type="radio" id="star4" name="nota" value="4"><label for="star4">★</label>
                    <input type="radio" id="star3" name="nota" value="3"><label for="star3">★</label>
                    <input type="radio" id="star2" name="nota" value="2"><label for="star2">★</label>
                    <input type="radio" id="star1" name="nota" value="1"><label for="star1">★</label>
                </div>

                <label class="field"><span>Comentário</span>
                    <textarea class="textarea" name="comentario" placeholder="Como foi sua experiência com o monitor?" required></textarea>
                </label>

                <div class="toolbar">
                    <button class="btn" type="submit">Enviar Avaliação</button>
                    <a class="btn ghost" href="#">Cancelar</a>
                </div>
            </form>

            <%
                java.util.List<String> comentarios = (java.util.List<String>) request.getAttribute("comentarios");
                if (comentarios != null && !comentarios.isEmpty()) {
            %>
            <div class="comentarios">
                <h4>Comentários enviados:</h4>
                <ul>
                    <% for (String c : comentarios) { %>
                    <li><%= c %></li>
                    <% } %>
                </ul>
            </div>
            <% } %>
        </div>
    </div>

    <footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>
</body>
</html>

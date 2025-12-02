<%@ page contentType="text/html;charset=UTF-8" language="java" %>
< !DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Aulas — Estudante | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    <style>
        .aulas-container {
            display: flex;
            flex-direction: column;
            gap: 2.5rem;
            margin-top: 2rem;
        }
        @media (min-width: 950px) {
            .aulas-container {
                flex-direction: row;
                justify-content: center;
                align-items: flex-start;
            }
        }
        .aulas-section {
            flex: 1;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
            padding: 2rem;
            max-width: 770px;
            min-height: 520px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 0.95rem;
        }
        th, td {
            padding: 0.75rem 0.6rem;
            border-bottom: 1px solid #e5e7eb;
            text-align: center;
        }
        th { background: #f3f4f6; color: #374151; }
        .status.ok { color: #16a34a; font-weight: 600; }
        .status.wait { color: #ca8a04; font-weight: 600; }

        .nome-link {
            color: #1e3fae;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
        }
        .nome-link:hover { text-decoration: underline; }

        .stars10 {
            display: flex;
            flex-direction: row-reverse;
            justify-content: center;
            gap: 0.1rem;
        }
        .stars10 input { display: none; }
        .stars10 label {
            font-size: 1.5rem;
            color: #d1d5db;
            cursor: pointer;
            transition: color 0.2s;
        }
        .stars10 label:hover,
        .stars10 label:hover ~ label,
        .stars10 input:checked ~ label {
            color: #facc15;
        }
    </style>
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
        <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
    </nav>
</header>

<main class="container">
    <h2>Suas Aulas</h2>
    <p class="subtle">Veja suas aulas, detalhes e avaliações de monitores.</p>

    <div class="aulas-container">
        <div class="aulas-section">
            <h3>Solicitações Pendentes</h3>
            <table>
                <thead><tr><th>Estudante</th><th>Disciplina</th><th>Data</th><th>Status</th><th>Ações</th><th></th></tr></thead>
                <tbody>
                <tr>
                    <td><a href="#perfilModalMaria" class="nome-link">Maria Souza</a></td>
                    <td>Física</td>
                    <td>18/10/2025</td>
                    <td><span class="status wait">Pendente</span></td>
                    <td>
                        <a class="btn light" href="#detalhesAulaMaria">Detalhes</a>
                        <a class="btn" href="#">Confirmar</a>
                    </td>
                    <td>
                        <a class="btn-denunciar"
                           href="#denunciaModal"
                           data-denunciado-id="${aula.monitor.id}"
                           data-aula-id="${aula.id}">
                            <img src="${pageContext.request.contextPath}/Imagens/report_23dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Denunciar">
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="aulas-container">
        <div class="aulas-section">
            <h3>Aulas Concluídas</h3>
            <table>
                <thead>
                <tr><th>Monitor</th><th>Disciplina</th><th>Data</th><th>Avaliação</th><th>Ações</th></tr>
                </thead>
                <tbody>
                <tr>
                    <td>João Pedro</td>
                    <td>Matemática</td>
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
                    <td>
                        <a class="btn-denunciar"
                           href="#denunciaModal"
                           data-denunciado-id="${aula.monitor.id}"
                           data-aula-id="${aula.id}">
                            <img src="${pageContext.request.contextPath}/Imagens/report_23dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Denunciar">
                        </a>
                    </td>
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
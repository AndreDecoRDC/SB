<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
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
            max-width: 600px;
            min-height: 520px;
        }
        table { width: 100%; border-collapse: collapse; font-size: 0.95rem; }
        th, td { padding: 0.75rem 0.6rem; border-bottom: 1px solid #e5e7eb; text-align: center; }
        th { background: #f3f4f6; color: #374151; }
        .status.ok { color: #16a34a; font-weight: 600; }

        .nome-link {
            color: #1e3fae;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
        }
        .nome-link:hover { text-decoration: underline; }

        .stars {
            display: flex;
            justify-content: center;
            flex-direction: row-reverse;
            gap: 0.2rem;
            margin-bottom: 1rem;
        }
        .stars input { display: none; }
        .stars label {
            font-size: 2rem;
            color: #d1d5db;
            cursor: pointer;
            transition: color 0.2s;
        }
        .stars label:hover,
        .stars label:hover ~ label,
        .stars input:checked ~ label { color: #facc15; }

        .perfil-modal { max-width: 520px; text-align: left; }
        .perfil-info { line-height: 1.6; margin-bottom: 1rem; }
        .perfil-info p { margin: 0.3rem 0; }
        .perfil-info span { font-weight: 600; color: #374151; }

        .horarios {
            background: #f9fafb;
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            padding: 0.8rem 1rem;
            margin-top: 1rem;
        }
        .horarios h4 { margin-bottom: 0.5rem; color: #1e3fae; }
        .horarios ul { list-style: none; padding-left: 0; line-height: 1.6; color: #374151; }
    </style>
</head>
<body>
<header class="header">
    <a href="estudante-dashboard.jsp" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="busca.jsp">Buscar Monitores</a>
        <a class="btn" href="aulas-estudante.jsp">Aulas</a>
        <a class="btn" href="perfil-estudante.jsp">Perfil</a>
        <a class="notif" href="#notifPanel"><img src="${pageContext.request.contextPath}/Imagens/notifications_24dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Notificações"></a>
        <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
    </nav>
</header>

<main class="container">
    <h2>Suas Aulas</h2>
    <p class="subtle">Veja suas aulas, detalhes e avaliações de monitores.</p>

    <div class="aulas-container">
        <div class="aulas-section">
            <h3>Aulas Futuras</h3>
            <table>
                <thead><tr><th>Monitor</th><th>Disciplina</th><th>Data</th><th>Status</th><th>Ações</th><th></th></tr></thead>
                <tbody>
                <tr>
                    <td><a href="#perfilModalCarla" class="nome-link">Carla Mendes</a></td>
                    <td>Matemática</td>
                    <td>15/10/2025</td>
                    <td><span class="status ok">Confirmada</span></td>
                    <td>
                        <a class="btn light" href="#detalhesAula1">Detalhes</a>
                        <a class="btn ghost" href="#cancelar1">Cancelar</a>
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
                    <td>
                        <a class="btn light" href="#avaliar1">Avaliar</a>
                    <td>
                        <a class="btn-denunciar"
                           href="#denunciaModal"
                           data-denunciado-id="${aula.monitor.id}"
                           data-aula-id="${aula.id}">
                            <img src="${pageContext.request.contextPath}/Imagens/report_23dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Denunciar">
                        </a>
                    </td>
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
    </div>
</div>
<div id="denunciarMonitor" class="modal">
    <div class="modal-content">
        <h3>Registrar Denúncia</h3>

        <form action="${pageContext.request.contextPath}/denuncias" method="post">
            <input type="hidden" name="denunciadoId" value="${param.denunciadoId}">

            <label class="field"><span>Motivo</span>
                <select name="motivo" class="select" required>
                    <option value="FALTA_SEM_AVISO">Falta sem aviso</option>
                    <option value="COMPORTAMENTO_INADEQUADO">Comportamento inadequado</option>
                    <option value="ASSEDIO">Assédio / Ofensa</option>
                    <option value="OUTRO">Outro</option>
                </select>
            </label>

            <label class="field"><span>Descrição (Opcional)</span>
                <textarea name="descricao" class="textarea" placeholder="Descreva o ocorrido..."></textarea>
            </label>

            <div class="toolbar">
                <button class="btn" type="submit">Enviar Denúncia</button>
                <a class="btn ghost" href="#">Cancelar</a>
            </div>
        </form>

        <%String sucesso = (String) session.getAttribute("denunciaSucesso");
            if ("true".equals(sucesso)) {
        %>

        <p class="success" id="mensagemSucesso">Denúncia enviada com sucesso!</p>

        <%session.removeAttribute("denunciaSucesso");
            }
        %>
    </div>
</div>

<footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>
<script>
    const botoesAvaliar = document.querySelectorAll('.btn-avaliar');
    botoesAvaliar.forEach(btn => {
        btn.addEventListener('click', () => {
            const idAvaliado = btn.dataset.avaliadoId;
            const idAula = btn.dataset.aulaId;
            document.getElementById('idMonitorAvaliar').value = idAvaliado;
            document.getElementById('idAulaAvaliar').value = idAula;
        });
    });

    const botoesDenunciar = document.querySelectorAll('.btn-denunciar');
    botoesDenunciar.forEach(btn => {
        btn.addEventListener('click', () => {
            const idDenunciado = btn.dataset.denunciadoId;
            const idAula = btn.dataset.aulaId;
            document.getElementById('idDenunciadoDenuncia').value = idDenunciado;
            document.getElementById('idAulaDenuncia').value = idAula;
        });
    });

    document.querySelectorAll('.modal .btn, .modal-content .btn, .notif-close').forEach(element => {
        element.addEventListener('click', (e) => {
            if (e.currentTarget.getAttribute('href') === '#') {
                e.preventDefault();
                window.location.hash = '';
            }
        });
    });
</script>
</body>
</html>

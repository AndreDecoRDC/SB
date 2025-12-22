<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Aulas — Estudante | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    <style>
        .aulas-container { display: grid; gap: 2rem; margin-top: 2rem; }
        @media (min-width: 950px) { .aulas-container { grid-template-columns: repeat(2, 1fr); } }
        .aulas-section { flex: 1; background: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); padding: 2rem; max-width: 100%; min-height: 520px; }
        table { width: 100%; border-collapse: collapse; font-size: 0.95rem; }
        th, td { padding: 0.75rem 0.6rem; border-bottom: 1px solid #e5e7eb; text-align: center; }
        th { background: #f3f4f6; color: #374151; }
        .status.ok { color: #16a34a; font-weight: 600; }
        .status.wait { color: #f59e0b; font-weight: 600; }
        .status.fail { color: #dc2626; font-weight: 600; }
        .nome-link { color: #1e3fae; font-weight: 600; cursor: pointer; text-decoration: none; }
        .nome-link:hover { text-decoration: underline; }
        td:last-child { padding-right: 0; }
        .modal:target { display: flex !important; }
        .success-msg { background-color: #dcfce7; color: #166534; padding: 1rem; border-radius: 8px; margin-bottom: 1rem; border: 1px solid #bbf7d0; text-align: center; }
    </style>
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="${pageContext.request.contextPath}/busca">Buscar Monitores</a>
        <a class="btn" href="${pageContext.request.contextPath}/denuncias">Aulas</a>
        <a class="btn" href="${pageContext.request.contextPath}/perfil-estudante">Perfil</a>
        <a class="notif" href="#notifPanel"><img src="Imagens/notifications_24dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Notificações"></a>
        <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
    </nav>
</header>

<main class="container">
    <h2>Suas Aulas</h2>
    <p class="subtle">Veja suas aulas, detalhes e avaliações de monitores.</p>

    <c:if test="${not empty sessionScope.denunciaSucesso}">
        <div class="success-msg" id="tempMsg">Denúncia enviada com sucesso!</div>
        <c:remove var="denunciaSucesso" scope="session" />
    </c:if>

    <div class="aulas-container">
        <div class="aulas-section">
            <h3>Aulas Pendentes e Aceitas</h3>
            <table>
                <thead><tr><th>Monitor</th><th>Disciplina</th><th>Data</th><th>Status</th><th>Ações</th></tr></thead>
                <tbody>
                <c:forEach var="a" items="${aulas}">
                    <c:if test="${a.status == 'PENDENTE' || a.status == 'ACEITA'}">
                        <tr>
                            <td><a class="nome-link" href="#">${a.nomeUsuarioAssociado}</a></td>
                            <td>${a.disciplina}</td>
                            <td><c:out value="${a.dataAulaFormatada}" /></td>
                            <td><span class="${a.status == 'ACEITA' ? 'status ok' : 'status wait'}">${a.status}</span></td>
                            <td>
                                <a class="btn light" href="${pageContext.request.contextPath}/detalhes-aula?idAula=${a.id}">Detalhes</a>
                                <button class="btn ghost" onclick="abrirModalCancelamento('${a.id}', '${a.disciplina}', '${a.dataAulaFormatada}', '${pageContext.request.contextPath}/estudante/aula/cancelar')">Cancelar</button>
                            </td>
                            <td>
                                <a class="btn-denunciar" href="#denunciarMonitor" data-denunciado-id="${a.idUsuarioAssociado}" data-aula-id="${a.id}">
                                    <img src="${pageContext.request.contextPath}/Imagens/report_23dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Denunciar">
                                </a>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="aulas-section">
            <h3>Aulas Canceladas e Recusadas</h3>
            <table>
                <thead><tr><th>Monitor</th><th>Disciplina</th><th>Data</th><th>Status</th><th>Ações</th></tr></thead>
                <tbody>
                <c:forEach var="a" items="${aulas}">
                    <c:if test="${a.status == 'CANCELADA' ||  a.status == 'RECUSADA'}">
                        <tr>
                            <td><a class="nome-link" href="#">${a.nomeUsuarioAssociado}</a></td>
                            <td>${a.disciplina}</td>
                            <td><c:out value="${a.dataAulaFormatada}" /></td>
                            <td><span class="status fail">${a.status}</span></td>
                            <td><a class="btn light" href="${pageContext.request.contextPath}/detalhes-aula?idAula=${a.id}">Detalhes</a></td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="aulas-section">
            <h3>Histórico</h3>
            <table>
                <thead><tr><th>Monitor</th><th>Disciplina</th><th>Data</th><th>Avaliação</th><th>Ações</th></tr></thead>
                <tbody>
                <c:forEach var="a" items="${aulas}">
                    <c:if test="${a.status == 'CONCLUIDA'}">
                        <tr>
                            <td><a class="nome-link" href="#">${a.nomeUsuarioAssociado}</a></td>
                            <td>${a.disciplina}</td>
                            <td>${a.dataAulaFormatada}</td>
                            <td>—</td>
                            <td>
                                <a class="btn light btn-avaliar" href="#avaliar1" data-avaliado-id="${a.idUsuarioAssociado}" data-aula-id="${a.id}">Avaliar</a>
                                <a class="btn-denunciar" href="#denunciarMonitor" data-denunciado-id="${a.idUsuarioAssociado}" data-aula-id="${a.id}">
                                    <img src="${pageContext.request.contextPath}/Imagens/report_23dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Denunciar">
                                </a>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div id="avaliar1" class="modal">
        <div class="modal-content">
            <h3>Avaliar Monitor</h3>
            <form action="${pageContext.request.contextPath}/avaliar" method="post">
                <input type="hidden" name="idMonitor" id="idMonitorAvaliar" value="">
                <input type="hidden" name="idAula" id="idAulaAvaliar" value="">
                <div class="stars">
                    <input type="radio" id="star5" name="nota" value="5"><label for="star5">★</label>
                    <input type="radio" id="star4" name="nota" value="4"><label for="star4">★</label>
                    <input type="radio" id="star3" name="nota" value="3"><label for="star3">★</label>
                    <input type="radio" id="star2" name="nota" value="2"><label for="star2">★</label>
                    <input type="radio" id="star1" name="nota" value="1"><label for="star1">★</label>
                </div>
                <label class="field"><span>Comentário</span>
                    <textarea class="textarea" name="comentario" required></textarea>
                </label>
                <div class="toolbar">
                    <button class="btn" type="submit">Enviar Avaliação</button>
                    <a class="btn ghost fechar-modal" href="#">Cancelar</a>
                </div>
            </form>
        </div>
    </div>

    <div id="denunciarMonitor" class="modal">
        <div class="modal-content">
            <h3>Registrar Denúncia</h3>
            <form action="${pageContext.request.contextPath}/denuncias" method="post">
                <input type="hidden" name="denunciadoId" id="idDenunciadoDenuncia" value="">
                <input type="hidden" name="aulaId" id="idAulaDenuncia" value="">
                <label class="field"><span>Motivo</span>
                    <select name="motivo" class="select" required>
                        <option value="" disabled selected>Selecione um motivo</option>
                        <option value="FALTA_SEM_AVISO">Falta sem aviso</option>
                        <option value="COMPORTAMENTO_INADEQUADO">Comportamento inadequado</option>
                        <option value="ASSEDIO_DISCURSO_OFENSIVO">Assédio / Ofensa</option>
                        <option value="OUTRO">Outro</option>
                    </select>
                </label>
                <label class="field"><span>Descrição (Opcional)</span>
                    <textarea name="descricao" class="textarea"></textarea>
                </label>
                <div class="toolbar">
                    <button class="btn" type="submit">Enviar Denúncia</button>
                    <a class="btn ghost fechar-modal" href="#">Cancelar</a>
                </div>
            </form>
        </div>
    </div>

    <div id="confirmarCancelamento" class="modal" style="display:none;">
        <div class="modal-content">
            <h3>Confirmar Cancelamento</h3>
            <p><b id="modalDisciplina"></b> - <b id="modalDataAula"></b></p>
            <form id="formCancelamento" action="#" method="post">
                <input type="hidden" name="idAula" id="inputModalIdAula" value="" />
                <div class="toolbar">
                    <button class="btn" type="submit">Sim, cancelar aula</button>
                    <a class="btn ghost fechar-modal" href="#">Voltar</a>
                </div>
            </form>
        </div>
    </div>
</main>

<footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>

<script>
    function abrirModalCancelamento(idAula, disciplina, dataAula, urlAction) {
        document.getElementById('modalDisciplina').innerText = disciplina;
        document.getElementById('modalDataAula').innerText = dataAula;
        document.getElementById('inputModalIdAula').value = idAula;
        document.getElementById('formCancelamento').action = urlAction;
        document.getElementById('confirmarCancelamento').style.display = 'flex';
    }

    document.addEventListener('click', (e) => {
        const btn = e.target.closest('.btn-denunciar, .btn-avaliar, .fechar-modal');
        if (!btn) return;

        if (btn.classList.contains('fechar-modal')) {
            e.preventDefault();
            document.getElementById('confirmarCancelamento').style.display = 'none';
            window.location.hash = '';
            return;
        }

        const idUser = btn.dataset.denunciadoId || btn.dataset.avaliadoId;
        const idAula = btn.dataset.aulaId;

        if (btn.classList.contains('btn-denunciar')) {
            document.getElementById('idDenunciadoDenuncia').value = idUser;
            document.getElementById('idAulaDenuncia').value = idAula;
        } else if (btn.classList.contains('btn-avaliar')) {
            document.getElementById('idMonitorAvaliar').value = idUser;
            document.getElementById('idAulaAvaliar').value = idAula;
        }
    });

    const tempMsg = document.getElementById('tempMsg');
    if(tempMsg) setTimeout(() => tempMsg.style.display = 'none', 4000);
</script>
</body>
</html>
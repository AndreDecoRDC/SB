<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Aulas — Monitor | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    <style>
        .aulas-container { display: grid; gap: 2rem; margin-top: 2rem; }
        @media (min-width: 950px) { .aulas-container { grid-template-columns: repeat(2, 1fr); } }
        .aulas-section { flex: 1; background: #fff; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); padding: 2rem; max-width: 100%; min-height: 520px; }
        table { width: 100%; border-collapse: collapse; font-size: 0.95rem; }
        th, td { padding: 0.75rem 0.6rem; border-bottom: 1px solid #e5e7eb; text-align: center; }
        th { background: #f3f4f6; color: #374151; }
        .status.ok { color: #16a34a; font-weight: 600; }
        .status.wait { color: #ca8a04; font-weight: 600; }
        .status.fail { color: #dc2626; font-weight: 600; }
        .nome-link { color: #1e3fae; font-weight: 600; cursor: pointer; text-decoration: none; }
        .nome-link:hover { text-decoration: underline; }
        td:last-child { padding-right: 0; }
        .modal:target { display: flex !important; }
    </style>
</head>

<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/monitor-dashboard" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="${pageContext.request.contextPath}/aulas">Aulas</a>
        <a class="btn" href="${pageContext.request.contextPath}/horarios">Horários</a>
        <a class="btn" href="${pageContext.request.contextPath}/perfil-monitor">Perfil</a>
        <a class="notif" href="#notifPanel">
            <img src="Imagens/notifications_24dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Notificações">
        </a>
        <a class="btn" href="${pageContext.request.contextPath}/">Sair</a>
    </nav>
</header>

<main class="container">
    <h2>Minhas Aulas</h2>
    <p class="subtle">Gerencie solicitações, confirme aulas e veja o perfil dos alunos.</p>

    <c:if test="${not empty sessionScope.denunciaSucesso}">
        <div style="background-color: #dcfce7; color: #166534; padding: 1rem; border-radius: 8px; margin-bottom: 1rem; border: 1px solid #bbf7d0; text-align: center;">
            <strong>Sucesso!</strong> Denúncia enviada com sucesso.
        </div>
        <c:remove var="denunciaSucesso" scope="session" />
    </c:if>

    <div class="aulas-container">
        <div class="aulas-section">
            <h3>Solicitações e Agendadas</h3>
            <table>
                <thead><tr><th>Estudante</th><th>Disciplina</th><th>Data</th><th>Status</th><th>Ações</th></tr></thead>
                <tbody>
                <c:forEach var="a" items="${aulas}">
                    <c:if test="${a.status == 'PENDENTE' || a.status == 'ACEITA'}">
                        <tr>
                            <td><a class="nome-link" href="#">${a.nomeUsuarioAssociado}</a></td>
                            <td>${a.disciplina}</td>
                            <td>${a.dataAulaFormatada}</td>
                            <td>
                                <span class="${a.status == 'PENDENTE' ? 'status wait' : 'status ok'}">${a.status}</span>
                            </td>
                            <td>
                                <a class="btn light" href="${pageContext.request.contextPath}/detalhes-aula?idAula=${a.id}">Detalhes</a>
                                <c:if test="${a.status == 'PENDENTE'}">
                                    <form action="${pageContext.request.contextPath}/monitor/aceitar-solicitacao" method="post" style="display:inline;">
                                        <input type="hidden" name="id" value="${a.id}" />
                                        <button class="btn ghost" type="submit">Confirmar</button>
                                    </form>
                                </c:if>
                                <c:if test="${a.status == 'ACEITA'}">
                                    <a class="btn" href="${pageContext.request.contextPath}/monitor/aula/concluir?idAula=${a.id}">Concluir</a>
                                </c:if>
                            </td>
                            <td>
                                <a class="btn-denunciar" href="#denunciaModal" data-denunciado-id="${a.idUsuarioAssociado}" data-aula-id="${a.id}">
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
            <h3>Aulas Concluídas</h3>
            <table>
                <thead><tr><th>Estudante</th><th>Disciplina</th><th>Data</th><th>Avaliação</th><th>Ações</th></tr></thead>
                <tbody>
                <c:forEach var="a" items="${aulas}">
                    <c:if test="${a.status == 'CONCLUIDA'}">
                        <tr>
                            <td><a class="nome-link" href="#">${a.nomeUsuarioAssociado}</a></td>
                            <td>${a.disciplina}</td>
                            <td>${a.dataAulaFormatada}</td>
                            <td>—</td>
                            <td><a class="btn light" href="${pageContext.request.contextPath}/avaliar?idAula=${a.id}">Avaliar</a></td>
                            <td>
                                <a class="btn-denunciar" href="#denunciaModal" data-denunciado-id="${a.idUsuarioAssociado}" data-aula-id="${a.id}">
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
</main>

<div id="denunciaModal" class="modal">
    <div class="modal-content">
        <h3>Registrar Denúncia contra Estudante</h3>
        <form action="${pageContext.request.contextPath}/denuncias" method="post">
            <input type="hidden" name="denunciadoId" id="idDenunciadoDenuncia" value="">
            <input type="hidden" name="aulaId" id="idAulaDenuncia" value="">
            <label class="field">
                <span>Motivo</span>
                <select class="select" name="motivo" required>
                    <option value="FALTA_SEM_AVISO">Falta sem aviso</option>
                    <option value="COMPORTAMENTO_INADEQUADO">Comportamento inadequado</option>
                    <option value="ASSEDIO_DISCURSO_OFENSIVO">Assédio / Discurso ofensivo</option>
                    <option value="OUTRO">Outro</option>
                </select>
            </label>
            <label class="field">
                <span>Descrição</span>
                <textarea class="textarea" name="descricao" placeholder="Descreva o ocorrido..."></textarea>
            </label>
            <div class="toolbar">
                <button type="submit" class="btn">Enviar Denúncia</button>
                <a class="btn ghost fechar-modal" href="#">Cancelar</a>
            </div>
        </form>
    </div>
</div>

<div id="confirmarCancelamento" class="modal">
    <div class="modal-content">
        <h3>Confirmar Cancelamento</h3>
        <form id="formCancelamento" action="#" method="post">
            <input type="hidden" name="idAula" id="inputModalIdAula" value="" />
            <p><b id="modalDisciplina"></b> - <b id="modalDataAula"></b></p>
            <div class="toolbar">
                <button class="btn" type="submit">Sim, cancelar aula</button>
                <a class="btn ghost fechar-modal" href="#">Voltar</a>
            </div>
        </form>
    </div>
</div>

<footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>

<script>
    document.addEventListener('click', (e) => {
        const btnDenunciar = e.target.closest('.btn-denunciar');
        const btnFechar = e.target.closest('.fechar-modal, .modal .btn.ghost');

        if (btnDenunciar) {
            document.getElementById('idDenunciadoDenuncia').value = btnDenunciar.dataset.denunciadoId;
            document.getElementById('idAulaDenuncia').value = btnDenunciar.dataset.aulaId;
        }

        if (btnFechar) {
            e.preventDefault();
            document.getElementById('confirmarCancelamento').style.display = 'none';
            window.location.hash = '';
        }
    });

    function abrirModalCancelamento(idAula, disciplina, dataAula, urlAction) {
        document.getElementById('modalDisciplina').innerText = disciplina;
        document.getElementById('modalDataAula').innerText = dataAula;
        document.getElementById('inputModalIdAula').value = idAula;
        document.getElementById('formCancelamento').action = urlAction;
        document.getElementById('confirmarCancelamento').style.display = 'flex';
    }
</script>
</body>
</html>
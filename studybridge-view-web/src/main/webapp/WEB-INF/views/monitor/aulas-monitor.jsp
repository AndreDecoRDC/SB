
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
            .aulas-container {
                display: grid;
                gap: 2rem;
                margin-top: 2rem;
            }
            @media (min-width: 950px) {
                .aulas-container {
                    grid-template-columns: repeat(2, 1fr);
                }
            }
            .aulas-section {
                flex: 1;
                background: #fff;
                border-radius: 10px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.05);
                padding: 2rem;
                max-width: 100%;
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
            th {
                background: #f3f4f6;
                color: #374151;
            }
            .status.ok {
                color: #16a34a;
                font-weight: 600;
            }
            .status.wait {
                color: #ca8a04;
                font-weight: 600;
            }
            .status.fail {
                color: #dc2626;
                font-weight: 600;
            }
            .nome-link {
                color: #1e3fae;
                font-weight: 600;
                cursor: pointer;
                text-decoration: none;
            }
            .nome-link:hover {
                text-decoration: underline;
            }
            td:last-child {
                padding-right: 0;
            }
            .stars10 {
                display: flex;
                flex-direction: row-reverse;
                justify-content: center;
                gap: 0.1rem;
            }
            .stars10 input {
                display: none;
            }
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
            .perfil-modal p {
                margin: 0.5rem 0;
            }
        </style>

    </head>

    <script>
        function abrirModalCancelamento(idAula, disciplina, dataAula, urlAction) {
            document.getElementById('modalDisciplina').innerText = disciplina;
            document.getElementById('modalDataAula').innerText = dataAula;

            document.getElementById('inputModalIdAula').value = idAula;

            document.getElementById('formCancelamento').action = urlAction;

            const modal = document.getElementById('confirmarCancelamento');
            if (modal) {
                modal.style.display = 'flex';
            }
    }

    document.addEventListener('DOMContentLoaded', () => {
        const fecharBotoes = document.querySelectorAll('.fechar-modal');
        const modal = document.getElementById('confirmarCancelamento');

        fecharBotoes.forEach(btn => {
            btn.addEventListener('click', (event) => {
                event.preventDefault();
                if (modal) {
                    modal.style.display = 'none';
                }
            });
        });

        if (modal) {
            modal.addEventListener('click', (event) => {
                if (event.target === modal) {
                    modal.style.display = 'none';
                }
            });
        }
    });
    </script>

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

            <div class="aulas-container">
                <div class="aulas-section">
                    <h3>Solicitações e Agendadas</h3>
                    <table>
                        <thead><tr><th>Estudante</th><th>Disciplina</th><th>Data</th><th>Status</th><th>Ações</th></tr></thead>
                        <tbody>
                            <c:set var="encontrouAtivas" value="${false}" />
                            <c:forEach var="a" items="${aulas}">
                                <c:if test="${a.status == 'PENDENTE' || a.status == 'ACEITA'}">
                                    <c:set var="encontrouAtivas" value="${true}" />
                                    <tr>
                                        <td><a class="nome-link" href="#">${a.nomeUsuarioAssociado}</a></td>
                                        <td>${a.disciplina}</td>
                                        <td>${a.dataAulaFormatada}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${a.status == 'PENDENTE'}"><span class="status wait">${a.status}</span></c:when>
                                                <c:when test="${a.status == 'ACEITA'}"><span class="status ok">${a.status}</span></c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a class="btn light" href="${pageContext.request.contextPath}/detalhes-aula?idAula=${a.id}">Detalhes</a>

                                            <c:choose>
                                                <c:when test="${a.status == 'PENDENTE'}">
                                                    <form action="${pageContext.request.contextPath}/monitor/aula/confirmar" method="post" style="display:inline;">
                                                        <input type="hidden" name="idAula" value="${a.id}" />
                                                        <button class="btn ghost" type="submit">Confirmar</button>
                                                    </form>
                                                    <a class="btn ghost" href="#">Recusar</a>
                                                </c:when>
                                                <c:when test="${a.status == 'ACEITA'}">
                                                    <a class="btn" href="${pageContext.request.contextPath}/monitor/aula/concluir?idAula=${a.id}">Concluir</a>
                                                    <button class="btn ghost"
                                                            onclick="abrirModalCancelamento('${a.id}', '${a.disciplina}', '${a.dataAulaFormatada}', '${pageContext.request.contextPath}/monitor/aula/cancelar')">
                                                        Cancelar
                                                    </button>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a class="btn-denunciar" href="#"
                                               data-denunciado-id="idDoAlunoMaria"
                                               data-aula-id="idDaAulaMaria">
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
                    <h3>Aulas Não Realizadas</h3>
                    <table>
                        <thead><tr><th>Estudante</th><th>Disciplina</th><th>Data</th><th>Status</th><th>Ações</th></tr></thead>
                        <tbody>
                            <c:set var="encontrouInativas" value="${false}" />
                            <c:forEach var="a" items="${aulas}">
                                <c:if test="${a.status == 'RECUSADA' || a.status == 'CANCELADA'}">
                                    <c:set var="encontrouInativas" value="${true}" />
                                    <tr>
                                        <td><a class="nome-link" href="#">${a.nomeUsuarioAssociado}</a></td>
                                        <td>${a.disciplina}</td>
                                        <td>${a.dataAulaFormatada}</td>
                                        <td><span class="status fail">${a.status}</span></td>
                                        <td>
                                            <a class="btn light" href="${pageContext.request.contextPath}/detalhes-aula?idAula=${a.id}">Detalhes</a>
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
                            <c:set var="encontrouConcluidas" value="${false}" />
                            <c:forEach var="a" items="${aulas}">
                                <c:if test="${a.status == 'CONCLUIDA'}">
                                    <c:set var="encontrouConcluidas" value="${true}" />
                                    <tr>
                                        <td><a class="nome-link" href="#">${a.nomeUsuarioAssociado}</a></td>
                                        <td>${a.disciplina}</td>
                                        <td>
                                            ${a.dataAulaFormatada}
                                        </td>
                                        <td>—</td>
                                        <td><a class="btn light" href="${pageContext.request.contextPath}/avaliar?idAula=${a.id}">Avaliar</a></td>
                                        <td>
                                            <a class="btn-denunciar" href="#"
                                               data-denunciado-id="idDoAlunoMaria"
                                               data-aula-id="idDaAulaMaria">
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

        <div id="detalhesAulaMaria" class="modal">
            <div class="modal-content">
                <h3>Detalhes da Solicitação</h3>
                <p><strong>Aluno:</strong> Maria Souza</p>
                <p><strong>Disciplina:</strong> Física</p>
                <p><strong>Data e horário:</strong> 18/10/2025 às 14:00</p>
                <p><strong>Mensagem enviada:</strong> “Mensagem genérica do aluno descrevendo o motivo da solicitação.”</p>
                <div class="toolbar"><a class="btn" href="#">Fechar</a></div>
            </div>
        </div>

<div id="avaliarAluno1" class="modal">
    <div class="modal-content">
        <h3>Avaliar Estudante</h3>
        <form action="${pageContext.request.contextPath}/avaliar" method="post">
            <input type="hidden" name="tipoConta" value="monitor">
            <input type="hidden" name="idAluno" id="idAlunoAvaliar" value="">
            <input type="hidden" name="idAula" id="idAulaAvaliar" value="">

            <div class="stars10">
                <input type="radio" id="t5" name="nota" value="5"><label for="t5">★</label>
                <input type="radio" id="t4" name="nota" value="4"><label for="t4">★</label>
                <input type="radio" id="t3" name="nota" value="3"><label for="t3">★</label>
                <input type="radio" id="t2" name="nota" value="2"><label for="t2">★</label>
                <input type="radio" id="t1" name="nota" value="1"><label for="t1">★</label>
            </div>
            <label class="field">
                <span>Comentário</span>
                <textarea class="textarea" name="comentario" placeholder="Como foi a participação do aluno?"></textarea>
            </label>
            <div class="toolbar">
                <button type="submit" class="btn">Enviar Avaliação</button>
                <a class="btn ghost" href="#">Cancelar</a>
            </div>
        </form>
    </div>
</div>

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
                    <option value="ASSEDIO">Assédio / Discurso ofensivo</option>
                    <option value="OUTRO">Outro</option>
                </select>
            </label>
            <label class="field">
                <span>Descrição</span>
                <textarea class="textarea" name="descricao" placeholder="Descreva o ocorrido..."></textarea>
            </label>
            <div class="toolbar">
                <button type="submit" class="btn">Enviar Denúncia</button>
                <a class="btn ghost" href="#">Cancelar</a>
            </div>
        </form>
    </div>
</div>

        <div id="perfilModalJoao" class="modal">
            <div class="modal-content perfil-modal">
                <h3>Perfil de João Pedro</h3>
                <div class="perfil-info">
                    <p><span>Curso:</span> Informática</p>
                    <p><b>Telefone de contato:</b> (99)9999-9999</p>
                    <p><span>Nota média:</span> ⭐ 4.8</p>
                    <p><span>Descrição:</span> Aluno dedicado</p>
                </div>
                <div class="toolbar"><a class="btn" href="#">Fechar</a></div>
            </div>
        </div>

<div id="perfilModalMaria" class="modal">
    <div class="modal-content perfil-modal">
        <h3>Perfil de Maria Souza</h3>
        <div class="perfil-info">
            <p><span>Curso:</span> Informática</p>
            <p><b>Telefone de contato:</b> (99)9999-9999</p>
            <p><span>Nota média:</span> ⭐ 4.8</p>
            <p><span>Descrição:</span> Aluna dedicada</p>
        </div>
        <div class="toolbar"><a class="btn ghost" href="#">Fechar</a></div>
    </div>
</div>

        <div id="notifPanel" class="modal">
            <div class="modal-content">
                <h3>Notificações</h3>
                <ul>
                    <li>Aula confirmada com <b>Maria Souza</b> — 18/10/2025 às 14:00</li>
                    <li>Nova mensagem de <b>João Pedro</b></li>
                    <li>⭐ Lembrete: Avalie o estudante da última aula</li>
                </ul>
                <div class="toolbar"><a class="btn" href="#">Fechar</a></div>
            </div>
        </div>

        <div id="confirmarCancelamento" class="modal">
            <div class="modal-content">
                <h3>Confirmar Cancelamento</h3>
                <p>Você está prestes a cancelar a aula:</p>
                <ul>
                    <li><b>Disciplina:</b> <span id="modalDisciplina"></span></li>
                    <li><b>Data:</b> <span id="modalDataAula"></span></li>
                </ul>

                <form id="formCancelamento" action="#" method="post">
                    <input type="hidden" name="idAula" id="inputModalIdAula" value="" />
                    <div class="toolbar" style="justify-content:center;">
                        <button class="btn" type="submit">Sim, cancelar aula</button>
                        <a class="btn ghost fechar-modal" href="#">Voltar</a>
                    </div>
                </form>
            </div>
        </div>

        <footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>
        <script>
            const botoesDenunciar = document.querySelectorAll('.btn-denunciar');
            botoesDenunciar.forEach(btn => {
                btn.addEventListener('click', (e) => {
                    e.preventDefault();

                    const idDenunciado = btn.dataset.denunciadoId;
                    const idAula = btn.dataset.aulaId;

                    document.getElementById('idDenunciadoDenuncia').value = idDenunciado;
                    document.getElementById('idAulaDenuncia').value = idAula;

                    window.location.hash = 'denunciaModal';
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
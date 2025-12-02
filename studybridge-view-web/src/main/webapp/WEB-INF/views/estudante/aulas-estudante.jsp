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
                color: #f59e0b;
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
        </style>
    </head>
    <body>
        <header class="header">
            <a href="${pageContext.request.contextPath}/" class="brand">
                <div class="logo">SB</div><strong>StudyBridge</strong>
            </a>
            <nav class="nav">
                <a class="btn" href="${pageContext.request.contextPath}/busca">Buscar Monitores</a>
                <a class="btn" href="${pageContext.request.contextPath}/aulas">Aulas</a>
                <a class="btn" href="${pageContext.request.contextPath}/perfil-estudante">Perfil</a>
                <%-- Mantive o botão de notificação da main --%>
                <a class="notif" href="#notifPanel"><img src="Imagens/notifications_24dp_1E3FAE_FILL0_wght400_GRAD0_opsz24.svg" alt="Notificações"></a>
                <a class="btn" href="${pageContext.request.contextPath}/">Sair</a>
            </nav>
        </header>

        <main class="container">
            <h2>Suas Aulas</h2>
            <p class="subtle">Veja suas aulas, detalhes e avaliações de monitores.</p>

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
                                        <td>
                                            <c:out value="${a.dataAulaFormatada}" />
                                        </td>
                                        <td><span class="${a.status == 'ACEITA' ? 'status ok' : 'status wait'}">${a.status}</span></td>
                                        <td>

                                            <a class="btn light" href="${pageContext.request.contextPath}/detalhes-aula?idAula=${a.id}">Detalhes</a>
                                            <a class="btn ghost" href="${pageContext.request.contextPath}/estudante/aula/confirmar-page?idAula=${a.id}">Cancelar</a>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <c:if test="${empty aulas}">
                                <tr><td colspan="5" style="text-align:center;">Nenhuma aula encontrada.</td></tr>
                            </c:if>
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
                                        <td>
                                            <c:out value="${a.dataAulaFormatada}" />
                                        </td>
                                        <td><span class="status fail">${a.status}</span></td>
                                        <td>
                                            <a class="btn light" href="${pageContext.request.contextPath}/detalhes-aula?idAula=${a.id}">Detalhes</a>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <c:if test="${empty aulas}">
                                <tr><td colspan="5" style="text-align:center;">Nenhuma aula aceita encontrada.</td></tr>
                            </c:if>
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
                                        <td>
                                            ${a.dataAulaFormatada}
                                        </td>
                                        <td>—</td>
                                        <td><a class="btn light" href="${pageContext.request.contextPath}/avaliar?idAula=${a.id}">Avaliar</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <c:if test="${empty aulas}">
                                <tr><td colspan="5" style="text-align:center;">Nenhuma aula concluída encontrada.</td></tr>
                            </c:if>
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
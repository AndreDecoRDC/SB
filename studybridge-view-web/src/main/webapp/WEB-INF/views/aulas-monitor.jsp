<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Aulas — Monitor | StudyBridge</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
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
                <a class="btn" href="${pageContext.request.contextPath}/">Sair</a>
            </nav>
        </header>

        <main class="container">
            <h2>Minhas Aulas</h2>
            <p class="subtle">Gerencie solicitações, confirme aulas e veja o perfil dos alunos.</p>

            <div class="aulas-container">
                <div class="aulas-section">
                    <h3>Solicitações Pendentes</h3>
                    <table>
                        <thead><tr><th>Estudante</th><th>Disciplina</th><th>Data</th><th>Status</th><th>Ações</th></tr></thead>
                        <tbody>
                            <c:forEach var="a" items="${aulas}">
                                <c:if test="${a.status == 'PENDENTE'}">
                                    <tr>
                                        <td>Estudante #${a.id_estudante}</td>
                                        <td>${a.disciplina}</td>
                                        <td><c:out value="${a.data_aula}" /></td>
                                        <td><span class="status wait">${a.status}</span></td>
                                        <td>
                                            <a class="btn light" href="${pageContext.request.contextPath}/detalhes-aula?idAula=${a.id}">Detalhes</a>

                                            <form action="${pageContext.request.contextPath}/monitor/aula/confirmar" method="post" style="display:inline;">
                                                <input type="hidden" name="idAula" value="${a.id}" />
                                                <button class="btn" type="submit">Confirmar</button>
                                            </form>

                                            <a class="btn ghost" href="${pageContext.request.contextPath}/monitor/aula/recusar-page?idAula=${a.id}">Recusar</a>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>

                            <c:if test="${empty aulas}">
                                <tr><td colspan="5" style="text-align:center;">Nenhuma solicitação encontrada.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

                <div class="aulas-section">
                    <h3>Aulas Concluídas</h3>
                    <table>
                        <thead><tr><th>Estudante</th><th>Disciplina</th><th>Data</th><th>Média</th><th>Ações</th></tr></thead>
                        <tbody>
                            <c:forEach var="a" items="${aulas}">
                                <c:if test="${a.status == 'CONCLUIDA'}">
                                    <tr>
                                        <td>Estudante #${a.id_estudante}</td>
                                        <td>${a.disciplina}</td>
                                        <td><c:out value="${a.data_aula}" /></td>
                                        <td>—</td>
                                        <td><a class="btn light" href="${pageContext.request.contextPath}/avaliar?idAula=${a.id}">Avaliar</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>

        <footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>
    </body>
</html>

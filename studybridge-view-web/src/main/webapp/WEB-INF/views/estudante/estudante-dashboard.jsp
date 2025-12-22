<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="pt-BR">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Dashboard do Estudante — StudyBridge</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />

        <style>
            .tabela-historico {
                width: 100%;
                border-collapse: collapse;
                margin-top: 16px;
            }

            .tabela-historico th,
            .tabela-historico td {
                padding: 12px 20px;   /* AQUI está o afastamento */
                text-align: left;
            }

            .tabela-historico thead th {
                font-weight: 600;
                color: #374151;
                border-bottom: 2px solid #e5e7eb;
            }

            .tabela-historico tbody tr {
                border-bottom: 1px solid #e5e7eb;
            }

            .tabela-historico tbody tr:last-child {
                border-bottom: none;
            }
        </style>
        
    </head>

    <body>

        <header class="header">
            <a href="${pageContext.request.contextPath}/estudante/dashboard" class="brand">
                <div class="logo">SB</div><strong>StudyBridge</strong>
            </a>
            <nav class="nav">
                <a class="btn" href="${pageContext.request.contextPath}/estudante/busca">Buscar Monitores</a>
                <a class="btn" href="${pageContext.request.contextPath}/estudante/aulas-estudante">Aulas</a>
                <a class="btn" href="${pageContext.request.contextPath}/estudante/perfil">Perfil</a>

                <a class="notif" href="#">
                    <img src="${pageContext.request.contextPath}/images/notifications.svg" alt="Notificações">
                </a>
                <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
            </nav>
        </header>

        <main class="container">
            <h2>Bem-vindo(a), Estudante</h2>
            <p class="subtle">Gerencie suas aulas e monitores no CEFET-MG — Campus Belo Horizonte.</p>

            <div class="grid-3">

                <div class="card">
                    <h3>Próxima Aula</h3>
                    <c:choose>
                        <c:when test="${not empty proximaAula}">
                            <p><b>${proximaAula.disciplina} com ${proximaAula.nomeUsuarioAssociado}</b></p>
                            <p>${proximaAula.dataAulaFormatada}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="subtle">Nenhuma aula marcada.</p>
                        </c:otherwise>
                    </c:choose>
                    <a class="btn" href="${pageContext.request.contextPath}/estudante/aulas-estudante">Ver aulas marcadas</a>
                </div>

                <div class="card">
                    <h3>Monitor Recomendado</h3>
                    <c:choose>
                        <c:when test="${not empty monitorBom}">
                            <p><b>${monitorBom.disciplina} com ${monitorBom.nome}</b></p>
                            <p>Nota média: <b>${monitorBom.mediaAvaliacao}</b></p>
                        </c:when>
                        <c:otherwise>
                            <p class="subtle">Ainda não temos recomendações.</p>
                        </c:otherwise>
                    </c:choose>
                    <a class="btn" href="${pageContext.request.contextPath}/estudante/busca">Buscar Monitores</a>
                </div>

                <div class="card">
                    <h3>Histórico de Aulas</h3>
                    <c:choose>
                        <c:when test="${not empty ultimaAula}">
                            <p><b>Última aula:</b> ${ultimaAula.disciplina} com ${ultimaAula.nomeUsuarioAssociado}</p>
                            <p>Data: ${ultimaAula.dataAulaFormatada}</p>
                        </c:when>
                        <c:otherwise>
                            <p class="subtle">Você não concluiu nenhuma aula.</p>
                        </c:otherwise>
                    </c:choose>
                    <a class="btn" href="#" onclick="abrirHistorico()">Ver histórico completo</a>
                </div>

            </div>
        </main>

        <footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>

        <div id="modalHistorico" class="modal">
            <div class="modal-content" style="max-width: 900px;">
                <span class="close" onclick="fecharHistorico()">×</span>

                <h3 style="color: #2563eb">Histórico de Aulas</h3>

                <c:choose>
                    <c:when test="${empty historico}">
                        <p class="subtle">Você ainda não concluiu nenhuma aula.</p>
                    </c:when>
                    <c:otherwise>
                        <table class="tabela-historico">
                            <thead>
                                <tr>
                                    <th>Monitor</th>
                                    <th>Disciplina</th>
                                    <th>Data</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="a" items="${historico}">
                                    <tr>
                                        <td style="color: #2563eb">${a.nomeUsuarioAssociado}</td>
                                        <td>${a.disciplina}</td>                                        
                                        <td>${a.dataAulaFormatada}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <script>
function abrirHistorico() {
    document.getElementById("modalHistorico").style.display = "flex";
}

function fecharHistorico() {
    document.getElementById("modalHistorico").style.display = "none";
}
        </script>

    </body>
</html>

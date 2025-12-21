<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<p>Total de denúncias vindas do Java: ${denuncias.size()}</p>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Denúncias — Administração | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    <style>
        .perfil-modal { max-width: 500px; text-align: left; }
        .perfil-modal h3 { margin-bottom: 0.8rem; color: #1e3fae; }
        .perfil-info { line-height: 1.6; margin-bottom: 1rem; }
        .perfil-info p { margin: 0.3rem 0; }
        .perfil-info span { font-weight: 600; color: #374151; }
        .nome-link { color: #1e3fae; font-weight: 600; text-decoration: none; }
        .nome-link:hover { text-decoration: underline; }
        .modal:target { display: flex !important; }
        .detalhe-box { background: #f3f4f6; padding: 1.2rem; border-radius: 8px; margin-top: 1rem; border-left: 4px solid #1e3fae; }
        .detalhe-box b { color: #1e3fae; display: block; margin-bottom: 5px; text-transform: uppercase; font-size: 0.8rem; }
        .descricao-texto { white-space: pre-wrap; color: #4b5563; margin-top: 10px; }
    </style>
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/admin/denuncias" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="${pageContext.request.contextPath}/admin/usuarios">Buscar Usuários</a>
        <a class="btn" href="${pageContext.request.contextPath}/admin/denuncias">Denúncias</a>
        <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
    </nav>
</header>

<main class="container">
    <section class="section">
        <h2>Gerenciar Denúncias</h2>
        <p class="subtle">Analise os relatos abaixo e tome as providências necessárias.</p>
    </section>

    <c:choose>
        <c:when test="${not empty denuncias}">
            <table class="table">
                <thead>
                <tr><th>ID</th><th>Denunciado</th><th>Motivo</th><th>Ações</th></tr>
                </thead>
                <tbody>
                <c:forEach var="d" items="${denuncias}">
                    <tr>
                        <td>D-${d.id}</td>
                        <td>
                            <a href="#perfil-${d.usuarioDenunciado.id}" class="nome-link">
                                <strong>${not empty d.usuarioDenunciado.nome ? d.usuarioDenunciado.nome : 'Monitor Indisponível'}</strong>
                            </a>
                        </td>
                        <td>${d.motivoDenuncia}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/denuncias" method="post" style="display:inline;">
                                <input type="hidden" name="idUsuario" value="${d.usuarioDenunciado.id}">
                                <input type="hidden" name="idDenuncia" value="${d.id}">
                                <button type="submit" class="btn ghost"
                                        style="color: #dc2626; border-color: #dc2626;"
                                        onclick="return confirm('Tem certeza que deseja SUSPENDER a conta de ${d.usuarioDenunciado.nome}?')">
                                    Suspender
                                </button>
                            </form>
                            <a class="btn ghost" href="#detalhes-${d.id}">Detalhes da Denúncia</a>
                        </td>
                    </tr>

                    <div id="perfil-${d.usuarioDenunciado.id}" class="modal">
                        <div class="modal-content perfil-modal">
                            <h3>Perfil de ${d.usuarioDenunciado.nome}</h3>
                            <div class="perfil-info">
                                <p><span>Nome:</span> ${d.usuarioDenunciado.nome}</p>
                                <p><span>Tipo de Conta:</span> ${d.usuarioDenunciado.tipoConta}</p>
                                <p><span>E-mail:</span> ${d.usuarioDenunciado.email}</p>
                                <p><span>Status Atual:</span>
                                    <b style="color: ${d.usuarioDenunciado.ativa ? '#16a34a' : '#dc2626'}">
                                            ${d.usuarioDenunciado.ativa ? 'ATIVO' : 'SUSPENSO'}
                                    </b>
                                </p>
                            </div>
                            <div class="toolbar"><a class="btn" href="#">Fechar</a></div>
                        </div>
                    </div>

                    <div id="detalhes-${d.id}" class="modal">
                        <div class="modal-content perfil-modal">
                            <h3>Relatório de Denúncia D-${d.id}</h3>
                            <div class="perfil-info">
                                <p><span>Denunciante:</span> ${d.usuarioDenunciante.nome} (${d.usuarioDenunciante.tipoConta})</p>
                                <p><span>Denunciado:</span> ${d.usuarioDenunciado.nome} (${d.usuarioDenunciado.tipoConta})</p>
                            </div>
                            <div class="detalhe-box">
                                <b>Motivo da Denúncia</b>
                                <p>${d.motivoDenuncia}</p>
                                <div style="margin: 15px 0; border-top: 1px solid #d1d5db;"></div>
                                <b>Descrição Detalhada</b>
                                <p class="descricao-texto">${not empty d.descricao ? d.descricao : 'O denunciante não forneceu uma descrição adicional.'}</p>
                            </div>
                            <div class="toolbar"><a class="btn" href="#">Fechar</a></div>
                        </div>
                    </div>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div style="text-align: center; padding: 3rem; color: #6b7280;">
                <p>Não há denúncias registradas no momento.</p>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<footer class="footer">© 2025 StudyBridge — Administração | CEFET-MG Campus Belo Horizonte</footer>
</body>
</html>
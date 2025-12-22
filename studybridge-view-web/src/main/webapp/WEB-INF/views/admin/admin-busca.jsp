<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="pt-BR">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <title>Buscar Usuários — StudyBridge</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
        <style>
            .filters {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 1.2rem;
                margin-top: 1rem;
            }
            .filters .field {
                flex: 1 1 220px;
                min-width: 220px;
            }
            .results {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                gap: 1.5rem;
                margin-top: 2rem;
            }

            .modal {
                display: none;
                position: fixed;
                inset: 0;
                background: rgba(0,0,0,0.6);
                justify-content: center;
                align-items: center;
                z-index: 1000;
            }

            .modal-content {
                background: #fff;
                padding: 2rem;
                border-radius: 12px;
                width: 90%;
                max-width: 500px;
            }

            .close {
                float: right;
                font-size: 1.5rem;
                cursor: pointer;
            }

            .user-email {
                color: #2563eb;
                cursor: pointer;
                text-decoration: underline;
            }
        </style>
    </head>

    <body>

        <header class="header">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">
                <div class="logo">SB</div><strong>StudyBridge</strong>
            </a>
            <nav class="nav">
                <a class="btn" href="${pageContext.request.contextPath}/admin/busca">Buscar Usuários</a>
                <a class="btn" href="#">Denúncias</a>
                <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
            </nav>
        </header>

        <main class="container">
            <h2>Buscar Usuário</h2>

            <div class="card">
                <form action="${pageContext.request.contextPath}/admin/busca" method="get">

                    <div class="filters">
                        <label class="field">
                            <span>Nome do Usuário</span>
                            <input class="input"
                                   name="nome"
                                   placeholder="Busque aqui"
                                   value="${nome}">

                        </label>

                        <label class="field">
                            <span>Tipo</span>
                            <select class="select" name="tipo">
                                <option value="">Todos</option>
                                <option value="Estudante" ${tipo == 'Estudante' ? 'selected' : ''}>
                                    Estudante
                                </option>
                                <option value="Monitor" ${tipo == 'Monitor' ? 'selected' : ''}>
                                    Monitor
                                </option>
                            </select>

                        </label>

                        <label class="field">
                            <span>Ordenar</span>
                            <select class="select" name="ordenar">
                                <option value="alfabetica" ${ordenar == 'alfabetica' ? 'selected' : ''}>
                                    Alfabeticamente
                                </option>
                                <option value="avaliacao" ${ordenar == 'avaliacao' ? 'selected' : ''}>
                                    Maior Avaliação
                                </option>
                            </select>

                        </label>
                    </div>

                    <div class="toolbar" style="justify-content:center;margin-top:1.5rem;">
                        <button type="submit" class="btn">Aplicar filtros</button>
                        <a href="${pageContext.request.contextPath}/admin/busca" class="btn ghost">
                            Limpar
                        </a>

                    </div>

                </form>
            </div>

            <c:if test="${not empty usuarios}">
                <div class="results">
                    <c:forEach var="u" items="${usuarios}">
                        <div class="card user-card">
                            <a href="javascript:void(0)"
                               class="user-nome"
                               onclick="abrirModal(
                        '${u.email}',
                        '${u.tipoConta}',
                        '${u.verificado}',
                        '${u.nome}',
                        '${u.telefone}',
                        '${u.curso}',
                        '${u.disciplina}',
                        '${u.campus}',
                        '${u.descricao}'
                   )">
                                ${u.nome}
                            </a>
                        </div>
                    </c:forEach>
                </div>
</c:if>

        </div>
    </main>

    <footer class="footer">
        © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
    </footer>


    <div id="modalUsuario" class="modal">
        <div class="modal-content">
            <span class="close" onclick="fecharModal()">&times;</span>

            <h3 style="color: #2563eb">Detalhes do Usuário</h3>

            <p><strong>Nome:</strong> <span id="mNome"></span></p>
            <p><strong>Email:</strong> <span id="mEmail"></span></p>
            <p><strong>Tipo:</strong> <span id="mTipo"></span></p>
            <p><strong>Verificado:</strong> <span id="mVerificado"></span></p>

            <p><strong>Telefone:</strong> <span id="mTelefone"></span></p>
            <p><strong>Curso / Disciplina:</strong> <span id="mExtra"></span></p>
            <p><strong>Campus:</strong> <span id="mCampus"></span></p>
            <p><strong>Descrição:</strong></p>
            <p id="mDescricao"></p>
        </div>
    </div>

    <script>
        function abrirModal(email, tipo, verificado, nome, telefone, curso, disciplina, campus, descricao) {
            
            document.getElementById("mNome").innerText = nome || '-';
            document.getElementById("mEmail").innerText = email;
            document.getElementById("mTipo").innerText = tipo;
            document.getElementById("mVerificado").innerText = verificado === 'true' ? 'Sim' : 'Não';
            document.getElementById("mTelefone").innerText = telefone || '-';
            document.getElementById("mExtra").innerText = curso || disciplina || '-';
            document.getElementById("mCampus").innerText = campus || '-';
            document.getElementById("mDescricao").innerText = descricao || '-';

            document.getElementById("modalUsuario").style.display = "flex";
        }

        function fecharModal() {
            document.getElementById("modalUsuario").style.display = "none";
        }
    </script>


</body>
</html>

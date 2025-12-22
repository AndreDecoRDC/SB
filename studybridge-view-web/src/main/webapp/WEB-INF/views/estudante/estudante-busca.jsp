<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>Buscar Monitores | StudyBridge</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    </head>
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
    <body>

        <header class="header">
            <a href="${pageContext.request.contextPath}/estudante/dashboard" class="brand">
                <div class="logo">SB</div><strong>StudyBridge</strong>
            </a>
            <nav class="nav">
                <a class="btn" href="${pageContext.request.contextPath}/estudante/busca">Buscar Monitores</a>
                <a class="btn" href="${pageContext.request.contextPath}/estudante/aulas-estudante">Aulas</a>
                <a class="btn" href="${pageContext.request.contextPath}/estudante/perfil">Perfil</a>
                <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
            </nav>
</header>

<main class="container">

    <h2>Buscar Monitores</h2>
    <p class="subtle">Veja os monitores disponíveis e seus horários.</p>

    <div class="card">
        <form action="${pageContext.request.contextPath}/estudante/busca" method="get">

            <div class="filters">

                <label class="field">
                    <span>Nome do Monitor</span>
                    <input class="input"
                           name="nome"
                           placeholder="Busque aqui"
                           value="${nome}">
                </label>

                           <label class="field">
                               <span>Disciplina</span>
                               <input class="input"
                                      type="text"
                                      name="disciplina"
                                      placeholder="Ex: Matemática, Física..."
                                      value="${disciplina}">
                           </label>

                           <label class="field">
                               <span>Ordenar por</span>
                               <select class="select" name="ordenar">
                                <option value="alfabetica" ${ordenar == 'alfabetica' ? 'selected' : ''}>
                                    Ordem Alfabética
                                </option>
                                <option value="avaliacao" ${ordenar == 'avaliacao' ? 'selected' : ''}>
                                    Maior Avaliação
                                </option>
                            </select>
                        </label>

            </div>

            <div class="toolbar center">
                <button type="submit" class="btn">Aplicar filtros</button>
                <a href="${pageContext.request.contextPath}/estudante/busca" class="btn ghost">
                    Limpar
                </a>
            </div>

        </form>
    </div>

    <c:if test="${not empty monitores}">
        <div class="grid-2">

            <c:forEach var="m" items="${monitores}">
                <div class="card monitor-card">

                    <h3>${m.nome}</h3>

                    <p class="subtle">
                        ${m.disciplina} • ⭐ ${m.mediaAvaliacao}
                    </p>

                    <p class="quote">
                        "${m.descricao}"
                    </p>

                    <div class="actions">
                        <button class="btn ghost"
                                onclick="abrirModal(
                                    '${m.monitorId}',
                                    '${m.nome}',
                                    '${m.disciplina}',
                                    '${m.telefone}',
                                    '${m.mediaAvaliacao}',
                                    '${m.descricao}'
                                )">
                            Ver Perfil
                        </button>

                        <a class="btn"
                           href="${pageContext.request.contextPath}/estudante/solicitar?usuarioId=${m.usuarioId}">
                            Solicitar aula
                        </a>
                    </div>
                </div>
            </c:forEach>

        </div>
    </c:if>

</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
                    
<div id="modalUsuario" class="modal">
    <div class="modal-content">
        <span class="close" onclick="fecharModal()">×</span>
        
        <h3 style="color: #2563eb">Detalhes do Monitor: </h3>
        <h3 id="mNome"></h3>

        <p><strong>Disciplina:</strong> <span id="mDisciplina"></span></p>
        <p><strong>Telefone:</strong> <span id="mTelefone"></span></p>
        <p><strong>Nota média:</strong> ⭐ <span id="mNota"></span></p>

        <p><strong>Descrição:</strong></p>
        <p id="mDescricao"></p>
        
        <h4>Horários disponíveis</h4>

        <iframe
            id="frameHorarios"
            style="width:100%; border:none; min-height:120px;">
        </iframe>

    </div>
</div>

<script>
function abrirModal(usuarioId, nome, disciplina, telefone, nota, descricao) {
    
    document.getElementById("mNome").innerText = nome;
    document.getElementById("mDisciplina").innerText = disciplina;
    document.getElementById("mTelefone").innerText = telefone || '-';
    document.getElementById("mNota").innerText = nota;
    document.getElementById("mDescricao").innerText = descricao;

    document.getElementById("frameHorarios").src =
        "${pageContext.request.contextPath}/estudante/ver-horarios?monitorId=" + usuarioId;

    document.getElementById("modalUsuario").style.display = "flex";
}

function fecharModal() {
    document.getElementById("modalUsuario").style.display = "none";
}
</script>

</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Dashboard do Estudante — StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
    <style>
        .notif-modal {
        position: absolute;
        top: 60px;
        right: 20px;
        width: 300px;
        background: #fff;
        border: 1px solid #ddd;
        border-radius: 6px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.15);
        padding: 10px;
        z-index: 1000;
    }

    .notif-modal.hidden {
        display: none;
    }

    .notif-item {
        padding: 8px;
        border-bottom: 1px solid #eee;
    }

    .notif-item a {
        text-decoration: none;
        color: #333;
        font-size: 14px;
    }

    .notif-item:hover {
        background: #f5f5f5;
    }
    </style>
</head>

<body>

<header class="header">
    <a href="${pageContext.request.contextPath}/estudante/dashboard" class="brand">
        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
    <nav class="nav">
        <a class="btn" href="#">Buscar Monitores</a>
        <a class="btn" href="${pageContext.request.contextPath}/estudante/aulas-estudante">Aulas</a>
        <a class="btn" href="${pageContext.request.contextPath}/estudante/perfil">Perfil</a>

        <a href="#" onclick="abrirNotificacoes(event)" class="notif">
            🔔
        </a>
        <div id="notifModal" class="notif-modal hidden">
            <h4>Notificações</h4>
            <div id="notifLista">
                <p>Carregando...</p>
            </div>
        </div>


        <a class="btn" href="${pageContext.request.contextPath}/logout">Sair</a>
    </nav>
</header>

<main class="container">
    <h2>Bem-vindo(a), Estudante</h2>
    <p class="subtle">Gerencie suas aulas e monitores no CEFET-MG — Campus Belo Horizonte.</p>

    <div class="grid-3">

        <div class="card">
            <h3>Próxima Aula</h3>
            <p><b>Matemática com Carla Mendes</b></p>
            <p>15/10/2025 às 14:00</p>
            <a class="btn" href="${pageContext.request.contextPath}/estudante/aulas-estudante">Ver aulas marcadas</a>
        </div>

        <div class="card">
            <h3>Monitores Recomendados</h3>
            <p><b>Física com Rafael Costa</b></p>
            <p>Nota média: <b>4.7</b></p>
            <a class="btn" href="#">Buscar Monitores</a>
        </div>

        <div class="card">
            <h3>Histórico de Aulas</h3>
            <p><b>Última aula:</b> Programação com Ana Paula</p>
            <p>05/10/2025 — Avaliação: ⭐ 4.5</p>
            <a class="btn" href="#">Ver histórico completo</a>
        </div>

    </div>
</main>
    <div id="notifModal" class="modal hidden">
        <h3>Notificações</h3>

        <c:forEach items="${notificacoes}" var="n">
            <div class="notif-item ${n.lida ? 'lida' : 'nao-lida'}">
                <a href="${n.link}">
                        ${n.mensagem}
                </a>
            </div>
        </c:forEach>
    </div>
        <script>
            function abrirNotificacoes(e) {
            e.preventDefault();

            const modal = document.getElementById("notifModal");
            modal.classList.toggle("hidden");

            if (!modal.classList.contains("hidden")) {
            carregarNotificacoes();
        }
        }

            function carregarNotificacoes() {
            fetch("${pageContext.request.contextPath}/notificacoes/nao-lidas")
                .then(resp => resp.json())
                .then(lista => {
                    const div = document.getElementById("notifLista");
                    div.innerHTML = "";

                    if (lista.length === 0) {
                        div.innerHTML = "<p>Nenhuma notificação nova.</p>";
                        return;
                    }

                    lista.forEach(n => {
                        const item = document.createElement("div");
                        item.className = "notif-item";

                        const link = document.createElement("a");
                        link.href = "${pageContext.request.contextPath}" + n.link;
                        link.innerText = n.mensagem;

                        item.appendChild(link);
                        div.appendChild(item);
                    });
                })
                .catch(() => {
                    document.getElementById("notifLista")
                        .innerHTML = "<p>Erro ao carregar notificações.</p>";
                });
        }
    </script>

<footer class="footer">© 2025 StudyBridge — CEFET-MG Campus Belo Horizonte</footer>

</body>
</html>

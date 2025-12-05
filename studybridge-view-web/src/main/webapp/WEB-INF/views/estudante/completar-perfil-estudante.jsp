<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="pt-BR">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Completar Perfil — Estudante | StudyBridge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
<header class="header">
    <a href="${pageContext.request.contextPath}/" class="brand">

        <div class="logo">SB</div><strong>StudyBridge</strong>
    </a>
</header>

<main class="container" style="max-width:600px;">
    <div class="card">
        <h2>Complete seu Perfil</h2>
        <p class="subtle">
            Antes de continuar, preencha suas informações. Elas serão usadas para identificação dentro do CEFET-MG — Campus BH.
        </p>

        <form action="${pageContext.request.contextPath}/salvar-perfil-estudante" method="post">
            <label class="field">
                <span>Nome completo</span>
                <input class="input" name="nome" required placeholder="Seu nome completo">
            </label>

            <label class="field">
                <span>Telefone</span>
                <input class="input" name="telefone" placeholder="(31) 9 9999-9999" required>
            </label>

            <label class="field">
                <span>Curso</span>
                <input class="input" name="curso" placeholder="Ex: Informática" required>
            </label>

            <label class="field">
                <span>Ano/Turma</span>
                <input class="input" name="turma" placeholder="Ex: 2° ano / Turma A" required>
            </label>

            <label class="field">
                <span>Número de Matrícula</span>
                <input class="input" name="matricula" placeholder="20252004212" required>
            </label>

            <label class="field">
                <span>Campus</span>
                <select class="select" name="campus" required>
                    <option value="">Selecione...</option>
                    <option>Nova Suíça</option>
                    <option>Nova Gameleira</option>
                </select>
            </label>

            <label class="field">
                <span>Descrição</span>
                <textarea class="textarea" name="descricao" placeholder="Fale um pouco sobre você ou as disciplinas que você quer monitorar" required></textarea>
            </label>

            <div class="toolbar" style="justify-content:center;margin-top:16px;">
                <button type="submit" class="btn">Salvar e continuar</button>
            </div>
        </form>
    </div>
</main>

<footer class="footer">
    © 2025 StudyBridge — CEFET-MG Campus Belo Horizonte
</footer>
</body>
</html>

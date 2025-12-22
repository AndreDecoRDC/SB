CREATE DATABASE IF NOT EXISTS studybridge DEFAULT CHARACTER SET utf8mb4;
USE studybridge;

--senha_hash padrão de todos os admin: admin123

INSERT INTO usuarios (email, senha_hash, tipo_conta, verificado)
SELECT 'beatrizpiedade1@gmail.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador', 1
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'beatrizpiedade1@gmail.com');

INSERT INTO usuarios (email, senha_hash, tipo_conta, verificado)
SELECT 'lilithgarbazza@gmail.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador', 1
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'lilithgarbazza@gmail.com');

INSERT INTO usuarios (email, senha_hash, tipo_conta, verificado)
SELECT 'Guilhermemfpereira@gmail.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador', 1
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'Guilhermemfpereira@gmail.com');

INSERT INTO usuarios (email, senha_hash, tipo_conta, verificado)
SELECT 'decosprite123@gmail.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador', 1
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'decosprite123@gmail.com');

SHOW TABLES;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    tipo_conta ENUM('Estudante','Monitor','Administrador') NOT NULL,
    verificado BOOLEAN NOT NULL DEFAULT 0,
    token_verificacao VARCHAR(100) UNIQUE,
    codigo_2fa VARCHAR(6),
    expiracao_2fa DATETIME,
    token_redefinicao VARCHAR(100),
    expiracao_redefinicao DATETIME
    );

CREATE TABLE IF NOT EXISTS avaliacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    nota DOUBLE NOT NULL,
    comentario VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

CREATE TABLE IF NOT EXISTS monitores (
	id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
	nome VARCHAR(255) NULL,
    telefone VARCHAR(20) NULL,
    disciplina VARCHAR(100) NULL,
    campus ENUM('Nova Suica', 'Nova Gameleira') NULL,
    descricao VARCHAR(1000),

    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS estudantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    nome VARCHAR(255) NULL,
    telefone VARCHAR(20) NULL,
    curso VARCHAR(100) NULL,
    ano_turma VARCHAR(100) NULL,
    campus ENUM('Nova Suica', 'Nova Gameleira') NULL,
    descricao VARCHAR(1000),

    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS horarios_disponiveis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    monitor_id INT NOT NULL,
    dia_da_semana ENUM('Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira') NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_termino TIME NOT NULL,
    duracao_media_aula INT NOT NULL,

    FOREIGN KEY (monitor_id) REFERENCES monitores(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS solicitacoes_aula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_estudante VARCHAR(255) NOT NULL,
    id_monitor VARCHAR(255) NULL,
    disciplina VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_solicitacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDENTE', 'ACEITA', 'RECUSADA', 'CANCELADA', 'CONCLUIDA') NOT NULL DEFAULT 'PENDENTE',
    data_aula DATETIME NULL,
    local VARCHAR(200) NULL,

   CONSTRAINT fk_solicitacao_estudante FOREIGN KEY (id_estudante)
       REFERENCES usuarios(email)
       ON DELETE CASCADE
       ON UPDATE CASCADE,

   CONSTRAINT fk_solicitacao_monitor FOREIGN KEY (id_monitor)
       REFERENCES usuarios(email)
       ON DELETE SET NULL
       ON UPDATE CASCADE
);

CREATE TABLE notificacoes IF NOT EXISTS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email_destinatario VARCHAR(255) NOT NULL,
    tipo ENUM('SOLICITACAO_AULA', 'STATUS_AULA') NOT NULL,
    mensagem VARCHAR(255) NOT NULL,
    link VARCHAR(255) NULL,
    lida BOOLEAN NOT NULL DEFAULT FALSE,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notif_usuario
          FOREIGN KEY (email_destinatario)
              REFERENCES usuarios(email)
              ON DELETE CASCADE
              ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS denuncias(
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_denunciante_id  INT NOT NULL,
    usuario_denunciado_id INT NOT NULL,
    motivo VARCHAR(50) NOT NULL,
    descricao TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    CONSTRAINT fk_denuncia_denunciante
        FOREIGN KEY(usuario_denunciante_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_denuncia_denunciado
        FOREIGN KEY(usuario_denunciado_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

ALTER TABLE estudantes
    MODIFY nome VARCHAR(255) NULL,
    MODIFY telefone VARCHAR(20) NULL,
    MODIFY curso VARCHAR(100) NULL,
    MODIFY campus ENUM('Nova Suica','Nova Gameleira') NULL;

ALTER TABLE monitores
    MODIFY nome VARCHAR(255) NULL,
    MODIFY telefone VARCHAR(20) NULL,
    MODIFY disciplina VARCHAR(100) NULL,
    MODIFY campus ENUM('Nova Suica','Nova Gameleira') NULL;

SELECT
    u.id            AS usuario_id,
    u.email,
    u.verificado,

    e.nome,
    e.telefone,
    e.curso,
    e.ano_turma,
    e.campus,
    e.descricao

FROM usuarios u
         JOIN estudantes e ON e.usuario_id = u.id
WHERE u.tipo_conta = 'Estudante'
ORDER BY u.id;

SELECT
    sa.*,
    e.nome AS nome_usuario_associado,
    sa.id_estudante AS id_usuario_associado
FROM
    solicitacoes_aula sa
JOIN
    estudantes e ON sa.id_estudante = e.usuario_id
WHERE
    sa.id_monitor = ?
;

SELECT
    u.id            AS usuario_id,
    u.email,
    u.verificado,

    m.nome,
    m.telefone,
    m.disciplina,
    m.campus,
    m.descricao

FROM usuarios u
         JOIN monitores m ON m.usuario_id = u.id
WHERE u.tipo_conta = 'Monitor'
ORDER BY u.id;

USE studybridge;
SELECT disciplina, data_aula, descricao
FROM solicitacoes_aula
ORDER BY id DESC;

USE studybridge;
SELECT id, email, tipo_conta, verificado, token_verificacao, codigo_2fa, expiracao_2fa
FROM usuarios
ORDER BY id DESC;

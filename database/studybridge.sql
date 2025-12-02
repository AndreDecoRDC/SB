CREATE DATABASE IF NOT EXISTS studybridge DEFAULT CHARACTER SET utf8mb4;
USE studybridge;

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
                                         nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    disciplina VARCHAR(100) NOT NULL,
    campus ENUM('Nova Suica', 'Nova Gameleira') NOT NULL,
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
                                                 id_estudante VARCHAR(30) NOT NULL,
                                                 id_monitor VARCHAR(30) NULL,
                                                 disciplina VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_solicitacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDENTE', 'ACEITA', 'RECUSADA', 'CANCELADA', 'CONCLUIDA') NOT NULL DEFAULT 'PENDENTE',
    data_aula DATETIME NULL,
    local VARCHAR(200) NULL,

    CONSTRAINT fk_solicitacao_estudante FOREIGN KEY (id_estudante)
    REFERENCES usuarios(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    CONSTRAINT fk_solicitacao_monitor FOREIGN KEY (id_monitor)
    REFERENCES usuarios(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
    );

USE studybridge;
SELECT disciplina, data_aula, descricao
FROM solicitacoes_aula
ORDER BY id DESC;

USE studybridge;
SELECT id, email, tipo_conta, verificado, token_verificacao, codigo_2fa, expiracao_2fa
FROM usuarios
ORDER BY id DESC;
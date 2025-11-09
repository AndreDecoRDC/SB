CREATE DATABASE IF NOT EXISTS studybridge DEFAULT CHARACTER SET utf8mb4;
USE studybridge;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    tipo_conta ENUM('Estudante','Monitor') NOT NULL,
    verificado BOOLEAN NOT NULL DEFAULT 0,
    token_verificacao VARCHAR(100) UNIQUE
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
    dia_da_semana ENUM('Segunda-feira', 'Terca-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira') NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_termino TIME NOT NULL,
    duracao_media_aula INT NOT NULL,
    
    FOREIGN KEY (monitor_id) REFERENCES monitores(id) ON DELETE CASCADE
);
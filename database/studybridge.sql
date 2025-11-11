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
CREATE TABLE IF NOT EXISTS solicitacoes_aula (
   id INT AUTO_INCREMENT PRIMARY KEY,
   id_estudante INT NOT NULL,
   id_monitor INT NULL,
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
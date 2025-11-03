/*
SCRIPT DE CRIAÇÃO DO BANCO DE DADOS - STUDYBRIDGE

SOBRE:
Este arquivo cria o banco de dados e as tabelas utilizadas
pelo sistema StudyBridge. Ele deve ser executado dentro do
MySQL Workbench (ou outro cliente MySQL) para configurar
o ambiente local de desenvolvimento.

COMO USAR:
1️ Abra o MySQL Workbench.
2️ Conecte-se ao servidor MySQL local (usuário root).
3️ Copie todo o conteudo deste arquivo
4️ Cole e execute no editor SQL (ícone de raio).
5️ Verifique se o banco "studybridge" foi criado na aba Schemas.

LOCAL DE EXECUÇÃO:
Este script deve ser executado no seu MySQL local.
A pasta "database" do projeto contém apenas este arquivo
para facilitar a configuração do ambiente por outros membros
da equipe.

EQUIPE:
Todos os integrantes do projeto devem ter o mesmo banco
criado localmente com este script, garantindo compatibilidade
entre as camadas DAO e Service.
*/

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
CREATE TABLE solicitacoes_aula (
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

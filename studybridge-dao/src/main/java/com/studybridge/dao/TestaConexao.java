package com.studybridge.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TestaConexao {

    public static void main(String[] args) {
        //Bloco try que fecha automaticamente a conexão no final
        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conexão estabelecida com sucesso");
            System.out.println("URL: " + conn.getMetaData().getURL());
            System.out.println("Usuário: " + conn.getMetaData().getUserName());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados:");
            e.printStackTrace();
        }
    }
}

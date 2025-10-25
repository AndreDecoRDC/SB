package com.studybridge.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    /*
    ConnectionFactory vai ser uma classe utilitária para estabelecer a conexão com o banco de dados.
    Ao invés de abrir uma conexão para cada classe DAO diferente, vai ser mais adequado padronizar assim.
    Todos os DAOs vão usar essa classe. (DAO = Data access object, e serve para separar a lógica de acesso
                                         aos dados da lógica principal da aplicação)
    */

    private static final String URL = "jdbc:mysql://localhost:3306/studybridge";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";

    //Vou usar DriverManager, que é uma classe da JDBC e retorna um objeto Connection (para poder criar a conexão)

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}

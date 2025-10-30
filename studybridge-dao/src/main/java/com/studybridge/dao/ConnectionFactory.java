package com.studybridge.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {


    /*
    ConnectionFactory vai ser uma classe utilitária para estabelecer a conexão com o banco de dados.
    ao invés de abrir uma conexão para cada classe DAO diferente, vai ser mais adequado padronizar assim.
    Todos os DAOs vão usar essa classe. (DAO = Data access object, e serve para separar a lógica de acesso
    aos dados da lógica principal da aplicação)
    */

    private static final String URL = "jdbc:mysql://localhost:3306/studybridge";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    //ou usar DriverManager, que é uma classe da JDBC e retorna um objeto Connection (para poder criar a conexão)

    public static Connection getConnection() throws SQLException {
        try {
            //forca o carregamento da classe do driver no classloader do Tomcat, pq esta dando o erro:No suitable driver found for jdbc:mysql://localhost:3306/studybridge
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do MySQL não encontrado no classpath.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}

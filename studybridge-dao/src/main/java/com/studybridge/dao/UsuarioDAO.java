package com.studybridge.dao;

import com.studybridge.domain.model.Usuario;
import java.sql.*; // importa as classes que trabalham com SQL (Connection, PreparedStatement, ResultSet)

public class UsuarioDAO {

    public boolean existePorEmail(String email) throws SQLException {
        //esse comando SQL procura se já tem alguém com o mesmo e-mail
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            //substitui o ? da query pelo valor real do e-mail
            ps.setString(1, email);

            // executa o comando e guarda em um result set que é tipo uma tabela temporária
            ResultSet rs = ps.executeQuery();

            // se rs.next() for verdadeiro, quer dizer que achou alguém com esse e-mail
            boolean existe = rs.next();

            rs.close();
            return existe;
        }
    }

    public void inserir(Usuario usuario) throws SQLException {
        //comando do SQL pra inserir
        String sql = "INSERT INTO usuarios (email, senha_hash, tipo_conta, verificado, token_verificacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { //o segundo parametro pede pro JDBC devolver o id gerado

            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenhaHash());
            ps.setString(3, usuario.getTipoConta());
            ps.setBoolean(4, usuario.isVerificado());
            ps.setString(5, usuario.getTokenVerificacao());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys(); //obtém o id gerado do banco
            if (rs.next()) {
                usuario.setId(rs.getInt(1)); //guarda esse id dentro do objeto, pq cada objeto usuario tem que ter seu id para localizacao depois
            }
            rs.close();
        }
    }
}
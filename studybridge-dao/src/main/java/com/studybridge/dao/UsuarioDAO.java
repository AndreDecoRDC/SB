package com.studybridge.dao;

import com.studybridge.domain.model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public boolean existePorEmail(String email) throws SQLException {
        //esse comando SQL procura se já tem alguem com o mesmo email
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            //substitui o ? da query pelo valor real do email
            ps.setString(1, email);

            //executa o comando e guarda em um result set que é tipo uma tabela temporária
            ResultSet rs = ps.executeQuery();

            //se rs.next() for verdadeiro, quer dizer que achou alguém com esse email
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

    /*
    estou planejando fazer do seguinte jeito: Como cada usuário vai ter o seu token específico. Quando a pessoa clicar
    em um link de confirmação referente ao token dela, a conta referente ao token vai se tornar verificada com as duas funções abaixo
     */

    public Usuario buscarPorToken(String token) throws SQLException {

        String sql = "SELECT * FROM usuarios WHERE token_verificacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                rs.close();
                return null;
            }

            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setEmail(rs.getString("email"));
            u.setSenhaHash(rs.getString("senha_hash"));
            u.setTipoConta(rs.getString("tipo_conta"));
            u.setVerificado(rs.getBoolean("verificado"));
            u.setTokenVerificacao(rs.getString("token_verificacao"));

            rs.close();
            return u;
        }
    }

    public void confirmarEmail(String token) throws SQLException {
        String sql = "UPDATE usuarios SET verificado = 1 WHERE token_verificacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.executeUpdate();
        }
    }
}


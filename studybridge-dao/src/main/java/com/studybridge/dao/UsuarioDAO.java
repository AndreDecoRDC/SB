package com.studybridge.dao;

import com.studybridge.domain.model.Usuario;
import java.sql.*;
import java.util.UUID;

public class UsuarioDAO {

    public boolean existePorEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            boolean existe = rs.next();
            rs.close();
            return existe;
        }
    }

    public int inserir(Usuario usuario) throws SQLException {

        String sql = """
            INSERT INTO usuarios (email, senha_hash, tipo_conta, token_verificacao)
            VALUES (?, ?, ?, ?)
        """;

        String token = UUID.randomUUID().toString();
        usuario.setTokenVerificacao(token);

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenhaHash());
            ps.setString(3, usuario.getTipoConta());
            ps.setString(4, token);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new SQLException("Falha ao obter ID do usuário");
    }

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

        String sql = """
            UPDATE usuarios
            SET verificado = 1, token_verificacao = NULL
            WHERE token_verificacao = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.executeUpdate();
        }
    }

    public Usuario buscarPorEmail(String email) throws SQLException {

        String sql = "SELECT * FROM usuarios WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
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
            u.setCodigo2FA(rs.getString("codigo_2fa"));

            Timestamp ts = rs.getTimestamp("expiracao_2fa");
            if (ts != null) {
                u.setExpiracao2FA(ts.toLocalDateTime());
            }

            rs.close();
            return u;
        }
    }

    public void salvarCodigo2FA(int idUsuario, String codigo) throws SQLException {
        String sql = """
            UPDATE usuarios
            SET codigo_2fa = ?, expiracao_2fa = NOW() + INTERVAL 5 MINUTE
            WHERE id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        }
    }

    public boolean validarCodigo2FA(int idUsuario, String codigo) throws SQLException {
        String sql = """
            SELECT 1 FROM usuarios
            WHERE id = ? AND codigo_2fa = ? AND expiracao_2fa > NOW()
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setString(2, codigo);
            ResultSet rs = ps.executeQuery();
            boolean valido = rs.next();
            rs.close();
            return valido;
        }
    }

    public void limparCodigo2FA(int idUsuario) throws SQLException {
        String sql = "UPDATE usuarios SET codigo_2fa = NULL, expiracao_2fa = NULL WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
    }

    public void salvarTokenRedefinicao(int id, String token) throws SQLException {

        String sql = """
            UPDATE usuarios
            SET token_redefinicao = ?, expiracao_redefinicao = NOW() + INTERVAL 30 MINUTE
            WHERE id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public Usuario buscarPorTokenRedefinicao(String token) throws SQLException {

        String sql = """
            SELECT * FROM usuarios
            WHERE token_redefinicao = ? AND expiracao_redefinicao > NOW()
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setEmail(rs.getString("email"));
            u.setSenhaHash(rs.getString("senha_hash"));
            u.setTipoConta(rs.getString("tipo_conta"));

            rs.close();
            return u;
        }
    }

    public void atualizarSenhaHash(int id, String novaHash) throws SQLException {
        String sql = "UPDATE usuarios SET senha_hash = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novaHash);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void limparTokenRedefinicao(int id) throws SQLException {

        String sql = "UPDATE usuarios SET token_redefinicao = NULL, expiracao_redefinicao = NULL WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

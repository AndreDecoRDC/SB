package com.studybridge.dao;

import com.studybridge.domain.model.Notificacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificacaoDAO {

    public void inserir(Notificacao n) throws SQLException {

        String sql = """
            INSERT INTO notificacoes
            (email_destinatario, tipo, mensagem, link, lida)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, n.getEmailDestinatario());
            ps.setString(2, n.getTipo());
            ps.setString(3, n.getMensagem());
            ps.setString(4, n.getLink());
            ps.setBoolean(5, n.isLida());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    n.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Notificacao> listarPorUsuario(String email) throws SQLException {

        String sql = """
            SELECT *
            FROM notificacoes
            WHERE email_destinatario = ?
            ORDER BY data_criacao DESC
        """;

        List<Notificacao> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }

        return lista;
    }

    public List<Notificacao> listarNaoLidas(String email) throws SQLException {

        String sql = """
            SELECT *
            FROM notificacoes
            WHERE email_destinatario = ?
              AND lida = FALSE
            ORDER BY data_criacao DESC
        """;

        List<Notificacao> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
        }

        return lista;
    }

    public void marcarComoLida(int id, String emailDestinatario) throws SQLException {

        String sql = """
            UPDATE notificacoes
            SET lida = TRUE
            WHERE id = ?
              AND email_destinatario = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, emailDestinatario);

            ps.executeUpdate();
        }
    }

    private Notificacao map(ResultSet rs) throws SQLException {

        Notificacao n = new Notificacao();

        n.setId(rs.getInt("id"));
        n.setEmailDestinatario(rs.getString("email_destinatario"));
        n.setTipo(rs.getString("tipo"));
        n.setMensagem(rs.getString("mensagem"));
        n.setLink(rs.getString("link"));
        n.setLida(rs.getBoolean("lida"));

        Timestamp ts = rs.getTimestamp("data_criacao");
        if (ts != null) {
            n.setDataCriacao(ts.toLocalDateTime());
        }

        return n;
    }
}

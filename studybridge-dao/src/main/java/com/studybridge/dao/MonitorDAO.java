package com.studybridge.dao;

import com.studybridge.domain.model.Monitor;

import java.sql.*;

public class MonitorDAO {

    public void inserir(int idUsuario) throws SQLException {
        String sql = "INSERT INTO monitores (usuario_id) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
    }

    public Monitor buscarPorUsuarioId(int usuarioId) throws SQLException {
        String sql = """
            SELECT nome, telefone, disciplina, campus, descricao
            FROM monitores
            WHERE usuario_id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Monitor m = new Monitor();
                m.setNome(rs.getString("nome"));
                m.setTelefone(rs.getString("telefone"));
                m.setDisciplina(rs.getString("disciplina"));
                m.setCampus(rs.getString("campus"));
                m.setDescricao(rs.getString("descricao"));

                return m;
            }
        }
    }

    public void atualizarPerfil(int usuarioId, Monitor m) throws SQLException {
        String sql = """
            UPDATE monitores
            SET nome = ?, telefone = ?, disciplina = ?, campus = ?, descricao = ?
            WHERE usuario_id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNome());
            ps.setString(2, m.getTelefone());
            ps.setString(3, m.getDisciplina());
            ps.setString(4, m.getCampus());
            ps.setString(5, m.getDescricao());
            ps.setInt(6, usuarioId);

            ps.executeUpdate();
        }
    }
}

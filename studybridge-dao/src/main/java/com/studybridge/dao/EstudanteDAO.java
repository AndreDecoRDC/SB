package com.studybridge.dao;

import com.studybridge.domain.model.Estudante;
import java.sql.*;

public class EstudanteDAO {

    public void inserir(int idUsuario) throws SQLException {
        String sql = "INSERT INTO estudantes (usuario_id) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
    }

    public Estudante buscarPorUsuarioId(int usuarioId) throws SQLException {
        String sql = """
            SELECT nome, telefone, curso, ano_turma, campus, descricao
            FROM estudantes
            WHERE usuario_id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Estudante e = new Estudante();
                e.setNome(rs.getString("nome"));
                e.setTelefone(rs.getString("telefone"));
                e.setCurso(rs.getString("curso"));
                e.setAnoTurma(rs.getString("ano_turma"));
                e.setCampus(rs.getString("campus"));
                e.setDescricao(rs.getString("descricao"));
                return e;
            }
        }
    }

    public void atualizarPerfil(int usuarioId, Estudante e) throws SQLException {
        String sql = """
            UPDATE estudantes
            SET nome = ?, telefone = ?, curso = ?, ano_turma = ?, campus = ?, descricao = ?
            WHERE usuario_id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getNome());
            ps.setString(2, e.getTelefone());
            ps.setString(3, e.getCurso());
            ps.setString(4, e.getAnoTurma());
            ps.setString(5, e.getCampus());
            ps.setString(6, e.getDescricao());
            ps.setInt(7, usuarioId);
            ps.executeUpdate();
        }
    }
}

package com.studybridge.dao;

import com.studybridge.domain.model.Aula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AulaDAO {

    public void inserir(Aula aula) throws SQLException {
        String sql = """
            INSERT INTO solicitacoes_aula
            (id_estudante, id_monitor, disciplina, descricao, status, data_aula)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, aula.getId_estudante());
            ps.setString(2, aula.getId_monitor());
            ps.setString(3, aula.getDisciplina());
            ps.setString(4, aula.getDescricao());
            ps.setString(5, aula.getStatus() != null ? aula.getStatus() : "PENDENTE");
            ps.setTimestamp(6, aula.getData_aula() != null ? Timestamp.valueOf(aula.getData_aula()) : null);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    aula.setId(rs.getInt(1));
                }
            }
        }
    }

    public void atualizarStatus(int id, String novoStatus) throws SQLException {
        String sql = "UPDATE solicitacoes_aula SET status = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoStatus);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }
    public List<Aula> listarPendentes() throws SQLException {
        List<Aula> lista = new ArrayList<>();

        String sql = "SELECT * FROM solicitacoes_aula WHERE status = 'PENDENTE' ORDER BY data_solicitacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Aula aula = new Aula();
                aula.setId(rs.getInt("id"));
                aula.setId_estudante(rs.getString("id_estudante"));
                aula.setId_monitor(rs.getString("id_monitor"));
                aula.setDisciplina(rs.getString("disciplina"));
                aula.setDescricao(rs.getString("descricao"));
                aula.setStatus(rs.getString("status"));
                Timestamp ts = rs.getTimestamp("data_aula");
                if (ts != null) aula.setData_aula(ts.toLocalDateTime());

                lista.add(aula);
            }
        }

        return lista;
    }
    public List<Aula> listarAceitas() throws SQLException {
        List<Aula> lista = new ArrayList<>();

        String sql = "SELECT * FROM solicitacoes_aula WHERE status = 'ACEITA' ORDER BY data_aula";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Aula aula = new Aula();
                aula.setId(rs.getInt("id"));
                aula.setId_estudante(rs.getString("id_estudante"));
                aula.setDisciplina(rs.getString("disciplina"));
                aula.setDescricao(rs.getString("descricao"));
                aula.setStatus(rs.getString("status"));
                Timestamp ts = rs.getTimestamp("data_aula");
                if (ts != null) aula.setData_aula(ts.toLocalDateTime());
                lista.add(aula);
            }
        }
        return lista;
    }
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM solicitacoes_aula WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public Aula buscarProximaAula(String emailMonitor) throws SQLException {
        String sql = """
        SELECT * FROM solicitacoes_aula
        WHERE status = 'ACEITA'
        AND id_monitor = ?
        AND data_aula > NOW()
        ORDER BY data_aula
        LIMIT 1
    """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emailMonitor);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Aula aula = new Aula();
                    aula.setId(rs.getInt("id"));
                    aula.setId_estudante(rs.getString("id_estudante"));
                    aula.setId_monitor(rs.getString("id_monitor"));
                    aula.setDisciplina(rs.getString("disciplina"));
                    aula.setDescricao(rs.getString("descricao"));
                    aula.setStatus(rs.getString("status"));

                    Timestamp ts = rs.getTimestamp("data_aula");
                    if (ts != null) aula.setData_aula(ts.toLocalDateTime());

                    return aula;
                }
            }
        }

        return null;
    }

}

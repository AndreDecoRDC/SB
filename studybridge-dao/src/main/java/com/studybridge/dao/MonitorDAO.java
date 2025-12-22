package com.studybridge.dao;

import static com.studybridge.dao.ConnectionFactory.getConnection;
import com.studybridge.domain.model.Monitor;
import com.studybridge.domain.model.MonitorBusca;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonitorDAO {

    public void inserir(int idUsuario) throws SQLException {
        String sql = "INSERT INTO monitores (usuario_id) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNome());
            ps.setString(2, m.getTelefone());
            ps.setString(3, m.getDisciplina());
            ps.setString(4, m.getCampus());
            ps.setString(5, m.getDescricao());
            ps.setInt(6, usuarioId);
            ps.executeUpdate();
        }
    }

    public List<MonitorBusca> buscarMonitores(
            String nome,
            String disciplina,
            String ordenar
    ) throws SQLException {

        List<MonitorBusca> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("""
            SELECT
                m.id AS monitor_id,
                u.id AS usuario_id,
                u.email,
                COALESCE(m.nome, 'Monitor sem Nome') AS nome,
                m.telefone,
                COALESCE(m.disciplina, 'Não informada') AS disciplina,
                m.campus,
                m.descricao,
                COALESCE((SELECT AVG(a.nota) FROM avaliacoes a WHERE a.usuario_id = u.id), 0) AS media_avaliacao
            FROM usuarios u
            JOIN monitores m ON m.usuario_id = u.id
            WHERE u.tipo_conta = 'Monitor'
              AND u.verificado = 1
        """);

        if (nome != null && !nome.isBlank()) {
            sql.append(" AND m.nome LIKE ? ");
        }

        if (disciplina != null && !disciplina.isBlank()) {
            sql.append(" AND m.disciplina = ? ");
        }

        if ("avaliacao".equals(ordenar)) {
            sql.append(" ORDER BY media_avaliacao DESC, m.nome ASC ");
        } else {
            sql.append(" ORDER BY nome ASC ");
        }

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;

            if (nome != null && !nome.isBlank()) {
                ps.setString(index++, "%" + nome + "%");
            }

            if (disciplina != null && !disciplina.isBlank()) {
                ps.setString(index++, disciplina);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MonitorBusca m = new MonitorBusca();

                m.setMonitorId(rs.getInt("monitor_id"));
                m.setUsuarioId(rs.getInt("usuario_id"));
                m.setEmail(rs.getString("email"));
                m.setNome(rs.getString("nome"));
                m.setTelefone(rs.getString("telefone"));
                m.setDisciplina(rs.getString("disciplina"));
                m.setCampus(rs.getString("campus"));
                m.setDescricao(rs.getString("descricao"));
                m.setMediaAvaliacao(rs.getDouble("media_avaliacao"));

                lista.add(m);

            }
        }

        return lista;
    }

    public Integer buscarMonitorIdPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT id FROM monitores WHERE usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return null;
    }

    public MonitorBusca buscarMelhorMonitor() throws SQLException {
        List<MonitorBusca> lista = buscarMonitores(null, null, "avaliacao");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public MonitorBusca buscarPorUsuarioId(String usuarioId) throws Exception {

        String sql = """
        SELECT 
            m.nome
        FROM monitores m
        WHERE m.usuario_id = ?
    """;

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MonitorBusca monitor = new MonitorBusca();
                    monitor.setNome(rs.getString("nome"));
                    return monitor;
                }
            }
        }

        return null;
    }

}

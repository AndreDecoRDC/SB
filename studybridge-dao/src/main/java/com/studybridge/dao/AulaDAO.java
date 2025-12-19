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


    public List<Aula> listarPorEstudante(int idEstudante) throws SQLException {
        String sql = "SELECT sa.*, m.nome AS nome_usuario_associado, sa.id_monitor AS id_usuario_associado " +
             "FROM solicitacoes_aula sa " +
             "LEFT JOIN monitores m ON sa.id_monitor = m.usuario_id " +
             "WHERE sa.id_estudante = ?";

        List<Aula> aulas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEstudante);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Aula aula = mapAula(rs);
                    aulas.add(aula);
                }
            }
        }
        return aulas;
    }

    public List<Aula> listarPorMonitor(String emailMonitor) throws SQLException {
        String sql = """
        SELECT
            sa.*,
            sa.id_estudante AS nome_usuario_associado,
            NULL AS id_usuario_associado
        FROM solicitacoes_aula sa
        WHERE sa.id_monitor = ?
    """;

        List<Aula> aulas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emailMonitor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Aula aula = mapAula(rs);
                    aulas.add(aula);
                }
            }
        }
        return aulas;
    }


    //estudante cancela aula
    public void cancelar(int idAula) throws SQLException {
        String sql = "UPDATE solicitacoes_aula SET status = 'CANCELADA' WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAula);
            ps.executeUpdate();
        }
    }

    //monitor recusa aula
    public void recusar(int idAula) throws SQLException {
        String sql = "UPDATE solicitacoes_aula SET status = 'RECUSADA' WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAula);
            ps.executeUpdate();
        }
    }

    public Aula buscarPorId(int idAula) throws SQLException {
        String sql = """
        SELECT
            sa.*,
            COALESCE(m.nome, e.nome) AS nome_usuario_associado,
            COALESCE(sa.id_monitor, sa.id_estudante) AS id_usuario_associado
        FROM solicitacoes_aula sa        
        LEFT JOIN monitores m ON sa.id_monitor = m.usuario_id        
        LEFT JOIN estudantes e ON sa.id_estudante = e.usuario_id
        WHERE sa.id = ?
    """;

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapAula(rs);
                }
            }
        }
        return null;
    }

    private Aula mapAula(ResultSet rs) throws SQLException {
        Aula aula = new Aula();
        aula.setId(rs.getInt("id"));
        aula.setId_estudante(rs.getString("id_estudante"));
        aula.setId_monitor(rs.getString("id_monitor"));
        aula.setDisciplina(rs.getString("disciplina"));
        aula.setDescricao(rs.getString("descricao"));
        aula.setStatus(rs.getString("status"));

        Timestamp tsSol = rs.getTimestamp("data_solicitacao");
        if (tsSol != null) {
            aula.setData_solicitacao(tsSol.toLocalDateTime());
        }

        Timestamp tsAula = rs.getTimestamp("data_aula");
        if (tsAula != null) {
            aula.setData_aula(tsAula.toLocalDateTime());
        }
        String nomeAssociado = rs.getString("nome_usuario_associado");

        if (nomeAssociado != null && !nomeAssociado.isEmpty()) {
            aula.setNomeUsuarioAssociado(nomeAssociado);
        } else {
            aula.setNomeUsuarioAssociado("Nome Indisponível");
        }

       Integer idAssociado = rs.getInt("id_usuario_associado");
        if(rs.wasNull()) {
            idAssociado = null;
        }
        aula.setIdUsuarioAssociado(idAssociado);

        return aula;
    }

    private int contar(String sql) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
                
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                    return 0;
                }
    }
    
    public int contarAulasMarcadas() throws SQLException {
        String sql = "SELECT COUNT(id) FROM solicitacoes_aula WHERE status = 'ACEITA'";
        return contar(sql);
    }
    
    public int contarAulasConcluidas() throws SQLException {
        String sql = "SELECT COUNT(id) FROM solicitacoes_aula WHERE status = 'CONCLUIDA'";
        return contar(sql);
    }
    
    public int contarTotalAulas() throws SQLException {
        String sql = "SELECT COUNT(id) FROM solicitacoes_aula";
        return contar(sql);
    }
}

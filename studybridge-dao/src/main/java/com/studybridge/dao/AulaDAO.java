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

            ps.setInt(1, 1);
            ps.setNull(2, Types.INTEGER);
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
    
    public List<Aula> listarPorEstudante(int idEstudante) throws SQLException {
        String sql = "SELECT * FROM solicitacoes_aula WHERE id_estudante = ? ORDER BY data_aula";

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
    
    public List<Aula> listarPorMonitor(int idMonitor) throws SQLException {
        String sql = "SELECT * FROM solicitacoes_aula WHERE id_monitor = ? ORDER BY data_aula";

        List<Aula> aulas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMonitor);

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

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idAula);
        ps.executeUpdate();
    }
}
    
    //monitor recusa aula
    public void recusar(int idAula) throws SQLException {
        String sql = "UPDATE solicitacoes_aula SET status = 'RECUSADA' WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAula);
            ps.executeUpdate();
        }
    }

    private Aula mapAula(ResultSet rs) throws SQLException {
        Aula aula = new Aula();
        aula.setId(rs.getInt("id"));
        aula.setId_estudante(rs.getInt("id_estudante"));
        aula.setId_monitor(rs.getInt("id_monitor"));
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

        return aula;
    }

}

package com.studybridge.dao;

import com.studybridge.domain.model.Aula;
import java.sql.*;

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
}

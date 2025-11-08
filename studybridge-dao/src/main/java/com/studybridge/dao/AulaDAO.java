package com.studybridge.dao;

import com.studybridge.domain.model.Aula;

import java.sql.*;

public class AulaDAO {
    public void inserir(Aula aula) throws SQLException{
        String sql = "INSERT INTO aula (id_estudante, id_monitor, disciplina, descricao, data_solicitacao, status, data_aula) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            ps.setInt(1, aula.getId_estudante());
            ps.setInt(2, aula.getId_monitor());
            ps.setString(3, aula.getDisciplina());
            ps.setString(4, aula.getDescricao());
            ps.setTimestamp(5, aula.getData_solicitacao() != null ? Timestamp.valueOf(aula.getData_solicitacao()) : null);
            ps.setString(6, aula.getStatus());
            ps.setTimestamp(7, aula.getData_aula() != null ? Timestamp.valueOf(aula.getData_aula()) : null);
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                aula.setId(rs.getInt(1));
            }
            rs.close();
        }
    }
}
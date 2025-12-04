package com.studybridge.dao;

import com.studybridge.domain.model.Monitor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MonitorDAO {

    public void inserir(int idUsuario) throws SQLException {
        
        String sql = "INSERT INTO monitores (usuario_id) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario); 
            
            ps.executeUpdate();
        }
    }
}
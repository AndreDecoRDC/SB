package com.studybridge.dao;

import com.studybridge.domain.model.Estudante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class EstudanteDAO {

    public void inserir(int idUsuario) throws SQLException {

        String sql = "INSERT INTO estudantes (usuario_id) VALUES (?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            
            ps.executeUpdate();
        }
    }
}
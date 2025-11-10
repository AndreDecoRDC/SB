package com.studybridge.dao;

import com.studybridge.domain.model.Horario;
import com.studybridge.dao.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class JDBCHorarioDAO implements HorarioDAO{
    
    @Override
    public Horario insertHorario(Horario horario) throws SQLException {
        String sql = "INSERT INTO horarios_disponiveis "  + 
               "(monitor_id, dia_da_semana, horario_inicio, horario_termino, duracao_media_aula) "  + 
               "VALUES (?,?,?,?,?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, horario.getMonitorId());
            ps.setString(2, horario.getDiaSemana());
            ps.setTime(3, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(4, Time.valueOf(horario.getHoraTermino()));
            ps.setInt(5, horario.getDuracaoMedia());
            
            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas == 0) {
                throw  new SQLException("Falha ao inserir horário.");
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    horario.setId(keys.getInt(1));
                }
            }
            return horario;
        }         
        
    }

    @Override
    public Horario getHorario(int id) throws SQLException {
        String sql = "SELECT * FROM horarios_disponiveis WHERE id = ?";
        
       try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
           
           ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
           }  
       }
        return null;
    }

    @Override
    public void updateHorario(Horario horario) throws SQLException {
        String sql = "UPDATE horarios_disponiveis SET dia_da_semana=?, horario_inicio=?, horario_termino=?, duracao_media_aula=? WHERE id = ?";
        
       try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
           
            ps.setString(1, horario.getDiaSemana());
            ps.setTime(2, Time.valueOf(horario.getHoraInicio()));
            ps.setTime(3, Time.valueOf(horario.getHoraTermino()));
            ps.setInt(4, horario.getDuracaoMedia());
            ps.setInt(5, horario.getId());
            
            int linhasModificadas = ps.executeUpdate();
            if (linhasModificadas == 0) {
                throw new SQLException("Nenhuma linha atualizada para id: " + horario.getId());
            }
       }        
    }

    @Override
    public void deleteHorario(Horario horario) throws SQLException {
        String sql = "DELETE FROM horarios_disponiveis WHERE id = ?"; 
        
       try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
           
           ps.setInt(1, horario.getId());
           ps.executeUpdate();
       }        
    }

    @Override
    public List<Horario> getAllHorarios(int monitorId) throws SQLException {
        String sql = "SELECT * FROM horarios_disponiveis WHERE  monitor_id = ? ORDER BY FIELD(dia_da_semana, 'Segunda-feira','Terca-feira','Quarta-feira','Quinta-feira','Sexta-feira'), horario_inicio";
        
        List<Horario> lista = new ArrayList<>();
        
       try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
       
           ps.setInt(1, monitorId);
           
           try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                   lista.add(mapRow(rs));
               }
           }
       }
       return lista;
    }

    @Override
    public boolean existDuplicado(int monitorId, String diaSemana, LocalTime inicio, LocalTime fim) throws SQLException {
        String sql = "SELECT COUNT(*) FROM horarios_disponiveis WHERE monitor_id=? AND dia_da_semana=? AND horario_inicio=? AND horario_termino=?";
        
       try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
       
           ps.setInt(1, monitorId);
           ps.setString(2, diaSemana);
           ps.setTime(3, Time.valueOf(inicio));
           ps.setTime(4, Time.valueOf(fim));
           
           try (ResultSet rs = ps.executeQuery()) {
               if (rs.next()) {
                   return rs.getInt(1) > 0;
               }
           }
       }
        return false;
    }

 private Horario mapRow(ResultSet rs) throws SQLException {
        Horario h = new Horario();
        h.setId(rs.getInt("id"));
        h.setMonitorId(rs.getInt("monitor_id"));
        h.setDiaSemana(rs.getString("dia_da_semana"));
        h.setHoraInicio(rs.getTime("horario_inicio").toLocalTime());
        h.setHoraTermino(rs.getTime("horario_termino").toLocalTime());
        h.setDuracaoMedia(rs.getInt("duracao_media_aula"));

        return h;
    }
}

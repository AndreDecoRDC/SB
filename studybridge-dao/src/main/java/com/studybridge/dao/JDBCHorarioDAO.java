package com.studybridge.dao;

import com.studybridge.domain.model.Horario;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public class JDBCHorarioDAO implements HorarioDAO{
    
    @Override
    public Horario insertHorario(Horario horario) throws SQLException {   
        return null;
    }

    @Override
    public Horario getHorario(int id) throws SQLException {
        return null;
    }

    @Override
    public void updateHorario(Horario horario) throws SQLException {

    }

    @Override
    public void deleteHorario(Horario horario) throws SQLException {
        
    }

    @Override
    public List<Horario> getAllHorarios(int monitorId) throws SQLException {
        return null;
    }

    @Override
    public boolean existDuplicado(int monitorId, String diaSemana, LocalTime inicio, LocalTime fim) throws SQLException {
        
        return false;
    }
    
}

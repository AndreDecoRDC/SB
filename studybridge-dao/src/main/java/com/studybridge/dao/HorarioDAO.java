package com.studybridge.dao;

import com.studybridge.domain.model.Horario;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

public interface HorarioDAO {
    
        public Horario insertHorario(Horario horario) throws SQLException;
        public Horario getHorario(int id) throws Exception;
        public void updateHorario(Horario horario) throws SQLException;
        public void deleteHorario(Horario horario) throws SQLException;
        public List<Horario> getAllHorarios(int monitorId) throws SQLException;
        public boolean existDuplicado(int monitorId, String diaSemana, java.time.LocalTime inicio, java.time.LocalTime fim) throws SQLException;
}

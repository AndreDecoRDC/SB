package com.studybridge.service;

import com.studybridge.dao.HorarioDAO;
import com.studybridge.dao.JDBCHorarioDAO;
import com.studybridge.domain.model.Horario;
import java.util.List;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.Duration;

public class GerenciarHorarioService {
    private final HorarioDAO horarioDAO;
    
    public GerenciarHorarioService() {
        this.horarioDAO = new JDBCHorarioDAO();
    }
    
    public String adicionarHorario(Horario horario) {
        
            
            int duracaoMedia = horario.getDuracaoMedia();
            LocalTime inicio = horario.getHoraInicio();
            LocalTime termino = horario.getHoraTermino();
            
            Duration duracao = Duration.between(inicio, termino);                                  
            
        try {
            
            if (inicio == null || termino == null) {
                return "Erro. Horário de início ou término inválido.";
            }
            if (inicio.equals(termino) || inicio.isAfter(termino)) {
                return "Erro. Horário de início deve ser antes de término.";
            }

             if (duracao.toMinutes() < 15) {
                return "Erro. Seu tempo de aula deve ter duração de pelo menos 15 minutos.";
            }

            boolean duplicado = horarioDAO.existDuplicado(
                    horario.getMonitorId(),
                    horario.getDiaSemana(),
                    horario.getHoraInicio(),
                    horario.getHoraTermino()
            );
            
            if (duplicado) {
                return "Erro. Já existe um horário cadastrado nesse dia/horário.";
            }
            
            Horario inserido = horarioDAO.insertHorario(horario);
            
            return "Horário adicionado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao adicionar horário: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro inesperado: " + e.getMessage();
        }
    }
    
    public String removerHorario(Horario horario) {
        try {
            horarioDAO.deleteHorario(horario);
            return "Horário removido com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao remover horário: " + e.getMessage();
        }
    }
    public String editarHorario(Horario horario) {
        try {
            
            if (horario.getHoraInicio() == null || horario.getHoraTermino() == null) {
                return "Erro. Horário inválido.";
            }
            if (horario.getHoraInicio().equals(horario.getHoraTermino()) || horario.getHoraInicio().isAfter(horario.getHoraTermino())) {
                return "Erro. Horário de início deve ser antes de término.";
            }
            
            horarioDAO.updateHorario(horario);
            return "Horário atualizado com sucesso!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar horário: " + e.getMessage();
        }
    }
    
    public List<Horario> listarHorario(int monitorId) {
        try {
            return horarioDAO.getAllHorarios(monitorId);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
    
    public Horario getHorarioById(int id) throws Exception {
        try {
            return horarioDAO.getHorario(id);
        } catch (SQLException e) {
            return null;
        }
    }
    
}

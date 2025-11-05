package com.studybridge.service;

import com.studybridge.domain.model.Horario;
import java.util.List;

public class GerenciarHorarioService {
   //receber monitorX
    
    private void adicionarHorario(Horario newHorario) {
        //verificar se não existe newHorario no banco de dados do monitorX (evitar horário duplicado)
            //se sim, continua; se não, retorna "Horário duplicado"
            //adicionar o newHorario no banco de dados do monitorX
            //retornar "Novo horário cadastrado"
    }       
    private void removerHorario(Horario remHorario) {
        //verificar se o remHorário existe no banco de dados do monitorX (try)
            //se sim, continua; se não, retorna "Horário não cadastrado"
            //remover o remHorario no monitorX
        //retornar "Horário removido!"
    }
    private void editarHorario(Horario horarioExistente, Horario newHorario) {
        //verificar se horarioExistente realmente existe no banco de dados do monitorX
            //se sim, continua; se não, retorna "Horário inexistente"
            //substituir no banco de dados o horarioExistente para newHorario
            //retornar "Horário editado com sucesso"
    }
    private List<Horario> listarHorario() {
        //Puxar do banco de dados de monitorX e listar todos os horários disponiveis
        return null;
    }
    
}

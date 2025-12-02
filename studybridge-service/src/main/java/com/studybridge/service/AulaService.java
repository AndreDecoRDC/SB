package com.studybridge.service;

import com.studybridge.dao.AulaDAO;
import com.studybridge.domain.model.Aula;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AulaService {

    private final AulaDAO aulaDAO = new AulaDAO();

    public void solicitar(String disciplina, String data_aula, String mensagem, String emailAluno) throws Exception{

        final LocalDateTime dataHora;

        if(disciplina == null || disciplina.isBlank())
            throw new Exception("Informe uma disciplina");

        if(mensagem == null || mensagem.isBlank())
            throw new Exception("Coloque uma Mensagem de descrição");

        if(data_aula == null)
            throw new Exception("Coloque uma data");
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            dataHora = LocalDateTime.parse(data_aula, formatter);
        } catch (Exception e) {
            throw new Exception("Formato de data/hora inválido! Use o formato dd/MM/yyyy HH:mm");
        }

        if(dataHora.isBefore(LocalDateTime.now())){
            throw new Exception("Esta data já passou!");
        }

        if(emailAluno == null){
            throw new Exception("Não tem usuário logado");
        }

        Aula novaAula = new Aula(disciplina, mensagem, dataHora, emailAluno);
        try {
            aulaDAO.inserir(novaAula);
        } catch (SQLException e) {
            throw new Exception("Erro ao salvar solicitação  no banco", e);
        }
    }

    public void aceitarSolicitacao(int idSolicitacao) throws Exception {
        try {
            aulaDAO.atualizarStatus(idSolicitacao, "ACEITA");
        } catch (SQLException e) {
            throw new Exception("Erro ao aceitar solicitação", e);
        }
    }

    public List<Aula> listarSolicitacoesPendentes() throws Exception {
        try {
            return aulaDAO.listarPendentes();
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar solicitações pendentes", e);
        }
    }
    public List<Aula> listarAceitas() throws Exception {
        try {
            return aulaDAO.listarAceitas();
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar aulas aceitas", e);
        }
    }
    public void deletarAula(int idAula) throws Exception {
        try {
            aulaDAO.deletar(idAula);
        } catch (SQLException e) {
            throw new Exception("Erro ao excluir a aula", e);
        }
    }
    public Aula getProximaAula(String emailMonitor) throws Exception {
        try {
            return aulaDAO.buscarProximaAula(emailMonitor);
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar próxima aula", e);
        }
    }
    public void recusarSolicitacao(int idAula) throws Exception {
        try {
            aulaDAO.atualizarStatus(idAula, "RECUSADA");
        } catch (SQLException e) {
            throw new Exception("Erro ao recusar solicitação", e);
        }
    }


}

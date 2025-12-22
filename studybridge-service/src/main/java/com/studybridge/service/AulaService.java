package com.studybridge.service;

import com.studybridge.dao.AulaDAO;
import com.studybridge.domain.model.Aula;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;

public class AulaService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final AulaDAO aulaDAO = new AulaDAO();

    public void solicitar(String disciplina, String data_aula, String mensagem, String emailAluno, String monitorId) throws Exception{

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
        
        novaAula.setId_monitor(monitorId);
        
        try {
            aulaDAO.inserir(novaAula);
            
            aulaDAO.associarMonitorUltimaSolicitacao(emailAluno, monitorId);
            
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

    public List<Aula> listarAulasDoEstudante(int idEstudante) throws SQLException {
        List<Aula> aulas = aulaDAO.listarPorEstudante(idEstudante);

        for (int i = 0; i < aulas.size(); i++) {
            Aula aula = aulas.get(i);

            if (aula.getData_aula() != null) {
                String dataFormatada = aula.getData_aula().format(FORMATTER);
                aula.setDataAulaFormatada(dataFormatada);
            } else {
                aula.setDataAulaFormatada("Aguardando Definição");
            }
        }
        return aulas;
    }

    public List<Aula> listarAulasDoMonitor(int idMonitor) throws SQLException {
        List<Aula> aulas = aulaDAO.listarPorMonitor(idMonitor);

        for (Aula aula : aulas) {
            if (aula.getData_aula() != null) {
                aula.setDataAulaFormatada(
                        aula.getData_aula().format(FORMATTER)
                );
            } else {
                aula.setDataAulaFormatada("Aguardando Definição");
            }
        }
        return aulas;
    }

    public Aula getProximaAula(String emailMonitor) throws Exception {
        try {
            return aulaDAO.buscarProximaAula(emailMonitor);
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar próxima aula", e);
        }
    }
    //estudante faz isso
    public void cancelarAula(int idAula) throws Exception {
        try {
            aulaDAO.cancelar(idAula);
        } catch (SQLException e) {
            throw new Exception("Erro ao cancelar aula.", e);
        }
    }

    //monitor faz isso
    public void recusarSolicitacao(int idAula) throws Exception {
        try {
            aulaDAO.atualizarStatus(idAula, "RECUSADA");
        } catch (SQLException e) {
            throw new Exception("Erro ao recusar solicitação", e);
        }
    }
    public Aula buscarAulaPorId(int idAula) throws Exception {
        try {
            return aulaDAO.buscarPorId(idAula);
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar aula por ID.", e);
        }
    }

}

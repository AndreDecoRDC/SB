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

    public void solicitar(String disciplina, String data_aula, String mensagem) throws Exception{

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
        Aula novaAula = new Aula(disciplina, mensagem, dataHora);
        try {
            aulaDAO.inserir(novaAula);
        } catch (SQLException e) {
            throw new Exception("Erro ao salvar solicitação  no banco", e);
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
    
    //estudante faz isso
    public void cancelarAula(int idAula) throws Exception {
        try {
            aulaDAO.cancelar(idAula);
        } catch (SQLException e) {
            throw new Exception("Erro ao cancelar aula.", e);
        }
    }
    
    //monitor faz isso
    public void recusarAula(int idAula) throws Exception {
        try {
            aulaDAO.recusar(idAula);
        } catch (SQLException e) {
            throw new Exception("Erro ao recusar aula.", e);
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

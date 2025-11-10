package com.studybridge.service;

import com.studybridge.dao.AulaDAO;
import com.studybridge.domain.model.Aula;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AulaService {

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
}

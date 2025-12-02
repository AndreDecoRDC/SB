package com.studybridge.domain.model;

import java.time.LocalDateTime;
import java.util.UUID.*;

public class Aula {
    private Integer id;
    private String id_estudante;
    private String id_monitor;
    private String disciplina;
    private String descricao;
    private LocalDateTime data_solicitacao;
    private String status;
    private LocalDateTime data_aula;

    public Aula(){
        this.status = "PENDENTE";
    }

    public Aula(String id_estudante, String id_monitor, String disciplina, String descricao, LocalDateTime data_solicitacao, LocalDateTime data_aula){
        this();
        this.id_estudante = id_estudante;
        this.id_monitor = id_monitor;
        this.disciplina = disciplina;
        this.descricao = descricao;
        this.data_solicitacao = data_solicitacao;
        this.data_aula = data_aula;
    }

    public Aula(String disciplina, String descricao, LocalDateTime data_aula, String id_estudante){
        this();
        this.disciplina = disciplina;
        this.descricao = descricao;
        this.data_aula = data_aula;
        this.id_estudante = id_estudante;
        // teoricamente, caso tivesse o caso de uso de cadastrar monitorias, não seria esse email aqui.
        this.id_monitor = "guilhermemfpereira@gmail.com";
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getId_estudante() {
        return id_estudante;
    }

    public void setId_estudante(String id_estudante) {
        this.id_estudante = id_estudante;
    }

    public String getId_monitor() {
        return id_monitor;
    }

    public void setId_monitor(String id_monitor) {
        this.id_monitor = id_monitor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData_solicitacao() {
        return data_solicitacao;
    }

    public void setData_solicitacao(LocalDateTime data_solicitacao) {
        this.data_solicitacao = data_solicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getData_aula() {
        return data_aula;
    }

    public void setData_aula(LocalDateTime data_aula) {
        this.data_aula = data_aula;
    }

}
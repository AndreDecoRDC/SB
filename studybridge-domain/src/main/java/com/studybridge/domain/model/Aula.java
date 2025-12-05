package com.studybridge.domain.model;

import java.time.LocalDateTime;
import java.util.UUID.*;
import java.time.format.DateTimeFormatter;

public class Aula {
    private Integer id;
    private Integer id_estudante;
    private Integer id_monitor;
    private String disciplina;
    private String descricao;
    private LocalDateTime data_solicitacao;
    private String status;
    private LocalDateTime data_aula;
    private String dataAulaFormatada;
    private String nomeUsuarioAssociado;  
    private Integer idUsuarioAssociado;

    public Aula(){
        this.status = "PENDENTE";
    }

    public Aula(Integer id_estudante, Integer id_monitor, String disciplina, String descricao, LocalDateTime data_solicitacao, LocalDateTime data_aula){
        this();
        this.id_estudante = id_estudante;
        this.id_monitor = id_monitor;
        this.disciplina = disciplina;
        this.descricao = descricao;
        this.data_solicitacao = data_solicitacao;
        this.data_aula = data_aula;
    }

    public Aula(String disciplina, String descricao, LocalDateTime data_aula){
        this();
        this.disciplina = disciplina;
        this.descricao = descricao;
        this.data_aula = data_aula;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_estudante() {
        return id_estudante;
    }

    public void setId_estudante(Integer id_estudante) {
        this.id_estudante = id_estudante;
    }

    public Integer getId_monitor() {
        return id_monitor;
    }

    public void setId_monitor(Integer id_monitor) {
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

    public String getDataAulaFormatada() {
        return dataAulaFormatada;
    }

    public void setDataAulaFormatada(String dataAulaFormatada) {
        this.dataAulaFormatada = dataAulaFormatada;
    }

    public void setNomeUsuarioAssociado(String nomeUsuarioAssociado) {
        this.nomeUsuarioAssociado = nomeUsuarioAssociado;
    }

    public String getNomeUsuarioAssociado() {
        return nomeUsuarioAssociado;
    }
    
    public Integer getIdUsuarioAssociado() {
        return idUsuarioAssociado;
    }

    public void setIdUsuarioAssociado(Integer idUsuarioAssociado) {
        this.idUsuarioAssociado = idUsuarioAssociado;
    }

}

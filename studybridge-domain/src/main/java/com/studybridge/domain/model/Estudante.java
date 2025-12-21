package com.studybridge.domain.model;

public class Estudante extends Usuario {
    
    private String nome;
    private String telefone;
    private String curso;
    private String anoTurma;
    private String campus;
    private String descricao;

    private Avaliacao avaliacaoRecebida;
    
    public Estudante(String email, String senhaHash){
        super(email, senhaHash, "Estudante"); 
        this.avaliacaoRecebida = new Avaliacao(this);
    }
    public Estudante() {
        super(null, null, "Estudante");
    }

    @Override
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }
    
    public String getAnoTurma() {
        return anoTurma;
    }
    public void setAnoTurma(String anoTurma) {
        this.anoTurma = anoTurma;
    }
    
    public String getCampus() {
        return campus;
    }
    public void setCampus(String campus) {
        this.campus = campus;
    }
    
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Avaliacao getAvaliacaoRecebida() {
        return avaliacaoRecebida;
    }
}
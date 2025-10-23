package com.studybridge.domain.model;

import java.util.UUID;

public class Usuario {
    private Integer id;
    private String email;
    private String senhaHash;
    private String tipoConta;
    private boolean verificado;
    private String tokenVerificacao;

    public Usuario() {
        this.verificado = false;
        this.tokenVerificacao = UUID.randomUUID().toString();
    }

    public Usuario(String email, String senhaHash, String tipoConta) {
        this();
        this.email = email;
        this.senhaHash = senhaHash;
        this.tipoConta = tipoConta;
    }

    // getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public String getTipoConta() { return tipoConta; }
    public void setTipoConta(String tipoConta) { this.tipoConta = tipoConta; }

    public boolean isVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }

    public String getTokenVerificacao() { return tokenVerificacao; }
    public void setTokenVerificacao(String tokenVerificacao) { this.tokenVerificacao = tokenVerificacao; }
}
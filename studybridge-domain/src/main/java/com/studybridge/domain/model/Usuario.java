package com.studybridge.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Usuario {
    private Integer id;
    private String email;
    private String senhaHash;
    private String tipoConta;
    private boolean verificado;
    private String tokenVerificacao;
    private String codigo2FA;
    private LocalDateTime expiracao2FA;

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

    public String getCodigo2FA() { return codigo2FA; }
    public void setCodigo2FA(String codigo2FA) { this.codigo2FA = codigo2FA; }

    public LocalDateTime getExpiracao2FA() { return expiracao2FA; }
    public void setExpiracao2FA(LocalDateTime expiracao2FA) { this.expiracao2FA = expiracao2FA; }
}

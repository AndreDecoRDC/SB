package com.studybridge.domain.model;
import java.time.LocalDateTime;

public class Notificacao {

    private Integer id;
    private String emailDestinatario;
    private String tipo;
    private String mensagem;
    private String link;
    private boolean lida;
    private LocalDateTime dataCriacao;

    public Integer getId() {
        return id;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getLink() {
        return link;
    }

    public boolean isLida() {
        return lida;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
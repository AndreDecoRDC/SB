package com.studybridge.domain.model;

public class Denuncia {
    public enum MotivoDenuncia{
        FALTA_SEM_AVISO,
        COMPORTAMENTO_INADEQUADO,
        ASSEDIO_DISCURSO_OFENSIVO,
        OUTRO
    }
    public enum StatusDenuncia{
        PENDENTE,
        EM_ANALISE,
        FINALIZADA,
        ARQUIVADA
    }
    private int id;
    private Usuario usuarioDenunciado;
    private Usuario usuarioDenunciante;
    private MotivoDenuncia motivoDenuncia;
    private String descricao;
    private StatusDenuncia status;

    public Denuncia(Usuario usuarioDenunciante, Usuario usuarioDenunciado, MotivoDenuncia mootivoDenuncia, String descricao) {
        this.usuarioDenunciante = usuarioDenunciante;
        this.usuarioDenunciado = usuarioDenunciado;
        this.motivoDenuncia = mootivoDenuncia;
        this.descricao = descricao == null ? "" : descricao;
        this.status = StatusDenuncia.PENDENTE;
    }
    public Denuncia( int id, Usuario usuarioDenunciante, Usuario usuarioDenunciado, MotivoDenuncia mootivoDenuncia, String descricao) {
        this.id = id;
        this.usuarioDenunciante = usuarioDenunciante;
        this.usuarioDenunciado = usuarioDenunciado;
        this.motivoDenuncia = mootivoDenuncia;
        this.descricao = descricao == null ? "" : descricao;
        this.status = StatusDenuncia.PENDENTE;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){this.id = id;}
    public Usuario getUsuarioDenunciante() {
        return usuarioDenunciante;
    }
    public void setUsuarioDenunciante(Usuario usuarioDenunciante){this.usuarioDenunciante = usuarioDenunciante;}
    public Usuario getUsuarioDenunciado() {
        return usuarioDenunciado;
    }
    public void setUsuarioDenunciado(Usuario usuarioDenunciado){this.usuarioDenunciado = usuarioDenunciado;}
    public MotivoDenuncia getMotivoDenuncia() {
        return motivoDenuncia;
    }
    public void setMotivoDenuncia(MotivoDenuncia motivoDenuncia){this.motivoDenuncia = motivoDenuncia;}
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao){this.descricao = descricao;}
    public StatusDenuncia getStatus() {
        return status;
    }
    public void setStatus(StatusDenuncia status) {
        this.status = status;
    }
}

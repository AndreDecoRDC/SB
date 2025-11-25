package com.studybridge.domain.model;

import java.util.ArrayList;
import java.util.List;

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
    private Usuario usuarioDenunciado;
    private Usuario usuarioDenunciante;
    private MotivoDenuncia motivoDenuncia;
    private String descricao;
    private StatusDenuncia status;
    private List<Denuncia> denunciasRecebidas;

    public Denuncia(Usuario usuarioDenunciante, Usuario usuarioDenunciado, MotivoDenuncia mootivoDenuncia, String descricao) {
        this.usuarioDenunciante = usuarioDenunciante;
        this.usuarioDenunciado = usuarioDenunciado;
        this.motivoDenuncia = mootivoDenuncia;
        this.descricao = descricao == null ? "" : descricao;
        this.status = StatusDenuncia.PENDENTE;
        this.denunciasRecebidas = new ArrayList<Denuncia>();
    }

    public Usuario getUsuarioDenunciante() {
        return usuarioDenunciante;
    }
    public Usuario getUsuarioDenunciado() {
        return usuarioDenunciado;
    }
    public MotivoDenuncia getMotivoDenuncia() {
        return motivoDenuncia;
    }
    public String getDescricao() {
        return descricao;
    }
    public StatusDenuncia getStatus() {
        return status;
    }
}

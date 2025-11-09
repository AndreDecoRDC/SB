package com.studybridge.domain.model;

public class Monitor extends Usuario{
    private Avaliacao avaliacaoRecebida;

    public Monitor(String email, String senhaHash){
        super(email, senhaHash, "Monitor");
        this.avaliacaoRecebida = new Avaliacao(this);
    }
    public Avaliacao getAvaliacaoRecebida() {
        return avaliacaoRecebida;
    }
}

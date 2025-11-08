package com.studybridge.domain.model;
import com.studybridge.service.Avaliacao;

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

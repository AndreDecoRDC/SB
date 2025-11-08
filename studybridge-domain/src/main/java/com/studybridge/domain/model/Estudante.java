package com.studybridge.domain.model;
import com.studybridge.service.Avaliacao;

public class Estudante extends Usuario{
    private Avaliacao avaliacaoRecebida;
    public Estudante(String email, String senhaHash){
        super(email, senhaHash, "Estudante");
        this.avaliacaoRecebida = new Avaliacao(this);
    }
    public Avaliacao getAvaliacaoRecebida() {
        return avaliacaoRecebida;
    }
}

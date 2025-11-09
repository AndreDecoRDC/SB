package com.studybridge.service;

import com.studybridge.dao.AvaliacaoDAO;
import com.studybridge.common.exceptions.NotaInvalidaException;
import com.studybridge.domain.model.Avaliacao;
import com.studybridge.domain.model.Usuario;

public class AvaliacaoService {
    private final AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO();

    public void registrarAvaliacao(Usuario usuarioAvaliado, double nota, String comentario) throws Exception{
        if(usuarioAvaliado == null){
            throw new Exception("Usuário não pode ser nulo.");
        }
        Avaliacao avaliacao = new Avaliacao(usuarioAvaliado);
        avaliacao.registrarAvaliacao(nota, comentario);
        if(avaliacao == null){
            throw new Exception("Avaliação não pode ser nula.");
        }
        avaliacaoDAO.salvarOuAtualizar(avaliacao);
    }
    public double calcularMedia(Usuario usuarioAvaliado) throws Exception{
        Avaliacao avaliacao = new Avaliacao(usuarioAvaliado);
        for(Avaliacao a : avaliacaoDAO.buscarPorUsuario(usuarioAvaliado)){
            for(int i = 0; i < a.getNotas().size(); i++){
                try{
                    avaliacao.registrarAvaliacao(a.getNotas().get(i), a.getComentarios().get(i));
                }catch (NotaInvalidaException ignored){}
            }
        }
        return avaliacao.calculoMediaNota();
    }
}

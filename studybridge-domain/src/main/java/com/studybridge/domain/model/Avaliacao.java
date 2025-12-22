package com.studybridge.domain.model;

import com.studybridge.common.exceptions.NotaInvalidaException;
import java.util.ArrayList;
import java.util.List;

public class Avaliacao{
    private Usuario usuarioAvaliado;
    private List<Double> notas;
    private List<String> comentarios;

    public Avaliacao(Usuario usuarioAvaliado){
        this.usuarioAvaliado = usuarioAvaliado;
        this.notas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    public void registrarAvaliacao(double nota, String comentario) throws NotaInvalidaException{
        if(nota < 1 || nota > 5){
            throw new NotaInvalidaException();
        }
        notas.add(nota);
        comentarios.add(comentario);
    }
    public double calculoMediaNota(){
        if (notas.isEmpty()){
            return 0;
        }
        double soma = 0;
        for (double nota : notas){
            soma += nota;
        }
        return soma / notas.size();
    }
    public Usuario getUsuarioAvaliado(){
        return usuarioAvaliado;
    }

    public List<Double> getNotas(){
        return notas;
    }
    public List<String> getComentarios(){
        return comentarios;
    }
    @Override
    public String toString(){
        return String.format("Média: %.2f (%d avaliações)", calculoMediaNota(), notas.size());
    }
}

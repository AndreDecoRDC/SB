package com.studybridge.service;

import com.studybridge.domain.Usuario;
import java.util.ArrayList;
import java.util.List;

public class Avaliacao{
    private Usuario usuarioAvaliado;
    private List<Double> notas = new ArrayList<>();
    private List<String> comentarios = new ArrayList<>();

    public Avaliacao(){
        this.usuarioAvaliado = usuarioAvaliado;
    }


    public void registrarAvaliacoes(double nota, String comentario) throws NotaInvalidaException{
        if(nota < 1 || nota > 5){
            throws new NotaInvalidaException();
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
    public void integraçãoBancoDeDados(){
    }

}

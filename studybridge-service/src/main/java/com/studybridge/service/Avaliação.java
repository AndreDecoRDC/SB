package com.studybridge.service;

import java.util.List;

public class Avaliação{
    private List<double> notas;
    private List<String> comentarios;



    public void registrarAvaliações(double nota, String comentario) throws NotaInvalidaException{
        if(nota < 1 || nota > 5){
            throws new NotaInvalidaException();
        }
        notas.add(nota);
        comentarios.add(comentario);
    }
    public double calculoMediaNota(){
        //recebe o array das notas de um usuário e retorna a media do usuario
        if (notas.isEmpty()){
            return 0;
        }
        double soma = 0;
        for (double nota : notas){
            soma += nota;
        }
        return soma / notas.size();
    }
    public List<Double> getNotas(){
        return notas;
    }
    public List<String> getComentarios(){
        return comentarios;
    }
    public void integraçãoBancoDeDados(){
    }

}

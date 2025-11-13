package com.studybridge.domain.model;
    
import java.util.ArrayList;
import java.util.List;

public class Monitor extends Usuario {
   private String disciplina;
   private String campus;
   private String descricao;
   private List<Horario> horarios = new ArrayList<>();
   private Avaliacao avaliacaoRecebida;

   public Monitor(String email, String senhaHash){
        super(email, senhaHash, "Monitor");
        this.avaliacaoRecebida = new Avaliacao(this);
   }
   public Avaliacao getAvaliacaoRecebida() {
        return avaliacaoRecebida;
   }
   
   public String getDisciplina() {
       return disciplina;
   }
   public void setDisciplina(String disciplina) {
       this.disciplina = disciplina;
   }
   
   public String getCampus() {
       return campus;
   }
   public void setCampus(String campus) {
       this.campus = campus;
   }
   
   public String getDescricao() {
       return descricao;
   }
   public void setDescricao(String descricao) {
       this.descricao = descricao;
   }
   
   public List<Horario> getHorarios() {
       return horarios;
   } 
   public void setHorarios(List<Horario> horarios) {
       this.horarios = horarios;
   }
}

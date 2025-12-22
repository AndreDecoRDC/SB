package com.studybridge.domain.model;
    
import java.util.ArrayList;
import java.util.List;

public class Monitor extends Usuario {
   private String nome;
   private String telefone;
   private String disciplina;
   private String campus;
   private String descricao;
   private List<Horario> horarios = new ArrayList<>();
   private Avaliacao avaliacaoRecebida;

   public Monitor(String email, String senhaHash){
        super(email, senhaHash, "Monitor");
        this.avaliacaoRecebida = new Avaliacao(this);
   }

    public Monitor() {
        super(null, null, "Monitor");
    }

   public Avaliacao getAvaliacaoRecebida() {
        return avaliacaoRecebida;
   }

   @Override
   public String getNome() {
       return nome;
   }
   public void setNome(String nome) {
       this.nome = nome;
   }
   
   public String getTelefone() {
       return telefone;
   }
   public void setTelefone(String telefone) {
       this.telefone = telefone;
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

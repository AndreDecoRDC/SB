package com.studybridge.domain.model;
import com.studybridge.common.model.TransformarMinutosEmHoras;
import java.time.LocalTime;

public class Horario {
    private int id;
    private int monitorId;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaTermino;
    private int duracaoMedia;
    
    public Horario() {}
    
    public Horario(int id, int monitorId, String diaSemana, LocalTime horaInicio, LocalTime horaTermino, int duracaoMedia) {
        this.id = id;
        this.monitorId = monitorId;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.duracaoMedia = duracaoMedia;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public int getMonitorId() {
        return monitorId;
    }
    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }
    
    public String getDiaSemana() {
        return diaSemana;
    }
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
    
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
        
    public LocalTime getHoraTermino() {
        return horaTermino;
    }
    public void setHoraTermino(LocalTime horaTermino) {
        this.horaTermino = horaTermino;
    }
       
    public int getDuracaoMedia() {
        return duracaoMedia;
    }
    public void setDuracaoMedia(int duracaoMedia) {
        this.duracaoMedia = duracaoMedia;
    }
    
    @Override
    public String toString() {
        return diaSemana + ": " + horaInicio + " - " + horaTermino + " · " + "Duração média: " + TransformarMinutosEmHoras.formatarDuracao(duracaoMedia);
    }
}

package com.studybridge.domain.model;

import java.time.LocalTime;

public class Horario {
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    
    public Horario(DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }
    
    public DiaSemana getDiaSemana() {
        return diaSemana;
    }
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public LocalTime getHoraFim() {
        return horaFim;
    }
    public void setHoraInicio(LocalTime NewHoraInicio) {
        this.horaInicio = NewHoraInicio;
    }
    public void setHoraFim(LocalTime NewHoraFim) {
        this.horaFim = NewHoraFim;
    }
    
    @Override
    public String toString() {
        return diaSemana + " - " + horaInicio + " às " + horaFim;
    }
}

//ia mandar para a Common, porém não está dando (não apareceu a package que criei)
enum DiaSemana {
    SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA;
}


package com.studybridge.common.model;

public class TransformarMinutosEmHoras {
    
    public static String formatarDuracao(int minutos) {
        int horas = minutos / 60;
        int mins = minutos % 60;

        if (horas > 0 && mins > 0)
            return horas + "h " + mins + "min";
        else if (horas > 0)
            return horas + "h";
        else
            return mins + "min";
    }
}

package com.studybridge.service;

import com.studybridge.domain.model.Usuario;

public class TestaEmail {
    public static void main(String[] args) {
        Usuario u = new Usuario("decosprite123@gmail.com", "hash123", "Estudante");
        EmailService service = new EmailService();

        try {
            service.enviarVerificacao(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

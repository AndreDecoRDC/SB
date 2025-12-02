package com.studybridge.service;

import com.studybridge.dao.UsuarioDAO;
import com.studybridge.domain.model.Usuario;

import jakarta.mail.MessagingException;
import com.studybridge.service.util.*;
import java.sql.SQLException;
import java.util.UUID;

/*
service responsavel pelo fluxo de redefinição de senha (csu07)
*/

public class RedefinirSenhaService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final EmailService emailService = new EmailService();

    public void iniciarRedefinicao(String email) throws Exception {
        Usuario u = usuarioDAO.buscarPorEmail(email);

        if (u == null) {
            return;
        }

        String token = UUID.randomUUID().toString();

        usuarioDAO.salvarTokenRedefinicao(u.getId(), token);

        try {
            emailService.enviarLinkRedefinicao(email, token);
        } catch (MessagingException e) {
            throw new Exception("Erro ao enviar e-mail de redefinição de senha.");
        }
    }

    public Usuario validarToken(String token) throws Exception {
        try {
            Usuario u = usuarioDAO.buscarPorTokenRedefinicao(token);

            if (u == null) {
                throw new Exception("Link inválido ou expirado.");
            }

            return u;
        } catch (SQLException e) {
            throw new Exception("Erro ao validar o token de redefinição.");
        }
    }

    public void alterarSenha(String token, String novaSenha) throws Exception {
        try {

            Usuario u = validarToken(token);

            String novaHash = HashUtil.gerarHash(novaSenha);

            if (novaHash.equals(u.getSenhaHash())) {
                throw new Exception("A nova senha não pode ser igual à senha atual.");
            }

            usuarioDAO.atualizarSenhaHash(u.getId(), novaHash);

            usuarioDAO.limparTokenRedefinicao(u.getId());
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar a senha.");
        }
    }
}

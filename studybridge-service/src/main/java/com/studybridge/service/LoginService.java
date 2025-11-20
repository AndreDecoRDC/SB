package com.studybridge.service;

import com.studybridge.dao.UsuarioDAO;
import com.studybridge.domain.model.Usuario;
import com.studybridge.service.util.HashUtil;
import java.sql.SQLException;
import java.util.Random;

public class LoginService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final EmailService emailService = new EmailService();

    public void iniciarLogin(String email, String senhaDigitada) throws Exception {
        try {
            Usuario usuario = usuarioDAO.buscarPorEmail(email);

            if (usuario == null)
                throw new Exception("E-mail não cadastrado.");

            if (!usuario.isVerificado())
                throw new Exception("Conta ainda não verificada. Verifique seu e-mail.");

            String senhaHash = HashUtil.gerarHash(senhaDigitada);
            if (!senhaHash.equals(usuario.getSenhaHash()))
                throw new Exception("Senha incorreta.");

            String codigo = gerarCodigo6();

            usuarioDAO.salvarCodigo2FA(usuario.getId(), codigo);

            emailService.enviarCodigo2FA(usuario.getEmail(), codigo);

        } catch (SQLException e) {
            throw new Exception("Erro ao acessar o banco de dados.", e);
        }
    }

    //gera ccodigo aleatorio de 6 digitos
    private String gerarCodigo6() {
        Random random = new Random();
        int numero = 100000 + random.nextInt(900000);
        return String.valueOf(numero);
    }
}

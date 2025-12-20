package com.studybridge.service;

import com.studybridge.dao.EstudanteDAO;
import com.studybridge.dao.MonitorDAO;
import com.studybridge.dao.UsuarioDAO;
import com.studybridge.domain.model.Usuario;
import com.studybridge.service.util.HashUtil;

import java.sql.SQLException;

public class CadastroService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final EstudanteDAO estudanteDAO = new EstudanteDAO();
    private final MonitorDAO monitorDAO = new MonitorDAO();
    private final EmailService emailService = new EmailService();

    public void cadastrar(String email, String senha, String confirmarSenha, String tipoConta) throws Exception {

        if (usuarioDAO.existePorEmail(email))
            throw new Exception("Email já cadastrado");

        String senhaHash = HashUtil.gerarHash(senha);
        Usuario novoUsuario = new Usuario(email, senhaHash, tipoConta);

        int usuarioId;

        try {
            usuarioId = usuarioDAO.inserir(novoUsuario);

            if ("Estudante".equals(tipoConta)) {
                estudanteDAO.inserir(usuarioId);
            } else if ("Monitor".equals(tipoConta)) {
                monitorDAO.inserir(usuarioId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erro ao salvar usuário no banco", e);
        }

        try {
            emailService.enviarVerificacao(novoUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String confirmarPorToken(String token) throws Exception {

        Usuario usuario = usuarioDAO.buscarPorToken(token);

        if (usuario == null)
            throw new Exception("Link inválido ou expirado");

        usuarioDAO.confirmarEmail(token);
        return usuario.getTipoConta();
    }
}

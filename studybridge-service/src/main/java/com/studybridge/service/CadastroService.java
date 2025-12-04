package com.studybridge.service;

import com.studybridge.dao.EstudanteDAO;
import com.studybridge.dao.MonitorDAO;
import com.studybridge.dao.UsuarioDAO;
import com.studybridge.domain.model.Usuario;
import com.studybridge.service.util.HashUtil;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.HexFormat;

public class CadastroService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final EmailService emailService = new EmailService();

    private final MonitorDAO monitorDAO = new MonitorDAO();
    private final EstudanteDAO estudanteDAO = new EstudanteDAO();
    
    public void cadastrar(String email, String senha, String confirmarSenha, String tipoConta) throws Exception {
 
        if (email == null || email.isBlank())
            throw new Exception("Informe um email");

        if (!email.contains("@"))
            throw new Exception("Email inválido");

        if (senha == null || senha.length() < 6)
            throw new Exception("A senha deve ter pelo menos 6 caracteres");

        if (!senha.equals(confirmarSenha))
            throw new Exception("As senhas não coincidem");

        if (!"Estudante".equals(tipoConta) && !"Monitor".equals(tipoConta))
            throw new Exception("Tipo de conta inválido");

        if (usuarioDAO.existePorEmail(email))
            throw new Exception("Email já cadastrado");

        String senhaHash = HashUtil.gerarHash(senha);

        Usuario novoUsuario = new Usuario(email, senhaHash, tipoConta);

        try {
            usuarioDAO.inserir(novoUsuario);
            int novoUsuarioId = novoUsuario.getId();
            
            if ("Estudante".equals(tipoConta)) {
                estudanteDAO.inserir(novoUsuarioId);
            }
            else if ("Monitor".equals(tipoConta)) {
                monitorDAO.inserir(novoUsuarioId);
            }
            
        } catch (SQLException e) {
            throw new Exception("Erro ao salvar usuário no banco", e);
        }

        emailService.enviarVerificacao(novoUsuario);
    }

    //esse metodo vai ser chamado quando clicarem no link que vai chegar no email
    public String confirmarPorToken(String token) throws Exception {

        if (token == null || token.isBlank())
            throw new Exception("Token ausente");

        try {
            Usuario usuario = usuarioDAO.buscarPorToken(token);
            if (usuario == null)
                throw new Exception("Link inválido ou expirado");

            usuarioDAO.confirmarEmail(token);
            return usuario.getTipoConta();

        } catch (SQLException e) {
            throw new Exception("Erro ao confirmar email no banco", e);
        }
    }
}

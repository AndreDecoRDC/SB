package com.studybridge.service;

import com.studybridge.dao.UsuarioDAO;
import com.studybridge.domain.model.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.HexFormat;

public class CadastroService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final EmailService emailService = new EmailService();

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

        String senhaHash = gerarHash(senha);

        Usuario novoUsuario = new Usuario(email, senhaHash, tipoConta);

        try {
            usuarioDAO.inserir(novoUsuario);
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

    /*
    importei a java.security.MessageDigest que tem metodo pra criptografar com base em um algoritmo específico
    nesse caso usei o algoritmo SHA-256, que gera um hash fixo a partir da senha digitada
    o digest() transforma a senha em um array de bytes, e depois converto esses bytes para hexadecimal
    usando o HexFormat.of().formatHex().
    esse valor é o hash da senha, que é o que vai ser salvo no banco no lugar da senha original
    assim, mesmo que alguém veja o banco, não consegue descobrir a senha verdadeira
    resumindo: gerarHash() pega a senha normal, aplica SHA-256, devolve uma versão segura em formato de texto
    */
    private String gerarHash(String senha) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(senha.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hashBytes);
    }
}

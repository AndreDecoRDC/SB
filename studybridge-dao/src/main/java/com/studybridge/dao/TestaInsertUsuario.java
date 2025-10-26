package com.studybridge.dao;

import com.studybridge.domain.model.Usuario;
import java.sql.SQLException;

public class TestaInsertUsuario {

    public static void main(String[] args) {

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail("teste" + System.currentTimeMillis() + "@gmail.com");
        novoUsuario.setSenhaHash("abc123");
        novoUsuario.setTipoConta("Estudante");
        novoUsuario.setVerificado(false);
        novoUsuario.setTokenVerificacao("token-" + System.currentTimeMillis());

        UsuarioDAO dao = new UsuarioDAO();

        try {
            dao.inserir(novoUsuario);
            System.out.println("Usuário inserido com sucesso");
            System.out.println("ID gerado: " + novoUsuario.getId());
            System.out.println("E-mail: " + novoUsuario.getEmail());
        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário:");
            e.printStackTrace();
        }
    }
}

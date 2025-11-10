package com.studybridge.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
esse servlet mostra a pagina de login do sistema
ele so exibe o jsp de login e nao faz nenhuma logica de autenticacao ainda
serve pra seguir o padrao de proteger os jsp dentro do web-inf
*/
@WebServlet("/login")
public class LoginPageServlet extends PageServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        render(req, res, "login");
    }
}

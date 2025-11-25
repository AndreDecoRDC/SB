package com.studybridge.web.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
esse servlet mostra a pagina de cadastro
usa forward pra chamar o jsp que esta dentro do web-inf
isso protege o arquivo e garante que so possa ser aberto pelo servlet
serve pra manter o padrao de acesso controlado a todas as paginas do sistema
*/


@WebServlet("/register")
public class RegisterPageServlet extends PageServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        render(req, res, "register");
    }
}
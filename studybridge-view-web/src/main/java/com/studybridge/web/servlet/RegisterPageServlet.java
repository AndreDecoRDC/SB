package com.studybridge.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
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
public class RegisterPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
}
package com.studybridge.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
servlet que responde quando o usuario acessa a raiz do site (/studybridge/)
ele so mostra a pagina inicial index.jsp usando forward
isso existe pra controlar o acesso ao jsp que fica dentro do web-inf
*/
@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //pra mandar o usuario direto pro index
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}
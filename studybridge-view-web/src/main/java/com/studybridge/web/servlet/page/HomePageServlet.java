package com.studybridge.web.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
servlet que responde quando o usuario acessa a raiz do site (/studybridge/)
ele so mostra a pagina inicial index.jsp usando forward
isso existe pra controlar o acesso ao jsp que fica dentro do web-inf
*/
@WebServlet("")
public class HomePageServlet extends PageServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        render(req, res, "index");
    }
}

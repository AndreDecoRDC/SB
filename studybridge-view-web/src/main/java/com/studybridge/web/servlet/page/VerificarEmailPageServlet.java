package com.studybridge.web.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
esse servlet mostra a pagina de verificar email apos o cadastro
ele recebe o email como parametro na url e passa esse valor pro jsp
se alguem tentar acessar a rota sem o parametro de email, ele manda de volta pro inicio
usa forward pra chamar o jsp que fica dentro do web-inf
serve pra manter o fluxo certo do cadastro e impedir acesso direto a tela
*/

@WebServlet("/verificar-email")
public class VerificarEmailPageServlet extends PageServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        req.setAttribute("email", email);

        render(req, res, "verificar-email");
    }
}

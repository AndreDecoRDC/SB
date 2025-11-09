package com.studybridge.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
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
public class VerificarEmailPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        request.setAttribute("email", email);

        if (email == null || email.isBlank()) {
            //redireciona pro início se a pessoa tentou entrar pela url
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/verificar-email.jsp").forward(request, response);
    }
}

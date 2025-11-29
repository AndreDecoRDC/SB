package com.studybridge.web.servlet.page;

import com.studybridge.service.RedefinirSenhaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
mostra o formulario para o usuario criar uma nova senha
a partir do token enviado por email (csu07)
*/

@WebServlet("/nova-senha")
public class NovaSenhaPageServlet extends PageServlet {

    private final RedefinirSenhaService service = new RedefinirSenhaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String token = req.getParameter("token");

        try {
            service.validarToken(token);

            req.setAttribute("token", token);

            render(req, res, "nova-senha");

        } catch (Exception e) {

            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/nova-senha.jsp").forward(req, res);
        }
    }
}

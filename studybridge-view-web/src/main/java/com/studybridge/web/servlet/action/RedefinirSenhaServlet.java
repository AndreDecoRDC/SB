package com.studybridge.web.servlet.action;

import com.studybridge.service.RedefinirSenhaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/redefinir-senha-action")
public class RedefinirSenhaServlet extends HttpServlet {

    private final RedefinirSenhaService service = new RedefinirSenhaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = req.getParameter("email");

        try {
            service.iniciarRedefinicao(email);

            req.setAttribute("msg", "Se o e-mail estiver cadastrado, enviaremos um link para redefinir sua senha.");
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
        }

        req.getRequestDispatcher("/WEB-INF/views/redefinir-senha.jsp").forward(req, res);
    }
}

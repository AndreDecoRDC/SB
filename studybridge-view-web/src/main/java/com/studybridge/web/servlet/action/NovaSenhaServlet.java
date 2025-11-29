package com.studybridge.web.servlet.action;

import com.studybridge.service.RedefinirSenhaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/nova-senha-action")
public class NovaSenhaServlet extends HttpServlet {

    private final RedefinirSenhaService service = new RedefinirSenhaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String token = req.getParameter("token");
        String senha1 = req.getParameter("senha1");
        String senha2 = req.getParameter("senha2");

        if (senha1 == null || senha2 == null || !senha1.equals(senha2)) {
            req.setAttribute("erro", "As senhas não coincidem.");
            req.setAttribute("token", token);
            req.getRequestDispatcher("/WEB-INF/views/nova-senha.jsp").forward(req, res);
            return;
        }

        try {
            service.alterarSenha(token, senha1);

            res.sendRedirect(req.getContextPath() + "/login?senhaRedefinida=1");

        } catch (Exception e) {

            req.setAttribute("erro", e.getMessage());
            req.setAttribute("token", token);
            req.getRequestDispatcher("/WEB-INF/views/nova-senha.jsp").forward(req, res);
        }
    }
}

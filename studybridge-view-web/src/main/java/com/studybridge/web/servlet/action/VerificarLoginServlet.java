package com.studybridge.web.servlet.action;

import com.studybridge.dao.UsuarioDAO;
import com.studybridge.domain.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/verificar-login-action")
public class VerificarLoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = (String) req.getSession().getAttribute("emailLogin");

        if (email == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String codigo = req.getParameter("codigo");

        try {
            Usuario usuario = usuarioDAO.buscarPorEmail(email);

            if (usuario == null) {
                throw new Exception("Usuário não encontrado.");
            }

            if (!usuarioDAO.validarCodigo2FA(usuario.getId(), codigo)) {
                throw new Exception("Código inválido ou expirado.");
            }

            usuarioDAO.limparCodigo2FA(usuario.getId());

            req.getSession().setAttribute("usuarioLogado", usuario);

            switch (usuario.getTipoConta()) {
                case "Estudante" ->
                        res.sendRedirect(req.getContextPath() + "/estudante/dashboard");

                case "Monitor" ->
                        res.sendRedirect(req.getContextPath() + "/monitor/dashboard");

                case "Administrador" ->
                        res.sendRedirect(req.getContextPath() + "/admin/dashboard");

                default -> throw new Exception("Tipo de conta inválido.");
            }

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/verificar-login.jsp").forward(req, res);
        }
    }
}

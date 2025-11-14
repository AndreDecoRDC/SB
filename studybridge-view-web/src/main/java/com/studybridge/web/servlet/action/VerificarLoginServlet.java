package com.studybridge.web.servlet.action;

import com.studybridge.dao.UsuarioDAO;
import com.studybridge.domain.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/verificar-login")
public class VerificarLoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/verificar-login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //pega o email salvo na sessao na etapa anterior
        String email = (String) request.getSession().getAttribute("emailLogin");

        //se tentar acessar sem passar pelo login volta pro inicio
        if (email == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String codigo = request.getParameter("codigo");

        try {
            Usuario usuario = usuarioDAO.buscarPorEmail(email);

            if (usuario == null) {
                throw new Exception("Usuário não encontrado.");
            }

            boolean valido = usuarioDAO.validarCodigo2FA(usuario.getId(), codigo);

            if (!valido) {
                throw new Exception("Código inválido ou expirado.");
            }

            usuarioDAO.limparCodigo2FA(usuario.getId());

            request.getSession().setAttribute("usuarioLogado", usuario);

            request.getSession().removeAttribute("emailLogin");

            switch (usuario.getTipoConta()) {
                case "Estudante" -> response.sendRedirect(request.getContextPath() + "/estudante-dashboard");
                case "Monitor" -> response.sendRedirect(request.getContextPath() + "/monitor-dashboard");
                case "Administrador" -> response.sendRedirect(request.getContextPath() + "/admin-dashboard");
                default -> throw new Exception("Tipo de conta inválido.");
            }

        } catch (Exception e) {

            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/verificar-login.jsp").forward(request, response);
        }
    }
}

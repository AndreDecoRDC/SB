package com.studybridge.web.servlet.page;

import com.studybridge.domain.model.Usuario;
import com.studybridge.domain.model.Estudante;
import com.studybridge.service.PerfilService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/estudante/perfil")
public class EstudantePerfilPageServlet extends PageServlet {

    private final PerfilService perfilService = new PerfilService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Usuario usuarioLogado = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if (usuarioLogado == null || !"Estudante".equals(usuarioLogado.getTipoConta())) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            Estudante estudante = perfilService.carregarPerfilEstudante(usuarioLogado.getId());

            req.setAttribute("estudante", estudante);

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
        }

        render(req, res, "estudante/perfil-estudante");
    }
}

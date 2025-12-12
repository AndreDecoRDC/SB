package com.studybridge.web.servlet.page;

import com.studybridge.domain.model.Usuario;
import com.studybridge.domain.model.Monitor;
import com.studybridge.service.PerfilService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/monitor/perfil")
public class MonitorPerfilPageServlet extends PageServlet {

    private final PerfilService perfilService = new PerfilService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Usuario usuarioLogado = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if (usuarioLogado == null || !"Monitor".equals(usuarioLogado.getTipoConta())) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            Monitor monitor = perfilService.carregarPerfilMonitor(usuarioLogado.getId());

            req.setAttribute("monitor", monitor);

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
        }

        render(req, res, "monitor/perfil-monitor");
    }
}

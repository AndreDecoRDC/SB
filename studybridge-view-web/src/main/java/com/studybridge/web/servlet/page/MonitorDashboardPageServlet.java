package com.studybridge.web.servlet.page;

import com.studybridge.domain.model.Usuario;
import com.studybridge.service.AulaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/monitor/dashboard")
public class MonitorDashboardPageServlet extends PageServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {

            Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");

            if (usuario == null) {
                res.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            String emailMonitor = usuario.getEmail();

            req.setAttribute("proximaAula", aulaService.getProximaAula(emailMonitor));

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
        }

        render(req, res, "monitor/dashboard");
    }
}

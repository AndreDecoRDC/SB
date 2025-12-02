package com.studybridge.web.servlet.page;

import com.studybridge.service.AulaService;
import com.studybridge.domain.model.Aula;
import com.studybridge.domain.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/monitor/aulas-monitor")
public class AulasMonitorPageServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/index");
            return;
        }

        if (!"monitor".equals(usuario.getTipoConta())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Integer idUsuario = usuario.getId();

        try {
            Integer idMonitor = usuario.getId();

            List<Aula> aulas = aulaService.listarAulasDoMonitor(idMonitor);

            req.setAttribute("aulas", aulas);
            req.getRequestDispatcher("/WEB-INF/views/monitor/aulas-monitor.jsp")
               .forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar aulas do monitor", e);
        }
    }
}
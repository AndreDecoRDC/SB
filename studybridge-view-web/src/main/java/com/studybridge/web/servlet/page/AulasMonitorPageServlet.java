package com.studybridge.web.servlet.page;

import com.studybridge.service.AulaService;
import com.studybridge.domain.model.Aula;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("aulas-monitor")
public class AulasMonitorPageServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            HttpSession session = req.getSession();
            Integer idMonitor = (Integer) session.getAttribute("idUsuario");

            List<Aula> aulas = aulaService.listarAulasDoMonitor(idMonitor);

            req.setAttribute("aulas", aulas);
            req.getRequestDispatcher("/WEB-INF/aulas-monitor.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar aulas do monitor", e);
        }
    }
}

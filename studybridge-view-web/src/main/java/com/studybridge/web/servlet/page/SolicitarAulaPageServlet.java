package com.studybridge.web.servlet.page;

import com.studybridge.dao.MonitorDAO;
import com.studybridge.domain.model.MonitorBusca;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/estudante/solicitar")
public class SolicitarAulaPageServlet extends PageServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String monitorId = req.getParameter("usuarioId");

        if (monitorId == null || monitorId.isBlank()) {
            res.sendRedirect(req.getContextPath() + "/estudante/busca");
            return;
        }
    
        MonitorDAO monitorDAO = new MonitorDAO();
        MonitorBusca monitor;

        try {
            monitor = monitorDAO.buscarPorUsuarioId(monitorId);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        req.setAttribute("monitorEmail", monitor.getEmail());
        req.setAttribute("monitorId", monitor.getUsuarioId()); // se quiser usar em outro lugar
        req.setAttribute("monitorNome", monitor.getNome());

        
        render(req, res, "estudante/solicitar");
    }
}

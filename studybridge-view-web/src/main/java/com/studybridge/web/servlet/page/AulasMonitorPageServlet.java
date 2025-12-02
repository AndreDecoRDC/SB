package com.studybridge.web.servlet.page;

import com.studybridge.service.AulaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/monitor/aulas")
public class AulasMonitorPageServlet extends PageServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            req.setAttribute("solicitacoesPendentes", aulaService.listarSolicitacoesPendentes());
            req.setAttribute("aulasAceitas", aulaService.listarAceitas());
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
        }

        render(req, res, "monitor/aulas-monitor");
    }
}


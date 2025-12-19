package com.studybridge.web.servlet.action;

import com.studybridge.service.AulaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/monitor/recusar-solicitacao")
public class RecusarSolicitacaoServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            aulaService.recusarSolicitacao(id);

            resp.sendRedirect(req.getContextPath() + "/monitor/aulas-monitor");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/monitor/aulas?erro=" + e.getMessage());
        }
    }
}


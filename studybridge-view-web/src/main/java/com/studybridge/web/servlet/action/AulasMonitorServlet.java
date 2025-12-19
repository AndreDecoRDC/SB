package com.studybridge.web.servlet.action;

import com.studybridge.service.AulaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/monitor/aceitar-solicitacao")
public class AulasMonitorServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            aulaService.aceitarSolicitacao(id);

            resp.sendRedirect(req.getContextPath() + "/monitor/aulas-monitor");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/monitor/dashboard?erro=" + e.getMessage());
        }

        }

    }


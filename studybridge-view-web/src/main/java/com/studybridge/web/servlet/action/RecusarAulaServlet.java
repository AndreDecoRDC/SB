package com.studybridge.web.servlet.action;

import com.studybridge.service.AulaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/monitor/aula/recusar")
public class RecusarAulaServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int idAula = Integer.parseInt(req.getParameter("idAula"));
            aulaService.recusarAula(idAula);

            resp.sendRedirect(req.getContextPath() + "/monitor/aulas");

        } catch (Exception e) {
            throw new ServletException("Erro ao recusar aula", e);
        }
    }
}

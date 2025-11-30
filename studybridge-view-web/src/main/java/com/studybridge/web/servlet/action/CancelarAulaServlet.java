package com.studybridge.web.servlet.action;

import com.studybridge.service.AulaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/estudante/aula/cancelar")
public class CancelarAulaServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int idAula = Integer.parseInt(req.getParameter("idAula"));
            aulaService.cancelarAula(idAula);

            resp.sendRedirect(req.getContextPath() + "/estudante/aulas");

        } catch (Exception e) {
            throw new ServletException("Erro ao cancelar aula", e);
        }
    }
}

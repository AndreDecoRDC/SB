package com.studybridge.web.servlet.action;

import com.studybridge.service.AulaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/*
@WebServlet("/monitor/deletar-aula")
public class DeletarAulaMonitorServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            aulaService.deletarAula(id);

            resp.sendRedirect(req.getContextPath() + "/monitor/aulas");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/monitor/aulas?erro=" + e.getMessage());
        }
    }
}
*/
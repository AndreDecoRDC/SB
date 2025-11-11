package com.studybridge.web.servlet;

import com.studybridge.domain.model.Horario;
import com.studybridge.service.GerenciarHorarioService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/confirmar-remocao")
public class ConfirmarRemocaoServlet extends HttpServlet {
    private final GerenciarHorarioService service = new GerenciarHorarioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idHorario = req.getParameter("id");
        try {
            int id = Integer.parseInt(idHorario);
            Horario h = service.getHorarioById(id);
            req.setAttribute("horario", h);
            req.getRequestDispatcher("/WEB-INF/views/confirmar-remocao.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("erro", "Erro ao carregar horário: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/editar-horarios.jsp").forward(req, resp);
        }
    }
}

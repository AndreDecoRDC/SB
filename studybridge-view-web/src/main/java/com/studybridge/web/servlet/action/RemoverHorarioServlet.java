package com.studybridge.web.servlet.action;

import com.studybridge.domain.model.Horario;
import com.studybridge.service.GerenciarHorarioService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/remover-horario")
public class RemoverHorarioServlet extends HttpServlet {
    private final GerenciarHorarioService service = new GerenciarHorarioService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idHorario = req.getParameter("id");
        try {
            int id = Integer.parseInt(idHorario);
            Horario horario = service.getHorarioById(id);
            service.removerHorario(horario);
            
            
            resp.sendRedirect(req.getContextPath() + "/editar-horarios");
        } catch (Exception e) {
            req.setAttribute("erro", "Erro ao remover: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/editar-horarios.jsp").forward(req, resp);
        }
    }
}

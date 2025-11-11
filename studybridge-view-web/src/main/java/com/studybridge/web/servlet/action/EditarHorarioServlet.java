package com.studybridge.web.servlet.action;

import com.studybridge.domain.model.Horario;
import com.studybridge.service.GerenciarHorarioService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/editar-horarios")
public class EditarHorarioServlet extends HttpServlet {

    private final GerenciarHorarioService service = new GerenciarHorarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int monitorId = 1; // teste enqnt não tem sistema de login

            /* 
            HttpSession session = req.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario == null) {
                resp.sendRedirect(req.getContextPath() + "/index");
                return;
            }
            int monitorId = usuario.getId();
            */
            List<Horario> horarios = service.listarHorario(monitorId);
            req.setAttribute("horarios", horarios);
            req.getRequestDispatcher("/WEB-INF/views/editar-horarios.jsp").forward(req, resp);
           
        } catch (Exception e) {
            req.setAttribute("erro", "Erro ao carregar horários: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/erro-horario.jsp").forward(req, resp);        
        }
    }
}
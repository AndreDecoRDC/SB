package com.studybridge.web.servlet.page;

import com.studybridge.service.AulaService;
import com.studybridge.domain.model.Aula;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/monitor/aula/recusar-page")
public class ConfirmarRecusarPageServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("idAula");
        try {
            int id = Integer.parseInt(idParam);

            Aula aula = aulaService.listarAulasDoMonitor((Integer) req.getSession().getAttribute("idUsuario"))
                                  .stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);

            req.setAttribute("aula", aula);
            req.getRequestDispatcher("/WEB-INF/views/confirma-recusar.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Erro ao abrir confirmação de recusa.", e);
        }
    }
}

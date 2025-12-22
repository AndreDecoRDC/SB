package com.studybridge.web.servlet.page;

import com.studybridge.dao.HorarioDAO;
import com.studybridge.dao.JDBCHorarioDAO;
import com.studybridge.domain.model.Horario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/estudante/ver-horarios")
public class EstudanteVerHorariosPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String monitorIdParam = request.getParameter("monitorId");

        if (monitorIdParam == null || monitorIdParam.isBlank()) {
            request.setAttribute("horarios", List.of());
        } else {
            try {
                int monitorId = Integer.parseInt(monitorIdParam);

                JDBCHorarioDAO dao = new JDBCHorarioDAO();
                List<Horario> horarios = dao.getAllHorarios(monitorId);

                request.setAttribute("horarios", horarios);

            } catch (Exception e) {
                throw new ServletException(e);
            }
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/estudante/horarios-lista.jsp"
        ).forward(request, response);
    }
}

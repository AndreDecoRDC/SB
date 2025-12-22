package com.studybridge.web.servlet.page;

import com.studybridge.dao.MonitorDAO;
import com.studybridge.domain.model.MonitorBusca;
import com.studybridge.domain.model.Monitor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/estudante/busca")
public class EstudanteBuscarMonitorPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nome = request.getParameter("nome");
        String disciplina = request.getParameter("disciplina");
        String ordenar = request.getParameter("ordenar");
        
        boolean temFiltro
                = (nome != null && !nome.isBlank())
                || (disciplina != null && !disciplina.isBlank())
                || (ordenar !=null && !ordenar.isBlank());
        
        if(temFiltro) {
            MonitorDAO dao = new MonitorDAO();
            
            try {
                List<MonitorBusca> monitores
                        = dao.buscarMonitores(nome, disciplina, ordenar);
                
                request.setAttribute("monitores", monitores);
            } catch (Exception e) {
                throw new ServletException(e);
            }
            
        } else {
            request.setAttribute("monitores", null);
        }
        
        request.setAttribute("nome", nome);
        request.setAttribute("disciplina", disciplina);
        request.setAttribute("ordenar", ordenar);

        request.getRequestDispatcher("/WEB-INF/views/estudante/estudante-busca.jsp")
                .forward(request, response);
        
    }
}


package com.studybridge.web.servlet.page;

import com.studybridge.dao.AulaDAO;
import com.studybridge.dao.MonitorDAO;
import com.studybridge.domain.model.Aula;
import com.studybridge.domain.model.MonitorBusca;
import com.studybridge.domain.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/estudante/dashboard")
public class EstudanteDashboardPageServlet extends PageServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Usuario usuarioLogado = (Usuario) req.getSession().getAttribute("usuarioLogado");
        String idEstudante = String.valueOf(usuarioLogado.getId());

        AulaDAO aulaDAO = new AulaDAO();
        MonitorDAO monitorDAO = new MonitorDAO();

        try {
            
            Aula proxima = aulaDAO.buscarProximaAulaEstudante(idEstudante);
            MonitorBusca melhor = monitorDAO.buscarMelhorMonitor();
            Aula ultima = aulaDAO.buscarUltimaAulaConcluida(idEstudante);

            List<Aula> historico = aulaDAO.listarConcluidasPorEstudante(idEstudante);

            
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            if (proxima != null && proxima.getData_aula() != null) {
                proxima.setDataAulaFormatada(proxima.getData_aula().format(fmt));
            }
            if (ultima != null && ultima.getData_aula() != null) {
                ultima.setDataAulaFormatada(ultima.getData_aula().format(fmt));
            }
     
            for (Aula a : historico) {
                if (a.getData_aula() != null) {
                    a.setDataAulaFormatada(
                            a.getData_aula().format(fmt)
                    );
                }
            }

            req.setAttribute("proximaAula", proxima);
            req.setAttribute("monitorBom", melhor);
            req.setAttribute("ultimaAula", ultima);
            req.setAttribute("historico", historico);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        render(req, res, "estudante/estudante-dashboard");
    }
}

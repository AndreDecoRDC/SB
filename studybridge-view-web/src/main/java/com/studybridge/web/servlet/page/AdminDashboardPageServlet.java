package com.studybridge.web.servlet.page;

import com.studybridge.dao.DenunciaDAO;
import com.studybridge.domain.model.Denuncia;
import com.studybridge.service.DenunciaService;
import com.studybridge.dao.AulaDAO;
import com.studybridge.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


@WebServlet("/admin/dashboard")
public class AdminDashboardPageServlet extends PageServlet {

    private final DenunciaService denunciaService = new DenunciaService();

   private UsuarioDAO usuarioDAO = new UsuarioDAO();
   private AulaDAO aulaDAO = new AulaDAO();
   private DenunciaDAO denunciaDAO = new DenunciaDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {

            int totalAtivos = usuarioDAO.contarTotalAtivos();
            int estudantesAtivos = usuarioDAO.contarEstudantesAtivos();
            int monitoresAtivos = usuarioDAO.contarMonitoresAtivos();
            int suspensos = usuarioDAO.contarUsuariosInativos();

            int totalAulas = aulaDAO.contarTotalAulas();
            int aulasMarcadas = aulaDAO.contarAulasMarcadas();
            int aulasConcluidas = aulaDAO.contarAulasConcluidas();

            int denunciasAtivas = denunciaDAO.contarDenunciasAtivas();

            req.setAttribute("totalAtivos", totalAtivos);
            req.setAttribute("estudantesAtivos", estudantesAtivos);
            req.setAttribute("monitoresAtivos", monitoresAtivos);
            req.setAttribute("suspensos", suspensos);

            req.setAttribute("totalAulas", totalAulas);
            req.setAttribute("aulasMarcadas", aulasMarcadas);
            req.setAttribute("aulasConcluidas", aulasConcluidas);
            req.setAttribute("denunciasAtivas", denunciasAtivas);

            render(req, res, "admin/admin-dashboard");
        } catch (Exception e) {
            throw new ServletException("Erro ao carregar dados do painel administrativo.", e);
        }


    }
}

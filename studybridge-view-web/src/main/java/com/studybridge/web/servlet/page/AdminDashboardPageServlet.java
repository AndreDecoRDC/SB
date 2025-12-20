package com.studybridge.web.servlet.page;

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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        List<Denuncia> denunciasPendentes = Collections.emptyList();
        try{
            denunciasPendentes = denunciaService.obterDenunciasPendentes();

        }catch(SQLException e){
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao carregar dados do dashboard.");
        }
        req.setAttribute("denunciasPendentes", denunciasPendentes);
        render(req, res, "admin/admin-dashboard");

        try {

            int totalAtivos = usuarioDAO.contarTotalAtivos();
            int estudantesAtivos = usuarioDAO.contarEstudantesAtivos();
            int monitoresAtivos = usuarioDAO.contarMonitoresAtivos();
            int suspensos = usuarioDAO.contarUsuariosInativos();

            int totalAulas = aulaDAO.contarTotalAulas();
            int aulasMarcadas = aulaDAO.contarAulasMarcadas();
            int aulasConcluidas = aulaDAO.contarAulasConcluidas();

            req.setAttribute("totalAtivos", totalAtivos);
            req.setAttribute("estudantesAtivos", estudantesAtivos);
            req.setAttribute("monitoresAtivos", monitoresAtivos);
            req.setAttribute("suspensos", suspensos);

            req.setAttribute("totalAulas", totalAulas);
            req.setAttribute("aulasMarcadas", aulasMarcadas);
            req.setAttribute("aulasConcluidas", aulasConcluidas);

            //Não está concluído o CSU de denúncias, então não consigo preencher a parte de denúncias
            req.setAttribute("denunciasAtivas", 7);

            render(req, res, "admin/admin-dashboard");
        } catch (Exception e) {
            throw new ServletException("Erro ao carregar dados do painel administrativo.", e);
        }


    }
}

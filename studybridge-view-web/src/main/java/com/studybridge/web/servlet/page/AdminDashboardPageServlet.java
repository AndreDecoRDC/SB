package com.studybridge.web.servlet.page;

import com.studybridge.domain.model.Denuncia;
import com.studybridge.service.DenunciaService;
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
    }
}

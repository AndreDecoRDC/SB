package com.studybridge.web.servlet.action;

import com.studybridge.dao.DenunciaDAO;
import com.studybridge.dao.UsuarioDAO;
import com.studybridge.domain.model.Denuncia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/denuncias")
public class AdminDenunciaServlet extends HttpServlet {
    private final DenunciaDAO denunciaDAO = new DenunciaDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Denuncia> denuncias = denunciaDAO.buscarDenunciasPendentes()   ;
            if(denunciaDAO.buscarDenunciasPendentes() == null){
                denuncias = new ArrayList<>();
            }
            req.setAttribute("denuncias", denuncias);

            req.getRequestDispatcher("/WEB-INF/views/admin/admin-denuncias.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print("Erro no Servlet: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String acao = req.getParameter("acao");
            String idDenunciaString = req.getParameter("idDenuncia");
            if(idDenunciaString == null || idDenunciaString.isEmpty()){
                resp.sendRedirect(req.getContextPath() + "/admin/denuncias");
                return;
            }

            int idDenuncia = Integer.parseInt(idDenunciaString);
            if("suspender".equals(acao)){
                String idUsuarioString = req.getParameter("idUsuario");
                if(idUsuarioString != null && !idUsuarioString.isEmpty()){
                    int idUsuario = Integer.parseInt((idUsuarioString));
                    usuarioDAO.atualizarStatusAtivo(idUsuario, false);
                    denunciaDAO.atualizarStatus(idDenuncia, Denuncia.StatusDenuncia.FINALIZADA);
                }
            }else if("arquivar".equals(acao)){
                denunciaDAO.atualizarStatus(idDenuncia, Denuncia.StatusDenuncia.ARQUIVADA);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/denuncias");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erro ao processar a ação na denúncia", e);
        }
    }
}
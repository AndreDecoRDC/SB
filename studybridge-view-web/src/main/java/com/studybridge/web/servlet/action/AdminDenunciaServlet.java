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
            List<Denuncia> denuncias = denunciaDAO.buscarDenuncias();
            if(denunciaDAO.buscarDenuncias() == null){
                System.out.println("Lista Vazia do DAO");
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
            int idUsuario = Integer.parseInt(req.getParameter("idUsuario"));
            usuarioDAO.atualizarStatusAtivo(idUsuario, false);
            resp.sendRedirect(req.getContextPath() + "/admin/denuncias");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }
}
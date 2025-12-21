package com.studybridge.web.servlet.page;

import com.studybridge.dao.UsuarioDAO;
import com.studybridge.domain.model.UsuarioAdmin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/busca")
public class AdminBuscarUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String tipo = request.getParameter("tipo");
        String ordenar = request.getParameter("ordenar");        

        boolean temFiltro
                = (nome != null && !nome.isBlank())
                || (tipo != null && !tipo.isBlank())
                || (ordenar != null && !ordenar.isBlank());

        if (temFiltro) {
            UsuarioDAO dao = new UsuarioDAO();

            try {
                List<UsuarioAdmin> usuarios
                        = dao.buscarUsuariosAdmin(nome, tipo, ordenar);

                request.setAttribute("usuarios", usuarios);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            request.setAttribute("usuarios", null);
        }

        request.setAttribute("nome", nome);
        request.setAttribute("tipo", tipo);
        request.setAttribute("ordenar", ordenar);

        request.getRequestDispatcher("/WEB-INF/views/admin/admin-busca.jsp")
                .forward(request, response);

    }
}

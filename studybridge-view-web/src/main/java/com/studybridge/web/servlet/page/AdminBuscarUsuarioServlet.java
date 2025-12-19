package com.studybridge.web.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/busca")
public class AdminBuscarUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String tipo = request.getParameter("tipo");
        String ordenar = request.getParameter("ordenar");

        request.setAttribute("usuarios", null);

        request.getRequestDispatcher("/WEB-INF/views/admin/admin-busca.jsp")
               .forward(request, response);
    }
}

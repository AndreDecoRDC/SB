package com.studybridge.web.servlet.action;

import com.studybridge.dao.MonitorDAO;
import com.studybridge.domain.model.Monitor;
import com.studybridge.domain.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/salvar-perfil-monitor")
public class SalvarPerfilMonitorServlet extends HttpServlet {

    private final MonitorDAO monitorDAO = new MonitorDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Monitor m = new Monitor();
        m.setNome(req.getParameter("nome"));
        m.setTelefone(req.getParameter("telefone"));
        m.setDisciplina(req.getParameter("disciplina"));
        m.setCampus(req.getParameter("campus"));
        m.setDescricao(req.getParameter("descricao"));

        try {
            monitorDAO.atualizarPerfil(usuario.getId(), m);

            req.getSession().setAttribute("msgSucesso", "Perfil atualizado com sucesso!");

            resp.sendRedirect(req.getContextPath() + "/monitor/perfil");
        } catch (Exception ex) {
            req.setAttribute("erro", "Erro ao salvar perfil");
            req.setAttribute("monitor", m);
            req.getRequestDispatcher("/WEB-INF/views/monitor/perfil.jsp")
                    .forward(req, resp);
        }
    }
}

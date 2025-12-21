package com.studybridge.web.servlet.action;

import com.studybridge.dao.EstudanteDAO;
import com.studybridge.domain.model.Estudante;
import com.studybridge.domain.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/salvar-perfil-estudante")
public class SalvarPerfilEstudanteServlet extends HttpServlet {

    private final EstudanteDAO estudanteDAO = new EstudanteDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Estudante e = new Estudante();
        e.setNome(req.getParameter("nome"));
        e.setTelefone(req.getParameter("telefone"));
        e.setCurso(req.getParameter("curso"));
        e.setAnoTurma(req.getParameter("turma"));
        e.setCampus(req.getParameter("campus"));
        e.setDescricao(req.getParameter("descricao"));

        try {
            estudanteDAO.atualizarPerfil(usuario.getId(), e);

            req.getSession().setAttribute("msgSucesso", "Perfil atualizado com sucesso!");

            resp.sendRedirect(req.getContextPath() + "/estudante/perfil");
        } catch (Exception ex) {

            req.setAttribute("erro", "Erro ao salvar perfil");
            req.setAttribute("estudante", e);
            req.getRequestDispatcher("/WEB-INF/views/estudante/perfil.jsp")
                    .forward(req, resp);
        }
    }
}

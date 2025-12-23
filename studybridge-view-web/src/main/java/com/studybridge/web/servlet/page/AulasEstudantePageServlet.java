package com.studybridge.web.servlet.page;

import com.studybridge.service.AulaService;
import com.studybridge.domain.model.Aula;
import com.studybridge.domain.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/estudante/aulas-estudante")
public class AulasEstudantePageServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            throw new ServletException("Usuário não está logado.");
        }

        if (!"Estudante".equals(usuario.getTipoConta())) {
            throw new ServletException("Acesso permitido somente para estudantes.");
        }

        Integer idEstudante = usuario.getId();

        try {
            List<Aula> aulas = aulaService.listarAulasPorEmailEstudante(usuario.getEmail());
            req.setAttribute("aulas", aulas);
            req.getRequestDispatcher("/WEB-INF/views/estudante/aulas-estudante.jsp")
                    .forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Erro ao carregar aulas do estudante.", e);
        }
    }
}

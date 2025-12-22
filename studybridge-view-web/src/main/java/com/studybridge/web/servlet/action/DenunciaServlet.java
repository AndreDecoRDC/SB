package com.studybridge.web.servlet.action;

import com.studybridge.service.AulaService;
import com.studybridge.domain.model.Aula;
import com.studybridge.domain.model.Denuncia;
import com.studybridge.domain.model.Usuario;
import com.studybridge.service.DenunciaService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/denuncias")
public class DenunciaServlet extends HttpServlet {
    private final DenunciaService denunciaService = new DenunciaService();
    private final AulaService aulaService = new AulaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int idDenunciado = Integer.parseInt(req.getParameter("denunciadoId"));
            String motivoStr = req.getParameter("motivo");
            String descricao = req.getParameter("descricao");

            Usuario denunciado = new Usuario();
            denunciado.setId(idDenunciado);

            Denuncia.MotivoDenuncia motivoEnum = Denuncia.MotivoDenuncia.valueOf(motivoStr);

            denunciaService.registrarDenuncia(usuarioLogado, denunciado, motivoEnum, descricao);

            session.setAttribute("denunciaSucesso", "true");
            resp.sendRedirect(req.getContextPath() + "/denuncias");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            List<Aula> aulas = aulaService.listarAulasDoEstudante(usuarioLogado.getId());
            req.setAttribute("aulas", aulas);
            req.getRequestDispatcher("/WEB-INF/views/estudante/aulas-estudante.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
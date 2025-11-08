package com.studybridge.web.servlet;

import com.studybridge.service.Avaliacao;
import com.studybridge.domain.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;


@WebServlet("/avaliar")
public class AvaliacaoServlet extends HttpServlet{
    private Avaliacao avaliacaoEstudante =  new Avaliacao();
    private Avaliacao avaliacaoMonitor =  new Avaliacao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double nota = Double.parseDouble(req.getParameter("nota"));
        String comentario = req.getParameter("comentario");
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            resp.sendRedirect("login");
        }

        try{
            avaliacao.registrarAvaliacoes(nota, comentario);
            double media = avaliacao.calculoMediaNota();

            req.setAttribute("media", media);
            req.setAttribute("comentarios", avaliacao.getComentarios());
            RequestDispatcher rd = req.getRequestDispatcher("avaliar.jsp");
            rd.forward(req, resp);
        }
        catch(IllegalArgumentException e){
            req.setAttribute("erro", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("avaliar.jsp");
            rd.forward(req, resp);
        }
    }

}

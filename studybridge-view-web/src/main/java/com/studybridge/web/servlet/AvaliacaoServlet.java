package com.studybridge.web.servlet;

import com.studybridge.service.AvaliacaoService;
import com.studybridge.domain.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;


@WebServlet("/avaliar")
public class AvaliacaoServlet extends HttpServlet{
    private final AvaliacaoService avaliacaoService = new AvaliacaoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        double nota = Double.parseDouble(req.getParameter("nota"));
        String comentario = req.getParameter("comentario");
        String destinoJSP;
        if("monitor".equalsIgnoreCase(usuario.getTipoConta())){
            destinoJSP = "aulas-monitor.jsp";
        }else{
            destinoJSP = "aulas-estudante.jsp";
        }
        try{
            avaliacaoService.registrarAvaliacao(usuario, nota, comentario);
            double media = avaliacaoService.calcularMedia(usuario);

            req.setAttribute("media", media);
            RequestDispatcher rd = req.getRequestDispatcher(destinoJSP);
            rd.forward(req, resp);
        }catch(Exception e){
            req.setAttribute("erro", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher(destinoJSP);
            rd.forward(req, resp);
        }
    }
}
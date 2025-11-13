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
            /*RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/login.jsp"); Código será utilizado quando for possível criar usuários
            rd.forward(req, resp);
            return;*/
            usuario = new Usuario();
            usuario.setId(1);
            usuario.setTipoConta("estudante");
            session.setAttribute("usuario", usuario);
        }
        double nota = Double.parseDouble(req.getParameter("nota"));
        String comentario = req.getParameter("comentario");
        String destinoJSP;
        if("monitor".equalsIgnoreCase(usuario.getTipoConta())){
            destinoJSP = "/WEB-INF/views/aulas-monitor.jsp";
        }else{
            destinoJSP = "/WEB-INF/views/aulas-estudante.jsp";
        }
        try{
            avaliacaoService.registrarAvaliacao(usuario, nota, comentario);
            System.out.println("Avaliacao registrada com sucesso no banco de dados");
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String destinoJSP;
        if ("monitor".equalsIgnoreCase(usuario.getTipoConta())) {
            destinoJSP = "/WEB-INF/views/aulas-monitor.jsp";
        } else {
            destinoJSP = "/WEB-INF/views/aulas-estudante.jsp";
        }

        try {
            double media = avaliacaoService.calcularMedia(usuario);
            req.setAttribute("media", media);
        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
        }

        RequestDispatcher rd = req.getRequestDispatcher(destinoJSP);
        rd.forward(req, resp);
    }
}
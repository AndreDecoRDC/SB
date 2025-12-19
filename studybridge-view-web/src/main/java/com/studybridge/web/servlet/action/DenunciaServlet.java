package com.studybridge.web.servlet.action;

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
    private DenunciaService denunciaService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.denunciaService = new DenunciaService();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if(usuarioLogado == null) {
            resp.sendRedirect("/login.jsp");
            return;
        }

        try{
            int denuncianteId = usuarioLogado.getId();
            String denunciadoIdParam = req.getParameter("denunciadoId");
            if(denunciadoIdParam == null || denunciadoIdParam.isEmpty()){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do denunciado é obrigatório.");
                return;
            }
            int denunciadoId = Integer.parseInt(denunciadoIdParam);
            String motivo = req.getParameter("motivo");
            String descricao = req.getParameter("descricao");

            if(denuncianteId == denunciadoId){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro: Não é permitido denunciar a si mesmo.");
                return;
            }

            Usuario denunciado = new Usuario();
            denunciado.setId(denunciadoId);

            Denuncia.MotivoDenuncia motivoDenuncia = Denuncia.MotivoDenuncia.valueOf(motivo.trim().toUpperCase());
            denunciaService.registrarDenuncia(usuarioLogado, denunciado, motivoDenuncia, descricao);
            //req.getSession().setAttribute("denunciaSucesso", "true");
            //resp.sendRedirect("/denuncias");
            resp.sendRedirect("/denuncias?sucesso=true");
        }catch(IllegalArgumentException | SQLException e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao registrar denuncia");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if(usuarioLogado == null){
            resp.sendRedirect("/WEB-INF/views/monitor/login.jsp");
            return;
        }
        String destinoJSP;
        if("monitor".equalsIgnoreCase(usuarioLogado.getTipoConta())){
            destinoJSP = "/WEB-INF/views/monitor/aulas-monitor.jsp";
        }
        else{
            destinoJSP = "/WEB-INF/views/estudante/aulas-estudante.jsp";
        }
        try{
            List<Denuncia> denuncias = denunciaService.listarDenuncias();
            req.setAttribute("denuncias", denuncias);
        }catch(Exception e){
            req.setAttribute("erro", e.getMessage());
        }
        RequestDispatcher rd = req.getRequestDispatcher(destinoJSP);
        rd.forward(req, resp);
    }
}

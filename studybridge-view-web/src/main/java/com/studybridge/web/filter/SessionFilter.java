package com.studybridge.web.filter;

import com.studybridge.domain.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
protege as páginas privadas
somente usuarios logados podem acessar suas dashboards
e cada tipo de conta só acessa a sua própria dashboard
*/

@WebFilter("/*")
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        //paginas publicas = liberadas
        boolean publica =
                path.equals(req.getContextPath() + "/") ||
                        path.startsWith(req.getContextPath() + "/css/") ||
                        path.startsWith(req.getContextPath() + "/js/") ||
                        path.startsWith(req.getContextPath() + "/images/") ||
                        path.startsWith(req.getContextPath() + "/register") ||
                        path.startsWith(req.getContextPath() + "/cadastrar") ||
                        path.startsWith(req.getContextPath() + "/login") ||
                        path.startsWith(req.getContextPath() + "/autenticar") ||
                        path.startsWith(req.getContextPath() + "/verificar-login") ||
                        path.startsWith(req.getContextPath() + "/verificar-email") ||
                        path.startsWith(req.getContextPath() + "/confirmar");

        //se for pública, libera
        if (publica) {
            chain.doFilter(request, response);
            return;
        }

        //verifica sessao para o resto
        Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            //não está logado volta pro início
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        //verificacoes por tipo de conta
        if (path.contains("/estudante-dashboard") && !usuario.getTipoConta().equals("Estudante")) {
            res.sendError(403);
            return;
        }

        if (path.contains("/monitor-dashboard") && !usuario.getTipoConta().equals("Monitor")) {
            res.sendError(403);
            return;
        }

        if (path.contains("/admin-dashboard") && !usuario.getTipoConta().equals("Administrador")) {
            res.sendError(403);
            return;
        }

        //passou em todas as verificações
        chain.doFilter(request, response);
    }
}

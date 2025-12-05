package com.studybridge.web.filter;

import com.studybridge.domain.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
protege as paginas privadas
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
                        path.startsWith(req.getContextPath() + "/confirmar") ||
                        path.startsWith(req.getContextPath() + "/logout") ||
                        path.startsWith(req.getContextPath() + "/redefinir-senha") ||
                        path.startsWith(req.getContextPath() + "/redefinir-senha-action") ||
                        path.startsWith(req.getContextPath() + "/nova-senha") ||
                        path.startsWith(req.getContextPath() + "/nova-senha-action");

        if (publica) {

            if (!path.contains("/verificar-login")) {
                req.getSession().removeAttribute("emailLogin");
            }

            chain.doFilter(request, response);
            return;
        }

        Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");
        if (usuario == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String tipo = usuario.getTipoConta();

        if (path.contains("/estudante/") && !tipo.equals("Estudante")) {
            res.sendError(403);
            return;
        }

        if (path.contains("/monitor/") && !tipo.equals("Monitor")) {
            res.sendError(403);
            return;
        }

        if (path.contains("/admin/") && !tipo.equals("Administrador")) {
            res.sendError(403);
            return;
        }

        chain.doFilter(request, response);
    }
}

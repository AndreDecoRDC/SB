package com.studybridge.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
basicamente, pelo que eu estudei, um filter atua em todas as requisições do sistema independente da url
entao antes de qualquer servlet ser executado o filter atua.
*/
/*
filtro que impede o usuario de acessar paginas diretamente pelas urls
so deixa passar as rotas publicas (index, cadastro, confirmação etc)
tudo o que for fora do fluxo e redirecionado para o index
*/
@WebFilter("/*")
public class AcessoDiretoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        boolean publica =
                path.equals(req.getContextPath() + "/") ||
                        path.startsWith(req.getContextPath() + "/css/") ||
                        path.startsWith(req.getContextPath() + "/images/") ||
                        path.startsWith(req.getContextPath() + "/js/") ||

                        //fluxo de cadastro
                        path.startsWith(req.getContextPath() + "/register") ||
                        path.startsWith(req.getContextPath() + "/cadastrar") ||
                        //acessadas apenas internamente ou via link de email
                        path.startsWith(req.getContextPath() + "/verificar-email") ||
                        path.startsWith(req.getContextPath() + "/confirmar") ||

                        //fluxo de login
                        path.startsWith(req.getContextPath() + "/login") ||
                        path.startsWith(req.getContextPath() + "/autenticar") ||
                        path.startsWith(req.getContextPath() + "/verificar-login");

        if (publica) {
            chain.doFilter(request, response); //deixa passar
        } else {
            res.sendRedirect(req.getContextPath() + "/");
        }
    }
}
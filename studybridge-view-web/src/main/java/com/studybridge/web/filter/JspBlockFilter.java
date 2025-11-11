package com.studybridge.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
esse filtro age em todas as urls que terminam com .jsp
se alguem tentar abrir um jsp direto, o filtro manda 404
isso impede acesso direto aos arquivos de pagina
so os servlets podem abrir os jsp com forward
*/

@WebFilter("*.jsp")
public class JspBlockFilter implements Filter {

    /*
    o filtro *.jsp so roda em requisicoes diretas que chegam do navegador
    quando o jsp e chamado por um forward interno, o filtro nao e acionado
    assim o usuario nao pode abrir o jsp direto, mas o servlet pode usar normalmente
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;
        res.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}

package com.studybridge.web.servlet.page;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

/*
vou criar essa abstrata pra me adequar aos principios poo e evitar repeticao
*/
/*
basicamente, pelo web-inf/views/ ser  protegido pelo servidor, não vai deixar acessar diretamente.
O que eu faco nessa classe é, toda vez que um link para determinada pagina for acessada, vai chamar
sua respectiva pageServlet, que vai dar o dispatcher e "guiar" para a pagina desejada.
Assim o usuario nunca vai acessar a pagina diretamente pelo url.
*/
public abstract class PageServlet extends HttpServlet {
    protected void render(HttpServletRequest req, HttpServletResponse res, String view)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(req, res);
    }
}
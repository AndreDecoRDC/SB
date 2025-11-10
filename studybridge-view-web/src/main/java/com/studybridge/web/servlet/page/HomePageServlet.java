package com.studybridge.web.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
servlet que responde quando o usuario acessa a raiz do site (/studybridge/)
ele so mostra a pagina inicial index.jsp usando forward
isso existe pra controlar o acesso ao jsp que fica dentro do web-inf
*/
@WebServlet("")
public class HomePageServlet extends PageServlet {
<<<<<<<< HEAD:studybridge-view-web/src/main/java/com/studybridge/web/servlet/page/HomePageServlet.java

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

========
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
>>>>>>>> bf33329 (Aplicação de princípios de POO nas classes PageServlet e desenvolvimento de métodos úteis para o login em Usuário e UsuárioDAO #2):studybridge-view-web/src/main/java/com/studybridge/web/servlet/HomePageServlet.java
        render(req, res, "index");
    }
}

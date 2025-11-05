package com.studybridge.web.servlet;

import com.studybridge.service.CadastroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
esse servlet e chamado quando o usuario clica no link de confirmação do email
o link vem tipo: http://localhost:8080/SB/confirmar?token=XYZ
a funcao dele é pegar esse token, confirmar o email no banco e redirecionar pra outra pagina
*/
@WebServlet("/confirmar")
public class ConfirmarServlet extends HttpServlet {

    private final CadastroService cadastroService = new CadastroService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //pega o token que veio na url e, caso tente acessar diretamente e redirecionado
        String token = request.getParameter("token");
        if (token == null || token.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            //tenta confirmar o usuário com base no token
            String tipoConta = cadastroService.confirmarPorToken(token);

            //guarda o tipo da conta
            request.setAttribute("tipoConta", tipoConta);

            //encaminha pra pagina de confirmacao concluida
            request.getRequestDispatcher("/WEB-INF/views/email-confirmado.jsp").forward(request, response);

        } catch (Exception e) {
            //so pra caso de erro
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/erro-confirmacao.jsp").forward(request, response);

        }
    }
}
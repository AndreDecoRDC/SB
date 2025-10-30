package com.studybridge.web.servlet;

import com.studybridge.service.CadastroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
servlet basicamente é responsavel por receber os dados do formulario de cadastro do register.jsp
e chamar o CadastroService para fazer o registro e envio do email de verificação.
*/
@WebServlet("/cadastrar")
public class CadastroServlet extends HttpServlet {

    private final CadastroService cadastroService = new CadastroService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //pega os parametros do formulário
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmar = request.getParameter("confirmarSenha");
        String tipo = request.getParameter("tipoConta");

        try {
            //chama o service para cadastrar e enviar o email
            cadastroService.cadastrar(email, senha, confirmar, tipo);

            //se tudo der certo, nao vai lancar a excecao e redireciona para página de instrução
            response.sendRedirect("verificar-email.jsp");

        } catch (Exception e) {
            //se der errado, retorna o erro e volta para a página de cadastro
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}

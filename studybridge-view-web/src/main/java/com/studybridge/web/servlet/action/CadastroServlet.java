package com.studybridge.web.servlet.action;

import com.studybridge.service.CadastroService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

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
            response.sendRedirect(request.getContextPath() + "/verificar-email?email=" + URLEncoder.encode(email, "UTF-8"));

            /*
            usa sendRedirect ao inves de forward porque o redirect manda uma resposta 302 pro navegador
            ai o navegador faz uma nova requisicao get pra outra pagina (verificar-email)
            isso muda a url la em cima e evita o problema de reenviar o formulario se o usuario apertar f5
            ja o forward so troca a pagina dentro do servidor, nao muda a url e mantem o mesmo request
            ele e bom pra mostrar erros, mas ruim pra depois de um cadastro bem sucedido
            entao aqui usa redirect pra aplicar o padrao post redirect get (evita duplicar o cadastro)
            */


        } catch (Exception e) {
            //se der errado, retorna o erro e volta para a página de cadastro
            request.setAttribute("email", email);
            request.setAttribute("senha", senha);
            request.setAttribute("confirmarSenha", confirmar);
            request.setAttribute("tipoConta", tipo);

            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}

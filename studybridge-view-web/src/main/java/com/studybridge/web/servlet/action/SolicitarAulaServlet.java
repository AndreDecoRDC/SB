package com.studybridge.web.servlet.action;

import com.studybridge.domain.model.Usuario;
import com.studybridge.service.AulaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/estudante/solicitarAula")
public class SolicitarAulaServlet extends HttpServlet {

    private final AulaService aulaService = new AulaService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String disciplina = request.getParameter("disciplina");
        String data_aula = request.getParameter("dataHora");
        String mensagem = request.getParameter("mensagem");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
        String emailAluno = usuario.getEmail();


        try {
            aulaService.solicitar(disciplina, data_aula, mensagem, emailAluno);
        } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("erro", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/estudante/solicitar.jsp").forward(request, response);
        }
        response.sendRedirect(request.getContextPath() + "/estudante/dashboard");
    }
}


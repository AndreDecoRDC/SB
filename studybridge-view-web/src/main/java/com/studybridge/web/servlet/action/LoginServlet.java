package com.studybridge.web.servlet.action;

import com.studybridge.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/autenticar")
public class LoginServlet extends HttpServlet {

    private final LoginService loginService = new LoginService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {

            loginService.iniciarLogin(email, senha);
            request.getSession().setAttribute("emailLogin", email);

            response.sendRedirect(request.getContextPath() + "/verificar-login");

        } catch (Exception e) {

            request.setAttribute("email", email);
            request.setAttribute("senha", senha);

            request.setAttribute("erro", e.getMessage());

            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}

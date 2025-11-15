package com.studybridge.web.servlet.page;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/verificar-login")
public class VerificarLoginPageServlet extends PageServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String email = (String) req.getSession().getAttribute("emailLogin");

        if (email == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        render(req, res, "verificar-login");
    }
}
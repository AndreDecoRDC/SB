package com.studybridge.web.servlet.page;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;

@WebServlet("/monitor/aulas-monitor")
public class AulasMonitorPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/monitor/aulas-monitor.jsp");
        rd.forward(req, resp);
    }
}

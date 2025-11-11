package com.studybridge.web.servlet;

import com.studybridge.domain.model.Horario;
import com.studybridge.domain.model.Usuario;
import com.studybridge.service.GerenciarHorarioService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/horarios")
public class GerenciarHorarioServlet extends HttpServlet {

    private final GerenciarHorarioService service = new GerenciarHorarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            
            int monitorId = 1; // teste temporário
            
            //Código definitivo (irei colocar ele após o sistema de login estar completo):
            /* 
            HttpSession session = req.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            

            if (usuario == null) {
                resp.sendRedirect(req.getContextPath() + "/index");
                return;
            }

            int monitorId = usuario.getId();
            */
            List<Horario> horarios = service.listarHorario(monitorId);

            req.setAttribute("horarios", horarios);
            req.getRequestDispatcher("/WEB-INF/views/horarios.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao listar horários: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/horarios.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            
            int monitorId = 1; //temporário
            
            /*
            HttpSession session = req.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if (usuario == null) {
                resp.sendRedirect(req.getContextPath() + "/index");
                return;
            }

            int monitorId = usuario.getId();
            */

            String diaSemana = req.getParameter("diaSemana");
            LocalTime inicio = LocalTime.parse(req.getParameter("horaInicio"));
            LocalTime termino = LocalTime.parse(req.getParameter("horaTermino"));
            int duracao = Integer.parseInt(req.getParameter("duracaoMedia"));

            Horario novo = new Horario();
            novo.setMonitorId(monitorId);
            novo.setDiaSemana(diaSemana);
            novo.setHoraInicio(inicio);
            novo.setHoraTermino(termino);
            novo.setDuracaoMedia(duracao);

            List<Horario> existentes = service.listarHorario(monitorId);

            boolean conflito = existentes.stream().anyMatch(h
                    -> h.getDiaSemana().equals(diaSemana)
                    && ((inicio.isBefore(h.getHoraTermino()) && termino.isAfter(h.getHoraInicio()))
                    )
            );

            if (conflito) {
                req.setAttribute("erro", "Erro. Este horário entra em conflito com outro já cadastrado.");
                req.setAttribute("horarios", existentes);
                req.getRequestDispatcher("/WEB-INF/views/horarios.jsp").forward(req, resp);
                return;
            }


            String mensagem = service.adicionarHorario(novo);

            if (mensagem.startsWith("Erro")) {
                req.setAttribute("erro", mensagem);
            } else {
                req.setAttribute("sucesso", mensagem);
            }
            
            List<Horario> horarios = service.listarHorario(monitorId);
            req.setAttribute("horarios", horarios);
            req.getRequestDispatcher("/WEB-INF/views/horarios.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/erro-horario.jsp").forward(req, resp);
        }
    }
}

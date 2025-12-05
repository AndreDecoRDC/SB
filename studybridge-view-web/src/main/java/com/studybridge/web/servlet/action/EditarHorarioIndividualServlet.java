package com.studybridge.web.servlet.action;

import com.studybridge.domain.model.Horario;
import com.studybridge.service.GerenciarHorarioService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@WebServlet("/editar-horario")
public class EditarHorarioIndividualServlet extends HttpServlet {

    private final GerenciarHorarioService service = new GerenciarHorarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Horario horario = service.getHorarioById(id);

            if (horario == null) {
                req.setAttribute("erro", "Horário não encontrado.");
                req.getRequestDispatcher("/WEB-INF/views/editar-horarios.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("horario", horario);
            req.getRequestDispatcher("/WEB-INF/views/editar-horario.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("erro", "Erro ao carregar horário: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/editar-horarios.jsp").forward(req, resp);
        }
    }

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
        int id = Integer.parseInt(req.getParameter("id"));
        String diaSemana = req.getParameter("diaSemana");
        LocalTime inicio = LocalTime.parse(req.getParameter("horaInicio"));
        LocalTime termino = LocalTime.parse(req.getParameter("horaTermino"));
        int duracao = (int) java.time.Duration.between(inicio, termino).toMinutes();

        Horario horario = service.getHorarioById(id);
        horario.setDiaSemana(diaSemana);
        horario.setHoraInicio(inicio);
        horario.setHoraTermino(termino);
        horario.setDuracaoMedia(duracao);

        if (inicio.equals(termino) || inicio.isAfter(termino)) {
            req.setAttribute("erro", "Erro. Horário de início deve ser antes de término.");
            req.setAttribute("horario", horario);
            req.getRequestDispatcher("/WEB-INF/views/editar-horario.jsp").forward(req, resp);
            return;
        }

        java.time.Duration tempo = java.time.Duration.between(inicio, termino);
        if (tempo.toMinutes() < 15) {
            req.setAttribute("erro", "Erro. Seu tempo de aula deve ter duração de pelo menos 15 minutos.");
            req.setAttribute("horario", horario);
            req.getRequestDispatcher("/WEB-INF/views/editar-horario.jsp").forward(req, resp);
            return;
        }
        
        boolean duplicado = service.listarHorario(horario.getMonitorId()).stream()
                .anyMatch(h -> !Objects.equals(h.getId(), id)
                        && h.getDiaSemana().equals(diaSemana)
                        && h.getHoraInicio().equals(inicio)
                        && h.getHoraTermino().equals(termino));

        if (duplicado) {
            req.setAttribute("erro", "Erro. Já existe um horário cadastrado nesse dia/horário.");
            req.setAttribute("horario", horario);
            req.getRequestDispatcher("/WEB-INF/views/editar-horario.jsp").forward(req, resp);
            return;
        }
        
       
        List<Horario> existentes = service.listarHorario(horario.getMonitorId());
        boolean conflito = existentes.stream().anyMatch(h
                -> !Objects.equals(h.getId(), id)
                && 
                h.getDiaSemana().equals(diaSemana)
                && ((inicio.isBefore(h.getHoraTermino()) && termino.isAfter(h.getHoraInicio())))
        );

        if (conflito) {
            req.setAttribute("erro", "Erro. Este horário entra em conflito com outro já cadastrado.");
            req.setAttribute("horario", horario);
            req.getRequestDispatcher("/WEB-INF/views/editar-horario.jsp").forward(req, resp);
            return;
        }


        String mensagem = service.editarHorario(horario);

        resp.sendRedirect(req.getContextPath() + "/editar-horarios?sucesso=" + java.net.URLEncoder.encode(mensagem, "UTF-8"));

    } catch (Exception e) {
        req.setAttribute("erro", "Erro ao atualizar: " + e.getMessage());
        req.getRequestDispatcher("/WEB-INF/views/editar-horario.jsp").forward(req, resp);
    }
}

}

package com.studybridge.web.servlet.action;

import com.studybridge.domain.model.Notificacao;
import com.studybridge.domain.model.Usuario;
import com.studybridge.service.NotificacaoService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/notificacoes/nao-lidas")
public class ListarNotificacoesServlet extends HttpServlet {

    private final NotificacaoService notificacaoService = new NotificacaoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");

        if (usuario == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        resp.setContentType("application/json;charset=UTF-8");

        try {
            List<Notificacao> lista =
                    notificacaoService.listarNaoLidas(usuario.getEmail());

            StringBuilder json = new StringBuilder();
            json.append("[");

            for (int i = 0; i < lista.size(); i++) {
                Notificacao n = lista.get(i);

                json.append("{")
                        .append("\"id\":").append(n.getId()).append(",")
                        .append("\"mensagem\":\"").append(escape(n.getMensagem())).append("\",")
                        .append("\"link\":\"").append(n.getLink()).append("\"")
                        .append("}");

                if (i < lista.size() - 1) json.append(",");
            }

            json.append("]");
            resp.getWriter().write(json.toString());

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("[]");
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

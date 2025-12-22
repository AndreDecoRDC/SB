package com.studybridge.service;

import com.studybridge.dao.NotificacaoDAO;
import com.studybridge.domain.model.Notificacao;
import java.sql.SQLException;
import java.util.List;

public class NotificacaoService {

    private final NotificacaoDAO dao = new NotificacaoDAO();

    public void criar(Notificacao n) throws Exception {
        if (n.getEmailDestinatario() == null || n.getEmailDestinatario().isBlank())
            throw new Exception("Destinatário inválido");

        if (n.getTipo() == null || n.getTipo().isBlank())
            throw new Exception("Tipo inválido");

        if (n.getMensagem() == null || n.getMensagem().isBlank())
            throw new Exception("Mensagem inválida");

        try {
            dao.inserir(n);
        } catch (SQLException e) {
            throw new Exception("Erro ao criar notificação", e);
        }
    }

    public List<Notificacao> listarNaoLidas(String email) throws Exception {
        try {
            return dao.listarNaoLidas(email);
        } catch (SQLException e) {
            throw new Exception("Erro ao listar notificações", e);
        }
    }

    public void marcarComoLida(int id, String emailDestinatario) throws Exception {
        try {
            dao.marcarComoLida(id, emailDestinatario);
        } catch (SQLException e) {
            throw new Exception("Erro ao marcar notificação como lida", e);
        }
    }
}

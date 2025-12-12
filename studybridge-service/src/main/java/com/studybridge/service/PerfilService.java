package com.studybridge.service;

import com.studybridge.dao.EstudanteDAO;
import com.studybridge.dao.MonitorDAO;
import com.studybridge.domain.model.Estudante;
import com.studybridge.domain.model.Monitor;

public class PerfilService {

    private final EstudanteDAO estudanteDAO = new EstudanteDAO();
    private final MonitorDAO monitorDAO = new MonitorDAO();


    public Estudante carregarPerfilEstudante(int usuarioId) throws Exception {
        Estudante estudante = estudanteDAO.buscarPorUsuarioId(usuarioId);

        if (estudante == null) {
            throw new Exception("Perfil de estudante não encontrado.");
        }

        return estudante;
    }

    public void atualizarPerfilEstudante(int usuarioId, String nome, String telefone, String curso, String anoTurma, String campus, String descricao) throws Exception {

        Estudante estudante = new Estudante();
        estudante.setNome(nome);
        estudante.setTelefone(telefone);
        estudante.setCurso(curso);
        estudante.setAnoTurma(anoTurma);
        estudante.setCampus(campus);
        estudante.setDescricao(descricao);

        estudanteDAO.atualizarPerfil(usuarioId, estudante);
    }

    public Monitor carregarPerfilMonitor(int usuarioId) throws Exception {
        Monitor monitor = monitorDAO.buscarPorUsuarioId(usuarioId);

        if (monitor == null) {
            throw new Exception("Perfil de monitor não encontrado.");
        }

        return monitor;
    }

    public void atualizarPerfilMonitor(int usuarioId, String nome, String telefone, String disciplina, String campus, String descricao) throws Exception {

        Monitor monitor = new Monitor();
        monitor.setNome(nome);
        monitor.setTelefone(telefone);
        monitor.setDisciplina(disciplina);
        monitor.setCampus(campus);
        monitor.setDescricao(descricao);

        monitorDAO.atualizarPerfil(usuarioId, monitor);
    }
}

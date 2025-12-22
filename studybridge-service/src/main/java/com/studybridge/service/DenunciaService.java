package com.studybridge.service;

import com.studybridge.dao.DenunciaDAO;
import com.studybridge.domain.model.Denuncia;
import com.studybridge.domain.model.Usuario;
import java.util.List;
import java.sql.SQLException;

public class DenunciaService {
    private final DenunciaDAO denunciaDAO;
    public DenunciaService() {
        this.denunciaDAO = new DenunciaDAO();
    }
    public DenunciaService(DenunciaDAO denunciaDAO) {
        this.denunciaDAO = denunciaDAO;
    }

    public void registrarDenuncia(Usuario denunciante, Usuario denunciado, Denuncia.MotivoDenuncia motivoDenuncia, String descricao) throws SQLException {
        if(denunciante == null) {
            throw new IllegalArgumentException();
        }
        if(denunciante.getId().equals(denunciado.getId())) {
            throw new IllegalArgumentException();
        }
        if(motivoDenuncia == null) {
            throw new IllegalArgumentException();
        }

        Denuncia denuncia = new Denuncia(0, denunciante, denunciado, motivoDenuncia, descricao);
        denunciaDAO.salvarDenuncia(denuncia);
    }
    public List<Denuncia> listarDenuncias() throws SQLException {
        return denunciaDAO.buscarDenuncias();
    }
    public List<Denuncia> listarPorDenunciado(Usuario usuario) throws SQLException {
        if(usuario == null) {
            throw new IllegalArgumentException();
        }
        return denunciaDAO.buscarPorDenunciado(usuario);
    }
    public List<Denuncia> listarPorDenunciante(Usuario usuario) throws SQLException {
        if(usuario == null) {
            throw new IllegalArgumentException();
        }
        return denunciaDAO.buscarPorDenunciante(usuario);
    }
    public void atualizarStatus(int idDenuncia, Denuncia.StatusDenuncia status) throws SQLException {
        if(idDenuncia <= 0) {
            throw new IllegalArgumentException();
        }
        if(status == null) {
            throw new IllegalArgumentException();
        }
        denunciaDAO.atualizarStatus(idDenuncia, status);
    }
    public List<Denuncia> obterDenunciasPendentes() throws SQLException{
        return denunciaDAO.buscarDenunciasPendentes();
    }
}

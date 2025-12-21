package com.studybridge.dao;

import com.studybridge.domain.model.Denuncia;
import com.studybridge.domain.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DenunciaDAO {
    public void salvarDenuncia(Denuncia denuncia) throws SQLException {
        String sql = "INSERT INTO denuncias (" + " usuario_denunciante_id, usuario_denunciado_id, motivo, descricao, status" + ") VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, denuncia.getUsuarioDenunciante().getId());
            stmt.setInt(2, denuncia.getUsuarioDenunciado().getId());
            stmt.setString(3, denuncia.getMotivoDenuncia().name());
            stmt.setString(4, denuncia.getDescricao());
            stmt.setString(5, denuncia.getStatus().name());

            stmt.executeUpdate();
        }
    }
    public List<Denuncia> buscarDenuncias() throws SQLException {
        List<Denuncia> denuncias = new ArrayList<>();
        String sql = "SELECT * FROM denuncias";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            UsuarioDAO usuarioDAO = new UsuarioDAO();

            while (rs.next()) {
                Usuario denunciante = usuarioDAO.buscarPorId(rs.getInt("usuario_denunciante_id"));
                Usuario denunciado = usuarioDAO.buscarPorId(rs.getInt("usuario_denunciado_id"));
                String motivoStr = rs.getString("motivo");
                Denuncia.MotivoDenuncia motivo = Denuncia.MotivoDenuncia.OUTRO;
                if (motivoStr != null) {
                    try {
                        motivo = Denuncia.MotivoDenuncia.valueOf(motivoStr.toUpperCase().trim());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Motivo inválido no banco: " + motivoStr);
                    }
                }
                Denuncia d = new Denuncia(
                        rs.getInt("id"),
                        denunciante,
                        denunciado,
                        motivo,
                        rs.getString("descricao")
                );
                String statusStr = rs.getString("status");
                if (statusStr != null) {
                    try {
                        d.setStatus(Denuncia.StatusDenuncia.valueOf(statusStr.toUpperCase().trim()));
                    } catch (IllegalArgumentException e) {
                        d.setStatus(Denuncia.StatusDenuncia.PENDENTE);
                    }
                }

                denuncias.add(d);
            }
        }
        return denuncias;
    }
    public void atualizarStatus(int id, Denuncia.StatusDenuncia status) throws SQLException {
        String sql = "UPDATE denuncias SET status = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
    public List<Denuncia> buscarPorDenunciado(Usuario usuario) throws SQLException {
        List<Denuncia> denuncias = new ArrayList<>();
        if(usuario == null) {
            return denuncias;
        }
        String sql = "SELECT * FROM denuncias WHERE usuario_denunciado_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario denunciante = new Usuario();
                denunciante.setId(rs.getInt("usuario_denunciante_id"));

                Denuncia denuncia = new Denuncia(rs.getInt("id"),denunciante, usuario, Denuncia.MotivoDenuncia.valueOf(rs.getString("motivo")), rs.getString("descricao"));
                denuncia.setStatus(Denuncia.StatusDenuncia.valueOf(rs.getString("status")));
                denuncias.add(denuncia);
            }
            rs.close();
        }
        return denuncias;
    }
    public List<Denuncia> buscarPorDenunciante(Usuario usuario) throws SQLException {
        List<Denuncia> denuncias = new ArrayList<>();
        if(usuario == null) {
            return denuncias;
        }
        String sql = "SELECT * FROM denuncias WHERE usuario_denunciante_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario denunciante = new Usuario();
                Usuario denunciado = new Usuario();
                denunciado.setId(rs.getInt("usuario_denunciado_id"));

                Denuncia denuncia = new Denuncia(rs.getInt("id"),usuario, denunciado, Denuncia.MotivoDenuncia.valueOf(rs.getString("motivo")), rs.getString("descricao"));
                denuncia.setStatus(Denuncia.StatusDenuncia.valueOf(rs.getString("status")));
                denuncias.add(denuncia);
            }
            rs.close();
        }
        return denuncias;
    }
    public List<Denuncia> buscarDenunciasPendentes() throws SQLException{
        String sql = "SELECT d.id, d.usuario_denunciante_id, d.usuario_denunciado_id, d.motivo, d.descricao, d.status " +
                "FROM denuncias d " +
                "WHERE d.status IN ('PENDENTE', 'EM_ANALISE')";
        List<Denuncia> denuncias = new ArrayList<>();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                Usuario denunciante = usuarioDAO.buscarPorId(rs.getInt("usuario_denunciante_id"));
                Usuario denunciado = usuarioDAO.buscarPorId(rs.getInt("usuario_denunciado_id"));

                Denuncia.MotivoDenuncia motivoDenuncia = Denuncia.MotivoDenuncia .valueOf(rs.getString("motivo").toUpperCase());

                Denuncia denuncia = new Denuncia(rs.getInt("id"), denunciante, denunciado, motivoDenuncia, rs.getString("descricao"));
                denuncia.setStatus(Denuncia.StatusDenuncia.valueOf(rs.getString("status").toUpperCase()));
                denuncias.add(denuncia);
            }
        }
        return denuncias;
    }
}

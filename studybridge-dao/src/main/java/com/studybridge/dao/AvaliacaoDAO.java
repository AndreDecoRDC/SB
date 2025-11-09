package com.studybridge.dao;

import com.studybridge.domain.model.Avaliacao;
import com.studybridge.domain.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AvaliacaoDAO{

    public void salvarOuAtualizar(Avaliacao avaliacao) throws SQLException{
        String sql = "insert into avaliacoes(usuario_id, nota, comentario) values(?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            for(int i = 0; i < avaliacao.getNotas().size(); i++){
                stmt.setInt(1, avaliacao.getUsuarioAvaliado().getId());
                stmt.setDouble(2, avaliacao.getNotas().get(i));
                stmt.setString(3, avaliacao.getComentarios().get(i));
                stmt.executeUpdate();
            }
        }
    }
    public List<Avaliacao> buscarPorUsuario(Usuario usuario) throws SQLException{
        List<Avaliacao> avaliacoes = new ArrayList<>();
        if(usuario == null){
            return avaliacoes;
        }

        String sql= "Select nota, comentario from avaliacoes where usuario_id=?";

        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Avaliacao avaliacao = new Avaliacao(usuario);
                avaliacao.getNotas().add(rs.getDouble("nota"));
                avaliacao.getComentarios().add(rs.getString("comentario"));
                avaliacoes.add(avaliacao);
            }
            rs.close();
        }
        return avaliacoes;
    }
}

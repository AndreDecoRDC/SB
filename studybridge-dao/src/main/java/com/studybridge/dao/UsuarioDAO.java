package com.studybridge.dao;

import com.studybridge.domain.model.Usuario;
import java.sql.*;

import com.studybridge.domain.model.UsuarioAdmin;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioDAO {

    public boolean existePorEmail(String email) throws SQLException {
        //esse comando SQL procura se já tem alguem com o mesmo email
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            //substitui o ? da query pelo valor real do email
            ps.setString(1, email);

            //executa o comando e guarda em um result set que é tipo uma tabela temporária
            ResultSet rs = ps.executeQuery();

            //se rs.next() for verdadeiro, quer dizer que achou alguém com esse email
            boolean existe = rs.next();

            rs.close();
            return existe;
        }
    }

    public void inserir(Usuario usuario) throws SQLException {
        //comando do SQL pra inserir
        String sql = "INSERT INTO usuarios (email, senha_hash, tipo_conta, verificado, token_verificacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { //o segundo parametro pede pro JDBC devolver o id gerado

            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenhaHash());
            ps.setString(3, usuario.getTipoConta());
            ps.setBoolean(4, usuario.isVerificado());
            ps.setString(5, usuario.getTokenVerificacao());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys(); //obtém o id gerado do banco
            if (rs.next()) {
                usuario.setId(rs.getInt(1)); //guarda esse id dentro do objeto, pq cada objeto usuario tem que ter seu id para localizacao depois
            }
            rs.close();
        }
    }

    /*
    estou planejando fazer do seguinte jeito: Como cada usuário vai ter o seu token específico. Quando a pessoa clicar
    em um link de confirmação referente ao token dela, a conta referente ao token vai se tornar verificada com as duas funções abaixo
     */

    public Usuario buscarPorToken(String token) throws SQLException {

        String sql = "SELECT * FROM usuarios WHERE token_verificacao = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                rs.close();
                return null;
            }

            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setEmail(rs.getString("email"));
            u.setSenhaHash(rs.getString("senha_hash"));
            u.setTipoConta(rs.getString("tipo_conta"));
            u.setVerificado(rs.getBoolean("verificado"));
            u.setTokenVerificacao(rs.getString("token_verificacao"));

            rs.close();
            return u;
        }
    }

    public void confirmarEmail(String token) throws SQLException {

        String sql = """
            UPDATE usuarios
            SET verificado = 1, token_verificacao = NULL
            WHERE token_verificacao = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.executeUpdate();
        }
    }

    //busca o usuario com base no email, usado no login pra validar senha
    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            //se nao achou ninguem com esse email retorna null
            if (!rs.next()) {
                rs.close();
                return null;
            }

            //cria o objeto usuario com as informacoes do banco
            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setEmail(rs.getString("email"));
            u.setSenhaHash(rs.getString("senha_hash"));
            u.setTipoConta(rs.getString("tipo_conta"));
            u.setVerificado(rs.getBoolean("verificado"));
            u.setTokenVerificacao(rs.getString("token_verificacao"));
            u.setCodigo2FA(rs.getString("codigo_2fa"));

            //converte o campo datetime pra LocalDateTime do Java
            Timestamp ts = rs.getTimestamp("expiracao_2fa");
            if (ts != null) {
                u.setExpiracao2FA(ts.toLocalDateTime());
            }

            rs.close();
            return u;
        }
    }

    //salva o codigo 2FA e define a validade de 5 minutos no banco
    public void salvarCodigo2FA(int idUsuario, String codigo) throws SQLException {
        String sql = "UPDATE usuarios SET codigo_2fa = ?, expiracao_2fa = NOW() + INTERVAL 5 MINUTE WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            //define o codigo gerado e o id do usuario que está tentando logar
            ps.setString(1, codigo);
            ps.setInt(2, idUsuario);

            //executa a atualizacao
            ps.executeUpdate();
        }
    }

    //verifica se o codigo 2FA informado esta correto e ainda nao expirou
    public boolean validarCodigo2FA(int idUsuario, String codigo) throws SQLException {
        String sql = "SELECT 1 FROM usuarios WHERE id = ? AND codigo_2fa = ? AND expiracao_2fa > NOW()";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setString(2, codigo);

            ResultSet rs = ps.executeQuery();

            //se retornar algo, quer dizer que o codigo é valido e nao expirou
            boolean valido = rs.next();

            rs.close();
            return valido;
        }
    }

    //limpa o codigo e a expiracao depois que o usuario termina o login com sucesso
    public void limparCodigo2FA(int idUsuario) throws SQLException {
        String sql = "UPDATE usuarios SET codigo_2fa = NULL, expiracao_2fa = NULL WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            //define o id do usuario e executa o update
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
    }

    //salva um token unico para redefinir senha e define validade
    public void salvarTokenRedefinicao(int id, String token) throws SQLException {

        String sql = "UPDATE usuarios SET token_redefinicao = ?, expiracao_redefinicao = NOW() + INTERVAL 30 MINUTE WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.setInt(2, id);

            //executa o update no banco
            ps.executeUpdate();
        }
    }

    //busca o usuario a partir do token de redefinição, usado para validar o link
    public Usuario buscarPorTokenRedefinicao(String token) throws SQLException {

        String sql = "SELECT * FROM usuarios WHERE token_redefinicao = ? AND expiracao_redefinicao > NOW()";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setEmail(rs.getString("email"));
            u.setSenhaHash(rs.getString("senha_hash"));
            u.setTipoConta(rs.getString("tipo_conta"));

            rs.close();
            return u;
        }
    }

    //atualiza a senha hash do usuario depois que ele confirma a redefinição
    public void atualizarSenhaHash(int id, String novaHash) throws SQLException {
        String sql = "UPDATE usuarios SET senha_hash = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novaHash);
            ps.setInt(2, id);


            ps.executeUpdate();
        }
    }

    //limpa o token de redefinicao e a data de expiração depois de usar
    public void limparTokenRedefinicao(int id) throws SQLException {

        String sql = "UPDATE usuarios SET token_redefinicao = NULL, expiracao_redefinicao = NULL WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }

    private int contar(String sql) throws SQLException {        
        try (Connection conn = ConnectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public int contarTotalAtivos() throws SQLException {
        String sql = "SELECT COUNT(id) FROM usuarios WHERE verificado = 1 AND tipo_conta != 'Administrador'";
        return contar(sql);
    }

    public int contarEstudantesAtivos() throws SQLException {
        String sql = "SELECT COUNT(id) FROM usuarios WHERE tipo_conta = 'Estudante' AND verificado = 1";
        return contar(sql);
    }

    public int contarMonitoresAtivos() throws SQLException {
        String sql = "SELECT COUNT(id) FROM usuarios WHERE tipo_conta = 'Monitor' AND verificado = 1";
        return contar(sql);
    }

    public int contarUsuariosInativos() throws SQLException {
        String sql = "SELECT COUNT(id) FROM usuarios WHERE verificado = 0 AND tipo_conta != 'Administrador'";
        return contar(sql);
    }


    public List<UsuarioAdmin> buscarUsuariosAdmin(
            String nome,
            String tipo,
            String ordenar
    ) throws SQLException {

        List<UsuarioAdmin> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("""
        SELECT
            u.id,
            u.email,
            u.tipo_conta,
            u.verificado,
        
            COALESCE(e.nome, m.nome) AS nome,
            COALESCE(e.telefone, m.telefone) AS telefone,
            e.curso,
            m.disciplina,
            COALESCE(e.campus, m.campus) AS campus,
            COALESCE(e.descricao, m.descricao) AS descricao,
        
            COALESCE(
                (
                    SELECT AVG(av.nota)
                    FROM avaliacoes av
                    WHERE av.usuario_id = u.id
                ),
                0
            ) AS media_avaliacao
        
        FROM usuarios u
        LEFT JOIN estudantes e ON e.usuario_id = u.id
        LEFT JOIN monitores m ON m.usuario_id = u.id
        WHERE u.tipo_conta != 'Administrador'
    """);

        if (nome != null && !nome.isBlank()) {
            sql.append(" AND COALESCE(e.nome, m.nome) LIKE ? ");
        }

        if (tipo != null && !tipo.isBlank()) {
            sql.append(" AND u.tipo_conta = ? ");
        }

        if ("avaliacao".equals(ordenar)) {
            sql.append(" ORDER BY media_avaliacao DESC ");
        } else {
            sql.append(" ORDER BY nome ASC ");
        }

        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;

            if (nome != null && !nome.isBlank()) {
                ps.setString(index++, "%" + nome + "%");
            }

            if (tipo != null && !tipo.isBlank()) {
                ps.setString(index++, tipo);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UsuarioAdmin ua = new UsuarioAdmin();

                ua.setId(rs.getInt("id"));
                ua.setEmail(rs.getString("email"));
                ua.setTipoConta(rs.getString("tipo_conta"));
                ua.setVerificado(rs.getBoolean("verificado"));

                ua.setNome(rs.getString("nome"));
                ua.setTelefone(rs.getString("telefone"));
                ua.setCurso(rs.getString("curso"));
                ua.setDisciplina(rs.getString("disciplina"));
                ua.setCampus(rs.getString("campus"));
                ua.setDescricao(rs.getString("descricao"));

                ua.setMediaAvaliacao(rs.getDouble("media_avaliacao"));

                lista.add(ua);

            }
        }

        return lista;
    }

}

package com.studybridge.service;

import com.studybridge.domain.model.Usuario;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    /*
    essa classe envia emails de verificacao para o usuario depois do cadastro.
    vou usar a biblioteca Jakarta Mail para conectar ao servidor SMTP do Gmail
    e mandar um email HTML com o link de confirmacao.
    o metodo enviarVerificacao() recebe um objeto Usuario (com email e token)
    e gera o link no formato http://localhost:8080/SB/confirmar?token=XYZ
    esse link vai ser enviado para o email do usuario, e quando clicar ele confirma a conta.
     */

    //Email e senha da conta do StudyBridge
    private static final String REMETENTE = "studybridgecft@gmail.com";
    private static final String SENHA = "eqtu ylhn qloi iddx ";

    //configuraçoes do servidor SMTP (Gmail)
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    public void enviarVerificacao(Usuario usuario) throws MessagingException, java.io.UnsupportedEncodingException {

        String link = "http://localhost:8080/studybridge/confirmar?token=" + usuario.getTokenVerificacao();

        //configuracoes da conexão SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");              //precisa autenticar
        props.put("mail.smtp.starttls.enable", "true");   //usa criptografia tls
        props.put("mail.smtp.host", SMTP_HOST);           //servidor smtp
        props.put("mail.smtp.port", SMTP_PORT);           //porta 587 tls

        //cria a sessão autenticada com usuario e senha
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMETENTE, SENHA);
            }
        });

        //cria a mensagem de email
        Message mensagem = new MimeMessage(session);

        //define o remetente com nome
        mensagem.setFrom(new InternetAddress(REMETENTE, "StudyBridge"));

        //define o destinatario
        mensagem.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(usuario.getEmail()));

        //define o assunto
        mensagem.setSubject("Confirmação de email - StudyBridge");

        //corpo html do email
        String corpo = """
            <html>
              <body style="font-family: Arial, sans-serif;">
                <h2>Confirme seu email no StudyBridge</h2>
                <p>Olá, clique no link abaixo para confirmar seu cadastro:</p>
                <p><a href="%s" target="_blank">%s</a></p>
                <hr>
                <p>Se você não criou uma conta no StudyBridge, ignore este e-mail.</p>
              </body>
            </html>
            """.formatted(link, link);

        //define o conteudo da mensagem como html
        mensagem.setContent(corpo, "text/html; charset=utf-8");

        //manda o email
        Transport.send(mensagem);

        System.out.println("Email de verificação enviado para " + usuario.getEmail());
    }
}

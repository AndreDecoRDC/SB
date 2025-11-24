package com.studybridge.service;

import com.studybridge.domain.model.Usuario;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

/*
reestruturei a classe pra evitar redundancia e seguir os principios de poo
*/

public class EmailService {

    private static final String REMETENTE = "studybridgecft@gmail.com";
    private static final String SENHA = "eqtu ylhn qloi iddx ";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    private Session criarSessao() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMETENTE, SENHA);
            }
        });
    }

    public void enviarVerificacao(Usuario usuario) throws MessagingException, java.io.UnsupportedEncodingException {
        String link = "http://localhost:8080/studybridge/confirmar?token=" + usuario.getTokenVerificacao();
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

        enviarEmail(usuario.getEmail(), "Confirmação de email - StudyBridge", corpo);
    }

    public void enviarCodigo2FA(String emailDestino, String codigo)
            throws MessagingException, java.io.UnsupportedEncodingException {

        String corpo = """
            <html>
              <body style="font-family: Arial, sans-serif;">
                <h2>Seu código de verificação - StudyBridge</h2>
                <p>Olá!</p>
                <p>Seu código de verificação é:</p>
                <h3 style="color:#3366cc;">%s</h3>
                <p>Ele expira em 5 minutos.</p>
                <hr>
                <p>Se você não tentou fazer login, ignore este e-mail.</p>
              </body>
            </html>
            """.formatted(codigo);

        enviarEmail(emailDestino, "Código de verificação - StudyBridge", corpo);
    }

    public void enviarLinkRedefinicao(String emailDestino, String token)
            throws MessagingException, UnsupportedEncodingException {

        String link = "http://localhost:8080/studybridge/redefinir-senha?token=" + token;

        String corpo = """
        <html>
          <body style="font-family: Arial, sans-serif;">
            <h2>Redefinição de senha - StudyBridge</h2>
            <p>Você solicitou redefinir sua senha.</p>
            <p>Clique no link abaixo para continuar:</p>
            <p><a href="%s" target="_blank">%s</a></p>
            <hr>
            <p>Se você não solicitou isso, simplesmente ignore este e-mail.</p>
          </body>
        </html>
        """.formatted(link, link);

        enviarEmail(emailDestino, "Redefinição de senha - StudyBridge", corpo);
    }


    private void enviarEmail(String destinatario, String assunto, String corpoHtml)
            throws MessagingException, UnsupportedEncodingException {

        Session session = criarSessao();
        Message mensagem = new MimeMessage(session);
        mensagem.setFrom(new InternetAddress(REMETENTE, "StudyBridge"));
        mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        mensagem.setSubject(assunto);
        mensagem.setContent(corpoHtml, "text/html; charset=utf-8");
        Transport.send(mensagem);
        System.out.println("Email enviado para " + destinatario + " com assunto: " + assunto);
    }
}

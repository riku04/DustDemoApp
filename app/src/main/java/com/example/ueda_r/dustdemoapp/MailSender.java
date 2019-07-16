package com.example.ueda_r.dustdemoapp;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    //final String to = "ueda-r@toack.co.jp";
    //final String from = "iruck6244@gmail.com";

    // Google account mail address
    private String username;
    // Google App password
    private String password;

    //final String charset = "ISO-2022-JP";
    final String charset = "UTF-8";
    final String encoding = "base64";

    // for gmail
    String host = "smtp.gmail.com";
    String port = "587";
    String starttls = "true";

    MailSender(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void send(String to, String subject, String content) {

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", starttls);

        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        props.put("mail.debug", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);

            // Set From:
            message.setFrom(new InternetAddress(username, "DemoUser"));
            // Set ReplyTo:
            message.setReplyTo(new Address[]{new InternetAddress(username)});
            // Set To:
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject, charset);
            message.setText(content, charset);

            message.setHeader("Content-Transfer-Encoding", encoding);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

}

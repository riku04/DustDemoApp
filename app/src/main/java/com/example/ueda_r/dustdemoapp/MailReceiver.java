package com.example.ueda_r.dustdemoapp;

import android.util.Log;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
//test
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

public class MailReceiver {

    // Google account mail address
    private String username;
    // Google App password
    private String password;

    //リクエスト送信時刻
    private Date requestDate;
    private String targetData = null;

    //final String charset = "ISO-2022-JP";
    final String charset = "UTF-8";
    final String encoding = "base64";

    MailReceiver(Date requestDate, String username, String password) {
        this.requestDate = requestDate;
        this.username = username;
        this.password = password;
    }

    public String proccess(){
        Properties props = new Properties();
        // 基本情報。ここでは gmailへの接続例を示します。
        props.setProperty("mail.pop3.host", "pop.gmail.com");
        props.setProperty("mail.pop3.port", "995");

        // タイムアウト設定
        props.setProperty("mail.pop3.connectiontimeout", "60000");
        props.setProperty("mail.pop3.timeout", "60000");

        // SSL関連設定
        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.pop3.socketFactory.fallback", "false");
        props.setProperty("mail.pop3.socketFactory.port", "995");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        session.setDebug(true);
        Store store = null;
        try{
        try {
            store = session.getStore("pop3");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }

        try {
            store.connect();
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            return null;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }

        Folder folder = null;
        try {
            try {
                folder = store.getFolder("INBOX");
            } catch (MessagingException e) {
                e.printStackTrace();
                return null;
            }
            try {
                folder.open(Folder.READ_ONLY);
            } catch (MessagingException e) {
                e.printStackTrace();
                return null;
            }

            try {
                final Message messages[] = folder.getMessages();

                //for (int index = messages.length - 1; index >= 0; index--) {

                //see only newest message
                int index = messages.length - 1;

                Date sentDate = messages[index].getSentDate();
                Boolean after = messages[index].getSentDate().after(requestDate);
                Boolean equal = messages[index].getSentDate().equals(requestDate);
                Boolean before = messages[index].getSentDate().before(requestDate);
                String sent = messages[index].getSentDate().toString();
                String request = requestDate.toString();
                Boolean strEq = sent.contains(request);

                if (requestDate != null) {
                    if (messages[index].getSentDate().after(requestDate) || sent.contains(request)) {
                        if (messages[index].getSubject().equals("demo")) {
                            Message msg = messages[index];
                            try {
                                final Object content = msg.getContent();
                                if (content instanceof Multipart) {
                                    MimeMultipart mimeMultipart = (MimeMultipart) content;
                                    BodyPart bodyPart = mimeMultipart.getBodyPart(0);
                                    String body = bodyPart.toString();
                                    targetData = body;
                                    Log.d("BODY:", body);
                                } else {
                                    String body = content.toString();
                                    targetData = body;
                                    Log.d("BODY", body);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    if (messages[index].getSubject().equals("demo")) {
                        Message msg = messages[index];
                        try {
                            final Object content = msg.getContent();
                            if (content instanceof Multipart) {
                                MimeMultipart mimeMultipart = (MimeMultipart) content;
                                BodyPart bodyPart = mimeMultipart.getBodyPart(0);
                                String body = bodyPart.toString();
                                targetData = body;
                                Log.d("BODY:", body);
                            } else {
                                String body = content.toString();
                                targetData = body;
                                Log.d("BODY", body);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //}


            } catch (MessagingException e) {
                e.printStackTrace();
                return null;
            }
        } finally {
            if (folder != null) {
                try {
                    folder.close(false);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    } finally {
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
        return targetData;
    }
}

package com.example.ueda_r.dustdemoapp;

import android.content.Context;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSendTask extends AsyncTask<Object, Object, Object> {

    private MailSendTaskCallback callback;

    String TAG = "MSTask";

    private String username;
    private String password;
    private String to;
    private String subject;
    private String content;

    Date requestDate;

    //final String charset = "ISO-2022-JP";
    final String charset = "UTF-8";
    final String encoding = "base64";

    // for gmail
    String host = "smtp.gmail.com";
    String port = "587";
    String starttls = "true";

    Context context;

    MailSendTask(Context context,Date requestDate, String username, String password, String to, String subject, String content, MailSendTaskCallback callback) {
        this.context = context;
        this.requestDate = requestDate;
        this.username = username;
        this.password = password;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        //バックグラウンド処理開始前にUIスレッドで実行される。
        //ダイアログの生成などを行う。
    }

    @Override
    protected Object doInBackground(Object... params) {
        send();
        return null;
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        //doInBackgroundの実行中にUIスレッドで実行される。
        //引数のvaluesを使ってプログレスバーの更新などをする際は、ここに記述する。
    }

    @Override
    protected void onPostExecute(Object result) {
        this.callback.onMSTaskComplete(requestDate);
        //new MailReceiveTask(context, requestDate, username, password);
        //doInBackgroundが終了するとUIスレッドで実行される。
        //ダイアログの消去などを行う。
        //doInBackgroundの結果を画面表示に反映させる処理もここに記述。
    }

    private void send() {

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

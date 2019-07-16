package com.example.ueda_r.dustdemoapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

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

public class MailReceiveTask extends AsyncTask<Object, Object, Object> {

    private MailReceiveTaskCallback callback;

    String TAG = "MRTask";

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

    private String dataString;
    Context context;

    MailReceiveTask(Context context, Date requestDate, String username, String password, MailReceiveTaskCallback callback) {
        this.context = context;
        this.requestDate = requestDate;
        this.username = username;
        this.password = password;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        //バックグラウンド処理開始前にUIスレッドで実行される。
        //ダイアログの生成などを行う。
    }

    @Override
    protected Object doInBackground(Object... params) {
        dataString = proccess();
        return null;
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        //doInBackgroundの実行中にUIスレッドで実行される。
        //引数のvaluesを使ってプログレスバーの更新などをする際は、ここに記述する。
    }

    @Override
    protected void onPostExecute(Object result) {
        callback.onMSTaskComplete(dataString);
        //doInBackgroundが終了するとUIスレッドで実行される。
        //ダイアログの消去などを行う。
        //doInBackgroundの結果を画面表示に反映させる処理もここに記述。
    }

    public String proccess(){
        Properties props = new Properties();
        // gmailへ接続例
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

                    //最新から5件まで参照
                    for (int index = messages.length - 1; index >= messages.length - 5; index--) {

                        //see only newest message

                        //int index = messages.length - 1;

                        Date sentDate = messages[index].getSentDate();
                        Boolean after = messages[index].getSentDate().after(requestDate);
                        Boolean equal = messages[index].getSentDate().equals(requestDate);
                        Boolean before = messages[index].getSentDate().before(requestDate);
                        String sent = messages[index].getSentDate().toString();
                        String request = requestDate.toString();
                        Boolean strEq = sent.contains(request);

                        if (requestDate != null) {
                            //リクエスト送信以降に受け取ったメールかチェック
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
                                            break;
                                        } else {
                                            String body = content.toString();
                                            targetData = body;
                                            Log.d("BODY", body);
                                            break;
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
//                    else {
//                        if (messages[index].getSubject().equals("demo")) {
//                            Message msg = messages[index];
//                            try {
//                                final Object content = msg.getContent();
//                                if (content instanceof Multipart) {
//                                    MimeMultipart mimeMultipart = (MimeMultipart) content;
//                                    BodyPart bodyPart = mimeMultipart.getBodyPart(0);
//                                    String body = bodyPart.toString();
//                                    targetData = body;
//                                    Log.d("BODY:", body);
//                                } else {
//                                    String body = content.toString();
//                                    targetData = body;
//                                    Log.d("BODY", body);
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
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

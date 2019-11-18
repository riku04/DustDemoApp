package com.example.ueda_r.dustdemoapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.Message;

public class MainActivity extends Activity {

    String TAG = "DEBUG";

    ArrayList<Message> receivedMessages = new ArrayList<>();
    final static String username = "yamag.t.work@gmail.com";
    final static String password = "Tk2010638";

    final static String dummmy = "v,01,2019/03/08 10:05,1.111,2.222,3.333,4.444,5.555,6.666,7.777,8.888,22.8";
    private Date sendRequestDate;

    private MailSendTask mailSendTask;

    private Context context = MainActivity.this;
    private long startTimeMillis = 0;

    private int currentCh = 1;
    private TextView voltageResult;
    private TextView tempResult;
    private LinearLayout voltageLayout;
    private TextView voltageText;
    private LinearLayout tempLayout;

    private TextView idDescription;
    private TextView dateDescription;

    private LinearLayout tag1;
    private LinearLayout tag2;
    private LinearLayout tag3;
    private LinearLayout tag4;
    private LinearLayout tag5;
    private LinearLayout tag6;
    private LinearLayout tag7;
    private LinearLayout tag8;

    private TextView value1;
    private TextView value2;
    private TextView value3;
    private TextView value4;
    private TextView value5;
    private TextView value6;
    private TextView value7;
    private TextView value8;

    private int color1;
    private int color2;
    private int color3;
    private int color4;
    private int color5;
    private int color6;
    private int color7;
    private int color8;

    private ProgressDialog requestDialog;
    private ProgressDialog receiveDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (netWorkCheck(MainActivity.this) == false) {
            finish();
        }

        requestDialog = new ProgressDialog(MainActivity.this);
        receiveDialog = new ProgressDialog(MainActivity.this);

        color1 = (int) getResources().getColor(R.color.SkyBlue);
        color2 = (int) getResources().getColor(R.color.Orange);
        color3 = (int) getResources().getColor(R.color.GreenYellow);
        color4 = (int) getResources().getColor(R.color.MediumPurple);
        color5 = (int) getResources().getColor(R.color.LightPink);
        color6 = (int) getResources().getColor(R.color.IndianRed);
        color7 = (int) getResources().getColor(R.color.Yellow);
        color8 = (int) getResources().getColor(R.color.ForestGreen);

        voltageResult = (TextView) findViewById(R.id.voltageResult);
        voltageLayout = (LinearLayout) findViewById(R.id.voltageLayout);
        voltageText = (TextView) findViewById(R.id.voltage);
        voltageText.setText("A");
        tempResult = (TextView) findViewById(R.id.tempResult);
        tempLayout = (LinearLayout) findViewById(R.id.tempLayout);

        idDescription = (TextView) findViewById(R.id.idDescription);
        dateDescription = (TextView) findViewById(R.id.dateDescription);

        value1 = (TextView) findViewById(R.id.value1);
        tag1 = (LinearLayout) findViewById(R.id.tag1);

        if (tag1 != null) {
            tag1.setBackgroundColor(color1);
            tag1.setClickable(true);
            tag1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (currentCh != 1) {
                            changeCh(1);
                            currentCh = 1;
                        }
                    }
                    return true;
                }
            });
        }

        value2 = (TextView) findViewById(R.id.value2);
        tag2 = (LinearLayout) findViewById(R.id.tag2);
        if (tag2 != null) {
            tag2.setBackgroundColor(color2);
            tag2.setClickable(true);
            tag2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (currentCh != 2) {
                            changeCh(2);
                            currentCh = 2;
                        }
                    }
                    return true;
                }
            });
        }

        value3 = (TextView) findViewById(R.id.value3);
        tag3 = (LinearLayout) findViewById(R.id.tag3);
        if (value3 != null) {
            value3.setBackgroundColor(color3);
            tag3.setClickable(true);
            tag3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (currentCh != 3) {
                            changeCh(3);
                            currentCh = 3;
                        }
                    }
                    return true;
                }
            });
        }

        value4 = (TextView) findViewById(R.id.value4);
        tag4 = (LinearLayout) findViewById(R.id.tag4);
        if (tag4 != null) {
            tag4.setBackgroundColor(color4);
            tag4.setClickable(true);
            tag4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (currentCh != 4) {
                            changeCh(4);
                            currentCh = 4;
                        }
                    }
                    return true;
                }
            });
        }

        value5 = (TextView) findViewById(R.id.value5);
        tag5 = (LinearLayout) findViewById(R.id.tag5);
        if (tag5 != null) {
            tag5.setBackgroundColor(color5);
            tag5.setClickable(true);
            tag5.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (currentCh != 5) {
                            changeCh(5);
                            currentCh = 5;
                        }
                    }
                    return true;
                }
            });
        }

        value6 = (TextView) findViewById(R.id.value6);
        tag6 = (LinearLayout) findViewById(R.id.tag6);
        if (tag6 != null) {
            tag6.setBackgroundColor(color6);
            tag6.setClickable(true);
            tag6.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (currentCh != 6) {
                            changeCh(6);
                            currentCh = 6;
                        }
                    }
                    return true;
                }
            });
        }

        value7 = (TextView) findViewById(R.id.value7);
        tag7 = (LinearLayout) findViewById(R.id.tag7);
        if(tag7!=null) {
            tag7.setBackgroundColor(color7);
            tag7.setClickable(true);
            tag7.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (currentCh != 7) {
                            changeCh(7);
                            currentCh = 7;
                        }
                    }
                    return true;
                }
            });
        }

        value8 = (TextView) findViewById(R.id.value8);
        tag8 = (LinearLayout) findViewById(R.id.tag8);
        if(tag8!=null) {
            tag8.setBackgroundColor(color8);
            tag8.setClickable(true);
            tag8.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (currentCh != 8) {
                            changeCh(8);
                            currentCh = 8;
                        }
                    }
                    return true;
                }
            });
        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //final MailSender mailSender = new MailSender(username, password);
        final Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("データ取得リクエストを送信します\r\nこの処理には1分程度かかることがあります")
                        .setPositiveButton("送信", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                requestDialog.setMessage("リクエスト送信中…");
                                requestDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                requestDialog.setCancelable(false);
                                requestDialog.show();

                                sendRequestDate = new Date();
                                startTimeMillis = System.currentTimeMillis();

                                //メール送信タスク完了後にメール受信タスク開始
                                new MailSendTask(context, sendRequestDate, username, password, username, "read data", "app", new MailSendTaskCallback() {
                                    @Override
                                    public void onMSTaskComplete(Date requestedDate) {
                                        requestDialog.cancel();
                                        receiveDialog.setMessage("データ受信待ち…");
                                        receiveDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                        receiveDialog.setCancelable(false);
                                        receiveDialog.show();
                                        //メール送信完了！
                                        Log.d(TAG, "request send complete!");
                                        new MailReceiveTask(context, requestedDate, username, password, new MailReceiveTaskCallback() {
                                            @Override
                                            public void onMSTaskComplete(String result) {
                                                //dataString 空なら10秒待ってからリトライ(タイムアウト2分)
                                                //Log.d(TAG, result);
                                                long currentTimeMillis = System.currentTimeMillis();
                                                long millis = currentTimeMillis - startTimeMillis;
                                                if ((result == null) && ((millis) <= 120 * 1000)) {

                                                    Log.d(TAG, "result null >>> retrying...");
                                                    retryMailReceiveTask(5, 24);

                                                } else if (result != null) {
                                                    receiveDialog.cancel();
                                                    Log.d(TAG, result);
                                                    display(result);
                                                }
                                            }
                                        }).execute();
                                    }
                                }).execute();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

//        Button receiveButton = (Button) findViewById(R.id.receiveButton);
//        receiveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final MailReceiver mailReceiver = new MailReceiver(sendRequestDate, username, password);
//                String str = mailReceiver.proccess();
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private Runnable r;
    private void retryMailReceiveTask(final int intervalSec, final int retryCount) {

        final Handler handler = new Handler();
        r = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                Log.d(TAG, "retry:" + Integer.toString(count));
                if (count > retryCount) {
                    receiveDialog.cancel();
                    Log.d(TAG, "retry >>> timeout");
                    return;
                }
                new MailReceiveTask(context, sendRequestDate, username, password, new MailReceiveTaskCallback() {
                    @Override
                    public void onMSTaskComplete(String result) {
                        if (result != null) {
                            receiveDialog.cancel();
                            display(result);
                            Log.d(TAG, "retry >>> completed!");
                            handler.removeCallbacks(r);
                            //handlerこっちの方がいい？
                        } else {
                            Log.d(TAG, "result null >>> retrying...");
                        }
                    }
                }).execute();
                handler.postDelayed(this, intervalSec * 1000);
            }
        };
        handler.post(r);
        return;
    }

    private String reverseNegative(String num) {

        //* 使わないのでコメントアウト *//

//        String temp = new String(num);
//        if (temp.contains("-")) {
//            temp = temp.substring(1);
//        } else {
//            temp = "-" + temp;
//        }
//        return temp;

        return num;
    }

    private void display(String data){
        data.replaceAll("\n", "");
        data.replaceAll("\r", "");
        data.replaceAll("\r\n", "");

        String[] split = data.split(",");

        String dataType = split[0];
        String id = split[1];
        String dateTime = split[2];
        String ch1 = split[3];
        String ch2_1 = split[4];
        String ch2_2 = split[5];
        String ch3 = split[6];
        String ch4_1 = split[7];
        String ch4_2 = split[8];
        String ch5 = split[9];
        String ch6_1 = split[10];
        String ch6_2 = split[11];
        String ch7 = split[12];
        String ch8_1 = split[13];
        String ch8_2 = split[14];
        String temp = split[15];

//        ch1 = reverseNegative(ch1);
//        ch2 = reverseNegative(ch2);
//        ch3 = reverseNegative(ch3);
//        ch4 = reverseNegative(ch4);
//        ch5 = reverseNegative(ch5);
//        ch6 = reverseNegative(ch6);
//        ch7 = reverseNegative(ch7);
//        ch8 = reverseNegative(ch8);

        idDescription.setText("ID:" + id);
        dateDescription.setText(dateTime);
        voltageResult.setText(ch1);
        voltageText.setText("A");
        tempResult.setText(temp);
        tempResult.setGravity(Gravity.CENTER);


        value2.setText(ch2_1);
        value3.setText(ch3);
        value4.setText(ch4_1);
        value5.setText(ch5);
        value6.setText(ch6_1);
        value7.setText(ch7);
        value8.setText(ch8_1);

        Toast.makeText(context, data, Toast.LENGTH_LONG).show();
    }

    private void changeCh(int ch) {

        Log.d(TAG, "change to " + Integer.toString(ch) + "ch");

        String mainValue = "";
        int mainColor;

        switch (currentCh) {
            case 1:
                value1.setText(voltageResult.getText());
                break;
            case 2:
                value2.setText(voltageResult.getText());
                break;
            case 3:
                value3.setText(voltageResult.getText());
                break;
            case 4:
                value4.setText(voltageResult.getText());
                break;
            case 5:
                value5.setText(voltageResult.getText());
                break;
            case 6:
                value6.setText(voltageResult.getText());
                break;
            case 7:
                value7.setText(voltageResult.getText());
                break;
            case 8:
                value8.setText(voltageResult.getText());
                break;
            default:
                return;
        }

        String tempStr;
        int tempColor;

        switch (ch){
            case 1:
                voltageLayout.setBackgroundColor(color1);
                tempLayout.setBackgroundColor(color1);
                voltageResult.setText(value1.getText());
                value1.setText("");
                voltageText.setText("A");
                break;
            case 2:
                voltageLayout.setBackgroundColor(color2);
                tempLayout.setBackgroundColor(color2);
                voltageResult.setText(value2.getText());
                value2.setText("");
                voltageText.setText("V");
                break;
            case 3:
                voltageLayout.setBackgroundColor(color3);
                tempLayout.setBackgroundColor(color3);
                voltageResult.setText(value3.getText());
                value3.setText("");
                voltageText.setText("A");
                break;
            case 4:
                voltageLayout.setBackgroundColor(color4);
                tempLayout.setBackgroundColor(color4);
                voltageResult.setText(value4.getText());
                value4.setText("");
                voltageText.setText("V");
                break;
            case 5:
                voltageLayout.setBackgroundColor(color5);
                tempLayout.setBackgroundColor(color5);
                voltageResult.setText(value5.getText());
                value5.setText("");
                voltageText.setText("A");
                break;
            case 6:
                voltageLayout.setBackgroundColor(color6);
                tempLayout.setBackgroundColor(color6);
                voltageResult.setText(value6.getText());
                value6.setText("");
                voltageText.setText("V");
                break;
            case 7:
                voltageLayout.setBackgroundColor(color7);
                tempLayout.setBackgroundColor(color7);
                voltageResult.setText(value7.getText());
                value7.setText("");
                voltageText.setText("A");
                break;
            case 8:
                voltageLayout.setBackgroundColor(color8);
                tempLayout.setBackgroundColor(color8);
                voltageResult.setText(value8.getText());
                value8.setText("");
                voltageText.setText("V");
                break;
            default:
                return;
        }
    }

    public static boolean netWorkCheck(Context context){
        ConnectivityManager cm =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if( info != null ){
            return info.isConnected();
        } else {
            return false;
        }
    }
}

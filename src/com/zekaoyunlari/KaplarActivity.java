package com.zekaoyunlari;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class KaplarActivity extends Activity {

    private KaplarView kView;
    private TextView infoView;

    long starttime;
    boolean stopped = false;
    final Handler h = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            long millis = System.currentTimeMillis() - starttime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds     = seconds % 60;
            if (!stopped){
                infoView.setText(String.format("%02d:%02d", minutes, seconds));
            }
            return false;
        }
    });

    class firstTask extends TimerTask {

        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaplar);
        kView = (KaplarView) findViewById(R.id.masa);
        infoView = (TextView) findViewById(R.id.info);
        Timer timer = new Timer();
        starttime = System.currentTimeMillis();
        timer.schedule(new firstTask(), 0, 500);
        findViewById(R.id.new_game).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        kView.newGame();
                        kView.invalidate();
                        starttime = System.currentTimeMillis();
                        stopped = false;
                    }
                });
        findViewById(R.id.solve_game).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        kView.solve();
                        stopped = true;
                    }
                });
    }

}

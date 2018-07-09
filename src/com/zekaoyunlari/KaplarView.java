package com.zekaoyunlari;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class KaplarView extends View {

    final double ALTIN_ORAN = (1 + Math.sqrt(5)) / 2;
    int boardWidth = 300;
    int soruno, fromBottle, toBottle;
    int doluluk[];
    Random rand;
    int sorular[][] = {{7, 5, 3, 6}, {9, 5, 4, 3}, {9, 5, 4, 7}, {9, 5, 4, 6}, {9, 6, 5, 2}, {9, 5, 3, 8}, {9, 5, 2, 8}, {7, 4, 3, 5},
            {8, 5, 2, 7}, {7, 7, 1, 3}, {7, 5, 2, 1}, {8, 7, 1, 4}, {8, 8, 1, 4}, {7, 6, 1, 3}, {7, 7, 3, 6}, {8, 7, 3, 6}, {7, 7, 2, 6},
            {8, 6, 1, 4}, {8, 8, 3, 6}, {8, 5, 3, 7}, {9, 6, 4, 8}, {10, 9, 2, 5}, {8, 5, 4, 2}, {9, 8, 3, 7}, {10, 9, 1, 5}, {8, 4, 1, 2},
            {10, 8, 3, 9}, {10, 7, 6, 2}, {8, 4, 3, 6}, {10, 7, 6, 8}, {11, 10, 4, 2}, {10, 8, 1, 5}, {11, 10, 4, 9}, {11, 8, 5, 4}, {10, 7, 5, 6},
            {10, 7, 1, 5}, {11, 8, 5, 9}};
    final int SORU_SAYISI = sorular.length;

    public void drawKap(Canvas canvas, float centerX, float centerY, float height, int filled, int volume){
        String s;
        Rect bounds = new Rect();
        Paint red = new Paint(), black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL_AND_STROKE);
        black.setTextSize(16);
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);
        canvas.drawLine(centerX - boardWidth / 12, centerY, centerX + boardWidth / 12, centerY, black);
        canvas.drawLine(centerX - boardWidth / 12, centerY, (float) (centerX - (ALTIN_ORAN * boardWidth) / 12.0), centerY - height, black);
        canvas.drawLine(centerX + boardWidth / 12, centerY, (float) (centerX + (ALTIN_ORAN * boardWidth) / 12.0), centerY - height, black);
        canvas.drawLine((float) (centerX - (ALTIN_ORAN * boardWidth) / 12.0), centerY - height, (float) (centerX + (ALTIN_ORAN * boardWidth) / 12.0), centerY - height, black);
        s = "(" + filled + "/" + volume + ")";
        black.getTextBounds(s, 0, s.length(), bounds);
        canvas.drawText(s, centerX - bounds.width() / 2, centerY + bounds.height(), black);
        if (filled > 0){
            Path path = new Path();
            path.moveTo(centerX - boardWidth / 12, centerY);
            path.lineTo((float) (centerX - boardWidth / 12.0 - (filled * (ALTIN_ORAN - 1) * boardWidth) / (12.0 * volume)), centerY - height * filled / volume);
            path.lineTo((float) (centerX + boardWidth / 12.0 + (filled * (ALTIN_ORAN - 1) * boardWidth) / (12.0 * volume)), centerY - height * filled / volume);
            path.lineTo(centerX + boardWidth / 12, centerY);
            path.lineTo(centerX - boardWidth / 12, centerY);
            path.close();
            canvas.drawPath(path, red);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        String s;
        float height;
        Paint red = new Paint(), black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        black.setTextSize(24);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < 3; i++){
            height = (float) ((sorular[soruno][i] * boardWidth) / (sorular[soruno][0] + 2.0));
            drawKap(canvas, (float) ((2 * i + 1) * boardWidth / 6.0), (float) (((sorular[soruno][0] + 1.0) * boardWidth) / (sorular[soruno][0] + 2.0)), height, doluluk[i], sorular[soruno][i]);
        }
        s = "" + sorular[soruno][3] + "";
        canvas.drawText(s, boardWidth - 30, 30, black);
    }

    boolean bosalt(int fromBottle, int toBottle){
        int bosluk = sorular[soruno][toBottle] - doluluk[toBottle];
        if (doluluk[fromBottle] > 0 && bosluk > 0){
            if (doluluk[fromBottle] > bosluk){
                doluluk[fromBottle] -= bosluk;
                doluluk[toBottle] += bosluk;
            } else {
                doluluk[toBottle] += doluluk[fromBottle];
                doluluk[fromBottle] = 0;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            fromBottle = (int) (event.getX() * 3 / boardWidth);
        } else if (action == MotionEvent.ACTION_UP) {
            toBottle = (int) (event.getX() * 3 / boardWidth);
            if (fromBottle != toBottle && bosalt(fromBottle, toBottle)){
                if (doluluk[0] == sorular[soruno][3] || doluluk[1] == sorular[soruno][3] || doluluk[2] == sorular[soruno][3]){
                    Context context = this.getContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, getContext().getString(R.string.win), duration);
                    toast.show();
                    newGame();
                }
                invalidate();
            }
        }
        return true;
    }

    public KaplarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rand = new Random();
        doluluk = new int[3];
        newGame();
        setFocusable(true);
    }

    public void newGame(){
        soruno = rand.nextInt(SORU_SAYISI);
        doluluk[0] = sorular[soruno][0];
        doluluk[1] = 0;
        doluluk[2] = 0;
    }

    public void solve(){
        final int[][] cozum;
        int i;
        KaplarCozum soru;
        KaplarDurum cozumsonu;
        soru = new KaplarCozum(sorular[soruno][0], sorular[soruno][1], sorular[soruno][2], sorular[soruno][3]);
        cozumsonu = soru.coz();
        cozum = new int[cozumsonu.cozumUzunlugu()][3];
        i = cozumsonu.cozumUzunlugu() - 1;
        while (cozumsonu != null){
            cozum[i] = cozumsonu.agirlik();
            cozumsonu = cozumsonu.gelinen();
            i--;
        }
        new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while(i < cozum.length){
                    try {
                        Thread.sleep(2000);
                        doluluk = cozum[i];
                        postInvalidate();
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}

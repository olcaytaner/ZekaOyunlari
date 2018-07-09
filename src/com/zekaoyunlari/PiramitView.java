package com.zekaoyunlari;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class PiramitView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth;
    Random rand;
    boolean finished;
    int[] cozum;
    int[] tablo;
    Point[][] merkezler;
    String sorular[][] = {{"4", "43", "252", "1453", "36141", "522663"},
            {"2", "34", "524", "4356", "26143", "614625"}, {"1", "61", "524", "2463", "13452", "326215"},
            {"3", "55", "424", "3156", "65631", "243245"}, {"6", "53", "634", "5426", "21351", "325265"},
            {"5", "43", "236", "6121", "35654", "465432"}, {"4", "45", "385", "2793", "14268", "683141", "7252765", "95138379", "834941283"},
            {"3", "45", "342", "4689", "29768", "161215", "4854647", "67167583", "529398619"}, {"2", "33", "154", "9812", "65474", "421359", "9187938", "58356165", "737438971"},
            {"1", "34", "925", "5483", "26192", "374564", "4123753", "53491475", "182356918"}};
    final int SORU_SAYISI = sorular.length;
    final int EMPTY = -1;

    @Override
    public void onDraw(Canvas canvas) {
        String s;
        Rect bounds = new Rect();
        Paint black = new Paint(), blue = new Paint();
        int i, j;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.STROKE);
        black.setTextSize(cellWidth / 2);
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.STROKE);
        blue.setTextSize(cellWidth / 2);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < width; i++){
            for (j = 0; j <= i; j++){
                blue.setStrokeWidth(3);
                if (tablo[i] != j){
                    canvas.drawArc(new RectF(merkezler[i][j].x - cellWidth / 2, merkezler[i][j].y - cellWidth / 2, merkezler[i][j].x + cellWidth / 2, merkezler[i][j].y + cellWidth / 2), 0, 360, false, black);
                } else {
                    canvas.drawArc(new RectF(merkezler[i][j].x - cellWidth / 2, merkezler[i][j].y - cellWidth / 2, merkezler[i][j].x + cellWidth / 2, merkezler[i][j].y + cellWidth / 2), 0, 360, false, blue);
                }
                s = "" + sorular[soruno][i].charAt(j);
                black.getTextBounds(s, 0, s.length(), bounds);
                blue.setStrokeWidth(1);
                if (tablo[i] != j){
                    canvas.drawText(s, merkezler[i][j].x - bounds.width() / 2, merkezler[i][j].y + bounds.height() / 2, black);
                } else {
                    canvas.drawText(s, merkezler[i][j].x - bounds.width() / 2, merkezler[i][j].y + bounds.height() / 2, blue);
                }
            }
        }
    }

    public PiramitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rand = new Random();
        newGame();
        setFocusable(true);
    }

    public Point hangiMerkez(int x, int y){
        int i, j;
        for (i = 0; i < width; i++)
            for (j = 0; j <= i; j++)
                if ((merkezler[i][j].x - x) * (merkezler[i][j].x - x) + (merkezler[i][j].y - y) * (merkezler[i][j].y - y) < cellWidth * cellWidth / 4){
                    return new Point(i, j);
                }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point hangi = hangiMerkez(x, y);
            if (hangi != null){
                if (tablo[hangi.x] == EMPTY)
                    tablo[hangi.x] = hangi.y;
                else
                    tablo[hangi.x] = EMPTY;
                invalidate();
            }
            return true;
        }
        return false;
    }

    public void newGame(){
        int i, j;
        finished = false;
        soruno = rand.nextInt(SORU_SAYISI);
        width = sorular[soruno].length;
        tablo = new int[width];
        cozum = new int[width];
        merkezler = new Point[width][];
        for (i = 0; i < width; i++){
            tablo[i] = EMPTY;
            merkezler[i] = new Point[i + 1];
        }
        cellWidth = boardWidth / (width + 2);
        for (i = 0; i < width; i++)
            for (j = 0; j <= i; j++)
                merkezler[i][j] = new Point((width + 2 - i + 2 * j) * cellWidth / 2, (int) ((i + 1) * cellWidth * Math.sqrt(3) / 2.0));
    }

    public void checkSolution(){
        String sonuc;
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        int i;
        for (i = 0; i < width; i++)
            cozum[i] = tablo[i];
        if (!satisfyConditions()){
            sonuc = getContext().getString(R.string.lose);
        } else {
            sonuc = getContext().getString(R.string.win);
        }
        Toast toast = Toast.makeText(context, sonuc, duration);
        toast.show();
    }

    private int constructCandidates(int k, int[] c){
        if (!satisfyNow(k)){
            return 0;
        } else {
            if (k == 0){
                c[0] = 0;
                return 1;
            } else {
                c[0] = cozum[k - 1];
                c[1] = cozum[k - 1] + 1;
                return 2;
            }
        }
    }

    private boolean satisfyConditions(){
        boolean[] secildi = new boolean[width];
        for (int i = 0; i < width; i++){
            if (cozum[i] != EMPTY && !secildi[sorular[soruno][i].charAt(cozum[i]) - '1'])
                secildi[sorular[soruno][i].charAt(cozum[i]) - '1'] = true;
            else
                return false;
        }
        return true;
    }

    private boolean satisfyNow(int k){
        boolean[] secildi = new boolean[width];
        for (int i = 0; i < k; i++){
            if (!secildi[sorular[soruno][i].charAt(cozum[i]) - '1'])
                secildi[sorular[soruno][i].charAt(cozum[i]) - '1'] = true;
            else
                return false;
        }
        return true;
    }

    private void backtrack(int k){
        int i, ncandidates;
        int[] c;
        c = new int[2];
        if (k == width){
            if (satisfyConditions()){
                finished = true;
            }
        }
        else{
            ncandidates = constructCandidates(k, c);
            for (i = 0; i < ncandidates; i++){
                cozum[k] = c[i];
                backtrack(k + 1);
                if (finished){
                    return;
                }
            }
        }
    }

    public void solve(){
        int i;
        finished = false;
        for (i = 0; i < width; i++)
            cozum[i] = EMPTY;
        backtrack(0);
        for (i = 0; i < width; i++){
            tablo[i] = cozum[i];
        }
    }
}

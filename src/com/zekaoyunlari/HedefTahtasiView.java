package com.zekaoyunlari;

import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Random;

/**
 * User: Olcay
 * Date: Sep 13, 2007
 * Time: 3:13:31 PM
 */
public class HedefTahtasiView extends View {

    int boardWidth = 300, arcWidth, center, soruno, atissayisi;
    int[] degerler;
    int[] vurulanlar;
    int[] atislar;
    Point[] pozisyonlar;
    boolean finished;
    int[] cozum;
    Random rand;
    int sorular[][] = {{13, 19, 26, 33, 43, 45}, {12, 17, 27, 36, 41, 48}, {14, 19, 26, 32, 38, 47}, {15, 21, 29, 33, 39, 47},
            {12, 18, 24, 35, 42, 45}, {14, 21, 24, 33, 42, 49}, {12, 21, 27, 33, 38, 48}, {15, 22, 28, 31, 43, 46},
            {15, 19, 29, 36, 38, 46}, {15, 18, 27, 35, 39, 48}, {15, 18, 29, 36, 38, 45}, {14, 17, 28, 31, 39, 49},
            {12, 21, 24, 36, 39, 46}, {11, 21, 28, 32, 42, 48}, {15, 22, 29, 36, 38, 48}, {12, 18, 29, 36, 39, 49},
            {10, 19, 28, 35, 38, 48}, {13, 19, 28, 32, 43, 45}, {13, 22, 27, 36, 41, 45}, {10, 17, 24, 34, 43, 48},
            {15, 22, 29, 33, 43, 46}, {14, 19, 26, 35, 41, 49}, {15, 18, 27, 35, 39, 45}, {14, 21, 27, 32, 39, 49},
            {12, 17, 24, 33, 39, 48}, {12, 21, 28, 33, 41, 45}, {13, 19, 26, 32, 43, 46}, {13, 17, 28, 34, 43, 46},
            {13, 21, 26, 31, 41, 45}, {15, 18, 24, 32, 42, 45}, {15, 21, 28, 33, 38, 45}, {12, 17, 29, 34, 38, 48},
            {15, 18, 24, 32, 39, 45}, {14, 21, 24, 35, 40, 47}, {13, 19, 26, 32, 43, 46}, {12, 21, 27, 33, 38, 45},
            {11, 18, 26, 36, 43, 47}, {15, 19, 24, 34, 41, 49}, {15, 19, 24, 34, 41, 46}, {15, 18, 24, 35, 42, 45},
            {15, 21, 27, 32, 42, 45}, {15, 18, 26, 36, 43, 45}, {12, 18, 27, 35, 42, 45}, {15, 22, 27, 32, 43, 49},
            {15, 18, 26, 36, 43, 45}, {14, 21, 24, 35, 42, 46}};
    final int MARGIN = 5;
    final int SORU_SAYISI = sorular.length;
    final int EMPTY = 0;

    @Override
    public void onDraw(Canvas canvas) {
        Paint lightGray = new Paint(), darkGray = new Paint(), gray, black = new Paint();
        Rect bounds = new Rect();
        super.onDraw(canvas);
        int i, j, x, y, tmp;
        String s;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        black.setTextSize(arcWidth - 2);
        darkGray.setColor(Color.DKGRAY);
        darkGray.setStyle(Paint.Style.FILL);
        lightGray.setColor(Color.LTGRAY);
        lightGray.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < 6; i++){
            if (i % 2 == 0){
                gray = darkGray;
            }
            else{
                gray = lightGray;
            }
            canvas.drawArc(new RectF(MARGIN + i * arcWidth, MARGIN + i * arcWidth, boardWidth - MARGIN - i * arcWidth, boardWidth - MARGIN - i * arcWidth), 0, 360, false, gray);
            s = "" + degerler[i] + "";
            black.getTextBounds(s, 0, s.length(), bounds);
            if (i != 5){
                canvas.drawText(s, center - bounds.width() / 2, MARGIN + arcWidth / 2 + arcWidth * i + bounds.width() / 2, black);
                canvas.drawText(s, center - bounds.width() / 2, boardWidth - MARGIN - arcWidth * i - arcWidth / 2 + bounds.width() / 2, black);
            }
            else{
                canvas.drawText(s, center - bounds.width() / 2, center + bounds.height() / 2, black);
            }
        }
        if (atissayisi == 0){
            for (i = 0; i < 6; i++){
                if (vurulanlar[i] > 0){
                    j = vurulanlar[i];
                    while (j > 0){
                        do{
                            x = MARGIN + rand.nextInt() % boardWidth;
                            y = MARGIN + rand.nextInt() % boardWidth;
                            tmp = hangiAlan(x, y);
                        }while(tmp != i);
                        canvas.drawArc(new RectF(x - 4, y - 4, x + 4, y + 4), 0, 360, false, black);
                        j--;
                    }
                }
            }
        } else {
            for (i = 0; i < atissayisi; i++){
                canvas.drawArc(new RectF(pozisyonlar[i].x - 4, pozisyonlar[i].y - 4, pozisyonlar[i].x + 4, pozisyonlar[i].y + 4), 0, 360, false, black);
            }
        }
    }

    public HedefTahtasiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        arcWidth = (boardWidth - 2 * MARGIN) / 12;
        center = boardWidth / 2;
        rand = new Random();
        newGame();
        setFocusable(true);
    }

    public void newGame(){
        cozum = new int[20];
        degerler = new int[6];
        vurulanlar = new int[6];
        atissayisi = 0;
        pozisyonlar = new Point[10];
        atislar = new int[10];
        soruno = rand.nextInt(SORU_SAYISI);
        System.arraycopy(sorular[soruno], 0, degerler, 0, 6);
    }

    private int constructCandidates(int[] c){
        int ncandidates = 0, i;
        for (i = 0; i < 6; i++){
            c[5 - i] = degerler[i];
            ncandidates++;
        }
        return ncandidates;
    }

    private void backtrack(int k){
        int sum = 0, i, ncandidates;
        int[] c;
        c = new int[6];
        for (i = 0; i < k; i++){
            sum += cozum[i];
        }
        if (sum > 100){
        }
        else{
            if (sum == 100){
                finished = true;
            }
            else{
                ncandidates = constructCandidates(c);
                for (i = 0; i < ncandidates; i++){
                    cozum[k] = c[i];
                    backtrack(k + 1);
                    if (finished){
                        return;
                    }
                }
            }
        }
    }

    private int hangiAlan(int x, int y){
        return 5 - (int) (Math.sqrt((x - center) * (x - center) + (y - center) * (y - center)) / arcWidth);
    }

    public void undo(){
        if (atissayisi > 0){
            vurulanlar[atislar[atissayisi - 1]]--;
            atissayisi--;
        }
    }

    public Bundle saveState() {
        int i;
        int xCoordinates[] = new int[10];
        int yCoordinates[] = new int[10];
        Bundle map = new Bundle();
        map.putIntArray("mDegerler", degerler);
        map.putIntArray("mVurulanlar", vurulanlar);
        map.putIntArray("mAtislar", atislar);
        map.putInt("mAtisSayisi", atissayisi);
        for (i = 0; i < atissayisi; i++){
            xCoordinates[i] = pozisyonlar[i].x;
            yCoordinates[i] = pozisyonlar[i].y;
        }
        map.putIntArray("xCoordinates", xCoordinates);
        map.putIntArray("yCoordinates", yCoordinates);
        return map;
    }

    public void restoreState(Bundle map) {
        int i;
        int xCoordinates[];
        int yCoordinates[];
        degerler = map.getIntArray("mDegerler");
        vurulanlar = map.getIntArray("mVurulanlar");
        atislar = map.getIntArray("mAtislar");
        atissayisi = map.getInt("mAtisSayisi");
        pozisyonlar = new Point[10];
        xCoordinates = map.getIntArray("xCoordinates");
        yCoordinates = map.getIntArray("yCoordinates");
        for (i = 0; i < atissayisi; i++){
            pozisyonlar[i].x = xCoordinates[i];
            pozisyonlar[i].y = yCoordinates[i];
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CharSequence sonuc;
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;

        } else if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int bolge = hangiAlan(x, y);
            if (bolge >= 0 && bolge <= 5){
                pozisyonlar[atissayisi] = new Point(x, y);
                atislar[atissayisi] = bolge;
                atissayisi++;
                vurulanlar[bolge]++;
                int toplam = 0;
                for (int i = 0; i < 6; i++){
                    toplam += vurulanlar[i] * degerler[i];
                }
                if (toplam >= 100){
                    Context context = this.getContext();
                    int duration = Toast.LENGTH_SHORT;
                    if (toplam > 100){
                        sonuc = getContext().getString(R.string.lose);
                    } else {
                        sonuc = getContext().getString(R.string.win);
                    }
                    Toast toast = Toast.makeText(context, sonuc, duration);
                    toast.show();
                    newGame();
                }
                invalidate();
            }
            return true;
        }
        return false;
    }

    public void solve(){
        atissayisi = 0;
        int i, j, sum = 0;
        for (i = 0; i < 6; i++){
            vurulanlar[i] = EMPTY;
        }
        finished = false;
        backtrack(0);
        i = 0;
        while (sum != 100){
            for (j = 0; j < 6; j++){
                if (degerler[j] == cozum[i]){
                    vurulanlar[j]++;
                    break;
                }
            }
            sum += cozum[i];
            i++;
        }
    }

}

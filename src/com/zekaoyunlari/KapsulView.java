package com.zekaoyunlari;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class KapsulView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth, kapsulSayisi;
    Random rand;
    boolean finished;
    int[] cozum;
    int[] tablo;
    Bitmap kapsulYatay, kapsulDusey;
    final int YATAY = 0;
    final int DUSEY = 1;
    int sorular[][][] = {{{0, 15, 0, 15, 0, 6, 0}, {17, 9, 0, 12, 5, 0, 0}},
            {{1, 7, 0, 11, 0, 0, 2}, {0, 12, 0, 3, 0, 0, 15}},
            {{14, 0, 0, 0, 8, 4, 0}, {3, 0, 0, 9, 0, 0, 15}},
            {{0, 0, 0, 13, 14, 0, 0}, {0, 14, 0, 6, 10, 10, 0}},
            {{0, 12, 0, 10, 0, 0, 0}, {11, 19, 0, 0, 6, 0, 0}},
            {{12, 0, 7, 0, 0, 14, 0}, {12, 0, 0, 14, 0, 9, 0}},
            {{12, 0, 0, 11, 0, 0, 7}, {0, 0, 15, 0, 9, 0, 0}},
            {{0, 12, 0, 11, 0, 9, 0}, {10, 0, 0, 0, 0, 0, 6}},
            {{0, 10, 8, 0, 11, 0, 13}, {9, 0, 13, 0, 0, 0, 0}},
            {{9, 8, 0, 0, 0, 12, 0}, {0, 8, 0, 0, 13, 0, 0}},
            {{7, 10, 15, 15, 0, 0, 0}, {0, 11, 0, 0, 0, 7, 0}},
            {{0, 8, 0, 0, 15, 0, 0}, {17, 0, 0, 0, 17, 11, 0}}};
    int kapsuller[][][] = {{{2, 2, DUSEY}, {2, 6, DUSEY}, {5, 2, DUSEY}, {5, 6, DUSEY}, {1, 4, YATAY}, {4, 4, YATAY}, {7, 2, YATAY}, {7, 6, YATAY}},
            {{2, 2, DUSEY}, {2, 6, DUSEY}, {4, 1, DUSEY}, {4, 7, DUSEY}, {6, 2, DUSEY}, {6, 6, DUSEY}, {1, 4, YATAY}, {7, 4, YATAY}},
            {{3, 4, DUSEY}, {6, 1, DUSEY}, {5, 6, DUSEY}, {1, 2, YATAY}, {1, 6, YATAY}, {3, 2, YATAY}, {5, 3, YATAY}, {7, 4, YATAY}},
            {{2, 1, DUSEY}, {2, 4, DUSEY}, {4, 2, DUSEY}, {4, 5, DUSEY}, {5, 7, DUSEY}, {6, 3, DUSEY}, {1, 6, YATAY}, {6, 5, YATAY}},
            {{1, 7, DUSEY}, {3, 2, DUSEY}, {3, 4, DUSEY}, {5, 1, DUSEY}, {1, 3, YATAY}, {5, 5, YATAY}, {6, 6, YATAY}, {7, 3, YATAY}},
            {{2, 2, DUSEY}, {2, 6, DUSEY}, {4, 1, DUSEY}, {6, 6, DUSEY}, {1, 4, YATAY}, {4, 5, YATAY}, {6, 2, YATAY}, {7, 4, YATAY}},
            {{2, 1, DUSEY}, {2, 7, DUSEY}, {3, 3, DUSEY}, {6, 4, DUSEY}, {2, 5, YATAY}, {5, 2, YATAY}, {5, 6, YATAY}, {7, 6, YATAY}},
            {{3, 1, DUSEY}, {2, 2, DUSEY}, {3, 4, DUSEY}, {4, 6, DUSEY}, {6, 7, DUSEY}, {1, 5, YATAY}, {5, 3, YATAY}, {7, 4, YATAY}},
            {{2, 2, DUSEY}, {3, 4, DUSEY}, {5, 5, DUSEY}, {5, 7, DUSEY}, {1, 4, YATAY}, {2, 6, YATAY}, {5, 2, YATAY}, {7, 4, YATAY}},
            {{3, 2, DUSEY}, {3, 6, DUSEY}, {5, 1, DUSEY}, {5, 4, DUSEY}, {1, 2, YATAY}, {2, 4, YATAY}, {6, 6, YATAY}, {7, 3, YATAY}},
            {{1, 3, YATAY}, {2, 2, YATAY}, {2, 6, YATAY}, {3, 5, YATAY}, {4, 4, YATAY}, {6, 3, YATAY}, {6, 6, YATAY}, {7, 2, YATAY}},
            {{2, 2, DUSEY}, {2, 5, DUSEY}, {2, 7, DUSEY}, {3, 3, DUSEY}, {4, 4, DUSEY}, {5, 2, DUSEY}, {5, 6, DUSEY}, {6, 5, DUSEY}}};
    int minMax[][] = {{1, 8}, {1, 8}, {1, 8}, {1, 8}, {1, 8}, {1, 8}, {1, 8}, {1, 8}, {1, 8}, {1, 8}, {1, 8}, {1, 8}};
    final int SORU_SAYISI = kapsuller.length;
    final int EMPTY = -1;

    @Override
    public void onDraw(Canvas canvas) {
        String s;
        Rect bounds = new Rect();
        Paint black = new Paint();
        int i, j;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.STROKE);
        black.setTextSize(cellWidth / 2);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < width + 1; i++){
            canvas.drawLine((i + 1) * cellWidth, cellWidth, (i + 1) * cellWidth, (width + 1) * cellWidth, black);
            canvas.drawLine(cellWidth, (i + 1) * cellWidth, (width + 1) * cellWidth, (i + 1) * cellWidth, black);
        }
        for (i = 0; i < width; i++){
            if (sorular[soruno][0][i] != 0){
                s = "" + sorular[soruno][0][i];
                black.getTextBounds(s, 0, s.length(), bounds);
                canvas.drawText(s, (i + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2, (width + 2) * cellWidth - bounds.height() / 2, black);
            }
            if (sorular[soruno][1][i] != 0){
                s = "" + sorular[soruno][1][i];
                black.getTextBounds(s, 0, s.length(), bounds);
                canvas.drawText(s, (width + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2, (i + 2) * cellWidth - bounds.height() / 2, black);
            }
        }
        for (i = 0; i < kapsulSayisi; i++){
            if (kapsuller[soruno][i][2] == YATAY){
                canvas.drawBitmap(kapsulYatay, null, new RectF((kapsuller[soruno][i][1] - 1) * cellWidth + 1, kapsuller[soruno][i][0] * cellWidth + 1, (kapsuller[soruno][i][1] + 2) * cellWidth - 1, (kapsuller[soruno][i][0] + 1) * cellWidth - 1), null);
            } else {
                canvas.drawBitmap(kapsulDusey, null, new RectF(kapsuller[soruno][i][1] * cellWidth + 1, (kapsuller[soruno][i][0] - 1) * cellWidth + 1, (kapsuller[soruno][i][1] + 1) * cellWidth - 1, (kapsuller[soruno][i][0] + 2) * cellWidth - 1), null);
            }
            if (tablo[i] != EMPTY){
                s = "" + tablo[i];
                black.getTextBounds(s, 0, s.length(), bounds);
                canvas.drawText(s, kapsuller[soruno][i][1] * cellWidth + cellWidth / 2 - bounds.width() / 2, (kapsuller[soruno][i][0] + 1) * cellWidth - bounds.height(), black);
            }
        }
    }

    public KapsulView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = this.getContext().getResources();
        kapsulYatay = BitmapFactory.decodeResource(mRes, R.drawable.pillhorizontal);
        kapsulDusey = BitmapFactory.decodeResource(mRes, R.drawable.pillvertical);
        rand = new Random();
        newGame();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int i;
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int posx = x / cellWidth - 1;
            int posy = y / cellWidth - 1;
            if (posx >= 0 && posx < width && posy >= 0 && posy < width){
                for (i = 0; i < kapsuller[soruno].length; i++)
                    if (posy + 1 == kapsuller[soruno][i][0] && posx + 1 == kapsuller[soruno][i][1]){
                        if (tablo[i] == EMPTY){
                            tablo[i] = minMax[soruno][0];
                        } else {
                            if (tablo[i] == minMax[soruno][1]){
                                tablo[i] = EMPTY;
                            } else {
                                tablo[i]++;
                            }
                        }
                        break;
                    }
            }
            invalidate();
            return true;
        }
        return false;
    }

    public void newGame(){
        finished = false;
        soruno = rand.nextInt(SORU_SAYISI);
        width = sorular[soruno][0].length;
        kapsulSayisi = kapsuller[soruno].length;
        tablo = new int[kapsulSayisi];
        for (int i = 0; i < kapsulSayisi; i++)
            tablo[i] = EMPTY;
        cozum = new int[kapsulSayisi];
        cellWidth = boardWidth / (width + 2);
    }

    public void checkSolution(){
        int i;
        String sonuc;
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        for (i = 0; i < kapsulSayisi; i++)
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
        int i, t;
        int[][] toplam;
        boolean done[];
        toplam = toplamlar();
        for (i = 0; i < width; i++){
            if (sorular[soruno][0][i] != 0 && toplam[0][i] > sorular[soruno][0][i]){
                return 0;
            }
            if (sorular[soruno][1][i] != 0 && toplam[1][i] > sorular[soruno][1][i]){
                return 0;
            }
        }
        done = new boolean[kapsulSayisi];
        for (i = 0; i < k; i++){
            done[cozum[i] - minMax[soruno][0]] = true;
        }
        for (i = 0, t = 0; i < kapsulSayisi; i++)
            if (!done[i]){
                c[t] = minMax[soruno][0] + i;
                t++;
            }
        return kapsulSayisi - k;
    }

    private int[][] toplamlar(){
        int i;
        int[][] toplam = new int[2][width];
        for (i = 0; i < kapsulSayisi; i++){
            if (cozum[i] != EMPTY){
                if (kapsuller[soruno][i][2] == YATAY){
                    toplam[0][kapsuller[soruno][i][1] - 2] += cozum[i];
                    toplam[0][kapsuller[soruno][i][1] - 1] += cozum[i];
                    toplam[0][kapsuller[soruno][i][1]] += cozum[i];
                    toplam[1][kapsuller[soruno][i][0] - 1] += cozum[i];
                } else {
                    toplam[1][kapsuller[soruno][i][0] - 2] += cozum[i];
                    toplam[1][kapsuller[soruno][i][0] - 1] += cozum[i];
                    toplam[1][kapsuller[soruno][i][0]] += cozum[i];
                    toplam[0][kapsuller[soruno][i][1] - 1] += cozum[i];
                }
            }
        }
        return toplam;
    }

    private boolean satisfyConditions(){
        int i;
        int[][] toplam;
        toplam = toplamlar();
        for (i = 0; i < width; i++){
            if (sorular[soruno][0][i] != 0 && toplam[0][i] != sorular[soruno][0][i]){
                return false;
            }
            if (sorular[soruno][1][i] != 0 && toplam[1][i] != sorular[soruno][1][i]){
                return false;
            }
        }
        return true;
    }

    private void backtrack(int k){
        int i, ncandidates;
        int[] c;
        c = new int[kapsulSayisi];
        if (k == kapsulSayisi){
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
            cozum[k] = EMPTY;
        }
    }

    public void solve(){
        int i;
        finished = false;
        for (i = 0; i < kapsulSayisi; i++){
            cozum[i] = EMPTY;
        }
        backtrack(0);
        for (i = 0; i < kapsulSayisi; i++){
            tablo[i] = cozum[i];
        }
    }
}

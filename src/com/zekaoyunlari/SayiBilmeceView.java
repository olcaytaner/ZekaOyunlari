package com.zekaoyunlari;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class SayiBilmeceView extends View{

    int boardWidth = 300;
    int soruno, cellWidth;
    Random rand;
    boolean finished;
    private int[][] cozum;
    private int[][] tablo;
    int sonuclar[][] = {{9, 8, -1, 39, -13, 7}, {8, 4, 14, -52, 2, 16}, {-11, 15, 2, 22, 2, 5}, {11, 10, 2, 5, 24, 13},
            {6, -26, 18, 15, 6, 6}, {7, 56, 5, 7, 11, 2}, {4, 55, 8, 0, 9, 30}, {8, 2, 8, 15, 9, -11},
            {14, 9, -7, 17, 7, 2}, {10, 8, -21, 10, -9, 8}, {-66, -10, 12, -1, 10, 13}, {34, 14, -5, 34, 13, 2}};
    String islemler[][] = {{"+/", "x-+", "+-", "-x+", "-/"}, {"-+", "-x+", "-/", "x/+", "-+"}, {"--", "x--", "++", "+/+", "x/"}, {"+/", "+xx", "-+", "-/-", "+-"},
            {"-/", "/x+", "-x", "+--", "++"}, {"+-", "-+-", "x/", "+-x", "/+"}, {"-+", "++/", "x-", "--x", "/+"}, {"+-", "/--", "x/", "++x", "-+"},
            {"/x", "+--", "+-", "++/", "-x"}, {"-+", "/-/", "+-", "+x+", "-x"}, {"-x", "-/+", "-x", "-++", "+/"}, {"+x", "x++", "+-", "-/-", "/-"}};
    final int SORU_SAYISI = sonuclar.length;
    final int EMPTY = -1;

    @Override
    public void onDraw(Canvas canvas) {
        String s;
        Rect bounds = new Rect();
        Paint black = new Paint();
        int i, j;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        black.setTextSize(cellWidth / 2);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < 6; i++){
            canvas.drawLine((i + 1) * cellWidth, cellWidth, (i + 1) * cellWidth, 6 * cellWidth, black);
            canvas.drawLine(cellWidth, (i + 1) * cellWidth, 6 * cellWidth, (i + 1) * cellWidth, black);
        }
        canvas.drawRect(new Rect(2 * cellWidth, 2 * cellWidth, 3 * cellWidth, 3 * cellWidth), black);
        canvas.drawRect(new Rect(4 * cellWidth, 2 * cellWidth, 5 * cellWidth, 3 * cellWidth), black);
        canvas.drawRect(new Rect(2 * cellWidth, 4 * cellWidth, 3 * cellWidth, 5 * cellWidth), black);
        canvas.drawRect(new Rect(4 * cellWidth, 4 * cellWidth, 5 * cellWidth, 5 * cellWidth), black);
        for (i = 0; i < 5; i++){
            for (j = 0; j < islemler[soruno][i].length(); j++){
                s = "" + islemler[soruno][i].charAt(j);
                black.getTextBounds(s, 0, s.length(), bounds);
                canvas.drawText(s, ((j + 1) * 2 - (i % 2)) * cellWidth + cellWidth / 2 - bounds.width() / 2, (i + 2) * cellWidth - bounds.height(), black);
            }
        }
        for (i = 0; i < 3; i++){
            for (j = 0; j < 3; j++){
                if (tablo[i][j] != EMPTY){
                    s = "" + tablo[i][j];
                    black.getTextBounds(s, 0, s.length(), bounds);
                    canvas.drawText(s, (2 * j + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2,  (2 * i + 2) * cellWidth - bounds.height(), black);
                }
            }
        }
        for (i = 0; i < 3; i++){
            s = " = " + sonuclar[soruno][i];
            black.getTextBounds(s, 0, s.length(), bounds);
            canvas.drawText(s, 6 * cellWidth + cellWidth / 2 - bounds.width() / 2,  (2 * i + 2) * cellWidth - bounds.height(), black);
            s = "=";
            black.getTextBounds(s, 0, s.length(), bounds);
            canvas.drawText(s, (2 * i + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2,  7 * cellWidth - bounds.height() / 2, black);
            s = "" + sonuclar[soruno][i + 3];
            black.getTextBounds(s, 0, s.length(), bounds);
            canvas.drawText(s, (2 * i + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2,  (float) (7.5 * cellWidth - bounds.height() / 2), black);
        }
    }

    public SayiBilmeceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rand = new Random();
        newGame();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int posx = x / cellWidth;
            int posy = y / cellWidth;
            if (posx % 2 == 1 && posy % 2 == 1 && posx < 6 && posy < 6){
                if (tablo[posy / 2][posx / 2] == 9){
                    tablo[posy / 2][posx / 2] = EMPTY;
                } else {
                    tablo[posy / 2][posx / 2]++;
                }
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
        tablo = new int[3][3];
        cozum = new int[3][3];
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                tablo[i][j] = EMPTY;
        cellWidth = boardWidth / 8;
    }

    public void checkSolution(){
        String sonuc;
        int i, j;
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                cozum[i][j] = tablo[i][j];
        if (!satisfyConditions()){
            sonuc = getContext().getString(R.string.lose);
        } else {
            sonuc = getContext().getString(R.string.win);
        }
        Toast toast = Toast.makeText(context, sonuc, duration);
        toast.show();
    }

    private int constructCandidates(int[] c, int k){
        int j, i, count = 0, row = k / 3, column = k % 3;
        boolean found;
        int sonuc;
        char islem1, islem2;
        int sayi1, sayi2, sayi3;
        if (row == 1 && column == 0){
            islem1 = islemler[soruno][0].charAt(0);
            islem2 = islemler[soruno][0].charAt(1);
            sayi1 = cozum[0][0];
            sayi2 = cozum[0][1];
            sayi3 = cozum[0][2];
            if (islemOnceligi(islem1, islem2)){
                sonuc = islem(sayi1, islem(sayi2, sayi3, islem2), islem1);
            } else {
                sonuc = islem(islem(sayi1, sayi2, islem1), sayi3, islem2);
            }
            if (sonuc != sonuclar[soruno][0])
                return 0;
        }
        if (row == 2 && column == 0){
            islem1 = islemler[soruno][2].charAt(0);
            islem2 = islemler[soruno][2].charAt(1);
            sayi1 = cozum[1][0];
            sayi2 = cozum[1][1];
            sayi3 = cozum[1][2];
            if (islemOnceligi(islem1, islem2)){
                sonuc = islem(sayi1, islem(sayi2, sayi3, islem2), islem1);
            } else {
                sonuc = islem(islem(sayi1, sayi2, islem1), sayi3, islem2);
            }
            if (sonuc != sonuclar[soruno][1])
                return 0;
        }
        if (row == 2 && column == 1){
            islem1 = islemler[soruno][1].charAt(0);
            islem2 = islemler[soruno][3].charAt(0);
            sayi1 = cozum[0][0];
            sayi2 = cozum[1][0];
            sayi3 = cozum[2][0];
            if (islemOnceligi(islem1, islem2)){
                sonuc = islem(sayi1, islem(sayi2, sayi3, islem2), islem1);
            } else {
                sonuc = islem(islem(sayi1, sayi2, islem1), sayi3, islem2);
            }
            if (sonuc != sonuclar[soruno][3])
                return 0;
        }
        for (i = 0; i < 10; i++){
            found = false;
            for (j = 0; j < k; j++){
                row = j / 3;
                column = j % 3;
                if (cozum[row][column] == i){
                    found = true;
                    break;
                }
            }
            if (!found){
                c[count] = i;
                count++;
            }
        }
        return count;
    }

    private boolean islemOnceligi(char ch1, char ch2){
        return (ch2 == 'x' || ch2 == '/') && (ch1 == '+' || ch1 == '-');
    }

    private int islem(int x, int y, char ch){
        switch (ch){
            case '+': return x + y;
            case '-': return x - y;
            case 'x': return x * y;
            case '/':
                if (y == 0 || x % y != 0)
                    return -1000;
                else
                    return x / y;
        }
        return -1;
    }

    private boolean satisfyConditions(){
        int i, sonuc;
        char islem1, islem2;
        int sayi1, sayi2, sayi3;
        for (i = 0; i < 3; i++){
            islem1 = islemler[soruno][i * 2].charAt(0);
            islem2 = islemler[soruno][i * 2].charAt(1);
            sayi1 = cozum[i][0];
            sayi2 = cozum[i][1];
            sayi3 = cozum[i][2];
            if (islemOnceligi(islem1, islem2)){
                sonuc = islem(sayi1, islem(sayi2, sayi3, islem2), islem1);
            } else {
                sonuc = islem(islem(sayi1, sayi2, islem1), sayi3, islem2);
            }
            if (sonuc != sonuclar[soruno][i])
                return false;
        }
        for (i = 0; i < 3; i++){
            islem1 = islemler[soruno][1].charAt(i);
            islem2 = islemler[soruno][3].charAt(i);
            sayi1 = cozum[0][i];
            sayi2 = cozum[1][i];
            sayi3 = cozum[2][i];
            if (islemOnceligi(islem1, islem2)){
                sonuc = islem(sayi1, islem(sayi2, sayi3, islem2), islem1);
            } else {
                sonuc = islem(islem(sayi1, sayi2, islem1), sayi3, islem2);
            }
            if (sonuc != sonuclar[soruno][3 + i])
                return false;
        }
        return true;
    }

    private void backtrack(int k){
        int i, ncandidates, row, column;
        int[] c;
        c = new int[10];
        if (k == 9){
            if (satisfyConditions()){
                finished = true;
            }
        }
        else{
            row = k / 3;
            column = k % 3;
            ncandidates = constructCandidates(c, k);
            for (i = 0; i < ncandidates; i++){
                cozum[row][column] = c[i];
                backtrack(k + 1);
                if (finished){
                    return;
                }
            }
        }
    }

    public void solve(){
        int i, j;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++)
                cozum[i][j] = EMPTY;
        backtrack(0);
        for (i = 0; i < 3; i++){
            for (j = 0; j < 3; j++){
                tablo[i][j] = cozum[i][j];
            }
        }
    }
}

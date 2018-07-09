package com.zekaoyunlari;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class CarpmacaView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth;
    Random rand;
    boolean finished;
    int[][] cozum;
    int[][] tablo;
    int[] right;
    int[] bottom;
    int sorular[][][] = {{{48, 6, 10, 20, 63}, {4, 14, 18, 50, 72}},
            {{50, 32, 27, 42, 2}, {30, 48, 4, 45, 14}},
            {{35, 6, 16, 36, 30}, {8, 28, 20, 54, 15}},
            {{72, 20, 30, 14, 6}, {24, 10, 16, 15, 63}},
            {{40, 45, 42, 8, 6}, {60, 40, 18, 21, 4}},
            {{20, 30, 21, 36, 8}, {27, 56, 20, 10, 12}},
            {{80, 3, 63, 20, 12}, {72, 5, 70, 18, 8}},
            {{15, 8, 40, 12, 0}, {14, 72, 10, 18, 20}},
            {{27, 55, 16, 48, 6, 70}, {60, 22, 4, 40, 21, 108}},
            {{99, 20, 14, 30, 6, 96}, {30, 110, 36, 2, 63, 32}},
            {{36, 84, 12, 11, 30, 40}, {15, 66, 90, 12, 16, 28}},
            {{120, 18, 6, 44, 40, 21}, {110, 72, 42, 12, 20, 6}},
            {{3, 28, 18, 88, 72, 50}, {72, 21, 11, 120, 24, 10}},
            {{70, 44, 2, 36, 30, 72}, {9, 20, 84, 22, 80, 18}},
            {{40, 14, 54, 55, 12, 24}, {7, 66, 18, 80, 36, 20}},
            {{30, 3, 48, 20, 77, 72}, {32, 10, 35, 36, 54, 22}},
            {{132, 6, 60, 7, 20, 72}, {6, 30, 40, 77, 48, 18}},
            {{72, 88, 15, 14, 10, 36}, {12, 20, 70, 8, 36, 99}},
            {{154, 18, 70, 40, 108, 26, 4}, {52, 84, 28, 24, 9, 55, 60}},
            {{42, 45, 96, 6, 40, 14, 143}, {168, 6, 60, 63, 8, 44, 65}},
            {{70, 33, 48, 42, 18, 13, 80}, {88, 18, 91, 40, 126, 12, 10}},
            {{36, 20, 42, 90, 104, 11, 28}, {168, 39, 40, 77, 6, 36, 20}},
            {{84, 45, 52, 42, 8, 12, 110}, {8, 91, 72, 11, 30, 84, 60}},
            {{2, 15, 99, 24, 140, 104, 84}, {45, 132, 4, 98, 48, 6, 130}},
            {{3, 8, 30, 70, 88, 117, 168}, {39, 60, 56, 42, 22, 9, 80}},
            {{24, 91, 84, 55, 32, 27, 10}, {117, 12, 154, 80, 18, 10, 28}}};
    final int SORU_SAYISI = sorular.length;
    final int EMPTY = 0;

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
            if (right[i] != 0){
                s = "" + right[i];
                black.getTextBounds(s, 0, s.length(), bounds);
                canvas.drawText(s, (width + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2, (i + 2) * cellWidth - bounds.height() / 2, black);
            }
            if (bottom[i] != 0){
                s = "" + bottom[i];
                black.getTextBounds(s, 0, s.length(), bounds);
                canvas.drawText(s, (i + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2, (width + 2) * cellWidth - bounds.height() / 2, black);
            }
        }
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (tablo[i][j] != EMPTY){
                    s = "" + tablo[i][j];
                    black.getTextBounds(s, 0, s.length(), bounds);
                    canvas.drawText(s, (j + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2, (i + 2) * cellWidth - bounds.height() / 2, black);
                }
            }
    }

    public CarpmacaView(Context context, AttributeSet attrs) {
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
            int posx = x / cellWidth - 1;
            int posy = y / cellWidth - 1;
            if (posx >= 0 && posx < width && posy >= 0 && posy < width){
                tablo[posy][posx] = (tablo[posy][posx] + 1) % (2 * width + 1);
                invalidate();
            }
            return true;
        }
        return false;
    }

    public void newGame(){
        finished = false;
        soruno = rand.nextInt(SORU_SAYISI);
        width = sorular[soruno][0].length;
        right = new int[width];
        bottom = new int[width];
        for (int i = 0; i < width; i++){
            right[i] = sorular[soruno][0][i];
            bottom[i] = sorular[soruno][1][i];
        }
        tablo = new int[width][width];
        cozum = new int[width][width];
        cellWidth = boardWidth / (width + 2);
    }

    public void checkSolution(){
        String sonuc;
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        int i, j;
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                cozum[i][j] = tablo[i][j];
            }
        if (!satisfyConditions()){
            sonuc = getContext().getString(R.string.lose);
        } else {
            sonuc = getContext().getString(R.string.win);
        }
        Toast toast = Toast.makeText(context, sonuc, duration);
        toast.show();
    }

    private int rowMultiply(int row){
        int i, count = 1;
        for (i = 0; i < width; i++){
            if (cozum[row][i] != EMPTY){
                count *= cozum[row][i];
            }
        }
        return count;
    }

    private int rowCount(int row){
        int i, count = 0;
        for (i = 0; i < width; i++){
            if (cozum[row][i] != EMPTY){
                count++;
            }
        }
        return count;
    }

    private int columnMultiply(int column){
        int i, count = 1;
        for (i = 0; i < width; i++){
            if (cozum[i][column] != EMPTY){
                count *= cozum[i][column];
            }
        }
        return count;
    }

    private int columnCount(int column){
        int i, count = 0;
        for (i = 0; i < width; i++){
            if (cozum[i][column] != EMPTY){
                count++;
            }
        }
        return count;
    }

    private int[] divisors(int N){
        int count = 0;
        int[] divisors;
        for (int i = 1; i <= 2 * width && i <= N; i++){
            if (N % i == 0 && N / i <= 2 * width){
                count++;
            }
        }
        divisors = new int[count];
        count = 0;
        for (int i = 1; i <= 2 * width && i <= N; i++){
            if (N % i == 0 && N / i <= 2 * width){
                divisors[count] = i;
                count++;
            }
        }
        return divisors;
    }

    private int constructCandidates(int k, int[] c){
        int row, column, i, count, dividend;
        int[] divisors;
        boolean[] checked = new boolean[2 * width];
        for (i = 0; i < k; i++){
            row = i / width;
            column = i % width;
            if (cozum[row][column] != EMPTY){
                checked[cozum[row][column] - 1] = true;
            }
        }
        row = k / width;
        column = k % width;
        if ((columnCount(column) == 0 && row == width - 1) || (rowCount(row) == 0 && column == width - 1)){
            return 0;
        } else {
            if (columnCount(column) == 2 || rowCount(row) == 2){
                c[0] = EMPTY;
                return 1;
            } else {
                if (columnCount(column) == 0 && rowCount(row) == 0){
                    count = 0;
                    divisors = divisors(right[row]);
                    for (i = 0; i < divisors.length; i++)
                        if (bottom[column] % divisors[i] == 0 && bottom[column] / divisors[i] <= 2 * width && !checked[divisors[i] - 1]){
                            c[count] = divisors[i];
                            count++;
                        }
                    if (column < width - 2){
                        c[count] = EMPTY;
                        count++;
                    }
                    return count;
                } else {
                    if (columnCount(column) == 1 && rowCount(row) == 1){
                        dividend = right[row] / rowMultiply(row);
                        if (dividend == bottom[column] / columnMultiply(column) && dividend <= 2 * width && !checked[dividend - 1]){
                            if (column != width - 1){
                                c[0] = dividend;
                                c[1] = EMPTY;
                                return 2;
                            } else {
                                c[0] = dividend;
                                return 1;
                            }
                        } else {
                            if (column != width - 1){
                                c[0] = EMPTY;
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    } else {
                        if (columnCount(column) == 0 && rowCount(row) == 1){
                            dividend = right[row] / rowMultiply(row);
                            if (bottom[column] % dividend == 0 && bottom[column] / dividend <= 2 * width && dividend <= 2 * width && !checked[dividend - 1]){
                                if (column != width - 1){
                                    c[0] = dividend;
                                    c[1] = EMPTY;
                                    return 2;
                                } else {
                                    c[0] = dividend;
                                    return 1;
                                }
                            } else {
                                if (column != width - 1){
                                    c[0] = EMPTY;
                                    return 1;
                                } else {
                                    return 0;
                                }
                            }
                        } else {
                            if (columnCount(column) == 1 && rowCount(row) == 0){
                                dividend = bottom[column] / columnMultiply(column);
                                if (right[row] % dividend == 0 && right[row] / dividend <= 2 * width && dividend <= 2 * width && !checked[dividend - 1]){
                                    if (column < width - 2){
                                        c[0] = dividend;
                                        c[1] = EMPTY;
                                        return 2;
                                    } else {
                                        c[0] = dividend;
                                        return 1;
                                    }
                                } else {
                                    if (column < width - 2){
                                        c[0] = EMPTY;
                                        return 1;
                                    } else {
                                        return 0;
                                    }
                                }
                            } else {
                                return 0;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean satisfyConditions(){
        int i;
        for (i = 0; i < width; i++){
            if (right[i] != 0 && rowMultiply(i) != right[i]){
                return false;
            }
            if (bottom[i] != 0 && columnMultiply(i) != bottom[i]){
                return false;
            }
            if (rowCount(i) != 2){
                return false;
            }
            if (columnCount(i) != 2){
                return false;
            }
        }
        return true;
    }

    private void backtrack(int k){
        int i, ncandidates, row, column;
        int[] c;
        c = new int[2 * width];
        if (k == width * width){
            if (satisfyConditions()){
                finished = true;
            }
        }
        else{
            row = k / width;
            column = k % width;
            ncandidates = constructCandidates(k, c);
            for (i = 0; i < ncandidates; i++){
                cozum[row][column] = c[i];
                backtrack(k + 1);
                if (finished){
                    return;
                }
            }
            cozum[row][column] = 0;
        }
    }

    public void solve(){
        int i, j;
        finished = false;
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                cozum[i][j] = 0;
            }
        }
        backtrack(0);
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                tablo[i][j] = cozum[i][j];
            }
    }
}

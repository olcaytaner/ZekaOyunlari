package com.zekaoyunlari;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class FenerlerView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth;
    boolean[][] deniz;
    Random rand;
    boolean finished;
    private int[][] cozum;
    private int[][] tablo;
    private boolean[][] yerUygun;
    Bitmap lighthouse, digits[];
    int koordinatlar[][][] = {{{1, 1, 1}, {4, 2, 1}, {5, 6, 2}, {6, 4, 1}}, {{1, 2, 1}, {2, 5, 2}, {4, 1, 2}, {6, 5, 2}},
            {{1, 4, 2}, {3, 3, 3}, {4, 5, 2}, {5, 3, 2}, {6, 7, 1}, {7, 5, 1}}, {{2, 4, 1}, {3, 1, 1}, {4, 6, 2}, {5, 1, 1}, {6, 7, 3}},
            {{1, 1, 3}, {2, 3, 1}, {4, 4, 2}, {6, 1, 5}, {7, 4, 1}, {8, 7, 1}}, {{1, 1, 3}, {2, 6, 1}, {3, 2, 3}, {4, 6, 1}, {6, 4, 3}, {7, 2, 1}, {8, 5, 2}},
            {{1, 2, 1}, {2, 5, 3}, {3, 1, 2}, {6, 3, 4}}, {{1, 3, 2}, {2, 1, 2}, {4, 5, 2}, {5, 3, 1}, {6, 1, 3}},
            {{1, 6, 1}, {3, 5, 2}, {4, 1, 2}, {5, 3, 1}, {6, 7, 1}, {7, 1, 1}}, {{1, 7, 4}, {2, 3, 1}, {3, 5, 2}, {4, 1, 1}, {5, 3, 1}, {6, 5, 1}, {7, 1, 1}},
            {{1, 8, 3}, {2, 6, 2}, {3, 1, 1}, {4, 4, 4}, {5, 1, 1}, {6, 7, 1}, {7, 5, 2}, {8, 1, 1}}, {{1, 6, 3}, {2, 8, 1}, {3, 2, 3}, {4, 6, 3}, {5, 4, 3}, {6, 1, 2}, {7, 8, 3}, {8, 4, 2}},
            {{3, 6, 2}, {4, 1, 3}, {5, 5, 1}, {6, 1, 3}}, {{1, 3, 2}, {2, 5, 1}, {3, 2, 1}, {4, 5, 1}},
            {{1, 2, 1}, {2, 4, 2}, {3, 1, 2}, {4, 6, 2}, {6, 5, 1}, {7, 2, 2}}, {{1, 6, 1}, {2, 4, 2}, {4, 7, 2}, {5, 5, 3}, {6, 3, 1}, {7, 1, 4}},
            {{1, 6, 1}, {2, 3, 2}, {3, 6, 1}, {4, 2, 1}, {5, 7, 1}, {6, 4, 4}, {7, 8, 2}, {8, 2, 1}}, {{1, 6, 3}, {2, 1, 5}, {3, 6, 2}, {4, 8, 3}, {6, 7, 2}, {7, 4, 1}, {8, 7, 2}},
            {{3, 2, 1}, {4, 6, 3}, {5, 3, 1}, {6, 1, 1}}, {{1, 3, 1}, {2, 6, 1}, {3, 3, 1}, {6, 1, 2}},
            {{1, 6, 1}, {2, 1, 1}, {3, 5, 1}, {4, 3, 3}, {6, 3, 3}, {7, 1, 1}}, {{1, 4, 3}, {2, 7, 1}, {3, 1, 2}, {4, 7, 1}, {5, 1, 2}, {6, 6, 1}, {7, 2, 3}},
            {{1, 2, 1}, {3, 7, 3}, {4, 5, 2}, {5, 2, 1}, {6, 6, 2}, {7, 3, 1}, {8, 7, 2}}, {{1, 5, 3}, {2, 8, 1}, {3, 2, 1}, {4, 5, 4}, {5, 2, 1}, {6, 8, 3}, {7, 3, 1}, {8, 7, 3}}};
    final int SORU_SAYISI = koordinatlar.length;
    final int FENER = -1;

    public void drawDigit(Canvas canvas, int x, int y, int digit){
        int W, H, w = digits[digit].getWidth(), h = digits[digit].getHeight(), l = cellWidth / 2;
        if (h > w){
            W = w * l / h;
            canvas.drawBitmap(digits[digit], null, new RectF(x + (cellWidth - l) / 2 + l / 2 - W / 2, y + (cellWidth - l) / 2, x + (cellWidth - l) / 2 + l / 2 + W / 2, y + (cellWidth + l) / 2), null);
        } else {
            H = h * l / w;
            canvas.drawBitmap(digits[digit], null, new RectF(x + (cellWidth - l) / 2, y + (cellWidth - l) / 2 + l / 2 - H / 2, x + (cellWidth + l) / 2, y + (cellWidth - l) / 2 + l / 2 + H / 2), null);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint black = new Paint(), red = new Paint();
        int i, j, posx, posy;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        black.setTextSize(cellWidth / 2);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < width + 1; i++){
            canvas.drawLine((i + 1) * cellWidth, cellWidth, (i + 1) * cellWidth, (width + 1) * cellWidth, black);
            canvas.drawLine(cellWidth, (i + 1) * cellWidth, (width + 1) * cellWidth, (i + 1) * cellWidth, black);
        }
        for (i = 0; i < koordinatlar[soruno].length; i++){
            posx = koordinatlar[soruno][i][0];
            posy = koordinatlar[soruno][i][1];
            drawDigit(canvas, posy * cellWidth, posx * cellWidth, koordinatlar[soruno][i][2]);
        }
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (deniz[i][j]){
                    canvas.drawBitmap(lighthouse, null, new RectF((j + 1) * cellWidth + 1, (i + 1) * cellWidth + 1, (j + 2) * cellWidth - 1, (i + 2) * cellWidth - 1), null);
                }
            }
    }

    public FenerlerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = this.getContext().getResources();
        lighthouse = BitmapFactory.decodeResource(mRes, R.drawable.lighthouse);
        digits = new Bitmap[10];
        digits[0] = BitmapFactory.decodeResource(mRes, R.drawable.digit0);
        digits[1] = BitmapFactory.decodeResource(mRes, R.drawable.digit1);
        digits[2] = BitmapFactory.decodeResource(mRes, R.drawable.digit2);
        digits[3] = BitmapFactory.decodeResource(mRes, R.drawable.digit3);
        digits[4] = BitmapFactory.decodeResource(mRes, R.drawable.digit4);
        digits[5] = BitmapFactory.decodeResource(mRes, R.drawable.digit5);
        digits[6] = BitmapFactory.decodeResource(mRes, R.drawable.digit6);
        digits[7] = BitmapFactory.decodeResource(mRes, R.drawable.digit7);
        digits[8] = BitmapFactory.decodeResource(mRes, R.drawable.digit8);
        digits[9] = BitmapFactory.decodeResource(mRes, R.drawable.digit9);
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
            if (posx >= 0 && posx < width && posy >= 0 && posy < width && tablo[posy][posx] <= 0){
                deniz[posy][posx] = !deniz[posy][posx];
                invalidate();
            }
            return true;
        }
        return false;
    }

    public void newGame(){
        int i;
        width = 0;
        finished = false;
        soruno = rand.nextInt(SORU_SAYISI);
        for (i = 0; i < koordinatlar[soruno].length; i++){
            if (koordinatlar[soruno][i][0] > width){
                width = koordinatlar[soruno][i][0];
            }
            if (koordinatlar[soruno][i][1] > width){
                width = koordinatlar[soruno][i][1];
            }
        }
        deniz = new boolean[width][width];
        cellWidth = boardWidth / (width + 2);
        tablo = new int[width][width];
        for (i = 0; i < width; i++)
            for (int j = 0; j < width; j++)
                tablo[i][j] = 0;
        for (i = 0; i < koordinatlar[soruno].length; i++){
            tablo[koordinatlar[soruno][i][0] - 1][koordinatlar[soruno][i][1] - 1] = koordinatlar[soruno][i][2];
        }
    }

    public void checkSolution(){
        String sonuc;
        int i, j;
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        cozum = new int[width][width];
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (deniz[i][j]){
                    cozum[i][j] = FENER;
                } else {
                    cozum[i][j] = 0;
                }
            }
        for (i = 0; i < koordinatlar[soruno].length; i++){
            cozum[koordinatlar[soruno][i][0] - 1][koordinatlar[soruno][i][1] - 1] = koordinatlar[soruno][i][2];
        }
        if (!satisfyConditions()){
            sonuc = getContext().getString(R.string.lose);
        } else {
            sonuc = getContext().getString(R.string.win);
        }
        Toast toast = Toast.makeText(context, sonuc, duration);
        toast.show();
    }

    private boolean gemiVarmi(int row, int column){
        int i, j;
        for (i = -1; i <= 1; i++){
            for (j = -1; j <= 1; j++){
                if (row + i >= 0 && row + i < width && column + j >= 0 && column + j < width){
                    if (tablo[row + i][column + j] > 0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean onundeFenerVarmi(int row, int column){
        if (row - 1 >= 0 && cozum[row - 1][column] == FENER){
            return true;
        }
        if (column - 1 >= 0 && cozum[row][column - 1] == FENER){
            return true;
        }
        if (row - 1 >= 0 && column - 1 >= 0 && cozum[row - 1][column - 1] ==FENER){
            return true;
        }
        if (row - 1 >= 0 && column + 1 < width && cozum[row - 1][column + 1] == FENER){
            return true;
        }
        return false;
    }

    private int constructCandidates(int k, int[] c){
        int row, column;
        row = k / width;
        column = k % width;
        if (tablo[row][column] > 0){
            c[0] = tablo[row][column];
            return 1;
        }
        else{
            if (yerUygun[row][column] && !onundeFenerVarmi(row, column) && toplamFenerSayisi() < width - 2){
                c[0] = FENER;
                c[1] = 0;
                return 2;
            }
            else{
                c[0] = 0;
                return 1;
            }
        }
    }

    private int toplamFenerSayisi(){
        int i, j, count = 0;
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++)
                if (cozum[i][j] == FENER){
                    count++;
                }
        }
        return count;
    }

    private int fenerSayisi(int row, int column){
        int i, j, k, count = 0;
        for (i = -1; i <= 1; i++)
            for (j = -1; j <= 1; j++)
                if (i != 0 || j != 0){
                    k = 1;
                    while (k < width){
                        if (row + i * k >= 0 && row + i * k < width && column + j * k >= 0 && column + j * k < width && cozum[row + i * k][column + j * k] == FENER){
                            count++;
                        }
                        if (row + i * k >= 0 && row + i * k < width && column + j * k >= 0 && column + j * k < width && cozum[row + i * k][column + j * k] > 0){
                            break;
                        }
                        k++;
                    }
                }
        return count;
    }

    private boolean satisfyConditions(){
        int i, j, count = 0;
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                if (cozum[i][j] == FENER){
                    count++;
                }
                if (cozum[i][j] > 0 && fenerSayisi(i, j) != cozum[i][j]){
                    return false;
                }
            }
        }
        return count == width - 2;
    }

    private void backtrack(int k){
        int i, ncandidates, row, column;
        int[] c;
        c = new int[2];
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
        }
    }

    public void solve(){
        int i, j;
        cozum = new int[width][width];
        tablo = new int[width][width];
        yerUygun = new boolean[width][width];
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++)
                tablo[i][j] = -1;
        for (i = 0; i < koordinatlar[soruno].length; i++){
            tablo[koordinatlar[soruno][i][0] - 1][koordinatlar[soruno][i][1] - 1] = koordinatlar[soruno][i][2];
        }
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                cozum[i][j] = 0;
                yerUygun[i][j] = !gemiVarmi(i, j);
            }
        }
        for (i = 0; i < koordinatlar[soruno].length; i++){
            cozum[koordinatlar[soruno][i][0] - 1][koordinatlar[soruno][i][1] - 1] = koordinatlar[soruno][i][2];
        }
        backtrack(0);
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                deniz[i][j] = (cozum[i][j] == FENER);
            }
        }
    }
}

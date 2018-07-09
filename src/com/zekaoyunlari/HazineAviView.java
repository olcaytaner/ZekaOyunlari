package com.zekaoyunlari;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class HazineAviView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth;
    Random rand;
    boolean finished;
    int[][] cozum;
    int[][] tablo;
    private boolean[][] yerUygun;
    Bitmap diamond, digits[];
    int elmasSayisi[] = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
    int koordinatlar[][][] = {{{1, 1, 2}, {1, 5, 2}, {2, 3, 2}, {3, 1, 3}, {3, 6, 3}, {4, 4, 1}, {5, 2, 1}, {6, 4, 3}, {6, 6, 3}},
            {{1, 1, 1}, {1, 6, 1}, {2, 2, 1}, {2, 3, 4}, {3, 5, 2}, {4, 2, 2}, {4, 3, 1}, {5, 4, 3}, {5, 6, 2}, {6, 2, 1}},
            {{1, 1, 1}, {1, 3, 2}, {1, 5, 1}, {2, 2, 1}, {3, 3, 3}, {4, 6, 3}, {5, 3, 3}, {5, 4, 1}, {5, 5, 1}, {6, 2, 1}, {6, 6, 1}},
            {{1, 2, 1}, {2, 2, 1}, {2, 4, 4}, {2, 6, 1}, {3, 3, 3}, {3, 5, 4}, {4, 1, 2}, {5, 1, 2}, {5, 3, 2}, {6, 4, 2}, {6, 6, 2}},
            {{1, 2, 1}, {1, 4, 2}, {3, 1, 3}, {3, 3, 1}, {3, 5, 3}, {5, 2, 1}, {5, 4, 2}, {5, 6, 2}, {6, 4, 2}},
            {{1, 2, 2}, {2, 4, 1}, {2, 6, 2}, {3, 1, 4}, {4, 6, 3}, {5, 2, 1}, {5, 3, 4}, {6, 5, 2}},
            {{1, 1, 1}, {1, 4, 3}, {2, 3, 2}, {2, 6, 2}, {3, 1, 3}, {3, 3, 1}, {3, 6, 2}, {4, 4, 3}, {5, 6, 1}, {6, 2, 1}, {6, 5, 2}},
            {{1, 5, 2}, {2, 1, 1}, {2, 2, 2}, {3, 3, 1}, {3, 6, 2}, {4, 1, 3}, {4, 2, 4}, {4, 4, 3}, {5, 6, 2}, {6, 2, 2}, {6, 4, 3}},
            {{1, 6, 2}, {2, 1, 2}, {2, 3, 2}, {3, 5, 6}, {4, 2, 1}, {5, 3, 2}, {5, 5, 1}, {6, 2, 2}},
            {{1, 2, 2}, {1, 4, 2}, {1, 5, 1}, {2, 2, 4}, {2, 5, 1}, {3, 1, 2}, {3, 6, 2}, {4, 1, 2}, {4, 5, 1}, {5, 2, 4}, {5, 5, 1}, {6, 2, 2}, {6, 4, 2}},
            {{1, 4, 3}, {2, 1, 2}, {2, 6, 2}, {3, 3, 1}, {4, 1, 3}, {4, 4, 3}, {5, 1, 1}, {5, 5, 2}, {6, 4, 3}},
            {{1, 2, 1}, {1, 4, 2}, {1, 8, 1}, {2, 2, 4}, {2, 6, 1}, {3, 4, 1}, {3, 8, 3}, {4, 2, 3}, {5, 4, 2}, {5, 6, 1}, {6, 6, 2}, {6, 7, 4}, {7, 2, 1}, {7, 4, 2}, {8, 5, 2}, {8, 8, 1}},
            {{1, 5, 2}, {2, 1, 1}, {2, 3, 1}, {2, 7, 1}, {3, 5, 1}, {4, 1, 2}, {4, 3, 1}, {5, 7, 4}, {6, 2, 1}, {6, 5, 1}, {7, 8, 2}, {8, 1, 1}, {8, 3, 4}, {8, 6, 3}},
            {{1, 2, 1}, {1, 4, 3}, {2, 1, 1}, {2, 6, 2}, {2, 8, 2}, {3, 3, 1}, {3, 6, 1}, {4, 8, 1}, {5, 1, 5}, {5, 6, 2}, {6, 3, 2}, {6, 8, 1}, {7, 1, 2}, {7, 5, 1}, {8, 3, 1}, {8, 7, 1}},
            {{1, 2, 1}, {1, 4, 2}, {1, 6, 2}, {1, 8, 1}, {2, 4, 3}, {3, 1, 2}, {3, 3, 1}, {3, 6, 2}, {3, 7, 1}, {4, 4, 1}, {5, 2, 2}, {5, 5, 5}, {6, 2, 2}, {6, 8, 1}, {7, 4, 2}, {7, 6, 2}, {8, 2, 3}, {8, 8, 1}},
            {{1, 3, 2}, {2, 2, 2}, {2, 5, 2}, {2, 7, 2}, {3, 7, 1}, {4, 1, 4}, {4, 2, 4}, {5, 5, 3}, {5, 7, 1}, {6, 1, 3}, {6, 3, 1}, {6, 6, 1}, {8, 2, 1}, {8, 4, 2}, {8, 6, 2}, {8, 8, 1}},
            {{1, 4, 1}, {2, 2, 2}, {2, 6, 1}, {2, 8, 2}, {3, 4, 1}, {4, 2, 3}, {5, 6, 2}, {5, 7, 1}, {6, 2, 2}, {6, 5, 1}, {6, 6, 2}, {7, 1, 2}, {7, 6, 1}, {7, 8, 3}, {8, 3, 4}},
            {{1, 1, 2}, {1, 6, 2}, {2, 3, 2}, {2, 8, 1}, {3, 3, 1}, {3, 5, 3}, {4, 1, 3}, {4, 6, 1}, {4, 7, 1}, {5, 1, 1}, {5, 4, 1}, {6, 2, 2}, {6, 3, 1}, {6, 6, 2}, {6, 8, 2}, {8, 2, 1}, {8, 5, 3}, {8, 7, 1}},
            {{1, 1, 1}, {1, 4, 3}, {1, 6, 3}, {2, 8, 1}, {3, 1, 3}, {3, 3, 1}, {3, 6, 1}, {4, 6, 1}, {4, 8, 1}, {5, 1, 1}, {5, 2, 1}, {6, 5, 1}, {6, 7, 3}, {7, 2, 1}, {7, 6, 3}, {8, 1, 1}, {8, 3, 3}, {8, 8, 1}},
            {{1, 1, 2}, {1, 3, 2}, {2, 5, 1}, {2, 7, 3}, {3, 2, 1}, {3, 4, 2}, {4, 2, 2}, {4, 6, 4}, {4, 7, 1}, {6, 1, 2}, {6, 4, 1}, {6, 6, 3}, {6, 8, 3}, {7, 5, 1}, {8, 1, 1}, {8, 3, 1}, {8, 7, 1}},
            {{1, 4, 1}, {2, 2, 4}, {2, 7, 1}, {3, 4, 1}, {3, 5, 2}, {4, 2, 2}, {4, 6, 1}, {5, 4, 3}, {5, 8, 2}, {6, 1, 5}, {7, 4, 1}, {7, 6, 2}, {7, 7, 1}, {8, 2, 2}},
            {{1, 5, 1}, {1, 7, 1}, {2, 1, 2}, {2, 2, 2}, {2, 5, 2}, {2, 8, 2}, {3, 3, 1}, {3, 6, 1}, {4, 1, 1}, {4, 5, 1}, {4, 7, 1}, {5, 2, 3}, {5, 4, 2}, {5, 6, 2}, {6, 1, 2}, {6, 5, 2}, {6, 8, 1}, {7, 3, 1}, {7, 6, 3}, {8, 2, 2}, {8, 5, 1}, {8, 8, 1}},
            {{1, 2, 3}, {1, 6, 2}, {1, 8, 2}, {2, 5, 2}, {3, 1, 1}, {3, 3, 2}, {3, 7, 2}, {3, 8, 1}, {4, 1, 1}, {4, 6, 3}, {4, 8, 1}, {5, 1, 1}, {5, 2, 1}, {5, 5, 2}, {6, 2, 1}, {6, 3, 2}, {6, 8, 1}, {7, 1, 1}, {7, 5, 1}, {7, 7, 2}, {8, 1, 1}, {8, 4, 2}, {8, 6, 1}, {8, 8, 1}}};
    final int SORU_SAYISI = elmasSayisi.length;
    final int DIAMOND = -1;
    final int EMPTY = 0;

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
        Paint black = new Paint(), blue = new Paint();
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
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL_AND_STROKE);
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (tablo[i][j] == DIAMOND){
                    canvas.drawBitmap(diamond, null, new RectF((j + 1) * cellWidth, (i + 1) * cellWidth, (j + 2) * cellWidth, (i + 2) * cellWidth), null);
                }
                if (tablo[i][j] > 0){
                    drawDigit(canvas, (j + 1) * cellWidth, (i + 1) * cellWidth, tablo[i][j]);
                }
            }
    }

    public HazineAviView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = this.getContext().getResources();
        diamond = BitmapFactory.decodeResource(mRes, R.drawable.diamond);
        digits = new Bitmap[10];
        digits[0] = BitmapFactory.decodeResource(mRes, R.drawable.silver0);
        digits[1] = BitmapFactory.decodeResource(mRes, R.drawable.silver1);
        digits[2] = BitmapFactory.decodeResource(mRes, R.drawable.silver2);
        digits[3] = BitmapFactory.decodeResource(mRes, R.drawable.silver3);
        digits[4] = BitmapFactory.decodeResource(mRes, R.drawable.silver4);
        digits[5] = BitmapFactory.decodeResource(mRes, R.drawable.silver5);
        digits[6] = BitmapFactory.decodeResource(mRes, R.drawable.silver6);
        digits[7] = BitmapFactory.decodeResource(mRes, R.drawable.silver7);
        digits[8] = BitmapFactory.decodeResource(mRes, R.drawable.silver8);
        digits[9] = BitmapFactory.decodeResource(mRes, R.drawable.silver9);
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
                if (tablo[posy][posx] < 1){
                    tablo[posy][posx] = -1 - tablo[posy][posx];
                }
                invalidate();
            }
            return true;
        }
        return false;
    }

    public void newGame(){
        int i;
        finished = false;
        soruno = rand.nextInt(SORU_SAYISI);
        width = 0;
        for (i = 0; i < koordinatlar[soruno].length; i++){
            if (koordinatlar[soruno][i][0] > width){
                width = koordinatlar[soruno][i][0];
            }
            if (koordinatlar[soruno][i][1] > width){
                width = koordinatlar[soruno][i][1];
            }
        }
        tablo = new int[width][width];
        for (i = 0; i < koordinatlar[soruno].length; i++){
            tablo[koordinatlar[soruno][i][0] - 1][koordinatlar[soruno][i][1] - 1] = koordinatlar[soruno][i][2];
        }
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

    private int diamondCount(int row, int column){
        int i, j, count = 0;
        for (i = -1; i <= 1; i++){
            for (j = -1; j <= 1; j++){
                if (row + i >= 0 && row + i < width && column + j >= 0 && column + j < width){
                    if (cozum[row + i][column + j] == DIAMOND){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean isaretVarmi(int row, int column){
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

    private boolean satisfiesNow(int row, int column){
        int i, j;
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                if (i == row - 1 && j == column - 1){
                    if (cozum[i][j] > 0 && (diamondCount(i, j) < cozum[i][j] - 1 || diamondCount(i, j) > cozum[i][j])){
                        return false;
                    }
                } else {
                    if (i < row && j < column && cozum[i][j] > 0 && diamondCount(i, j) != cozum[i][j]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int constructCandidates(int k, int[] c){
        int row, column;
        row = k / width;
        column = k % width;
        if (!satisfiesNow(row, column)){
            return 0;
        } else {
            if (tablo[row][column] > 0){
                c[0] = tablo[row][column];
                return 1;
            }
            else{
                if (yerUygun[row][column] && totalDiamonds() < elmasSayisi[soruno]){
                    c[0] = DIAMOND;
                    c[1] = EMPTY;
                    return 2;
                }
                else{
                    c[0] = EMPTY;
                    return 1;
                }
            }}
    }

    private int totalDiamonds(){
        int i, j, count = 0;
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (cozum[i][j] == DIAMOND){
                    count++;
                }
            }
        return count;
    }

    private boolean satisfyConditions(){
        int i, j;
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                if (cozum[i][j] > 0 && diamondCount(i, j) != cozum[i][j]){
                    return false;
                }
            }
        }
        return totalDiamonds() == elmasSayisi[soruno];
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
        finished = false;
        yerUygun = new boolean[width][width];
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                cozum[i][j] = EMPTY;
                yerUygun[i][j] = isaretVarmi(i, j);
            }
        }
        for (i = 0; i < koordinatlar[soruno].length; i++){
            cozum[koordinatlar[soruno][i][0] - 1][koordinatlar[soruno][i][1] - 1] = koordinatlar[soruno][i][2];
        }
        backtrack(0);
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                tablo[i][j] = cozum[i][j];
            }
    }
}

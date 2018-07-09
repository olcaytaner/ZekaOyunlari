package com.zekaoyunlari;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class CadirView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth;
    Random rand;
    boolean finished;
    int[][] cozum;
    int[][] tablo;
    Bitmap cadir, agac, digits[];
    int sorular[][][] = {{{1, 2}, {1, 5}, {2, 4}, {3, 4}, {3, 5}, {4, 2}, {4, 6}, {5, 1}, {6, 4}},
            {{1, 1}, {1, 3}, {2, 6}, {4, 1}, {4, 5}, {5, 2}, {5, 4}, {6, 6}},
            {{1, 1}, {1, 3}, {1, 6}, {3, 2}, {5, 1}, {5, 3}, {5, 5}, {6, 2}, {6, 5}},
            {{1, 4}, {1, 6}, {2, 1}, {2, 2}, {3, 4}, {3, 6}, {5, 2}, {5, 3}, {6, 6}},
            {{1, 1}, {1, 3}, {1, 6}, {2, 2}, {3, 3}, {4, 1}, {4, 6}, {5, 5}},
            {{2, 1}, {2, 4}, {2, 5}, {2, 6}, {3, 2}, {4, 4}, {4, 6}, {5, 2}, {6, 5}},
            {{1, 2}, {1, 6}, {2, 1}, {2, 4}, {5, 4}, {5, 5}, {6, 1}, {6, 6}},
            {{1, 2}, {2, 5}, {2, 6}, {3, 3}, {4, 4}, {5, 1}, {5, 2}, {6, 5}},
            {{1, 2}, {1, 5}, {2, 1}, {2, 4}, {3, 3}, {5, 2}, {5, 5}, {6, 4}},
            {{1, 3}, {2, 2}, {2, 5}, {3, 1}, {4, 4}, {5, 2}, {5, 6}, {6, 1}, {6, 4}},
            {{1, 1}, {1, 5}, {2, 4}, {2, 6}, {2, 8}, {3, 2}, {3, 6}, {4, 3}, {4, 5}, {4, 7}, {7, 4}, {8, 1}, {8, 6}, {8, 7}},
            {{1, 5}, {2, 2}, {2, 3}, {2, 7}, {3, 4}, {3, 6}, {4, 1}, {4, 4}, {5, 2}, {5, 5}, {6, 7}, {7, 1}, {7, 6}, {7, 8}, {8, 4}},
            {{1, 5}, {1, 7}, {1, 8}, {2, 1}, {3, 2}, {4, 4}, {4, 6}, {4, 7}, {5, 4}, {6, 2}, {7, 1}, {7, 6}, {7, 7}, {8, 2}, {8, 4}, {8, 8}},
            {{1, 1}, {1, 3}, {1, 5}, {1, 7}, {3, 2}, {3, 4}, {3, 6}, {4, 1}, {5, 4}, {5, 7}, {7, 2}, {7, 4}, {7, 7}, {8, 1}},
            {{1, 3}, {2, 2}, {2, 7}, {3, 3}, {3, 6}, {4, 5}, {5, 4}, {5, 8}, {6, 3}, {7, 2}, {7, 7}, {8, 1}, {8, 6}, {8, 8}},
            {{1, 1}, {1, 6}, {1, 8}, {2, 1}, {2, 3}, {3, 5}, {4, 1}, {4, 2}, {4, 7}, {6, 3}, {6, 7}, {7, 6}, {8, 1}, {8, 5}, {8, 8}},
            {{1, 2}, {1, 5}, {1, 8}, {2, 4}, {3, 7}, {4, 1}, {4, 5}, {4, 6}, {6, 1}, {6, 4}, {6, 7}, {7, 2}, {8, 5}},
            {{1, 2}, {1, 8}, {2, 3}, {2, 6}, {4, 1}, {4, 5}, {4, 7}, {5, 3}, {6, 1}, {6, 6}, {6, 8}, {7, 4}, {7, 6}, {8, 2}, {8, 7}}};
    String hints[][] = {{"2__2_3", "3___1_"}, {"_2_3__", "111_1_"}, {"3_____", "12___3"}, {"__2_2_", "_0__1_"}, {"______", "______"},
            {"2_1___", "3___1_"}, {"____3_", "2___2_"}, {"1____1", "_____2"}, {"_____1", "1_____"}, {"______", "______"},
            {"__2__2__", "4_______"}, {"__2_____", "3____21_"}, {"2_2_2_2_", "_222__1_"}, {"1______1", "_____3__"}, {"________", "________"},
            {"3113__13", "131313__"}, {"__1___1_", "__1___1_"}, {"____2__1", "3_12_3__"}};
    final int SORU_SAYISI = sorular.length;
    final int EMPTY = 0;
    final int AGAC = 1;
    final int CADIR = 2;

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
            if (hints[soruno][1].charAt(i) != '_'){
                drawDigit(canvas, (width + 1) * cellWidth, (i + 1) * cellWidth, hints[soruno][1].charAt(i) - '0');
            }
            if (hints[soruno][0].charAt(i) != '_'){
                drawDigit(canvas, (i + 1) * cellWidth, (width + 1) * cellWidth, hints[soruno][0].charAt(i) - '0');
            }
        }
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (tablo[i][j] != EMPTY){
                    if (tablo[i][j] == CADIR){
                        canvas.drawBitmap(cadir, null, new RectF((j + 1) * cellWidth + 1, (i + 1) * cellWidth + 1, (j + 2) * cellWidth - 1, (i + 2) * cellWidth - 1), null);
                    }
                    if (tablo[i][j] == AGAC){
                        canvas.drawBitmap(agac, null, new RectF((j + 1) * cellWidth + 1, (i + 1) * cellWidth + 1, (j + 2) * cellWidth - 1, (i + 2) * cellWidth - 1), null);
                    }
                }
            }
    }

    public CadirView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = this.getContext().getResources();
        cadir = BitmapFactory.decodeResource(mRes, R.drawable.tent);
        agac = BitmapFactory.decodeResource(mRes, R.drawable.tree);
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
            if (posx >= 0 && posx < width && posy >= 0 && posy < width){
                if (tablo[posy][posx] != AGAC){
                    if (tablo[posy][posx] == CADIR)
                        tablo[posy][posx] = EMPTY;
                    else
                        tablo[posy][posx] = CADIR;
                    invalidate();
                }
            }
            return true;
        }
        return false;
    }

    public void newGame(){
        int i;
        finished = false;
        soruno = rand.nextInt(SORU_SAYISI);
        width = hints[soruno][0].length();
        tablo = new int[width][width];
        cozum = new int[width][width];
        for (i = 0; i < sorular[soruno].length; i++)
            tablo[sorular[soruno][i][0] - 1][sorular[soruno][i][1] - 1] = AGAC;
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

    private boolean agacVarmi(int row, int column){
        if (row - 1 >= 0 && tablo[row - 1][column] == AGAC){
            return true;
        }
        if (column - 1 >= 0 && tablo[row][column - 1] == AGAC){
            return true;
        }
        if (row + 1 < width && tablo[row + 1][column] == AGAC){
            return true;
        }
        if (column + 1 < width && tablo[row][column + 1] == AGAC){
            return true;
        }
        return false;
    }

    private boolean agacEtrafindaCadirVarmi(int row, int column){
        if (row - 1 >= 0 && cozum[row - 1][column] == CADIR){
            return true;
        }
        if (column - 1 >= 0 && cozum[row][column - 1] == CADIR){
            return true;
        }
        if (row + 1 < width && cozum[row + 1][column] == CADIR){
            return true;
        }
        if (column + 1 < width && cozum[row][column + 1] == CADIR){
            return true;
        }
        return false;
    }

    private boolean cadirEtrafindaCadirVarmi(int row, int column){
        if (row - 1 >= 0 && cozum[row - 1][column] == CADIR){
            return true;
        }
        if (column - 1 >= 0 && cozum[row][column - 1] == CADIR){
            return true;
        }
        if (row + 1 < width && cozum[row + 1][column] == CADIR){
            return true;
        }
        if (column + 1 < width && cozum[row][column + 1] == CADIR){
            return true;
        }
        if (row - 1 >= 0 && column - 1 >= 0 && cozum[row - 1][column - 1] == CADIR){
            return true;
        }
        if (row - 1 >= 0 && column + 1 < width && cozum[row - 1][column + 1] == CADIR){
            return true;
        }
        if (row + 1 < width && column - 1 >= 0 && cozum[row + 1][column - 1] == CADIR){
            return true;
        }
        if (row + 1 < width && column + 1 < width && cozum[row + 1][column + 1] == CADIR){
            return true;
        }
        return false;
    }

    private int constructCandidates(int k, int[] c){
        int row, column;
        row = k / width;
        column = k % width;
        if (tablo[row][column] == AGAC){
            c[0] = AGAC;
            return 1;
        } else {
            if (cadirCount() > agacCount()){
                return 0;
            }
            if (column == 0 && row > 0 && hints[soruno][1].charAt(row - 1) != '_' && rowCount(row - 1) != hints[soruno][1].charAt(row - 1) - '0'){
                return 0;
            }
            if (hints[soruno][1].charAt(row) != '_' && rowCount(row) > hints[soruno][1].charAt(row) - '0'){
                return 0;
            }
            if (hints[soruno][0].charAt(column) != '_' && columnCount(column) > hints[soruno][0].charAt(column) - '0'){
                return 0;
            }
            if (!agacVarmi(row, column) || cadirEtrafindaCadirVarmi(row, column)){
                c[0] = EMPTY;
                return 1;
            } else {
                c[0] = CADIR;
                c[1] = EMPTY;
                return 2;
            }
        }
    }

    private int cadirCount(){
        int i, j, count = 0;
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++)
                if (cozum[i][j] == CADIR)
                    count++;
        return count;
    }

    private int agacCount(){
        int i, j, count = 0;
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++)
                if (cozum[i][j] == AGAC)
                    count++;
        return count;
    }

    private int rowCount(int row){
        int i, count = 0;
        for (i = 0; i < width; i++){
            if (cozum[row][i] == CADIR){
                count++;
            }
        }
        return count;
    }

    private int columnCount(int column){
        int i, count = 0;
        for (i = 0; i < width; i++){
            if (cozum[i][column] == CADIR){
                count++;
            }
        }
        return count;
    }

    private boolean satisfyConditions(){
        int i, j;
        if (agacCount() != cadirCount())
            return false;
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (cozum[i][j] == CADIR && (!agacVarmi(i, j) || cadirEtrafindaCadirVarmi(i, j)))
                    return false;
                if (cozum[i][j] == AGAC && !agacEtrafindaCadirVarmi(i, j))
                    return false;
            }
        for (i = 0; i < width; i++){
            if (hints[soruno][0].charAt(i) != '_'){
                if (columnCount(i) != hints[soruno][0].charAt(i) - '0')
                    return false;
            }
            if (hints[soruno][1].charAt(i) != '_'){
                if (rowCount(i) != hints[soruno][1].charAt(i) - '0')
                    return false;
            }
        }
        return true;
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
            if (tablo[row][column] == AGAC)
                cozum[row][column] = AGAC;
            else
                cozum[row][column] = EMPTY;
        }
    }

    public void solve(){
        int i, j;
        finished = false;
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                if (tablo[i][j] == AGAC)
                    cozum[i][j] = AGAC;
                else
                    cozum[i][j] = EMPTY;
            }
        }
        backtrack(0);
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                tablo[i][j] = cozum[i][j];
            }
    }
}

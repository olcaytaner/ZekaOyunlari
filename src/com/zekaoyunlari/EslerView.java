package com.zekaoyunlari;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class EslerView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth;
    Random rand;
    boolean finished;
    int[][] cozum;
    int[][] tablo;
    int[] right;
    int[] bottom;
    Bitmap male, female, digits[];
    private boolean[][] yerUygun;
    String sorular[][] = {{"40403122", "40310404"}, {"31220404", "22131313"}, {"222041322", "223130313"}, {"223031304", "404113122"}, {"3231123214", "3140405032"}, {"4031405032", "3140140504"}};
    int koordinatlar[][][] = {{{1, 4}, {1, 5}, {1, 7}, {2, 1}, {2, 8}, {3, 2}, {3, 5}, {4, 1}, {5, 2}, {5, 4}, {5, 7}, {6, 8}, {8, 1}, {8, 2}, {8, 7}, {8, 8}},
            {{1, 8}, {2, 2}, {2, 3}, {2, 5}, {3, 3}, {3, 6}, {4, 2}, {4, 8}, {5, 4}, {5, 8}, {6, 5}, {7, 2}, {7, 8}, {8, 1}, {8, 3}, {8, 5}},
            {{1, 1}, {1, 9}, {2, 4}, {2, 8}, {3, 1}, {3, 4}, {4, 3}, {4, 9}, {5, 5}, {6, 1}, {6, 7}, {7, 1}, {7, 6}, {8, 3}, {8, 9}, {9, 4}, {9, 5}, {9, 9}},
            {{1, 3}, {2, 5}, {2, 9}, {3, 1}, {3, 7}, {4, 1}, {4, 3}, {4, 9}, {5, 4}, {5, 5}, {5, 8}, {7, 2}, {7, 4}, {7, 5}, {8, 1}, {8, 8}, {9, 4}, {9, 7}},
            {{1, 4}, {1, 5}, {1, 8}, {1, 9}, {2, 9}, {3, 1}, {3, 2}, {3, 6}, {5, 5}, {6, 2}, {6, 5}, {6, 6}, {6, 10}, {7, 2}, {8, 1}, {8, 6}, {8, 10}, {9, 1}, {9, 5}, {10, 3}, {10, 8}, {10, 9}},
            {{1, 1}, {1, 5}, {2, 8}, {2, 10}, {3, 1}, {3, 4}, {3, 9}, {4, 6}, {4, 8}, {4, 10}, {5, 4}, {5, 5}, {6, 6}, {7, 4}, {8, 1}, {8, 8}, {8, 10}, {9, 1}, {9, 2}, {10, 5}, {10, 7}, {10, 10}}};
    final int SORU_SAYISI = sorular.length;
    final int EMPTY = 0;
    final int MALE = 1;
    final int FEMALE = 2;

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
        Paint black = new Paint(), blue = new Paint(), red = new Paint();
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
            drawDigit(canvas, (width + 1) * cellWidth, (i + 1) * cellWidth, right[i]);
            drawDigit(canvas, (i + 1) * cellWidth, (width + 1) * cellWidth, bottom[i]);
        }
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL_AND_STROKE);
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL_AND_STROKE);
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (tablo[i][j] == MALE){
                    canvas.drawBitmap(male, null, new RectF((j + 1) * cellWidth + 1, (i + 1) * cellWidth + 1, (j + 2) * cellWidth - 1, (i + 2) * cellWidth - 1), null);
                }
                if (tablo[i][j] == FEMALE){
                    canvas.drawBitmap(female, null, new RectF((j + 1) * cellWidth + 1, (i + 1) * cellWidth + 1, (j + 2) * cellWidth - 1, (i + 2) * cellWidth - 1), null);
                }
            }
    }

    public EslerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = this.getContext().getResources();
        male = BitmapFactory.decodeResource(mRes, R.drawable.male);
        female = BitmapFactory.decodeResource(mRes, R.drawable.female);
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
                if (tablo[posy][posx] != MALE){
                    tablo[posy][posx] = 2 - tablo[posy][posx];
                }
                invalidate();
            }
            return true;
        }
        return false;
    }

    public void newGame(){
        finished = false;
        soruno = rand.nextInt(SORU_SAYISI);
        width = sorular[soruno][0].length();
        right = new int[width];
        bottom = new int[width];
        for (int i = 0; i < width; i++){
            right[i] = sorular[soruno][0].charAt(i) - '0';
            bottom[i] = sorular[soruno][1].charAt(i) - '0';
        }
        tablo = new int[width][width];
        for (int i = 0; i < koordinatlar[soruno].length; i++){
            tablo[koordinatlar[soruno][i][0] - 1][koordinatlar[soruno][i][1] - 1] = MALE;
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

    private int rowCount(int row){
        int i, count = 0;
        for (i = 0; i < width; i++){
            if (cozum[row][i] == FEMALE){
                count++;
            }
        }
        return count;
    }

    private int rowCount(int row, int column){
        int i, count = 0;
        for (i = 0; i < column; i++){
            if (cozum[row][i] == FEMALE){
                count++;
            }
        }
        return count;
    }

    private int columnCount(int column){
        int i, count = 0;
        for (i = 0; i < width; i++){
            if (cozum[i][column] == FEMALE){
                count++;
            }
        }
        return count;
    }

    private int columnCount(int column, int row){
        int i, count = 0;
        for (i = 0; i < row; i++){
            if (cozum[i][column] == FEMALE){
                count++;
            }
        }
        return count;
    }

    private boolean bayVarmi(int row, int column){
        if (row - 1 >= 0 && tablo[row - 1][column] == MALE){
            return true;
        }
        if (column - 1 >= 0 && tablo[row][column - 1] == MALE){
            return true;
        }
        if (row + 1 < width && tablo[row + 1][column] == MALE){
            return true;
        }
        if (column + 1 < width && tablo[row][column + 1] == MALE){
            return true;
        }
        return false;
    }

    private boolean onundeBayanVarmi(int row, int column){
        if (row - 1 >= 0 && cozum[row - 1][column] == FEMALE){
            return true;
        }
        if (column - 1 >= 0 && cozum[row][column - 1] == FEMALE){
            return true;
        }
        if (row - 1 >= 0 && column - 1 >= 0 && cozum[row - 1][column - 1] == FEMALE){
            return true;
        }
        if (row - 1 >= 0 && column + 1 < width && cozum[row - 1][column + 1] == FEMALE){
            return true;
        }
        return false;
    }

    private boolean bayanVarmi(int row, int column){
        if (row - 1 >= 0 && cozum[row - 1][column] == FEMALE){
            return true;
        }
        if (column - 1 >= 0 && cozum[row][column - 1] == FEMALE){
            return true;
        }
        if (row + 1 < width && cozum[row + 1][column] == FEMALE){
            return true;
        }
        if (column + 1 < width && cozum[row][column + 1] == FEMALE){
            return true;
        }
        return false;
    }

    private int constructCandidates(int k, int[] c){
        int row, column;
        row = k / width;
        column = k % width;
        if (tablo[row][column] == MALE){
            c[0] = MALE;
            return 1;
        }
        else{
            if (yerUygun[row][column] && !onundeBayanVarmi(row, column) && rowCount(row, column) < right[row] && columnCount(column, row) < bottom[column]){
                if (column == width - 1){
                    if (rowCount(row, column) < right[row] - 1){
                        return 0;
                    }
                }
                c[0] = FEMALE;
                c[1] = EMPTY;
                return 2;
            }
            else{
                c[0] = 0;
                return 1;
            }
        }
    }

    private boolean satisfyConditions(){
        int i, j;
        for (i = 0; i < width; i++){
            if (rowCount(i) != right[i]){
                return false;
            }
            if (columnCount(i) != bottom[i]){
                return false;
            }
        }
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                if (cozum[i][j] == 1 && !bayanVarmi(i, j)){
                    return false;
                }
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
        }
    }
    
    public void solve(){
        int i, j;
        finished = false;
        yerUygun = new boolean[width][width];
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                cozum[i][j] = -1;
                yerUygun[i][j] = bayVarmi(i, j);
            }
        }
        backtrack(0);
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                tablo[i][j] = cozum[i][j];
            }
    }
}

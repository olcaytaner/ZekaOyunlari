package com.zekaoyunlari;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class HarflerView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth;
    char[][] board;
    Random rand;
    boolean finished;
    int[][] cozum;
    int[] top;
    int[] bottom;
    int[] left;
    int[] right;
    Bitmap letters[];
    Bitmap silverLetters[];
    String sorular[][] = {{"A___", "__B_", "A___", "_A_C"}, {"____", "A_A_", "C__C_", "__B_"}, {"_D_D_", "___BA", "C____", "__B__"},
            {"_A_C_", "__B__", "_B_A_", "D_BB_"}, {"_E_AD_", "DBD__E", "_C____", "_ABC__"}, {"_E__DA", "BDBE_C", "_B___E", "__C___"},
            {"_A__", "BB__", "_C__", "___B"}, {"__C_", "C___", "____", "___B"}, {"_BC_A", "_D_C_", "_C___", "C_AB_"},
            {"_D_D_", "_AC__", "B__A_", "__A_D"}, {"_EDC__", "BB_C__", "_C_AB_", "_CEB_B"}, {"C_EA_A", "___AEC", "__DCA_", "___E__"},
            {"CABB", "CCAB", "BACC", "BBAC"}, {"ABAC", "BABC", "CBCA", "CABA"}, {"BCAB", "CBCA", "BABC", "ABBC"},
            {"CACB", "ACAB", "BACA", "BCBA"}, {"CBCA", "CABA", "ABAC", "ACBC"}, {"ACCB", "ABBC", "BCCA", "CBBA"}};
    final int SORU_SAYISI = sorular.length;
    final int EMPTY = 0;

    public void drawLetter(Canvas canvas, int x, int y, int index){
        int W, H, w = letters[index].getWidth(), h = letters[index].getHeight(), l = cellWidth / 2;
        if (h > w){
            W = w * l / h;
            canvas.drawBitmap(letters[index], null, new RectF(x + (cellWidth - l) / 2 + l / 2 - W / 2, y + (cellWidth - l) / 2, x + (cellWidth - l) / 2 + l / 2 + W / 2, y + (cellWidth + l) / 2), null);
        } else {
            H = h * l / w;
            canvas.drawBitmap(letters[index], null, new RectF(x + (cellWidth - l) / 2, y + (cellWidth - l) / 2 + l / 2 - H / 2, x + (cellWidth + l) / 2, y + (cellWidth - l) / 2 + l / 2 + H / 2), null);
        }
    }

    public void drawSilverLetter(Canvas canvas, int x, int y, int index){
        int W, H, w = silverLetters[index].getWidth(), h = silverLetters[index].getHeight(), l = cellWidth / 2;
        if (h > w){
            W = w * l / h;
            canvas.drawBitmap(silverLetters[index], null, new RectF(x + (cellWidth - l) / 2 + l / 2 - W / 2, y + (cellWidth - l) / 2, x + (cellWidth - l) / 2 + l / 2 + W / 2, y + (cellWidth + l) / 2), null);
        } else {
            H = h * l / w;
            canvas.drawBitmap(silverLetters[index], null, new RectF(x + (cellWidth - l) / 2, y + (cellWidth - l) / 2 + l / 2 - H / 2, x + (cellWidth + l) / 2, y + (cellWidth - l) / 2 + l / 2 + H / 2), null);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        Paint black = new Paint();
        int i, j;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        black.setTextSize(cellWidth / 2);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < width + 1; i++){
            canvas.drawLine((i + 1) * cellWidth, cellWidth, (i + 1) * cellWidth, (width + 1) * cellWidth, black);
            canvas.drawLine(cellWidth, (i + 1) * cellWidth, (width + 1) * cellWidth, (i + 1) * cellWidth, black);
        }
        for (i = 0; i < width; i++){
            if (sorular[soruno][0].charAt(i) != '_'){
                drawLetter(canvas, (i + 1) * cellWidth, 0, sorular[soruno][0].charAt(i) - 'A');
            }
            if (sorular[soruno][1].charAt(i) != '_'){
                drawLetter(canvas, 0, (i + 1) * cellWidth, sorular[soruno][1].charAt(i) - 'A');
            }
            if (sorular[soruno][2].charAt(i) != '_'){
                drawLetter(canvas, (width + 1) * cellWidth, (i + 1) * cellWidth, sorular[soruno][2].charAt(i) - 'A');
            }
            if (sorular[soruno][3].charAt(i) != '_'){
                drawLetter(canvas, (i + 1) * cellWidth, (width + 1) * cellWidth, sorular[soruno][3].charAt(i) - 'A');
            }
        }
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (board[i][j] != EMPTY){
                    drawSilverLetter(canvas, (i + 1) * cellWidth, (j + 1) * cellWidth, board[i][j] - 'A');
                }
            }
    }

    public HarflerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = this.getContext().getResources();
        rand = new Random();
        letters = new Bitmap[5];
        letters[0] = BitmapFactory.decodeResource(mRes, R.drawable.a);
        letters[1] = BitmapFactory.decodeResource(mRes, R.drawable.b);
        letters[2] = BitmapFactory.decodeResource(mRes, R.drawable.c);
        letters[3] = BitmapFactory.decodeResource(mRes, R.drawable.d);
        letters[4] = BitmapFactory.decodeResource(mRes, R.drawable.e);
        silverLetters = new Bitmap[5];
        silverLetters[0] = BitmapFactory.decodeResource(mRes, R.drawable.silvera);
        silverLetters[1] = BitmapFactory.decodeResource(mRes, R.drawable.silverb);
        silverLetters[2] = BitmapFactory.decodeResource(mRes, R.drawable.silverc);
        silverLetters[3] = BitmapFactory.decodeResource(mRes, R.drawable.silverd);
        silverLetters[4] = BitmapFactory.decodeResource(mRes, R.drawable.silvere);
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
                switch (board[posx][posy]){
                    case EMPTY:
                        board[posx][posy] = 'A';
                        break;
                    case 'A':
                        board[posx][posy] = 'B';
                        break;
                    case 'B':
                        board[posx][posy] = 'C';
                        break;
                    case 'C':
                        if (width > 4)
                            board[posx][posy] = 'D';
                        else
                            board[posx][posy] = EMPTY;
                        break;
                    case 'D':
                        if (width > 5)
                            board[posx][posy] = 'E';
                        else
                            board[posx][posy] = EMPTY;
                        break;
                    case 'E':
                        board[posx][posy] = EMPTY;
                        break;
                }
                invalidate();
            }
            return true;
        }
        return false;
    }
    
    public void setHint(int[] array, int pos, char ch){
        switch (ch){
            case '_':
                array[pos] = -1;
                break;
            case 'A':
                array[pos] = 0;
                break;
            case 'B':
                array[pos] = 1;
                break;
            case 'C':
                array[pos] = 2;
                break;
            case 'D':
                array[pos] = 3;
                break;
            case 'E':
                array[pos] = 4;
                break;
        }
    }

    public void newGame(){
        int i;
        soruno = rand.nextInt(SORU_SAYISI);
        width = sorular[soruno][0].length();
        board = new char[width][width];
        cellWidth = boardWidth / (width + 2);
        finished = false;
        top = new int[width];
        bottom = new int[width];
        left = new int[width];
        right = new int[width];
        for (i = 0; i < width; i++){
            setHint(top, i, sorular[soruno][0].charAt(i));
            setHint(left, i, sorular[soruno][1].charAt(i));
            setHint(right, i, sorular[soruno][2].charAt(i));
            setHint(bottom, i, sorular[soruno][3].charAt(i));
        }
    }

    private int constructCandidates(int k, int[] c){
        int i, j, ncandidates = 0, row, column;
        boolean foundColumn, foundRow;
        row = k / width;
        column = k % width;
        for (i = -1; i < width - 1; i++){
            foundColumn = false;
            for (j = 0; j < column; j++){
                if (cozum[row][j] == i){
                    foundColumn = true;
                    break;
                }
            }
            if (!foundColumn){
                foundRow = false;
                for (j = 0; j < row; j++){
                    if (cozum[j][column] == i){
                        foundRow = true;
                        break;
                    }
                }
                if (!foundRow){
                    if (i != -1){
                        if (top[row] != -1 && i != top[row]){
                            if (column == 0){
                                continue;
                            }
                            if (column == 1 && cozum[row][0] == -1){
                                continue;
                            }
                        }
                        if (bottom[row] != -1 && i != bottom[row] && column == width - 1){
                            continue;
                        }
                        if (left[column] != -1 && i != left[column]){
                            if (row == 0){
                                continue;
                            }
                            if (row == 1 && cozum[0][column] == -1){
                                continue;
                            }
                        }
                        if (right[column] != -1 && i != right[column] && row == width - 1){
                            continue;
                        }
                    }
                    else{
                        if (bottom[row] != -1 && cozum[row][width - 2] != bottom[row] && column == width - 1){
                            continue;
                        }
                        if (right[column] != -1 && cozum[width - 2][column] != right[column] && row == width - 1){
                            continue;
                        }
                    }
                    c[ncandidates] = i;
                    ncandidates++;
                }
            }
        }
        return ncandidates;
    }

    private void backtrack(int k){
        int i, ncandidates, row, column;
        int[] c;
        c = new int[width];
        if (k == width * width){
            finished = true;
        }
        else{
            ncandidates = constructCandidates(k, c);
            for (i = 0; i < ncandidates; i++){
                row = k / width;
                column = k % width;
                cozum[row][column] = c[i];
                backtrack(k + 1);
                if (finished){
                    return;
                }
            }
        }
    }

    public char firstInRow(int index){
        if (board[0][index] == EMPTY)
            return board[1][index];
        return board[0][index];
    }

    public char firstInColumn(int index){
        if (board[index][0] == EMPTY)
            return board[index][1];
        return board[index][0];
    }

    public char lastInRow(int index){
        if (board[width - 1][index] == EMPTY)
            return board[width - 2][index];
        return board[width - 1][index];
    }

    public char lastInColumn(int index){
        if (board[index][width - 1] == EMPTY)
            return board[index][width - 2];
        return board[index][width - 1];
    }

    public boolean check(){
        int i;
        for (i = 0; i < width; i++){
            if (sorular[soruno][0].charAt(i) != '_' && sorular[soruno][0].charAt(i) != firstInColumn(i)){
                return false;
            }
            if (sorular[soruno][1].charAt(i) != '_' && sorular[soruno][1].charAt(i) != firstInRow(i)){
                return false;
            }
            if (sorular[soruno][2].charAt(i) != '_' && sorular[soruno][2].charAt(i) != lastInRow(i)){
                return false;
            }
            if (sorular[soruno][3].charAt(i) != '_' && sorular[soruno][3].charAt(i) != lastInColumn(i)){
                return false;
            }
        }
        return true;
    }

    public void checkSolution(){
        String sonuc;
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        if (!check()){
            sonuc = getContext().getString(R.string.lose);
        } else {
            sonuc = getContext().getString(R.string.win);
        }
        Toast toast = Toast.makeText(context, sonuc, duration);
        toast.show();
    }

    public void solve(){
        int i, j;
        cozum = new int[width][width];
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                cozum[i][j] = -1;
            }
        }
        backtrack(0);
        for (i = 0; i < width; i++){
            for (j = 0; j < width; j++){
                switch (cozum[i][j]){
                    case -1:
                        board[i][j] = EMPTY;
                        break;
                    case 0:
                        board[i][j] = 'A';
                        break;
                    case 1:
                        board[i][j] = 'B';
                        break;
                    case 2:
                        board[i][j] = 'C';
                        break;
                    case 3:
                        board[i][j] = 'D';
                        break;
                    case 4:
                        board[i][j] = 'E';
                        break;
                }
            }
        }
    }

}

package com.zekaoyunlari;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class CiceklerView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth;
    Random rand;
    boolean finished;
    int[][] cozum;
    int[][] tablo;
    Bitmap cicek, digits[], silverDigits[];
    String hints[][] = {{"211112", "410120"}, {"023120", "041030"}, {"2024001", "1311111"}, {"3012111", "0115110"}, {"23020111", "02010124"}, {"10121104", "31201012"}};
    String sorular[][] = {{"012343", "012343", "110011", "331000", "331000", "221000"}, {"123321", "134421", "134421", "133311", "122211", "122211"},
            {"1222111", "1333111", "1233211", "0133200", "0033300", "1122200", "1111100"}, {"1100000", "1111100", "2223432", "2223432", "2323332", "1211000", "0111000"},
            {"11001110", "11001110", "22101110", "11100000", "11211000", "11211011", "33422122", "33311122"}, {"12221011", "12222132", "00112121", "00112121", "00111000", "00111011", "00011122", "00011122"}};
    final int SORU_SAYISI = sorular.length;
    final int EMPTY = 0;
    final int CICEK = 1;

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

    public void drawSilverDigit(Canvas canvas, int x, int y, int digit){
        int W, H, w = silverDigits[digit].getWidth(), h = silverDigits[digit].getHeight(), l = cellWidth / 2;
        if (h > w){
            W = w * l / h;
            canvas.drawBitmap(silverDigits[digit], null, new RectF(x + (cellWidth - l) / 2 + l / 2 - W / 2, y + (cellWidth - l) / 2, x + (cellWidth - l) / 2 + l / 2 + W / 2, y + (cellWidth + l) / 2), null);
        } else {
            H = h * l / w;
            canvas.drawBitmap(silverDigits[digit], null, new RectF(x + (cellWidth - l) / 2, y + (cellWidth - l) / 2 + l / 2 - H / 2, x + (cellWidth + l) / 2, y + (cellWidth - l) / 2 + l / 2 + H / 2), null);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        String s;
        Rect bounds = new Rect();
        Paint black = new Paint(), smallBlack = new Paint();
        int i, j;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.STROKE);
        black.setTextSize(cellWidth / 2);
        smallBlack.setColor(Color.BLACK);
        smallBlack.setStyle(Paint.Style.STROKE);
        smallBlack.setTextSize(cellWidth / 3);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < width + 1; i++){
            canvas.drawLine((i + 1) * cellWidth, cellWidth, (i + 1) * cellWidth, (width + 1) * cellWidth, black);
            canvas.drawLine(cellWidth, (i + 1) * cellWidth, (width + 1) * cellWidth, (i + 1) * cellWidth, black);
        }
        for (i = 0; i < width; i++){
            drawDigit(canvas, (i + 1) * cellWidth, 0, hints[soruno][0].charAt(i) - '0');
            drawDigit(canvas, 0, (i + 1) * cellWidth, hints[soruno][1].charAt(i) - '0');
        }
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                if (tablo[i][j] == CICEK){
                    canvas.drawBitmap(cicek, null, new RectF((j + 1) * cellWidth + 1, (i + 1) * cellWidth + 1, (j + 2) * cellWidth - 1, (i + 2) * cellWidth - 1), null);
                    s = "" + sorular[soruno][i].charAt(j);
                    smallBlack.getTextBounds(s, 0, s.length(), bounds);
                    canvas.drawText(s, (j + 2) * cellWidth - bounds.width() / 2 - 4, (i + 2) * cellWidth - bounds.height() / 2, smallBlack);
                } else {
                    drawSilverDigit(canvas, (j + 1) * cellWidth, (i + 1) * cellWidth, sorular[soruno][i].charAt(j) - '0');
                }
            }
    }

    public CiceklerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = context.getResources();
        rand = new Random();
        cicek = BitmapFactory.decodeResource(mRes, R.drawable.flower);
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
        silverDigits = new Bitmap[10];
        silverDigits[0] = BitmapFactory.decodeResource(mRes, R.drawable.silver0);
        silverDigits[1] = BitmapFactory.decodeResource(mRes, R.drawable.silver1);
        silverDigits[2] = BitmapFactory.decodeResource(mRes, R.drawable.silver2);
        silverDigits[3] = BitmapFactory.decodeResource(mRes, R.drawable.silver3);
        silverDigits[4] = BitmapFactory.decodeResource(mRes, R.drawable.silver4);
        silverDigits[5] = BitmapFactory.decodeResource(mRes, R.drawable.silver5);
        silverDigits[6] = BitmapFactory.decodeResource(mRes, R.drawable.silver6);
        silverDigits[7] = BitmapFactory.decodeResource(mRes, R.drawable.silver7);
        silverDigits[8] = BitmapFactory.decodeResource(mRes, R.drawable.silver8);
        silverDigits[9] = BitmapFactory.decodeResource(mRes, R.drawable.silver9);
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
                tablo[posy][posx] = 1 - tablo[posy][posx];
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

    private int cicekCount(int row, int column){
        int i, j, count = 0;
        for (i = -1; i <= 1; i++){
            for (j = -1; j <= 1; j++){
                if (row + i >= 0 && row + i < width && column + j >= 0 && column + j < width){
                    if (cozum[row + i][column + j] == CICEK){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean cicekKonabilirMi(int row, int column){
        int i, j;
        for (i = -1; i <= 1; i++){
            for (j = -1; j <= 1; j++){
                if (row + i >= 0 && row + i < width && column + j >= 0 && column + j < width){
                    if (cicekCount(row + i, column + j) >= sorular[soruno][row + i].charAt(column + j) - '0'){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int rowCount(int row){
        int i, count = 0;
        for (i = 0; i < width; i++){
            if (cozum[row][i] == CICEK){
                count++;
            }
        }
        return count;
    }

    private int columnCount(int column){
        int i, count = 0;
        for (i = 0; i < width; i++){
            if (cozum[i][column] == CICEK){
                count++;
            }
        }
        return count;
    }

    private int constructCandidates(int k, int[] c){
        int row, column;
        row = k / width;
        column = k % width;
        if (rowCount(row) > hints[soruno][1].charAt(row) - '0'){
            return 0;
        }
        if (columnCount(column) > hints[soruno][0].charAt(column) - '0'){
            return 0;
        }
        if (row > 0 && column > 1 && cicekCount(row - 1, column - 2) != sorular[soruno][row - 1].charAt(column - 2) - '0'){
            return 0;
        }
        if (cicekKonabilirMi(row, column) && rowCount(row) < hints[soruno][1].charAt(row) - '0' && columnCount(column) < hints[soruno][0].charAt(column) - '0'){
            c[0] = CICEK;
            c[1] = EMPTY;
            return 2;
        } else {
            c[0] = EMPTY;
            return 1;
        }
    }

    private boolean satisfyConditions(){
        int i, j;
        for (i = 0; i < width; i++){
            if (rowCount(i) > hints[soruno][1].charAt(i) - '0'){
                return false;
            }
            if (columnCount(i) > hints[soruno][0].charAt(i) - '0'){
                return false;
            }
            for (j = 0; j < width; j++)
                if (cicekCount(i, j) != sorular[soruno][i].charAt(j) - '0')
                    return false;
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
            cozum[row][column] = EMPTY;
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

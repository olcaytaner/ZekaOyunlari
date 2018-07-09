package com.zekaoyunlari;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class SatrancTaslariView extends View{

    int boardWidth = 300;
    int soruno, cellWidth, fromPiece, moveX, moveY;
    Random rand;
    boolean finished;
    boolean[] from;
    boolean moving;
    int[] cozum;
    int[] tablo;
    int[][] yerUygun;
    Bitmap king, rook, bishop, knight, queen, digits[];
    int yerlestirilecekYerler[][][] = {{{1, 6}, {3, 1}, {3, 4}, {4, 1}, {4, 4}, {5, 7}, {6, 1}, {6, 5}},
            {{1, 3}, {1, 7}, {4, 3}, {5, 6}, {5, 8}, {7, 4}, {8, 1}, {8, 7}},
            {{3, 3}, {3, 6}, {5, 1}, {7, 1}, {7, 6}, {8, 2}, {8, 4}, {8, 6}},
            {{1, 4}, {2, 1}, {2, 8}, {4, 4}, {5, 8}, {6, 4}, {8, 1}, {8, 4}},
            {{1, 3}, {2, 6}, {5, 4}, {5, 7}, {6, 2}, {7, 1}, {8, 3}, {8, 6}},
            {{1, 3}, {1, 7}, {4, 1}, {4, 4}, {7, 1}, {7, 7}, {8, 4}, {8, 7}},
            {{1, 2}, {1, 7}, {4, 3}, {5, 7}, {6, 4}, {7, 1}, {7, 3}, {8, 7}},
            {{1, 7}, {2, 3}, {4, 1}, {4, 3}, {4, 6}, {5, 8}, {8, 3}, {8, 8}}};
    int tehditler[][][] = {{{1, 2, 0}, {3, 2, 1}, {3, 3, 2}, {4, 5, 1}, {6, 6, 1}, {8, 6, 1}, {8, 7, 0}},
            {{1, 2, 1}, {1, 8, 0}, {4, 1, 2}, {4, 7, 4}, {5, 7, 2}, {6, 3, 2}, {7, 8, 2}},
            {{1, 2, 1}, {1, 4, 0}, {3, 1, 2}, {3, 5, 2}, {4, 8, 0}, {5, 6, 0}, {8, 5, 2}},
            {{1, 1, 1}, {2, 7, 2}, {3, 7, 1}, {5, 1, 2}, {6, 3, 0}, {6, 7, 1}, {7, 3, 1}},
            {{1, 4, 1}, {2, 5, 1}, {2, 7, 2}, {4, 2, 1}, {4, 3, 3}, {5, 1, 0}, {6, 3, 3}, {8, 1, 3}},
            {{2, 2, 2}, {4, 2, 2}, {5, 7, 2}, {8, 1, 1}, {8, 2, 0}, {8, 5, 2}},
            {{2, 5, 0}, {3, 2, 2}, {3, 5, 2}, {5, 2, 3}, {6, 2, 2}, {7, 8, 1}},
            {{2, 7, 1}, {3, 2, 2}, {3, 6, 3}, {5, 3, 0}, {8, 4, 2}}};
    final int SORU_SAYISI = tehditler.length;
    final int EMPTY = -1;

    Bitmap getBitmap(int pieceNo){
        switch (pieceNo){
            case 0:return king;
            case 1:return queen;
            case 2:
            case 3:return rook;
            case 4:
            case 5:return bishop;
            case 6:
            case 7:return knight;
            default:return king;
        }
    }

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
        int i, posx, posy;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.STROKE);
        black.setTextSize(cellWidth / 2);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < 9; i++){
            canvas.drawLine((i + 1) * cellWidth, cellWidth, (i + 1) * cellWidth, 9 * cellWidth, black);
            canvas.drawLine(cellWidth, (i + 1) * cellWidth, 9 * cellWidth, (i + 1) * cellWidth, black);
        }
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.STROKE);
        blue.setStrokeWidth(3);
        for (i = 0; i < yerlestirilecekYerler[soruno].length; i++){
            posx = yerlestirilecekYerler[soruno][i][0] - 1;
            posy = yerlestirilecekYerler[soruno][i][1] - 1;
            if (tablo[i] == EMPTY){
                canvas.drawRect(new Rect((posy + 1) * cellWidth, (posx + 1) * cellWidth, (posy + 2) * cellWidth, (posx + 2) * cellWidth), blue);
            } else {
                if (moving && fromPiece >= 8 && fromPiece - 8 == i){
                    canvas.drawBitmap(getBitmap(tablo[i]), null, new RectF(moveX, moveY, moveX + cellWidth - 2, moveY + cellWidth - 2), null);
                } else {
                    canvas.drawBitmap(getBitmap(tablo[i]), null, new RectF((posy + 1) * cellWidth + 1, (posx + 1) * cellWidth + 1, (posy + 2) * cellWidth - 1, (posx + 2) * cellWidth - 1), null);
                }
            }
        }
        for (i = 0; i < tehditler[soruno].length; i++){
            posx = tehditler[soruno][i][0] - 1;
            posy = tehditler[soruno][i][1] - 1;
            drawDigit(canvas, (posy + 1) * cellWidth, (posx + 1) * cellWidth, tehditler[soruno][i][2]);
        }
        for (i = 0; i < 8; i++){
            if (!from[i]){
                if (moving && fromPiece < 8 && fromPiece == i){
                    canvas.drawBitmap(getBitmap(i), null, new RectF(moveX, moveY, moveX + cellWidth - 2, moveY + cellWidth - 2), null);
                } else {
                    canvas.drawBitmap(getBitmap(i), null, new RectF((i + 1) * cellWidth + 1, 9 * cellWidth + 1, (i + 2) * cellWidth - 1, 10 * cellWidth - 1), null);
                }
            }
        }
    }

    public SatrancTaslariView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = context.getResources();
        rand = new Random();
        king = BitmapFactory.decodeResource(mRes, R.drawable.king);
        queen = BitmapFactory.decodeResource(mRes, R.drawable.queen);
        rook = BitmapFactory.decodeResource(mRes, R.drawable.rook);
        bishop = BitmapFactory.decodeResource(mRes, R.drawable.bishop);
        knight = BitmapFactory.decodeResource(mRes, R.drawable.knight);
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
        newGame();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int posx, posy, index;
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (y < 9 * cellWidth){
                posx = x / cellWidth - 1;
                posy = y / cellWidth - 1;
                if (posx >= 0 && posx < 8 && posy >= 0 && posy < 8){
                    index = yerUygun[posy][posx];
                    if (index != -1 && tablo[index] != EMPTY){
                        fromPiece = index + 8;
                        moving = true;
                    }
                }
            } else {
                posx = x / cellWidth - 1;
                if (posx >= 0 && posx < 8 && !from[posx]){
                    fromPiece = posx;
                    moving = true;
                }
            }
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            moving = false;
            if (y < 9 * cellWidth){
                posx = x / cellWidth - 1;
                posy = y / cellWidth - 1;
                if (posx >= 0 && posx < 8 && posy >= 0 && posy < 8){
                    index = yerUygun[posy][posx];
                    if (index != -1 && tablo[index] == EMPTY && fromPiece >= 0 && fromPiece < 8 && !from[fromPiece]){
                        from[fromPiece] = true;
                        tablo[index] = fromPiece;
                        invalidate();
                    }
                }
            } else {
                posx = x / cellWidth - 1;
                if (posx >= 0 && posx < 8 && fromPiece >= 8 && from[tablo[fromPiece - 8]]){
                    from[tablo[fromPiece - 8]] = false;
                    tablo[fromPiece - 8] = EMPTY;
                    invalidate();
                }
            }
            invalidate();
            return true;
        } else if (action == MotionEvent.ACTION_MOVE && moving){
            moveX = (int) event.getX();
            moveY = (int) event.getY();
            invalidate();
        }
        return false;
    }

    public void newGame(){
        int i, j, x, y;
        finished = false;
        soruno = rand.nextInt(SORU_SAYISI);
        tablo = new int[8];
        cozum = new int[8];
        from = new boolean[8];
        yerUygun = new int[8][8];
        for (i = 0; i < 8; i++){
            tablo[i] = EMPTY;
        }
        for (i = 0; i < 8; i++)
            for (j = 0; j < 8; j++)
                yerUygun[i][j] = EMPTY;
        for (i = 0; i < yerlestirilecekYerler[soruno].length; i++){
            x = yerlestirilecekYerler[soruno][i][0] - 1;
            y = yerlestirilecekYerler[soruno][i][1] - 1;
            yerUygun[x][y] = i;
        }
        cellWidth = boardWidth / 10;
        moving = false;
    }

    public void checkSolution(){
        String sonuc;
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        int i;
        for (i = 0; i < 8; i++)
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
        int i, j, count = 0;
        boolean found;
        for (i = 0; i < 8; i++){
            found = false;
            for (j = 0; j < k; j++){
                if (cozum[j] == i){
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

    private int checkKing(int x, int y){
        int i, j, kim;
        for (i = -1; i < 2; i++)
            for (j = -1; j < 2; j++)
                if (x + i >= 0 && x + i < 8 && y + j >= 0 && y + j < 8){
                    kim = yerUygun[x + i][y + j];
                    if (kim != EMPTY && cozum[kim] == 0)
                        return 1;
                }
        return 0;
    }

    private int checkBishop(int x, int y){
        int xArtis[4] = {1, 1, -1, -1};
    	int yArtis[4] = {1, -1, 1, -1};
        int i, j, kim, count = 0, a, b;
            for (j = 0; j < 4; j++){
                for (i = 1; i < 8; i++){
		            a = x + i * xArtis[j];
		            b = y + i * yArtis[j];
                    if (a >= 0 && a < 8 && b >= 0 && b < 8){
                        kim = yerUygun[a][b];
                        if (kim != EMPTY){
                            if (cozum[kim] == 4 || cozum[kim] == 5)
                                count++;
                            else
                                break;
                        }
                    }
                }
            }
        return count;
    }

    private int checkRook(int x, int y){
	    int xArtis[4] = {1, -1, 0, 0};
    	int yArtis[4] = {0, 0, 1, -1};    
        int i, j, kim, count = 0, a, b;
        for (j = 0; j < 4; j++){
            for (i = 1; i < 8; i++){
		        a = x + i * xArtis[j];
		        b = y + i * yArtis[j];
                if (a >= 0 && a < 8 && b >= 0 && b < 8){
                    kim = yerUygun[a][b];
                    if (kim != EMPTY){
                        if (cozum[kim] == 2 || cozum[kim] == 3)
                            count++;
                        else
                            break;
                    }
                }
            }
        }
        return count;
    }

    private int checkQueen(int x, int y){
	    int xArtis[8] = {1, 1, -1, -1, 1, -1, 0, 0};
	    int yArtis[8] = {1, -1, 1, -1, 0, 0, 1, -1};
        int i, j, kim, a, b;
        for (j = 0; j < 8; j++){
            for (i = 1; i < 8; i++){
		        a = x + i * xArtis[j];
		        b = y + i * yArtis[j];
                if (a >= 0 && a < 8 && b >= 0 && b < 8){
                    kim = yerUygun[a][b];
                    if (kim != EMPTY){
                        if (cozum[kim] == 1)
                            return 1;
                        else
                            break;
                    }
                }
            }
        }
        return 0;
    }

    private int checkKnight(int x, int y){
    	int xArtis[8] = {1, 2, 1, -1, -1, 2, -2, -2};
    	int yArtis[8] = {-2, -1, 2, 2, -2, 1, 1, -1};
        int j, kim, a, b, count = 0;
        for (j = 0; j < 8; j++){
		    a = x + xArtis[j];
		    b = y + yArtis[j];
            if (a >= 0 && a < 8 && b >= 0 && b < 8){
                kim = yerUygun[a][b];
                if (kim != EMPTY){
                    if (cozum[kim] == 6 || cozum[kim] == 7)
                        count++;
                    else
                        break;
                }
            }
        }
        return count;
    }

    private boolean satisfyConditions(){
        int i, x, y, count;
        for (i = 0; i < tehditler[soruno].length; i++){
            x = tehditler[soruno][i][0] - 1;
            y = tehditler[soruno][i][1] - 1;
            count = checkKing(x, y) + checkBishop(x, y) + checkRook(x, y) + checkQueen(x, y) + checkKnight(x, y);
            if (count != tehditler[soruno][i][2])
                return false;
        }
        return true;
    }

    private boolean satisfyNow(int k){
        int i, x, y, count;
        for (i = k; i < 8; i++)
            cozum[i] = EMPTY;
        for (i = 0; i < tehditler[soruno].length; i++){
            x = tehditler[soruno][i][0] - 1;
            y = tehditler[soruno][i][1] - 1;
            count = checkKing(x, y) + checkBishop(x, y) + checkRook(x, y) + checkQueen(x, y) + checkKnight(x, y);
            if (count > tehditler[soruno][i][2])
                return false;
        }
        return true;
    }

    private void backtrack(int k){
        int i, ncandidates;
        int[] c;
        c = new int[8];
        if (!satisfyNow(k)){
            return;
        } else {
            if (k == 8){
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
            }
        }
    }

    public void solve(){
        int i;
        finished = false;
        for (i = 0; i < 8; i++)
            cozum[i] = EMPTY;
        backtrack(0);
        for (i = 0; i < 8; i++){
            tablo[i] = cozum[i];
            from[i] = true;
        }
    }
}

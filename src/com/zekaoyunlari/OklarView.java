package com.zekaoyunlari;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class OklarView extends View{

    int boardWidth = 300;
    int soruno, width, cellWidth;
    Random rand;
    boolean finished;
    int[][] cozum;
    int[][] tablo;
    Bitmap up, upright, right, downright, down, downleft, left, upleft, digits[];
    String sorular[][] = {{"2431", "4324", "5333", "2631"}, {"1331", "5454", "3343", "4453"}, {"25142", "43235", "34162", "45544", "26242"}, {"26243", "44522", "25031", "74336", "14031"},
            {"14141", "54423", "26030", "64534", "25151"}, {"37244", "53224", "23031", "45434", "25232"}, {"3342", "3411", "7543", "3541"}, {"2332", "4345", "3334", "3553"},
            {"25243", "44423", "25031", "74435", "15031"}, {"36151", "43433", "14050", "43334", "25152"}, {"374264", "553535", "222131", "140122", "632125", "150031"}, {"341154", "521136", "210032", "310122", "643245", "563254"},
            {"3442", "3434", "3433", "2433"}, {"1342", "3445", "2343", "2552"}, {"26143", "53334", "14051", "44546", "14051"}, {"26141", "63444", "15052", "43434", "14041"},
            {"252254", "533447", "111133", "220123", "643235", "252123"}, {"453354", "552345", "321013", "330012", "752235", "352134"}, {"2132", "4213", "6544", "3452"}, {"2241", "3311", "7544", "2561"},
            {"36241", "44513", "25030", "64425", "14021"}, {"25041", "53434", "15051", "43435", "14052"}, {"262253", "554337", "231122", "230122", "321015", "351144"}, {"342154", "521236", "210042", "321234", "633245", "462254"}};
    final int SORU_SAYISI = sorular.length;
    final int EMPTY = 0;
    final int UP = 1;
    final int UPRIGHT = 2;
    final int RIGHT = 3;
    final int DOWNRIGHT = 4;
    final int DOWN = 5;
    final int DOWNLEFT = 6;
    final int LEFT = 7;
    final int UPLEFT = 8;

    Bitmap getBitmap(int direction){
        switch (direction){
            case UP:return up;
            case UPRIGHT:return upright;
            case RIGHT:return right;
            case DOWNRIGHT:return downright;
            case DOWN:return down;
            case DOWNLEFT:return downleft;
            case LEFT:return left;
            case UPLEFT:return upleft;
            default:return up;
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
            if (tablo[0][i] != EMPTY){
                canvas.drawBitmap(getBitmap(tablo[0][i]), null, new RectF((i + 1) * cellWidth, 0, (i + 2) * cellWidth, cellWidth), null);
            }
            if (tablo[1][i] != EMPTY){
                canvas.drawBitmap(getBitmap(tablo[1][i]), null, new RectF(0, (i + 1) * cellWidth, cellWidth, (i + 2) * cellWidth), null);
            }
            if (tablo[2][i] != EMPTY){
                canvas.drawBitmap(getBitmap(tablo[2][i]), null, new RectF((width + 1) * cellWidth, (i + 1) * cellWidth, (width + 2) * cellWidth, (i + 2) * cellWidth), null);
            }
            if (tablo[3][i] != EMPTY){
                canvas.drawBitmap(getBitmap(tablo[3][i]), null, new RectF((i + 1) * cellWidth, (width + 1) * cellWidth, (i + 2) * cellWidth, (width + 2) * cellWidth), null);
            }
        }
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++){
                drawDigit(canvas, (j + 1) * cellWidth, (i + 1) * cellWidth, sorular[soruno][i].charAt(j) - '0');
            }
    }

    public OklarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources mRes;
        mRes = context.getResources();
        rand = new Random();
        up = BitmapFactory.decodeResource(mRes, R.drawable.up);
        upright = BitmapFactory.decodeResource(mRes, R.drawable.upright);
        right = BitmapFactory.decodeResource(mRes, R.drawable.right);
        downright = BitmapFactory.decodeResource(mRes, R.drawable.downright);
        down = BitmapFactory.decodeResource(mRes, R.drawable.down);
        downleft = BitmapFactory.decodeResource(mRes, R.drawable.downleft);
        left = BitmapFactory.decodeResource(mRes, R.drawable.left);
        upleft = BitmapFactory.decodeResource(mRes, R.drawable.upleft);
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
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;

        } else if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int posx = x / cellWidth - 1;
            int posy = y / cellWidth - 1;
            if (posx >= 0 && posx < width && posy == -1){
                if (posx == 0){
                    switch (tablo[0][posx]){
                        case EMPTY :
                            tablo[0][posx] = DOWN;
                            break;
                        case DOWN :
                            tablo[0][posx] = DOWNRIGHT;
                            break;
                        case DOWNRIGHT:
                            tablo[0][posx] = EMPTY;
                            break;
                    }
                } else {
                    if (posx == width - 1){
                        switch (tablo[0][posx]){
                            case EMPTY :
                                tablo[0][posx] = DOWN;
                                break;
                            case DOWN :
                                tablo[0][posx] = DOWNLEFT;
                                break;
                            case DOWNLEFT:
                                tablo[0][posx] = EMPTY;
                                break;
                        }
                    } else {
                        switch (tablo[0][posx]){
                            case EMPTY :
                                tablo[0][posx] = DOWNLEFT;
                                break;
                            case DOWNLEFT :
                                tablo[0][posx] = DOWN;
                                break;
                            case DOWN:
                                tablo[0][posx] = DOWNRIGHT;
                                break;
                            case DOWNRIGHT:
                                tablo[0][posx] = EMPTY;
                                break;
                        }
                    }
                }
                invalidate();
            }
            if (posx >= 0 && posx < width && posy == width){
                if (posx == 0){
                    switch (tablo[3][posx]){
                        case EMPTY :
                            tablo[3][posx] = UP;
                            break;
                        case UP :
                            tablo[3][posx] = UPRIGHT;
                            break;
                        case UPRIGHT:
                            tablo[3][posx] = EMPTY;
                            break;
                    }
                } else {
                    if (posx == width - 1){
                        switch (tablo[3][posx]){
                            case EMPTY :
                                tablo[3][posx] = UP;
                                break;
                            case UP :
                                tablo[3][posx] = UPLEFT;
                                break;
                            case UPLEFT:
                                tablo[3][posx] = EMPTY;
                                break;
                        }
                    } else {
                        switch (tablo[3][posx]){
                            case EMPTY :
                                tablo[3][posx] = UPLEFT;
                                break;
                            case UPLEFT :
                                tablo[3][posx] = UP;
                                break;
                            case UP:
                                tablo[3][posx] = UPRIGHT;
                                break;
                            case UPRIGHT:
                                tablo[3][posx] = EMPTY;
                                break;
                        }
                    }
                }
                invalidate();
            }
            if (posy >= 0 && posy < width && posx == -1){
                if (posy == 0){
                    switch (tablo[1][posy]){
                        case EMPTY :
                            tablo[1][posy] = RIGHT;
                            break;
                        case RIGHT :
                            tablo[1][posy] = DOWNRIGHT;
                            break;
                        case DOWNRIGHT:
                            tablo[1][posy] = EMPTY;
                            break;
                    }
                } else {
                    if (posx == width - 1){
                        switch (tablo[1][posy]){
                            case EMPTY :
                                tablo[1][posy] = RIGHT;
                                break;
                            case RIGHT :
                                tablo[1][posy] = UPRIGHT;
                                break;
                            case UPRIGHT:
                                tablo[1][posy] = EMPTY;
                                break;
                        }
                    } else {
                        switch (tablo[1][posy]){
                            case EMPTY :
                                tablo[1][posy] = UPRIGHT;
                                break;
                            case UPRIGHT :
                                tablo[1][posy] = RIGHT;
                                break;
                            case RIGHT:
                                tablo[1][posy] = DOWNRIGHT;
                                break;
                            case DOWNRIGHT:
                                tablo[1][posy] = EMPTY;
                                break;
                        }
                    }
                }
                invalidate();
            }
            if (posy >= 0 && posy < width && posx == width){
                if (posy == 0){
                    switch (tablo[2][posy]){
                        case EMPTY :
                            tablo[2][posy] = LEFT;
                            break;
                        case LEFT :
                            tablo[2][posy] = DOWNLEFT;
                            break;
                        case DOWNLEFT:
                            tablo[2][posy] = EMPTY;
                            break;
                    }
                } else {
                    if (posy == width - 1){
                        switch (tablo[2][posy]){
                            case EMPTY :
                                tablo[2][posy] = LEFT;
                                break;
                            case LEFT :
                                tablo[2][posy] = UPLEFT;
                                break;
                            case UPLEFT:
                                tablo[2][posy] = EMPTY;
                                break;
                        }
                    } else {
                        switch (tablo[2][posy]){
                            case EMPTY :
                                tablo[2][posy] = UPLEFT;
                                break;
                            case UPLEFT :
                                tablo[2][posy] = LEFT;
                                break;
                            case LEFT:
                                tablo[2][posy] = DOWNLEFT;
                                break;
                            case DOWNLEFT:
                                tablo[2][posy] = EMPTY;
                                break;
                        }
                    }
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
        tablo = new int[4][width];
        cozum = new int[4][width];
        cellWidth = boardWidth / (width + 2);
    }

    public void checkSolution(){
        String sonuc;
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        int i, j;
        for (i = 0; i < 4; i++)
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

    private int constructCandidates(int k, int[] c){
        int i, j, x, y;
        int[][] counts;
        x = k / width;
        y = k % width;
        counts = findCounts();
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++)
                if (counts[i][j] > sorular[soruno][i].charAt(j) - '0')
                    return 0;
        switch (x){
            case 0:
                if (y == 0){
                    c[0] = DOWN;
                    c[1] = DOWNRIGHT;
                    return 2;
                } else {
                    if (y == width - 1){
                        c[0] = DOWN;
                        c[1] = DOWNLEFT;
                        return 2;
                    } else {
                        c[0] = DOWNLEFT;
                        c[1] = DOWN;
                        c[2] = DOWNRIGHT;
                        return 3;
                    }
                }
            case 1:
                if (y == 0){
                    c[0] = RIGHT;
                    c[1] = DOWNRIGHT;
                    return 2;
                } else {
                    if (y == width - 1){
                        c[0] = RIGHT;
                        c[1] = UPRIGHT;
                        return 2;
                    } else {
                        c[0] = UPRIGHT;
                        c[1] = RIGHT;
                        c[2] = DOWNRIGHT;
                        return 3;
                    }
                }
            case 2:
                if (y == 0){
                    c[0] = LEFT;
                    c[1] = DOWNLEFT;
                    return 2;
                } else {
                    if (y == width - 1){
                        c[0] = LEFT;
                        c[1] = UPLEFT;
                        return 2;
                    } else {
                        c[0] = UPLEFT;
                        c[1] = LEFT;
                        c[2] = DOWNLEFT;
                        return 3;
                    }
                }
            case 3:
                if (y == 0){
                    c[0] = UP;
                    c[1] = UPRIGHT;
                    return 2;
                } else {
                    if (y == width - 1){
                        c[0] = UP;
                        c[1] = UPLEFT;
                        return 2;
                    } else {
                        c[0] = UPLEFT;
                        c[1] = UP;
                        c[2] = UPRIGHT;
                        return 3;
                    }
                }
        }
        return 0;
    }

    private void updateCounts(int x, int y, int direction, int[][] counts){
        switch (direction){
            case UP:
                y--;
                while (y >= 0 && y < width && x >= 0 && x < width){
                    counts[y][x]++;
                    y--;
                }
                break;
            case UPRIGHT:
                y--;
                x++;
                while (y >= 0 && y < width && x >= 0 && x < width){
                    counts[y][x]++;
                    x++;
                    y--;
                }
                break;
            case RIGHT:
                x++;
                while (y >= 0 && y < width && x >= 0 && x < width){
                    counts[y][x]++;
                    x++;
                }
                break;
            case DOWNRIGHT:
                y++;
                x++;
                while (y >= 0 && y < width && x >= 0 && x < width){
                    counts[y][x]++;
                    x++;
                    y++;
                }
                break;
            case DOWN:
                y++;
                while (y >= 0 && y < width && x >= 0 && x < width){
                    counts[y][x]++;
                    y++;
                }
                break;
            case DOWNLEFT:
                y++;
                x--;
                while (y >= 0 && y < width && x >= 0 && x < width){
                    counts[y][x]++;
                    x--;
                    y++;
                }
                break;
            case LEFT:
                x--;
                while (y >= 0 && y < width && x >= 0 && x < width){
                    counts[y][x]++;
                    x--;
                }
                break;
            case UPLEFT:
                y--;
                x--;
                while (y >= 0 && y < width && x >= 0 && x < width){
                    counts[y][x]++;
                    x--;
                    y--;
                }
                break;
        }
    }

    private int[][] findCounts(){
        int i, j, x, y;
        int[][] counts;
        counts = new int[width][width];
        for (i = 0; i < 4; i++){
            for (j = 0; j < width; j++){
                switch (i){
                    case 0:
                        y = -1;
                        x = j;
                        break;
                    case 1:
                        x = -1;
                        y = j;
                        break;
                    case 2:
                        x = width;
                        y = j;
                        break;
                    case 3:
                        y = width;
                        x = j;
                        break;
                    default:
                        x = 0;
                        y = 0;
                        break;
                }
                if (cozum[i][j] != 0)
                    updateCounts(x, y, cozum[i][j], counts);
            }
        }
        return counts;
    }

    private boolean satisfyConditions(){
        int i, j;
        int[][] counts;
        counts = findCounts();
        for (i = 0; i < width; i++)
            for (j = 0; j < width; j++)
                if (counts[i][j] != sorular[soruno][i].charAt(j) - '0')
                    return false;
        return true;
    }

    private void backtrack(int k){
        int i, ncandidates, row, column;
        int[] c;
        c = new int[3];
        if (k == 4 * width){
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
        for (i = 0; i < 4; i++){
            for (j = 0; j < width; j++){
                cozum[i][j] = 0;
            }
        }
        backtrack(0);
        for (i = 0; i < 4; i++)
            for (j = 0; j < width; j++){
                tablo[i][j] = cozum[i][j];
            }
    }
}

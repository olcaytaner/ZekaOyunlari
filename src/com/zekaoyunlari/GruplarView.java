package com.zekaoyunlari;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class GruplarView extends View{

    int boardWidth = 300, cellWidth;
    boolean selected[][];
    boolean finished;
    int[][] sayilar;
    int[] cozum;
    int soruno;
    Random rand;
    int sorular[][] = {{3122, 3424, 4114, 2142, 4423, 1142, 3414, 2342, 2121, 3112, 4134, 1241, 3133, 4221, 1213, 2144},
            {3141, 4223, 4231, 3134, 1213, 1114, 4124, 2241, 4311, 4412, 1122, 4434, 1141, 4233, 4442, 3234},
            {1134, 4142, 2241, 4442, 1314, 2424, 2432, 2441, 1141, 2121, 1331, 2224, 2133, 2123, 3413, 3342},
            {1312, 3111, 1431, 3433, 2221, 1313, 1424, 1142, 4114, 3224, 2144, 1231, 3312, 2424, 1223, 4121},
            {4422, 2243, 2413, 1131, 3334, 4412, 2432, 4123, 3113, 4212, 2134, 3413, 4113, 2142, 3124, 2412},
            {3344, 2324, 2344, 4312, 3411, 2123, 2223, 1341, 3442, 1112, 1334, 2311, 4344, 1143, 2322, 4314},
            {3241, 4233, 3413, 3311, 2422, 1323, 2334, 2223, 3424, 1343, 4321, 1121, 4133, 3242, 2313, 4221},
            {2132, 1314, 3442, 1213, 3424, 4231, 3114, 1333, 3241, 3122, 3131, 1134, 4221, 4241, 4234, 2123},
            {2141, 4423, 1334, 1132, 2324, 4421, 4442, 4441, 1121, 3421, 2241, 2333, 4434, 2323, 3231, 4112},
            {1344, 4441, 2333, 3313, 4343, 3223, 4132, 1124, 4124, 2111, 1323, 4223, 2213, 1412, 1221, 3131},
            {2122, 4212, 4311, 2314, 4424, 2141, 2312, 4234, 1131, 4241, 2443, 3134, 1432, 3331, 4421, 3243},
            {1333, 4223, 2333, 4413, 1144, 3333, 1433, 2412, 1322, 2142, 4234, 3332, 3114, 1241, 2124, 3131},
            {4243, 3132, 4123, 3141, 3221, 3334, 4324, 3133, 2412, 2133, 3214, 4422, 4441, 4141, 4221, 1342},
            {2224, 3344, 3431, 3421, 4211, 4412, 2344, 3242, 3144, 1233, 1333, 4443, 3234, 2423, 3413, 3132},
            {4312, 1421, 1433, 1211, 2442, 3414, 1234, 1324, 2434, 3223, 3332, 3222, 4212, 2422, 1322, 2241},
            {3433, 4213, 4314, 4431, 4424, 4133, 4232, 2441, 3411, 3242, 3432, 1112, 1413, 1444, 1214, 2121},
            {4324, 4231, 1212, 2132, 3131, 3431, 4432, 4413, 4113, 1134, 4112, 4243, 3422, 1321, 2221, 4443},
            {4311, 3221, 2242, 3141, 1324, 1314, 1322, 1343, 3431, 1244, 2333, 1344, 2331, 2244, 1434, 4444},
            {1242, 2432, 3342, 2331, 4223, 1114, 4334, 3313, 3333, 1224, 1411, 3213, 4131, 2122, 1221, 4244},
            {1411, 3222, 1421, 2422, 4241, 3121, 2124, 2322, 3333, 3323, 4134, 3141, 4421, 2224, 2331, 3211},
            {3242, 4324, 1342, 1243, 3144, 3113, 2141, 1442, 3241, 2231, 1234, 3233, 4122, 2224, 1224, 3321},
            {2334, 3234, 3422, 2241, 2244, 3334, 4311, 3134, 3142, 4211, 3331, 3432, 3133, 4244, 4344, 3322},
            {3112, 4413, 3444, 3323, 4222, 1134, 3233, 2243, 1241, 3421, 1224, 1431, 2144, 2113, 3313, 2342},
            {3421, 1131, 2221, 3422, 3331, 2414, 2232, 4141, 2344, 4433, 4212, 3121, 2242, 3224, 3113, 3323}};
    final int SORU_SAYISI = sorular.length;

    @Override
    public void onDraw(Canvas canvas) {
        String s;
        Rect bounds = new Rect();
        Paint black = new Paint(), red = new Paint();
        int i, j;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        black.setTextSize(16);
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);
        red.setTextSize(16);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < 4; i++){
            for (j = 0; j < 4; j++){
                s = "" + sayilar[i][j];
                black.getTextBounds(s, 0, s.length(), bounds);
                if (selected[i][j]){
                    canvas.drawText(s, j * cellWidth + cellWidth / 2 - bounds.width() / 2, i * cellWidth + cellWidth / 2 - bounds.height() / 2, red);
                } else {
                    canvas.drawText(s, j * cellWidth + cellWidth / 2 - bounds.width() / 2, i * cellWidth + cellWidth / 2 - bounds.height() / 2, black);
                }
            }
        }
    }

    public GruplarView(Context context, AttributeSet attrs) {
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
            int posx = y / cellWidth;
            int posy = x / cellWidth;
            if (posx >= 0 && posx < 4 && posy >= 0 && posy < 4){
                selected[posx][posy] = !selected[posx][posy];
                invalidate();
            }
            return true;
        }
        return false;
    }

    public void newGame(){
        int i, posx, posy;
        soruno = rand.nextInt(SORU_SAYISI);
        cellWidth = boardWidth / 4;
        selected = new boolean[4][4];
        sayilar = new int[4][4];
        for (i = 0; i < 16; i++){
            posx = i / 4;
            posy = i % 4;
            sayilar[posx][posy] = sorular[soruno][i];
        }
        cozum = new int[4];
    }


    public boolean check(){
        int i, j, k = 0;
        for (i = 0; i < 4; i++)
            for (j = 0; j < 4; j++){
                if (selected[i][j]){
                    if (k < 4){
                        cozum[k] = sayilar[i][j];
                        k++;
                    } else
                        return false;
                }
            }
        return k == 4 && dogruGrup();
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

    private int constructCandidates(int k, int[] c){
        int i, j, ncandidates = 0, posx, posy;
        boolean found;
        for (i = 0; i < 16; i++){
            posx = i / 4;
            posy = i % 4;
            found = false;
            for (j = 0; j < k; j++){
                if (sayilar[posx][posy] == cozum[j]){
                    found = true;
                    break;
                }
            }
            if (!found){
                c[ncandidates] = sayilar[posx][posy];
                ncandidates++;
            }
        }
        return ncandidates;
    }

    private boolean hepsiAyni(int[] b){
        return (b[0] == b[1] && b[0] == b[2] && b[0] == b[3]);
    }

    private boolean hepsiFarkli(int[] b){
        return (b[0] != b[1] && b[0] != b[2] && b[0] != b[3] && b[1] != b[2] && b[1] != b[3] && b[2] != b[3]);
    }

    private boolean dogruGrup(){
        int i, j;
        int[] basamaklar;
        basamaklar = new int[4];
        for (i = 0; i < 4; i++){
            for (j = 0; j < 4; j++){
                switch (i){
                    case 0:basamaklar[j] = cozum[j] % 10;
                        break;
                    case 1:basamaklar[j] = (cozum[j] / 10) % 10;
                        break;
                    case 2:basamaklar[j] = (cozum[j] / 100) % 10;
                        break;
                    case 3:basamaklar[j] = cozum[j] / 1000;
                        break;
                }
            }
            if (!hepsiAyni(basamaklar) && !hepsiFarkli(basamaklar)){
                return false;
            }
        }
        return true;
    }

    private void backtrack(int k){
        int i, ncandidates;
        int[] c;
        if (k == 4){
            finished = dogruGrup();
        }
        else{
            c = new int[16];
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

    public void solve(){
        backtrack(0);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                selected[i][j] = false;
                for (int k = 0; k < 4; k++){
                    if (cozum[k] == sayilar[i][j]){
                        selected[i][j] = true;
                    }
                }
            }
        }
    }

}

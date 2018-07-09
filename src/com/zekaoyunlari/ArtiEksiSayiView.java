package com.zekaoyunlari;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class ArtiEksiSayiView extends View{

    Random rand;
    final double RATIO = 3.0 / 4;
    int soruno, basamakSayisi;
    boolean isaretli[][];
    int boardWidth = 300, cellWidth, cellHeight;
    int [][][] sorular = {{{631, 1, -1}, {295, 1, 0}, {961, 0, -1}, {953, 0, -1}}, {{138, 0, -1}, {374, 1, -1}, {769, 1, 0}, {817, 0, -2}},
            {{190, 0, -1}, {427, 0, -1}, {934, 0, -2}, {865, 1, 0}}, {{306, 0, -1}, {425, 0, -1}, {812, 1, -1}, {139, 1, 0}},
            {{123, 0, -2}, {514, 0, -1}, {256, 0, -1}, {367, 0, -1}}, {{859, 0, -1}, {197, 0, -1}, {563, 0, -1}, {681, 0, -2}},
            {{207, 0, -2}, {132, 0, -1}, {879, 0, -1}, {560, 0, -1}, {456, 0, -1}}, {{357, 0, -1}, {146, 1, 0}, {235, 0, -1}, {890, 1, 0}, {642, 0, -1}},
            {{819, 0, -1}, {143, 1, 0}, {427, 0, -1}, {586, 1, 0}, {791, 0, -1}}, {{147, 0, -1}, {538, 0, -1}, {519, 0, -1}, {290, 1, 0}, {796, 0, -1}},
            {{945, 0, -1}, {260, 1, 0}, {781, 0, -1}, {976, 0, -1}, {163, 0, -1}}, {{285, 0, -1}, {169, 0, -1}, {127, 0, -1}, {573, 0, -1}, {470, 1, 0}},
            {{6721, 2, 0}, {9435, 0, -2}, {7859, 1, 0}, {5298, 0, -1}}, {{1234, 0, -2}, {5678, 0, -1}, {9012, 1, 0}, {4761, 2, 0}},
            {{2479, 0, -1}, {7981, 1, 0}, {4235, 0, -1}, {6810, 2, 0}}, {{1083, 1, 0}, {9254, 1, -1}, {7480, 0, -2}, {6572, 0, -3}},
            {{2408, 0, -2}, {3259, 1, 0}, {7290, 1, 0}, {9463, 1, 0}}, {{9308, 0, -2}, {1742, 1, -1}, {6819, 0, -2}, {2403, 1, 0}},
            {{5162, 0, -2}, {6538, 0, -2}, {7483, 0, -2}, {9617, 0, -2}, {1846, 0, -2}}, {{8346, 0, -2}, {7095, 0, -1}, {4567, 1, 0}, {9102, 1, -1}, {9403, 2, 0}},
            {{1234, 1, 0}, {4762, 0, -1}, {6879, 1, -1}, {5013, 0, -2}, {6170, 2, 0}}, {{1479, 0, -1}, {3657, 0, -1}, {4523, 0, -2}, {5038, 0, -1}, {7245, 0, -2}},
            {{1375, 0, -2}, {3018, 0, -2}, {9132, 0, -2}, {2938, 0, -2}, {8297, 0, -2}}, {{9286, 0, -2}, {5817, 0, -2}, {2904, 0, -2}, {3762, 1, -1}, {1389, 2, 0}},
            {{26781, 1, -1}, {62154, 0, -2}, {18469, 0, -3}, {82341, 0, -3}, {76980, 0, -3}}, {{12345, 1, -2}, {56789, 1, -2}, {91234, 0, -2}, {45678, 0, -3}, {89123, 2, 0}},
            {{97531, 0, -2}, {86421, 3, 0}, {75310, 0, -1}, {98765, 0, -2}, {76543, 1, -1}}, {{54316, 0, -3}, {90164, 2, 0}, {27580, 1, -1}, {13867, 2, 0}, {70925, 0, -3}},
            {{24609, 0, -2}, {31968, 1, -3}, {94726, 0, -3}, {26015, 2, 0}, {18270, 0, -2}}, {{35072, 1, -2}, {41836, 1, -2}, {96183, 1, -2}, {20645, 1, -2}, {57190, 1, -2}}};
    final int SORU_SAYISI = sorular.length;

    @Override
    public void onDraw(Canvas canvas) {
        int[] b = new int[basamakSayisi];
        Paint black = new Paint(), red = new Paint();
        super.onDraw(canvas);
        int i, j;
        String s;
        black.setColor(Color.BLACK);
        black.setTextSize(boardWidth / 15);
        red.setColor(Color.RED);
        red.setTextSize(boardWidth / 15);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < sorular[soruno].length; i++){
            basamaklar(sorular[soruno][i][0], b);
            for (j = 0; j < basamakSayisi; j++){
                s = "" + b[j] + "";
                if (isaretli[i][j]){
                    canvas.drawText(s, (2 * basamakSayisi - 1 - 2 * j) * cellWidth, cellHeight * (i + 1), red);
                } else {
                    canvas.drawText(s, (2 * basamakSayisi - 1 - 2 * j) * cellWidth, cellHeight * (i + 1), black);
                }
            }
            s = "";
            for (j = 0; j < sorular[soruno][i][1]; j++){
                s = s + "+";
            }
            for (j = 0; j < -sorular[soruno][i][2]; j++){
                s = s + "-";
            }
            canvas.drawText(s, (int) (RATIO * boardWidth), cellHeight * (i + 1), black);
        }
    }

    public ArtiEksiSayiView(Context context, AttributeSet attrs) {
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
            int posx = x / (2 * cellWidth);
            int posy = y / cellHeight;
            if (posx < basamakSayisi && posy < sorular[soruno].length){
                int [] b = new int[basamakSayisi];
                basamaklar(sorular[soruno][posy][0], b);
                int sayi = b[basamakSayisi - posx - 1];
                for (int i = 0; i < sorular[soruno].length; i++){
                    basamaklar(sorular[soruno][i][0], b);
                    for (int j = 0; j < basamakSayisi; j++){
                        if (b[j] == sayi){
                            isaretli[i][j] = !isaretli[i][j];
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
        soruno = rand.nextInt(SORU_SAYISI);
        if (sorular[soruno][0][0] > 10000){
            basamakSayisi = 5;
        } else {
            if (sorular[soruno][0][0] > 1000){
                basamakSayisi = 4;
            } else {
                basamakSayisi = 3;
            }
        }
        cellWidth = (int) ((RATIO * boardWidth) / (2 * basamakSayisi + 1));
        cellHeight = boardWidth / (sorular[soruno].length + 1);
        isaretli = new boolean[sorular[soruno].length][basamakSayisi];
    }

    private int pow10(int x){
        int result = 1;
        for (int i = 0; i < x; i++)
            result *= 10;
        return result;
    }

    private boolean basamaklar(int sayi, int[] b){
        for (int i = 0; i < basamakSayisi; i++){
            b[i] = sayi / pow10(i) % 10;
        }
        for (int i = 0; i < basamakSayisi; i++)
            for (int j = i + 1; j < basamakSayisi; j++){
                if (b[i] == b[j])
                    return false;
            }
        return true;
    }

    private boolean karsilastir(int[] b, int[] c, int index){
        int i, j, arti = 0, eksi = 0;
        for (i = 0; i < basamakSayisi; i++){
            for (j = 0; j < basamakSayisi; j++){
                if (b[i] == c[j]){
                    if (i == j){
                        arti++;
                    }
                    else{
                        eksi++;
                    }
                }
            }
        }
        return (sorular[soruno][index][1] == arti && sorular[soruno][index][2] == -eksi);
    }

    private int bul(){
        int i, j, start = pow10(basamakSayisi - 1), end = pow10(basamakSayisi);
        int[] b;
        int[] c;
        boolean found;
        b = new int[basamakSayisi];
        c = new int[basamakSayisi];
        for (i = start; i < end; i++){
            if (basamaklar(i, b)){
                found = true;
                for (j = 0; j < sorular[soruno].length; j++){
                    basamaklar(sorular[soruno][j][0], c);
                    if (!karsilastir(b, c, j)){
                        found = false;
                        break;
                    }
                }
                if (found){
                    return i;
                }
            }
        }
        return -1;
    }

    public void solve(){
        int sayi = bul();
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "" + sayi, duration);
        toast.show();
    }

    public void answer(String answer){
        if (!answer.isEmpty()){
            String sonuc;
            int cevap = Integer.parseInt(answer);
            int sayi = bul();
            Context context = this.getContext();
            int duration = Toast.LENGTH_SHORT;
            if (cevap != sayi){
                sonuc = getContext().getString(R.string.lose);
            } else {
                sonuc = getContext().getString(R.string.win);
            }
            Toast toast = Toast.makeText(context, sonuc, duration);
            toast.show();
            newGame();
            invalidate();
        }
    }

}

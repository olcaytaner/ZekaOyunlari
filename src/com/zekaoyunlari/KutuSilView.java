package com.zekaoyunlari;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class KutuSilView extends View{

    int boardWidth = 300, cellWidth;
    boolean finished;
    boolean[] cozum;
    boolean[] selected;
    int soruno;
    Random rand;
    String sorular[] = {"52+965-37=37-8+1", "1x4x6=58+62/8x76", "252+9x96x1=34+45", "1x6x1=645x7+5-27", "8+4/7-629=79-581", "8x8x6-97=7+45x92", "54/439-84=2-2-66", "5+1x957=9x48-1-9",
    "3x9-78-2=9073-85", "2+860+93=596x3-2", "69+25x15=2+5+6x4", "387+15=8-34-4+53", "51+624=3-8+7x908", "6-8/78=95-71-1-9", "94+15-19=3x163x5"};
    final int SORU_SAYISI = sorular.length;

    @Override
    public void onDraw(Canvas canvas) {
        String s;
        Rect bounds = new Rect();
        Paint black = new Paint(), red = new Paint();
        int i;
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);
        black.setTextSize(16);
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);
        red.setTextSize(16);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for (i = 0; i < sorular[soruno].length(); i++){
            s = "" + sorular[soruno].charAt(i);
            black.getTextBounds(s, 0, s.length(), bounds);
            if (selected[i]){
                canvas.drawText(s, (i + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2, 50, red);
            } else {
                canvas.drawText(s, (i + 1) * cellWidth + cellWidth / 2 - bounds.width() / 2, 50, black);
            }
        }
    }

    public KutuSilView(Context context, AttributeSet attrs) {
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
            int posx = x / cellWidth - 1;
            if (posx >= 0 && posx < sorular[soruno].length() && sorular[soruno].charAt(posx) != '='){
                selected[posx] = !selected[posx];
                invalidate();
            }
            return true;
        }
        return false;
    }

    public void newGame(){
        soruno = rand.nextInt(SORU_SAYISI);
        cellWidth = boardWidth / 18;
        selected = new boolean[sorular[soruno].length()];
    }


    public void checkSolution(){
        String sonuc;
        Context context = this.getContext();
        int duration = Toast.LENGTH_SHORT;
        cozum = new boolean[16];
        System.arraycopy(selected, 0, cozum, 0, 16);
        if (!satisfyConditions()){
            sonuc = getContext().getString(R.string.lose);
        } else {
            sonuc = getContext().getString(R.string.win);
        }
        Toast toast = Toast.makeText(context, sonuc, duration);
        toast.show();
    }

    private int hesapla(String s){
        int i, sayi = 0;
        boolean done;
        ArrayList<String> operators = new ArrayList<String>();
        ArrayList<Integer> operands = new ArrayList<Integer>();
        for (i = 0; i < s.length(); i++){
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                sayi = sayi * 10 + s.charAt(i) - '0';
            } else {
                if (sayi != 0){
                    operands.add(sayi);
                    sayi = 0;
                }
                operators.add(new String("" + s.charAt(i)));
            }
        }
        operands.add(sayi);
        while (operands.size() != 1){
            done = false;
            for (i = 0; i < operators.size(); i++){
                if (operators.get(i).compareTo("x") == 0 || operators.get(i).compareTo("/") == 0){
                    if (operators.get(i).compareTo("x") == 0)
                        sayi = operands.get(i) * operands.get(i + 1);
                    else
                        sayi = operands.get(i) / operands.get(i + 1);
                    operators.remove(i);
                    operands.remove(i + 1);
                    operands.remove(i);
                    operands.add(i, sayi);
                    done = true;
                    break;
                }
            }
            if (done)
                continue;
            for (i = 0; i < operators.size(); i++){
                if (operators.get(i).compareTo("+") == 0 || operators.get(i).compareTo("-") == 0){
                    if (operators.get(i).compareTo("+") == 0)
                        sayi = operands.get(i) + operands.get(i + 1);
                    else
                        sayi = operands.get(i) - operands.get(i + 1);
                    operators.remove(i);
                    operands.remove(i + 1);
                    operands.remove(i);
                    operands.add(i, sayi);
                    break;
                }
            }
        }
        return operands.get(0);
    }

    private boolean expressionOk(String s){
        int i;
        if (s.charAt(0) < '0' || s.charAt(0) > '9')
            return false;
        if (s.charAt(s.length() - 1) < '0' || s.charAt(s.length() - 1) > '9')
            return false;
        for (i = 1; i < s.length(); i++){
            if ((s.charAt(i) < '0' || s.charAt(i) > '9') && (s.charAt(i - 1) < '0' || s.charAt(i - 1) > '9'))
                return false;
        }
        return true;
    }

    private boolean satisfyConditions(){
        String s = "";
        for (int i = 0; i < sorular[soruno].length(); i++)
            if (!cozum[i])
                s = s + sorular[soruno].charAt(i);
        if (!expressionOk(s.substring(0, s.indexOf('='))))
            return false;
        if (!expressionOk(s.substring(s.indexOf('=') + 1, s.length())))
            return false;
        return hesapla(s.substring(0, s.indexOf('='))) == hesapla(s.substring(s.indexOf('=') + 1, s.length()));
    }

    private void backtrack(int k){
        int i, count = 0;
        if (k == sorular[soruno].length()){
            finished = satisfyConditions();
        }
        else{
            for (i = 0; i < k; i++)
                if (cozum[i])
                    count++;
            if (count < 2 && sorular[soruno].charAt(i) != '='){
                cozum[k] = true;
                backtrack(k + 1);
                if (finished){
                    return;
                }
            }
            if (k != sorular[soruno].length() - 1 || count == 2){
                cozum[k] = false;
                backtrack(k + 1);
            }
        }
    }

    public void solve(){
        cozum = new boolean[sorular[soruno].length()];
        backtrack(0);
        System.arraycopy(cozum, 0, selected, 0, sorular[soruno].length());
    }

}

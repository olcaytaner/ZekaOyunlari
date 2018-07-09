package com.zekaoyunlari;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class AltigenView extends View{

    int boardWidth = 300, moveX, moveY;
    float radius = (float) (boardWidth / (7 * Math.sqrt(3)));
    int soruno, fromHexagon, toHexagon;
    Random rand;
    boolean finished, moving;
    boolean[] from;
    Point[] hexagons;
    Point[] hexagonsBottom;
    int[] cozum;
    int[] tablo;
    String altigenler[][] = {{"215634", "153246", "134256", "315264", "612453", "364251"}, {"163524", "425361", "246315", "635214", "241365", "263415"},
            {"364125", "526134", "213564", "643152", "624153", "645213"}, {"354126", "564213", "345261", "451263", "513642", "326415"},
            {"425163", "413265", "456321", "362451", "453612", "351624"}, {"256341", "315624", "541362", "625314", "415263", "234156"},
            {"125643", "453126", "612453", "346152", "134265", "652314"}, {"426153", "245613", "351426", "264315", "612543", "132546"},
            {"361524", "641523", "426135", "142635", "456132", "165342"}, {"614253", "451326", "351462", "453126", "453261", "564312"},
            {"352416", "643512", "246351", "312645", "624513", "256143"}, {"641235", "651234", "351426", "342561", "164253", "135624"},
            {"431562", "651423", "341256", "345621", "251634", "146523"}, {"251364", "256341", "352146", "362415", "463251", "634251"},
            {"142356", "524613", "541263", "245316", "624513", "312645"}, {"364215", "532614", "415263", "124635", "523461", "425316"},
            {"135624", "154263", "152364", "342561", "512643", "425136"}, {"612354", "423516", "361524", "654132", "452361", "241563"},
            {"123456", "532614", "642351", "346215", "135426", "451263"}, {"534612", "613542", "631542", "562314", "516423", "361254"},
            {"352461", "134625", "645123", "645231", "312546", "124563"}, {"263415", "634512", "256413", "621435", "345261", "561342"},
            {"235461", "324615", "146235", "134625", "342651", "236451"}, {"541326", "362451", "621543", "453621", "253641", "164325"}};
    final int SORU_SAYISI = altigenler.length;
    final int EMPTY = -1;


    public void drawHexagon(Canvas canvas, float centerX, float centerY, Paint color, String s){
        String t;
        Rect bounds = new Rect();
        canvas.drawLine(centerX - radius / 2, (float) (centerY + radius * Math.sqrt(3) / 2), centerX + radius / 2, (float) (centerY + radius * Math.sqrt(3) / 2), color);
        canvas.drawLine(centerX + radius / 2, (float) (centerY + radius * Math.sqrt(3) / 2), centerX + radius, centerY, color);
        canvas.drawLine(centerX + radius, centerY, centerX + radius / 2, (float) (centerY - radius * Math.sqrt(3) / 2), color);
        canvas.drawLine(centerX + radius / 2, (float) (centerY - radius * Math.sqrt(3) / 2), centerX - radius / 2, (float) (centerY - radius * Math.sqrt(3) / 2), color);
        canvas.drawLine(centerX - radius / 2, (float) (centerY - radius * Math.sqrt(3) / 2), centerX - radius, centerY, color);
        canvas.drawLine(centerX - radius, centerY, centerX - radius / 2, (float) (centerY + radius * Math.sqrt(3) / 2), color);
        if (s != null){
            t = "" + s.charAt(4);
            color.getTextBounds(t, 0, t.length(), bounds);
            canvas.drawText(t, centerX - 3, (float) (centerY + radius * Math.sqrt(3) / 2 - bounds.height() / 2) + 5, color);
            t = "" + s.charAt(3);
            color.getTextBounds(t, 0, t.length(), bounds);
            canvas.drawText(t, centerX + 3 * radius / 4 - bounds.width() / 2 - 5, (float) (2 * centerY + radius * Math.sqrt(3) / 2) / 2, color);
            t = "" + s.charAt(2);
            color.getTextBounds(t, 0, t.length(), bounds);
            canvas.drawText(t, centerX + 3 * radius / 4 - bounds.width() / 2 - 5, (float) (2 * centerY - radius * Math.sqrt(3) / 2) / 2 + bounds.height() / 2, color);
            t = "" + s.charAt(1);
            color.getTextBounds(t, 0, t.length(), bounds);
            canvas.drawText(t, centerX - 3, (float) (centerY - radius * Math.sqrt(3) / 2 + bounds.height() / 2) + 5, color);
            t = "" + s.charAt(0);
            color.getTextBounds(t, 0, t.length(), bounds);
            canvas.drawText(t, centerX - 3 * radius / 4 + bounds.width() / 2, (float) (2 * centerY - radius * Math.sqrt(3) / 2) / 2 + bounds.height() / 2, color);
            t = "" + s.charAt(5);
            color.getTextBounds(t, 0, t.length(), bounds);
            canvas.drawText(t, centerX - 3 * radius / 4 + bounds.width() / 2, (float) (2 * centerY + radius * Math.sqrt(3) / 2) / 2, color);
        }
    }

    String permute(String s, int x, int pos){
        String t = s;
        while (t.charAt(pos) - '0' != x){
            t = t.substring(1, 6) + t.substring(0, 1);
        }
        return t;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int i;
        Paint black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.STROKE);
        black.setTextSize(radius / 2);
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        float centerX, centerY;
        centerX = boardWidth / 2;
        centerY = (float) (2 * Math.sqrt(3) * radius);
        drawHexagon(canvas, centerX, centerY, black, "123456");
        for (i = 0; i < 6; i++){
            if (tablo[i] == EMPTY){
                drawHexagon(canvas, hexagons[i].x, hexagons[i].y, black, null);
            } else {
                if (!moving || fromHexagon != tablo[i]){
                    drawHexagon(canvas, hexagons[i].x, hexagons[i].y, black, permute(altigenler[soruno][tablo[i]], (i + 1) % 6 == 0 ? 6 : (i + 1) % 6, (i + 3) % 6));
                } else {
                    drawHexagon(canvas, hexagons[i].x, hexagons[i].y, black, null);
                }
            }
        }
        for (i = 0; i < 6; i++){
            if (!from[i]){
                if (moving && fromHexagon == i + 6){
                    drawHexagon(canvas, moveX, moveY, black, altigenler[soruno][i]);
                } else {
                    drawHexagon(canvas, hexagonsBottom[i].x, hexagonsBottom[i].y, black, altigenler[soruno][i]);
                }
            } else {
                if (moving && fromHexagon < 6 && tablo[fromHexagon] == i){
                    drawHexagon(canvas, moveX, moveY, black, altigenler[soruno][i]);
                }
            }
        }
    }

    public AltigenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        float centerX, centerY;
        centerX = boardWidth / 2;
        centerY = (float) (2 * Math.sqrt(3) * radius);
        hexagons = new Point[6];
        hexagons[0] = new Point((int) (centerX - 3 * radius / 2), (int) (centerY - radius * Math.sqrt(3) / 2));
        hexagons[1] = new Point((int) centerX, (int) (centerY - radius * Math.sqrt(3)));
        hexagons[2] = new Point((int) (centerX + 3 * radius / 2), (int) (centerY - radius * Math.sqrt(3) / 2));
        hexagons[3] = new Point((int) (centerX + 3 * radius / 2), (int) (centerY + radius * Math.sqrt(3) / 2));
        hexagons[4] = new Point((int) centerX, (int) (centerY + radius * Math.sqrt(3)));
        hexagons[5] = new Point((int) (centerX - 3 * radius / 2), (int) (centerY + radius * Math.sqrt(3) / 2));
        hexagonsBottom = new Point[6];
        hexagonsBottom[0] = new Point(50, (int) (radius * 4.5 * Math.sqrt(3)));
        hexagonsBottom[1] = new Point(150, (int) (radius * 4.5 * Math.sqrt(3)));
        hexagonsBottom[2] = new Point(250, (int) (radius * 4.5 * Math.sqrt(3)));
        hexagonsBottom[3] = new Point(50, (int) (radius * 6 * Math.sqrt(3)));
        hexagonsBottom[4] = new Point(150, (int) (radius * 6 * Math.sqrt(3)));
        hexagonsBottom[5] = new Point(250, (int) (radius * 6 * Math.sqrt(3)));
        rand = new Random();
        newGame();
        setFocusable(true);
    }

    int hexagonContains(float x, float y){
        int i;
        for (i = 0; i < 6; i++){
            if ((x - hexagons[i].x) * (x - hexagons[i].x) + (y - hexagons[i].y) * (y - hexagons[i].y) < radius * radius){
                return i;
            }
        }
        return EMPTY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x, y;
        x = event.getX();
        y = event.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            if (y < radius * 3.5 * Math.sqrt(3)){
                fromHexagon = hexagonContains(x, y);
            } else {
                if (y < radius * 5.25 * Math.sqrt(3)){
                    fromHexagon = 6 + (int) x / 100;
                } else {
                    fromHexagon = 9 + (int) x / 100;
                }
            }
            moving = true;
        } else if (action == MotionEvent.ACTION_UP) {
            moving = false;
            if (y < radius * 3.5 * Math.sqrt(3)){
                toHexagon = hexagonContains(x, y);
            } else {
                if (y < radius * 5.25 * Math.sqrt(3)){
                    toHexagon = 6 + (int) x / 100;
                } else {
                    toHexagon = 9 + (int) x / 100;
                }
            }
            if (fromHexagon < 6 && fromHexagon >= 0 && tablo[fromHexagon] != EMPTY && toHexagon >= 6){
                from[tablo[fromHexagon]] = false;
                tablo[fromHexagon] = EMPTY;
                invalidate();
            }
            if (toHexagon < 6 && toHexagon >= 0 && tablo[toHexagon] == EMPTY && fromHexagon >= 6 && fromHexagon <= 11 && !from[fromHexagon - 6]){
                tablo[toHexagon] = fromHexagon - 6;
                from[fromHexagon - 6] = true;
                invalidate();
            }
        } else if (action == MotionEvent.ACTION_MOVE && moving){
            moveX = (int) event.getX();
            moveY = (int) event.getY();
            invalidate();
        }
        return true;
    }

    public void newGame(){
        finished = false;
        soruno = rand.nextInt(SORU_SAYISI);
        tablo = new int[6];
        cozum = new int[6];
        from = new boolean[6];
        for (int i = 0; i < 6; i++){
            tablo[i] = EMPTY;
        }
    }

    private int constructCandidates(int k, int[] c){
        int i, j, count = 0;
        boolean found;
        for (i = 0; i < 6; i++){
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

    private boolean satisfyConditions(){
        int i;
        String[] a;
        a = new String[6];
        for (i = 0; i < 6; i++){
            a[i] = permute(altigenler[soruno][cozum[i]], (i + 1) % 6 == 0 ? 6 : (i + 1) % 6, (i + 3) % 6);
        }
        for (i = 0; i < 6; i++)
            if (a[i].charAt((i + 2) % 6) != a[(i + 1) % 6].charAt((i + 5) % 6))
                return false;
        return true;
    }

    private void backtrack(int k){
        int i, ncandidates;
        int[] c;
        c = new int[6];
        if (k == 6){
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

    public void solve(){
        finished = false;
        backtrack(0);
        for (int i = 0; i < 6; i++){
            tablo[i] = cozum[i];
            from[i] = true;
        }
    }
}

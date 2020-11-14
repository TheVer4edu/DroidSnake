package ru.thever4.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {

    private Context context;
    private View parentUI;
    private Timer timer;
    private Bitmap dot, apple;
    private int SIZE;
    private int DOT_SIZE = 32, DOTS_PER_SIDE, ALL_DOTS;
    private int[] x, y;
    private int dotsCount;
    private Point appleCoords = new Point();
    private Direction direction;
    private boolean inGame = true;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public GameEngine(Context context, View parentUI) {
        this.context = context;
        this.parentUI = parentUI;
        loadImages();
        this.SIZE = context.getDisplay().getWidth();
        this.DOTS_PER_SIDE = SIZE / DOT_SIZE;
        this.ALL_DOTS = DOTS_PER_SIDE*DOTS_PER_SIDE/2;
    }

    private void actionPerformed() {
        if(isInGame()){
            checkApple();
            checkCollisions();
            move();
        }
        parentUI.postInvalidate();
    }

    public void initGame() {
        dotsCount = 3;
        this.x = new int[ALL_DOTS];
        this.y = new int[ALL_DOTS];
        direction = Direction.right;
        for (int i = 0; i < dotsCount; i++) {
            x[i] = (dotsCount + 1) * DOT_SIZE - i * DOT_SIZE;
            x[i] = (dotsCount + 1) * DOT_SIZE;
        }
        if(timer != null)
            timer.cancel();
        timer = new Timer();
        timer.schedule(new GamePerformer(), 250, 250);
        //TODO Create and start timer!!! (run repaint())
        createApple();
    }


    private void loadImages() {
        dot = BitmapFactory.decodeResource(context.getResources(), R.drawable.dot);
        apple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
    }

    private void createApple() {
        Random random = new Random();
        appleCoords.x = random.nextInt(SIZE/DOT_SIZE)*DOT_SIZE;
        appleCoords.y = random.nextInt(SIZE/DOT_SIZE)*DOT_SIZE;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isInGame() {
        return this.inGame;
    }

    public void reloadGame() {
        this.inGame = true;
        if(inGame) this.initGame();
    }

    private void move() {
        for (int i = dotsCount; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case up:
                y[0] -= DOT_SIZE;
                break;
            case down:
                y[0] += DOT_SIZE;
                break;
            case left:
                x[0] -= DOT_SIZE;
                break;
            case right:
                x[0] += DOT_SIZE;
                break;
        }
    }

    private void checkApple(){
        if(x[0] == appleCoords.x && y[0] == appleCoords.y){
            dotsCount++;
            createApple();
        }
    }

    private void checkCollisions(){
        for (int i = dotsCount; i > 0; i--){
            if(i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if (inGameBounds()) {
            inGame = false;
        }
    }

    private boolean inGameBounds() {
        return x[0] > SIZE || x[0] < 0 || y[0] > SIZE || y[0] < 0;
    }

    public void redrawGame(Canvas canvas) {
        if(isInGame()) {
            canvas.drawBitmap(apple, appleCoords.x, appleCoords.y, null);
            for(int i = 0; i < dotsCount; i++) {
                canvas.drawBitmap(dot, x[i], y[i], null);
            }
        }
        else {
            String str = "Game over!";
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(100);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(str, SIZE/2, SIZE/2, paint);
        }
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, SIZE, SIZE, p);
    }

    public enum Direction {
        left,
        right,
        up,
        down
    }

    private class GamePerformer extends TimerTask {

        @Override
        public void run() {
            actionPerformed();
        }
    }
}

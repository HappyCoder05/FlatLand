package com.example.sergiovela.flatlandrevised;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import static com.example.sergiovela.flatlandrevised.Constants.SCREEN_WIDTH;

public class GameplayScene implements Scene{

    private Rect r1 = new Rect();
    private boolean ready = false;
    private RectPlayer player;
    private Point playerPoint;
    private boolean movingPlayer =false;

    private boolean gameOver =false;
    private long gameOverTime;

    private OrientationData orientationData;
    private long frameTime;
    private int wide = SCREEN_WIDTH;
    private int high = Constants.SCREEN_HEIGHT;

    Rect newbutton = new Rect(wide  2 - 250, high  2 - 300, wide  2 + 250, high  2);


    public GameplayScene(){
        if(ready ==false) {
            player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
            playerPoint = new Point(SCREEN_WIDTH  2, 3  Constants.SCREEN_HEIGHT  4+150);
            player.update(playerPoint);
            orientationData = new OrientationData();
            orientationData.register();
            frameTime = System.currentTimeMillis();
        }

    }

    public void reset(){
        if(ready ==false) {
            playerPoint = new Point(SCREEN_WIDTH  2, 3  Constants.SCREEN_HEIGHT  4+150);
            player.update(playerPoint);
            movingPlayer= false;
        }
    }


    @Override
    public void terminate(){
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void recieveTouch(MotionEvent event) {

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        if(ready ==false) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN
                    if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY()))
                        movingPlayer = true;
                    if (gameOver && System.currentTimeMillis() - gameOverTime = 2000){
                        SceneManager.ACTIVE_SCENE =1;
                        reset();
                        gameOver = false;
                        orientationData.newGame();
                    }
                    break;
                case MotionEvent.ACTION_MOVE
                    if (movingPlayer && !gameOver)
                        playerPoint.set((int) event.getX(), (int) event.getY());
                    break;
                case MotionEvent.ACTION_UP
                    movingPlayer = false;
                    break;
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        if(ready ==false) {
            player.draw(canvas);
            if(gameOver) {
                gameOver=false;
                reset();
                SceneManager.ACTIVE_SCENE = 1;
            }
        }
    }

    @Override
    public void update(){
        if(ready ==false) {
            if (!gameOver) {
                if (frameTime  Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;
                int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
                frameTime = System.currentTimeMillis();
                if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                    float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                    float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                    float xSpeed = 2  roll  Constants.SCREEN_WIDTH  1000f;
                    float ySpeed = pitch  Constants.SCREEN_HEIGHT  1000f;

                    playerPoint.x += Math.abs(xSpeed  elapsedTime)  5  xSpeed  elapsedTime  0;
                    playerPoint.y -= Math.abs(ySpeed  elapsedTime)  5  ySpeed  elapsedTime  0;
                }
                if (playerPoint.x  0)
                playerPoint.x = 0;
                else if (playerPoint.x  SCREEN_WIDTH)
                playerPoint.x = SCREEN_WIDTH;
                if (playerPoint.y  0)
                playerPoint.y = 0;
                else if (playerPoint.y  Constants.SCREEN_HEIGHT)
                playerPoint.y = Constants.SCREEN_HEIGHT;
                player.update(playerPoint);
                if (Rect.intersects(newbutton, player.getRectangle())) {
                    gameOver = true;
                    gameOverTime = System.currentTimeMillis();
                }
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {

    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r1);
        int cHeight = r1.height();
        int cWidth = r1.width();
        paint.getTextBounds(text, 0, text.length(), r1);
        float x = cWidth  2f - r1.width()  2f - r1.left;
        float y = cHeight  2f + r1.height()  2f - r1.bottom;
        canvas.drawText(text, x, y, paint);

    }

}

package com.example.sergiovela.flatlandrevised;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Sergio Vela on 4/25/2017.
 */

//this class is unfinished, but it being incomplete will not cause any errors
//in running the program
public class

InformationScene implements Scene{
    private Rect r1 = new Rect();
    private RectPlayer player2;
    private Point playerPoint;
    private boolean movingPlayer =false;
    private boolean gameOver =false;

    private long gameOverTime;
    private OrientationData orientationData;

    private long frameTime;
    private int wide = Constants.SCREEN_WIDTH;
    private int high = Constants.SCREEN_HEIGHT;
    BitmapFactory bf = new BitmapFactory();
    Bitmap exitImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.bat_fly);
    private Rect exitButton = new Rect( wide-110, high-110,wide-10, high-10);

    public InformationScene(){
        player2 = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4+150);
        player2.update(playerPoint);
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }
    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4+150);
        player2.update(playerPoint);
        movingPlayer= false;
    }


    @Override
    public void terminate(){SceneManager.ACTIVE_SCENE = 0;};
    @Override
    public void recieveTouch(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player2.getRectangle().contains((int) event.getX(), (int) event.getY()))
                    movingPlayer = true;
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000){
                    //SceneManager.ACTIVE_SCENE =1;
                    reset();
                    gameOver = false;
                    orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (movingPlayer && !gameOver)
                    playerPoint.set((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }

    }
    @Override
    public void update(){
        if (!gameOver) {
            if (frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();
            if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 1000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT / 1000f;

                playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;
                playerPoint.y -= Math.abs(ySpeed * elapsedTime) > 5 ? ySpeed * elapsedTime : 0;
            }
            if (playerPoint.x < 0)
                playerPoint.x = 0;
            else if (playerPoint.x > Constants.SCREEN_WIDTH)
                playerPoint.x = Constants.SCREEN_WIDTH;
            if (playerPoint.y < 0)
                playerPoint.y = 0;
            else if (playerPoint.y > Constants.SCREEN_HEIGHT)
                playerPoint.y = Constants.SCREEN_HEIGHT;
            player2.update(playerPoint);
            if (Rect.intersects(exitButton, player2.getRectangle())) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }
    @Override
    public void draw(Canvas canvas){
        player2.draw(canvas);
        if(gameOver) {
            gameOver=false;
            reset();
            SceneManager.ACTIVE_SCENE = 0;
        }
    }
    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r1);
        int cHeight = r1.height();
        int cWidth = r1.width();
        paint.getTextBounds(text, 0, text.length(), r1);
        float x = cWidth / 2f - r1.width() / 2f - r1.left;
        float y = cHeight / 2f + r1.height() / 2f - r1.bottom;
        canvas.drawText(text, x, y, paint);

    }

}

package com.example.sergiovela.flatlandrevised;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Sergio Vela on 4/25/2017.
 */

//this the main character's class
//I also used this class to set up the background and the sprites
public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private int wide = Constants.SCREEN_WIDTH;
    private int high = Constants.SCREEN_HEIGHT;
    private Rect exitButton = new Rect( wide-110, high-110,wide-10, high-10);
    private Rect infobutton = new Rect(wide-210, high-100, wide-110, high-10);
    BitmapFactory bf = new BitmapFactory();
    Bitmap exitImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.bat_fly);
    Bitmap infoImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.bee);
    Bitmap idleImg9 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.barnacle);
    Bitmap idleImg10 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.barnacleone);
    Bitmap startbutton = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.barnacle_bite);
    Bitmap quitbutton = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.barnacle_bitetwo);
    private Animation idle;
    private AnimationManager animManager;

    public Rect getRectangle(){
        return rectangle;
    }

    public RectPlayer(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);

        idle = new Animation(new Bitmap[]{idleImg}, 2);

        animManager = new AnimationManager(new Animation[]{idle});
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        Rect rectangle1 = null;
        Rect fullScreen = new Rect( 0, 0 ,wide, high);
        if(SceneManager.ACTIVE_SCENE==1) {

            canvas.drawBitmap(idleImg9, rectangle1, fullScreen, paint);
            canvas.drawRect(rectangle, paint);
            canvas.drawBitmap(exitImg, rectangle1, exitButton, paint);
            animManager.draw(canvas, rectangle);
        }
        else if(SceneManager.ACTIVE_SCENE ==0){
            canvas.drawBitmap(idleImg10, rectangle1, fullScreen, paint);
            Rect newbutton = new Rect(wide / 2 - 250, high / 2 - 300, wide / 2 + 250, high / 2);
            canvas.drawBitmap(startbutton, rectangle1, newbutton, paint);
            Rect newbutton1 = new Rect(wide / 2 - 250, high / 2 +200, wide / 2 + 250, high / 2+500);
            canvas.drawBitmap(quitbutton, rectangle1, newbutton1, paint);

            canvas.drawRect(rectangle, paint);
            canvas.drawBitmap(infoImg, rectangle1, infobutton, paint);
            animManager.draw(canvas, rectangle);
        }
        else if(SceneManager.ACTIVE_SCENE ==3){
            canvas.drawBitmap(idleImg9, rectangle1, fullScreen, paint);
            canvas.drawRect(rectangle, paint);
            canvas.drawBitmap(exitImg, rectangle1, exitButton, paint);
            animManager.draw(canvas, rectangle);
        }
    }


    @Override
    public void update(Point playerPoint){
        animManager.update();
    }

    public void update(Point point){
        float oldLeft = rectangle.left;
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);
        int state = 0;
        if(rectangle.left - oldLeft > 5)
            state =0;
        else if(rectangle.left - oldLeft < -5)
            state =0;
        animManager.playAnim(state);
        animManager.update();
    }

}


package com.example.sergiovela.flatlandrevised;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by Sergio Vela on 4/21/2017.
 */

public class Obstacle implements GameObject {
    private Rect rectangle;
    private Rect rectangle2;
    private int color;


    private int wide = Constants.SCREEN_WIDTH;
    //   private int high = Constants.SCREEN_HEIGHT;
    BitmapFactory bf = new BitmapFactory();
    //    Bitmap idleImg9 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.barnacle);
    Bitmap idleImg1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.batone);
    Bitmap idleImg2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.battwo);
    Bitmap idleImg3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.batthree);
    Bitmap idleImg4 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.batfour);
    Bitmap idleImg5 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.batfive);
    Bitmap idleImg6 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.batsix);
    Bitmap idleImg7 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.batseven);
    Bitmap idleImg8 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.bateight);
    //

    private Animation idle;
    private AnimationManager animManager1;

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom +=y;
        rectangle2.top +=y;
        rectangle2.bottom += y;
    }

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap){
        this.color = color;
        //l, t,r,b
        rectangle = new Rect(0, startY, startX, startY+rectHeight);
        rectangle2 = new Rect(startX+playerGap, startY, Constants.SCREEN_WIDTH, startY+rectHeight);

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);
        idle = new Animation(new Bitmap[]{idleImg}, 2);
        animManager1 = new AnimationManager(new Animation[]{idle});//, walkRight, walkLeft});
    }

    public boolean playerCollide(RectPlayer player ){
        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle())  ;
    }

    @Override
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);

        Rect rectangle1= null;
        int rect1width = rectangle.width();
        if(rect1width <= wide/10)
        {
            canvas.drawBitmap(idleImg1, rectangle1, rectangle, paint);
            canvas.drawBitmap(idleImg8, rectangle1, rectangle2, paint);
        }
        else if(rect1width > wide/10 && rect1width < (  2 * (wide/10) ))
        {
            canvas.drawBitmap(idleImg2, rectangle1, rectangle, paint);
            canvas.drawBitmap(idleImg7, rectangle1, rectangle2, paint);
        }

        else if(rect1width > (  2 * (wide/10) ) && rect1width < (  3 * (wide/10) ))
        {
            canvas.drawBitmap(idleImg3, rectangle1, rectangle, paint);
            canvas.drawBitmap(idleImg6, rectangle1, rectangle2, paint);
        }

        else if(rect1width > (  3 * (wide/10) ) && rect1width < (  4 * (wide/10) ))
        {
            canvas.drawBitmap(idleImg4, rectangle1, rectangle, paint);
            canvas.drawBitmap(idleImg5, rectangle1, rectangle2, paint);
        }

        else if(rect1width > (  4 * (wide/10) ) && rect1width < (  5 * (wide/10) ))
        {
            canvas.drawBitmap(idleImg5, rectangle1, rectangle, paint);
            canvas.drawBitmap(idleImg4, rectangle1, rectangle2, paint);
        }

        else if(rect1width > (  5 * (wide/10) ) && rect1width < (  6 * (wide/10) ))
        {
            canvas.drawBitmap(idleImg6, rectangle1, rectangle, paint);
            canvas.drawBitmap(idleImg3, rectangle1, rectangle2, paint);
        }

        else if(rect1width > (  6 * (wide/10) ) && rect1width < (  7 * (wide/10) ))
        {
            canvas.drawBitmap(idleImg7, rectangle1, rectangle, paint);
            canvas.drawBitmap(idleImg2, rectangle1, rectangle2, paint);
        }

        else
        {
            canvas.drawBitmap(idleImg8, rectangle1, rectangle, paint);
            canvas.drawBitmap(idleImg1, rectangle1, rectangle2, paint);
        }

        animManager1.draw(canvas, rectangle);
    }

    @Override
    public void update(){
        animManager1.playAnim(0);
        animManager1.update();

    }

}

package com.example.sergiovela.flatlandrevised;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Sergio Vela on 4/25/2017.
 */

//this is actually the gameplayscene, i just wrote the names wrong and never
//bothered to fix it
public class MainMenuScene implements Scene{

    BitmapFactory bf = new BitmapFactory();
    Bitmap gameovermess = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.barnacle_dead);
    private boolean wantQuit = false;
    private Rect r = new Rect();
    private RectPlayer player1;
    private Point playerPoint1;
    private ObstacleManager obstacleManager1;
    private int counter=0;
    private boolean movingPlayer =false;
    private boolean gameOver =true;
    private long gameOverTime1;
    private Rect exitButton = new Rect( Constants.SCREEN_WIDTH-110, Constants.SCREEN_HEIGHT-100,Constants.SCREEN_WIDTH-10, Constants.SCREEN_HEIGHT-10);
    private OrientationData orientationData;
    private long frameTime1;

    public MainMenuScene(){
        player1 = new RectPlayer(new Rect(100,100,200,200), Color.rgb(255,0,0));
        playerPoint1 = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player1.update(playerPoint1);
        obstacleManager1 = new ObstacleManager(200, 350,75, Color.BLACK);
        orientationData = new OrientationData();
        orientationData.register();
        frameTime1 = System.currentTimeMillis();
        gameOver=true;
    }

    public void reset(){
        playerPoint1 = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player1.update(playerPoint1);
        obstacleManager1 = new ObstacleManager(200, 350,75, Color.BLACK);
        movingPlayer= false;
    }

    @Override
    public void terminate(){
        SceneManager.ACTIVE_SCENE = 1;
    }

    @Override
    public void recieveTouch(MotionEvent event) {

    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player1.getRectangle().contains( (int)event.getX(), (int)event.getY() ))
                    movingPlayer =true;
                if(gameOver && System.currentTimeMillis() - gameOverTime1 >= 2000){
                    reset();
                    gameOver = false;
                    counter++;

                    orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingPlayer && !gameOver)
                    playerPoint1.set((int)event.getX(), (int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer =false;
                break;
        }
    }

    @Override
    public void draw(Canvas canvas){
        player1.draw(canvas);
        obstacleManager1.draw(canvas);
        if(wantQuit){
            wantQuit = false;
            gameOver= true;
            counter=0;
            reset();
            SceneManager.ACTIVE_SCENE=0;
        }
        if(gameOver){
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(100);
            if(counter!=0) {
                Rect emptyRec= null;
                Rect background =new Rect((int)Constants.SCREEN_WIDTH/2-300,(int)Constants.SCREEN_HEIGHT/2-100, (int)Constants.SCREEN_WIDTH/2+300, (int)Constants.SCREEN_HEIGHT/2+100);
                canvas.drawBitmap(gameovermess, emptyRec, background, paint);
            }
            if(counter==0){
                drawCenterText(canvas, paint, "Tap when Ready");
            }
        }
    }

    @Override
    public void update(){
        if (!gameOver) {
            if(frameTime1< Constants.INIT1_TIME1)
                frameTime1 = Constants.INIT1_TIME1;
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime1);
            frameTime1 = System.currentTimeMillis();
            if(orientationData.getOrientation() != null && orientationData.getStartOrientation()!=null) {
                float pitch = orientationData.getOrientation() [1] - orientationData.getStartOrientation() [1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];
                float xSpeed = 2* roll * Constants.SCREEN_WIDTH/1000f;
                float ySpeed = pitch*Constants.SCREEN_HEIGHT/1000f;
                playerPoint1.x += Math.abs(xSpeed*elapsedTime) > 5 ? xSpeed*elapsedTime : 0;
                playerPoint1.y -= Math.abs(ySpeed*elapsedTime) > 5 ? ySpeed*elapsedTime : 0;
            }
            if(playerPoint1.x <0)
                playerPoint1.x =0;
            else if(playerPoint1.x > Constants.SCREEN_WIDTH)
                playerPoint1.x = Constants.SCREEN_WIDTH;
            if(playerPoint1.y <0)
                playerPoint1.y =0;
            else if(playerPoint1.y > Constants.SCREEN_HEIGHT)
                playerPoint1.y = Constants.SCREEN_HEIGHT;
            player1.update(playerPoint1);
            obstacleManager1.update();
            if(obstacleManager1.playerCollide(player1)) {
                gameOver =true;
                gameOverTime1 = System.currentTimeMillis();
            }
            if(Rect.intersects(exitButton, player1.getRectangle())){
                gameOver= true;
                wantQuit= true;
                gameOverTime1 = System.currentTimeMillis();
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {


    }

    private void drawCenterText(Canvas canvas, Paint paint, String text){
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth /2f - r.width() /2f - r.left;
        float y = cHeight /2f + r.height() /2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

}

package com.example.sergiovela.flatlandrevised;

/**
 * Created by Sergio Vela on 4/25/2017.
 */

public class ObstacleManager {
    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int wide = Constants.SCREEN_WIDTH;
    private int high = Constants.SCREEN_HEIGHT;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime;
    private long init1Time;
    private int score =0;
    private int highScore =21;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        startTime = init1Time = System.currentTimeMillis();
        obstacles = new ArrayList<>();
        populateObstacles();
    }

    public boolean playerCollide(RectPlayer player) {
        for (Obstacle ob : obstacles) {
            if (ob.playerCollide(player))
                return true;
        }
        return false;
    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (currY < 0) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
        if (startTime < Constants.INIT1_TIME1)
            startTime = Constants.INIT1_TIME1;
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float) (Math.sqrt((1 + startTime - init1Time) / 2000.0)) * Constants.SCREEN_HEIGHT / (10000.0f);
        for (Obstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            score++;
        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle ob : obstacles) {
            ob.draw(canvas);
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.RED);
            canvas.drawText("High Score: " + highScore, 40, 20 + paint.descent() - paint.ascent(), paint);
            if(score> highScore)
                highScore= score;
            canvas.drawText("Score: " + score, 40, 110 + paint.descent() - paint.ascent(), paint);
        }
    }
}


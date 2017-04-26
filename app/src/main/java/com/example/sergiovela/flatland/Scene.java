package com.example.sergiovela.flatlandrevised;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Sergio Vela on 4/25/2017.
 */

//when creating this class select interface as the kind of class
public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);

}

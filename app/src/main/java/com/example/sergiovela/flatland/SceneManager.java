package com.example.sergiovela.flatlandrevised;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Sergio Vela on 4/25/2017.
 */

//this class is an array of scenes, when you want to create a new
//screen, create a new scene and add it here, in SceneManager()
//the static int ACTIVE_SCENE establishes which screen the app will
//start off on, when trying to get a new scene to work, try changing the value
//so the app starts on that scene
public class SceneManager {
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE=0;

    public SceneManager(){
        scenes.add(new GameplayScene());
        scenes.add(new MainMenuScene());
        scenes.add(new InformationScene());
    }

    public void recieveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}


package com.example.sergiovela.flatlandrevised;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Sergio Vela on 4/25/2017.
 */

//this class controls the gyroscope
public class OrientationData implements SensorEventListener {

    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private float[] startOrientation = null;

    private float[] accelOutput;
    private float[] magOutput;
    private float[] orientation = new float[3];

    public float[] getOrientation(){
        return orientation;
    }


    public float[] getStartOrientation(){
        return startOrientation;
    }
    public void newGame() {
        startOrientation = null;
    }
    public OrientationData() {
        manager = (SensorManager) Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }
    public void register () {
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause() {
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged (Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelOutput = event.values;
        }
        else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magOutput = event.values;
            if(accelOutput!=null && magOutput !=null){
                float[] R = new float[9];
                float[] I = new float[8];
                boolean sucess = SensorManager.getRotationMatrix(R,I,accelOutput,magOutput);
                if(sucess) {
                    SensorManager.getOrientation(R, orientation);
                    if(startOrientation==null){
                        startOrientation = new float[orientation.length];
                        System.arraycopy(orientation, 0 ,startOrientation,0,orientation.length);
                    }
                }
            }
        }
    }

}


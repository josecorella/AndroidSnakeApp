package com.example.jose.androidsnakeapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.example.jose.androidsnakeapp.driver.GameDriver;
import com.example.jose.androidsnakeapp.enumerators.Direction;
import com.example.jose.androidsnakeapp.enumerators.GameCondition;
import com.example.jose.androidsnakeapp.structure.SnakeStructure;

public class MainActivity extends AppCompatActivity implements OnTouchListener {
    private GameDriver gameDriver;
    private SnakeStructure snakeStructure;

    private final Handler handler = new Handler();
    public static long delay= 150;

    private float prevXCoord, prevYCoord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameDriver = new GameDriver();
        gameDriver.beginGame();

        snakeStructure = (SnakeStructure) findViewById(R.id.SnakeStructure);
        snakeStructure.setOnTouchListener(this);
        startHandler();

    }
    private void startHandler(){
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                gameDriver.update();
                if (gameDriver.getCurrentType() == GameCondition.Running){
                    handler.postDelayed(this, delay);
                } if(gameDriver.getCurrentType() == GameCondition.Lost){
                    GameLost();
                }
                snakeStructure.setSnakeStructreMap(gameDriver.getMap());
                snakeStructure.invalidate();
            }
        },delay);
    }

    private void GameLost() {
        Toast.makeText(this, "YOU LOST", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevXCoord = event.getX();
                prevYCoord = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                float newXCoord = event.getX();
                float newYCoord = event.getY();

                //where in screen did we swipe
                if(Math.abs(newXCoord - prevXCoord) > Math.abs(newYCoord - prevYCoord)){
                    //left - right movement
                    if(newXCoord > prevXCoord){
                        gameDriver.updateDirection(Direction.Left);
                    } else{
                        gameDriver.updateDirection(Direction.Right);
                    }

                } else {
                    if(newYCoord > prevYCoord){
                        gameDriver.updateDirection(Direction.Down);
                    } else{
                        gameDriver.updateDirection(Direction.Up);
                    }
                }
        }
        return true;
    }
}


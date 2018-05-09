package com.example.jose.androidsnakeapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.jose.androidsnakeapp.driver.GameDriver;
import com.example.jose.androidsnakeapp.enumerators.Direction;
import com.example.jose.androidsnakeapp.enumerators.GameCondition;
import com.example.jose.androidsnakeapp.structure.SnakeStructure;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private GameDriver gameDriver;
    private SnakeStructure snakeStructure;

    private final Handler handler = new Handler();
    private final long delay= 200;

    private float prevXcoord, prevYcoord;

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
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevXcoord = event.getX();
                prevYcoord = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float newXcoord = event.getX();
                float newYcoord = event.getY();

                if(Math.abs(newXcoord -prevXcoord) > Math.abs(newYcoord - prevYcoord)){
                    // left right
                    if (newXcoord > prevXcoord){
                        //right
                        gameDriver.updateDirection(Direction.Left);
                    }else{
                        //left
                        gameDriver.updateDirection(Direction.Right);
                    }
                } else{
                    if(newYcoord > prevYcoord){
                        //up
                        gameDriver.updateDirection(Direction.Down);
                    } else{
                        //down
                        gameDriver.updateDirection(Direction.Up);
                    }
                }
                break;



        }
        return true;
    }
}

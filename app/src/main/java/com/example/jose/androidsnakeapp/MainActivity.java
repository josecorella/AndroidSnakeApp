package com.example.jose.androidsnakeapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jose.androidsnakeapp.driver.GameDriver;
import com.example.jose.androidsnakeapp.enumerators.GameCondition;
import com.example.jose.androidsnakeapp.structure.SnakeStructure;

public class MainActivity extends AppCompatActivity {
    private GameDriver gameDriver;
    private SnakeStructure snakeStructure;

    private final Handler handler = new Handler();
    private final long delay= 150;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameDriver = new GameDriver();
        gameDriver.beginGame();

        snakeStructure = (SnakeStructure) findViewById(R.id.SnakeStructure);
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
}

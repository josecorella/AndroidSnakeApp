package com.example.jose.androidsnakeapp.structure;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.graphics.Paint;
import android.view.View;

import com.example.jose.androidsnakeapp.enumerators.GridType;

public class SnakeStructure extends View{
    private Paint ePaint = new Paint();

    private GridType snakeStructreMap[][];

    public SnakeStructure(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSnakeStructreMap(GridType[][] map){
        this.snakeStructreMap = map;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if( snakeStructreMap != null) {
            //if Map has been set yet
            float gridSizeX = canvas.getWidth() / snakeStructreMap.length;
            float gridSizeY = canvas.getHeight() / snakeStructreMap[0].length;


            for (int i = 0; i < snakeStructreMap.length; i++) {
                for (int j = 0; j < snakeStructreMap[i].length; j++) {
                    switch (snakeStructreMap[i][j]){
                        case Empty:
                            ePaint.setColor(Color.WHITE);
                            break;
                        case Walls:
                            ePaint.setColor(Color.BLACK);
                            break;
                        case SnakeHead:
                            ePaint.setColor(Color.BLUE);
                            break;
                        case SnakeTail:
                            ePaint.setColor(Color.BLACK);
                            break;
                        case Food:
                            ePaint.setColor(Color.BLUE);
                            break;
                    }
                    canvas.drawRect((i * gridSizeX) + 1, (j * gridSizeY) + 1, (i * gridSizeX) + (gridSizeX - 1), (j * gridSizeY) + (gridSizeY - 1), ePaint);
                }
            }
        }
    }
}

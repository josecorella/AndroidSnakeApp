package com.example.jose.androidsnakeapp.driver;

import com.example.jose.androidsnakeapp.enumerators.Direction;
import com.example.jose.androidsnakeapp.enumerators.GameCondition;
import com.example.jose.androidsnakeapp.enumerators.GridType;
import com.example.jose.androidsnakeapp.support.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameDriver {
    public int GameWidth = 28;
    public int GameHeight = 42;

    //for the walls in the game we are not going to use natural edges of the screen we
    //are going to make a wall around the edge of the screen which will be a coordinate "type" and stored in an array
    private List<Coordinate> walls = new ArrayList<>(); //these will not change as much because walls are not changing with the movement of the snake.
    private List<Coordinate> snake = new ArrayList<>(); //this array will change constantly

    private Direction currentDirection = Direction.Left;
    private GameCondition currentCondition = GameCondition.Running;

    private GridType[][] gameMap;

    private Coordinate food;
    private boolean hasFood = false;


    public GameDriver() {
        gameMap = new GridType[GameWidth][GameHeight]; //two dimensional array with the size of the screen
    }
    
    public void beginGame(){
        createSnake();
        generateWalls();
    }

    public void update(){
        // check for wall collision - go through wall
        for (Coordinate c: walls) {
            if (snake.get(0).equals(c)) {
                if (c.getX() == GameWidth - 1) {
                    snake.get(0).setX(1);
                }
                if (c.getX() == 0) {
                    snake.get(0).setX(GameWidth - 2);
                }
                if (c.getY() == GameHeight - 1) {
                    snake.get(0).setY(1);
                }
                if (c.getY() == 0) {
                    snake.get(0).setY(GameHeight - 2);
                }
            }
        }

        //collision check - change condition to lost if snake head hits snake body
        for (Coordinate c: snake.subList(1, snake.size() - 1)) {
            if(snake.get(0).equals(c)){
                currentCondition = GameCondition.Lost;
            }
        }

        // update snake location
        switch (currentDirection){
            case Up:
                updateDirection(0, -1);
                break;
            case Left:
                updateDirection(1, 0);
                break;
            case Down:
                updateDirection(0, 1);
                break;
            case Right:
                updateDirection(-1, 0);
                break;
        }

        // creates food if food isn't already there / was eaten
        if (!hasFood) {
            createFood();
        }

        // detects if snake touches food
        if (snake.get(0).equals(food)) {
            hasFood = false;
            // set food location to 0,0 while food randomizer is working
            food.setX(0);
            food.setY(0);
            // increase snake length from eating food
            snake.add(new Coordinate(snake.get(snake.size() -1).getX(), snake.get(snake.size() -1).getY()));
        }

    }

    public void updateDirection(Direction newDirection){
        // cannot swipe the opposite direction
        if(Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1){
            currentDirection = newDirection;
        }
    }

    //create a two dimensional array that will map out the game map with the enums we created
    public GridType[][] getMap(){
        //first set the grid to be empty
        for (int x = 0; x < GameWidth ; x++) {
            for (int y = 0; y < GameHeight; y++) {
                gameMap[x][y] = GridType.Empty;
            }
        }

        for (Coordinate p : snake) {
            gameMap[p.getX()][p.getY()] = GridType.SnakeTail;
        }
        gameMap[snake.get(0).getX()][snake.get(0).getY()] = GridType.SnakeHead;

        //fill the outside grid
        for (Coordinate wall: walls) {  //for each loop //for each coordinate type object inside the size of the walls array
            gameMap[wall.getX()][wall.getY()] = GridType.Walls; //make the outside border of the two dimensional array a type enum Walls
        }

        if (hasFood) {
            gameMap[food.getX()][food.getY()] = GridType.Food;
        }

        return gameMap; //return the now filled gameMap
    }

    private void updateDirection(int x, int y){
        //this function works by udpating things from the back of the body, "tail", to the head. 
        for (int i = snake.size() -1; i > 0; i--) {
            snake.get(i).setX(snake.get(i-1).getX());   //this will update things in the horizontal direction
            snake.get(i).setY(snake.get(i-1).getY());   //this will update things in the vertical direction
        }
        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);


    }

    private void generateWalls() {      //private so that other classes or MainActivity cant access here
        //add top and bottom walls
        for (int x = 0; x < GameWidth; x++) {
            walls.add(new Coordinate(x,0)); //top wall
            walls.add(new Coordinate(x, (GameHeight - 1) ));   //bottom wall
        }
        //add left and right walls
        for (int y = 0; y < GameHeight; y++) {
            walls.add(new Coordinate(0, y));    //left wall
            walls.add(new Coordinate((GameWidth - 1), y )); //right wall
        }
    }
    private void createSnake() {    //private so that other classes or MainActivity cant access here
        snake.clear(); //this is here so that when a new game begins its starts from the default position.

        snake.add(new Coordinate(7, 7));
        snake.add(new Coordinate(6, 7));
        snake.add(new Coordinate(5, 7));
        snake.add(new Coordinate(4, 7));
        snake.add(new Coordinate(3, 7));
    }

    // creates food in empty locations
    private void createFood() {
        Random random = new Random();
        int newX = random.nextInt(26);
        int newY = random.nextInt(40);
        if (gameMap[newX][newY] == GridType.Empty) {
            food = new Coordinate(newX, newY);
            hasFood = true;
        }
    }

    public GameCondition getCurrentType(){
        return currentCondition;
    }



}

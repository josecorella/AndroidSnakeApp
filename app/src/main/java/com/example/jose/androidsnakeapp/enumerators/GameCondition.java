package com.example.jose.androidsnakeapp.enumerators;

public enum GameCondition {
    Ready,  //When the game starts or after player looses
    Running, //While the game is going
    Lost    //After player looses
}

/*
Notice that there is no Win state since you cant actually win this game you can only loose.
How well you do depends on your score and there is no max score.
 */

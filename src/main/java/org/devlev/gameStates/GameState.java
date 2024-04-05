package org.devlev.gameStates;

public enum GameState {

    PLAYING,
    MENU,
    OPTIONS,
    QUIT;

    public static GameState currentGameState = MENU;

}

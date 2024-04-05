package org.devlev.gameStates;

import org.devlev.Game;

public abstract class AbstractGameState {

    protected final Game gameInstance;

    protected AbstractGameState(Game gameInstance) {
        this.gameInstance = gameInstance;
    }

    public Game getGameInstance() {
        return this.gameInstance;
    }
}

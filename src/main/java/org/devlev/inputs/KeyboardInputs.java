package org.devlev.inputs;

import org.devlev.GamePanel;
import org.devlev.gameStates.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private final GamePanel gamePanel; //Create gamePanel variable

    public KeyboardInputs(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel; //Set gamePanel to constructor variable
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        //EMPTY
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (GameState.currentGameState)
        {
            case MENU:
                this.gamePanel.getGameInstance().getMenuState().keyPressed(e);
                break;
            case PLAYING:
                this.gamePanel.getGameInstance().getPlayingState().keyPressed(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (GameState.currentGameState)
        {
            case MENU:
                this.gamePanel.getGameInstance().getMenuState().keyReleased(e);
                break;
            case PLAYING:
                this.gamePanel.getGameInstance().getPlayingState().keyReleased(e);
                break;
        }
    }
}

package org.devlev.inputs;

import org.devlev.GamePanel;
import org.devlev.gameStates.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel; //GamePanel variable to modify screen

    public MouseInputs(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel; //Set gamePanel to constructor variable
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        switch (GameState.currentGameState)
        {
            case PLAYING:
                this.gamePanel.getGameInstance().getPlayingState().mouseClicked(e);
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        switch (GameState.currentGameState)
        {
            case MENU:
                this.gamePanel.getGameInstance().getMenuState().mousePressed(e);
                break;
            case PLAYING:
                this.gamePanel.getGameInstance().getPlayingState().mousePressed(e);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        switch (GameState.currentGameState)
        {
            case MENU:
                this.gamePanel.getGameInstance().getMenuState().mouseReleased(e);
                break;
            case PLAYING:
                this.gamePanel.getGameInstance().getPlayingState().mouseReleased(e);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e)
    {
        switch (GameState.currentGameState)
        {
            case PLAYING:
                this.gamePanel.getGameInstance().getPlayingState().mouseDragged(e);
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        //this.gamePanel.setRectPosition(e.getX(), e.getY()); //Set new screen pos to rect
        switch (GameState.currentGameState)
        {
            case MENU:
                this.gamePanel.getGameInstance().getMenuState().mouseMoved(e);
                break;
            case PLAYING:
                this.gamePanel.getGameInstance().getPlayingState().mouseMoved(e);
                break;
        }
    }
}

package org.devlev;

import lombok.Getter;
import org.devlev.gameStates.GameState;
import org.devlev.gameStates.MenuState;
import org.devlev.gameStates.PlayingState;

import java.awt.*;

public class Game implements Runnable {

    //Create all game variables
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    //Calculate game size
    private static final int DEFAULT_TILE_SIZE = 32;
    public static final float SCALE = 1.5F;
    public static final int TILES_WIDTH = 26;
    public static final int TILES_HEIGHT = 14;
    public static final int TILES_SIZE = (int)(DEFAULT_TILE_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_HEIGHT;

    private Thread gameThread; //Thread of the game
    private final int FPS_SET = 120; //Fps of the gameLoop
    private final int UPS_SET = 200; //Updates per sec of the game

    @Getter
    private MenuState menuState;
    @Getter
    private PlayingState playingState;

    public Game()
    {
        init(); //Initialize Game classes

        this.gamePanel = new GamePanel(this); //Create new gamePanel instance
        this.gameWindow = new GameWindow(gamePanel); //Create new gameWindow instance

        //Allows class to have input focus
        this.gamePanel.requestFocus();

        //Start game loop
        startGameThread();
    }

    private void init()
    {
        this.menuState = new MenuState(this);
        this.playingState = new PlayingState(this);
    }

    private void startGameThread()
    {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public void update()
    {
        switch (GameState.currentGameState)
        {
            case MENU:
                this.menuState.update();
                break;
            case PLAYING:
                this.playingState.update();
                break;
            default: break;
        }
    }

    public void render(Graphics graphics)
    {
        switch (GameState.currentGameState)
        {
            case MENU:
                this.menuState.draw(graphics);
                break;
            case PLAYING:
                this.playingState.draw(graphics);
                break;
            case OPTIONS:
            case QUIT:
            default: System.exit(0);
        }
    }

    public void gameFocusLost()
    {
        if (GameState.currentGameState == GameState.PLAYING) {
            this.playingState.gameFocusLost();
        }
    }

    @Override
    public void run()
    {

        double timePerFrame = 1000000000.0 / this.FPS_SET; //Frames in nano seconds
        double updatePerFrame = 1000000000.0 / this.UPS_SET; //Updates in nano seconds

        double delataU = 0;
        double deltaF = 0;
        long currentTime;
        long previousTime = System.nanoTime();

        //Frame stats checker
        int frames = 0;
        int updates = 0;
        long lastFramesCheck = System.currentTimeMillis();

        while (true)
        {
            currentTime = System.nanoTime();

            delataU += (currentTime - previousTime) / updatePerFrame;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (delataU >= 1)
            {
                update();
                updates++;
                delataU--;
            }
            if (deltaF >= 1) //Checks and sees if we need to update screen
            {
                this.gamePanel.repaint(); //Update display on screen
                frames++;
                deltaF--;
            }
            /*if (currentFrame - lastFrame >= timePerFrame) //Checks and sees if we need to update screen
            {
                this.gamePanel.repaint(); //Update display on screen
                lastFrame = currentFrame;
                frames++;
            }*/

            if (System.currentTimeMillis() - lastFramesCheck >= 1000)
            {
                lastFramesCheck = System.currentTimeMillis();
                System.out.println("FPS : " + frames + " UPS : " + updates);
                frames = 0;
                updates = 0;
            }

        }

    }
}

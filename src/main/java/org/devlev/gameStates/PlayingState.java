package org.devlev.gameStates;

import org.devlev.Game;
import org.devlev.entities.PlayerEntity;
import org.devlev.level.LevelHandler;
import org.devlev.ui.PauseScreenUI;
import org.devlev.utils.GameFile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.devlev.utils.GameConstants.UXElements.EnvironmentUX.*;

public class PlayingState extends AbstractGameState  implements IGameState {

    //Load in level
    private LevelHandler levelHandler;

    //Create entity variables
    private PlayerEntity playerEntity;

    //Get skyBackground image
    private BufferedImage skyBackgroundImg, bigCloudsImg;

    //Store level map data
    private int xLvlOffset;
    //"moves" map when player crosses over border
    private int leftDisplayBorder = (int)(0.2 * Game.GAME_WIDTH);
    private int rightDisplayBorder = (int)(0.8 * Game.GAME_WIDTH);
    //Total width of the map
    private int levelMapWidth = GameFile.getLevelData()[0].length;
    //Max length players can move
    private int maxTitlesOffset = levelMapWidth - Game.TILES_WIDTH;
    //Max length in pixels
    private int maxLevelOffsetX = maxTitlesOffset * Game.TILES_SIZE;


    //Load in the game pause menu
    private PauseScreenUI pauseScreen;
    private boolean paused = false; //Check if the game is paused

    public PlayingState(Game gameInstance) { //Get gameInstance from constructor
        super(gameInstance);
        loadEnvironment();
        init(); //Initiate level and entity
    }

    private void init()
    {
        this.levelHandler = new LevelHandler(gameInstance); //Initialize game level handler
        this.playerEntity = new PlayerEntity(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE)); //Initialize playerEntity
        //Loads the level data to the player class
        this.playerEntity.loadCurrentLevelData(this.levelHandler.getCurrentLevel().getLevelData());
        //Load pauseScreen menu
        this.pauseScreen = new PauseScreenUI(this);
    }

    private void loadEnvironment()
    {
        //load in sky background image
        this.skyBackgroundImg = GameFile.getSpriteImage(GameFile.PLAYING_BG_IMG);
        //Load in big cloud image
        this.bigCloudsImg = GameFile.getSpriteImage(GameFile.BIG_CLOUDS);
    }

    @Override
    public void update()
    {
        if (!paused)
        {
            this.levelHandler.update();
            this.playerEntity.update();
            calculateLevelOffset();
        } else {
            //Call update method for pause screen menu
            this.pauseScreen.update();
        }
    }

    @Override
    public void draw(Graphics graphics)
    {
        //Draw sky background image
        graphics.drawImage(this.skyBackgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        //Draw the cloud images
        drawMovingClouds(graphics);

        this.levelHandler.draw(graphics, this.xLvlOffset);
        this.playerEntity.render(graphics, this.xLvlOffset);
        //Draw pause screen (temp) ontop of game
        //TODO: Remove method and move to a specific key pressed
        if (paused)
        {
            graphics.setColor(new Color(0,0,0, 135));
            graphics.fillRect(0,0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            this.pauseScreen.draw(graphics);
        }
    }

    private void drawMovingClouds(Graphics graphics)
    {
        for (int i = 0; i < 3; i++)
            graphics.drawImage(
                    this.bigCloudsImg,
                    i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3),
                    (int) (204 * Game.SCALE),
                    BIG_CLOUD_WIDTH,
                    BIG_CLOUD_HEIGHT, null);
    }

    private void calculateLevelOffset()
    {
        int playerHitboxXPosition = (int)this.playerEntity.getEntityHitbox().x;
        int xPositionDiff = playerHitboxXPosition - this.xLvlOffset; //Gets the xPosition differance
        //Calculates to see if a player is closer to the left or right
        if (xPositionDiff > this.rightDisplayBorder)
            this.xLvlOffset += xPositionDiff - this.rightDisplayBorder;
        else if (xPositionDiff < this.leftDisplayBorder)
            this.xLvlOffset += xPositionDiff - this.leftDisplayBorder;

        if (this.xLvlOffset > this.maxLevelOffsetX)
            this.xLvlOffset = this.maxLevelOffsetX;
        else if (this.xLvlOffset < 0)
            this.xLvlOffset = 0;
    }

    public void unpauseGame() {this.paused = false;}

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) //Set player attacking to true
            this.playerEntity.setPlayerAttacking(true);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        if (this.paused) {
            this.pauseScreen.mousePressed(mouseEvent);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
        if (this.paused) {
            this.pauseScreen.mouseReleased(mouseEvent);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        if (this.paused) {
            this.pauseScreen.mouseMoved(mouseEvent);
        }
    }

    public void mouseDragged(MouseEvent mouseEvent)
    {
        if (this.paused) {
            this.pauseScreen.mouseDragged(mouseEvent);
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        //Switch through key input types
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_A:
                this.playerEntity.setMovingLeft(true);
                break;
            case KeyEvent.VK_D:
                this.playerEntity.setMovingRight(true);
                break;
            case KeyEvent.VK_SPACE:
                this.playerEntity.setJumping(true);
                break;
            case KeyEvent.VK_P:
                this.paused = !this.paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent)
    {
        //Changes player state from moving to idle once key is released
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_A:
                this.playerEntity.setMovingLeft(false);
                break;
            case KeyEvent.VK_D:
                this.playerEntity.setMovingRight(false);
                break;
            case KeyEvent.VK_SPACE:
                this.playerEntity.setJumping(false);
                break;
        }
    }

    public void gameFocusLost()
    {
        this.playerEntity.resetPlayerMovement(); //Resets player movement when focus is lost
    }

    public PlayerEntity getPlayerEntity() {
        return this.playerEntity;
    }

}

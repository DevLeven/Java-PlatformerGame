package org.devlev.ui;

import org.devlev.Game;
import org.devlev.gameStates.GameState;
import org.devlev.gameStates.PlayingState;
import org.devlev.ui.buttons.SoundButtonUI;
import org.devlev.ui.buttons.URMButtonUI;
import org.devlev.ui.buttons.VolumeButtonUI;
import org.devlev.utils.GameFile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.devlev.utils.GameConstants.UIElements.PauseButtonUI.SOUND_SIZE;
import static org.devlev.utils.GameConstants.UIElements.URMButtonUI.URM_SIZE;
import static org.devlev.utils.GameConstants.UIElements.VolumeButtonUI.*;

public class PauseScreenUI {

    private BufferedImage pauseScreenBackground; //Variable that holds the pause screen backdrop
    private int xPosition, yPosition, width, height; //Determine background placement

    //Access playing state
    private final PlayingState playingState;

    //Load pause screen buttons
    private SoundButtonUI musicButton, sfxButton;
    //Load menu urm buttons
    private URMButtonUI menuButton, replayButton, unpauseButton;
    //Load volume button
    private VolumeButtonUI volumeButton;

    public PauseScreenUI(PlayingState playingState)
    {
        this.playingState = playingState; //Initialize playing state
        loadPauseScreenBackdrop(); //Loads background for the pause screen, sets position for backdrop
        initializeSoundButtons(); //Loads sound buttons for the pause screen
        initializeURMButtons(); //Loads in the menu urn buttons
        initializeVolumeButton(); //Loads volume button and slider for pause menu
    }

    public void draw(Graphics graphics)
    {
        //Draws pause menu on screen
        graphics.drawImage(
                this.pauseScreenBackground,
                this.xPosition,
                this.yPosition,
                this.width,
                this.height,
                null
        );
        //Draws pause menu sound buttons on screen
        this.musicButton.draw(graphics);
        this.sfxButton.draw(graphics);
        //Draw pause menu urm buttons
        this.menuButton.draw(graphics);
        this.replayButton.draw(graphics);
        this.unpauseButton.draw(graphics);
        //Draw volume button
        this.volumeButton.draw(graphics);
    }

    public void update()
    {
        //Call update method for buttons
        this.musicButton.update();
        this.sfxButton.update();
        //Call update methods for urm buttons
        this.menuButton.update();
        this.replayButton.update();
        this.unpauseButton.update();
        //Update volume button
        this.volumeButton.update();
    }

    private void initializeSoundButtons()
    {
        //Set position for each sound button
        int soundButtonX = (int) (450 * Game.SCALE);
        int musicButtonY = (int) (140 * Game.SCALE);
        int sfxButtonY = (int) (186 * Game.SCALE);
        this.musicButton = new SoundButtonUI(soundButtonX, musicButtonY, SOUND_SIZE, SOUND_SIZE); //Load in music button
        this.sfxButton = new SoundButtonUI(soundButtonX, sfxButtonY, SOUND_SIZE, SOUND_SIZE); //Load in sfx button
    }

    private void initializeURMButtons()
    {
        //Create button x and y locations
        int menuButtonX = (int) (313 * Game.SCALE);
        int replayButtonX = (int) (387 * Game.SCALE);
        int unpauseButtonX = (int) (462 * Game.SCALE);
        int buttonY = (int) (325 * Game.SCALE);

        //Create buttons
        this.menuButton = new URMButtonUI(menuButtonX, buttonY, URM_SIZE, URM_SIZE, 2);
        this.replayButton = new URMButtonUI(replayButtonX, buttonY, URM_SIZE, URM_SIZE, 1);
        this.unpauseButton = new URMButtonUI(unpauseButtonX, buttonY, URM_SIZE, URM_SIZE, 0);
    }

    private void initializeVolumeButton()
    {
        //X and Y coords for the button and slider
        int volumeButtonX = (int) (309 * Game.SCALE);
        int volumeButtonY = (int) (278 * Game.SCALE);
        //Initialize the volume button class with coords defined above
        this.volumeButton = new VolumeButtonUI(volumeButtonX, volumeButtonY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void loadPauseScreenBackdrop()
    {
        //Load in pause screen background image
        this.pauseScreenBackground = GameFile.getSpriteImage(GameFile.PAUSE_MENU_BACKGROUND);
        //Set position for pause screen
        this.width = (int) (this.pauseScreenBackground.getWidth() * Game.SCALE);
        this.height = (int) (this.pauseScreenBackground.getHeight() * Game.SCALE);
        this.xPosition = Game.GAME_WIDTH / 2 - this.width / 2; //Position it in the middle of the screen
        this.yPosition = (int) (25 * Game.SCALE);
    }


    public void mouseDragged(MouseEvent mouseEvent)
    {
        if (this.volumeButton.isMousePressed())
            this.volumeButton.changeButtonXPosition(mouseEvent.getX()); //Set new xPos
    }

    public void mousePressed(MouseEvent mouseEvent)
    {
        if (this.musicButton.isInButtonBounds(mouseEvent))
            this.musicButton.setMousePressed(true);
        else if (this.sfxButton.isInButtonBounds(mouseEvent))
        {
            this.sfxButton.setMousePressed(true);
        }
        else if (this.menuButton.isInButtonBounds(mouseEvent))
        {
            this.menuButton.setMousePressed(true);
        }
        else if (this.replayButton.isInButtonBounds(mouseEvent))
        {
            this.replayButton.setMousePressed(true);
        }
        else if (this.unpauseButton.isInButtonBounds(mouseEvent))
        {
            this.unpauseButton.setMousePressed(true);
        }
        else if (this.volumeButton.isInButtonBounds(mouseEvent))
        {
            this.volumeButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent mouseEvent)
    {
        if (this.musicButton.isInButtonBounds(mouseEvent))
        {
            if (this.musicButton.isMousePressed())
                this.musicButton.setButtonMuted(!this.musicButton.isButtonMuted());
        }
        else if (this.sfxButton.isInButtonBounds(mouseEvent)) {
            if (this.sfxButton.isMousePressed())
                this.sfxButton.setButtonMuted(!this.sfxButton.isButtonMuted());
        }
        //Reset button mouse events
        this.musicButton.resetMouseEvents();
        this.sfxButton.resetMouseEvents();
        //Set mouse released for urm buttons
        if (this.menuButton.isInButtonBounds(mouseEvent))
        {
            if (this.menuButton.isMousePressed())
            {
                GameState.currentGameState = GameState.MENU;
                this.playingState.unpauseGame();
            }
        }
        else if (this.replayButton.isInButtonBounds(mouseEvent)) {
            if (this.replayButton.isMousePressed()) {}
                //TODO: Start the level all over from scratch
        }
        else if (this.unpauseButton.isInButtonBounds(mouseEvent))
        {
            if (this.unpauseButton.isMousePressed())
                this.playingState.unpauseGame();
        }
        //Reset button mouse events
        this.menuButton.resetMouseEvents();
        this.replayButton.resetMouseEvents();
        this.unpauseButton.resetMouseEvents();
    }

    public void mouseMoved(MouseEvent mouseEvent)
    {
        //Reset booleans first
        this.musicButton.setMouseHover(false);
        this.sfxButton.setMouseHover(false);

        //Do Checks
        if (this.musicButton.isInButtonBounds(mouseEvent))
            this.musicButton.setMouseHover(true);
        else if (this.sfxButton.isInButtonBounds(mouseEvent)) {
            this.sfxButton.setMouseHover(true);
        }

        //Reset urm button booleans
        this.menuButton.resetMouseEvents();
        this.replayButton.resetMouseEvents();
        this.unpauseButton.resetMouseEvents();

        //Do button checks
        if (this.menuButton.isInButtonBounds(mouseEvent))
            this.menuButton.setMouseHover(true);
        else if (this.replayButton.isInButtonBounds(mouseEvent)) {
            this.replayButton.setMouseHover(true);
        }
        else if (this.unpauseButton.isInButtonBounds(mouseEvent)) {
            this.unpauseButton.setMouseHover(true);
        }

        //Reset volume button
        this.volumeButton.resetMouseEvents();
        //Do button check
        if (this.volumeButton.isInButtonBounds(mouseEvent))
            this.volumeButton.setMouseHover(true);
    }

}

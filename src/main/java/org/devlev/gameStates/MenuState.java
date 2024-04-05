package org.devlev.gameStates;

import org.devlev.Game;
import org.devlev.ui.buttons.MenuButtonUI;
import org.devlev.utils.GameFile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MenuState extends AbstractGameState implements IGameState {


    private final MenuButtonUI[] menuButtonArray = new MenuButtonUI[3]; //Holds menu buttons

    //Variables for menu background and background position
    private BufferedImage menuBackground, menuBackgroundImage;
    private int backdropXPos, backdropYPos, backdropWidth, backdropHeight;

    public MenuState(Game gameInstance) {
        super(gameInstance);
        loadMenuButtons();
        loadMenuBackdrop();
        this.menuBackgroundImage = GameFile.getSpriteImage(GameFile.MENU_BACKGROUND_IMAGE);
    }

    private void loadMenuBackdrop()
    {
        this.menuBackground = GameFile.getSpriteImage(GameFile.MENU_BACKGROUND); //Load in menu background
        //Initialize menu background position variables
        this.backdropWidth = (int) (this.menuBackground.getWidth() * Game.SCALE);
        this.backdropHeight = (int) (this.menuBackground.getHeight() * Game.SCALE);
        this.backdropXPos = Game.GAME_WIDTH / 2 - this.backdropWidth / 2;
        this.backdropYPos = (int) (45 * Game.SCALE);
    }


    private void loadMenuButtons()
    {
        this.menuButtonArray[0] = new MenuButtonUI(
                Game.GAME_WIDTH / 2,
                (int) (150 * Game.SCALE),
                0,
                GameState.PLAYING
        );
        this.menuButtonArray[1] = new MenuButtonUI(
                Game.GAME_WIDTH / 2,
                (int) (220 * Game.SCALE),
                1,
                GameState.OPTIONS
        );
        this.menuButtonArray[2] = new MenuButtonUI(
                Game.GAME_WIDTH / 2,
                (int) (290 * Game.SCALE),
                2,
                GameState.QUIT
        );
    }

    @Override
    public void update() {
        for (MenuButtonUI menuButton : this.menuButtonArray) {
            menuButton.update();
        }
    }

    @Override
    public void draw(Graphics graphics)
    {
        //Draws menu backdrop image
        graphics.drawImage(this.menuBackgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        graphics.drawImage( //Load in menu background
                this.menuBackground,
                this.backdropXPos,
                this.backdropYPos,
                this.backdropWidth,
                this.backdropHeight,
                null
        );
        for (MenuButtonUI menuButton : this.menuButtonArray) { //Load in menu buttons
            menuButton.draw(graphics);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        for (MenuButtonUI menuButton : this.menuButtonArray)
        {
            if (menuButton.isInButtonBounds(mouseEvent)) {
                menuButton.setMousePressedButton(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
        for (MenuButtonUI menuButton : this.menuButtonArray)
        {
            if (menuButton.isInButtonBounds(mouseEvent)) {
                if (menuButton.isMousePressedButton())
                    menuButton.updateGameState();
                break;
            }
        }
        resetMenuButtons();
    }

    private void resetMenuButtons()
    {
        for (MenuButtonUI menuButton : this.menuButtonArray) {
            menuButton.resetMouseButtonAction();
        }
    }


    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        for (MenuButtonUI menuButton : this.menuButtonArray) {
            menuButton.setMouseOverButton(false);
        }
        for (MenuButtonUI menuButton : this.menuButtonArray)
        {
            if (menuButton.isInButtonBounds(mouseEvent)) {
                menuButton.setMouseOverButton(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
            GameState.currentGameState = GameState.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

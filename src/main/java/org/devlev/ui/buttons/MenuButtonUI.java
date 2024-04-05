package org.devlev.ui.buttons;

import lombok.Getter;
import lombok.Setter;
import org.devlev.gameStates.GameState;
import org.devlev.utils.GameFile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.devlev.utils.GameConstants.UIElements.MenuButtonUI.*;

public class MenuButtonUI extends AbstractButtonUI {

    private int rowIndex, imageIndex; //get specific button type
    //Change the state of the game depending on button clicked
    private GameState gameState;
    private BufferedImage[] buttonImageStates; //Store each Image state in array
    @Getter @Setter
    private boolean mouseOverButton, mousePressedButton;

    public MenuButtonUI(int xPosition, int yPosition, int rowIndex, GameState gameState) {
        super(xPosition - B_WIDTH / 2, yPosition, B_WIDTH, B_HEIGHT);
        this.rowIndex = rowIndex;
        this.gameState = gameState;
        loadButtonImage();
    }

    @Override
    public void draw(Graphics graphics)
    {
        graphics.drawImage(this.buttonImageStates[imageIndex],
                this.buttonXPosition,
                this.buttonYPosition,
                B_WIDTH, B_HEIGHT,
                null
        );
    }

    @Override
    public void update()
    {
        this.imageIndex = 0;
        if (mouseOverButton)
            this.imageIndex = 1;
        else if (mousePressedButton) {
            this.imageIndex = 2;
        }
    }

    public boolean isInButtonBounds(MouseEvent event) {
        return this.buttonBoundBox.getBounds().contains(event.getX(), event.getY());
    }

    public void resetMouseButtonAction()
    {
        this.mouseOverButton = false;
        this.mousePressedButton = false;
    }

    public void updateGameState() {GameState.currentGameState = this.gameState;}

    private void loadButtonImage()
    {
        this.buttonImageStates = new BufferedImage[3]; //Initialize button array
        BufferedImage loadedImage = GameFile.getSpriteImage(GameFile.MENU_BUTTONS);

        //Stream through image and save subImages in array
        for (int i = 0; i < this.buttonImageStates.length; i++)
        {
            this.buttonImageStates[i] = loadedImage.getSubimage(
                    i * B_WIDTH_DEFAULT,
                    this.rowIndex * B_HEIGHT_DEFAULT,
                    B_WIDTH_DEFAULT,
                    B_HEIGHT_DEFAULT
            );
        }

    }

}

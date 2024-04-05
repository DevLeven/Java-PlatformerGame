package org.devlev.ui.buttons;

import lombok.Getter;
import lombok.Setter;
import org.devlev.utils.GameFile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.devlev.utils.GameConstants.UIElements.URMButtonUI.URM_SIZE;
import static org.devlev.utils.GameConstants.UIElements.URMButtonUI.URM_SIZE_DEFAULT;

public class URMButtonUI extends AbstractButtonUI {

    private BufferedImage[] urmButtonImageArray; //Stores the urm button subImages
    //Determine which button image is selected
    private int index;
    private final int rowIndex;
    //Booleans to keep track of the mouseEvent for the button
    @Getter @Setter
    private boolean mouseHover, mousePressed;

    public URMButtonUI(int buttonXPosition, int buttonYPosition, int buttonWidth, int buttonHeight, int rowIndex) {
        super(buttonXPosition, buttonYPosition, buttonWidth, buttonHeight);
        this.rowIndex = rowIndex;
        loadURMButtonImage();
    }

    @Override
    public void draw(Graphics graphics)
    {
        //Draws the button to specified position on screen
        graphics.drawImage(
                this.urmButtonImageArray[this.index],
                this.buttonXPosition,
                this.buttonYPosition,
                URM_SIZE,
                URM_SIZE,
                null
        );
    }

    @Override
    public void update()
    {
        //Update button index
        this.index = 0; //Reset button index at the start
        if (this.mouseHover)
            this.index = 1;
        if (this.mousePressed)
            this.index = 2;
    }

    public void resetMouseEvents()
    {
        this.mouseHover = false;
        this.mousePressed = false;
    }

    private void loadURMButtonImage()
    {
        //Load in the urm button image
        BufferedImage urmButtonImage = GameFile.getSpriteImage(GameFile.URM_BUTTONS);
        this.urmButtonImageArray = new BufferedImage[3];
        //Stream and set each subImage to respective index
        for (int i = 0; i < this.urmButtonImageArray.length; i++)
        {
            //Get and set the subImage to right index
            this.urmButtonImageArray[i] = urmButtonImage.getSubimage(
                    i * URM_SIZE_DEFAULT,
                    this.rowIndex * URM_SIZE_DEFAULT,
                    URM_SIZE_DEFAULT,
                    URM_SIZE_DEFAULT
            );
        }
    }

}

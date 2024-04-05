package org.devlev.ui.buttons;

import lombok.Getter;
import lombok.Setter;
import org.devlev.utils.GameFile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.devlev.utils.GameConstants.UIElements.VolumeButtonUI.*;

public class VolumeButtonUI extends AbstractButtonUI {

    //Stores the sprites of the volume image
    private BufferedImage[] volumeButtonSprites;
    private BufferedImage volumeSliderSprite;
    @Getter @Setter
    private boolean mouseHover, mousePressed; //Keeps track of mouse events on button
    private int index; //Keeps track of which image should be displayed
    private int sliderButton; //Tracks the buttons movement
    //Sets the minimum and maximum move position for the slider button
    private final int sliderMinimumX, sliderMaximumX;

    public VolumeButtonUI(int buttonXPosition, int buttonYPosition, int buttonWidth, int buttonHeight)
    {
        super( //Puts the hitbox in the middle of the slider
            buttonXPosition + buttonWidth / 2,
            buttonYPosition,
            VOLUME_WIDTH,
            buttonHeight
        );
        //Update button bounding box
        this.buttonBoundBox.x -= VOLUME_WIDTH / 2;
        this.sliderButton = this.buttonXPosition; //Sets it to the middle of the slider
        //Reset position values back to passed in values
        this.buttonXPosition = buttonXPosition;
        this.buttonWidth = buttonWidth;
        //Set the min and max values the button can move on the slider
        this.sliderMinimumX = buttonXPosition + VOLUME_WIDTH / 2;
        this.sliderMaximumX = buttonXPosition + buttonWidth - VOLUME_WIDTH / 2;
        loadVolumeButtonImage();
    }

    @Override
    public void draw(Graphics graphics)
    {
        //Draw volume slider
        graphics.drawImage(
                this.volumeSliderSprite,
                this.buttonXPosition,
                this.buttonYPosition,
                this.buttonWidth,
                this.buttonHeight,
                null
        );
        //Draw volume slider button
        graphics.drawImage(this.volumeButtonSprites[this.index], this.sliderButton - VOLUME_WIDTH / 2, this.buttonYPosition, VOLUME_WIDTH, this.buttonHeight, null);
    }

    @Override
    public void update()
    {
        this.index = 0;
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

    public void changeButtonXPosition(int buttonXPosition)
    {
        if (buttonXPosition < this.sliderMinimumX)
            buttonXPosition = this.sliderMinimumX;
        else if (buttonXPosition > this.sliderMaximumX)
            buttonXPosition = this.sliderMaximumX;
        this.sliderButton = buttonXPosition;
        this.buttonBoundBox.x = buttonXPosition - VOLUME_WIDTH / 2; //Update bounding box position
    }

    private void loadVolumeButtonImage()
    {
        //Load in the volume button sprite
        BufferedImage volumeButtonImage = GameFile.getSpriteImage(GameFile.VOLUME_BUTTONS);
        this.volumeButtonSprites = new BufferedImage[3]; //Initialize array
        //Loop through array length and set subImage to respective index
        for (int i = 0; i < this.volumeButtonSprites.length; i++)
        {
            this.volumeButtonSprites[i] = volumeButtonImage.getSubimage(
                    i * VOLUME_DEFAULT_WIDTH,
                    0,
                    VOLUME_DEFAULT_WIDTH,
                    VOLUME_DEFAULT_HEIGHT
            );
        }
        //Get the slider sprite
        //Multiplied by 3 to move 3 places over from the buttons in sprite image
        this.volumeSliderSprite = volumeButtonImage.getSubimage(
                3 * VOLUME_DEFAULT_WIDTH,
                0,
                SLIDER_DEFAULT_WIDTH,
                VOLUME_DEFAULT_HEIGHT
        );
    }

}

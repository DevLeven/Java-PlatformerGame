package org.devlev.ui.buttons;

import lombok.Getter;
import lombok.Setter;
import org.devlev.utils.GameFile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.devlev.utils.GameConstants.UIElements.PauseButtonUI.SOUND_SIZE_DEFAULT;

public class SoundButtonUI extends AbstractButtonUI {

    private BufferedImage[][] soundButtonImages; //Holds the images of the sound button

    //variables that determine which image we display
    @Getter @Setter
    private boolean mouseHover, mousePressed;
    @Getter @Setter
    private boolean buttonMuted;
    private int rowIndex, columnIndex;

    public SoundButtonUI(int buttonXPosition, int buttonYPosition, int buttonWidth, int buttonHeight) {
        super(buttonXPosition, buttonYPosition, buttonWidth, buttonHeight);
        loadSoundButtonImages();
    }

    @Override
    public void draw(Graphics graphics)
    {
        graphics.drawImage( //Draw in sound button
                this.soundButtonImages[this.rowIndex][this.columnIndex],
                this.buttonXPosition,
                this.buttonYPosition,
                this.buttonWidth,
                this.buttonHeight,
                null
        );
    }

    @Override
    public void update()
    {
        if (this.buttonMuted)
            this.rowIndex = 1;
        else {
            this.rowIndex = 0;
        }

        this.columnIndex = 0; //Set initial column index to 0

        if (this.mouseHover)
            this.columnIndex = 1;
        if (this.mousePressed)
            this.columnIndex = 2;
    }

    public void resetMouseEvents()
    {
        this.mouseHover = false;
        this.mousePressed = false;
    }

    private void loadSoundButtonImages()
    {
        //Loads sound button image
        BufferedImage soundButtonImage = GameFile.getSpriteImage(GameFile.SOUND_BUTTONS);
        this.soundButtonImages = new BufferedImage[2][3]; //Initialize sound button image array
        //Stream through array length and set image subimage to respective index
        for (int o = 0; o < this.soundButtonImages.length; o++)
        {
            for (int i = 0; i < this.soundButtonImages[o].length; i++)
            {
                //Load sound button subImage into respective index
                this.soundButtonImages[o][i] = soundButtonImage.getSubimage(
                        i * SOUND_SIZE_DEFAULT,
                        o * SOUND_SIZE_DEFAULT,
                        SOUND_SIZE_DEFAULT,
                        SOUND_SIZE_DEFAULT
                );
            }
        }
    }


}

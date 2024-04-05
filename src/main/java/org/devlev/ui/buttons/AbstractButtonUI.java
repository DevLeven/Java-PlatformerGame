package org.devlev.ui.buttons;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseEvent;

@Getter
@Setter
public abstract class AbstractButtonUI {

    //Determines position of button on screen
    protected int buttonXPosition, buttonYPosition, buttonWidth, buttonHeight;
    protected Rectangle buttonBoundBox;

    public AbstractButtonUI(int buttonXPosition, int buttonYPosition, int buttonWidth, int buttonHeight) {
        this.buttonXPosition = buttonXPosition;
        this.buttonYPosition = buttonYPosition;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        loadButtonBoundBox();
    }

    public abstract void draw(Graphics graphics);
    public abstract void update();

    //Checks to see if mouse is inside button bound box
    public boolean isInButtonBounds(MouseEvent event)
    {
        return this.buttonBoundBox.contains(event.getX(), event.getY());
    }

    private void loadButtonBoundBox()
    {
        this.buttonBoundBox = new Rectangle(//Loads in button bounding box
                this.buttonXPosition,
                this.buttonYPosition,
                this.buttonWidth,
                this.buttonHeight
        );
    }

}

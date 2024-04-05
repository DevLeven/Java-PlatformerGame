package org.devlev.gameStates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface IGameState {

    void update();
    void draw(Graphics graphics);
    void mouseClicked(MouseEvent mouseEvent);
    void mousePressed(MouseEvent mouseEvent);
    void mouseReleased(MouseEvent mouseEvent);
    void mouseMoved(MouseEvent mouseEvent);
    void keyPressed(KeyEvent keyEvent);
    void keyReleased(KeyEvent keyEvent);

}

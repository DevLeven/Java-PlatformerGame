package org.devlev;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GameWindow {

    private JFrame jFrame; //Create new jFrame variable

    public GameWindow(GamePanel gamePanel)
    {
        jFrame = new JFrame(); //Create new jFrame instance

        //Set screen properties
        //jFrame.setSize(400, 400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);

        jFrame.addFocusListener(new FocusListener() { //Register listeners
            @Override
            public void focusGained(FocusEvent e) {}
            @Override
            public void focusLost(FocusEvent e) {
                //Called when player interacts with elements outside game
                gamePanel.getGameInstance().gameFocusLost();
            }
        });

    }

}

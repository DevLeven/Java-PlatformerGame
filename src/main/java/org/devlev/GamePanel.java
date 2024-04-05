package org.devlev;

import org.devlev.inputs.KeyboardInputs;
import org.devlev.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static org.devlev.Game.GAME_HEIGHT;
import static org.devlev.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs; //Create new mouseInputs variable
    private final Game gameInstance;

    public GamePanel(Game gameInstance)
    {
        this.mouseInputs = new MouseInputs(this); //Initialize class
        this.gameInstance = gameInstance; //Initialize gameClass

        setScreenSize(); //Set size of screen

        addKeyListener(new KeyboardInputs(this)); //Set listener for player key board inputs
        //Set listener for player mouse inputs
        addMouseListener(this.mouseInputs);
        addMouseMotionListener(this.mouseInputs);
    }

    private void setScreenSize() {setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));}

    public void updateGame()
    {
    }

    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics); //Call jPanel paint component method
        this.gameInstance.render(graphics); //Call render in game class, pass in graphics component
        /*this.frames++; //Update the frame each time this is called
        if (System.currentTimeMillis() - lastCheck >= 1000)
        {
            this.lastCheck = System.currentTimeMillis();
            System.out.println("FPS : " + this.frames);
            this.frames = 0;
        }*/

        //repaint(); //Updates the item on the screen
    }

    public Game getGameInstance() {
        return gameInstance;
    }
}

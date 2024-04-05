package org.devlev.level;

import org.devlev.Game;
import org.devlev.utils.GameFile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.devlev.Game.TILES_SIZE;

public class LevelHandler {

    private final Game gameInstance;
    private BufferedImage[] levelImageSpriteArray;
    private Level levelOneInstance;

    public LevelHandler(Game gameInstance)
    {
        this.gameInstance = gameInstance; //Initialize game instance
        loadLevelSpriteImage(); //Load in level image sprite from file
        this.levelOneInstance = new Level(GameFile.getLevelData());
    }

    private void loadLevelSpriteImage()
    {
        //Load level image from file
        BufferedImage levelSpriteImage = GameFile.getSpriteImage(GameFile.LEVEL_SPRITE);
        this.levelImageSpriteArray = new BufferedImage[48];

        //Stream through array and set each image subimage to respective index
        for (int o = 0; o < 4; o++)
        {
            for (int i = 0; i < 12; i++)
            {
                int index = o*12 + i;
                this.levelImageSpriteArray[index] = levelSpriteImage.getSubimage(i*32, o*32, 32, 32);
            }
        }

    }

    public void draw(Graphics graphics, int levelOffset)
    {
        for (int o = 0; o < Game.TILES_HEIGHT; o++)
        {
            for (int i = 0; i < this.levelOneInstance.getLevelData()[0].length; i++)
            {
                int index = this.levelOneInstance.getSpriteIndex(i, o);
                graphics.drawImage(
                        this.levelImageSpriteArray[index],
                        TILES_SIZE * i - levelOffset,
                        TILES_SIZE * o,
                        TILES_SIZE,
                        TILES_SIZE,
                        null
                );
            }
        }
    }

    public void update() {}

    public Level getCurrentLevel() { return this.levelOneInstance; }

}

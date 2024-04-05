package org.devlev.utils;

import org.devlev.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameFile {

    public static final String PLAYER_SPRITE = "player_sprites.png";
    public static final String LEVEL_SPRITE = "outside_sprites.png";
    //public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND_IMAGE = "background_menu_image.png";
    public static final String PLAYING_BG_IMG = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String PAUSE_MENU_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";

    public static BufferedImage getSpriteImage(String imageDir)
    {
        BufferedImage retrievedImage = null;
        InputStream is = GameFile.class.getResourceAsStream("/" + imageDir); //Retrieve image from res folder
        try (is) {
            if (is == null) //Null checker
                return retrievedImage;
            retrievedImage = ImageIO.read(is); //Load image in
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return retrievedImage;
    }

    public static int[][] getLevelData()
    {
        BufferedImage levelImageSprite = getSpriteImage(LEVEL_ONE_DATA);// Retrieve level sprite image
        //Store level data
        int[][] levelDataArray = new int[levelImageSprite.getHeight()][levelImageSprite.getWidth()];

        for (int o = 0; o < levelImageSprite.getHeight(); o++)
        {
            for (int i = 0; i < levelImageSprite.getWidth(); i++)
            {
                Color color = new Color(levelImageSprite.getRGB(i, o));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                levelDataArray[o][i] = value;
            }
        }

        return levelDataArray; //Return level data array

    }

}
